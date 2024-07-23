package net.hypixel.skyblock.item.armor;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class WitherGoggles implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 200.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 0.0;
    }
    
    @Override
    public String getURL() {
        return "37ceb8f0758e2d8ac49de6f977603c7bfc23fd82a8574810a45f5e97c6436d79";
    }
    
    @Override
    public String getDisplayName() {
        return "Wither Goggles";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }
}
