package net.hypixel.skyblock.nms.nmsutil.reflection.annotation;

import java.util.regex.Matcher;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.annotation.Annotation;
import java.util.List;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.MethodWrapper;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.MethodResolver;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.FieldWrapper;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.FieldResolver;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.ClassWrapper;
import net.hypixel.skyblock.nms.nmsutil.reflection.minecraft.Minecraft;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.ClassResolver;
import java.util.regex.Pattern;

public class ReflectionAnnotations
{
    public static final ReflectionAnnotations INSTANCE;
    static final Pattern classRefPattern;
    
    private ReflectionAnnotations() {
    }
    
    public void load(final Object toLoad) {
        if (toLoad == null) {
            throw new IllegalArgumentException("toLoad cannot be null");
        }
        final ClassResolver classResolver = new ClassResolver();
        for (final java.lang.reflect.Field field : toLoad.getClass().getDeclaredFields()) {
            final Class classAnnotation = (Class)field.getAnnotation((java.lang.Class)Class.class);
            final Field fieldAnnotation = (Field)field.getAnnotation((java.lang.Class)Field.class);
            final Method methodAnnotation = (Method)field.getAnnotation((java.lang.Class)Method.class);
            if (classAnnotation != null || fieldAnnotation != null || methodAnnotation != null) {
                field.setAccessible(true);
                if (classAnnotation != null) {
                    final List<String> nameList = this.parseAnnotationVersions(Class.class, classAnnotation);
                    if (nameList.isEmpty()) {
                        throw new IllegalArgumentException("@Class names cannot be empty");
                    }
                    final String[] names = (String[])nameList.toArray((Object[])new String[nameList.size()]);
                    for (int i = 0; i < names.length; ++i) {
                        names[i] = names[i].replace((CharSequence)"{nms}", (CharSequence)("net.minecraft.server." + Minecraft.VERSION.name())).replace((CharSequence)"{obc}", (CharSequence)("org.bukkit.craftbukkit." + Minecraft.VERSION.name()));
                    }
                    try {
                        if (ClassWrapper.class.isAssignableFrom(field.getType())) {
                            field.set(toLoad, (Object)classResolver.resolveWrapper(names));
                        }
                        else {
                            if (!java.lang.Class.class.isAssignableFrom(field.getType())) {
                                this.throwInvalidFieldType(field, toLoad, "Class or ClassWrapper");
                                return;
                            }
                            field.set(toLoad, (Object)classResolver.resolve(names));
                        }
                    }
                    catch (final ReflectiveOperationException e) {
                        if (!classAnnotation.ignoreExceptions()) {
                            this.throwReflectionException("@Class", field, toLoad, e);
                            return;
                        }
                    }
                }
                else if (fieldAnnotation != null) {
                    final List<String> nameList = this.parseAnnotationVersions(Field.class, fieldAnnotation);
                    if (nameList.isEmpty()) {
                        throw new IllegalArgumentException("@Field names cannot be empty");
                    }
                    final String[] names = (String[])nameList.toArray((Object[])new String[nameList.size()]);
                    try {
                        final FieldResolver fieldResolver = new FieldResolver(this.parseClass(Field.class, fieldAnnotation, toLoad));
                        if (FieldWrapper.class.isAssignableFrom(field.getType())) {
                            field.set(toLoad, (Object)fieldResolver.resolveWrapper(names));
                        }
                        else {
                            if (!java.lang.reflect.Field.class.isAssignableFrom(field.getType())) {
                                this.throwInvalidFieldType(field, toLoad, "Field or FieldWrapper");
                                return;
                            }
                            field.set(toLoad, (Object)fieldResolver.resolve(names));
                        }
                    }
                    catch (final ReflectiveOperationException e) {
                        if (!fieldAnnotation.ignoreExceptions()) {
                            this.throwReflectionException("@Field", field, toLoad, e);
                            return;
                        }
                    }
                }
                else if (methodAnnotation != null) {
                    final List<String> nameList = this.parseAnnotationVersions(Method.class, methodAnnotation);
                    if (nameList.isEmpty()) {
                        throw new IllegalArgumentException("@Method names cannot be empty");
                    }
                    final String[] names = (String[])nameList.toArray((Object[])new String[nameList.size()]);
                    final boolean isSignature = names[0].contains((CharSequence)" ");
                    for (final String s : names) {
                        if (s.contains((CharSequence)" ") != isSignature) {
                            throw new IllegalArgumentException("Inconsistent method names: Cannot have mixed signatures/names");
                        }
                    }
                    try {
                        final MethodResolver methodResolver = new MethodResolver(this.parseClass(Method.class, methodAnnotation, toLoad));
                        if (MethodWrapper.class.isAssignableFrom(field.getType())) {
                            if (isSignature) {
                                field.set(toLoad, (Object)methodResolver.resolveSignatureWrapper(names));
                            }
                            else {
                                field.set(toLoad, (Object)methodResolver.resolveWrapper(names));
                            }
                        }
                        else {
                            if (!java.lang.reflect.Method.class.isAssignableFrom(field.getType())) {
                                this.throwInvalidFieldType(field, toLoad, "Method or MethodWrapper");
                                return;
                            }
                            if (isSignature) {
                                field.set(toLoad, (Object)methodResolver.resolveSignature(names));
                            }
                            else {
                                field.set(toLoad, (Object)methodResolver.resolve(names));
                            }
                        }
                    }
                    catch (final ReflectiveOperationException e2) {
                        if (!methodAnnotation.ignoreExceptions()) {
                            this.throwReflectionException("@Method", field, toLoad, e2);
                            return;
                        }
                    }
                }
            }
        }
    }
    
     <A extends Annotation> List<String> parseAnnotationVersions(final java.lang.Class<A> clazz, final A annotation) {
        final List<String> list = (List<String>)new ArrayList();
        try {
            final String[] names = (String[])clazz.getMethod("value", (java.lang.Class<?>[])new java.lang.Class[0]).invoke((Object)annotation, new Object[0]);
            final Minecraft.Version[] versions = (Minecraft.Version[])clazz.getMethod("versions", (java.lang.Class<?>[])new java.lang.Class[0]).invoke((Object)annotation, new Object[0]);
            if (versions.length == 0) {
                Collections.addAll((Collection)list, (Object[])names);
            }
            else {
                if (versions.length > names.length) {
                    throw new RuntimeException("versions array cannot have more elements than the names (" + (Object)clazz + ")");
                }
                for (int i = 0; i < versions.length; ++i) {
                    if (Minecraft.VERSION == versions[i]) {
                        list.add((Object)names[i]);
                    }
                    else if (names[i].startsWith(">") && Minecraft.VERSION.newerThan(versions[i])) {
                        list.add((Object)names[i].substring(1));
                    }
                    else if (names[i].startsWith("<") && Minecraft.VERSION.olderThan(versions[i])) {
                        list.add((Object)names[i].substring(1));
                    }
                }
            }
        }
        catch (final ReflectiveOperationException e) {
            throw new RuntimeException((Throwable)e);
        }
        return list;
    }
    
     <A extends Annotation> String parseClass(final java.lang.Class<A> clazz, final A annotation, final Object toLoad) {
        try {
            final String className = (String)clazz.getMethod("className", (java.lang.Class<?>[])new java.lang.Class[0]).invoke((Object)annotation, new Object[0]);
            final Matcher matcher = ReflectionAnnotations.classRefPattern.matcher((CharSequence)className);
            while (matcher.find()) {
                if (matcher.groupCount() != 1) {
                    continue;
                }
                final String fieldName = matcher.group(1);
                final java.lang.reflect.Field field = toLoad.getClass().getField(fieldName);
                if (ClassWrapper.class.isAssignableFrom(field.getType())) {
                    return ((ClassWrapper)field.get(toLoad)).getName();
                }
                if (java.lang.Class.class.isAssignableFrom(field.getType())) {
                    return ((java.lang.Class)field.get(toLoad)).getName();
                }
            }
            return className;
        }
        catch (final ReflectiveOperationException e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    void throwInvalidFieldType(final java.lang.reflect.Field field, final Object toLoad, final String expected) {
        throw new IllegalArgumentException("Field " + field.getName() + " in " + (Object)toLoad.getClass() + " is not of type " + expected + ", it's " + (Object)field.getType());
    }
    
    void throwReflectionException(final String annotation, final java.lang.reflect.Field field, final Object toLoad, final ReflectiveOperationException exception) {
        throw new RuntimeException("Failed to set " + annotation + " field " + field.getName() + " in " + (Object)toLoad.getClass(), (Throwable)exception);
    }
    
    static {
        INSTANCE = new ReflectionAnnotations();
        classRefPattern = Pattern.compile("@Class\\((.*)\\)");
    }
}
