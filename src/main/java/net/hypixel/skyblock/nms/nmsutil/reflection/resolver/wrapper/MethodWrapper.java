package net.hypixel.skyblock.nms.nmsutil.reflection.resolver.wrapper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;
import java.lang.reflect.Method;

public class MethodWrapper<R> extends WrapperAbstract
{
    private final Method method;
    
    public MethodWrapper(final Method method) {
        this.method = method;
    }
    
    @Override
    public boolean exists() {
        return null != this.method;
    }
    
    public String getName() {
        return this.method.getName();
    }
    
    public R invoke(final Object object, final Object... args) {
        try {
            return (R)this.method.invoke(object, args);
        }
        catch (final Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public R invokeSilent(final Object object, final Object... args) {
        try {
            return (R)this.method.invoke(object, args);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object || this.getClass() != object.getClass()) {
            return false;
        }
        final MethodWrapper<?> that = (MethodWrapper<?>)object;
        return Objects.equals((Object)this.method, (Object)that.method);
    }
    
    @Override
    public int hashCode() {
        return (null != this.method) ? this.method.hashCode() : 0;
    }
    
    public static String getMethodSignature(final Method method, final boolean fullClassNames) {
        return MethodSignature.of(method, fullClassNames).getSignature();
    }
    
    public static String getMethodSignature(final Method method) {
        return getMethodSignature(method, false);
    }
    
    public static class MethodSignature
    {
        static final Pattern SIGNATURE_STRING_PATTERN;
        private final String returnType;
        private final Pattern returnTypePattern;
        private final String name;
        private final Pattern namePattern;
        private final String[] parameterTypes;
        private final String signature;
        
        public MethodSignature(final String returnType, final String name, final String[] parameterTypes) {
            this.returnType = returnType;
            this.returnTypePattern = Pattern.compile(returnType.replace((CharSequence)"?", (CharSequence)"\\w").replace((CharSequence)"*", (CharSequence)"\\w*"));
            this.name = name;
            this.namePattern = Pattern.compile(name.replace((CharSequence)"?", (CharSequence)"\\w").replace((CharSequence)"*", (CharSequence)"\\w*"));
            this.parameterTypes = parameterTypes;
            final StringBuilder builder = new StringBuilder();
            builder.append(returnType).append(" ").append(name).append("(");
            boolean first = true;
            for (final String parameterType : parameterTypes) {
                if (!first) {
                    builder.append(",");
                }
                builder.append(parameterType);
                first = false;
            }
            this.signature = builder.append(")").toString();
        }
        
        public static MethodSignature of(final Method method, final boolean fullClassNames) {
            final Class<?> returnType = method.getReturnType();
            final Class<?>[] parameterTypes = method.getParameterTypes();
            String returnTypeString;
            if (returnType.isPrimitive()) {
                returnTypeString = returnType.toString();
            }
            else {
                returnTypeString = (fullClassNames ? returnType.getName() : returnType.getSimpleName());
            }
            final String methodName = method.getName();
            final String[] parameterTypeStrings = new String[parameterTypes.length];
            for (int i = 0; i < parameterTypeStrings.length; ++i) {
                if (parameterTypes[i].isPrimitive()) {
                    parameterTypeStrings[i] = parameterTypes[i].toString();
                }
                else {
                    parameterTypeStrings[i] = (fullClassNames ? parameterTypes[i].getName() : parameterTypes[i].getSimpleName());
                }
            }
            return new MethodSignature(returnTypeString, methodName, parameterTypeStrings);
        }
        
        public static MethodSignature fromString(final String signatureString) {
            if (null == signatureString) {
                return null;
            }
            final Matcher matcher = MethodSignature.SIGNATURE_STRING_PATTERN.matcher((CharSequence)signatureString);
            if (!matcher.find()) {
                throw new IllegalArgumentException("invalid signature");
            }
            if (3 != matcher.groupCount()) {
                throw new IllegalArgumentException("invalid signature");
            }
            return new MethodSignature(matcher.group(1), matcher.group(2), matcher.group(3).split(","));
        }
        
        public String getReturnType() {
            return this.returnType;
        }
        
        public boolean isReturnTypeWildcard() {
            return "?".equals((Object)this.returnType) || "*".equals((Object)this.returnType);
        }
        
        public String getName() {
            return this.name;
        }
        
        public boolean isNameWildcard() {
            return "?".equals((Object)this.name) || "*".equals((Object)this.name);
        }
        
        public String[] getParameterTypes() {
            return this.parameterTypes;
        }
        
        public String getParameterType(final int index) throws IndexOutOfBoundsException {
            return this.parameterTypes[index];
        }
        
        public boolean isParameterWildcard(final int index) throws IndexOutOfBoundsException {
            return "?".equals((Object)this.getParameterType(index)) || "*".equals((Object)this.getParameterType(index));
        }
        
        public String getSignature() {
            return this.signature;
        }
        
        public boolean matches(final MethodSignature other) {
            if (null == other) {
                return false;
            }
            if (!this.returnTypePattern.matcher((CharSequence)other.returnType).matches()) {
                return false;
            }
            if (!this.namePattern.matcher((CharSequence)other.name).matches()) {
                return false;
            }
            if (this.parameterTypes.length != other.parameterTypes.length) {
                return false;
            }
            for (int i = 0; i < this.parameterTypes.length; ++i) {
                if (!Pattern.compile(this.getParameterType(i).replace((CharSequence)"?", (CharSequence)"\\w").replace((CharSequence)"*", (CharSequence)"\\w*")).matcher((CharSequence)other.getParameterType(i)).matches()) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (null == o || this.getClass() != o.getClass()) {
                return false;
            }
            final MethodSignature signature1 = (MethodSignature)o;
            return this.returnType.equals((Object)signature1.returnType) && this.name.equals((Object)signature1.name) && Arrays.equals((Object[])this.parameterTypes, (Object[])signature1.parameterTypes) && this.signature.equals((Object)signature1.signature);
        }
        
        @Override
        public int hashCode() {
            int result = this.returnType.hashCode();
            result = 31 * result + this.name.hashCode();
            result = 31 * result + Arrays.hashCode((Object[])this.parameterTypes);
            result = 31 * result + this.signature.hashCode();
            return result;
        }
        
        @Override
        public String toString() {
            return this.getSignature();
        }
        
        static {
            SIGNATURE_STRING_PATTERN = Pattern.compile("(.+) (.*)\\((.*)\\)");
        }
    }
}
