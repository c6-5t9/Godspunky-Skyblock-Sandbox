package net.hypixel.skyblock.item.dragon.old;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class OldDragonHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseHealth() {
        return 110.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 90.0;
    }
    
    @Override
    public String getURL() {
        return "59e9e5600410c1d0254474a81fecfb3885c1cf6f504190d856f0ec7c9f055c42";
    }
    
    @Override
    public String getDisplayName() {
        return "Old Dragon Helmet";
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
