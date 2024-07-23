package net.hypixel.skyblock.item.rune;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Rune;

public class SpiritRune implements Rune
{
    @Override
    public String getDisplayName() {
        return (Object)ChatColor.AQUA + "\u25c6 Spirit Rune";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.COSMETIC;
    }
    
    @Override
    public String getURL() {
        return "c738b8af8d7ce1a26dc6d40180b3589403e11ef36a66d7c4590037732829542e";
    }
}
