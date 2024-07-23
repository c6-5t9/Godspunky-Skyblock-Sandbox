package net.hypixel.skyblock.item.armor.sorrow;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class SorrowArmorChestplate implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Sorrow Chestplate";
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
        return SpecificItemType.CHESTPLATE;
    }
    
    @Override
    public double getBaseMagicFind() {
        return 0.05;
    }
    
    @Override
    public double getBaseDefense() {
        return 200.0;
    }
}
