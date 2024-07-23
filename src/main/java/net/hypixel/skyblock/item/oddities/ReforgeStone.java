package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Reforgable;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class ReforgeStone implements SkullStatistics, MaterialFunction, Reforgable
{
    @Override
    public String getDisplayName() {
        return "Reforge Stone";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getURL() {
        return "bdbe5a690633758683d3f1785541e6de481b775b3b1b552040da713b7738817c";
    }
    
    @Override
    public boolean displayRarity() {
        return false;
    }
}
