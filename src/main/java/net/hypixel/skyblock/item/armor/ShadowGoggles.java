package net.hypixel.skyblock.item.armor;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class ShadowGoggles implements MaterialFunction, SkullStatistics, ToolStatistics
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
        return "c465a847e114ef62e7833cbfb2fe5de5764bab5f10af125fd2d316238268279f";
    }
    
    @Override
    public String getDisplayName() {
        return "Shadow Goggles";
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
