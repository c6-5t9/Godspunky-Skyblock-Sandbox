package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class GameBreaker implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Game Breaker";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
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
        return "This item was given to a player who reported a game breaking exploit. What a guy!";
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
