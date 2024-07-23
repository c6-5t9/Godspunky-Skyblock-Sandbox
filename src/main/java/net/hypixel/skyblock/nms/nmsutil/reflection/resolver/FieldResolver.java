package net.hypixel.skyblock.nms.nmsutil.reflection.resolver;

import java.lang.reflect.Member;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.WrapperAbstract;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper.FieldWrapper;
import net.hypixel.skyblock.nms.nmsutil.reflection.util.AccessUtil;
import java.lang.reflect.Field;

public class FieldResolver extends MemberResolver<Field>
{
    public FieldResolver(final Class<?> clazz) {
        super(clazz);
    }
    
    public FieldResolver(final String className) throws ClassNotFoundException {
        super(className);
    }
    
    @Override
    public Field resolveIndex(final int index) throws IndexOutOfBoundsException, ReflectiveOperationException {
        return AccessUtil.setAccessible(this.clazz.getDeclaredFields()[index]);
    }
    
    @Override
    public Field resolveIndexSilent(final int index) {
        try {
            return this.resolveIndex(index);
        }
        catch (final IndexOutOfBoundsException | ReflectiveOperationException ex2) {
            return null;
        }
    }
    
    @Override
    public FieldWrapper resolveIndexWrapper(final int index) {
        return new FieldWrapper(this.resolveIndexSilent(index));
    }
    
    public FieldWrapper resolveWrapper(final String... names) {
        return new FieldWrapper(this.resolveSilent(names));
    }
    
    public Field resolveSilent(final String... names) {
        try {
            return this.resolve(names);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Field resolve(final String... names) throws NoSuchFieldException {
        final ResolverQuery.Builder builder = ResolverQuery.builder();
        for (final String name : names) {
            builder.with(name);
        }
        try {
            return super.resolve(builder.build());
        }
        catch (final ReflectiveOperationException e) {
            throw (NoSuchFieldException)e;
        }
    }
    
    public Field resolveSilent(final ResolverQuery... queries) {
        try {
            return this.resolve(queries);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Field resolve(final ResolverQuery... queries) throws NoSuchFieldException {
        try {
            return super.resolve(queries);
        }
        catch (final ReflectiveOperationException e) {
            throw (NoSuchFieldException)e;
        }
    }
    
    @Override
    protected Field resolveObject(final ResolverQuery query) throws ReflectiveOperationException {
        if (query.getTypes() == null || query.getTypes().length == 0) {
            return AccessUtil.setAccessible(this.clazz.getDeclaredField(query.getName()));
        }
        for (final Field field : this.clazz.getDeclaredFields()) {
            if (field.getName().equals((Object)query.getName())) {
                for (final Class type : query.getTypes()) {
                    if (field.getType().equals(type)) {
                        return field;
                    }
                }
            }
        }
        return null;
    }
    
    public Field resolveByFirstType(final Class<?> type) throws ReflectiveOperationException {
        for (final Field field : this.clazz.getDeclaredFields()) {
            if (field.getType().equals(type)) {
                return AccessUtil.setAccessible(field);
            }
        }
        throw new NoSuchFieldException("Could not resolve field of type '" + type.toString() + "' in class " + (Object)this.clazz);
    }
    
    public Field resolveByFirstTypeSilent(final Class<?> type) {
        try {
            return this.resolveByFirstType(type);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Field resolveByLastType(final Class<?> type) throws ReflectiveOperationException {
        Field field = null;
        for (final Field field2 : this.clazz.getDeclaredFields()) {
            if (field2.getType().equals(type)) {
                field = field2;
            }
        }
        if (field == null) {
            throw new NoSuchFieldException("Could not resolve field of type '" + type.toString() + "' in class " + (Object)this.clazz);
        }
        return AccessUtil.setAccessible(field);
    }
    
    public Field resolveByLastTypeSilent(final Class<?> type) {
        try {
            return this.resolveByLastType(type);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    protected NoSuchFieldException notFoundException(final String joinedNames) {
        return new NoSuchFieldException("Could not resolve field for " + joinedNames + " in class " + (Object)this.clazz);
    }
}
