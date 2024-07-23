package net.hypixel.skyblock.npc.impl.type.impl;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.hypixel.skyblock.api.reflection.ReflectionsUtils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Location;
import net.hypixel.skyblock.npc.impl.type.NPCBase;
import net.minecraft.server.v1_8_R3.EntityVillager;

public class NPCVillagerImpl extends EntityVillager implements NPCBase
{
    private final Location location;
    
    public NPCVillagerImpl(final Location location) {
        super((World)((CraftWorld)location.getWorld()).getHandle());
        this.location = location;
        this.setPosition(location.getX(), location.getY(), location.getZ());
        ((CraftVillager)this.getBukkitEntity()).getHandle().setInvisible(false);
    }
    
    public void hide(final Player player) {
        final PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(new int[] { this.getId() });
        this.sendPacket(player, (Packet<?>)destroyPacket);
    }
    
    public void show(final Player player) {
        final PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving((EntityLiving)this);
        ReflectionsUtils.setValue(spawnPacket, "b", 120);
        this.sendPacket(player, (Packet<?>)spawnPacket);
    }
    
    public void sendRotation(final Player player) {
        final Location original = this.location;
        final Location location = original.clone().setDirection(player.getLocation().subtract(original.clone()).toVector());
        final byte yaw = (byte)(location.getYaw() * 256.0f / 360.0f);
        final byte pitch = (byte)(location.getPitch() * 256.0f / 360.0f);
        final PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation((Entity)this, yaw);
        this.sendPacket(player, (Packet<?>)headRotationPacket);
        final PacketPlayOutEntity.PacketPlayOutEntityLook lookPacket = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.getId(), yaw, pitch, false);
        this.sendPacket(player, (Packet<?>)lookPacket);
    }
    
    public void setLocation(final Location location) {
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
    
    public int entityId() {
        return this.bukkitEntity.getEntityId();
    }
}
