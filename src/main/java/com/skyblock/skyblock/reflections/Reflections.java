package com.skyblock.skyblock.reflections;

import org.slf4j.LoggerFactory;
import java.util.HashSet;
import com.skyblock.skyblock.reflections.scanners.MemberUsageScanner;
import java.util.Collections;
import com.skyblock.skyblock.reflections.scanners.MethodParameterNamesScanner;
import java.util.List;
import java.lang.reflect.Member;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.skyblock.skyblock.reflections.util.ReflectionUtilsPredicates;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import com.skyblock.skyblock.reflections.util.QueryFunction;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Collection;
import java.util.stream.StreamSupport;
import com.skyblock.skyblock.reflections.util.ClasspathHelper;
import com.skyblock.skyblock.reflections.serializers.Serializer;
import com.skyblock.skyblock.reflections.serializers.XmlSerializer;
import com.skyblock.skyblock.reflections.util.FilterBuilder;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import javassist.bytecode.ClassFile;
import javax.annotation.Nullable;
import java.util.function.Predicate;
import com.skyblock.skyblock.reflections.vfs.Vfs;
import java.util.Iterator;
import java.net.URL;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.HashMap;
import com.skyblock.skyblock.reflections.scanners.Scanner;
import com.skyblock.skyblock.reflections.util.ConfigurationBuilder;
import java.util.Set;
import com.skyblock.skyblock.reflections.scanners.Scanners;
import java.util.Map;
import org.slf4j.Logger;
import com.skyblock.skyblock.reflections.util.NameHelper;

public class Reflections implements NameHelper
{
    public static final Logger log;
    protected final transient Configuration configuration;
    protected final Store store;
    
    public Reflections(final Configuration configuration) {
        this.configuration = configuration;
        final Map<String, Map<String, Set<String>>> storeMap = this.scan();
        if (configuration.shouldExpandSuperTypes()) {
            this.expandSuperTypes((Map<String, Set<String>>)storeMap.get((Object)Scanners.SubTypes.index()), (Map<String, Set<String>>)storeMap.get((Object)Scanners.TypesAnnotated.index()));
        }
        this.store = new Store(storeMap);
    }
    
    public Reflections(final Store store) {
        this.configuration = new ConfigurationBuilder();
        this.store = store;
    }
    
    public Reflections(final String prefix, final Scanner... scanners) {
        this(new Object[] { prefix, scanners });
    }
    
    public Reflections(final Object... params) {
        this(ConfigurationBuilder.build(params));
    }
    
    protected Reflections() {
        this.configuration = new ConfigurationBuilder();
        this.store = new Store((Map<String, Map<String, Set<String>>>)new HashMap());
    }
    
    protected Map<String, Map<String, Set<String>>> scan() {
        final long start = System.currentTimeMillis();
        final Map<String, Set<Map.Entry<String, String>>> collect = (Map<String, Set<Map.Entry<String, String>>>)this.configuration.getScanners().stream().map(Scanner::index).distinct().collect(Collectors.toMap(s -> s, s -> Collections.synchronizedSet((Set)new HashSet())));
        final Set<URL> urls = this.configuration.getUrls();
        ((Stream)(this.configuration.isParallel() ? urls.stream().parallel() : urls.stream())).forEach(url -> {
            Vfs.Dir dir = null;
            try {
                dir = Vfs.fromURL(url);
                for (final Vfs.File file : dir.getFiles()) {
                    if (this.doFilter(file, this.configuration.getInputsFilter())) {
                        ClassFile classFile = null;
                        for (final Scanner scanner : this.configuration.getScanners()) {
                            try {
                                if (!this.doFilter(file, (Predicate<String>)scanner::acceptsInput)) {
                                    continue;
                                }
                                List<Map.Entry<String, String>> entries = scanner.scan(file);
                                if (entries == null) {
                                    if (classFile == null) {
                                        classFile = this.getClassFile(file);
                                    }
                                    entries = scanner.scan(classFile);
                                }
                                if (entries == null) {
                                    continue;
                                }
                                ((Set)collect.get((Object)scanner.index())).addAll((Collection)entries);
                            }
                            catch (final Exception e) {
                                if (Reflections.log == null) {
                                    continue;
                                }
                                Reflections.log.trace("could not scan file {} with scanner {}", new Object[] { file.getRelativePath(), scanner.getClass().getSimpleName(), e });
                            }
                        }
                    }
                }
            }
            catch (final Exception e2) {
                if (Reflections.log != null) {
                    Reflections.log.warn("could not create Vfs.Dir from url. ignoring the exception and continuing", (Throwable)e2);
                }
            }
            finally {
                if (dir != null) {
                    dir.close();
                }
            }
        });
        final Map<String, Map<String, Set<String>>> storeMap = (Map<String, Map<String, Set<String>>>)collect.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (HashMap)((Set)entry.getValue()).stream().filter(e -> e.getKey() != null).collect(Collectors.groupingBy(Map.Entry::getKey, HashMap::new, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())))));
        if (Reflections.log != null) {
            int keys = 0;
            int values = 0;
            for (final Map<String, Set<String>> map : storeMap.values()) {
                keys += map.size();
                values += (int)map.values().stream().mapToLong(Set::size).sum();
            }
            Reflections.log.info(String.format("Reflections took %d ms to scan %d urls, producing %d keys and %d values", new Object[] { System.currentTimeMillis() - start, urls.size(), keys, values }));
        }
        return storeMap;
    }
    
    private boolean doFilter(final Vfs.File file, @Nullable final Predicate<String> predicate) {
        final String path = file.getRelativePath();
        final String fqn = path.replace('/', '.');
        return predicate == null || predicate.test((Object)path) || predicate.test((Object)fqn);
    }
    
    private ClassFile getClassFile(final Vfs.File file) {
        try (final DataInputStream dis = new DataInputStream((InputStream)new BufferedInputStream(file.openInputStream()))) {
            return new ClassFile(dis);
        }
        catch (final Exception e) {
            throw new ReflectionsException("could not create class object from file " + file.getRelativePath(), (Throwable)e);
        }
    }
    
    public static Reflections collect() {
        return collect("META-INF/reflections/", (Predicate<String>)new FilterBuilder().includePattern(".*-reflections\\.xml"));
    }
    
    public static Reflections collect(final String packagePrefix, final Predicate<String> resourceNameFilter) {
        return collect(packagePrefix, resourceNameFilter, new XmlSerializer());
    }
    
    public static Reflections collect(final String packagePrefix, final Predicate<String> resourceNameFilter, final Serializer serializer) {
        final Collection<URL> urls = ClasspathHelper.forPackage(packagePrefix, new ClassLoader[0]);
        final Iterable<Vfs.File> files = Vfs.findFiles(urls, packagePrefix, resourceNameFilter);
        final Reflections reflections = new Reflections();
        StreamSupport.stream(files.spliterator(), false).forEach(file -> {
            try (final InputStream inputStream = file.openInputStream()) {
                reflections.collect(inputStream, serializer);
            }
            catch (final IOException e) {
                throw new ReflectionsException("could not merge " + (Object)file, (Throwable)e);
            }
        });
        return reflections;
    }
    
    public Reflections collect(final InputStream inputStream, final Serializer serializer) {
        return this.merge(serializer.read(inputStream));
    }
    
    public Reflections collect(final File file, final Serializer serializer) {
        try (final FileInputStream inputStream = new FileInputStream(file)) {
            return this.collect((InputStream)inputStream, serializer);
        }
        catch (final IOException e) {
            throw new ReflectionsException("could not obtain input stream from file " + (Object)file, (Throwable)e);
        }
    }
    
    public Reflections merge(final Reflections reflections) {
        reflections.store.forEach((index, map) -> {
            final Map map2 = (Map)this.store.merge((Object)index, (Object)map, (m1, m2) -> {
                m2.forEach((k, v) -> {
                    final Set set = (Set)m1.merge((Object)k, (Object)v, (s1, s2) -> {
                        s1.addAll((Collection)s2);
                        return s1;
                    });
                });
                return m1;
            });
        });
        return this;
    }
    
    public void expandSuperTypes(final Map<String, Set<String>> subTypesStore, final Map<String, Set<String>> typesAnnotatedStore) {
        if (subTypesStore == null || subTypesStore.isEmpty()) {
            return;
        }
        final Set<String> keys = (Set<String>)new LinkedHashSet((Collection)subTypesStore.keySet());
        keys.removeAll((Collection)subTypesStore.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()));
        keys.remove((Object)"java.lang.Object");
        for (final String key : keys) {
            final Class<?> type = this.forClass(key, this.loaders());
            if (type != null) {
                this.expandSupertypes(subTypesStore, typesAnnotatedStore, key, type);
            }
        }
    }
    
    private void expandSupertypes(final Map<String, Set<String>> subTypesStore, final Map<String, Set<String>> typesAnnotatedStore, final String key, final Class<?> type) {
        final Set<Annotation> typeAnnotations = ReflectionUtils.getAnnotations(type, (Predicate<Annotation>[])new Predicate[0]);
        if (typesAnnotatedStore != null && !typeAnnotations.isEmpty()) {
            final String typeName = type.getName();
            for (final Annotation typeAnnotation : typeAnnotations) {
                final String annotationName = typeAnnotation.annotationType().getName();
                ((Set)typesAnnotatedStore.computeIfAbsent((Object)annotationName, s -> new HashSet())).add((Object)typeName);
            }
        }
        for (final Class<?> supertype : ReflectionUtils.getSuperTypes(type)) {
            final String supertypeName = supertype.getName();
            if (subTypesStore.containsKey((Object)supertypeName)) {
                ((Set)subTypesStore.get((Object)supertypeName)).add((Object)key);
            }
            else {
                ((Set)subTypesStore.computeIfAbsent((Object)supertypeName, s -> new HashSet())).add((Object)key);
                this.expandSupertypes(subTypesStore, typesAnnotatedStore, supertypeName, supertype);
            }
        }
    }
    
    public <T> Set<T> get(final QueryFunction<Store, T> query) {
        return query.apply(this.store);
    }
    
    public <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> type) {
        return this.get(Scanners.SubTypes.of(new AnnotatedElement[] { (AnnotatedElement)type }).as((Class<? extends Class<? extends T>>)Class.class, this.loaders()));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        return this.get(Scanners.SubTypes.of(Scanners.TypesAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation })).asClass(this.loaders()));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation, final boolean honorInherited) {
        if (!honorInherited) {
            return this.getTypesAnnotatedWith(annotation);
        }
        if (annotation.isAnnotationPresent((Class<? extends Annotation>)Inherited.class)) {
            return this.get(Scanners.TypesAnnotated.get((AnnotatedElement)annotation).add(Scanners.SubTypes.of(Scanners.TypesAnnotated.get((AnnotatedElement)annotation).filter((java.util.function.Predicate<? super String>)(c -> !this.forClass(c, this.loaders()).isInterface())))).asClass(this.loaders()));
        }
        return this.get(Scanners.TypesAnnotated.get((AnnotatedElement)annotation).asClass(this.loaders()));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Annotation annotation) {
        return this.get(Scanners.SubTypes.of(Scanners.TypesAnnotated.of(Scanners.TypesAnnotated.get((AnnotatedElement)annotation.annotationType()).filter((java.util.function.Predicate<? super String>)(c -> ReflectionUtilsPredicates.withAnnotation(annotation).test((Object)this.forClass(c, this.loaders())))))).asClass(this.loaders()));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Annotation annotation, final boolean honorInherited) {
        if (!honorInherited) {
            return this.getTypesAnnotatedWith(annotation);
        }
        final Class<? extends Annotation> type = annotation.annotationType();
        if (type.isAnnotationPresent((Class<? extends Annotation>)Inherited.class)) {
            return this.get(Scanners.TypesAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)type }).asClass(this.loaders()).filter(ReflectionUtilsPredicates.withAnnotation(annotation)).add(Scanners.SubTypes.of(Scanners.TypesAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)type }).asClass(this.loaders()).filter((java.util.function.Predicate<? super Class<?>>)(c -> !c.isInterface())))));
        }
        return this.get(Scanners.TypesAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)type }).asClass(this.loaders()).filter(ReflectionUtilsPredicates.withAnnotation(annotation)));
    }
    
    public Set<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> annotation) {
        return this.get(Scanners.MethodsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation }).as((Class<? extends Method>)Method.class, this.loaders()));
    }
    
    public Set<Method> getMethodsAnnotatedWith(final Annotation annotation) {
        return this.get((QueryFunction<Store, Method>)Scanners.MethodsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation.annotationType() }).as((Class<? extends T>)Method.class, this.loaders()).filter(ReflectionUtilsPredicates.withAnnotation(annotation)));
    }
    
    public Set<Method> getMethodsWithSignature(final Class<?>... types) {
        return this.get(Scanners.MethodsSignature.with((AnnotatedElement[])types).as((Class<? extends Method>)Method.class, this.loaders()));
    }
    
    public Set<Method> getMethodsWithParameter(final AnnotatedElement type) {
        return this.get(Scanners.MethodsParameter.with(new AnnotatedElement[] { type }).as((Class<? extends Method>)Method.class, this.loaders()));
    }
    
    public Set<Method> getMethodsReturn(final Class<?> type) {
        return this.get(Scanners.MethodsReturn.of(new AnnotatedElement[] { (AnnotatedElement)type }).as((Class<? extends Method>)Method.class, this.loaders()));
    }
    
    public Set<Constructor> getConstructorsAnnotatedWith(final Class<? extends Annotation> annotation) {
        return this.get(Scanners.ConstructorsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation }).as((Class<? extends Constructor>)Constructor.class, this.loaders()));
    }
    
    public Set<Constructor> getConstructorsAnnotatedWith(final Annotation annotation) {
        return this.get((QueryFunction<Store, Constructor>)Scanners.ConstructorsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation.annotationType() }).as((Class<? extends T>)Constructor.class, this.loaders()).filter((java.util.function.Predicate<? super T>)ReflectionUtilsPredicates.withAnyParameterAnnotation(annotation)));
    }
    
    public Set<Constructor> getConstructorsWithSignature(final Class<?>... types) {
        return this.get(Scanners.ConstructorsSignature.with((AnnotatedElement[])types).as((Class<? extends Constructor>)Constructor.class, this.loaders()));
    }
    
    public Set<Constructor> getConstructorsWithParameter(final AnnotatedElement type) {
        return this.get(Scanners.ConstructorsParameter.of(new AnnotatedElement[] { type }).as((Class<? extends Constructor>)Constructor.class, this.loaders()));
    }
    
    public Set<Field> getFieldsAnnotatedWith(final Class<? extends Annotation> annotation) {
        return this.get(Scanners.FieldsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation }).as((Class<? extends Field>)Field.class, this.loaders()));
    }
    
    public Set<Field> getFieldsAnnotatedWith(final Annotation annotation) {
        return this.get((QueryFunction<Store, Field>)Scanners.FieldsAnnotated.with(new AnnotatedElement[] { (AnnotatedElement)annotation.annotationType() }).as((Class<? extends T>)Field.class, this.loaders()).filter(ReflectionUtilsPredicates.withAnnotation(annotation)));
    }
    
    public Set<String> getResources(final String pattern) {
        return this.get(Scanners.Resources.with(pattern));
    }
    
    public Set<String> getResources(final Pattern pattern) {
        return this.getResources(pattern.pattern());
    }
    
    public List<String> getMemberParameterNames(final Member member) {
        return (List<String>)((Set)((Map)this.store.getOrDefault((Object)MethodParameterNamesScanner.class.getSimpleName(), (Object)Collections.emptyMap())).getOrDefault((Object)this.toName((AnnotatedElement)member), (Object)Collections.emptySet())).stream().flatMap(s -> Stream.of((Object[])s.split(", "))).collect(Collectors.toList());
    }
    
    public Collection<Member> getMemberUsage(final Member member) {
        final Set<String> usages = (Set<String>)((Map)this.store.getOrDefault((Object)MemberUsageScanner.class.getSimpleName(), (Object)Collections.emptyMap())).getOrDefault((Object)this.toName((AnnotatedElement)member), (Object)Collections.emptySet());
        return this.forNames((Collection<String>)usages, Member.class, this.loaders());
    }
    
    @Deprecated
    public Set<String> getAllTypes() {
        return this.getAll(Scanners.SubTypes);
    }
    
    public Set<String> getAll(final Scanner scanner) {
        final Map<String, Set<String>> map = (Map<String, Set<String>>)this.store.getOrDefault((Object)scanner.index(), (Object)Collections.emptyMap());
        return (Set<String>)Stream.concat(map.keySet().stream(), map.values().stream().flatMap(Collection::stream)).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    public Store getStore() {
        return this.store;
    }
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public File save(final String filename) {
        return this.save(filename, new XmlSerializer());
    }
    
    public File save(final String filename, final Serializer serializer) {
        return serializer.save(this, filename);
    }
    
    ClassLoader[] loaders() {
        return this.configuration.getClassLoaders();
    }
    
    static {
        log = LoggerFactory.getLogger((Class)Reflections.class);
    }
}
