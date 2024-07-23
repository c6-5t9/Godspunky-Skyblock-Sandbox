package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class HotPotatoBook implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Hot Potato Book";
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
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public String getLore() {
        return "Combine this Book in an Anvil with a weapon or armor piece to gain a small but permanent stat boost!";
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
