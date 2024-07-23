package net.hypixel.skyblock.item.mining;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class Mitril implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Mithril";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("&7&o\"The man called it \"true-silver\" while the Dwarves, who loved it above all other things, had their own, secret name for it.\"");
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isEnchanted() {
        return false;
    }
    
    @Override
    public boolean isStackable() {
        return true;
    }
}
