package net.hypixel.skyblock.item.armor.necron;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class NecronLeggings implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseStrength() {
        return 40.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.3;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 10.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 230.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 125.0;
    }
    
    @Override
    public int getColor() {
        return 15162428;
    }
    
    @Override
    public String getDisplayName() {
        return "Necron's Leggings";
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
        return SpecificItemType.LEGGINGS;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
