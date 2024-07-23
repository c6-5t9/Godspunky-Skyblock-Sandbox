package net.hypixel.skyblock.api.disguise.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Map;

final class CachedPackage
{
    private final String packageName;
    private final ClassSource source;
    private final Map<String, Optional<Class<?>>> cache;
    
    CachedPackage(final String packageName, final ClassSource source) {
        this.source = source;
        this.packageName = packageName;
        this.cache = (Map<String, Optional<Class<?>>>)new ConcurrentHashMap();
    }
    
    public static String combine(final String packageName, final String className) {
        if (packageName == null || packageName.isEmpty()) {
            return className;
        }
        return packageName + "." + className;
    }
    
    public void setPackageClass(final String className, final Class<?> clazz) {
        if (clazz != null) {
            this.cache.put((Object)className, (Object)Optional.of((Object)clazz));
        }
        else {
            this.cache.remove((Object)className);
        }
    }
    
    private Optional<Class<?>> resolveClass(final String className) {
        return this.source.loadClass(combine(this.packageName, className));
    }
    
    public Optional<Class<?>> getPackageClass(final String className, final String... aliases) {
        return (Optional<Class<?>>)this.cache.computeIfAbsent((Object)className, x -> {
            Optional<Class<?>> clazz = this.resolveClass(className);
            if (clazz.isPresent()) {
                return clazz;
            }
            for (final String alias : aliases) {
                clazz = this.resolveClass(alias);
                if (clazz.isPresent()) {
                    return clazz;
                }
            }
            return Optional.empty();
        });
    }
}
