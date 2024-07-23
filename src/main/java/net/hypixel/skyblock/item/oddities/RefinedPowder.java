package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class RefinedPowder implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Refined Powder";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
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
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("Smell like... fish?");
    }
}
