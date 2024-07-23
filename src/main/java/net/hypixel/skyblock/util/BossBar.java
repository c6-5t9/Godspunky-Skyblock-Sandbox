package net.hypixel.skyblock.util;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import java.util.Map;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.minecraft.server.v1_8_R3.EntityWither;
import java.util.UUID;
import java.util.HashMap;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBar extends BukkitRunnable
{
    private String title;
    private final HashMap<UUID, EntityWither> withers;
    
    public BossBar(final String title) {
        this.withers = (HashMap<UUID, EntityWither>)new HashMap();
        this.title = title;
        this.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void addPlayer(final UUID playerId) {
        final Player player = Bukkit.getPlayer(playerId);
        if (player != null && player.isOnline()) {
            final EntityWither wither = new EntityWither((World)((CraftWorld)player.getWorld()).getHandle());
            final Location location = this.getWitherLocation(player.getLocation());
            wither.setCustomName(this.title);
            wither.setInvisible(true);
            wither.r(877);
            wither.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
            final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)wither);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
            this.withers.put((Object)playerId, (Object)wither);
        }
    }
    
    public void removePlayer(final UUID playerId) {
        final EntityWither wither = (EntityWither)this.withers.remove((Object)playerId);
        if (wither != null) {
            final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { wither.getId() });
            final Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.isOnline()) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }
    }
    
    public void setTitle(final String title) {
        this.title = title;
        for (final Map.Entry<UUID, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)entry.getValue();
            wither.setCustomName(title);
            final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            final Player player = Bukkit.getPlayer((UUID)entry.getKey());
            if (player != null && player.isOnline()) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }
    }
    
    public void setProgress(final double progress) {
        for (final Map.Entry<UUID, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)entry.getValue();
            wither.setHealth((float)(progress * wither.getMaxHealth()));
            final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            final Player player = Bukkit.getPlayer((UUID)entry.getKey());
            if (player != null && player.isOnline()) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }
    }
    
    public Location getWitherLocation(final Location location) {
        return location.add(location.getDirection().multiply(20));
    }
    
    public void run() {
        for (final Map.Entry<UUID, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)entry.getValue();
            final Player player = Bukkit.getPlayer((UUID)entry.getKey());
            if (player != null && player.isOnline()) {
                final Location location = this.getWitherLocation(player.getLocation());
                wither.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
                final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport((Entity)wither);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }
    }
}
