package net.hypixel.skyblock.item.rune;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Rune;

public class PestilenceRune implements Rune
{
    @Override
    public String getDisplayName() {
        return (Object)ChatColor.DARK_GREEN + "\u25c6 Pestilence Rune";
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
        return "a8c4811395fbf7f620f05cc3175cef1515aaf775ba04a01045027f0693a90147";
    }
}
