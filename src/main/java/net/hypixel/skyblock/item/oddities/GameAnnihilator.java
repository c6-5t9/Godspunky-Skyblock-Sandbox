package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class GameAnnihilator implements MaterialStatistics, MaterialFunction, SkullStatistics
{
    @Override
    public String getDisplayName() {
        return "Game Annihilator";
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
        return "This item was given to a player who reported enough bugs to make the SkyBlock developers cry.";
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getURL() {
        return "796a4daab9ee48cba2d99b16070516284a65afb0b26e873a54c8fde603c0da76";
    }
}
