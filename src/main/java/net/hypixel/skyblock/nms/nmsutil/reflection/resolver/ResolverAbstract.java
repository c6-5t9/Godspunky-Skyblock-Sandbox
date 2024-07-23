package net.hypixel.skyblock.nms.nmsutil.reflection.resolver;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public abstract class ResolverAbstract<T>
{
    protected final Map<ResolverQuery, T> resolvedObjects;
    
    public ResolverAbstract() {
        this.resolvedObjects = (Map<ResolverQuery, T>)new ConcurrentHashMap();
    }
    
    protected T resolveSilent(final ResolverQuery... queries) {
        try {
            return this.resolve(queries);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    protected T resolve(final ResolverQuery... queries) throws ReflectiveOperationException {
        if (queries == null || queries.length <= 0) {
            throw new IllegalArgumentException("Given possibilities are empty");
        }
        final int length = queries.length;
        int i = 0;
        while (i < length) {
            final ResolverQuery query = queries[i];
            if (this.resolvedObjects.containsKey((Object)query)) {
                return (T)this.resolvedObjects.get((Object)query);
            }
            try {
                final T resolved = this.resolveObject(query);
                this.resolvedObjects.put((Object)query, (Object)resolved);
                return resolved;
            }
            catch (final ReflectiveOperationException e) {
                ++i;
                continue;
            }
            break;
        }
        throw this.notFoundException(Arrays.asList((Object[])queries).toString());
    }
    
    protected abstract T resolveObject(final ResolverQuery p0) throws ReflectiveOperationException;
    
    protected ReflectiveOperationException notFoundException(final String joinedNames) {
        return new ReflectiveOperationException("Objects could not be resolved: " + joinedNames);
    }
}
