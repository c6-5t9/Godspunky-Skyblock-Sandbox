package com.skyblock.skyblock.reflections;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.HashSet;
import com.skyblock.skyblock.reflections.util.ClasspathHelper;
import java.util.stream.Stream;
import java.lang.reflect.Proxy;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.function.Function;
import java.util.Arrays;
import java.util.Set;
import com.skyblock.skyblock.reflections.util.QueryFunction;
import java.net.URL;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import com.skyblock.skyblock.reflections.util.UtilQueryBuilder;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.List;
import com.skyblock.skyblock.reflections.util.ReflectionUtilsPredicates;

public abstract class ReflectionUtils extends ReflectionUtilsPredicates
{
    private static final List<String> objectMethodNames;
    public static final Predicate<Method> notObjectMethod;
    public static final UtilQueryBuilder<Class<?>, Class<?>> SuperClass;
    public static final UtilQueryBuilder<Class<?>, Class<?>> Interfaces;
    public static final UtilQueryBuilder<Class<?>, Class<?>> SuperTypes;
    public static final UtilQueryBuilder<AnnotatedElement, Annotation> Annotations;
    public static final UtilQueryBuilder<AnnotatedElement, Class<? extends Annotation>> AnnotationTypes;
    public static final UtilQueryBuilder<Class<?>, Method> Methods;
    public static final UtilQueryBuilder<Class<?>, Constructor> Constructors;
    public static final UtilQueryBuilder<Class<?>, Field> Fields;
    public static final UtilQueryBuilder<String, URL> Resources;
    
    public static <C, T> Set<T> get(final QueryFunction<C, T> function) {
        return function.apply(null);
    }
    
    public static <T> Set<T> get(final QueryFunction<Store, T> queryFunction, final Predicate<? super T>... predicates) {
        return get(queryFunction.filter((java.util.function.Predicate<? super T>)Arrays.stream((Object[])predicates).reduce((Object)(t -> true), Predicate::and)));
    }
    
    public static <T extends AnnotatedElement> UtilQueryBuilder<AnnotatedElement, T> extendType() {
        return element -> {
            if (element instanceof Class && !((Class)element).isAnnotation()) {
                final QueryFunction<Store, Class> single = QueryFunction.single((Class)element);
                return (QueryFunction<Store, T>)single.add(single.getAll((java.util.function.Function<Class, QueryFunction<Store, Class>>)ReflectionUtils.SuperTypes::get));
            }
            else {
                return (QueryFunction<Store, T>)QueryFunction.single(element);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final T type, final Predicate<Annotation>... predicates) {
        return get(ReflectionUtils.Annotations.of(type), (java.util.function.Predicate<? super Annotation>[])predicates);
    }
    
    public static Set<Class<?>> getAllSuperTypes(final Class<?> type, final Predicate<? super Class<?>>... predicates) {
        final Predicate<? super Class<?>>[] filter = (Predicate<? super Class<?>>[])((predicates == null || predicates.length == 0) ? new Predicate[] { t -> !Object.class.equals(t) } : predicates);
        return get(ReflectionUtils.SuperTypes.of(type), filter);
    }
    
    public static Set<Class<?>> getSuperTypes(final Class<?> type) {
        return get(ReflectionUtils.SuperTypes.get(type));
    }
    
    public static Set<Method> getAllMethods(final Class<?> type, final Predicate<? super Method>... predicates) {
        return get(ReflectionUtils.Methods.of(type), predicates);
    }
    
    public static Set<Method> getMethods(final Class<?> t, final Predicate<? super Method>... predicates) {
        return get(ReflectionUtils.Methods.get(t), predicates);
    }
    
    public static Set<Constructor> getAllConstructors(final Class<?> type, final Predicate<? super Constructor>... predicates) {
        return get(ReflectionUtils.Constructors.of(type), predicates);
    }
    
    public static Set<Constructor> getConstructors(final Class<?> t, final Predicate<? super Constructor>... predicates) {
        return get(ReflectionUtils.Constructors.get(t), predicates);
    }
    
    public static Set<Field> getAllFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        return get(ReflectionUtils.Fields.of(type), predicates);
    }
    
    public static Set<Field> getFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        return get(ReflectionUtils.Fields.get(type), predicates);
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAnnotations(final T type, final Predicate<Annotation>... predicates) {
        return get(ReflectionUtils.Annotations.get(type), (java.util.function.Predicate<? super Annotation>[])predicates);
    }
    
    public static Map<String, Object> toMap(final Annotation annotation) {
        return (Map<String, Object>)get(ReflectionUtils.Methods.of(annotation.annotationType()).filter((java.util.function.Predicate<? super Method>)ReflectionUtils.notObjectMethod.and((Predicate)ReflectionUtilsPredicates.withParametersCount(0)))).stream().collect(Collectors.toMap(Method::getName, m -> {
            final Object v1 = invoke(m, annotation, new Object[0]);
            return (v1.getClass().isArray() && v1.getClass().getComponentType().isAnnotation()) ? Stream.of((Object[])v1).map(ReflectionUtils::toMap).collect(Collectors.toList()) : v1;
        }));
    }
    
    public static Map<String, Object> toMap(final Annotation annotation, final AnnotatedElement element) {
        final Map<String, Object> map = toMap(annotation);
        if (element != null) {
            map.put((Object)"annotatedElement", (Object)element);
        }
        return map;
    }
    
    public static Annotation toAnnotation(final Map<String, Object> map) {
        return toAnnotation(map, (Class<Annotation>)map.get((Object)"annotationType"));
    }
    
    public static <T extends Annotation> T toAnnotation(final Map<String, Object> map, final Class<T> annotationType) {
        return (T)Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[] { annotationType }, (proxy, method, args) -> ReflectionUtils.notObjectMethod.test((Object)method) ? map.get((Object)method.getName()) : method.invoke((Object)map, new Object[0]));
    }
    
    public static Object invoke(final Method method, final Object obj, final Object... args) {
        try {
            return method.invoke(obj, args);
        }
        catch (final Exception e) {
            return e;
        }
    }
    
    static {
        objectMethodNames = Arrays.asList((Object[])new String[] { "equals", "hashCode", "toString", "wait", "notify", "notifyAll" });
        notObjectMethod = (m -> !ReflectionUtils.objectMethodNames.contains((Object)m.getName()));
        SuperClass = (element -> ctx -> {
            final Class superclass = element.getSuperclass();
            return (superclass != null && !superclass.equals(Object.class)) ? Collections.singleton((Object)superclass) : Collections.emptySet();
        });
        Interfaces = (element -> ctx -> (LinkedHashSet)Stream.of((Object[])element.getInterfaces()).collect(Collectors.toCollection(LinkedHashSet::new)));
        SuperTypes = new UtilQueryBuilder<Class<?>, Class<?>>() {
            @Override
            public QueryFunction<Store, Class<?>> get(final Class<?> element) {
                return ReflectionUtils.SuperClass.get(element).add(ReflectionUtils.Interfaces.get(element));
            }
            
            @Override
            public QueryFunction<Store, Class<?>> of(final Class<?> element) {
                return QueryFunction.single(element).getAll((java.util.function.Function<Class<?>, QueryFunction<Store, Class<?>>>)ReflectionUtils.SuperTypes::get);
            }
        };
        Annotations = new UtilQueryBuilder<AnnotatedElement, Annotation>() {
            @Override
            public QueryFunction<Store, Annotation> get(final AnnotatedElement element) {
                return ctx -> (LinkedHashSet)Arrays.stream((Object[])element.getAnnotations()).collect(Collectors.toCollection(LinkedHashSet::new));
            }
            
            @Override
            public QueryFunction<Store, Annotation> of(final AnnotatedElement element) {
                return ReflectionUtils.extendType().get(element).getAll((java.util.function.Function<AnnotatedElement, QueryFunction<Store, Annotation>>)ReflectionUtils.Annotations::get, (java.util.function.Function<Annotation, AnnotatedElement>)Annotation::annotationType);
            }
        };
        AnnotationTypes = new UtilQueryBuilder<AnnotatedElement, Class<? extends Annotation>>() {
            @Override
            public QueryFunction<Store, Class<? extends Annotation>> get(final AnnotatedElement element) {
                return ReflectionUtils.Annotations.get(element).map((java.util.function.Function<? super Annotation, ? extends Class<? extends Annotation>>)Annotation::annotationType);
            }
            
            @Override
            public QueryFunction<Store, Class<? extends Annotation>> of(final AnnotatedElement element) {
                return ReflectionUtils.extendType().get(element).getAll((java.util.function.Function<AnnotatedElement, QueryFunction<Store, Class<? extends Annotation>>>)ReflectionUtils.AnnotationTypes::get, (java.util.function.Function<Class<? extends Annotation>, AnnotatedElement>)(a -> a));
            }
        };
        Methods = (element -> ctx -> (LinkedHashSet)Arrays.stream((Object[])element.getDeclaredMethods()).filter((Predicate)ReflectionUtils.notObjectMethod).collect(Collectors.toCollection(LinkedHashSet::new)));
        Constructors = (element -> ctx -> (LinkedHashSet)Arrays.stream((Object[])element.getDeclaredConstructors()).collect(Collectors.toCollection(LinkedHashSet::new)));
        Fields = (element -> ctx -> (LinkedHashSet)Arrays.stream((Object[])element.getDeclaredFields()).collect(Collectors.toCollection(LinkedHashSet::new)));
        Resources = (element -> ctx -> new HashSet((Collection)ClasspathHelper.forResource(element, new ClassLoader[0])));
    }
}
