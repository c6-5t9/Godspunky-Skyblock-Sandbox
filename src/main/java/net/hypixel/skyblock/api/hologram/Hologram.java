package net.hypixel.skyblock.api.hologram;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.hypixel.skyblock.api.reflection.ReflectionsUtils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import java.util.HashSet;
import org.bukkit.entity.ArmorStand;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Location;
import java.util.UUID;
import java.util.Set;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

public class Hologram extends EntityArmorStand
{
    private final Set<UUID> viewers;
    public Location location;
    
    public Hologram(final Location location) {
        super((World)((CraftWorld)location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setInvisible(true);
        this.setCustomNameVisible(true);
        this.setGravity(false);
        ((ArmorStand)this.getBukkitEntity()).setMarker(true);
        this.setSmall(false);
        this.setBasePlate(false);
        this.location = location;
        this.viewers = (Set<UUID>)new HashSet();
        HologramManager.register(this);
    }
    
    public void setText(final String text) {
        if (this.getCustomName().equals((Object)text)) {
            return;
        }
        this.setCustomName(text);
        this.setCustomNameVisible(true);
        this.update();
    }
    
    public void mount(final Entity attachEntity) {
        this.mount(((CraftEntity)attachEntity).getHandle());
        this.update();
    }
    
    public void update() {
        final PacketPlayOutEntityMetadata updatePacket = new PacketPlayOutEntityMetadata(this.getId(), this.getDataWatcher(), true);
        this.sendPacketToViewers((Packet<?>)updatePacket);
    }
    
    public void show(final Player player) {
        if (this.viewers.contains((Object)player.getUniqueId())) {
            return;
        }
        final PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving((EntityLiving)this);
        ReflectionsUtils.setValue(spawnPacket, "b", 30);
        this.sendPacket(player, (Packet<?>)spawnPacket);
        this.viewers.add((Object)player.getUniqueId());
    }
    
    public boolean isShown(final Player player) {
        return this.viewers.contains((Object)player.getUniqueId());
    }
    
    public void hide(final Player player) {
        if (!this.viewers.contains((Object)player.getUniqueId())) {
            return;
        }
        final PacketPlayOutEntityDestroy entityDestroyPacket = new PacketPlayOutEntityDestroy(new int[] { this.getId() });
        this.sendPacket(player, (Packet<?>)entityDestroyPacket);
        this.viewers.remove((Object)player.getUniqueId());
    }
    
    public void teleport(final Location location) {
        this.location = location;
        if (location.getX() == this.locX && location.getY() == this.locY && location.getZ() == this.locZ) {
            return;
        }
        final PacketPlayOutEntityTeleport entityTeleportPacket = new PacketPlayOutEntityTeleport(this.getId(), MathHelper.floor(location.getX() * 32.0), MathHelper.floor(location.getY() * 32.0), MathHelper.floor(location.getZ() * 32.0), (byte)(location.getYaw() * 256.0f / 360.0f), (byte)(location.getPitch() * 256.0f / 360.0f), this.onGround);
        this.sendPacketToViewers((Packet<?>)entityTeleportPacket);
    }
    
    public boolean inRangeOf(final Player player) {
        if (player == null) {
            return false;
        }
        if (!player.getWorld().equals(this.location.getWorld())) {
            return false;
        }
        final double hideDistance = 20.0;
        final double distanceSquared = player.getLocation().distanceSquared(this.location);
        final double bukkitRange = Bukkit.getViewDistance() << 4;
        return distanceSquared <= SUtil.square(hideDistance) && distanceSquared <= SUtil.square(bukkitRange);
    }
    
    public void remove() {
        final PacketPlayOutEntityDestroy entityDestroyPacket = new PacketPlayOutEntityDestroy(new int[] { this.getId() });
        this.sendPacketToViewers((Packet<?>)entityDestroyPacket);
        HologramManager.remove(this);
    }
    
    public void sendPacketToViewers(final Packet<?> packet) {
        if (this.viewers.isEmpty()) {
            return;
        }
        this.viewers.forEach(uuid -> {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.sendPacket(player, packet);
            }
        });
    }
    
    public void sendPacket(final Player player, final Packet<?> packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
    }
}
