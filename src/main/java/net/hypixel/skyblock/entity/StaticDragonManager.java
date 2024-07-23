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

public final class StaticDragonManager
{
    public static boolean ACTIVE;
    public static Map<UUID, List<Location>> EYES;
    public static SEntity DRAGON;
    
    public static void endFight() {
        if (StaticDragonManager.DRAGON == null) {
            return;
        }
        StaticDragonManager.ACTIVE = false;
        for (final List<Location> locations : StaticDragonManager.EYES.values()) {
            for (final Location location : locations) {
                final Block b = location.getBlock();
                final BlockState s = b.getState();
                s.setRawData((byte)0);
                s.update();
                b.removeMetadata("placer", (Plugin)SkyBlock.getPlugin());
            }
        }
        StaticDragonManager.EYES.clear();
        StaticDragonManager.DRAGON = null;
    }
    
    static {
        StaticDragonManager.ACTIVE = false;
        StaticDragonManager.EYES = (Map<UUID, List<Location>>)new HashMap();
        StaticDragonManager.DRAGON = null;
    }
}
