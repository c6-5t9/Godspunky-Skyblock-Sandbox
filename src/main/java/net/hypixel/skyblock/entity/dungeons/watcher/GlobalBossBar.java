package net.hypixel.skyblock.entity.dungeons.watcher;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import java.util.Map;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.ArrayList;
import org.bukkit.World;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityWither;
import org.bukkit.entity.Player;
import java.util.HashMap;
import org.bukkit.scheduler.BukkitRunnable;

public class GlobalBossBar extends BukkitRunnable
{
    private String title;
    private final HashMap<Player, EntityWither> withers;
    public List<Player> players;
    private final World w;
    
    public GlobalBossBar(final String title, final World w) {
        this.withers = (HashMap<Player, EntityWither>)new HashMap();
        this.players = (List<Player>)new ArrayList();
        this.title = title;
        this.w = w;
        this.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void addPlayer(final Player p) {
        this.players.add((Object)p);
        final EntityWither wither = new EntityWither((net.minecraft.server.v1_8_R3.World)((CraftWorld)p.getWorld()).getHandle());
        final Location l = this.getWitherLocation(p.getLocation());
        wither.setCustomName(this.title);
        wither.setInvisible(true);
        wither.r(877);
        wither.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)wither);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
        this.withers.put((Object)p, (Object)wither);
    }
    
    public void removePlayer(final Player p) {
        this.players.remove((Object)p);
        final EntityWither wither = (EntityWither)this.withers.remove((Object)p);
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { wither.getId() });
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    public void setTitle(final String title) {
        this.title = title;
        for (final Map.Entry<Player, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)entry.getValue();
            wither.setCustomName(title);
            final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            ((CraftPlayer)entry.getKey()).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public void setProgress(final double progress) {
        for (final Map.Entry<Player, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)entry.getValue();
            wither.setHealth((float)(progress * wither.getMaxHealth()));
            final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            ((CraftPlayer)entry.getKey()).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public Location getWitherLocation(final Location l) {
        return l.add(l.getDirection().multiply(20)).add(0.0, 1.5, 0.0);
    }
    
    public void run() {
        for (final Map.Entry<Player, EntityWither> en : this.withers.entrySet()) {
            final EntityWither wither = (EntityWither)en.getValue();
            final Location l = this.getWitherLocation(((Player)en.getKey()).getLocation());
            wither.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
            final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport((Entity)wither);
            ((CraftPlayer)en.getKey()).getHandle().playerConnection.sendPacket((Packet)packet);
        }
        for (final Player p : this.w.getPlayers()) {
            if (!this.players.contains((Object)p)) {
                this.addPlayer(p);
            }
            if (p.getWorld() != this.w || !p.isOnline()) {
                this.removePlayer(p);
            }
        }
    }
}
