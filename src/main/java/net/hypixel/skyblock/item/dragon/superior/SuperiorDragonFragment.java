package net.hypixel.skyblock.item.dragon.superior;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class SuperiorDragonFragment implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "6f89b150be9c4c5249f355f68ea0c4391300a9be1f260d750fc35a1817ad796e";
    }
    
    @Override
    public String getDisplayName() {
        return "Superior Dragon Fragment";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
}
