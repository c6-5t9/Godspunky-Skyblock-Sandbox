package net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper;

import java.util.Objects;
import java.lang.reflect.Constructor;

public class ConstructorWrapper<R> extends WrapperAbstract
{
    private final Constructor<R> constructor;
    
    public ConstructorWrapper(final Constructor<R> constructor) {
        this.constructor = constructor;
    }
    
    @Override
    public boolean exists() {
        return null != this.constructor;
    }
    
    public R newInstance(final Object... args) {
        try {
            return (R)this.constructor.newInstance(args);
        }
        catch (final Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public R newInstanceSilent(final Object... args) {
        try {
            return (R)this.constructor.newInstance(args);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Class<?>[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }
    
    public Constructor<R> getConstructor() {
        return this.constructor;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object || this.getClass() != object.getClass()) {
            return false;
        }
        final ConstructorWrapper<?> that = (ConstructorWrapper<?>)object;
        return Objects.equals((Object)this.constructor, (Object)that.constructor);
    }
    
    @Override
    public int hashCode() {
        return (null != this.constructor) ? this.constructor.hashCode() : 0;
    }
}
