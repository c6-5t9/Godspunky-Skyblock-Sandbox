package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class EtherwarpMerger implements MaterialStatistics, MaterialFunction, SkullStatistics
{
    @Override
    public String getDisplayName() {
        return "Etherwarp Merger";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
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
    public String getLore() {
        return Sputnik.trans("&8Crafted by the &8Etherchemist &8and dropped &8by the Enderseraph.");
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getURL() {
        return "3e5314f4919691ccbf807743dae47ae45ac2e3ff08f79eecdd452fe602eff7f6";
    }
}
