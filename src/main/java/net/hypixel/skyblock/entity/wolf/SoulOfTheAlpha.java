package net.hypixel.skyblock.entity.wolf;

import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.ChatColor;

public class SoulOfTheAlpha extends BaseWolf
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.DARK_AQUA + "Soul of the Alpha";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 31150.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 1282.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SMaterial.JUNGLE_WOOD, EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.WEAK_WOLF_CATALYST, EntityDropType.VERY_RARE, 0.005) });
    }
    
    @Override
    public double getXPDropped() {
        return 50.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
    
    @Override
    public int mobLevel() {
        return 55;
    }
}
