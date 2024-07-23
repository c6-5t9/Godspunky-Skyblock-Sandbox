package net.hypixel.skyblock.item.rune;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Rune;

public class CoutureRune implements Rune
{
    @Override
    public String getDisplayName() {
        return (Object)ChatColor.AQUA + "\u25c6 Couture Rune";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
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
        return "734fb3203233efbae82628bd4fca7348cd071e5b7b52407f1d1d2794e31799ff";
    }
}
