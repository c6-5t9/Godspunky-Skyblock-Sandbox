package net.hypixel.skyblock.entity.wolf;

import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public class PackSpirit extends BaseWolf
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.AQUA + "Pack Spirit";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 6000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 270.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SMaterial.BONE, EntityDropType.COMMON, 0.5), new EntityDrop(SMaterial.OAK_WOOD, EntityDropType.COMMON, 0.1), new EntityDrop(SMaterial.BIRCH_WOOD, EntityDropType.COMMON, 0.1) });
    }
    
    @Override
    public double getXPDropped() {
        return 15.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
    
    @Override
    public int mobLevel() {
        return 30;
    }
}
