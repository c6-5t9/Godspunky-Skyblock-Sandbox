package net.hypixel.skyblock.item.armor.miner;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;

public class MinerChestplate implements LeatherArmorStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Miner Chestplate";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
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
    public double getBaseDefense() {
        return 5.0;
    }
    
    @Override
    public int getColor() {
        return 255;
    }
}
