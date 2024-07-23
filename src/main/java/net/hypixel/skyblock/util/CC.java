package net.hypixel.skyblock.util;

import org.bukkit.ChatColor;

public class CC
{
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static String getTimeDifferenceAndColor(final long start, final long end) {
        return (Object)getColorBasedOnSize(end - start, 20, 5000, 10000) + "" + (end - start) + "ms";
    }
    
    public static ChatColor getColorBasedOnSize(final long num, final int low, final int med, final int high) {
        if (num <= low) {
            return ChatColor.GREEN;
        }
        if (num <= med) {
            return ChatColor.YELLOW;
        }
        if (num <= high) {
            return ChatColor.RED;
        }
        return ChatColor.DARK_RED;
    }
}
