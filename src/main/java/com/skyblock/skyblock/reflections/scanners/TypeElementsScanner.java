package com.skyblock.skyblock.reflections.scanners;

import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.AttributeInfo;
import java.util.function.Function;
import com.skyblock.skyblock.reflections.util.JavassistHelper;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;
import java.util.function.Predicate;

public class TypeElementsScanner implements Scanner
{
    private boolean includeFields;
    private boolean includeMethods;
    private boolean includeAnnotations;
    private boolean publicOnly;
    private Predicate<String> resultFilter;
    
    public TypeElementsScanner() {
        this.includeFields = true;
        this.includeMethods = true;
        this.includeAnnotations = true;
        this.publicOnly = true;
        this.resultFilter = (Predicate<String>)(s -> true);
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = (List<Map.Entry<String, String>>)new ArrayList();
        final String className = classFile.getName();
        if (this.resultFilter.test((Object)className) && this.isPublic(classFile)) {
            entries.add((Object)this.entry(className, ""));
            if (this.includeFields) {
                classFile.getFields().forEach(field -> entries.add((Object)this.entry(className, field.getName())));
            }
            if (this.includeMethods) {
                classFile.getMethods().stream().filter(this::isPublic).forEach(method -> entries.add((Object)this.entry(className, method.getName() + "(" + String.join((CharSequence)", ", (Iterable)JavassistHelper.getParameters(method)) + ")")));
            }
            if (this.includeAnnotations) {
                JavassistHelper.getAnnotations((Function<String, AttributeInfo>)classFile::getAttribute).stream().filter((Predicate)this.resultFilter).forEach(annotation -> entries.add((Object)this.entry(className, "@" + annotation)));
            }
        }
        return entries;
    }
    
    private boolean isPublic(final Object object) {
        return !this.publicOnly || JavassistHelper.isPublic(object);
    }
    
    public TypeElementsScanner filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    public TypeElementsScanner includeFields() {
        return this.includeFields(true);
    }
    
    public TypeElementsScanner includeFields(final boolean include) {
        this.includeFields = include;
        return this;
    }
    
    public TypeElementsScanner includeMethods() {
        return this.includeMethods(true);
    }
    
    public TypeElementsScanner includeMethods(final boolean include) {
        this.includeMethods = include;
        return this;
    }
    
    public TypeElementsScanner includeAnnotations() {
        return this.includeAnnotations(true);
    }
    
    public TypeElementsScanner includeAnnotations(final boolean include) {
        this.includeAnnotations = include;
        return this;
    }
    
    public TypeElementsScanner publicOnly(final boolean only) {
        this.publicOnly = only;
        return this;
    }
    
    public TypeElementsScanner publicOnly() {
        return this.publicOnly(true);
    }
}
