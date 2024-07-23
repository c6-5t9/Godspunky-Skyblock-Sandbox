package net.hypixel.skyblock.item.dragon.wise;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class WiseDragonChestplate implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 75.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 120.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 160.0;
    }
    
    @Override
    public int getColor() {
        return 2748649;
    }
    
    @Override
    public String getDisplayName() {
        return "Wise Dragon Chestplate";
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
