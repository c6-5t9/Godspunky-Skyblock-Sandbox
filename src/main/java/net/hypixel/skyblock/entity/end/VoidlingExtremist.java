package net.hypixel.skyblock.entity.end;

import java.util.Arrays;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public class VoidlingExtremist extends BaseEnderman
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.LIGHT_PURPLE + "Voidling Extremist";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 8000000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 13500.0;
    }
    
    @Override
    public double getXPDropped() {
        return 500.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.ENDER_PEARL, SUtil.random(32, 64)), EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.ENCHANTED_ENDER_PEARL, EntityDropType.RARE, 0.05), new EntityDrop(SMaterial.SUMMONING_EYE, EntityDropType.EXTRAORDINARILY_RARE, 0.01) });
    }
    
    @Override
    public int mobLevel() {
        return 100;
    }
}
