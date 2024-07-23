package net.hypixel.skyblock.item.dragon.young;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class YoungDragonHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseSpeed() {
        return 0.2;
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
    public String getURL() {
        return "5c486af3b882766e82a0bc1665ff02eb6e873b6e0d771f3adabc759b720226a";
    }
    
    @Override
    public String getDisplayName() {
        return "Young Dragon Helmet";
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
