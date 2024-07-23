package net.hypixel.skyblock.nms.nmsutil.reflection.resolver;

import java.lang.reflect.Member;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.WrapperAbstract;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.ConstructorWrapper;
import net.hypixel.skyblock.nms.nmsutil.reflection.util.AccessUtil;
import java.lang.reflect.Constructor;

public class ConstructorResolver extends MemberResolver<Constructor>
{
    public ConstructorResolver(final Class<?> clazz) {
        super(clazz);
    }
    
    public ConstructorResolver(final String className) throws ClassNotFoundException {
        super(className);
    }
    
    @Override
    public Constructor resolveIndex(final int index) throws IndexOutOfBoundsException, ReflectiveOperationException {
        return AccessUtil.setAccessible(this.clazz.getDeclaredConstructors()[index]);
    }
    
    @Override
    public Constructor resolveIndexSilent(final int index) {
        try {
            return this.resolveIndex(index);
        }
        catch (final IndexOutOfBoundsException | ReflectiveOperationException ex2) {
            return null;
        }
    }
    
    @Override
    public ConstructorWrapper resolveIndexWrapper(final int index) {
        return new ConstructorWrapper((Constructor<R>)this.resolveIndexSilent(index));
    }
    
    public ConstructorWrapper resolveWrapper(final Class<?>[]... types) {
        return new ConstructorWrapper((Constructor<R>)this.resolveSilent(types));
    }
    
    public Constructor resolveSilent(final Class<?>[]... types) {
        try {
            return this.resolve(types);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Constructor resolve(final Class<?>[]... types) throws NoSuchMethodException {
        final ResolverQuery.Builder builder = ResolverQuery.builder();
        for (final Class<?>[] type : types) {
            builder.with(type);
        }
        try {
            return super.resolve(builder.build());
        }
        catch (final ReflectiveOperationException e) {
            throw (NoSuchMethodException)e;
        }
    }
    
    @Override
    protected Constructor resolveObject(final ResolverQuery query) throws ReflectiveOperationException {
        return AccessUtil.setAccessible(this.clazz.getDeclaredConstructor(query.getTypes()));
    }
    
    public Constructor resolveFirstConstructor() throws ReflectiveOperationException {
        final Constructor<?>[] declaredConstructors = this.clazz.getDeclaredConstructors();
        final int length = declaredConstructors.length;
        final int n = 0;
        if (0 < length) {
            final Constructor constructor = declaredConstructors[0];
            return AccessUtil.setAccessible(constructor);
        }
        return null;
    }
    
    public Constructor resolveFirstConstructorSilent() {
        try {
            return this.resolveFirstConstructor();
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Constructor resolveLastConstructor() throws ReflectiveOperationException {
        Constructor constructor = null;
        final Constructor<?>[] declaredConstructors = this.clazz.getDeclaredConstructors();
        for (int length = declaredConstructors.length, i = 0; i < length; ++i) {
            constructor = declaredConstructors[i];
        }
        if (constructor != null) {
            return AccessUtil.setAccessible(constructor);
        }
        return null;
    }
    
    public Constructor resolveLastConstructorSilent() {
        try {
            return this.resolveLastConstructor();
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    protected NoSuchMethodException notFoundException(final String joinedNames) {
        return new NoSuchMethodException("Could not resolve constructor for " + joinedNames + " in class " + (Object)this.clazz);
    }
}
