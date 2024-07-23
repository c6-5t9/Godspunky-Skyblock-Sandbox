package net.hypixel.skyblock.item.armor.eleganttux;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;

public class ElegantTuxChestplate implements LeatherArmorStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Elegant Tuxedo Jacket";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.CHESTPLATE;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 1.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 300.0;
    }
    
    @Override
    public int getColor() {
        return 1644825;
    }
}
