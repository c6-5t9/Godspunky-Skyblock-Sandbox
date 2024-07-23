package net.hypixel.skyblock.api.disguise.utils;

import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;

public class Assert
{
    public static <T> void notNull(final T obj, final String msg) {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }
    
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isTrue(final boolean expression, final Supplier<String> messageSupplier) {
        if (!expression) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }
    
    public static void notNull(@Nullable final Object object, final Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }
    
    public static <T extends Throwable> void notNull(final Object object, final T e) throws T, Throwable {
        if (object == null) {
            throw e;
        }
    }
    
    public static void notNull(@Nullable final Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }
    
    public static void isTrue(final boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }
    
    public static void isNull(@Nullable final Object object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNull(@Nullable final Object object, final Supplier<String> messageSupplier) {
        if (object != null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }
    
    public static void isNull(@Nullable final Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }
    
    public static void state(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }
    
    public static void state(final boolean expression, final Supplier<String> messageSupplier) {
        if (!expression) {
            throw new IllegalStateException(nullSafeGet(messageSupplier));
        }
    }
    
    public static void state(final boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
    
    @Nullable
    private static String nullSafeGet(@Nullable final Supplier<String> messageSupplier) {
        return (messageSupplier != null) ? ((String)messageSupplier.get()) : null;
    }
    
    public static void allNotNull(final String message, final Object... objects) {
        notNull(objects, "objects should not be null!");
        for (final Object o : objects) {
            notNull(o, message);
        }
    }
    
    public static void isLarger(final double base, final double test) {
        isLarger(base, test, "[Assertion failed] - the value " + test + " has to be larger than " + base);
    }
    
    public static void isLarger(final double base, final double test, final String message) {
        if (test <= base) {
            throw new IllegalStateException(message);
        }
    }
    
    public static void isSmaller(final double base, final double test) {
        isSmaller(base, test, "[Assertion failed] - the value " + test + " has to be smaller than " + base);
    }
    
    public static void isSmaller(final double base, final double test, final String message) {
        if (test >= base) {
            throw new IllegalStateException(message);
        }
    }
}
