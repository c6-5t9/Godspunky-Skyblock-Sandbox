package net.hypixel.skyblock.npc.impl;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.type.impl.NPCPlayerImpl;
import com.mojang.authlib.properties.Property;
import net.hypixel.skyblock.npc.impl.type.impl.NPCVillagerImpl;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.HashSet;
import com.mojang.authlib.GameProfile;
import net.hypixel.skyblock.npc.impl.type.NPCBase;
import org.bukkit.Location;
import org.bukkit.World;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import java.util.List;
import java.util.UUID;
import java.util.Set;

public class SkyBlockNPC
{
    private final Set<UUID> viewers;
    private static final Set<UUID> ALREADY_TALKING;
    private final List<EntityArmorStand> holograms;
    protected double cosFOV;
    private final String[] messages;
    private final UUID uuid;
    private final String id;
    private final NPCType type;
    private final World world;
    private final Location location;
    private final NPCSkin skin;
    private final NPCBase npcBase;
    private final GameProfile gameProfile;
    private final NPCParameters parameters;
    
    public SkyBlockNPC(final NPCParameters npcParameters) {
        this.viewers = (Set<UUID>)new HashSet();
        this.holograms = (List<EntityArmorStand>)new ArrayList();
        this.cosFOV = Math.cos(Math.toRadians(60.0));
        this.uuid = UUID.randomUUID();
        this.id = npcParameters.id();
        this.type = npcParameters.type();
        this.world = Bukkit.getWorld(npcParameters.world());
        this.messages = npcParameters.messages();
        if (this.world == null) {
            throw new NullPointerException("World cannot be null for npc : " + this.id);
        }
        this.location = new Location(this.world, npcParameters.x(), npcParameters.y(), npcParameters.z(), npcParameters.yaw(), npcParameters.pitch());
        this.skin = npcParameters.skin();
        this.gameProfile = new GameProfile(this.uuid, this.id);
        if (this.type == NPCType.VILLAGER) {
            this.npcBase = new NPCVillagerImpl(this.location);
        }
        else {
            if (this.skin != null && this.skin.getTexture() != null && this.skin.getSignature() != null) {
                this.gameProfile.getProperties().put((Object)"textures", (Object)new Property("textures", this.skin.getTexture(), this.skin.getSignature()));
            }
            this.npcBase = new NPCPlayerImpl(this.location, this.gameProfile);
        }
        this.npcBase.setLocation(this.location);
        this.parameters = npcParameters;
        SkyblockNPCManager.registerNPC(this);
    }
    
    public void showTo(final Player player) {
        if (this.viewers.contains((Object)player.getUniqueId())) {
            return;
        }
        this.npcBase.show(player);
        this.npcBase.hideNameTag(player);
        this.sendHologram(player, this.getParameters().holograms());
        this.viewers.add((Object)player.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                if (!player.isOnline() || !SkyBlockNPC.this.viewers.contains((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (SkyBlockNPC.this.inRangeOf(player)) {
                    SkyBlockNPC.this.sendHeadRotationPacket(player);
                }
            }
        }.runTaskTimerAsynchronously((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public void hideFrom(final Player player) {
        if (!this.viewers.contains((Object)player.getUniqueId())) {
            return;
        }
        this.viewers.remove((Object)player.getUniqueId());
        this.npcBase.hide(player);
        this.removeHolograms(player);
    }
    
    public void sendHeadRotationPacket(final Player player) {
        this.npcBase.sendRotation(player);
    }
    
    public int getEntityID() {
        return this.npcBase.entityId();
    }
    
    public boolean isShown(final Player player) {
        return this.viewers.contains((Object)player.getUniqueId());
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
    
    public static void sendPacket(final Player player, final Packet<?> packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    public boolean canSpeak(final User user) {
        return this.messages != null && this.messages.length != 0 && !SkyBlockNPC.ALREADY_TALKING.contains((Object)user.getUuid()) && !user.getTalkedNPCs().contains((Object)this.getId());
    }
    
    public void sendHologram(final Player player, final String[] lines) {
        double yOffset = (this.type == NPCType.PLAYER) ? 0.0 : 0.2;
        final double DELTA = 0.3;
        for (final String text : lines) {
            final EntityArmorStand armorStand = new EntityArmorStand(((CraftPlayer)player).getHandle().getWorld());
            final Location hologramLocation = this.getLocation().clone().add(0.0, yOffset, 0.0);
            armorStand.setLocation(hologramLocation.getX(), hologramLocation.getY(), hologramLocation.getZ(), 0.0f, 0.0f);
            armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
            armorStand.setCustomNameVisible(true);
            armorStand.setInvisible(true);
            final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)armorStand);
            sendPacket(player, (Packet<?>)packet);
            this.holograms.add((Object)armorStand);
            yOffset -= DELTA;
        }
    }
    
    public void removeHolograms(final Player player) {
        for (final EntityArmorStand armorStand : this.holograms) {
            final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { armorStand.getId() });
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
        }
        this.holograms.clear();
    }
    
    public CompletableFuture<Void> speak(final Player player) {
        final CompletableFuture<Void> future = (CompletableFuture<Void>)new CompletableFuture();
        if (SkyBlockNPC.ALREADY_TALKING.contains((Object)player.getUniqueId())) {
            future.complete((Object)null);
            return future;
        }
        SkyBlockNPC.ALREADY_TALKING.add((Object)player.getUniqueId());
        int i = 0;
        for (final String message : this.messages) {
            SUtil.delay(() -> this.sendMessage(player, message), i * 20L);
            ++i;
        }
        SUtil.delay(() -> {
            SkyBlockNPC.ALREADY_TALKING.remove((Object)player.getUniqueId());
            if (Objects.equals((Object)this.id, (Object)"JERRY")) {
                return;
            }
            User.getUser(player.getUniqueId()).addTalkedNPC(this.id);
            future.complete((Object)null);
        }, this.messages.length * 20L);
        return future;
    }
    
    public void sendMessage(final Player player, final String message) {
        player.sendMessage((Object)ChatColor.YELLOW + "[NPC] " + this.parameters.name().replace((CharSequence)"&f", (CharSequence)"") + (Object)ChatColor.WHITE + ": " + message);
    }
    
    public Set<UUID> getViewers() {
        return this.viewers;
    }
    
    public List<EntityArmorStand> getHolograms() {
        return this.holograms;
    }
    
    public double getCosFOV() {
        return this.cosFOV;
    }
    
    public String[] getMessages() {
        return this.messages;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public String getId() {
        return this.id;
    }
    
    public NPCType getType() {
        return this.type;
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public NPCSkin getSkin() {
        return this.skin;
    }
    
    public NPCBase getNpcBase() {
        return this.npcBase;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public NPCParameters getParameters() {
        return this.parameters;
    }
    
    static {
        ALREADY_TALKING = (Set)new HashSet();
    }
}
