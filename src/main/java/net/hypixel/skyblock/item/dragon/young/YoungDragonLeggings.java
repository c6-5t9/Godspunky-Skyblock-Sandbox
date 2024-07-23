package net.hypixel.skyblock.item.dragon.young;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class YoungDragonLeggings implements MaterialFunction, LeatherArmorStatistics
{
    @Override
    public double getBaseSpeed() {
        return 0.2;
    }
    
    @Override
    public double getBaseHealth() {
        return 110.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 140.0;
    }
    
    @Override
    public int getColor() {
        return 14542064;
    }
    
    @Override
    public String getDisplayName() {
        return "Young Dragon Leggings";
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
        return SpecificItemType.LEGGINGS;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
