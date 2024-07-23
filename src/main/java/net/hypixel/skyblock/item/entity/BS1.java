package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class BS1 implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "52dd11da04252f76b6934bc26612f54f264f30eed74df89941209e191bebc0a2";
    }
    
    @Override
    public String getDisplayName() {
        return "BZB1";
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
