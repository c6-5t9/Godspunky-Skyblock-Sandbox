package net.hypixel.skyblock.item.pickaxe.vanilla;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class IronPickaxe implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Iron Pickaxe";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public int getBaseDamage() {
        return 25;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.TOOL;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.PICKAXE;
    }
}
