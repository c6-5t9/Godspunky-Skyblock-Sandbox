package net.hypixel.skyblock.item.dragon.unstable;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class UnstableDragonChestplate implements MaterialFunction, LeatherArmorStatistics
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
        return 120.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 160.0;
    }
    
    @Override
    public int getColor() {
        return 11670243;
    }
    
    @Override
    public String getDisplayName() {
        return "Unstable Dragon Chestplate";
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
