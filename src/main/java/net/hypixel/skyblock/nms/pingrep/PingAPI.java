package net.hypixel.skyblock.nms.pingrep;

import net.hypixel.skyblock.util.SLog;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;

public class PingAPI
{
    private static List<PingListener> listeners;
    
    public static void register() {
        try {
            PingAPI.listeners = (List<PingListener>)new ArrayList();
            final Class<?> injector = PingInjector.class;
            Bukkit.getPluginManager().registerEvents((Listener)injector.newInstance(), (Plugin)SkyBlock.getPlugin());
            SLog.info("Successfully hooked into " + Bukkit.getServer().getName());
        }
        catch (final SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            SLog.severe("Non compatible server version!");
            Bukkit.getPluginManager().disablePlugin((Plugin)SkyBlock.getPlugin());
        }
    }
    
    public static void registerListener(final PingListener listener) {
        PingAPI.listeners.add((Object)listener);
    }
    
    public static List<PingListener> getListeners() {
        return PingAPI.listeners;
    }
}
