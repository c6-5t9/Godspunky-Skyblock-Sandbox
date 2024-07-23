package net.hypixel.skyblock.npc.impl;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;

public interface NPCParameters
{
    public static final String CLICK = "&e&lCLICK";
    
    String id();
    
    default String name() {
        return null;
    }
    
    default NPCType type() {
        return NPCType.PLAYER;
    }
    
    default String[] messages() {
        return null;
    }
    
    default String[] holograms() {
        return (String[])((this.name() != null) ? new String[] { this.name(), "&e&lCLICK" } : null);
    }
    
    default NPCSkin skin() {
        return null;
    }
    
    String world();
    
    double x();
    
    double y();
    
    double z();
    
    default float yaw() {
        return 0.0f;
    }
    
    default float pitch() {
        return 0.0f;
    }
    
    default boolean looking() {
        return true;
    }
    
    void onInteract(final Player p0, final SkyBlockNPC p1);
}
