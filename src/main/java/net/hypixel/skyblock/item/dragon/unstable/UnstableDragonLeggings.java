package net.hypixel.skyblock.item.dragon.unstable;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class UnstableDragonLeggings implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseCritChance() {
        return 0.05;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.15;
    }
    
    @Override
    public double getBaseHealth() {
        return 100.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 140.0;
    }
    
    @Override
    public int getColor() {
        return 11670243;
    }
    
    @Override
    public String getDisplayName() {
        return "Unstable Dragon Leggings";
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
