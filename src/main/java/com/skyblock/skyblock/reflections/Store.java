package com.skyblock.skyblock.reflections;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Store extends HashMap<String, Map<String, Set<String>>>
{
    public Store() {
    }
    
    public Store(final Map<String, Map<String, Set<String>>> storeMap) {
        super((Map)storeMap);
    }
}
