package net.hypixel.skyblock.api.disguise.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@FunctionalInterface
public interface ClassSource
{
    default ClassSource fromClassLoader() {
        return fromClassLoader(ClassSource.class.getClassLoader());
    }
    
    default ClassSource fromPackage(final String packageName) {
        return fromClassLoader().usingPackage(packageName);
    }
    
    default ClassSource fromClassLoader(final ClassLoader loader) {
        return canonicalName -> {
            try {
                return (Optional<Class<?>>)Optional.of((Object)loader.loadClass(canonicalName));
            }
            catch (final ClassNotFoundException ignored) {
                return (Optional<Class<?>>)Optional.empty();
            }
        };
    }
    
    default ClassSource fromMap(final Map<String, Class<?>> map) {
        return canonicalName -> Optional.ofNullable(map.get((Object)canonicalName));
    }
    
    default ClassSource empty() {
        return fromMap((Map<String, Class<?>>)Collections.emptyMap());
    }
    
    default String append(final String a, final String b) {
        final boolean left = a.endsWith(".");
        final boolean right = b.endsWith(".");
        if (left && right) {
            return a.substring(0, a.length() - 1) + b;
        }
        if (left != right) {
            return a + b;
        }
        return a + "." + b;
    }
    
    default ClassSource usingPackage(final String packageName) {
        return canonicalName -> this.loadClass(append(packageName, canonicalName));
    }
    
    Optional<Class<?>> loadClass(final String p0);
}
