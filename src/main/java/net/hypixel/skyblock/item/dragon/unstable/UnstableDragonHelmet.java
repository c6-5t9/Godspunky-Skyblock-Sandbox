package net.hypixel.skyblock.item.dragon.unstable;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class UnstableDragonHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
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
        return 70.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 110.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 25.0;
    }
    
    @Override
    public String getURL() {
        return "2922b5f8d554ca923f96832a5a4e9169bc2cdb360a2b06ebec09b6a6af4618e3";
    }
    
    @Override
    public String getDisplayName() {
        return "Unstable Dragon Helmet";
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
        return SpecificItemType.HELMET;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
