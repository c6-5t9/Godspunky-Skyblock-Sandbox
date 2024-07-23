package net.hypixel.skyblock.command;

import net.hypixel.skyblock.features.ranks.PlayerRank;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters {
    String description() default "";
    
    String usage() default "/<command>";
    
    String aliases() default "";
    
    PlayerRank permission();
}
