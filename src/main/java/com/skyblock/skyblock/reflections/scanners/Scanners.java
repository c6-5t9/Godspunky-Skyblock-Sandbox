package com.skyblock.skyblock.reflections.scanners;

import java.lang.reflect.AnnotatedElement;
import java.util.stream.Stream;
import java.util.Set;
import java.util.LinkedHashSet;
import com.skyblock.skyblock.reflections.Store;
import com.skyblock.skyblock.reflections.util.QueryFunction;
import java.util.Collections;
import com.skyblock.skyblock.reflections.vfs.Vfs;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.AttributeInfo;
import java.util.function.Function;
import com.skyblock.skyblock.reflections.util.JavassistHelper;
import java.lang.annotation.Inherited;
import java.util.Collection;
import java.util.Arrays;
import com.skyblock.skyblock.reflections.util.FilterBuilder;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;
import java.util.function.Predicate;
import com.skyblock.skyblock.reflections.util.NameHelper;
import com.skyblock.skyblock.reflections.util.QueryBuilder;

public enum Scanners implements Scanner, QueryBuilder, NameHelper
{
    SubTypes {
        {
            this.filterResultsBy((Predicate<String>)new FilterBuilder().excludePattern("java\\.lang\\.Object"));
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            entries.add((Object)this.entry(classFile.getSuperclass(), classFile.getName()));
            entries.addAll((Collection)this.entries((Collection<String>)Arrays.asList((Object[])classFile.getInterfaces()), classFile.getName()));
        }
    }, 
    TypesAnnotated {
        public boolean acceptResult(final String annotation) {
            return super.acceptResult(annotation) || annotation.equals((Object)Inherited.class.getName());
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getAnnotations((Function<String, AttributeInfo>)classFile::getAttribute), classFile.getName()));
        }
    }, 
    MethodsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getAnnotations((Function<String, AttributeInfo>)method::getAttribute), JavassistHelper.methodName(classFile, method))));
        }
    }, 
    ConstructorsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getConstructors(classFile).forEach(constructor -> entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getAnnotations((Function<String, AttributeInfo>)constructor::getAttribute), JavassistHelper.methodName(classFile, constructor))));
        }
    }, 
    FieldsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            classFile.getFields().forEach(field -> entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getAnnotations((Function<String, AttributeInfo>)field::getAttribute), JavassistHelper.fieldName(classFile, field))));
        }
    }, 
    Resources {
        public boolean acceptsInput(final String file) {
            return !file.endsWith(".class");
        }
        
        public List<Map.Entry<String, String>> scan(final Vfs.File file) {
            return (List<Map.Entry<String, String>>)Collections.singletonList((Object)this.entry(file.getName(), file.getRelativePath()));
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            throw new IllegalStateException();
        }
        
        public QueryFunction<Store, String> with(final String pattern) {
            return store -> (LinkedHashSet)((Map)store.getOrDefault((Object)this.index(), (Object)Collections.emptyMap())).entrySet().stream().filter(entry -> ((String)entry.getKey()).matches(pattern)).flatMap(entry -> ((Set)entry.getValue()).stream()).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }, 
    MethodsParameter {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> {
                final String value = JavassistHelper.methodName(classFile, method);
                entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getParameters(method), value));
                JavassistHelper.getParametersAnnotations(method).forEach(annotations -> entries.addAll((Collection)this.entries((Collection<String>)annotations, value)));
            });
        }
    }, 
    ConstructorsParameter {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getConstructors(classFile).forEach(constructor -> {
                final String value = JavassistHelper.methodName(classFile, constructor);
                entries.addAll((Collection)this.entries((Collection<String>)JavassistHelper.getParameters(constructor), value));
                JavassistHelper.getParametersAnnotations(constructor).forEach(annotations -> entries.addAll((Collection)this.entries((Collection<String>)annotations, value)));
            });
        }
    }, 
    MethodsSignature {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.add((Object)this.entry(JavassistHelper.getParameters(method).toString(), JavassistHelper.methodName(classFile, method))));
        }
        
        public QueryFunction<Store, String> with(final AnnotatedElement... keys) {
            return QueryFunction.single(this.toNames(keys).toString()).getAll((java.util.function.Function<String, QueryFunction<Store, String>>)this::get);
        }
    }, 
    ConstructorsSignature {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getConstructors(classFile).forEach(constructor -> entries.add((Object)this.entry(JavassistHelper.getParameters(constructor).toString(), JavassistHelper.methodName(classFile, constructor))));
        }
        
        public QueryFunction<Store, String> with(final AnnotatedElement... keys) {
            return QueryFunction.single(this.toNames(keys).toString()).getAll((java.util.function.Function<String, QueryFunction<Store, String>>)this::get);
        }
    }, 
    MethodsReturn {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.add((Object)this.entry(JavassistHelper.getReturnType(method), JavassistHelper.methodName(classFile, method))));
        }
    };
    
    private Predicate<String> resultFilter;
    
    private Scanners() {
        this.resultFilter = (Predicate<String>)(s -> true);
    }
    
    public String index() {
        return this.name();
    }
    
    public Scanners filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    public final List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = (List<Map.Entry<String, String>>)new ArrayList();
        this.scan(classFile, entries);
        return (List<Map.Entry<String, String>>)entries.stream().filter(a -> this.acceptResult((String)a.getKey())).collect(Collectors.toList());
    }
    
    abstract void scan(final ClassFile p0, final List<Map.Entry<String, String>> p1);
    
    protected boolean acceptResult(final String fqn) {
        return fqn != null && this.resultFilter.test((Object)fqn);
    }
}
