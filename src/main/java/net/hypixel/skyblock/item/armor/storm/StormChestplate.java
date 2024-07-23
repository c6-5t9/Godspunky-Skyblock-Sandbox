package net.hypixel.skyblock.item.armor.storm;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class StormChestplate implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 250.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 260.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 120.0;
    }
    
    @Override
    public int getColor() {
        return 1545156;
    }
    
    @Override
    public String getDisplayName() {
        return "Storm's Chestplate";
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
