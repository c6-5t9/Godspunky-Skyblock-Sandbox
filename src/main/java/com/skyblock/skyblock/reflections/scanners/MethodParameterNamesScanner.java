package com.skyblock.skyblock.reflections.scanners;

import javassist.bytecode.CodeAttribute;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.lang.reflect.Modifier;
import javassist.bytecode.LocalVariableAttribute;
import java.util.Iterator;
import com.skyblock.skyblock.reflections.util.JavassistHelper;
import javassist.bytecode.MethodInfo;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;

public class MethodParameterNamesScanner implements Scanner
{
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = (List<Map.Entry<String, String>>)new ArrayList();
        for (final MethodInfo method : classFile.getMethods()) {
            final String key = JavassistHelper.methodName(classFile, method);
            final String value = this.getString(method);
            if (!value.isEmpty()) {
                entries.add((Object)this.entry(key, value));
            }
        }
        return entries;
    }
    
    private String getString(final MethodInfo method) {
        final CodeAttribute codeAttribute = method.getCodeAttribute();
        final LocalVariableAttribute table = (codeAttribute != null) ? ((LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable")) : null;
        final int length = JavassistHelper.getParameters(method).size();
        if (length > 0) {
            final int shift = Modifier.isStatic(method.getAccessFlags()) ? 0 : 1;
            return (String)IntStream.range(shift, length + shift).mapToObj(i -> method.getConstPool().getUtf8Info(table.nameIndex(i))).filter(name -> !name.startsWith("this$")).collect(Collectors.joining((CharSequence)", "));
        }
        return "";
    }
}
