package net.hypixel.skyblock.item.armor.necron;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class NecronChestplate implements MaterialFunction, LeatherArmorStatistics
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
        return 260.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 140.0;
    }
    
    @Override
    public int getColor() {
        return 15155516;
    }
    
    @Override
    public String getDisplayName() {
        return "Necron's Chestplate";
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
