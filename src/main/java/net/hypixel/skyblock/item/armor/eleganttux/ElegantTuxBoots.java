package net.hypixel.skyblock.item.armor.eleganttux;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;

public class ElegantTuxBoots implements LeatherArmorStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Elegant Tuxedo Oxfords";
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
        return SpecificItemType.BOOTS;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.5;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 100.0;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.1;
    }
    
    @Override
    public int getColor() {
        return 1644825;
    }
}
