package net.hypixel.skyblock.item.shovel.vanilla;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class StoneShovel implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Stone Shovel";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public int getBaseDamage() {
        return 20;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.TOOL;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SHOVEL;
    }
}
