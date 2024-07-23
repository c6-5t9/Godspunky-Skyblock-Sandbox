package net.hypixel.skyblock.item.revenant;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class ScytheBlade implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Scythe Blade";
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
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
