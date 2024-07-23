package com.skyblock.skyblock.reflections.scanners;

import javassist.ClassPath;
import javassist.LoaderClassPath;
import com.skyblock.skyblock.reflections.util.JavassistHelper;
import javassist.bytecode.MethodInfo;
import javassist.CannotCompileException;
import javassist.expr.FieldAccess;
import javassist.expr.ConstructorCall;
import javassist.expr.MethodCall;
import javassist.NotFoundException;
import javassist.expr.NewExpr;
import javassist.expr.ExprEditor;
import javassist.CtMethod;
import javassist.CtBehavior;
import javassist.CtConstructor;
import javassist.CtClass;
import com.skyblock.skyblock.reflections.ReflectionsException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import javassist.bytecode.ClassFile;
import javax.annotation.Nonnull;
import com.skyblock.skyblock.reflections.util.ClasspathHelper;
import javassist.ClassPool;
import java.util.function.Predicate;

public class MemberUsageScanner implements Scanner
{
    private Predicate<String> resultFilter;
    private final ClassLoader[] classLoaders;
    private volatile ClassPool classPool;
    
    public MemberUsageScanner() {
        this(ClasspathHelper.classLoaders(new ClassLoader[0]));
    }
    
    public MemberUsageScanner(@Nonnull final ClassLoader[] classLoaders) {
        this.resultFilter = (Predicate<String>)(s -> true);
        this.classLoaders = classLoaders;
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = (List<Map.Entry<String, String>>)new ArrayList();
        CtClass ctClass = null;
        try {
            ctClass = this.getClassPool().get(classFile.getName());
            for (final CtBehavior member : ctClass.getDeclaredConstructors()) {
                this.scanMember(member, entries);
            }
            for (final CtBehavior member : ctClass.getDeclaredMethods()) {
                this.scanMember(member, entries);
            }
        }
        catch (final Exception e) {
            throw new ReflectionsException("Could not scan method usage for " + classFile.getName(), (Throwable)e);
        }
        finally {
            if (ctClass != null) {
                ctClass.detach();
            }
        }
        return entries;
    }
    
    public Scanner filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    private void scanMember(final CtBehavior member, final List<Map.Entry<String, String>> entries) throws CannotCompileException {
        final String key = member.getDeclaringClass().getName() + "." + member.getMethodInfo().getName() + "(" + parameterNames(member.getMethodInfo()) + ")";
        member.instrument((ExprEditor)new ExprEditor() {
            public void edit(final NewExpr e) {
                try {
                    MemberUsageScanner.this.add(entries, e.getConstructor().getDeclaringClass().getName() + ".<init>(" + MemberUsageScanner.parameterNames(e.getConstructor().getMethodInfo()) + ")", key + " #" + e.getLineNumber());
                }
                catch (final NotFoundException e2) {
                    throw new ReflectionsException("Could not find new instance usage in " + key, (Throwable)e2);
                }
            }
            
            public void edit(final MethodCall m) {
                try {
                    MemberUsageScanner.this.add(entries, m.getMethod().getDeclaringClass().getName() + "." + m.getMethodName() + "(" + MemberUsageScanner.parameterNames(m.getMethod().getMethodInfo()) + ")", key + " #" + m.getLineNumber());
                }
                catch (final NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + m.getClassName() + " in " + key, (Throwable)e);
                }
            }
            
            public void edit(final ConstructorCall c) {
                try {
                    MemberUsageScanner.this.add(entries, c.getConstructor().getDeclaringClass().getName() + ".<init>(" + MemberUsageScanner.parameterNames(c.getConstructor().getMethodInfo()) + ")", key + " #" + c.getLineNumber());
                }
                catch (final NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + c.getClassName() + " in " + key, (Throwable)e);
                }
            }
            
            public void edit(final FieldAccess f) {
                try {
                    MemberUsageScanner.this.add(entries, f.getField().getDeclaringClass().getName() + "." + f.getFieldName(), key + " #" + f.getLineNumber());
                }
                catch (final NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + f.getFieldName() + " in " + key, (Throwable)e);
                }
            }
        });
    }
    
    private void add(final List<Map.Entry<String, String>> entries, final String key, final String value) {
        if (this.resultFilter.test((Object)key)) {
            entries.add((Object)this.entry(key, value));
        }
    }
    
    public static String parameterNames(final MethodInfo info) {
        return String.join((CharSequence)", ", (Iterable)JavassistHelper.getParameters(info));
    }
    
    private ClassPool getClassPool() {
        if (this.classPool == null) {
            synchronized (this) {
                if (this.classPool == null) {
                    this.classPool = new ClassPool();
                    for (final ClassLoader classLoader : this.classLoaders) {
                        this.classPool.appendClassPath((ClassPath)new LoaderClassPath(classLoader));
                    }
                }
            }
        }
        return this.classPool;
    }
}
