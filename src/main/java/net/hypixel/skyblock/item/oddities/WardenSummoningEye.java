package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class WardenSummoningEye implements SkullStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Summoning Eye";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public String getLore() {
        return "Use this at the " + (Object)ChatColor.DARK_PURPLE + "Ender Altar" + (Object)ChatColor.GRAY + " in the " + (Object)ChatColor.DARK_PURPLE + "Void Sepulture" + (Object)ChatColor.GRAY + " to summon Voidlings Warden!";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getURL() {
        return "12c318157f5c9daf8a7677a3669f9798940aff014a674ee0af2a574cbb21b8c3";
    }
}
