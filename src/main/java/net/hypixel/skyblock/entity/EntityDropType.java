package net.hypixel.skyblock.entity;

import org.bukkit.ChatColor;

public enum EntityDropType
{
    GUARANTEED(ChatColor.GREEN), 
    COMMON(ChatColor.GREEN), 
    OCCASIONAL(ChatColor.BLUE), 
    RARE(ChatColor.GOLD), 
    VERY_RARE(ChatColor.AQUA), 
    EXTRAORDINARILY_RARE(ChatColor.DARK_PURPLE), 
    CRAZY_RARE(ChatColor.LIGHT_PURPLE), 
    INSANE_RARE(ChatColor.RED);
    
    private final ChatColor color;
    
    private EntityDropType(final ChatColor color) {
        this.color = color;
    }
    
    public String getDisplay() {
        return "" + (Object)this.color + (Object)ChatColor.BOLD + this.name().replaceAll("_", " ");
    }
    
    public ChatColor getColor() {
        return this.color;
    }
}
