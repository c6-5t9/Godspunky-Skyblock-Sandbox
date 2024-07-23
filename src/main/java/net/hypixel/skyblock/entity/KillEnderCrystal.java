package net.hypixel.skyblock.entity;

import java.util.Iterator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.World;

public final class KillEnderCrystal
{
    public static void killEC(final World world) {
        for (final Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.ENDER_CRYSTAL) {
                entity.remove();
            }
        }
    }
}
