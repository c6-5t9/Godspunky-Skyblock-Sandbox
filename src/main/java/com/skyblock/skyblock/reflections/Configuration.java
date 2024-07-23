package com.skyblock.skyblock.reflections;

import java.util.function.Predicate;
import java.net.URL;
import com.skyblock.skyblock.reflections.scanners.Scanner;
import java.util.Set;

public interface Configuration
{
    Set<Scanner> getScanners();
    
    Set<URL> getUrls();
    
    Predicate<String> getInputsFilter();
    
    boolean isParallel();
    
    ClassLoader[] getClassLoaders();
    
    boolean shouldExpandSuperTypes();
}
