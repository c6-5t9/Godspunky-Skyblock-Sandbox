package net.hypixel.skyblock.item.armor.vanilla.chainmail;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class ChainmailLeggings implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Chainmail Chestplate";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.LEGGINGS;
    }
    
    @Override
    public double getBaseDefense() {
        return 20.0;
    }
}
