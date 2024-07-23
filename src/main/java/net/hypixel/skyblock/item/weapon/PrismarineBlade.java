package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class PrismarineBlade implements ToolStatistics, MaterialFunction
{
    @Override
    public int getBaseDamage() {
        return 50;
    }
    
    @Override
    public double getBaseStrength() {
        return 25.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Prismarine Blade";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public String getLore() {
        return "Deals " + (Object)ChatColor.GREEN + "+200% " + (Object)ChatColor.GRAY + "damage while in water.";
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
