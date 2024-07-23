package net.hypixel.skyblock.entity.dungeons.watcher;

import java.util.HashMap;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.Sound;
import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import com.mojang.authlib.properties.Property;
import java.util.Base64;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.inventory.meta.SkullMeta;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.Vector;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import org.bukkit.Effect;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import java.util.Collections;
import java.util.Collection;
import java.util.Arrays;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.Map;
import org.bukkit.event.Listener;

public class Watcher implements Listener
{
    private static final Map<World, Watcher> WATCHER_CACHE;
    private final World arg0;
    private boolean a;
    private final Location arg1;
    private final Location arg2;
    public Location watcher;
    public ArmorStand ewatcher;
    public ArmorStand econv;
    public ArmorStand wfe2;
    public ArmorStand wfe1;
    public float floorY;
    public int mobSpawned;
    public int perRunMS;
    private boolean wfe1_shoot;
    private boolean wfe2_shoot;
    public boolean isResting;
    private boolean firstRun;
    public int currentMobsCount;
    public boolean welcomeParticles;
    private final List<HeadsOnWall> how;
    private final List<String> spawnedMob;
    public List<Location> P1Heads;
    public String[] mobSummonConvs;
    public String[] mobDeathConvs;
    public String[] sneakyPeaky;
    public String[] watcherAttack;
    
    public Watcher(final Location pos1, final Location pos2, final int floorY) {
        this.currentMobsCount = 15;
        this.how = (List<HeadsOnWall>)new ArrayList();
        this.spawnedMob = (List<String>)new ArrayList();
        this.P1Heads = (List<Location>)new ArrayList();
        this.mobSummonConvs = new String[] { "You'll do", "Let's see how you can handle this", "Go, fight!", "Go and live again!", "Hmm... This one!", "Oops. Wasn't meant to revive that one.", "This one looks like a fighter." };
        this.mobDeathConvs = new String[] { "That one was weak anyway.", "I'm Impressed.", "Not bad.", "Aw, I liked that one.", "Very nice." };
        this.sneakyPeaky = new String[] { "We're always watching. Come down from there!", "Don't try to sneak anything past my Watchful Eyes. They see you up there!", "My Watchful Eyes see you up there! Come down and fight!" };
        this.watcherAttack = new String[] { "I am not your enemy", "Stop Attacking me", "Don't make me zap you", "Ouch, just kidding..." };
        this.arg0 = pos1.getWorld();
        this.arg1 = pos1;
        this.arg2 = pos2;
        this.floorY = (float)floorY;
        this.a = false;
    }
    
    public static Watcher getWatcher(final World world) {
        if (Watcher.WATCHER_CACHE.containsKey((Object)world)) {
            return (Watcher)Watcher.WATCHER_CACHE.get((Object)world);
        }
        return null;
    }
    
    public void intitize() {
        final Cuboid cb = new Cuboid(this.arg0, this.arg1.getBlockX(), this.arg1.getBlockY(), this.arg1.getBlockZ(), this.arg2.getBlockX(), this.arg2.getBlockY(), this.arg2.getBlockZ());
        for (final Block b : cb.getBlocks()) {
            if (Material.BEACON == b.getType()) {
                this.watcher = b.getLocation().add(0.5, 0.0, 0.5);
            }
            else {
                if (Material.WOOL != b.getType()) {
                    continue;
                }
                if (4 != b.getData()) {
                    continue;
                }
                this.P1Heads.add((Object)b.getLocation().add(0.5, 0.0, 0.5));
            }
        }
        this.apt();
        Watcher.WATCHER_CACHE.put((Object)this.arg0, (Object)this);
        this.a = true;
        if (this.isIntitized()) {
            int i = 0;
            for (final Location loc : this.P1Heads) {
                this.placeHead(loc, i);
                ++i;
            }
        }
        this.spawnWatcher(this.watcher);
        final GlobalBossBar bb = new GlobalBossBar(trans("&c&lThe Watcher"), this.watcher.getWorld());
        for (final Player p : this.watcher.getWorld().getPlayers()) {
            bb.addPlayer(p);
        }
        bb.setProgress(this.currentMobsCount / 15);
        new BukkitRunnable() {
            public void run() {
                if (Watcher.this.ewatcher.isDead()) {
                    final List<Player> plist = (List<Player>)new ArrayList();
                    for (final Player p : bb.players) {
                        plist.add((Object)p);
                    }
                    plist.forEach(pl -> {
                        final Object val$bb = bb;
                        bb.removePlayer(pl);
                    });
                    bb.setProgress(0.0);
                    bb.cancel();
                    this.cancel();
                    return;
                }
                if (0 < Watcher.this.currentMobsCount) {
                    bb.setProgress(Watcher.this.currentMobsCount / 15.0);
                }
                else {
                    bb.setProgress(1.0E-5);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static double random(final double min, final double max) {
        return Math.random() * (max - min) + min;
    }
    
    private void apt() {
        for (final EnumWatcherType ew : EnumWatcherType.values()) {
            if (ew != EnumWatcherType.PLAYER) {
                this.how.add((Object)new HeadsOnWall(ew));
            }
        }
        for (final Player p : this.arg0.getPlayers()) {
            final HeadsOnWall a = new HeadsOnWall(EnumWatcherType.PLAYER);
            a.skullTexture = p.getName();
            a.arg0 = true;
            this.how.add((Object)a);
        }
        final EnumWatcherType[] rp = EnumWatcherType.values();
        final List<EnumWatcherType> ewt = (List<EnumWatcherType>)new ArrayList((Collection)Arrays.asList((Object[])rp));
        ewt.removeIf(r -> EnumWatcherType.BONZO == r || EnumWatcherType.LIVID == r || EnumWatcherType.PLAYER == r);
        Collections.shuffle((List)ewt);
        for (final EnumWatcherType ew : ewt) {
            this.how.add((Object)new HeadsOnWall(ew));
            if (30 == this.how.size()) {
                break;
            }
        }
        Collections.shuffle((List)this.how);
    }
    
    public void cleanUp() {
        Watcher.WATCHER_CACHE.remove((Object)this.arg0);
    }
    
    public boolean isIntitized() {
        return this.a;
    }
    
    public static float getYaw(final Location loc) {
        final Location newA = loc.getBlock().getLocation().add(0.5, 0.0, 0.5);
        newA.add(0.0, 1.7, 0.0);
        int rot = 0;
        for (int i = 0; 4 > i; ++i) {
            rot += 90;
            newA.setYaw((float)rot);
            final Location newLoc = newA.clone().add(newA.getDirection().normalize().multiply(2));
            if (Material.AIR == newLoc.getBlock().getType()) {
                return (float)rot;
            }
        }
        return 0.0f;
    }
    
    public void placeHead(final Location l, int index) {
        index = Math.min(29, index);
        l.getBlock().setType(Material.AIR);
        final Location sloc = l.add(0.0, -1.25, 0.0);
        sloc.setYaw(getYaw(sloc));
        final ArmorStand stand = (ArmorStand)l.getWorld().spawn(sloc, (Class)ArmorStand.class);
        stand.setMetadata("WATCHER_ENTITY", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)0));
        stand.setMetadata("TYPE", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)((HeadsOnWall)this.how.get(index)).stype));
        stand.setCustomNameVisible(false);
        if (!((HeadsOnWall)this.how.get(index)).arg0) {
            stand.getEquipment().setHelmet(getSkull(((HeadsOnWall)this.how.get(index)).skullTexture));
        }
        else {
            stand.getEquipment().setHelmet(getSkullStack(((HeadsOnWall)this.how.get(index)).skullTexture));
        }
        stand.setGravity(false);
        stand.setVisible(false);
    }
    
    public void returnWatcher(final Entity e) {
        final Watcher w = getWatcher(e.getWorld());
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (e.getWorld().getNearbyEntities(w.watcher.clone().add(0.0, -1.5, 0.0), 0.1, 0.1, 0.1).contains((Object)e)) {
                    Watcher.this.isResting = true;
                    if (15 > Watcher.this.mobSpawned) {
                        SUtil.delay(() -> {
                            final Object val$e = e;
                            if (!e.isDead()) {
                                if (15 > Watcher.this.mobSpawned) {
                                    Watcher.this.moveWatcher(e, false);
                                }
                            }
                        }, 100L);
                    }
                    if (2 == Watcher.random(1, 7)) {
                        Watcher.this.sd(Watcher.this.mobSummonConvs[Watcher.random(0, Watcher.this.mobSummonConvs.length - 1)], 10, 50, true);
                    }
                    Watcher.this.perRunMS = 0;
                    this.cancel();
                    return;
                }
                final Location r = e.getLocation().setDirection(w.watcher.clone().add(0.0, -1.5, 0.0).toVector().subtract(e.getLocation().toVector()));
                e.teleport(r);
                Watcher.sendHeadRotation(e, r.getYaw(), r.getPitch());
                double x = 0.0;
                final double y = 0.0;
                final double z = 0.0;
                x = Math.toRadians((double)r.getPitch());
                ((ArmorStand)e).setHeadPose(new EulerAngle(x, 0.0, 0.0));
                e.teleport(e.getLocation().add(e.getLocation().getDirection().multiply(0.5)));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public ArmorStand findArmorStand(final Location l) {
        for (final Entity e : l.getWorld().getNearbyEntities(l, 0.10000000149011612, 0.10000000149011612, 0.10000000149011612)) {
            if (e instanceof ArmorStand && e.hasMetadata("WATCHER_ENTITY")) {
                return (ArmorStand)e;
            }
        }
        return null;
    }
    
    public void moveWatcher(final Entity e, final boolean firstMove) {
        this.firstRun = firstMove;
        this.isResting = false;
        final Watcher w = getWatcher(e.getWorld());
        final int inx = random(0, w.P1Heads.size() - 1);
        Location tpTo = null;
        if (this.firstRun) {
            this.firstRun = false;
            final List<Location> removedLoc = (List<Location>)new ArrayList();
            for (final Location l : w.P1Heads) {
                if (((MetadataValue)this.findArmorStand(l).getMetadata("TYPE").get(0)).asString().contains((CharSequence)"TEAMMATE")) {
                    removedLoc.add((Object)l);
                }
            }
            w.P1Heads.removeAll((Collection)removedLoc);
            for (final Location l : w.P1Heads) {
                final String lb = ((MetadataValue)this.findArmorStand(l).getMetadata("TYPE").get(0)).asString();
                if (lb.contains((CharSequence)"BONZO") || lb.contains((CharSequence)"LIVID")) {
                    tpTo = l;
                    w.P1Heads.remove((Object)l);
                    break;
                }
            }
        }
        else {
            tpTo = (Location)w.P1Heads.get(inx);
            w.P1Heads.remove(inx);
            while (this.spawnedMob.contains((Object)((MetadataValue)this.findArmorStand(tpTo).getMetadata("TYPE").get(0)).asString())) {
                final int inxe = random(0, w.P1Heads.size() - 1);
                tpTo = (Location)w.P1Heads.get(inxe);
                w.P1Heads.remove(inxe);
            }
        }
        this.spawnedMob.add((Object)((MetadataValue)this.findArmorStand(tpTo).getMetadata("TYPE").get(0)).asString());
        final Location tpToC = tpTo;
        if (1 > this.perRunMS) {
            this.sd(this.mobSummonConvs[random(0, this.mobSummonConvs.length - 1)], 10, 50, true);
        }
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (e.getWorld().getNearbyEntities(tpToC, 0.6, 0.6, 0.6).contains((Object)e)) {
                    final ArmorStand st = Watcher.this.findArmorStand(tpToC);
                    st.getEquipment().setHelmet((ItemStack)null);
                    st.setCustomNameVisible(false);
                    Watcher.this.playHeadSpawning((Entity)st);
                    SUtil.delay(() -> {
                        final Object val$e = e;
                        if (15 <= Watcher.this.mobSpawned) {
                            Watcher.this.sd("That will be enough for now.", 20, 50, true);
                        }
                        if (3 <= Watcher.this.perRunMS || 15 <= Watcher.this.mobSpawned) {
                            Watcher.this.returnWatcher(e);
                        }
                        else {
                            Watcher.this.moveWatcher(e, false);
                        }
                    }, 15L);
                    final Watcher this$3;
                    final Watcher this$0 = this$3 = Watcher.this;
                    ++this$3.perRunMS;
                    final Watcher this$4;
                    final Watcher this$2 = this$4 = Watcher.this;
                    ++this$4.mobSpawned;
                    if (1 == Watcher.random(1, 3)) {
                        Watcher.this.sd(Watcher.this.mobSummonConvs[Watcher.random(0, Watcher.this.mobSummonConvs.length - 1)], 10, 50, true);
                    }
                    this.cancel();
                    return;
                }
                final Location r = e.getLocation().setDirection(tpToC.toVector().subtract(e.getLocation().toVector()));
                e.teleport(r);
                Watcher.sendHeadRotation(e, r.getYaw(), r.getPitch());
                double x = 0.0;
                final double y = 0.0;
                final double z = 0.0;
                x = Math.toRadians((double)r.getPitch());
                ((ArmorStand)e).setHeadPose(new EulerAngle(x, 0.0, 0.0));
                e.teleport(e.getLocation().add(e.getLocation().getDirection().multiply(0.5)));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void sendHeadRotation(final Entity e, final float yaw, final float pitch) {
        final net.minecraft.server.v1_8_R3.Entity pl = (net.minecraft.server.v1_8_R3.Entity)((CraftArmorStand)e).getHandle();
        pl.setLocation(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), yaw, pitch);
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(pl);
        for (final Player p : e.getWorld().getPlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static int random(int min, int max) {
        if (0 > min) {
            min = 0;
        }
        if (0 > max) {
            max = 0;
        }
        return new Random().nextInt(max - min + 1) + min;
    }
    
    public ArmorStand spawnWatchfulEyes(final Entity e) {
        final Location l1 = e.getLocation().clone().add(e.getLocation().getDirection().normalize().multiply(-1.5));
        final float angle1 = l1.getYaw() / 60.0f;
        final Location loc_1 = l1.add(Math.cos((double)angle1), 0.0, Math.sin((double)angle1));
        final Location loc = e.getLocation();
        final ArmorStand stand = (ArmorStand)loc.getWorld().spawn(loc_1.add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
        stand.setCustomName(trans("&3&lWatchful Eye"));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.getEquipment().setHelmet(getSkull("37cc76e7af29f5f3fbfd6ece794160811eff96f753459fa61d7ad176a064e3c5"));
        stand.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 3L, 3L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                if (e instanceof ArmorStand && !Watcher.this.wfe1_shoot) {
                    final Location r = stand.getLocation().setDirection(e.getLocation().toVector().subtract(stand.getLocation().toVector()));
                    stand.teleport(r);
                    Watcher.sendHeadRotation((Entity)stand, r.getYaw(), r.getPitch());
                    double x = 0.0;
                    final double y = 0.0;
                    final double z = 0.0;
                    x = Math.toRadians((double)r.getPitch());
                    stand.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                }
                final Location l = e.getLocation().clone().add(0.0, 1.0, 0.0).add(e.getLocation().getDirection().normalize().multiply(-1.5));
                final float angle = l.getYaw() / 60.0f;
                final Location loc_ = l.add(Math.cos((double)angle), 0.0, Math.sin((double)angle));
                if (2.5 < stand.getLocation().distance(e.getLocation()) && !Watcher.this.wfe1_shoot) {
                    stand.teleport(stand.getLocation().add(loc_.toVector().subtract(stand.getLocation().toVector()).normalize().multiply(Math.min(9.0, Math.min(15.0, e.getLocation().distance(stand.getLocation())) / 4.0 + 0.2))));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
        return stand;
    }
    
    public ArmorStand spawnWatchfulEyes2(final Entity e) {
        final Location l1 = e.getLocation().clone().add(e.getLocation().getDirection().normalize().multiply(-1.5));
        final float angle1 = l1.getYaw() / 60.0f;
        final Location loc_1 = l1.subtract(Math.cos((double)angle1), 0.0, Math.sin((double)angle1));
        final Location loc = e.getLocation();
        final ArmorStand stand = (ArmorStand)loc.getWorld().spawn(loc_1.add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
        stand.setCustomName(trans("&3&lWatchful Eye"));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.getEquipment().setHelmet(getSkull("37cc76e7af29f5f3fbfd6ece794160811eff96f753459fa61d7ad176a064e3c5"));
        stand.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 21, 0, 0.2f, 0.1f, 0.2f, 0.01f, 1, 30);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 3L, 3L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                if (e instanceof ArmorStand && !Watcher.this.wfe2_shoot) {
                    final Location r = stand.getLocation().setDirection(e.getLocation().toVector().subtract(stand.getLocation().toVector()));
                    stand.teleport(r);
                    Watcher.sendHeadRotation((Entity)stand, r.getYaw(), r.getPitch());
                    double x = 0.0;
                    final double y = 0.0;
                    final double z = 0.0;
                    x = Math.toRadians((double)r.getPitch());
                    stand.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                }
                final Location l = e.getLocation().clone().add(0.0, 1.0, 0.0).add(e.getLocation().getDirection().normalize().multiply(-1.5));
                final float angle = l.getYaw() / 60.0f;
                final Location loc_ = l.subtract(Math.cos((double)angle), 0.0, Math.sin((double)angle));
                if (2.5 < stand.getLocation().distance(e.getLocation()) && !Watcher.this.wfe2_shoot) {
                    stand.teleport(stand.getLocation().add(loc_.toVector().subtract(stand.getLocation().toVector()).normalize().multiply(Math.min(9.0, Math.min(15.0, e.getLocation().distance(stand.getLocation())) / 4.0 + 0.2))));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
        return stand;
    }
    
    public void spawnWatcher(final Location loc) {
        final ArmorStand stand = (ArmorStand)loc.getWorld().spawn(loc, (Class)ArmorStand.class);
        this.welcomeParticles = true;
        (this.ewatcher = stand).setCustomName(trans("&e\ufd3e &c&lThe Watcher &e\ufd3f"));
        stand.setMetadata("WATCHER_M", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)0));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.getEquipment().setHelmet(getSkull("a137229538d619da70b5fd2ea06a560d9ce50b0e2f92413e6aa73d99f9d7a878"));
        stand.setGravity(false);
        final ArmorStand stand2 = (ArmorStand)loc.getWorld().spawn(loc.add(0.0, 2.4, 0.0), (Class)ArmorStand.class);
        (this.econv = stand2).setCustomNameVisible(false);
        stand2.setVisible(false);
        stand2.setMarker(true);
        stand2.setGravity(false);
        this.isResting = true;
        for (final Entity e : stand.getNearbyEntities(20.0, 20.0, 20.0)) {
            if (e instanceof Player) {
                final Location r = stand.getLocation().setDirection(e.getLocation().toVector().subtract(stand.getLocation().toVector()));
                stand.teleport(r);
                sendHeadRotation((Entity)stand, r.getYaw(), r.getPitch());
                double x = 0.0;
                final double y = 0.0;
                final double z = 0.0;
                x = Math.toRadians((double)r.getPitch());
                stand.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                break;
            }
        }
        this.wfe1 = this.spawnWatchfulEyes((Entity)stand);
        this.wfe2 = this.spawnWatchfulEyes2((Entity)stand);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity e : stand.getNearbyEntities(20.0, 20.0, 20.0)) {
                    if (e instanceof Player && !Watcher.this.welcomeParticles && e.getLocation().getY() >= Watcher.this.floorY + 6.0f) {
                        ArmorStand st = null;
                        final int rnd = Watcher.random(0, 1);
                        if (1 == rnd) {
                            st = Watcher.this.wfe1;
                        }
                        else {
                            st = Watcher.this.wfe2;
                        }
                        if (1 == rnd) {
                            Watcher.this.wfe1_shoot = true;
                        }
                        else {
                            Watcher.this.wfe2_shoot = true;
                        }
                        for (int i = 0; 2 > i; ++i) {
                            Watcher.drawLineforMovingPoints(st.getLocation().clone().add(0.0, 1.8, 0.0), e.getLocation().clone().add(0.0, 1.4, 0.0), 25.0, (Player)e, (Entity)stand);
                        }
                        Watcher.this.sd(Watcher.this.sneakyPeaky[Watcher.random(0, Watcher.this.sneakyPeaky.length - 1)], 0, 50, true);
                        User.getUser(e.getUniqueId()).damage(((Player)e).getMaxHealth() / 4.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)Watcher.this.ewatcher);
                        ((Player)e).damage(1.0E-5);
                        final ArmorStand s = st;
                        new BukkitRunnable() {
                            int i = 0;
                            
                            public void run() {
                                if (15 <= this.i) {
                                    if (1 == rnd) {
                                        Watcher.this.wfe1_shoot = false;
                                    }
                                    else {
                                        Watcher.this.wfe2_shoot = false;
                                    }
                                    this.cancel();
                                    return;
                                }
                                ++this.i;
                                final Location r = stand.getLocation().setDirection(e.getLocation().toVector().subtract(s.getLocation().toVector()));
                                stand.teleport(r);
                                Watcher.sendHeadRotation((Entity)s, r.getYaw(), r.getPitch());
                                double x = 0.0;
                                final double y = 0.0;
                                final double z = 0.0;
                                x = Math.toRadians((double)r.getPitch());
                                s.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                            }
                        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 30L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.CLOUD, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.2, 0.0), Effect.EXPLOSION, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                if (Watcher.this.isResting) {
                    for (final Entity e : stand.getNearbyEntities(20.0, 20.0, 20.0)) {
                        if (e instanceof Player) {
                            final Location r = stand.getLocation().setDirection(e.getLocation().toVector().subtract(stand.getLocation().toVector()));
                            stand.teleport(r);
                            Watcher.sendHeadRotation((Entity)stand, r.getYaw(), r.getPitch());
                            double x = 0.0;
                            final double y = 0.0;
                            final double z = 0.0;
                            x = Math.toRadians((double)r.getPitch());
                            stand.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                            break;
                        }
                    }
                }
                stand2.teleport(stand.getLocation().clone().add(0.0, 2.4, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (Watcher.this.ewatcher.isDead()) {
                    this.cancel();
                    return;
                }
                if (0 >= Watcher.this.currentMobsCount) {
                    this.cancel();
                    Watcher.this.sd("You have proven yourself. You may pass.", 5, 50, true);
                    SUtil.delay(() -> {
                        Watcher.this.ewatcher.getWorld().strikeLightningEffect(Watcher.this.ewatcher.getLocation().clone().add(0.0, 1.8, 0.0));
                        Watcher.this.ewatcher.remove();
                        Watcher.this.wfe1.remove();
                        Watcher.this.wfe2.remove();
                        Watcher.this.econv.remove();
                    }, 60L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead() || !Watcher.this.welcomeParticles) {
                    this.cancel();
                    return;
                }
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 0.75, 0.0), Effect.LARGE_SMOKE, 0, 1, (float)Watcher.random(-2, 2), (float)Watcher.random(-1.5, 1.5), (float)Watcher.random(-2, 2), 0.0f, 1, 20);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 0.75, 0.0), Effect.WITCH_MAGIC, 0, 1, (float)Watcher.random(-2, 2), (float)Watcher.random(-1.5, 1.5), (float)Watcher.random(-2, 2), 0.0f, 1, 20);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 0.75, 0.0), Effect.LARGE_SMOKE, 0, 1, (float)Watcher.random(-2, 2), (float)Watcher.random(-1.5, 1.5), (float)Watcher.random(-2, 2), 0.0f, 1, 20);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 0.75, 0.0), Effect.WITCH_MAGIC, 0, 1, (float)Watcher.random(-2, 2), (float)Watcher.random(-1.5, 1.5), (float)Watcher.random(-2, 2), 0.0f, 1, 20);
                stand.getWorld().spigot().playEffect(new Location(stand.getWorld(), stand.getLocation().getX() + Watcher.random(-2, 2), stand.getLocation().getY() + 1.75 + Watcher.random(-1.5, 1.5), stand.getLocation().getZ() + Watcher.random(-2, 2)), Effect.COLOURED_DUST, 0, 1, 0.99607843f, 0.12941177f, 0.003921569f, 1.0f, 0, 64);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 2L, 2L);
        this.sd("Oh... hello?", 20, 0, false);
        this.sd(null, 60, 0, false);
        this.sd("You've arrived too early, I haven't even set up...", 80, 0, false);
        this.sd(null, 120, 0, false);
        this.sd("Anyway, let's fight... I guess.", 140, 0, false);
        this.sd(null, 180, 0, false);
        SUtil.delay(() -> {
            this.moveWatcher((Entity)stand, true);
            this.welcomeParticles = false;
        }, 200L);
    }
    
    public static void drawLineforMovingPoints(final Location point1, final Location point2, final double space, final Player p, final Entity e) {
        final Location blockLocation = point1;
        final Location crystalLocation = point2;
        final Vector vector = blockLocation.clone().toVector().subtract(crystalLocation.clone().toVector());
        final double count = 45.0;
        for (int i = 1; 45 >= i; ++i) {
            final Vector v = vector.clone().multiply(i / 45.0);
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(v), Effect.MAGIC_CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        }
    }
    
    public static String trans(final String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }
    
    public static ItemStack getSkull(final String texture) {
        final ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1);
        stack.setDurability((short)3);
        final SkullMeta meta = (SkullMeta)stack.getItemMeta();
        final String stringUUID = UUID.randomUUID().toString();
        final GameProfile profile = new GameProfile(UUID.fromString(stringUUID), (String)null);
        final byte[] ed = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/%s\"}}}", new Object[] { texture }).getBytes());
        profile.getProperties().put((Object)"textures", (Object)new Property("textures", new String(ed)));
        try {
            final Field f = meta.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set((Object)meta, (Object)profile);
        }
        catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {}
        stack.setItemMeta((ItemMeta)meta);
        return stack;
    }
    
    public static ItemStack getSkullStack(final String name) {
        final ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1);
        stack.setDurability((short)3);
        final SkullMeta meta = (SkullMeta)stack.getItemMeta();
        meta.setOwner(name);
        stack.setItemMeta((ItemMeta)meta);
        return stack;
    }
    
    public void playHeadSpawning(final Entity e) {
        String amd = ((MetadataValue)e.getMetadata("TYPE").get(0)).asString();
        amd = amd.replace((CharSequence)"WATCHER_", (CharSequence)"");
        final HeadsOnWall h = new HeadsOnWall(EnumWatcherType.valueOf(amd));
        final Location target = this.watcher.clone().add(0.0, -3.5, 0.0);
        final ArmorStand stand = (ArmorStand)e.getWorld().spawn(e.getLocation(), (Class)ArmorStand.class);
        stand.setCustomNameVisible(false);
        stand.setVisible(false);
        stand.getEquipment().setHelmet(getSkull(h.skullTexture));
        stand.setGravity(false);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.LARGE_SMOKE, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                stand.getWorld().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.POTION_SWIRL, 0);
                stand.getWorld().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.FLYING_GLYPH, 0);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.COLOURED_DUST, 0, 1, 0.99607843f, 0.12941177f, 0.003921569f, 1.0f, 0, 64);
                stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 1.6, 0.0), Effect.COLOURED_DUST, 0, 1, 0.99607843f, 0.12941177f, 0.003921569f, 1.0f, 0, 64);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 2L, 2L);
        new BukkitRunnable() {
            int t = 0;
            int i = 0;
            
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                if (this.i >= Watcher.random(40, 45) && Material.AIR == stand.getLocation().getBlock().getType() && Material.AIR == stand.getLocation().clone().add(0.0, 1.0, 0.0).getBlock().getType()) {
                    stand.remove();
                    for (int i = 0; 20 > i; ++i) {
                        stand.getWorld().spigot().playEffect(stand.getLocation().clone().add(0.0, 0.25, 0.0), Effect.EXPLOSION, 0, 1, (float)Watcher.random(-0.5, 0.5), (float)Watcher.random(-1, 1), (float)Watcher.random(-0.5, 0.5), 0.0f, 1, 20);
                    }
                    stand.getWorld().playSound(stand.getLocation(), Sound.ZOMBIE_REMEDY, 0.2f, 1.8f);
                    new SEntity(stand.getLocation(), SEntityType.valueOf(h.stype), new Object[0]);
                    this.cancel();
                    return;
                }
                ++this.i;
                this.t += 15;
                final Location r = stand.getLocation().setDirection(target.toVector().subtract(stand.getLocation().toVector()));
                stand.teleport(r);
                stand.teleport(stand.getLocation().add(stand.getLocation().getDirection().multiply(0.25)));
                Watcher.sendHeadRotation((Entity)stand, (float)this.t, r.getPitch());
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
    }
    
    public void say(final String str) {
        if (null == str) {
            this.econv.setCustomNameVisible(false);
            return;
        }
        for (final Player p : this.econv.getWorld().getPlayers()) {
            p.sendMessage(trans("&c[BOSS] The Watcher&f: " + str));
        }
        this.econv.setCustomNameVisible(true);
        this.econv.setCustomName(trans("&f&l" + str));
    }
    
    public void sd(final String str, final int delay, final int timeout, final boolean needTo) {
        SUtil.delay(() -> this.say(str), delay);
        if (needTo) {
            SUtil.delay(() -> {
                if (this.econv.getCustomName().contains((CharSequence)str)) {
                    this.say(null);
                }
            }, timeout + delay);
        }
    }
    
    static {
        WATCHER_CACHE = (Map)new HashMap();
    }
}
