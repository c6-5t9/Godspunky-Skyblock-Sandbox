package net.hypixel.skyblock.item.armor;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class DonatorHelmet implements ToolStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Donator's Space Helmet";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }
    
    @Override
    public double getBaseMagicFind() {
        return 0.01;
    }
    
    @Override
    public double getBaseDefense() {
        return 690.0;
    }
    
    @Override
    public double getBaseStrength() {
        return 3000.0;
    }
    
    @Override
    public double getBaseFerocity() {
        return 100.0;
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("&7Given to people who donated or help us out in the development of SkyBlock, thank you very much! &c\u2764");
    }
}
