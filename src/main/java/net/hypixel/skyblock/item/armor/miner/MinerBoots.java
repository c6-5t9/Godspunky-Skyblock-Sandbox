package net.hypixel.skyblock.item.armor.miner;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;

public class MinerBoots implements LeatherArmorStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Miner Boots";
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
        return SpecificItemType.BOOTS;
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
