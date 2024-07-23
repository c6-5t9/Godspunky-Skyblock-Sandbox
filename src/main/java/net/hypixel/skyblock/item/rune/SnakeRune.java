package net.hypixel.skyblock.item.rune;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Rune;

public class SnakeRune implements Rune
{
    @Override
    public String getDisplayName() {
        return (Object)ChatColor.GREEN + "\u25c6 Snake Rune";
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
        return "2c4a65c689b2d36409100a60c2ab8d3d0a67ce94eea3c1f7ac974fd893568b5d";
    }
}
