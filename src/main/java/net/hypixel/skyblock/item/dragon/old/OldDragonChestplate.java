package net.hypixel.skyblock.item.dragon.old;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class OldDragonChestplate implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseHealth() {
        return 160.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 150.0;
    }
    
    @Override
    public int getColor() {
        return 15787690;
    }
    
    @Override
    public String getDisplayName() {
        return "Old Dragon Chestplate";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.CHESTPLATE;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
