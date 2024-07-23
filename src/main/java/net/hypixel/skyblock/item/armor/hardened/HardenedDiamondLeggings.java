package net.hypixel.skyblock.item.armor.hardened;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class HardenedDiamondLeggings implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Hardened Diamond Leggings";
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
        return SpecificItemType.LEGGINGS;
    }
    
    @Override
    public double getBaseDefense() {
        return 95.0;
    }
}
