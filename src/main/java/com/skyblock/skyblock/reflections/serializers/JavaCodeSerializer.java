package com.skyblock.skyblock.reflections.serializers;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import com.skyblock.skyblock.reflections.scanners.TypeElementsScanner;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.charset.Charset;
import java.util.Date;
import java.io.File;
import com.skyblock.skyblock.reflections.Reflections;
import java.io.InputStream;
import java.util.List;

public class JavaCodeSerializer implements Serializer
{
    private static final String pathSeparator = "_";
    private static final String doubleSeparator = "__";
    private static final String dotSeparator = ".";
    private static final String arrayDescriptor = "$$";
    private static final String tokenSeparator = "_";
    private StringBuilder sb;
    private List<String> prevPaths;
    private int indent;
    
    @Override
    public Reflections read(final InputStream inputStream) {
        throw new UnsupportedOperationException("read is not implemented on JavaCodeSerializer");
    }
    
    @Override
    public File save(final Reflections reflections, String name) {
        if (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1);
        }
        final String filename = name.replace('.', '/').concat(".java");
        final File file = Serializer.prepareFile(filename);
        final int lastDot = name.lastIndexOf(46);
        String packageName;
        String className;
        if (lastDot == -1) {
            packageName = "";
            className = name.substring(name.lastIndexOf(47) + 1);
        }
        else {
            packageName = name.substring(name.lastIndexOf(47) + 1, lastDot);
            className = name.substring(lastDot + 1);
        }
        try {
            this.sb = new StringBuilder();
            this.sb.append("//generated using Reflections JavaCodeSerializer").append(" [").append((Object)new Date()).append("]").append("\n");
            if (packageName.length() != 0) {
                this.sb.append("package ").append(packageName).append(";\n");
                this.sb.append("\n");
            }
            this.sb.append("public interface ").append(className).append(" {\n\n");
            this.toString(reflections);
            this.sb.append("}\n");
            Files.write(new File(filename).toPath(), this.sb.toString().getBytes(Charset.defaultCharset()), new OpenOption[0]);
        }
        catch (final IOException e) {
            throw new RuntimeException();
        }
        return file;
    }
    
    private void toString(final Reflections reflections) {
        final Map<String, Set<String>> map = (Map<String, Set<String>>)reflections.getStore().get((Object)TypeElementsScanner.class.getSimpleName());
        this.prevPaths = (List<String>)new ArrayList();
        this.indent = 1;
        map.keySet().stream().sorted().forEach(fqn -> {
            final List<String> typePaths = (List<String>)Arrays.asList((Object[])fqn.split("\\."));
            final String className = fqn.substring(fqn.lastIndexOf(46) + 1);
            final List<String> fields = (List<String>)new ArrayList();
            final List<String> methods = (List<String>)new ArrayList();
            final List<String> annotations = (List<String>)new ArrayList();
            ((Set)map.get((Object)fqn)).stream().sorted().forEach(element -> {
                if (element.startsWith("@")) {
                    annotations.add((Object)element.substring(1));
                }
                else if (element.contains((CharSequence)"(")) {
                    if (!element.startsWith("<")) {
                        final int i = element.indexOf(40);
                        final String name = element.substring(0, i);
                        final String params = element.substring(i + 1, element.indexOf(")"));
                        final String paramsDescriptor = (params.length() != 0) ? ("_" + params.replace((CharSequence)".", (CharSequence)"_").replace((CharSequence)", ", (CharSequence)"__").replace((CharSequence)"[]", (CharSequence)"$$")) : "";
                        methods.add((Object)(methods.contains((Object)name) ? (name + paramsDescriptor) : name));
                    }
                }
                else if (!element.isEmpty()) {
                    fields.add((Object)element);
                }
            });
            final int i = this.indentOpen(typePaths, this.prevPaths);
            this.addPackages(typePaths, i);
            this.addClass(typePaths, className);
            this.addFields(typePaths, fields);
            this.addMethods(typePaths, fields, methods);
            this.addAnnotations(typePaths, annotations);
            this.prevPaths = typePaths;
        });
        this.indentClose(this.prevPaths);
    }
    
    protected int indentOpen(final List<String> typePaths, final List<String> prevPaths) {
        int i;
        for (i = 0; i < Math.min(typePaths.size(), prevPaths.size()) && ((String)typePaths.get(i)).equals(prevPaths.get(i)); ++i) {}
        for (int j = prevPaths.size(); j > i; --j) {
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
        return i;
    }
    
    protected void indentClose(final List<String> prevPaths) {
        for (int j = prevPaths.size(); j >= 1; --j) {
            this.sb.append(this.indent(j)).append("}\n");
        }
    }
    
    protected void addPackages(final List<String> typePaths, final int i) {
        for (int j = i; j < typePaths.size() - 1; ++j) {
            this.sb.append(this.indent(this.indent++)).append("interface ").append(this.uniqueName((String)typePaths.get(j), typePaths, j)).append(" {\n");
        }
    }
    
    protected void addClass(final List<String> typePaths, final String className) {
        this.sb.append(this.indent(this.indent++)).append("interface ").append(this.uniqueName(className, typePaths, typePaths.size() - 1)).append(" {\n");
    }
    
    protected void addFields(final List<String> typePaths, final List<String> fields) {
        if (!fields.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface fields {\n");
            for (final String field : fields) {
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(field, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    protected void addMethods(final List<String> typePaths, final List<String> fields, final List<String> methods) {
        if (!methods.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface methods {\n");
            for (final String method : methods) {
                final String methodName = this.uniqueName(method, fields);
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(methodName, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    protected void addAnnotations(final List<String> typePaths, final List<String> annotations) {
        if (!annotations.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface annotations {\n");
            for (final String annotation : annotations) {
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(annotation, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    private String uniqueName(final String candidate, final List<String> prev, final int offset) {
        final String normalized = this.normalize(candidate);
        for (int i = 0; i < offset; ++i) {
            if (normalized.equals(prev.get(i))) {
                return this.uniqueName(normalized + "_", prev, offset);
            }
        }
        return normalized;
    }
    
    private String normalize(final String candidate) {
        return candidate.replace((CharSequence)".", (CharSequence)"_");
    }
    
    private String uniqueName(final String candidate, final List<String> prev) {
        return this.uniqueName(candidate, prev, prev.size());
    }
    
    private String indent(final int times) {
        return (String)IntStream.range(0, times).mapToObj(i -> "  ").collect(Collectors.joining());
    }
}
