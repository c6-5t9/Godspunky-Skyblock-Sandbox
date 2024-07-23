package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class ReaperFalchion implements ToolStatistics, MaterialFunction
{
    @Override
    public int getBaseDamage() {
        return 120;
    }
    
    @Override
    public double getBaseStrength() {
        return 100.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 200.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Reaper Falchion";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public String getLore() {
        return "Heal " + (Object)ChatColor.RED + "10" + (Object)ChatColor.RED + "\u2764" + (Object)ChatColor.GRAY + " per hit. Deal " + (Object)ChatColor.GREEN + "+200% " + (Object)ChatColor.GRAY + "damage to Zombies. Receive " + (Object)ChatColor.GREEN + "20% " + (Object)ChatColor.GRAY + "less damage from Zombies when held.";
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
