package net.hypixel.skyblock.item.axe.vanilla;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class GoldenAxe implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Golden Axe";
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
        return SpecificItemType.AXE;
    }
}
