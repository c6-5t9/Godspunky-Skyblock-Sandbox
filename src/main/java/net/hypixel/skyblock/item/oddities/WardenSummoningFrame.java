package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class WardenSummoningFrame implements MaterialFunction, MaterialStatistics
{
    @Override
    public String getDisplayName() {
        return "Warden Summoning Frame";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.BLOCK;
    }
}
