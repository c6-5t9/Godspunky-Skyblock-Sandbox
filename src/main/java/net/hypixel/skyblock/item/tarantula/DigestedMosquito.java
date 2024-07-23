package net.hypixel.skyblock.item.tarantula;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class DigestedMosquito implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Digested Mosquito";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
