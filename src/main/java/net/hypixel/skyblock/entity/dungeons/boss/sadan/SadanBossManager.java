package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import java.util.Iterator;
import org.bukkit.World;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Location;
import net.hypixel.skyblock.api.worldmanager.SkyBlockWorldManager;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.util.ArrayList;

public class SadanBossManager
{
    public static void startFloor(final ArrayList<UUID> plist) {
        final String worldname = "f6_" + SadanFunction.generateRandom();
        final World source = Bukkit.getWorld("f6");
        final SkyBlockWorldManager skyBlockWorldManager = new SkyBlockWorldManager(source);
        skyBlockWorldManager.cloneWorld(worldname);
        final World world = Bukkit.getWorld(worldname);
        for (final UUID tm : plist) {
            Bukkit.getPlayer(tm).teleport(new Location(world, 213.0, 71.0, 221.0, 0.0f, 0.0f));
        }
        Sputnik.RunThisSession.put((Object)Bukkit.getServer(), (Object)(Sputnik.rf_() + 1));
        SUtil.delay(() -> r(plist, world), 1L);
        SUtil.delay(() -> new SEntity(new Location(world, 183.0, 100.0, 251.0), SEntityType.SADAN, new Object[0]), 1L);
    }
    
    public static void r(final ArrayList<UUID> plist, final World world) {
        for (final UUID tm : plist) {
            Bukkit.getPlayer(tm).teleport(new Location(world, 191.5, 69.0, 199.5, 0.0f, 0.0f));
        }
    }
    
    public static void endFloor(final World w) {
        if (w.getName().toLowerCase().startsWith("f6") && !w.getName().equalsIgnoreCase("f6")) {
            for (final Entity e : w.getEntities()) {
                if (e instanceof Player) {
                    continue;
                }
                e.remove();
            }
            new SkyBlockWorldManager(w).delete();
            SLog.severe("[DUNGEON BOSSROOM] Deleted " + w.getName() + " and cleaned the memory !");
        }
    }
}
