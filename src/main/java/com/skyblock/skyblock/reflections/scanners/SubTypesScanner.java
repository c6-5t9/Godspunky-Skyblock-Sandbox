package com.skyblock.skyblock.reflections.scanners;

import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;
import java.util.function.Predicate;

@Deprecated
public class SubTypesScanner extends AbstractScanner
{
    @Deprecated
    public SubTypesScanner() {
        super(Scanners.SubTypes);
    }
    
    @Deprecated
    public SubTypesScanner(final boolean excludeObjectClass) {
        super(excludeObjectClass ? Scanners.SubTypes : Scanners.SubTypes.filterResultsBy((Predicate<String>)(s -> true)));
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile cls) {
        return this.scanner.scan(cls);
    }
}
