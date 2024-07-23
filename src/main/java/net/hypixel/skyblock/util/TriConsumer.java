package net.hypixel.skyblock.util;

public interface TriConsumer<T, U, V>
{
    void accept(final T p0, final U p1, final V p2);
    
    default TriConsumer<T, U, V> andThen(final TriConsumer<? super T, ? super U, ? super V> after) {
        return null;
    }
}
