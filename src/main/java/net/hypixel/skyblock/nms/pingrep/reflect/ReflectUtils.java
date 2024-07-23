package net.hypixel.skyblock.nms.pingrep.reflect;

import java.lang.reflect.Field;

public class ReflectUtils
{
    public static Field getFirstFieldByType(final Class<?> clazz, final Class<?> type) {
        for (final Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType() == type) {
                return field;
            }
        }
        return null;
    }
}
