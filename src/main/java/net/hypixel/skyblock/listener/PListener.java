package net.hypixel.skyblock.listener;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.Listener;

public class PListener implements Listener
{
    private static int amount;
    protected SkyBlock plugin;
    
    protected PListener() {
        this.plugin = SkyBlock.getPlugin();
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.plugin);
        ++PListener.amount;
    }
    
    public static int getAmount() {
        return PListener.amount;
    }
    
    static {
        PListener.amount = 0;
    }
}
