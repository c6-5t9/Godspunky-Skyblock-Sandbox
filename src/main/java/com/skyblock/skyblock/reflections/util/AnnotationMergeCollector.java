package com.skyblock.skyblock.reflections.util;

import com.skyblock.skyblock.reflections.ReflectionUtils;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.function.BinaryOperator;
import java.util.function.BiConsumer;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.lang.annotation.Annotation;
import java.util.stream.Collector;

public class AnnotationMergeCollector implements Collector<Annotation, Map<String, Object>, Map<String, Object>>
{
    private final AnnotatedElement annotatedElement;
    private final BiFunction<Object, Object, Object> mergeFunction;
    
    public AnnotationMergeCollector(final AnnotatedElement annotatedElement, final BiFunction<Object, Object, Object> mergeFunction) {
        this.annotatedElement = annotatedElement;
        this.mergeFunction = mergeFunction;
    }
    
    public AnnotationMergeCollector() {
        this(null);
    }
    
    public AnnotationMergeCollector(final AnnotatedElement annotatedElement) {
        this(annotatedElement, (BiFunction<Object, Object, Object>)AnnotationMergeCollector::concatValues);
    }
    
    public Supplier<Map<String, Object>> supplier() {
        return (Supplier<Map<String, Object>>)HashMap::new;
    }
    
    public BiConsumer<Map<String, Object>, Annotation> accumulator() {
        return (BiConsumer<Map<String, Object>, Annotation>)((acc, ann) -> this.mergeMaps((Map<String, Object>)acc, ReflectionUtils.toMap(ann, this.annotatedElement)));
    }
    
    public BinaryOperator<Map<String, Object>> combiner() {
        return (BinaryOperator<Map<String, Object>>)this::mergeMaps;
    }
    
    public Function<Map<String, Object>, Map<String, Object>> finisher() {
        return (Function<Map<String, Object>, Map<String, Object>>)Function.identity();
    }
    
    public Set<Collector.Characteristics> characteristics() {
        return (Set<Collector.Characteristics>)Collections.emptySet();
    }
    
    private Map<String, Object> mergeMaps(final Map<String, Object> m1, final Map<String, Object> m2) {
        m2.forEach((k1, v1) -> m1.merge((Object)k1, v1, (BiFunction)this.mergeFunction));
        return m1;
    }
    
    private static Object concatValues(final Object v1, final Object v2) {
        if (v1.getClass().isArray()) {
            if (v2.getClass().getComponentType().equals(String.class)) {
                return stringArrayConcat((String[])v1, (String[])v2);
            }
            return arrayAdd((Object[])v1, (Object[])v2);
        }
        else {
            if (v2.getClass().equals(String.class)) {
                return stringConcat((String)v1, (String)v2);
            }
            return v2;
        }
    }
    
    private static Object[] arrayAdd(final Object[] o1, final Object[] o2) {
        return (o2.length == 0) ? o1 : ((o1.length == 0) ? o2 : Stream.concat(Stream.of(o1), Stream.of(o2)).toArray(Object[]::new));
    }
    
    private static Object stringArrayConcat(final String[] v1, final String[] v2) {
        return (v2.length == 0) ? v1 : ((v1.length == 0) ? v2 : Arrays.stream((Object[])v2).flatMap(s2 -> Arrays.stream((Object[])v1).map(s1 -> s2 + s1)).toArray(String[]::new));
    }
    
    private static Object stringConcat(final String v1, final String v2) {
        return v2.isEmpty() ? v1 : (v1.isEmpty() ? v2 : (v1 + v2));
    }
}
