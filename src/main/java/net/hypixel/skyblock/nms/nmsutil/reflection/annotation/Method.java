package net.hypixel.skyblock.nms.nmsutil.reflection.annotation;

import net.hypixel.skyblock.nms.nmsutil.reflection.minecraft.Minecraft;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    String className();
    
    String[] value();
    
    Minecraft.Version[] versions() default {};
    
    boolean ignoreExceptions() default true;
}
