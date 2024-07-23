package net.hypixel.skyblock.api.disguise;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.IAttribute;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.Scoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.hypixel.skyblock.api.disguise.utils.ReflectionUtils;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.user.User;
import java.util.HashSet;
import java.util.HashMap;

public class PlayerDisguise
{
    public static final HashMap<Integer, PlayerDisguise> nonFake;
    public static final HashMap<Integer, PlayerDisguise> fake;
    private final HashSet<User> shown;
    private final LivingEntity entity;
    private final EntityPlayer fakePlayer;
    private Location l;
    private final BukkitRunnable runnable;
    private static final HashMap<Class<? extends Packet<?>>, String> idFieldsOut;
    private static final HashMap<Class<? extends Packet<?>>, String> idFieldsIn;
    
    public PlayerDisguise(final LivingEntity entity, final String texture, final String value) {
        this(entity, new Property("textures", texture, value));
    }
    
    public PlayerDisguise(final LivingEntity entity, final Property skin) {
        this.shown = (HashSet<User>)new HashSet();
        this.l = entity.getLocation();
        this.entity = entity;
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "§§§§§9§9§9§9");
        new BukkitRunnable() {
            double maxH = 0.0;
            
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (this.maxH != PlayerDisguise.this.getIAttribute(entity, GenericAttributes.maxHealth).getValue()) {
                    PlayerDisguise.this.getIAttribute((LivingEntity)PlayerDisguise.this.fakePlayer.getBukkitEntity(), GenericAttributes.maxHealth).setValue(PlayerDisguise.this.getIAttribute(entity, GenericAttributes.maxHealth).getValue());
                }
                this.maxH = PlayerDisguise.this.getIAttribute(entity, GenericAttributes.maxHealth).getValue();
                PlayerDisguise.this.fakePlayer.setHealth((float)entity.getHealth());
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
        if (skin != null) {
            profile.getProperties().put((Object)"textures", (Object)skin);
        }
        (this.fakePlayer = new EntityPlayer(((CraftServer)Bukkit.getServer()).getServer(), ((CraftWorld)entity.getWorld()).getHandle(), profile, new PlayerInteractManager((World)((CraftWorld)entity.getWorld()).getHandle()))).setPosition(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.check(User.getUser(player));
        }
        PlayerDisguise.nonFake.put((Object)entity.getEntityId(), (Object)this);
        PlayerDisguise.fake.put((Object)this.fakePlayer.getId(), (Object)this);
        (this.runnable = new BukkitRunnable() {
            int i = 0;
            
            public void run() {
                ++this.i;
                if (PlayerDisguise.this.l.equals((Object)entity.getLocation())) {
                    return;
                }
                final Location location = entity.getLocation();
                Packet<?> packet = null;
                if (entity.getLocation().distance(PlayerDisguise.this.l) > 5.0 || this.i >= 100) {
                    System.out.println("sending entity teleport packet!");
                    packet = (Packet<?>)new PacketPlayOutEntityTeleport(PlayerDisguise.this.fakePlayer.getId(), MathHelper.floor(location.getX() * 32.0), MathHelper.floor(location.getY() * 32.0), MathHelper.floor(location.getZ() * 32.0), (byte)(location.getYaw() * 256.0f / 360.0f), (byte)(location.getPitch() * 256.0f / 360.0f), entity.isOnGround());
                }
                if (packet != null) {
                    PlayerDisguise.this.sendPacket(packet);
                }
                PlayerDisguise.this.l = entity.getLocation();
            }
        }).runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
    }
    
    public boolean onPacketOut(final Packet<?> packet) {
        if (PlayerDisguise.idFieldsOut.containsKey((Object)packet.getClass())) {
            ReflectionUtils.setField((String)PlayerDisguise.idFieldsOut.get((Object)packet.getClass()), packet, this.fakePlayer.getId());
        }
        if (packet instanceof PacketPlayOutEntity.PacketPlayOutEntityLook || packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMove || packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook) {
            return false;
        }
        if (packet instanceof PacketPlayOutEntityStatus || packet instanceof PacketPlayOutEntityMetadata) {
            this.getIAttribute((LivingEntity)this.fakePlayer.getBukkitEntity(), GenericAttributes.maxHealth).setValue(this.getIAttribute(this.entity, GenericAttributes.maxHealth).getValue());
            this.fakePlayer.setHealth((float)this.entity.getHealth());
        }
        return true;
    }
    
    public void onPacketIn(final Packet<?> packet) {
        if (PlayerDisguise.idFieldsIn.containsKey((Object)packet.getClass())) {
            ReflectionUtils.setField((String)PlayerDisguise.idFieldsIn.get((Object)packet.getClass()), packet, this.entity.getEntityId());
        }
    }
    
    public void check(final User player) {
        if (player == null) {
            return;
        }
        if (!this.shown.contains((Object)player)) {
            if (this.inRangeOf(player.toBukkitPlayer(), this.entity.getLocation())) {
                this.shown.add((Object)player);
                player.sendPacket((Packet<?>)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { this.fakePlayer }));
                player.sendPacket((Packet<?>)new PacketPlayOutNamedEntitySpawn((EntityHuman)this.fakePlayer));
                final Location location = this.entity.getLocation();
                final PacketPlayOutEntityTeleport entityTeleportPacket = new PacketPlayOutEntityTeleport(this.fakePlayer.getId(), MathHelper.floor(location.getX() * 32.0), MathHelper.floor(location.getY() * 32.0), MathHelper.floor(location.getZ() * 32.0), (byte)(location.getYaw() * 256.0f / 360.0f), (byte)(location.getPitch() * 256.0f / 360.0f), this.entity.isOnGround());
                player.sendPacket((Packet<?>)entityTeleportPacket);
                player.sendPacket((Packet<?>)new PacketPlayOutEntityDestroy(new int[] { this.entity.getEntityId() }));
                final String s = "99n" + this.fakePlayer.getUniqueID().toString().substring(1, 5);
                final CraftScoreboardManager scoreboardManager = ((CraftServer)Bukkit.getServer()).getScoreboardManager();
                final CraftScoreboard craftScoreboard = scoreboardManager.getMainScoreboard();
                final Scoreboard scoreboard = craftScoreboard.getHandle();
                ScoreboardTeam scoreboardTeam = scoreboard.getTeam(s);
                if (scoreboardTeam == null) {
                    scoreboardTeam = new ScoreboardTeam(scoreboard, s);
                }
                scoreboardTeam.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
                scoreboardTeam.setPrefix("[NPC] ");
                player.sendPacket((Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, 1));
                player.sendPacket((Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, 0));
                player.sendPacket((Packet<?>)new PacketPlayOutScoreboardTeam(scoreboardTeam, (Collection)Collections.singletonList((Object)s), 3));
            }
        }
        else if (!this.inRangeOf(player.toBukkitPlayer(), this.entity.getLocation())) {
            this.shown.remove((Object)player);
        }
    }
    
    private void sendPacket(final Packet<?> packet) {
        if (this.shown.isEmpty()) {
            return;
        }
        for (final User player : this.shown) {
            if (player == null) {
                continue;
            }
            player.sendPacket(packet);
        }
    }
    
    public static void packetInManager(final Packet<?> packet) {
        if (!PlayerDisguise.idFieldsIn.containsKey((Object)packet.getClass())) {
            return;
        }
        final int id = (int)ReflectionUtils.getField(ReflectionUtils.findField(packet.getClass(), (String)PlayerDisguise.idFieldsIn.get((Object)packet.getClass())), packet);
        if (PlayerDisguise.fake.containsKey((Object)id)) {
            ((PlayerDisguise)PlayerDisguise.fake.get((Object)id)).onPacketIn(packet);
        }
    }
    
    public boolean inRangeOf(final Player player, final Location location) {
        if (player == null) {
            return false;
        }
        if (!player.getWorld().equals(location.getWorld())) {
            return false;
        }
        final double hideDistance = 40.0;
        final double distanceSquared = player.getLocation().distanceSquared(location);
        final double bukkitRange = Bukkit.getViewDistance() << 4;
        return distanceSquared <= SUtil.square(hideDistance) && distanceSquared <= SUtil.square(bukkitRange);
    }
    
    public static boolean packetOutManager(final Packet<?> packet) {
        if ((packet instanceof PacketPlayOutSpawnEntityLiving || packet instanceof PacketPlayOutSpawnEntity || packet instanceof PacketPlayOutNamedEntitySpawn) && PlayerDisguise.nonFake.containsKey((Object)(int)ReflectionUtils.getField(ReflectionUtils.findField(packet.getClass(), (String)PlayerDisguise.idFieldsOut.get((Object)packet.getClass())), packet))) {
            return false;
        }
        if (!PlayerDisguise.idFieldsOut.containsKey((Object)packet.getClass())) {
            return true;
        }
        final int id = (int)ReflectionUtils.getField(ReflectionUtils.findField(packet.getClass(), (String)PlayerDisguise.idFieldsOut.get((Object)packet.getClass())), packet);
        return !PlayerDisguise.nonFake.containsKey((Object)id) || ((PlayerDisguise)PlayerDisguise.nonFake.get((Object)id)).onPacketOut(packet);
    }
    
    public void kill(final User player) {
        if (player != null) {
            final Vector vec = this.entity.getLocation().toVector().subtract(player.toBukkitPlayer().getLocation().toVector()).normalize().setY(0.3);
            final PacketPlayOutEntityVelocity p = new PacketPlayOutEntityVelocity(this.fakePlayer.getId(), vec.getX(), vec.getY(), vec.getZ());
            this.sendPacket((Packet<?>)p);
        }
        Bukkit.getScheduler().runTaskLater((Plugin)SkyBlock.getPlugin(), () -> {
            this.entity.setHealth(0.0);
            this.fakePlayer.setHealth(0.0f);
            this.sendPacket((Packet<?>)new PacketPlayOutEntityStatus((Entity)this.fakePlayer, (byte)60));
        }, 1L);
        this.runnable.cancel();
        Bukkit.getScheduler().runTaskLater((Plugin)SkyBlock.getPlugin(), () -> {
            this.sendPacket((Packet<?>)new PacketPlayOutEntityDestroy(new int[] { this.fakePlayer.getId() }));
            PlayerDisguise.nonFake.remove((Object)this.entity.getEntityId());
            PlayerDisguise.fake.remove((Object)this.fakePlayer.getId());
            this.entity.remove();
        }, 20L);
    }
    
    public AttributeInstance getIAttribute(final LivingEntity entity, final IAttribute iAttribute) {
        return ((CraftLivingEntity)entity).getHandle().getAttributeInstance(iAttribute);
    }
    
    public void status(final byte b) {
        this.sendPacket((Packet<?>)new PacketPlayOutEntityStatus((Entity)this.fakePlayer, b));
    }
    
    public EntityPlayer getFakePlayer() {
        return this.fakePlayer;
    }
    
    static {
        nonFake = new HashMap();
        fake = new HashMap();
        idFieldsOut = new HashMap();
        idFieldsIn = new HashMap();
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntity.PacketPlayOutEntityLook.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntity.PacketPlayOutRelEntityMove.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityStatus.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityHeadRotation.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutAttachEntity.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityVelocity.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityEffect.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityEquipment.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutAnimation.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutEntityMetadata.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutNamedEntitySpawn.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutSpawnEntityLiving.class, (Object)"a");
        PlayerDisguise.idFieldsOut.put((Object)PacketPlayOutSpawnEntity.class, (Object)"a");
        PlayerDisguise.idFieldsIn.put((Object)PacketPlayInEntityAction.class, (Object)"a");
        PlayerDisguise.idFieldsIn.put((Object)PacketPlayInUseEntity.class, (Object)"a");
    }
}
