package net.hypixel.skyblock.entity;

import java.util.HashMap;
import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Location;
import java.util.List;
import java.util.UUID;
import java.util.Map;

public final class StaticWardenManager
{
    public static boolean ACTIVE;
    public static Map<UUID, List<Location>> EYES;
    public static SEntity WARDEN;
    
    public static void endFight() {
        if (StaticWardenManager.WARDEN == null) {
            return;
        }
        StaticWardenManager.ACTIVE = false;
        for (final List<Location> locations : StaticWardenManager.EYES.values()) {
            for (final Location location : locations) {
                final Block b = location.getBlock();
                final BlockState s = b.getState();
                s.setRawData((byte)0);
                s.update();
                b.removeMetadata("placer", (Plugin)SkyBlock.getPlugin());
            }
        }
        StaticWardenManager.EYES.clear();
        StaticWardenManager.WARDEN = null;
    }
    
    static {
        StaticWardenManager.ACTIVE = false;
        StaticWardenManager.EYES = (Map<UUID, List<Location>>)new HashMap();
        StaticWardenManager.WARDEN = null;
    }
}
