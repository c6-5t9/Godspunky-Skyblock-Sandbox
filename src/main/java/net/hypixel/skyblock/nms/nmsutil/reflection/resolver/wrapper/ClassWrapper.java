package net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper;

import java.util.Objects;

public class ClassWrapper<R> extends WrapperAbstract
{
    private final Class<R> clazz;
    
    public ClassWrapper(final Class<R> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public boolean exists() {
        return null != this.clazz;
    }
    
    public Class<R> getClazz() {
        return this.clazz;
    }
    
    public String getName() {
        return this.clazz.getName();
    }
    
    public R newInstance() {
        try {
            return this.clazz.newInstance();
        }
        catch (final Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public R newInstanceSilent() {
        try {
            return this.clazz.newInstance();
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object || this.getClass() != object.getClass()) {
            return false;
        }
        final ClassWrapper<?> that = (ClassWrapper<?>)object;
        return Objects.equals((Object)this.clazz, (Object)that.clazz);
    }
    
    @Override
    public int hashCode() {
        return (null != this.clazz) ? this.clazz.hashCode() : 0;
    }
}
