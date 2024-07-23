package net.hypixel.skyblock.item.dragon.superior;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class SuperiorDragonHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseStrength() {
        return 10.0;
    }
    
    @Override
    public double getBaseCritChance() {
        return 0.02;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.08;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 25.0;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.03;
    }
    
    @Override
    public double getBaseHealth() {
        return 90.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 130.0;
    }
    
    @Override
    public String getURL() {
        return "7558efbe66976099cfd62760d9e05170d2bb8f51e68829ab8a051c48cbc415cb";
    }
    
    @Override
    public String getDisplayName() {
        return "Superior Dragon Helmet";
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
