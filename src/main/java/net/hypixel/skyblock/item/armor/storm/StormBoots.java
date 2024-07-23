package net.hypixel.skyblock.item.armor.storm;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class StormBoots implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 250.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 145.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 65.0;
    }
    
    @Override
    public int getColor() {
        return 1889508;
    }
    
    @Override
    public String getDisplayName() {
        return "Storm's Boots";
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
        return SpecificItemType.BOOTS;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
