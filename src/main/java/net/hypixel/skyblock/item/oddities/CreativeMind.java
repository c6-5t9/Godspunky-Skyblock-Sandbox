package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class CreativeMind implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Creative Mind";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public String getLore() {
        return "Original, visionary, inventive and innovative! I would even add ingenious, clever! A masterpiece!";
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
