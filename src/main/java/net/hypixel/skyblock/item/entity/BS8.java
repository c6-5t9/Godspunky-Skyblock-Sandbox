package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class BS8 implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "4f85522ee815d110587fffc74113f419d929598e2463b8ce9d39caa9fb6ff5ab";
    }
    
    @Override
    public String getDisplayName() {
        return "BZB8";
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
