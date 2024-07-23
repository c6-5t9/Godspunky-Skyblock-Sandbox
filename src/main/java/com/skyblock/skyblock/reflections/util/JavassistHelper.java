package com.skyblock.skyblock.reflections.util;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ParameterAnnotationsAttribute;
import java.util.Collections;
import java.util.stream.Collectors;
import javassist.bytecode.annotation.Annotation;
import java.util.Collection;
import javassist.bytecode.AttributeInfo;
import java.util.function.Function;
import javassist.bytecode.Descriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.ClassFile;

public class JavassistHelper
{
    public static boolean includeInvisibleTag;
    
    public static String fieldName(final ClassFile classFile, final FieldInfo object) {
        return String.format("%s.%s", new Object[] { classFile.getName(), object.getName() });
    }
    
    public static String methodName(final ClassFile classFile, final MethodInfo object) {
        return String.format("%s.%s(%s)", new Object[] { classFile.getName(), object.getName(), String.join((CharSequence)", ", (Iterable)getParameters(object)) });
    }
    
    public static boolean isPublic(final Object object) {
        if (object instanceof ClassFile) {
            return AccessFlag.isPublic(((ClassFile)object).getAccessFlags());
        }
        if (object instanceof FieldInfo) {
            return AccessFlag.isPublic(((FieldInfo)object).getAccessFlags());
        }
        return object instanceof MethodInfo && AccessFlag.isPublic(((MethodInfo)object).getAccessFlags());
    }
    
    public static Stream<MethodInfo> getMethods(final ClassFile classFile) {
        return (Stream<MethodInfo>)classFile.getMethods().stream().filter(MethodInfo::isMethod);
    }
    
    public static Stream<MethodInfo> getConstructors(final ClassFile classFile) {
        return (Stream<MethodInfo>)classFile.getMethods().stream().filter(methodInfo -> !methodInfo.isMethod());
    }
    
    public static List<String> getParameters(final MethodInfo method) {
        final List<String> result = (List<String>)new ArrayList();
        final String descriptor = method.getDescriptor().substring(1);
        final Descriptor.Iterator iterator = new Descriptor.Iterator(descriptor);
        Integer prev = null;
        while (iterator.hasNext()) {
            final int cur = iterator.next();
            if (prev != null) {
                result.add((Object)Descriptor.toString(descriptor.substring((int)prev, cur)));
            }
            prev = cur;
        }
        return result;
    }
    
    public static String getReturnType(final MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.lastIndexOf(")") + 1);
        return Descriptor.toString(descriptor);
    }
    
    public static List<String> getAnnotations(final Function<String, AttributeInfo> function) {
        final Function<String, List<String>> names = (Function<String, List<String>>)function.andThen(attribute -> (attribute != null) ? ((AnnotationsAttribute)attribute).getAnnotations() : null).andThen(JavassistHelper::annotationNames);
        final List<String> result = (List<String>)new ArrayList((Collection)names.apply((Object)"RuntimeVisibleAnnotations"));
        if (JavassistHelper.includeInvisibleTag) {
            result.addAll((Collection)names.apply((Object)"RuntimeInvisibleAnnotations"));
        }
        return result;
    }
    
    public static List<List<String>> getParametersAnnotations(final MethodInfo method) {
        final Function<String, List<List<String>>> names = (Function<String, List<List<String>>>)((Function)method::getAttribute).andThen(attribute -> (attribute != null) ? ((ParameterAnnotationsAttribute)attribute).getAnnotations() : null).andThen(aa -> (aa != null) ? Stream.of((Object[])aa).map(JavassistHelper::annotationNames).collect(Collectors.toList()) : Collections.emptyList());
        final List<List<String>> visibleAnnotations = (List<List<String>>)names.apply((Object)"RuntimeVisibleParameterAnnotations");
        if (!JavassistHelper.includeInvisibleTag) {
            return (List<List<String>>)new ArrayList((Collection)visibleAnnotations);
        }
        final List<List<String>> invisibleAnnotations = (List<List<String>>)names.apply((Object)"RuntimeInvisibleParameterAnnotations");
        if (invisibleAnnotations.isEmpty()) {
            return (List<List<String>>)new ArrayList((Collection)visibleAnnotations);
        }
        final List<List<String>> result = (List<List<String>>)new ArrayList();
        for (int i = 0; i < Math.max(visibleAnnotations.size(), invisibleAnnotations.size()); ++i) {
            final List<String> concat = (List<String>)new ArrayList();
            if (i < visibleAnnotations.size()) {
                concat.addAll((Collection)visibleAnnotations.get(i));
            }
            if (i < invisibleAnnotations.size()) {
                concat.addAll((Collection)invisibleAnnotations.get(i));
            }
            result.add((Object)concat);
        }
        return result;
    }
    
    private static List<String> annotationNames(final Annotation[] annotations) {
        return (List<String>)((annotations != null) ? Stream.of((Object[])annotations).map(Annotation::getTypeName).collect(Collectors.toList()) : Collections.emptyList());
    }
    
    static {
        JavassistHelper.includeInvisibleTag = true;
    }
}
