package net.hypixel.skyblock.item.armor;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class DarkGoggles implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 150.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 0.0;
    }
    
    @Override
    public String getURL() {
        return "29319525825f1f30727eb940d3a06426bc4cec07fbd80af5cd146e3eb3879f68";
    }
    
    @Override
    public String getDisplayName() {
        return "Dark Goggles";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
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
