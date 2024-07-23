package net.hypixel.skyblock.api.disguise.utils;

import org.bukkit.Bukkit;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import java.lang.reflect.Method;

public class AttributeUtils
{
    private static Method getAttributeMethod;
    
    public static double getAttribute(final LivingEntity entity, final Attribute attribute) {
        if (AttributeUtils.getAttributeMethod != null) {
            try {
                final Object attributeInstance = AttributeUtils.getAttributeMethod.invoke((Object)entity, new Object[] { attribute });
                if (attributeInstance != null) {
                    final Method getValueMethod = attributeInstance.getClass().getMethod("getValue", (Class<?>[])new Class[0]);
                    return (double)getValueMethod.invoke(attributeInstance, new Object[0]);
                }
            }
            catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }
    
    static {
        try {
            final Class<?> craftLivingEntityClass = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".entity.CraftLivingEntity");
            (AttributeUtils.getAttributeMethod = craftLivingEntityClass.getDeclaredMethod("getAttribute", Attribute.class)).setAccessible(true);
        }
        catch (final ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
