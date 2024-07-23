package net.hypixel.skyblock.item.accessory;

import java.util.Arrays;
import org.bukkit.ChatColor;
import java.util.List;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;

public class CrackedPiggyBank implements AccessoryStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "14a7aac08593a1a0bc6666fe0aeedfb195d413fc9cf87c73f4a8c04da6418857";
    }
    
    @Override
    public String getDisplayName() {
        return "Cracked Piggy Bank";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ACCESSORY;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.ACCESSORY;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Saves " + (Object)ChatColor.RED + "75%" + (Object)ChatColor.GRAY + " of your coins on death.", "Only when in player inventory.", (Object)ChatColor.RED + "Very fragile!", "", (Object)ChatColor.DARK_GRAY + "Triggers when losing 20k+ coins." });
    }
}