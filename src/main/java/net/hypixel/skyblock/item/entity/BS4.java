package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class BS4 implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "76387fc246893d92a6dd9ea1b52dcd581e991eeee2e263b27fff1bcf1b154eb7";
    }
    
    @Override
    public String getDisplayName() {
        return "BZB4";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
}
