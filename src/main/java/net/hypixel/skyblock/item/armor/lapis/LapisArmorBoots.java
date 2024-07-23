package net.hypixel.skyblock.item.armor.lapis;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;

public class LapisArmorBoots implements LeatherArmorStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Lapis Armor Boots";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOOTS;
    }
    
    @Override
    public double getBaseDefense() {
        return 20.0;
    }
    
    @Override
    public int getColor() {
        return 255;
    }
}
