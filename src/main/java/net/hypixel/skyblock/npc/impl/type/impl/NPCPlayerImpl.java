package net.hypixel.skyblock.npc.impl.type.impl;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.Scoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
import net.hypixel.skyblock.util.SUtil;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import net.hypixel.skyblock.npc.impl.type.NPCBase;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class NPCPlayerImpl extends EntityPlayer implements NPCBase
{
    private final Location location;
    private final String name;
    
    public NPCPlayerImpl(final Location location, final GameProfile gameProfile) {
        super(((CraftServer)Bukkit.getServer()).getServer(), ((CraftWorld)location.getWorld()).getHandle(), gameProfile, new PlayerInteractManager((World)((CraftWorld)location.getWorld()).getHandle()));
        this.name = gameProfile.getName();
        this.location = location;
    }
    
    public void show(final Player player) {
        final PacketPlayOutPlayerInfo infoAddPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { this });
        final PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn((EntityHuman)this);
        final PacketPlayOutPlayerInfo infoRemovePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { this });
        this.sendPacket(player, (Packet<?>)infoAddPacket);
        this.sendPacket(player, (Packet<?>)spawnPacket);
        this.sendPacket(player, (Packet<?>)infoRemovePacket);
    }
    
    public void hide(final Player player) {
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.getId() });
        this.sendPacket(player, (Packet<?>)packet);
    }
    
    public void hideNameTag(final Player player) {
        final CraftScoreboardManager scoreboardManager = ((CraftServer)Bukkit.getServer()).getScoreboardManager();
        final CraftScoreboard craftScoreboard = scoreboardManager.getMainScoreboard();
        final Scoreboard scoreboard = craftScoreboard.getHandle();
        ScoreboardTeam scoreboardTeam = scoreboard.getTeam(this.name);
        if (scoreboardTeam == null) {
            scoreboardTeam = new ScoreboardTeam(scoreboard, this.name);
        }
        scoreboardTeam.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        scoreboardTeam.setPrefix("[NPC] ");
        this.sendPacket(player, (Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, 1));
        this.sendPacket(player, (Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, 0));
        this.sendPacket(player, (Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, (Collection)Collections.singletonList((Object)this.name), 3));
        SUtil.delay(() -> this.fixSkinHelmetLayerForPlayer(player), 8L);
    }
    
    public void fixSkinHelmetLayerForPlayer(final Player player) {
        final DataWatcher watcher = this.getDataWatcher();
        watcher.watch(10, (Object)127);
        final PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(this.getId(), watcher, true);
        this.sendPacket(player, (Packet<?>)metadata);
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
        return this.getBukkitEntity().getEntityId();
    }
}
