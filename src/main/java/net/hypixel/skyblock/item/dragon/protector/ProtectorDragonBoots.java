package net.hypixel.skyblock.item.dragon.protector;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class ProtectorDragonBoots implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseHealth() {
        return 60.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 115.0;
    }
    
    @Override
    public int getColor() {
        return 10065803;
    }
    
    @Override
    public String getDisplayName() {
        return "Protector Dragon Boots";
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
    public String getLore() {
        return null;
    }
}
