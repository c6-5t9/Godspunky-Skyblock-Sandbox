package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class AtonedHorrorHead implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "2b0aad2f4f06d2e0ca83c5462460065fc4a0d3093ab67c564a5ae5d89dbf02b4";
    }
    
    @Override
    public String getDisplayName() {
        return "Atoned Horror Head";
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
