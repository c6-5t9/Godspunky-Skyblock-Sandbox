package com.skyblock.skyblock.reflections.scanners;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.AbstractMap;
import javax.annotation.Nullable;
import com.skyblock.skyblock.reflections.vfs.Vfs;
import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;

public interface Scanner
{
    List<Map.Entry<String, String>> scan(final ClassFile p0);
    
    @Nullable
    default List<Map.Entry<String, String>> scan(final Vfs.File file) {
        return null;
    }
    
    default String index() {
        return this.getClass().getSimpleName();
    }
    
    default boolean acceptsInput(final String file) {
        return file.endsWith(".class");
    }
    
    default Map.Entry<String, String> entry(final String key, final String value) {
        return (Map.Entry<String, String>)new AbstractMap.SimpleEntry((Object)key, (Object)value);
    }
    
    default List<Map.Entry<String, String>> entries(final Collection<String> keys, final String value) {
        return (List<Map.Entry<String, String>>)keys.stream().map(key -> this.entry(key, value)).collect(Collectors.toList());
    }
    
    default List<Map.Entry<String, String>> entries(final String key, final String value) {
        return (List<Map.Entry<String, String>>)Collections.singletonList((Object)this.entry(key, value));
    }
    
    default List<Map.Entry<String, String>> entries(final String key, final Collection<String> values) {
        return (List<Map.Entry<String, String>>)values.stream().map(value -> this.entry(key, value)).collect(Collectors.toList());
    }
}
