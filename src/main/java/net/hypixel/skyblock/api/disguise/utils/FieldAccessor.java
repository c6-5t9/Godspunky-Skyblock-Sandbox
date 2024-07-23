package net.hypixel.skyblock.api.disguise.utils;

import java.lang.reflect.Field;

public class FieldAccessor<T, K>
{
    private Field field;
    
    public FieldAccessor(final Class<T> instanceClass, final Class<K> fieldClass) {
        this.field = null;
        for (final Field f : instanceClass.getFields()) {
            if (f.getDeclaringClass().equals(fieldClass)) {
                this.field = f;
                break;
            }
        }
        if (this.field == null) {
            throw new FiledNotFoundException("No filed with this type " + (Object)fieldClass + " in class " + (Object)instanceClass);
        }
    }
    
    public Object get(final Object object) {
        try {
            this.field.setAccessible(true);
            return this.field.get(object);
        }
        catch (final Exception e) {
            throw new RuntimeException();
        }
    }
    
    public static class FiledNotFoundException extends RuntimeException
    {
        public FiledNotFoundException(final String s) {
            super(s);
        }
    }
}
