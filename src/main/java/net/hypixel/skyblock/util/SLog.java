package net.hypixel.skyblock.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SLog
{
    private static final Logger LOGGER;
    private static final String PREFIX;
    
    private static void log(final Object o, final Level l) {
        SLog.LOGGER.log(l, getPrefix() + o);
    }
    
    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', "&7[&aHypixel&bSkyblock&dCore&7] &f");
    }
    
    public static void sendMessage(final String message) {
        Bukkit.getConsoleSender().sendMessage(getPrefix() + CC.translate(message));
    }
    
    public static void info(final Object o) {
        sendMessage((String)o);
    }
    
    public static void warn(final Object o) {
        log(o, Level.WARNING);
    }
    
    public static void severe(final Object o) {
        log(o, Level.SEVERE);
    }
    
    static {
        PREFIX = Sputnik.trans("&7[&aHypixel&bSkyblock&dCore&7] &f");
        LOGGER = Logger.getLogger("Minecraft");
    }
}
