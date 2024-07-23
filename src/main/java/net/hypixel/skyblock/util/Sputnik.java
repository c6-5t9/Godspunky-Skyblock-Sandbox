package net.hypixel.skyblock.util;

import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanBossManager;
import net.hypixel.skyblock.gui.BossMenu;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import net.hypixel.skyblock.features.dungeons.blessing.BlessingChest;
import net.hypixel.skyblock.features.dungeons.blessing.Blessings;
import net.hypixel.skyblock.features.dungeons.chest.ItemChest;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import com.google.common.util.concurrent.AtomicDouble;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.hypixel.skyblock.command.AccessTimedCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.hypixel.skyblock.gui.TradeMenu;
import java.util.HashMap;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.registry.WorldData;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.WorldEditException;
import java.io.IOException;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.Closeable;
import java.io.FileInputStream;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import java.io.File;
import org.bukkit.block.Block;
import org.bukkit.inventory.EntityEquipment;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.entity.EntityType;
import java.lang.reflect.Method;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import java.lang.reflect.InvocationTargetException;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.Sound;
import java.text.DecimalFormat;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.gui.PetsGUI;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.api.beam.Beam;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.EntityItem;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.UserStash;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Bukkit;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.World;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.FireworkEffect;
import org.bukkit.Color;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import java.util.UUID;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.Server;
import java.util.Map;
import java.util.Random;

public class Sputnik
{
    public Random random;
    public static final Map<Server, Integer> RunThisSession;
    public static final Map<SItem, Integer> MidasStaff;
    public static final Map<SItem, Long> MidasStaffDmg;
    public static final Map<UUID, Integer> CoinsTakenOut;
    public static final Map<UUID, Boolean> IsInsideTheBeam;
    public static final Map<UUID, Boolean> CooldownAbs;
    public static final Map<UUID, Boolean> HaveDMGReduction;
    public static final Map<Entity, Location> MAP_PARTICLE_1;
    
    public Sputnik() {
        this.random = new Random();
    }
    
    public static String trans(final String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }
    
    public static String trans(final String... content) {
        return ChatColor.translateAlternateColorCodes('&', String.valueOf((Object)content));
    }
    
    public static String trans4(String content, String arg1, String arg2, String arg3) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        arg1 = ChatColor.translateAlternateColorCodes('&', arg1);
        arg2 = ChatColor.translateAlternateColorCodes('&', arg2);
        arg3 = ChatColor.translateAlternateColorCodes('&', arg3);
        return String.format(content, new Object[] { arg1, arg2, arg3 });
    }
    
    public static String trans2(String content, String arg1) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        arg1 = ChatColor.translateAlternateColorCodes('&', arg1);
        return String.format(content, new Object[] { arg1 });
    }
    
    public static String trans3(String content, String arg1, String arg2) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        arg1 = ChatColor.translateAlternateColorCodes('&', arg1);
        arg2 = ChatColor.translateAlternateColorCodes('&', arg2);
        return String.format(content, new Object[] { arg1, arg2 });
    }
    
    public static String trans5(String content, String arg1, String arg2, String arg3, String arg4) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        arg1 = ChatColor.translateAlternateColorCodes('&', arg1);
        arg2 = ChatColor.translateAlternateColorCodes('&', arg2);
        arg3 = ChatColor.translateAlternateColorCodes('&', arg3);
        arg4 = ChatColor.translateAlternateColorCodes('&', arg4);
        return String.format(content, new Object[] { arg1, arg2, arg3, arg4 });
    }
    
    public static String trans5(String content, String arg1, String arg2, String arg3, String arg4, String arg5) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        arg1 = ChatColor.translateAlternateColorCodes('&', arg1);
        arg2 = ChatColor.translateAlternateColorCodes('&', arg2);
        arg3 = ChatColor.translateAlternateColorCodes('&', arg3);
        arg4 = ChatColor.translateAlternateColorCodes('&', arg4);
        arg5 = ChatColor.translateAlternateColorCodes('&', arg5);
        return String.format(content, new Object[] { arg1, arg2, arg3, arg4, arg5 });
    }
    
    public static void sendHeadRotation(final Entity e, final float yaw, final float pitch) {
        final net.minecraft.server.v1_8_R3.Entity pl = (net.minecraft.server.v1_8_R3.Entity)((CraftZombie)e).getHandle();
        pl.setLocation(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), yaw, pitch);
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(pl);
        sendPacket(e.getWorld(), (Packet)packet);
    }
    
    public static void sendColoredFireWork(final Color color, final Location loc) {
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        final FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BURST).withColor(color).build();
        SSU.spawn(loc, effect, new Player[0]);
    }
    
    public static void sendReportToMotherland(final String message) {
    }
    
    public static void spawnEnumParticlePIW(final EnumParticle effect, final Location loc, final int arg1, final int arg2, final int arg3, final int arg4, final int arg5, final World world) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(effect, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), (float)arg1, (float)arg2, (float)arg3, (float)arg4, arg5, new int[0]);
        for (final Player online : world.getPlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void spawnEnumParticleCSO(final EnumParticle effect, final Location loc, final int arg1, final int arg2, final int arg3, final int arg4, final int arg5, final Player player) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(effect, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), (float)arg1, (float)arg2, (float)arg3, (float)arg4, arg5, new int[0]);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    public static void spawnEnumParticleLOP(final EnumParticle effect, final Location loc, final int arg1, final int arg2, final int arg3, final int arg4, final int arg5, final Player[] p) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(effect, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), (float)arg1, (float)arg2, (float)arg3, (float)arg4, arg5, new int[0]);
        for (final Player online : p) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void spawnEnumParticlePIS(final EnumParticle effect, final Location loc, final int arg1, final int arg2, final int arg3, final int arg4, final int arg5) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(effect, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), (float)arg1, (float)arg2, (float)arg3, (float)arg4, arg5, new int[0]);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void midasFlame(final Entity entity) {
        final float angle_1 = ((LivingEntity)entity).getEyeLocation().getYaw() / 60.0f;
        final Location loc1_a = ((LivingEntity)entity).getEyeLocation().add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
        final Location loc2_a = ((LivingEntity)entity).getEyeLocation().subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
        entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc1_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc2_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc1_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc2_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc1_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc2_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc1_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc2_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc1_a, Effect.LAVA_POP, 3);
        entity.getWorld().playEffect(loc2_a, Effect.LAVA_POP, 3);
    }
    
    public static void midasCalcDamage(final Entity entity1, final Player player, final SItem sItem) {
        String act = "True";
        if (act == "True") {
            act = "False";
            for (final Entity entity2 : entity1.getWorld().getNearbyEntities(entity1.getLocation(), 1.0, 4.0, 1.0)) {
                int count = 0;
                if (entity2.isDead()) {
                    continue;
                }
                if (!(entity2 instanceof LivingEntity)) {
                    continue;
                }
                if (entity2.hasMetadata("GiantSword")) {
                    continue;
                }
                if (entity2.hasMetadata("NoAffect")) {
                    continue;
                }
                if (entity2 instanceof Player || entity2 instanceof EnderDragonPart) {
                    continue;
                }
                if (entity2 instanceof Villager) {
                    continue;
                }
                if (entity2 instanceof ArmorStand) {
                    continue;
                }
                final User user = User.getUser(player.getUniqueId());
                final double baseDamage = calculateMagicDamage(entity2, player, 32000, 0.3);
                user.damageEntityIgnoreShield((Damageable)entity2, (int)baseDamage);
                ++count;
                PlayerListener.spawnDamageInd(entity2, baseDamage, false);
                if (!Sputnik.MidasStaff.containsKey((Object)sItem)) {
                    Sputnik.MidasStaff.put((Object)sItem, (Object)0);
                }
                if (!Sputnik.MidasStaffDmg.containsKey((Object)sItem)) {
                    Sputnik.MidasStaffDmg.put((Object)sItem, (Object)0L);
                }
                Sputnik.MidasStaff.put((Object)sItem, (Object)((int)Sputnik.MidasStaff.get((Object)sItem) + count));
                Sputnik.MidasStaffDmg.put((Object)sItem, (Object)(long)((int)Sputnik.MidasStaff.get((Object)sItem) * baseDamage));
            }
        }
    }
    
    public static Integer midasCalcCounter(final Entity entity1, final Player player) {
        return 0;
    }
    
    @Deprecated
    public static int makeZero() {
        return 0;
    }
    
    public static void smartGiveItem(final ItemStack item, final Player p) {
        if (item == null) {
            return;
        }
        if (p.getInventory().firstEmpty() != -1) {
            p.getInventory().addItem(new ItemStack[] { item });
        }
        else if (User.getUser(p.getUniqueId()) != null) {
            final UserStash us = UserStash.getStash(p.getUniqueId());
            us.addItemInStash(item);
        }
    }
    
    public static void GiveItem(final ItemStack item, final Player p) {
        p.getInventory().addItem(new ItemStack[] { item });
    }
    
    public static boolean isFullInv(final Player p) {
        return p.getInventory().firstEmpty() == -1;
    }
    
    public static String calMagicLore(final Player p, int baseMagicDmg, final double scale) {
        if (p == null) {
            return SUtil.commaify(baseMagicDmg);
        }
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
        final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
        final PlayerInventory inv = p.getInventory();
        final SItem helmet = SItem.find(inv.getHelmet());
        if (helmet != null) {
            if (helmet.getType() == SMaterial.DARK_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 25 / 100;
            }
            else if (helmet.getType() == SMaterial.SHADOW_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 35 / 100;
            }
            else if (helmet.getType() == SMaterial.WITHER_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 45 / 100;
            }
        }
        return SUtil.commaify(baseMagicDmg * (manaPool / 100 * 0.3 + 1.0));
    }
    
    public static void endermanCarryBlock(final Enderman e, final Material mat) {
        e.setCarriedMaterial(new ItemStack(mat).getData());
    }
    
    public static void showFakeItem(final Location loc, final ItemStack material, final Player p) {
        final EntityItem item = new EntityItem((net.minecraft.server.v1_8_R3.World)((CraftWorld)loc.getWorld()).getHandle());
        item.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0.0f, 0.0f);
        item.setItemStack(CraftItemStack.asNMSCopy(material));
        final PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity((net.minecraft.server.v1_8_R3.Entity)item, 2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    public static void summonCircle(final Entity e, final Location location, final int size) {
        final Beam beam = new Beam(location, location.add(e.getLocation().getDirection().multiply(30)));
        beam.start();
        for (int d = 0; d <= 90; ++d) {
            final Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
            particleLoc.setX(location.getX() + Math.cos((double)d) * size);
            particleLoc.setZ(location.getZ() + Math.sin((double)d) * size);
            beam.setStartingPosition(location);
            beam.setEndingPosition(location.add(e.getLocation().getDirection().multiply(size)));
        }
    }
    
    public static void entityBeam(final ArmorStand stand, final Location location1, final Player p, final Entity e) {
        final Beam beam = new Beam(stand.getLocation().clone().add(stand.getLocation().getDirection().normalize().multiply(20)), stand.getLocation().clone().add(stand.getLocation().getDirection().normalize().multiply(-20)));
        stand.setGravity(false);
        beam.start();
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    if (beam.isActive()) {
                        beam.stop();
                    }
                    this.cancel();
                    return;
                }
                final Location location = stand.getLocation();
                location.setYaw(stand.getLocation().getYaw() + 2.25f);
                stand.teleport(location);
                Sputnik.getEntity(stand.getLocation().add(stand.getLocation().getDirection().multiply(20)), stand.getLocation().add(stand.getLocation().getDirection().multiply(-20)), p, e);
                beam.setEndingPosition(stand.getLocation().add(stand.getLocation().getDirection().multiply(-Sputnik.findArgo(p.getLocation(), stand.getLocation()))));
                beam.setStartingPosition(stand.getLocation().add(stand.getLocation().getDirection().normalize().multiply(Sputnik.findArgo(p.getLocation(), stand.getLocation()))));
                if (!beam.isActive() && !stand.isDead()) {
                    beam.update();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static int findArgo(final Location arg0, final Location arg1) {
        final double dist = arg0.distance(arg1);
        if (dist < 5.0) {
            return 10;
        }
        return 20;
    }
    
    public static void RemoveEntityArray(final ArrayList<Entity> a) {
        for (final Entity e : a) {
            if (e != null) {
                e.remove();
            }
        }
    }
    
    public static void RemoveEntityArray(final List<Entity> a) {
        for (final Entity e : a) {
            if (e != null) {
                e.remove();
            }
        }
    }
    
    public static void createPet(final Player player) {
        final Pet.PetItem pet = User.getUser(player.getUniqueId()).getActivePet();
        final Pet petclass = User.getUser(player.getUniqueId()).getActivePetClass();
        if (pet != null && petclass != null) {
            PetsGUI.spawnFlyingHeads(player, petclass, pet.toItem().getStack());
        }
    }
    
    public static void getEntity(final Location finaldestination, final Location ended, final Player player, final Entity e) {
        final Location blockLocation = finaldestination;
        final Location crystalLocation = ended;
        final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 25.0;
        for (int i = 1; i <= 25; ++i) {
            for (final Entity entity : ended.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 25.0)), 0.2, 0.0, 0.2)) {
                if (entity == player) {
                    Sputnik.IsInsideTheBeam.put((Object)player.getUniqueId(), (Object)true);
                    return;
                }
            }
        }
    }
    
    public static void drawLine(final Location point1, final Location point2, final double space) {
        final float angle_1 = point1.getYaw() / 60.0f;
        final Location loc1 = point1.add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
        final float angle_2 = point2.getYaw() / 60.0f;
        final Location loc2 = point2.subtract(Math.cos((double)angle_2), 0.0, Math.sin((double)angle_2));
        final Location blockLocation = loc1;
        final Location crystalLocation = loc2;
        final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 13.0;
        final double length = 0.0;
        for (int i = 1; i <= 13; ++i) {
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 13.0)), Effect.COLOURED_DUST, 0, 1, 0.8627451f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 13.0)), Effect.COLOURED_DUST, 0, 1, 1.0196079f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
        }
    }
    
    public static void drawLineforMovingPoints(final Location point1, final Location point2, final double space, final Player p, final Integer i1uu, final Entity e) {
        final Location blockLocation = point1;
        final Location crystalLocation = point2;
        int i1 = 0;
        if (VoidgloomSeraph.NUKEKUBI_DAMAGE.containsKey((Object)p)) {
            i1 = (int)VoidgloomSeraph.NUKEKUBI_DAMAGE.get((Object)p);
        }
        final Vector vector = blockLocation.clone().toVector().subtract(crystalLocation.clone().toVector());
        final double count = 30.0;
        final double length = 0.0;
        for (int j = 1; j <= 30; ++j) {
            final Vector v = vector.clone().multiply(j / 30.0);
            SUtil.delay(() -> point1.getWorld().spigot().playEffect(crystalLocation.clone().add(v), Effect.MAGIC_CRIT, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30), 0L);
        }
    }
    
    public static void ferocityParticle(final LivingEntity e) {
        final int side = SUtil.random(0, 1);
        final float angle_1 = e.getLocation().clone().add(0.0, 1.5, 0.0).getYaw() / 60.0f;
        final Location loc1 = e.getLocation().clone().add(0.0, 1.5, 0.0).add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
        final Location loc2 = e.getLocation().clone().add(0.0, 1.5, 0.0).subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
        if (side == 1) {
            final Location up = loc1.add(0.0, 1.0, 0.0);
            final Location down = loc2.add(0.0, -1.0, 0.0);
            drawLine(up, down, 0.0);
        }
        else {
            final Location up2 = loc1.add(0.0, -1.0, 0.0);
            final Location down2 = loc2.add(0.0, 1.0, 0.0);
            drawLine(up2, down2, 0.0);
        }
    }
    
    public static String format(float value) {
        final String[] arr = { "", "k", "M", "B", "T", "P", "E" };
        int index = 0;
        final float realvalue = value;
        while (value / 1000.0f >= 1.0f) {
            value /= 1000.0f;
            ++index;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        if (realvalue < 1000000.0f) {
            decimalFormat = new DecimalFormat("#");
        }
        final String finalr = String.format("%s%s", new Object[] { decimalFormat.format((double)value), arr[index] });
        String fin = finalr.replaceAll(",", ".");
        if (realvalue <= 20000.0f && realvalue > 0.0f) {
            fin = String.valueOf((long)Math.round(realvalue));
        }
        return fin;
    }
    
    public static String formatFull(float value) {
        final String[] arr = { "", "k", "M", "B", "T", "P", "E" };
        int index = 0;
        final float realvalue = value;
        while (value / 1000.0f >= 1.0f) {
            value /= 1000.0f;
            ++index;
        }
        final DecimalFormat decimalFormat = new DecimalFormat("#.#");
        final String finalr = String.format("%s%s", new Object[] { decimalFormat.format((double)value), arr[index] });
        String fin = finalr.replaceAll(",", ".");
        if (realvalue <= 1000.0f && realvalue > 0.0f) {
            fin = SUtil.commaify(Math.round(realvalue));
        }
        return fin;
    }
    
    public static void createHelix(final Entity e) {
        final Location loc = e.getLocation();
        final int radius = 1;
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                for (double y = 0.0; y <= 1.5; y += 0.05) {
                    final double x = 1.0 * Math.cos(y);
                    final double z = 1.0 * Math.sin(y);
                    final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, (float)(loc.getX() + x), (float)(loc.getY() + y), (float)(loc.getZ() + z), 0.0f, 0.0f, 0.0f, 0.0f, 1, new int[0]);
                    for (final Player online : e.getWorld().getPlayers()) {
                        ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
    }
    
    public static void witherShieldActive2(final Player p) {
        if (!Sputnik.HaveDMGReduction.containsKey((Object)p.getUniqueId())) {
            Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)false);
        }
        if (Sputnik.CooldownAbs.containsKey((Object)p.getUniqueId())) {
            final boolean isActivable = (boolean)Sputnik.CooldownAbs.get((Object)p.getUniqueId());
            if (!isActivable) {
                Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)true);
                Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)true);
                p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 1.25f, 0.25f);
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
                final EntityHuman human = ((CraftHumanEntity)p).getHandle();
                final double critDmg = statistics.getCritDamage().addAll();
                final double absorbsion = critDmg * 100.0 * 300.0 / 100.0;
                final float apply = (float)Math.round(absorbsion);
                SputnikPlayer.setCustomAbsorptionHP(p, apply);
                createCircle2(p, 0.7, 1);
                new BukkitRunnable() {
                    public void run() {
                        final int finalHP = Math.round((float)(SputnikPlayer.getCustomAbsorptionHP(p) * 50 / 100));
                        SputnikPlayer.setCustomAbsorptionHP(p, 0.0f);
                        p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + finalHP));
                        Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)false);
                        new BukkitRunnable() {
                            public void run() {
                                Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)false);
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
                    }
                }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
            }
        }
        else {
            Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)false);
            witherShieldActive2(p);
        }
    }
    
    public static void witherShieldActive(final Player p) {
        if (!Sputnik.HaveDMGReduction.containsKey((Object)p.getUniqueId())) {
            Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)false);
        }
        if (Sputnik.CooldownAbs.containsKey((Object)p.getUniqueId())) {
            final boolean isActivable = (boolean)Sputnik.CooldownAbs.get((Object)p.getUniqueId());
            if (!isActivable) {
                Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)true);
                Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)true);
                p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 1.25f, 0.25f);
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
                final EntityHuman human = ((CraftHumanEntity)p).getHandle();
                final double critDmg = statistics.getCritDamage().addAll();
                final double absorbsion = critDmg * 100.0 * 150.0 / 100.0;
                final float apply = (float)Math.round(absorbsion);
                SputnikPlayer.setCustomAbsorptionHP(p, apply);
                createCircle(p, 0.7, 1);
                new BukkitRunnable() {
                    public void run() {
                        final int finalHP = Math.round((float)(SputnikPlayer.getCustomAbsorptionHP(p) * 50 / 100));
                        SputnikPlayer.setCustomAbsorptionHP(p, 0.0f);
                        p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + finalHP));
                        Sputnik.HaveDMGReduction.put((Object)p.getUniqueId(), (Object)false);
                        new BukkitRunnable() {
                            public void run() {
                                Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)false);
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
                    }
                }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
            }
        }
        else {
            Sputnik.CooldownAbs.put((Object)p.getUniqueId(), (Object)false);
            witherShieldActive(p);
        }
    }
    
    private static void createCircle(final Player player, final double radius, final int distance) {
        final Vector dist = player.getEyeLocation().getDirection().multiply(distance);
        final Location mid = player.getEyeLocation().add(dist);
        for (int particles = 18, i = 0; i < particles; ++i) {
            final double angle = 6.283185307179586 * i / particles;
            final double x = Math.cos(angle) * radius;
            final double y = Math.sin(angle) * radius;
            Vector v = rotateAroundAxisX(new Vector(x, y, 0.0), player.getEyeLocation().getPitch());
            v = rotateAroundAxisY(v, player.getEyeLocation().getYaw());
            final Location temp = mid.clone().add(v);
            player.getWorld().spigot().playEffect(temp, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        }
    }
    
    private static void createCircle2(final Player player, final double radius, final int distance) {
        final Vector dist = player.getEyeLocation().getDirection().multiply(distance);
        final Location mid = player.getEyeLocation().add(dist);
        for (int particles = 18, i = 0; i < particles; ++i) {
            final double angle = 6.283185307179586 * i / particles;
            final double x = Math.cos(angle) * radius;
            final double y = Math.sin(angle) * radius;
            Vector v = rotateAroundAxisX(new Vector(x, y, 0.0), player.getEyeLocation().getPitch());
            v = rotateAroundAxisY(v, player.getEyeLocation().getYaw());
            final Location temp = mid.clone().add(v);
            player.getWorld().spigot().playEffect(temp, Effect.FLAME, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        }
    }
    
    private static Vector rotateAroundAxisX(final Vector v, double angle) {
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }
    
    private static Vector rotateAroundAxisY(final Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
    
    public static void playActivateSound(final Player p) {
        p.playSound(p.getLocation(), Sound.GHAST_MOAN, 1.0f, 2.0f);
        p.playSound(p.getLocation(), Sound.GHAST_SCREAM2, 1.0f, 2.0f);
        SUtil.delay(() -> p.playSound(p.getLocation(), Sound.GHAST_SCREAM2, 0.5f, 1.5f), 2L);
    }
    
    public static void playDeActivateSound(final Player p) {
        p.playSound(p.getLocation(), Sound.GHAST_SCREAM2, 1.0f, 1.5f);
        p.playSound(p.getLocation(), Sound.GHAST_SCREAM2, 1.0f, 1.1f);
    }
    
    public static void playFuckingSoundOfVoidgloomThatTookForeverToMake(final Player p, final Entity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity e : p.getWorld().getNearbyEntities(p.getLocation(), 20.0, 20.0, 20.0)) {
                    if (e instanceof Player) {
                        final Player player = (Player)e;
                        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_UNFECT, 0.1f, 0.7f);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 5L);
    }
    
    public static void moveTo(final LivingEntity entity, final Location moveTo, final float speed) {
        final EntityLiving nmsEntity = ((CraftLivingEntity)entity).getHandle();
        final PathEntity path = ((EntityInsentient)nmsEntity).getNavigation().a(moveTo.getX(), moveTo.getY(), moveTo.getZ());
        ((EntityInsentient)nmsEntity).getNavigation().a(path, (double)speed);
    }
    
    public static String buildcustomString(final String entityname, final Integer level, final boolean hideLVL) {
        if (level == 0) {
            return (Object)ChatColor.RED + entityname;
        }
        String returnstring = trans("&8[&7Lvl" + (Object)level + "&8] &c" + entityname);
        if (hideLVL) {
            returnstring = trans(entityname);
        }
        return returnstring;
    }
    
    public static String entityNameTag(final LivingEntity entity, String customstring) {
        String returnstring = "";
        customstring = trans(customstring);
        final int hp = (int)entity.getMaxHealth() / 2;
        final int hp2 = (int)entity.getHealth();
        final int hp3 = (int)entity.getMaxHealth();
        if (!VoidgloomSeraph.HIT_SHIELD.containsKey((Object)entity)) {
            if (hp2 <= hp && hp3 < 50000 && !entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.YELLOW + hp2 + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + hp3 + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp2 > hp && hp3 < 50000 && !entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.GREEN + hp2 + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + hp3 + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp3 > 50000 && hp2 > hp && !entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.GREEN + format((float)hp2) + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + format((float)hp3) + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp3 > 50000 && hp2 <= hp && !entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.YELLOW + format((float)hp2) + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + format((float)hp3) + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp2 <= hp && hp3 < 50000 && entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.YELLOW + hp2 + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp2 > hp && hp3 < 50000 && entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.GREEN + hp2 + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp3 > 50000 && hp2 > hp && entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.GREEN + format((float)hp2) + (Object)ChatColor.RED + "\u2764";
            }
            else if (hp3 > 50000 && hp2 <= hp && entity.hasMetadata("SlayerBoss")) {
                returnstring = (Object)ChatColor.RED + customstring + " " + (Object)ChatColor.YELLOW + format((float)hp2) + (Object)ChatColor.RED + "\u2764";
            }
        }
        else {
            final int hitshield = (int)VoidgloomSeraph.HIT_SHIELD.get((Object)entity);
            final int hitshieldmax = (int)VoidgloomSeraph.HIT_SHIELD_MAX.get((Object)entity);
            String defineHitShield = trans("&f&l" + hitshield + " Hits");
            if (hitshield <= hitshieldmax / 2 && hitshield > hitshieldmax * 25 / 100) {
                defineHitShield = trans("&d&l" + hitshield + " Hits");
            }
            else if (hitshield <= hitshieldmax * 25 / 100 && hitshield != 1) {
                defineHitShield = trans("&5&l" + hitshield + " Hits");
            }
            else if (hitshield == 1) {
                defineHitShield = trans("&5&l" + hitshield + " Hit");
            }
            returnstring = (Object)ChatColor.RED + customstring + " " + defineHitShield;
        }
        return returnstring;
    }
    
    public static PlayerDisguise applyPacketNPC(final Entity entity, final String skinURLorUsername, final String URL_2, final boolean isURLSkin) {
        final PlayerDisguise playerDisguise = new PlayerDisguise("");
        try {
            final Method m = FlagWatcher.class.getDeclaredMethod("setValue", Integer.TYPE, Object.class);
            m.setAccessible(true);
            m.invoke((Object)playerDisguise.getWatcher(), new Object[] { 10, 127 });
        }
        catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        playerDisguise.setShowName(false);
        if (isURLSkin && URL_2 != null) {
            playerDisguise.getGameProfile().getProperties().put((Object)"textures", (Object)new WrappedSignedProperty("textures", skinURLorUsername, URL_2));
        }
        else {
            playerDisguise.setSkin(skinURLorUsername);
        }
        playerDisguise.setEntity(entity);
        playerDisguise.startDisguise();
        return playerDisguise;
    }
    
    public static void applyPacketGiant(final Entity entity) {
        final MobDisguise d = new MobDisguise(EntityType.GIANT);
        d.setEntity(entity);
        d.startDisguise();
    }
    
    public static void applyPacketGolem(final Entity entity) {
        final MobDisguise d = new MobDisguise(EntityType.IRON_GOLEM);
        d.setEntity(entity);
        d.startDisguise();
    }
    
    public static MobDisguise applyPacketSkeleton(final Entity entity) {
        final MobDisguise d = new MobDisguise(EntityType.SKELETON);
        d.setEntity(entity);
        d.startDisguise();
        return d;
    }
    
    public static void sendPacket(final World w, final Packet pk) {
        for (final Player p : w.getPlayers()) {
            final EntityPlayer player = ((CraftPlayer)p).getHandle();
            player.playerConnection.sendPacket(pk);
        }
    }
    
    public static float randomVector() {
        return -0.1f + (float)(Math.random() * 0.2);
    }
    
    public static void createSphere(final Location loc) {
        new BukkitRunnable() {
            double phi = 0.0;
            
            public void run() {
                this.phi += 0.3141592653589793;
                for (double theta = 0.0; theta <= 6.283185307179586; theta += 0.09817477042468103) {
                    final double r = 1.5;
                    final double x = 1.5 * Math.cos(theta) * Math.sin(this.phi);
                    final double y = 1.5 * Math.cos(this.phi) + 1.5;
                    final double z = 1.5 * Math.sin(theta) * Math.sin(this.phi);
                    loc.add(x, y, z);
                    final Location p1 = loc.clone();
                    loc.subtract(x, y, z);
                    p1.getWorld().playEffect(p1, Effect.FLAME, 3);
                    if (this.phi > 12.566370614359172) {
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 0L);
    }
    
    public static final float getAngle(final Vector point1, final Vector point2) {
        final double dx = point2.getX() - point1.getX();
        final double dz = point2.getZ() - point1.getZ();
        float angle = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
        if (angle < 0.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static void setEyeLocation(final Entity entity, final Location location1, final Location location2) {
        entity.getLocation().setYaw(getAngle(new Vector(location1.getX(), 0.0, location1.getZ()), location2.toVector()));
    }
    
    private static double distanceSquared(final Vector from, final Vector to) {
        final double dx = to.getBlockX() - from.getBlockX();
        final double dz = to.getBlockZ() - from.getBlockZ();
        return dx * dx + dz * dz;
    }
    
    public static Vector calculateVelocityBlock(final Vector from, final Vector to, final int heightGain) {
        final double gravity = 0.115;
        final int endGain = to.getBlockY() - from.getBlockY();
        final double horizDist = Math.sqrt(distanceSquared(from, to));
        final int gain = heightGain;
        final double maxGain = (gain > endGain + gain) ? gain : ((double)(endGain + gain));
        final double a = -horizDist * horizDist / (4.0 * maxGain);
        final double b = horizDist;
        final double c = -endGain;
        final double slope = -b / (2.0 * a) - Math.sqrt(b * b - 4.0 * a * c) / (2.0 * a);
        final double vy = Math.sqrt(maxGain * 0.115);
        final double vh = vy / slope;
        final int dx = to.getBlockX() - from.getBlockX();
        final int dz = to.getBlockZ() - from.getBlockZ();
        final double mag = Math.sqrt((double)(dx * dx + dz * dz));
        final double dirx = dx / mag;
        final double dirz = dz / mag;
        final double vx = vh * dirx;
        final double vz = vh * dirz;
        return new Vector(vx, vy, vz);
    }
    
    public static Vector r(final Vector from, final Vector to, final int heightGain) {
        final double gravity = 0.3;
        final int endGain = to.getBlockY() - from.getBlockY();
        final double horizDist = Math.sqrt(distanceSquared(from, to));
        final int gain = heightGain;
        final double maxGain = (gain > endGain + gain) ? gain : ((double)(endGain + gain));
        final double a = -horizDist * horizDist / (4.0 * maxGain);
        final double b = horizDist;
        final double c = -endGain;
        final double slope = -b / (2.0 * a) - Math.sqrt(b * b - 4.0 * a * c) / (2.0 * a);
        final double vy = Math.sqrt(maxGain * 0.3);
        final double vh = vy / slope;
        final int dx = to.getBlockX() - from.getBlockX();
        final int dz = to.getBlockZ() - from.getBlockZ();
        final double mag = Math.sqrt((double)(dx * dx + dz * dz));
        final double dirx = dx / mag;
        final double dirz = dz / mag;
        final double vx = vh * dirx;
        final double vz = vh * dirz;
        return new Vector(vx, vy, vz);
    }
    
    public static int itemCount(final Player player, final String type) {
        int count = 0;
        final PlayerInventory inv = player.getInventory();
        for (final ItemStack is : inv.getContents()) {
            if (is != null && is.hasItemMeta() && is.getItemMeta().getDisplayName().contains((CharSequence)type)) {
                count += is.getAmount();
            }
        }
        return count;
    }
    
    public static void zero(final Entity killed) {
        if (!(killed instanceof LivingEntity)) {
            return;
        }
        final EntityEquipment ep = ((LivingEntity)killed).getEquipment();
        ep.setHelmet((ItemStack)null);
        ep.setChestplate((ItemStack)null);
        ep.setLeggings((ItemStack)null);
        ep.setBoots((ItemStack)null);
        ep.setItemInHand((ItemStack)null);
    }
    
    public static List<Block> pasteSchematicRep(final String schematic, final boolean withAir, final float lx, final float ly, final float lz, final World w) {
        final List<Block> lb = (List<Block>)new ArrayList();
        final File schem = new File((Object)SkyBlock.getPlugin().getDataFolder() + File.separator + "/schematics/" + schematic + ".schematic");
        final com.sk89q.worldedit.world.World world = (com.sk89q.worldedit.world.World)new BukkitWorld(w);
        final Closer closer = Closer.create();
        FileInputStream fis = null;
        try {
            fis = (FileInputStream)closer.register((Closeable)new FileInputStream(schem));
        }
        catch (final FileNotFoundException e3) {
            e3.printStackTrace();
        }
        final BufferedInputStream bis = (BufferedInputStream)closer.register((Closeable)new BufferedInputStream((InputStream)fis));
        ClipboardReader reader = null;
        try {
            reader = ClipboardFormat.SCHEMATIC.getReader((InputStream)bis);
        }
        catch (final IOException e4) {
            e4.printStackTrace();
        }
        Clipboard clipboard = null;
        try {
            clipboard = reader.read(world.getWorldData());
        }
        catch (final IOException e5) {
            e5.printStackTrace();
        }
        Location minYLoc = null;
        int minY = Integer.MAX_VALUE;
        for (int x = 0; x < clipboard.getRegion().getWidth(); ++x) {
            for (int y = 0; y < clipboard.getRegion().getHeight(); ++y) {
                for (int z = 0; z < clipboard.getRegion().getLength(); ++z) {
                    final com.sk89q.worldedit.Vector minimumPoint = clipboard.getMinimumPoint();
                    final com.sk89q.worldedit.Vector clipboardLoc = new com.sk89q.worldedit.Vector(minimumPoint.getBlockX() + x, minimumPoint.getBlockY() + y, minimumPoint.getBlockZ() + z);
                    final BaseBlock baseBlock = clipboard.getBlock(clipboardLoc);
                    if (baseBlock.getId() != 0) {
                        final Location newLocation = new Location(w, (double)lx, (double)ly, (double)lz).add((double)x, (double)y, (double)z);
                        final com.sk89q.worldedit.Vector loc = new com.sk89q.worldedit.Vector(newLocation.getBlockX(), newLocation.getBlockY(), newLocation.getBlockZ());
                        try {
                            world.setBlock(loc, baseBlock);
                            if (y < minY && baseBlock.getId() != 165 && clipboard.getBlock(clipboardLoc.add(0, 1, 0)).getId() == 0 && newLocation.getBlock().getType().isSolid()) {
                                minY = y;
                                minYLoc = newLocation;
                            }
                            lb.add((Object)w.getBlockAt(newLocation));
                        }
                        catch (final WorldEditException e6) {
                            e6.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            closer.close();
        }
        catch (final IOException e7) {
            e7.printStackTrace();
        }
        return lb;
    }
    
    public static boolean pasteSchematic(final String schematic, final boolean withAir, final float x, final float y, final float z, final World w) {
        final Location location = new Location(w, (double)x, (double)y, (double)z);
        try {
            final com.sk89q.worldedit.Vector pasteLocation = new com.sk89q.worldedit.Vector(location.getX(), location.getY(), location.getZ());
            final BukkitWorld pasteWorld = new BukkitWorld(location.getWorld());
            final WorldData pasteWorldData = pasteWorld.getWorldData();
            final File schem = new File((Object)SkyBlock.getPlugin().getDataFolder() + File.separator + "/schematics/" + schematic + ".schematic");
            Clipboard clipboard;
            try {
                clipboard = ClipboardFormat.SCHEMATIC.getReader((InputStream)new FileInputStream(schem)).read(pasteWorldData);
            }
            catch (final FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            catch (final IOException e2) {
                e2.printStackTrace();
                return false;
            }
            final ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard, pasteWorldData);
            final EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((LocalWorld)pasteWorld, -1);
            final Operation operation = clipboardHolder.createPaste((Extent)editSession, pasteWorldData).to(pasteLocation).ignoreAirBlocks(!withAir).build();
            Operations.complete(operation);
            return true;
        }
        catch (final WorldEditException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String formatTime(final int z) {
        final int seconds = z;
        final int p1 = seconds % 60;
        int p2 = seconds / 60;
        final int p3 = p2 % 60;
        p2 /= 60;
        String a = String.valueOf(p1);
        String b = String.valueOf(p2);
        String c = String.valueOf(p3);
        if (p1 < 10) {
            a = "0" + p1;
        }
        if (p2 < 10) {
            b = "0" + p2;
        }
        if (p3 < 10) {
            c = "0" + p3;
        }
        if (p2 == 0) {
            return c + "m " + a + "s";
        }
        return b + "h " + c + "m " + a + "s";
    }
    
    public static int runningFloors() {
        int i = 0;
        for (final World w : Bukkit.getWorlds()) {
            if (w.getName().contains((CharSequence)"f6") && !w.getName().equals((Object)"f6")) {
                ++i;
            }
        }
        return i;
    }
    
    public static int rf_() {
        int i = 0;
        if (Sputnik.RunThisSession.containsKey((Object)Bukkit.getServer())) {
            i = (int)Sputnik.RunThisSession.get((Object)Bukkit.getServer());
        }
        else {
            Sputnik.RunThisSession.put((Object)Bukkit.getServer(), (Object)0);
        }
        return i;
    }
    
    public static void startRoom(final Player player) {
        final ArrayList<UUID> plist = (ArrayList<UUID>)new ArrayList();
        plist.add((Object)player.getUniqueId());
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.GREEN + "Entering The Catacombs Demo - Floor 6!"), 10L);
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.GRAY + "Preparing the boss for you, please wait..."), 20L);
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.GRAY + "Hooking up request, and sending you to that world..."), 30L);
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.GRAY + " "), 40L);
        SUtil.delay(() -> SadanBossManager.startFloor(plist), 50L);
        SUtil.delay(() -> {
            final Boolean b = (Boolean)BossMenu.ableToJoin.put((Object)player, (Object)true);
        }, 60L);
    }
    
    public static Integer dmgc(final int damage, final Player p, final Entity e) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
        if (statistics == null) {
            return 0;
        }
        final double defense = statistics.getDefense().addAll();
        final int dmglater = (int)Math.round(damage - damage * (defense / (defense + 100.0)));
        User.getUser(p.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, e);
        p.damage(1.0E-6, (Entity)null);
        return dmglater;
    }
    
    public static double calculateMagicDamage(final Entity entity, final Player player, int baseMagicDmg, final double scale) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
        final User user = User.getUser(player.getUniqueId());
        final PlayerInventory inv = player.getInventory();
        final SItem helmet = SItem.find(inv.getHelmet());
        if (helmet != null) {
            if (helmet.getType() == SMaterial.DARK_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 25 / 100;
            }
            else if (helmet.getType() == SMaterial.SHADOW_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 35 / 100;
            }
            else if (helmet.getType() == SMaterial.WITHER_GOGGLES) {
                baseMagicDmg += baseMagicDmg * 45 / 100;
            }
        }
        final Pet pet = user.getActivePetClass();
        final Pet.PetItem active1 = user.getActivePet();
        if (active1 != null && pet.getDisplayName().equals((Object)"Magicivy")) {
            final Pet.PetItem active2 = user.getActivePet();
            final int level = Pet.getLevel(active2.getXp(), active2.getRarity());
            baseMagicDmg += (int)(baseMagicDmg * (level / 100.0f));
        }
        double baseDamage = baseMagicDmg * (manaPool / 100 * scale + 1.0);
        if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity)) {
            int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity);
            if (defensepercent > 100) {
                defensepercent = 100;
            }
            baseDamage -= baseDamage * defensepercent / 100.0;
        }
        return baseDamage;
    }
    
    public static PlayerUtils.DamageResult calculateNormalDamage(final Entity entity, final Player player, final int baseDamage) {
        final ItemStack weapon = player.getInventory().getItemInHand();
        final PlayerUtils.DamageResult result = PlayerUtils.getDamageDealt(weapon, player, entity, player instanceof Arrow);
        return result;
    }
    
    public static void playHitShieldParticle(final Entity e) {
        new BukkitRunnable() {
            int loc = 0;
            
            public void run() {
                final ArrayList<Location> locs = getCircle(e.getLocation(), 0.5, 40);
                e.getWorld().spigot().playEffect((Location)locs.get(this.loc), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                ++this.loc;
                if (this.loc == 40) {
                    this.loc = 0;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void playSoulWell(final Entity e, final String id) {
        final HashMap<Integer, Integer> S = (HashMap<Integer, Integer>)new HashMap();
        Sputnik.MAP_PARTICLE_1.put((Object)e, (Object)e.getLocation());
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)SkyBlock.getPlugin(), (Runnable)new Runnable() {
            final Random random = new Random();
            
            void startSoulWell() {
                final int num = this.random.nextInt(Integer.MAX_VALUE);
                S.put((Object)num, (Object)Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)SkyBlock.getPlugin(), (Runnable)new Runnable() {
                    final Location height = (Location)Sputnik.MAP_PARTICLE_1.get((Object)e);
                    int loc = 0;
                    int lifeSpan = 0;
                    
                    public void run() {
                        final ArrayList<Location> locs = getCircle(this.height, 0.5, 40);
                        ((Location)Sputnik.MAP_PARTICLE_1.get((Object)e)).getWorld().spigot().playEffect((Location)locs.get(this.loc), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        ++this.loc;
                        ++this.lifeSpan;
                        this.height.add(0.0, 0.045, 0.0);
                        if (this.loc == 40) {
                            this.loc = 0;
                        }
                        if (this.lifeSpan == 40) {
                            Bukkit.getScheduler().cancelTask((int)S.get((Object)num));
                            S.remove((Object)num);
                        }
                    }
                }, 0L, 1L));
            }
            
            public void run() {
                this.startSoulWell();
            }
        }, 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                Sputnik.MAP_PARTICLE_1.put((Object)e, (Object)e.getLocation());
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    private static ArrayList<Location> getCircle(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = (ArrayList<Location>)new ArrayList();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() + radius * Math.cos(angle);
            final double z = center.getZ() + radius * Math.sin(angle);
            locations.add((Object)new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    
    private static ArrayList<Location> getCircleReverse(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = (ArrayList<Location>)new ArrayList();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() - radius * Math.cos(angle);
            final double z = center.getZ() - radius * Math.sin(angle);
            locations.add((Object)new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    
    public static void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0.0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player);
            amount -= expToLevel;
            if (amount >= 0) {
                player.giveExp(expToLevel);
            }
            else {
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }
    
    private static int getExpAtLevel(final Player player) {
        return getExpAtLevel(player.getLevel());
    }
    
    public static int getExpAtLevel(final int level) {
        if (level > 29) {
            return 62 + (level - 30) * 7;
        }
        if (level > 15) {
            return 17 + (level - 15) * 3;
        }
        return 17;
    }
    
    public static int getExpToLevel(final int level) {
        int currentLevel = 0;
        int exp = 0;
        while (currentLevel < level) {
            exp += getExpAtLevel(currentLevel);
            ++currentLevel;
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }
    
    public static int getTotalExperience(final Player player) {
        int exp = Math.round(getExpAtLevel(player) * player.getExp());
        for (int currentLevel = player.getLevel(); currentLevel > 0; --currentLevel, exp += getExpAtLevel(currentLevel)) {}
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }
    
    public static int getExpUntilNextLevel(final Player player) {
        final int exp = Math.round(getExpAtLevel(player) * player.getExp());
        final int nextLevel = player.getLevel();
        return getExpAtLevel(nextLevel) - exp;
    }
    
    public static void tradeIntitize(final Player target, final Player p) {
        if (p == target) {
            p.sendMessage(trans("&cYou cannot trade with yourself!"));
            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
            return;
        }
        final UUID uuid = UUID.randomUUID();
        if (!target.isOnline()) {
            p.sendMessage(trans("&cCannot find player with that name, maybe they've gone offline?"));
            p.playSound(p.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
            return;
        }
        if (!p.getWorld().equals(target.getWorld())) {
            p.sendMessage((Object)ChatColor.RED + "You can't trade with that player!");
            p.playSound(p.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
            return;
        }
        if (p.getLocation().distance(target.getLocation()) > 5.0) {
            p.sendMessage((Object)ChatColor.RED + "You are too far away to trade with that player!");
            p.playSound(p.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
            return;
        }
        if (TradeUtil.hasRequest(target, p)) {
            p.sendMessage((Object)ChatColor.RED + "Woah there! You already have an /trade request");
            p.playSound(p.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
            return;
        }
        new BukkitRunnable() {
            int t = 0;
            
            public void run() {
                ++this.t;
                if (TradeUtil.isTrading(p) || TradeUtil.isTrading(target)) {
                    this.cancel();
                    return;
                }
                if (this.t >= 200 && !TradeUtil.hasRequest(p, target) && !TradeUtil.isTrading(p) && !TradeUtil.isTrading(target)) {
                    this.cancel();
                    p.sendMessage(Sputnik.trans("&cThe /trade request to " + target.getDisplayName() + " &cexpired!"));
                    target.sendMessage(Sputnik.trans("&cThe /trade request from " + p.getDisplayName() + " &cexpired!"));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    target.playSound(target.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    TradeUtil.resetTrade(p);
                    TradeUtil.resetTrade(target);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        if (TradeUtil.hasRequest(p, target)) {
            p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
            target.playSound(target.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
            new TradeMenu(p, target, uuid).open();
            return;
        }
        p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
        target.playSound(target.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
        p.sendMessage(trans("&aYou have sent a trade request to &b" + target.getDisplayName() + "&a."));
        final TextComponent message = new TextComponent(trans("&b" + p.getName() + " &ahas sent you a trade request. &bClick here &ato accept."));
        final UUID accessKey = UUID.randomUUID();
        AccessTimedCommand.KEYS.add((Object)accessKey);
        SUtil.delay(() -> AccessTimedCommand.KEYS.remove((Object)accessKey), 200L);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (BaseComponent[])new TextComponent[] { new TextComponent((Object)ChatColor.GOLD + "Click to trade!") }));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/attc " + (Object)accessKey + " " + p.getName()));
        target.spigot().sendMessage((BaseComponent)message);
        TradeUtil.requestTrade(p, target);
    }
    
    public static ArmorStand spawnStaticDialougeBox(final Entity e, final Location l) {
        final ArmorStand as = (ArmorStand)e.getWorld().spawn(l, (Class)ArmorStand.class);
        as.setVisible(false);
        as.setMarker(true);
        as.setCustomNameVisible(false);
        as.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    SUtil.delay(() -> {
                        final Object val$as = as;
                        as.remove();
                    }, 0L);
                    this.cancel();
                    return;
                }
                if (as.isDead()) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        return as;
    }
    
    public static ArmorStand spawnStaticDialougeBox(final Entity e, final double yoffset) {
        final ArmorStand as = (ArmorStand)e.getWorld().spawn(e.getLocation().add(0.0, yoffset, 0.0), (Class)ArmorStand.class);
        as.setVisible(false);
        as.setMarker(true);
        as.setCustomNameVisible(false);
        as.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    SUtil.delay(() -> {
                        final Object val$as = as;
                        as.remove();
                    }, 0L);
                    this.cancel();
                    return;
                }
                if (as.isDead()) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        return as;
    }
    
    public static ArmorStand spawnDialougeBox(final Entity e, final double yoffset) {
        final ArmorStand as = (ArmorStand)e.getWorld().spawn(e.getLocation().add(0.0, yoffset, 0.0), (Class)ArmorStand.class);
        as.setVisible(false);
        as.setMarker(true);
        as.setCustomNameVisible(false);
        as.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    SUtil.delay(() -> {
                        final Object val$as = as;
                        as.remove();
                    }, 20L);
                    this.cancel();
                    return;
                }
                if (as.isDead()) {
                    this.cancel();
                    return;
                }
                as.teleport(e.getLocation().add(0.0, yoffset, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        return as;
    }
    
    public static String createStarStringFrom(final SItem sitem) {
        final int amount = sitem.getDataInt("itemStar");
        if (amount <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        final String star = "&6\u272a";
        final String mstar = "&c\u272a";
        int iam = 0;
        iam = Math.min(5, amount);
        int iams = 0;
        iams = Math.min(10, amount);
        if (amount <= 5) {
            for (int i = 0; i < 5; ++i) {
                sb.append("&6\u272a");
                if (i >= iam - 1) {
                    break;
                }
            }
        }
        else {
            iams = amount - 5;
            for (int i = 0; i < 5; ++i) {
                if (iams > i) {
                    sb.append("&c\u272a");
                }
                else {
                    sb.append("&6\u272a");
                }
            }
        }
        return trans(sb.toString());
    }
    
    public static String createStarStringFromAmount(final int amount) {
        if (amount <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        final String star = "&6\u272a";
        final String mstar = "&c\u272a";
        int iam = 0;
        iam = Math.min(5, amount);
        int iams = 0;
        iams = Math.min(10, amount);
        if (amount <= 5) {
            for (int i = 0; i < 5; ++i) {
                sb.append("&6\u272a");
                if (i >= iam - 1) {
                    break;
                }
            }
        }
        else {
            iams = amount - 5;
            for (int i = 0; i < 5; ++i) {
                if (iams > i) {
                    sb.append("&c\u272a");
                }
                else {
                    sb.append("&6\u272a");
                }
            }
        }
        return trans(sb.toString());
    }
    
    public static Object[] calculateDamage(final Player player, final Player damager, final ItemStack weapon, final LivingEntity damaged, final boolean isBow) {
        final PlayerUtils.DamageResult result = PlayerUtils.getDamageDealt(weapon, player, (Entity)damaged, isBow);
        float displayDmg = new AtomicDouble(result.getFinalDamage()).floatValue();
        if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)damaged)) {
            int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)damaged);
            if (defensepercent > 100) {
                defensepercent = 100;
            }
            displayDmg -= displayDmg * defensepercent / 100.0f;
        }
        return new Object[] { new AtomicDouble(result.getFinalDamage()).floatValue(), result.didCritDamage(), displayDmg };
    }
    
    public static double calMagicDamage(final Player p, final int baseDamage, final double mult) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
        final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
        int baseMagicDmg1 = baseDamage;
        baseMagicDmg1 += (int)(baseMagicDmg1 * (statistics.getAbilityDamage().addAll() / 100.0));
        final PlayerInventory inv = p.getInventory();
        final SItem helmet = SItem.find(inv.getHelmet());
        if (helmet != null) {
            if (helmet.getType() == SMaterial.DARK_GOGGLES) {
                baseMagicDmg1 += baseMagicDmg1 * 25 / 100;
            }
            else if (helmet.getType() == SMaterial.SHADOW_GOGGLES) {
                baseMagicDmg1 += baseMagicDmg1 * 35 / 100;
            }
            else if (helmet.getType() == SMaterial.WITHER_GOGGLES) {
                baseMagicDmg1 += baseMagicDmg1 * 45 / 100;
            }
        }
        return baseMagicDmg1 * (manaPool / 100 * mult + 1.0);
    }
    
    public static void sendEatingAnimation(final LivingEntity e) {
        final net.minecraft.server.v1_8_R3.ItemStack itemstack = CraftItemStack.asNMSCopy(e.getEquipment().getItemInHand());
        final Location l = e.getLocation();
        final float pitch = l.getPitch();
        final float yaw = l.getYaw();
        final Random random = new Random();
        if (itemstack.m() == EnumAnimation.EAT) {
            for (int j = 0; j < 5; ++j) {
                Vec3D vec3d = new Vec3D((random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                vec3d = vec3d.a(-pitch * 3.141593f / 180.0f);
                vec3d = vec3d.b(-yaw * 3.141593f / 180.0f);
                final double d0 = -random.nextFloat() * 0.6 - 0.3;
                Vec3D vec3d2 = new Vec3D((random.nextFloat() - 0.5) * 0.3, d0, 0.6);
                vec3d2 = vec3d2.a(-pitch * 3.141593f / 180.0f);
                vec3d2 = vec3d2.b(-yaw * 3.141593f / 180.0f);
                vec3d2 = vec3d2.add(l.getX(), l.getY() + 1.690000057220459, l.getZ());
                sendPacket(e.getWorld(), (Packet)new PacketPlayOutWorldParticles(EnumParticle.ITEM_CRACK, true, (float)vec3d2.a, (float)vec3d2.b, (float)vec3d2.c, (float)vec3d.a, (float)((float)vec3d.b + 0.05), (float)vec3d.c, 0.75f, 0, new int[] { Item.getId(itemstack.getItem()), itemstack.getData() }));
            }
            e.getWorld().playSound(l, Sound.EAT, 0.5f + 0.5f * random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    public static String roundComma(final float i) {
        return new BigDecimal((double)i).setScale(1, RoundingMode.HALF_EVEN).toPlainString();
    }
    
    public static ItemChest makeChestItemLoot(final Location loc, final ItemStack type, final boolean locked, final byte rot) {
        loc.getBlock().setType(Material.CHEST);
        final Block bl = loc.getBlock();
        bl.setData(rot);
        final ItemChest bc = new ItemChest(type, bl, rot);
        bc.setLocked(locked);
        return bc;
    }
    
    public static BlessingChest makeChestBlessings(final Location loc, final Blessings type, final boolean locked, final byte rot) {
        loc.getBlock().setType(Material.CHEST);
        final Block bl = loc.getBlock();
        bl.setData(rot);
        final BlessingChest bc = new BlessingChest(type, bl, rot);
        bc.setLocked(locked);
        return bc;
    }
    
    public static boolean tpAbilUsable(final Player p) {
        return !p.getWorld().getName().equals((Object)"arena");
    }
    
    public static void teleport(final Player p, final Location loc) {
        p.teleport(loc);
    }
    
    public static PositionSongPlayer playNativeSound(final String filename, final int radius, final int volume, final boolean loop, final Location loc) {
        final Song song = NBSDecoder.parse(new File((Object)SkyBlock.getPlugin().getDataFolder() + File.separator + "/songs/" + filename + ".nbs"));
        final PositionSongPlayer esp = new PositionSongPlayer(song);
        esp.setDistance(radius);
        esp.setVolume((byte)100);
        esp.setLoop(loop);
        esp.setTargetLocation(loc);
        loc.getWorld().getPlayers().forEach(esp::addPlayer);
        esp.setPlaying(true);
        new BukkitRunnable() {
            public void run() {
                if (!esp.isPlaying()) {
                    this.cancel();
                    return;
                }
                for (final Player p : loc.getWorld().getPlayers()) {
                    if (!esp.getPlayerUUIDs().contains((Object)p.getUniqueId())) {
                        esp.addPlayer(p);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        return esp;
    }
    
    public static void playSound(final File filename, final int radius, final int volume, final boolean loop, final Player p, final Location loc) {
        final Song song = NBSDecoder.parse(filename);
        final PositionSongPlayer esp = new PositionSongPlayer(song);
        esp.setDistance(radius);
        esp.setVolume((byte)100);
        esp.setLoop(loop);
        esp.setTargetLocation(loc);
        p.getLocation().getWorld().getPlayers().forEach(pl -> esp.addPlayer(pl));
        esp.setPlaying(true);
        User.getUser(p.getUniqueId()).setPlayingSong(true);
        new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline()) {
                    esp.destroy();
                    this.cancel();
                    return;
                }
                if (!User.getUser(p.getUniqueId()).isPlayingSong()) {
                    esp.destroy();
                    this.cancel();
                    return;
                }
                for (final Player p : loc.getWorld().getPlayers()) {
                    if (!esp.getPlayerUUIDs().contains((Object)p.getUniqueId())) {
                        esp.addPlayer(p);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    static {
        RunThisSession = (Map)new HashMap();
        MidasStaff = (Map)new HashMap();
        MidasStaffDmg = (Map)new HashMap();
        CoinsTakenOut = (Map)new HashMap();
        IsInsideTheBeam = (Map)new HashMap();
        CooldownAbs = (Map)new HashMap();
        HaveDMGReduction = (Map)new HashMap();
        MAP_PARTICLE_1 = (Map)new HashMap();
    }
}
