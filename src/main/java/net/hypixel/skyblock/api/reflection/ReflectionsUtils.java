package net.hypixel.skyblock.api.reflection;

import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import java.util.Map;
import java.lang.reflect.Field;

public class ReflectionsUtils
{
    private static final Field[] EMPTY_FIELD_ARRAY;
    private static final Map<Class<?>, Field[]> declaredFieldsCache;
    
    public static void setValue(final Object obj, final String name, final Object value) {
        try {
            final Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }
        catch (final Exception ex) {}
    }
    
    public static Object getValue(final Object instance, final String name) {
        Object result = null;
        try {
            final Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
    
    public static void setField(final String name, final Object obj, final Object value) {
        setValue(obj, name, value);
    }
    
    public static Object getField(final Field field, final Object target) {
        field.setAccessible(true);
        try {
            return field.get(target);
        }
        catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Should never get here");
        }
    }
    
    public static Object getField(final String f, final Object target) {
        final Field field = findField(target.getClass(), f, null);
        field.setAccessible(true);
        try {
            return field.get(target);
        }
        catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Should never get here");
        }
    }
    
    public static Field findField(final Class<?> clazz, @Nullable final String name, @Nullable final Class<?> type) {
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
    
    private static Field[] getDeclaredFields(final Class<?> clazz) {
        Field[] result = (Field[])ReflectionsUtils.declaredFieldsCache.get((Object)clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                ReflectionsUtils.declaredFieldsCache.put((Object)clazz, (Object)((result.length == 0) ? ReflectionsUtils.EMPTY_FIELD_ARRAY : result));
            }
            catch (final Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + (Object)clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }
    
    static {
        EMPTY_FIELD_ARRAY = new Field[0];
        declaredFieldsCache = (Map)new ConcurrentHashMap(256);
    }
}
