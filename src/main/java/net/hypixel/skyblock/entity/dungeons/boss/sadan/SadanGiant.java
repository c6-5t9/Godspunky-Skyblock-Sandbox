package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import org.bukkit.entity.FallingBlock;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Giant;
import java.util.List;
import org.bukkit.block.Block;
import net.hypixel.skyblock.api.block.BlockFallAPI;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.entity.HumanEntity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.user.User;
import org.bukkit.scheduler.BukkitTask;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.GameMode;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.World;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.dungeons.watcher.GlobalBossBar;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class SadanGiant extends BaseZombie
{
    private static LivingEntity e;
    private boolean laserActiveCD;
    private boolean laserActive;
    private boolean shockWave;
    private boolean shockWaveCD;
    private boolean terToss;
    private boolean terTossCD;
    private boolean swordActiv;
    private boolean swordSlamCD;
    private GlobalBossBar bb;
    
    public SadanGiant() {
        this.laserActiveCD = true;
        this.laserActive = false;
        this.shockWave = false;
        this.shockWaveCD = true;
        this.terToss = false;
        this.terTossCD = true;
        this.swordActiv = false;
        this.swordSlamCD = true;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&c&lSadan");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 4.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 55000.0;
    }
    
    public GlobalBossBar setBar(final World w, final String s) {
        this.bb = new GlobalBossBar(Sputnik.trans(s), w);
        for (final Player p : w.getPlayers()) {
            this.bb.addPlayer(p);
        }
        return this.bb;
    }
    
    public void updateBar(final double percent) {
        this.bb.setProgress(percent);
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        final GlobalBossBar boss = this.setBar(entity.getWorld(), Sputnik.trans("&c&lSadan"));
        SadanGiant.e = entity;
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        SUtil.delay(() -> this.shockWaveCD = false, 120L);
        SUtil.delay(() -> this.terTossCD = false, 100L);
        SUtil.delay(() -> this.laserActiveCD = false, 1L);
        SUtil.delay(() -> this.swordSlamCD = false, 40L);
        entity.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)55);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("highername", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Boss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Giant_", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
                if (entity.getHealth() > 0.0) {
                    SadanGiant.this.updateBar(entity.getHealth() / entity.getMaxHealth());
                }
                else {
                    SadanGiant.this.updateBar(9.990009990009992E-4);
                }
                if (entity.isDead()) {
                    SUtil.delay(() -> {
                        final ArrayList<Player> plist = (ArrayList<Player>)new ArrayList();
                        for (final Player p : SadanGiant.this.bb.players) {
                            plist.add((Object)p);
                        }
                        plist.forEach(pl -> SadanGiant.this.bb.removePlayer(pl));
                        SadanGiant.this.bb.setProgress(0.0);
                        SadanGiant.this.bb.cancel();
                    }, 250L);
                    this.cancel();
                    return;
                }
                if (!SadanGiant.this.laserActiveCD && !SadanGiant.this.laserActive && SUtil.random(1, 120) <= 6 && target != null) {
                    SadanGiant.this.laserActiveCD = true;
                    SadanGiant.this.laserActive = true;
                    SadanGiant.this.laser(entity);
                }
                if (!SadanGiant.this.swordSlamCD && !SadanGiant.this.swordActiv && !SadanGiant.this.shockWave && SUtil.random(1, 140) <= 7 && target != null) {
                    SadanGiant.this.swordActiv = true;
                    SadanGiant.this.swordSlamCD = true;
                    SadanGiant.this.swordSlamAC(entity, target);
                }
                if (!SadanGiant.this.shockWave && !SadanGiant.this.shockWaveCD && SUtil.random(1, 100) <= 5 && !SadanGiant.this.swordActiv && target != null) {
                    SadanGiant.this.shockWaveCD = true;
                    SadanGiant.this.shockWave = true;
                    final Vector vec = new Vector(0, 0, 0);
                    vec.setY(2);
                    SadanGiant.e.setVelocity(vec);
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        SadanGiant.this.jumpAni(entity);
                    }, 10L);
                }
                if (!SadanGiant.this.terToss && !SadanGiant.this.terTossCD && SUtil.random(1, 150) <= 4 && target != null) {
                    SadanGiant.this.terTossCD = true;
                    SadanGiant.this.terToss = true;
                    SUtil.delay(() -> SadanGiant.this.terToss = false, 300L);
                    SadanGiant.this.launchTerrain(entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target = (Player)entities;
                    if (target.getGameMode() == GameMode.CREATIVE) {
                        continue;
                    }
                    if (target.getGameMode() == GameMode.SPECTATOR) {
                        continue;
                    }
                    if (target.hasMetadata("NPC")) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target.getLocation().toVector().subtract(target.getLocation().toVector())));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 8L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)), b(12228503, Material.LEATHER_HELMET), b(14751108, Material.LEATHER_CHESTPLATE), c(Material.DIAMOND_LEGGINGS), b(8991025, Material.LEATHER_BOOTS));
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        Sputnik.zero(killed);
        SadanHuman.IsMusicPlaying.put((Object)killed.getWorld().getUID(), (Object)false);
        SadanHuman.BossRun.put((Object)killed.getWorld().getUID(), (Object)false);
        SUtil.delay(() -> SadanFunction.sendReMsg(true, killed.getWorld()), 30L);
        SUtil.delay(() -> SadanFunction.endRoom2(killed.getWorld()), 40L);
        final BukkitTask bkt = SadanHuman.playHBS(killed.getWorld());
        new BukkitRunnable() {
            public void run() {
                if (killed.getWorld() == null || killed.getWorld().getPlayers().size() == 0) {
                    bkt.cancel();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        for (final Player p : killed.getWorld().getPlayers()) {
            final User user = User.getUser(p.getUniqueId());
            user.addBCollection(1);
        }
        this.aA(killed.getWorld());
        final EntityPlayer a = DeadBodyMaker.spawn(killed.getLocation());
        final ArmorStand as = (ArmorStand)killed.getWorld().spawn(killed.getLocation().clone().add(0.0, -0.2, 0.0), (Class)ArmorStand.class);
        final ArmorStand as2 = (ArmorStand)killed.getWorld().spawn(killed.getLocation().add(0.0, 0.1, 0.0), (Class)ArmorStand.class);
        as.setVisible(false);
        as.setGravity(false);
        as.setCustomNameVisible(true);
        as.setCustomName(Sputnik.trans("&e\ufd3e &c&lSadan &e0&c\u2764 &e\ufd3f"));
        as2.setVisible(false);
        as2.setGravity(false);
        as2.setCustomNameVisible(true);
        as2.setCustomName(Sputnik.trans("&f&lNOOOOOO!!! THIS IS IMPOSSIBLE!!!"));
        SUtil.broadcastWorld(Sputnik.trans("&c[BOSS] Sadan&f: NOOOOOO!!! THIS IS IMPOSSIBLE!!!"), killed.getWorld());
        new BukkitRunnable() {
            public void run() {
                as2.setCustomName(Sputnik.trans("&f&lFATHER, FORGIVE ME!!!"));
                SUtil.broadcastWorld(Sputnik.trans("&c[BOSS] Sadan&f: FATHER, FORGIVE ME!!!"), killed.getWorld());
                SUtil.delay(() -> {
                    final Object val$as2 = as2;
                    as2.setCustomNameVisible(false);
                }, 60L);
                SUtil.delay(() -> {
                    final Object val$as3 = as2;
                    as2.setCustomName("");
                }, 60L);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 35L);
    }
    
    @Override
    public boolean hasNameTag() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return false;
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.35;
    }
    
    public void laser(final LivingEntity e) {
        final int[] array_colors = { 12228503, 8739418, 6897985, 6042419, 5385260 };
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[0])), 20L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[1])), 40L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[2])), 60L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[3])), 80L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[4])), 100L);
        SUtil.delay(() -> this.laserAni(e), 105L);
        SUtil.delay(() -> this.laserActive = false, 250L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[4])), 270L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[3])), 290L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[2])), 310L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[1])), 330L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[0])), 350L);
        SUtil.delay(() -> e.getEquipment().setHelmet(b(15249075, Material.LEATHER_HELMET)), 370L);
        SUtil.delay(() -> this.laserActiveCD = false, 650L);
    }
    
    public void aA(final World world) {
        for (final Entity entity : world.getEntities()) {
            if (entity instanceof HumanEntity) {
                continue;
            }
            entity.remove();
        }
    }
    
    public void jumpAni(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!SadanGiant.this.shockWave) {
                    this.cancel();
                    return;
                }
                if (e.isOnGround()) {
                    SadanGiant.this.shockWave = false;
                    SUtil.delay(() -> SadanGiant.this.shockWaveCD = false, 500L);
                    e.getWorld().playEffect(e.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 3);
                    e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION_HUGE, 3);
                    e.getWorld().playSound(e.getLocation(), Sound.EXPLODE, 3.0f, 0.0f);
                    SUtil.delay(() -> {
                        final Object val$e = e;
                        e.getWorld().playSound(e.getLocation(), Sound.EXPLODE, 10.0f, 0.0f);
                    }, 5L);
                    for (final Entity entities : e.getNearbyEntities(15.0, 10.0, 15.0)) {
                        final Vector vec = new Vector(0, 0, 0);
                        if (entities.hasMetadata("NPC")) {
                            continue;
                        }
                        if (entities instanceof ArmorStand) {
                            continue;
                        }
                        if (entities.hasMetadata("highername")) {
                            continue;
                        }
                        if (!(entities instanceof Player)) {
                            continue;
                        }
                        if (entities.getLocation().distance(e.getLocation()) <= 5.0) {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eStomp &cfor " + SUtil.commaify(Math.round((float)SadanFunction.dmgc(40000, p, (Entity)e))) + " &cdamage."));
                        }
                        else {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eStomp &cfor " + SUtil.commaify(Math.round((float)SadanFunction.dmgc(30000, p, (Entity)e))) + " &cdamage."));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void laserAni(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!SadanGiant.this.laserActive) {
                    this.cancel();
                    return;
                }
                final LivingEntity target = (LivingEntity)((CraftZombie)e).getTarget();
                final float angle_1 = e.getEyeLocation().getYaw() / 60.0f;
                final Location loc1 = e.getEyeLocation().add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                final Location loc2 = e.getEyeLocation().subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                loc1.add(0.0, 9.5, 0.0);
                loc2.add(0.0, 9.5, 0.0);
                if (target != null) {
                    if (target.getLocation().distance(e.getLocation()) < 1.0 || target.getLocation().distance(e.getLocation()) > 100.0) {
                        return;
                    }
                    final Location loc1_ = target.getLocation();
                    final Location loc2_ = target.getLocation();
                    final Location en1 = loc1_.add(0.0, 0.5, 0.0);
                    final Location en2 = loc2_.add(0.0, 0.5, 0.0);
                    SadanGiant.drawLine(loc1, en1, 0.0);
                    SadanGiant.drawLine(loc2, en2, 0.0);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!SadanGiant.this.laserActive) {
                    this.cancel();
                    return;
                }
                final LivingEntity target = (LivingEntity)((CraftZombie)e).getTarget();
                final float angle_1 = e.getEyeLocation().getYaw() / 60.0f;
                final Location loc1 = e.getEyeLocation().add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                final Location loc2 = e.getEyeLocation().subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                loc1.add(0.0, 9.5, 0.0);
                loc2.add(0.0, 9.5, 0.0);
                if (target != null) {
                    if (target.getLocation().distance(e.getLocation()) < 1.0 || target.getLocation().distance(e.getLocation()) > 100.0) {
                        return;
                    }
                    final Location loc1_ = target.getLocation();
                    final Location loc2_ = target.getLocation();
                    final Location en1 = loc1_.add(0.0, 0.5, 0.0);
                    final Location en2 = loc2_.add(0.0, 0.5, 0.0);
                    SadanGiant.getEntity(loc1, en1, e);
                    SadanGiant.getEntity(loc2, en2, e);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 10L);
    }
    
    public void launchTerrain(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!SadanGiant.this.terToss) {
                    SUtil.delay(() -> SadanGiant.this.terTossCD = false, 550L);
                    this.cancel();
                    return;
                }
                final LivingEntity t = (LivingEntity)((CraftZombie)e).getTarget();
                if (t != null) {
                    SadanGiant.this.throwTerrain(e, (Entity)t);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 30L);
    }
    
    public static ItemStack buildColorStack(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack c(final Material m) {
        final ItemStack stack = new ItemStack(m);
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static void drawLine(final Location point1, final Location point2, final double space) {
        final Location blockLocation = point1;
        final Location crystalLocation = point2;
        final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 90.0;
        for (int i = 1; i <= 90; ++i) {
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), Effect.COLOURED_DUST, 0, 1, 0.8627451f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), Effect.COLOURED_DUST, 0, 1, 1.0196079f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
        }
    }
    
    public static void getEntity(final Location finaldestination, final Location ended, final LivingEntity e) {
        final Location blockLocation = finaldestination;
        final Location crystalLocation = ended;
        final Vector vector = blockLocation.clone().add(0.1, 0.1, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 90.0;
        for (int i = 1; i <= 90; ++i) {
            for (final Entity entity : ended.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), 0.17, 0.0, 0.17)) {
                if (entity instanceof Player) {
                    final Player p = (Player)entity;
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eLaser Eyes &cfor " + SUtil.commaify(Math.round((float)SadanFunction.dmgc(16000, p, (Entity)e))) + " &cdamage."));
                    return;
                }
            }
        }
    }
    
    public static void applyEffect(final PotionEffectType e, final Entity en, final int ticks, final int amp) {
        ((LivingEntity)en).addPotionEffect(new PotionEffect(e, ticks, amp));
    }
    
    public static void createBlockTornado(final Entity e, final Material mat, final byte id) {
        for (int i = 0; i <= 30; ++i) {
            final int random = SUtil.random(0, 3);
            double range = 0.0;
            final Location loc = e.getLocation().clone();
            loc.setYaw((float)SUtil.random(0, 360));
            if (random == 1) {
                range = 0.6;
            }
            if (random == 2) {
                range = 0.7;
            }
            if (random == 3) {
                range = 0.8;
            }
            final Vector vec = loc.getDirection().normalize().multiply(range);
            vec.setY(1.1);
            BlockFallAPI.sendVelocityBlock(e.getLocation(), mat, id, e.getWorld(), 70, vec);
        }
    }
    
    public static void damagePlayer(final Player p) {
        p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eBoulder Toss &cfor " + SUtil.commaify(SadanFunction.dmgc(30000, p, (Entity)SadanGiant.e)) + " &cdamage."));
    }
    
    public void throwTerrain(final LivingEntity e, final Entity target) {
        final Block b = target.getLocation().getBlock();
        final World world = e.getWorld();
        final Location startBlock = e.getLocation().add(e.getLocation().getDirection().multiply(2));
        final List<Location> locationList = (List<Location>)new ArrayList();
        final List<Location> endList = (List<Location>)new ArrayList();
        final List<Material> blockTypes = (List<Material>)new ArrayList();
        final List<Material> launchTypes = (List<Material>)new ArrayList();
        for (int length = -1; length < 2; ++length) {
            for (int height = -1; height < 2; ++height) {
                final Location loc = startBlock.clone().add((double)length, 0.0, (double)height);
                final Location end = b.getLocation().clone().add((double)length, 0.0, (double)height);
                locationList.add((Object)loc);
                endList.add((Object)end);
            }
        }
        locationList.add((Object)startBlock.clone().add(0.0, 0.0, 2.0));
        locationList.add((Object)startBlock.clone().add(0.0, 0.0, -2.0));
        locationList.add((Object)startBlock.clone().add(2.0, 0.0, 0.0));
        locationList.add((Object)startBlock.clone().add(-2.0, 0.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(0.0, 0.0, 2.0));
        endList.add((Object)b.getLocation().clone().add(0.0, 0.0, -2.0));
        endList.add((Object)b.getLocation().clone().add(2.0, 0.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(-2.0, 0.0, 0.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(1.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(-1.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, 1.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, -1.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(1.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(-1.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, 1.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, -1.0));
        final Byte blockData = 0;
        locationList.forEach(block -> {
            final Location loc2 = block.getBlock().getLocation().clone().subtract(0.0, 1.0, 0.0);
            Material mat = loc2.getBlock().getType();
            if (mat == Material.AIR) {
                mat = Material.STONE;
            }
            launchTypes.add((Object)mat);
            blockTypes.add((Object)block.getBlock().getType());
        });
        locationList.forEach(location -> {
            final Material material = (Material)launchTypes.get(locationList.indexOf((Object)location));
            final Location origin = location.clone().add(0.0, 7.0, 0.0);
            final int pos = locationList.indexOf((Object)location);
            final Location endPos = (Location)endList.get(pos);
            final FallingBlock block2 = world.spawnFallingBlock(origin, material, (byte)blockData);
            block2.setDropItem(false);
            block2.setMetadata("f", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            block2.setVelocity(Sputnik.calculateVelocityBlock(origin.toVector(), endPos.toVector(), 3));
        });
    }
    
    public static void playLaserSound(final Player p, final Entity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void swordSlamAC(final LivingEntity e, final LivingEntity tar) {
        SUtil.delay(() -> this.swordSlamF(e, tar), 40L);
    }
    
    public void swordSlamF(final LivingEntity e, final LivingEntity tar) {
        SUtil.delay(() -> this.swordSlam(e, tar), 30L);
    }
    
    public void swordSlam(final LivingEntity e, final LivingEntity player) {
        if (e.isDead()) {
            return;
        }
        e.getEquipment().setItemInHand((ItemStack)null);
        final Giant armorStand = (Giant)player.getWorld().spawn(e.getLocation().add(0.0, 12.0, 0.0), (Class)Giant.class);
        armorStand.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
        Sputnik.applyPacketGiant((Entity)armorStand);
        armorStand.setCustomName("Dinnerbone");
        armorStand.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        armorStand.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.Woosh((LivingEntity)armorStand);
        EntityManager.noHit((Entity)armorStand);
        EntityManager.shutTheFuckUp((Entity)armorStand);
        final Location firstLocation = e.getLocation().add(0.0, 12.0, 0.0);
        final EntityLiving nms = ((CraftLivingEntity)e).getHandle();
        for (final Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)e).getHandle(), 0));
        }
        nms.r(((CraftEntity)e).getHandle());
        final Location secondLocation = player.getLocation();
        final Vector mobsVector = firstLocation.toVector();
        final Vector vectorBetween = secondLocation.toVector().subtract(mobsVector);
        vectorBetween.divide(new Vector(10, 10, 10));
        vectorBetween.add(new Vector(0, 0, 0));
        final int id = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)SkyBlock.getPlugin(), () -> armorStand.setVelocity(vectorBetween), 10L, 10L);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SkyBlock.getPlugin(), () -> Bukkit.getScheduler().cancelTask(id), 40L);
        new BukkitRunnable() {
            public void run() {
                if (!SadanGiant.this.swordActiv) {
                    this.cancel();
                    return;
                }
                if (armorStand.isOnGround()) {
                    SadanGiant.this.swordActiv = false;
                    SUtil.delay(() -> SadanGiant.this.swordSlamCD = false, 300L);
                    armorStand.remove();
                    final Giant sword = (Giant)e.getWorld().spawnEntity(armorStand.getLocation(), EntityType.GIANT);
                    Sputnik.applyPacketGiant((Entity)sword);
                    sword.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                    EntityManager.noAI((Entity)sword);
                    EntityManager.noHit((Entity)sword);
                    EntityManager.shutTheFuckUp((Entity)sword);
                    sword.setCustomName("Dinnerbone");
                    sword.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    sword.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    final ArmorStand stand = (ArmorStand)player.getWorld().spawnEntity(armorStand.getLocation(), EntityType.ARMOR_STAND);
                    stand.setVisible(false);
                    stand.setGravity(true);
                    stand.setPassenger((Entity)sword);
                    sword.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
                    e.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 0.0f);
                    e.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 0.35f);
                    for (final Entity entities : sword.getWorld().getNearbyEntities(sword.getLocation().add(sword.getLocation().getDirection().multiply(3)), 4.0, 4.0, 4.0)) {
                        if (entities.hasMetadata("NPC")) {
                            continue;
                        }
                        if (entities instanceof ArmorStand) {
                            continue;
                        }
                        if (entities instanceof Giant) {
                            continue;
                        }
                        if (!(entities instanceof Player)) {
                            continue;
                        }
                        if (entities.getLocation().add(sword.getLocation().getDirection().multiply(3)).distance(sword.getLocation()) > 1.0) {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eSword of 10,000 Truths &cfor " + SUtil.commaify(SadanFunction.dmgc(50000, p, (Entity)e)) + " &cdamage."));
                        }
                        else {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lThe Giant One &chit you with &eSword of 10,000 Truths &cfor " + SUtil.commaify(SadanFunction.dmgc(55000, p, (Entity)e)) + " &cdamage."));
                        }
                    }
                    SUtil.delay(() -> sword.remove(), 35L);
                    SUtil.delay(() -> stand.remove(), 35L);
                    SUtil.delay(() -> {
                        final Object val$e = e;
                        e.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
                    }, 35L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENDERDRAGON_HIT, 1.0f, 0.3f);
    }
}
