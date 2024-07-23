package net.hypixel.skyblock.api.disguise.utils;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.lang.reflect.UndeclaredThrowableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ReflectionUtils
{
    public static final MethodFilter USER_DECLARED_METHODS;
    public static final FieldFilter COPYABLE_FIELDS;
    private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";
    private static final Class<?>[] EMPTY_CLASS_ARRAY;
    private static final Method[] EMPTY_METHOD_ARRAY;
    private static final Field[] EMPTY_FIELD_ARRAY;
    private static final Object[] EMPTY_OBJECT_ARRAY;
    private static final Map<Class<?>, Method[]> declaredMethodsCache;
    private static final Map<Class<?>, Field[]> declaredFieldsCache;
    
    public static void handleReflectionException(final Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException)ex);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        }
        throw new UndeclaredThrowableException((Throwable)ex);
    }
    
    public static void handleInvocationTargetException(final InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }
    
    public static void rethrowRuntimeException(final Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        }
        if (ex instanceof Error) {
            throw (Error)ex;
        }
        throw new UndeclaredThrowableException(ex);
    }
    
    public static void rethrowException(final Throwable throwable) throws Exception {
        if (throwable instanceof Exception) {
            throw (Exception)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        throw new UndeclaredThrowableException(throwable);
    }
    
    public static <T> Constructor<T> accessibleConstructor(final Class<T> clazz, final Class<?>... parameterTypes) throws NoSuchMethodException {
        final Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
        makeAccessible(ctor);
        return ctor;
    }
    
    public static void makeAccessible(final Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }
    
    @Nullable
    public static Method findMethod(final Class<?> clazz, final String name) {
        return findMethod(clazz, name, ReflectionUtils.EMPTY_CLASS_ARRAY);
    }
    
    @Nullable
    public static Method findMethod(final Class<?> clazz, final String name, @Nullable final Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
        for (Class<?> searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            final Method[] array;
            final Method[] methods = array = (searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType, false));
            for (final Method method : array) {
                if (name.equals((Object)method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes))) {
                    return method;
                }
            }
        }
        return null;
    }
    
    private static boolean hasSameParams(final Method method, final Class<?>[] paramTypes) {
        return paramTypes.length == method.getParameterCount() && Arrays.equals((Object[])paramTypes, (Object[])method.getParameterTypes());
    }
    
    @Nullable
    public static Object invokeMethod(final Method method, @Nullable final Object target) {
        return invokeMethod(method, target, ReflectionUtils.EMPTY_OBJECT_ARRAY);
    }
    
    @Nullable
    public static Object invokeMethod(final Method method, @Nullable final Object target, @Nullable final Object... args) {
        try {
            return method.invoke(target, args);
        }
        catch (final Exception ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Should never get here");
        }
    }
    
    public static boolean declaresException(final Method method, final Class<?> exceptionType) {
        Assert.notNull(method, "Method must not be null");
        final Class[] exceptionTypes;
        final Class<?>[] declaredExceptions = exceptionTypes = method.getExceptionTypes();
        for (final Class<?> declaredException : exceptionTypes) {
            if (declaredException.isAssignableFrom(exceptionType)) {
                return true;
            }
        }
        return false;
    }
    
    public static void doWithLocalMethods(final Class<?> clazz, final MethodCallback mc) {
        final Method[] declaredMethods;
        final Method[] methods = declaredMethods = getDeclaredMethods(clazz, false);
        for (final Method method : declaredMethods) {
            try {
                mc.doWith(method);
            }
            catch (final IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + (Object)ex);
            }
        }
    }
    
    public static void doWithMethods(final Class<?> clazz, final MethodCallback mc) {
        doWithMethods(clazz, mc, null);
    }
    
    public static void doWithMethods(final Class<?> clazz, final MethodCallback mc, @Nullable final MethodFilter mf) {
        if (mf == ReflectionUtils.USER_DECLARED_METHODS && clazz == Object.class) {
            return;
        }
        final Method[] declaredMethods;
        final Method[] methods = declaredMethods = getDeclaredMethods(clazz, false);
        for (final Method method : declaredMethods) {
            if (mf == null || mf.matches(method)) {
                try {
                    mc.doWith(method);
                }
                catch (final IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + (Object)ex);
                }
            }
        }
        if (clazz.getSuperclass() != null && (mf != ReflectionUtils.USER_DECLARED_METHODS || clazz.getSuperclass() != Object.class)) {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        }
        else if (clazz.isInterface()) {
            for (final Class<?> superIfc : clazz.getInterfaces()) {
                doWithMethods(superIfc, mc, mf);
            }
        }
    }
    
    public static Method[] getAllDeclaredMethods(final Class<?> leafClass) {
        final List<Method> methods = (List<Method>)new ArrayList(20);
        doWithMethods(leafClass, methods::add);
        return (Method[])methods.toArray((Object[])ReflectionUtils.EMPTY_METHOD_ARRAY);
    }
    
    public static Method[] getUniqueDeclaredMethods(final Class<?> leafClass) {
        return getUniqueDeclaredMethods(leafClass, null);
    }
    
    public static Method[] getUniqueDeclaredMethods(final Class<?> leafClass, @Nullable final MethodFilter mf) {
        final List<Method> methods = (List<Method>)new ArrayList(20);
        doWithMethods(leafClass, method -> {
            boolean knownSignature = false;
            Method methodBeingOverriddenWithCovariantReturnType = null;
            methods.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Method existingMethod = (Method)iterator.next();
                if (method.getName().equals((Object)existingMethod.getName()) && method.getParameterCount() == existingMethod.getParameterCount() && Arrays.equals((Object[])method.getParameterTypes(), (Object[])existingMethod.getParameterTypes())) {
                    if (existingMethod.getReturnType() != method.getReturnType() && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                        methodBeingOverriddenWithCovariantReturnType = existingMethod;
                        break;
                    }
                    else {
                        knownSignature = true;
                        break;
                    }
                }
            }
            if (methodBeingOverriddenWithCovariantReturnType != null) {
                methods.remove((Object)methodBeingOverriddenWithCovariantReturnType);
            }
            if (!knownSignature && !isCglibRenamedMethod(method)) {
                methods.add((Object)method);
            }
            return;
        }, mf);
        return (Method[])methods.toArray((Object[])ReflectionUtils.EMPTY_METHOD_ARRAY);
    }
    
    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        return getDeclaredMethods(clazz, true);
    }
    
    private static Method[] getDeclaredMethods(final Class<?> clazz, final boolean defensive) {
        Assert.notNull(clazz, "Class must not be null");
        Method[] result = (Method[])ReflectionUtils.declaredMethodsCache.get((Object)clazz);
        if (result == null) {
            try {
                final Method[] declaredMethods = clazz.getDeclaredMethods();
                final List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy((Object)declaredMethods, 0, (Object)result, 0, declaredMethods.length);
                    int index = declaredMethods.length;
                    for (final Method defaultMethod : defaultMethods) {
                        result[index] = defaultMethod;
                        ++index;
                    }
                }
                else {
                    result = declaredMethods;
                }
                ReflectionUtils.declaredMethodsCache.put((Object)clazz, (Object)((result.length == 0) ? ReflectionUtils.EMPTY_METHOD_ARRAY : result));
            }
            catch (final Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + (Object)clazz.getClassLoader() + "]", ex);
            }
        }
        return (result.length == 0 || !defensive) ? result : result.clone();
    }
    
    @Nullable
    private static List<Method> findConcreteMethodsOnInterfaces(final Class<?> clazz) {
        List<Method> result = null;
        for (final Class<?> ifc : clazz.getInterfaces()) {
            for (final Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = (List<Method>)new ArrayList();
                    }
                    result.add((Object)ifcMethod);
                }
            }
        }
        return result;
    }
    
    public static boolean isEqualsMethod(@Nullable final Method method) {
        return method != null && method.getParameterCount() == 1 && method.getName().equals((Object)"equals") && method.getParameterTypes()[0] == Object.class;
    }
    
    public static boolean isHashCodeMethod(@Nullable final Method method) {
        return method != null && method.getParameterCount() == 0 && method.getName().equals((Object)"hashCode");
    }
    
    public static boolean isToStringMethod(@Nullable final Method method) {
        return method != null && method.getParameterCount() == 0 && method.getName().equals((Object)"toString");
    }
    
    public static boolean isObjectMethod(@Nullable final Method method) {
        return method != null && (method.getDeclaringClass() == Object.class || isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method));
    }
    
    public static boolean isCglibRenamedMethod(final Method renamedMethod) {
        final String name = renamedMethod.getName();
        if (name.startsWith("CGLIB$")) {
            int i;
            for (i = name.length() - 1; i >= 0 && Character.isDigit(name.charAt(i)); --i) {}
            return i > "CGLIB$".length() && i < name.length() - 1 && name.charAt(i) == '$';
        }
        return false;
    }
    
    public static void makeAccessible(final Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }
    
    @Nullable
    public static Field findField(final Class<?> clazz, final String name) {
        return findField(clazz, name, null);
    }
    
    @Nullable
    public static Field findField(final Class<?> clazz, @Nullable final String name, @Nullable final Class<?> type) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        for (Class<?> searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
            final Field[] declaredFields;
            final Field[] fields = declaredFields = getDeclaredFields(searchType);
            for (final Field field : declaredFields) {
                if ((name == null || name.equals((Object)field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
        }
        return null;
    }
    
    public static void setField(final Field field, @Nullable final Object target, @Nullable final Object value) {
        try {
            field.set(target, value);
        }
        catch (final IllegalAccessException ex) {
            handleReflectionException((Exception)ex);
        }
    }
    
    public static void setField(final String field, @Nullable final Object target, @Nullable final Object value) {
        final Field f = findField(target.getClass(), field);
        f.setAccessible(true);
        setField(f, target, value);
        f.setAccessible(false);
    }
    
    @Nullable
    public static Object getField(final Field field, @Nullable final Object target) {
        field.setAccessible(true);
        try {
            return field.get(target);
        }
        catch (final IllegalAccessException ex) {
            handleReflectionException((Exception)ex);
            throw new IllegalStateException("Should never get here");
        }
    }
    
    public static void doWithLocalFields(final Class<?> clazz, final FieldCallback fc) {
        for (final Field field : getDeclaredFields(clazz)) {
            try {
                fc.doWith(field);
            }
            catch (final IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + (Object)ex);
            }
        }
    }
    
    public static void doWithFields(final Class<?> clazz, final FieldCallback fc) {
        doWithFields(clazz, fc, null);
    }
    
    public static void doWithFields(final Class<?> clazz, final FieldCallback fc, @Nullable final FieldFilter ff) {
        Class<?> targetClass = clazz;
        do {
            final Field[] declaredFields;
            final Field[] fields = declaredFields = getDeclaredFields(targetClass);
            for (final Field field : declaredFields) {
                if (ff == null || ff.matches(field)) {
                    try {
                        fc.doWith(field);
                    }
                    catch (final IllegalAccessException ex) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + (Object)ex);
                    }
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }
    
    private static Field[] getDeclaredFields(final Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Field[] result = (Field[])ReflectionUtils.declaredFieldsCache.get((Object)clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                ReflectionUtils.declaredFieldsCache.put((Object)clazz, (Object)((result.length == 0) ? ReflectionUtils.EMPTY_FIELD_ARRAY : result));
            }
            catch (final Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + (Object)clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }
    
    public static void shallowCopyFieldState(final Object src, final Object dest) {
        Assert.notNull(src, "Source for field copy cannot be null");
        Assert.notNull(dest, "Destination for field copy cannot be null");
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        }
        doWithFields(src.getClass(), field -> {
            makeAccessible(field);
            final Object srcValue = field.get(src);
            field.set(dest, srcValue);
        }, ReflectionUtils.COPYABLE_FIELDS);
    }
    
    public static boolean isPublicStaticFinal(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }
    
    public static void makeAccessible(final Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
    
    public static Field getDeclaredFieldByIndex(final Class<?> src, final Class<?> fieldType, final int index) throws NoSuchFieldException {
        return getFieldFromArrayAndIndex(src.getDeclaredFields(), fieldType, index);
    }
    
    private static Field getFieldFromArrayAndIndex(final Field[] fields, final Class<?> fieldType, final int index) throws NoSuchFieldException {
        int counter = 0;
        final String typeName = fieldType.getName();
        for (int i = 0; i < fields.length; ++i) {
            final Field field = fields[i];
            if (field.getType().getName().equals((Object)typeName)) {
                if (counter == index) {
                    return field;
                }
                ++counter;
            }
        }
        throw new NoSuchFieldException("Couldn't find a field of " + typeName + " at index " + index + ".");
    }
    
    public static void clearCache() {
        ReflectionUtils.declaredMethodsCache.clear();
        ReflectionUtils.declaredFieldsCache.clear();
    }
    
    static {
        USER_DECLARED_METHODS = (method -> !method.isBridge() && !method.isSynthetic() && method.getDeclaringClass() != Object.class);
        COPYABLE_FIELDS = (field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()));
        EMPTY_CLASS_ARRAY = new Class[0];
        EMPTY_METHOD_ARRAY = new Method[0];
        EMPTY_FIELD_ARRAY = new Field[0];
        EMPTY_OBJECT_ARRAY = new Object[0];
        declaredMethodsCache = (Map)new ConcurrentReferenceHashMap(256);
        declaredFieldsCache = (Map)new ConcurrentReferenceHashMap(256);
    }
    
    @FunctionalInterface
    public interface MethodFilter
    {
        boolean matches(final Method p0);
        
        default MethodFilter and(final MethodFilter next) {
            Assert.notNull(next, "Next MethodFilter must not be null");
            return method -> this.matches(method) && next.matches(method);
        }
    }
    
    @FunctionalInterface
    public interface FieldFilter
    {
        boolean matches(final Field p0);
        
        default FieldFilter and(final FieldFilter next) {
            Assert.notNull(next, "Next FieldFilter must not be null");
            return field -> this.matches(field) && next.matches(field);
        }
    }
    
    @FunctionalInterface
    public interface FieldCallback
    {
        void doWith(final Field p0) throws IllegalArgumentException, IllegalAccessException;
    }
    
    @FunctionalInterface
    public interface MethodCallback
    {
        void doWith(final Method p0) throws IllegalArgumentException, IllegalAccessException;
    }
}
