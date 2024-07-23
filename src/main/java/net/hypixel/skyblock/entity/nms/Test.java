package net.hypixel.skyblock.entity.nms;

import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SkeletonStatistics;

public class Test implements SkeletonStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.LIGHT_PURPLE + "\u2620 " + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "Terminator Golem";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 9.5E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 50000.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return null;
    }
    
    @Override
    public double getXPDropped() {
        return 100000.0;
    }
}
