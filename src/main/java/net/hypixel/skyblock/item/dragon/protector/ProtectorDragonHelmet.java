package net.hypixel.skyblock.item.dragon.protector;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class ProtectorDragonHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseHealth() {
        return 70.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 135.0;
    }
    
    @Override
    public String getURL() {
        return "f37a596cdc4b11a9948ffa38c2aa3c6942ef449eb0a3982281d3a5b5a14ef6ae";
    }
    
    @Override
    public String getDisplayName() {
        return "Protector Dragon Helmet";
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
