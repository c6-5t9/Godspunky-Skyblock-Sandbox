package net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper;

import java.lang.reflect.Field;

public class FieldWrapper<R> extends WrapperAbstract
{
    private final Field field;
    
    public FieldWrapper(final Field field) {
        this.field = field;
    }
    
    @Override
    public boolean exists() {
        return this.field != null;
    }
    
    public String getName() {
        return this.field.getName();
    }
    
    public R get(final Object object) {
        try {
            return (R)this.field.get(object);
        }
        catch (final Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public R getSilent(final Object object) {
        try {
            return (R)this.field.get(object);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public void set(final Object object, final R value) {
        try {
            this.field.set(object, (Object)value);
        }
        catch (final Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public void setSilent(final Object object, final R value) {
        try {
            this.field.set(object, (Object)value);
        }
        catch (final Exception ex) {}
    }
    
    public Field getField() {
        return this.field;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final FieldWrapper<?> that = (FieldWrapper<?>)object;
        if (this.field != null) {
            return this.field.equals((Object)that.field);
        }
        return that.field == null;
    }
    
    @Override
    public int hashCode() {
        return (this.field != null) ? this.field.hashCode() : 0;
    }
}
