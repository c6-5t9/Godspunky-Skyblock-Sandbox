package net.hypixel.skyblock.item.tarantula;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class SpiderCatalyst implements SkullStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Spider Catalyst";
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
    public String getURL() {
        return "983b30e9d135b05190eea2c3ac61e2ab55a2d81e1a58dbb26983a14082664";
    }
}
