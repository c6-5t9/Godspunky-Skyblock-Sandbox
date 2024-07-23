package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import java.util.HashMap;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.PlayJingle;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.entity.SEntityEquipment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Location;
import net.hypixel.skyblock.user.User;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.BossBar;
import java.util.Iterator;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.dungeons.watcher.GlobalBossBar;
import org.bukkit.World;
import java.util.UUID;
import org.bukkit.entity.Entity;
import java.util.Map;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class SadanHuman extends BaseZombie
{
    private final double y;
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    public static final Map<Entity, String> DIALOUGE_BOSS;
    public static final Map<UUID, Boolean> SadanReach;
    @Deprecated
    public static final Map<World, Boolean> SadanP1;
    @Deprecated
    public static final Map<World, Boolean> SadanP2;
    public static final Map<UUID, Integer> SadanInterest;
    public static final Map<UUID, Integer> SadanGiantsCount;
    public static final Map<UUID, Boolean> IsMusicPlaying;
    public static final Map<UUID, Boolean> BBRunning;
    public static final Map<UUID, Boolean> BossRun;
    @Deprecated
    public static final Map<World, Byte> SadanCurrentPhase;
    @Deprecated
    public static final Map<World, Boolean> IsSadanDied;
    private boolean phase1;
    private boolean phase2;
    private boolean phase3;
    private GlobalBossBar bb;
    
    public SadanHuman() {
        this.y = 0.85;
        this.h = false;
        this.phase1 = false;
        this.phase2 = false;
        this.phase3 = false;
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
        return 40000.0;
    }
    
    public GlobalBossBar setBar(final World w, final String s) {
        this.bb = new GlobalBossBar(Sputnik.trans(s), w);
        for (final Player p : w.getPlayers()) {
            this.bb.addPlayer(p);
        }
        return this.bb;
    }
    
    public void removeAllBar(final World w, final BossBar b) {
        for (final Player p : w.getPlayers()) {
            b.removePlayer(p.getUniqueId());
        }
    }
    
    public void updateBar(final double percent) {
        this.bb.setProgress(percent);
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (SadanHuman.BBRunning.containsKey((Object)entity.getWorld().getUID())) {
            if (SadanHuman.BBRunning.get((Object)entity.getWorld().getUID())) {
                entity.remove();
                return;
            }
        }
        else {
            SadanHuman.BBRunning.put((Object)entity.getWorld().getUID(), (Object)true);
        }
        if (!entity.getWorld().getName().contains((CharSequence)"f6")) {
            entity.remove();
            Bukkit.broadcastMessage(Sputnik.trans("&cAn Error has been occured while performing the /spawncustommob command! Please check the Console!"));
            return;
        }
        for (final Player p : entity.getWorld().getPlayers()) {
            final User user = User.getUser(p.getUniqueId());
            user.addBRun6(1);
        }
        SadanHuman.BossRun.put((Object)entity.getWorld().getUID(), (Object)true);
        SadanFunction.s_((Entity)entity);
        final BukkitTask bkt = playHBS(entity.getWorld());
        this.sayPhase1Dialouge((Entity)entity);
        final Location loc = new Location(entity.getWorld(), 191.5, 78.0, 296.5, 180.0f, 0.0f);
        entity.teleport(loc);
        Repeater.FloorLivingSec.put((Object)entity.getWorld().getUID(), (Object)0);
        SadanFunction.roomLoop(entity.getWorld());
        SadanHuman.SadanInterest.put((Object)entity.getWorld().getUID(), (Object)105);
        SadanHuman.SadanGiantsCount.put((Object)entity.getWorld().getUID(), (Object)4);
        final GlobalBossBar bb = this.setBar(entity.getWorld(), "&c&lSadan");
        new BukkitRunnable() {
            public void run() {
                SadanHuman.this.phase1 = true;
                bb.setTitle(Sputnik.trans("&c&lSadan's Interest Level"));
                bb.setProgress(1.0);
                SadanHuman.this.phase1((Entity)entity);
                bkt.cancel();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 170L);
        new BukkitRunnable() {
            public void run() {
                if (entity.getWorld() == null || entity.getWorld().getPlayers().size() == 0 || entity.isDead()) {
                    bkt.cancel();
                    SadanHuman.DIALOUGE_BOSS.remove((Object)entity);
                    SadanHuman.SadanReach.remove((Object)entity.getWorld().getUID());
                    SadanHuman.SadanInterest.remove((Object)entity.getWorld().getUID());
                    SadanHuman.SadanGiantsCount.remove((Object)entity.getWorld().getUID());
                    SadanHuman.BossRun.remove((Object)entity.getWorld().getUID());
                    SadanHuman.IsMusicPlaying.remove((Object)entity.getWorld().getUID());
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
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
                if (SadanHuman.this.phase1) {
                    if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) > 0) {
                        final int interest = (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID());
                        final double result = interest / 105.0;
                        bb.setProgress(result);
                    }
                    else {
                        bb.setTitle(Sputnik.trans("&c&lSadan"));
                        bb.setProgress(1.0);
                        SadanHuman.this.phase1 = false;
                        SadanHuman.this.phase2((Entity)entity);
                        new BukkitRunnable() {
                            public void run() {
                                bb.setTitle(Sputnik.trans("&c&lSadan's Giants"));
                                bb.setProgress(1.0);
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 35L);
                    }
                }
                if (SadanHuman.this.phase2) {
                    if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) > 0) {
                        final int giant = (int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID());
                        final double result = giant / 4.0;
                        bb.setProgress(result);
                    }
                    else {
                        bb.setTitle(Sputnik.trans("&c&lSadan"));
                        bb.setProgress(1.0);
                        SadanHuman.this.phase2 = false;
                        SadanHuman.this.phase3((Entity)entity);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        ((CraftZombie)entity).setBaby(false);
        Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTYxMjAyOTExOTA2MiwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEwNmNiMGM0NzFjMWM5YmMxNjlhZjI3MGNkNDY2ZWE3MDE5NDY3NzYwNTZlNDcyZWNkYWViNDlmMGY0YTRkYyIKICAgIH0KICB9Cn0=", "mpSbfgDmWvoHASfqQ+poj2b4Y0QEZYh4QlqcCqHrZ4DNKY7mIenlbY2s7Ptmhb46dONt5OVfHb1pDLDlCPnYP9QYDXhl/wR99wxA4F7HHjs1g1omvZBfGRCvwHU/Bc3aWhjlaKZCVotf0snzrPTWIHFYnQoVLnhXoz19b3SQfdztIipZoZFKgMxwM2l4y+hBS9p7b/u2loz6/kVLBiLxzzYtAayF+ekma+bWlQcqhdsaf/BAJJSjh/UtipZLvAo4L2E2JlBsoKhj9PVSRVk4eAS1KE7p9Dupbrr/Ypj4bYVpUH5KhMJlQn7vCGoWILwd1NjFWk6KVlGUCag8/3pE1BNeD5d3QOfiVCkFH/rofRfS0/w0Nv8ROK0JQP/cFaAQ3kQ2ilvifF0kzPiA1M7si22lbXGyLqhQAVFsNSgKIU0Fe2qfD536Rr+kkBc/sVAzfVh4ajfsOXtMuMoZGIDJULpA1RD9qsybGvl7kkVQd2jPzlvZD8Ef8ZW8wr64Lu+/zZEj30zISIKZiwIsMKM2vOO7eqbfTs+tu0BNKKjiRg7uLF0qhyCpQrlJENzFud04ZiaTyI1Btt2LpOHQmKASWfg7/TEr8rPVPWiVqRBPCpHe5xJlAtQc2+PrtBO8u+qG3TTRKVci2a+Mpx1SwuPtMY2ZRj1NmYW3yBuu9pQnvlg=", true);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("h_sadan", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Boss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.noAI((Entity)entity);
        EntityManager.noHit((Entity)entity);
        EntityManager.shutTheFuckUp((Entity)entity);
        entity.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final net.minecraft.server.v1_8_R3.Entity e = ((CraftEntity)entity).getHandle();
        final double height_;
        final double height = height_ = e.getBoundingBox().e - e.getBoundingBox().b;
        final ArmorStand hologram_d = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, height + 0.22, 0.0), (Class)ArmorStand.class);
        hologram_d.setVisible(false);
        hologram_d.setGravity(false);
        hologram_d.setSmall(false);
        hologram_d.setMarker(true);
        hologram_d.setBasePlate(false);
        hologram_d.setCustomNameVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTYxMjAyOTExOTA2MiwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEwNmNiMGM0NzFjMWM5YmMxNjlhZjI3MGNkNDY2ZWE3MDE5NDY3NzYwNTZlNDcyZWNkYWViNDlmMGY0YTRkYyIKICAgIH0KICB9Cn0=", "mpSbfgDmWvoHASfqQ+poj2b4Y0QEZYh4QlqcCqHrZ4DNKY7mIenlbY2s7Ptmhb46dONt5OVfHb1pDLDlCPnYP9QYDXhl/wR99wxA4F7HHjs1g1omvZBfGRCvwHU/Bc3aWhjlaKZCVotf0snzrPTWIHFYnQoVLnhXoz19b3SQfdztIipZoZFKgMxwM2l4y+hBS9p7b/u2loz6/kVLBiLxzzYtAayF+ekma+bWlQcqhdsaf/BAJJSjh/UtipZLvAo4L2E2JlBsoKhj9PVSRVk4eAS1KE7p9Dupbrr/Ypj4bYVpUH5KhMJlQn7vCGoWILwd1NjFWk6KVlGUCag8/3pE1BNeD5d3QOfiVCkFH/rofRfS0/w0Nv8ROK0JQP/cFaAQ3kQ2ilvifF0kzPiA1M7si22lbXGyLqhQAVFsNSgKIU0Fe2qfD536Rr+kkBc/sVAzfVh4ajfsOXtMuMoZGIDJULpA1RD9qsybGvl7kkVQd2jPzlvZD8Ef8ZW8wr64Lu+/zZEj30zISIKZiwIsMKM2vOO7eqbfTs+tu0BNKKjiRg7uLF0qhyCpQrlJENzFud04ZiaTyI1Btt2LpOHQmKASWfg7/TEr8rPVPWiVqRBPCpHe5xJlAtQc2+PrtBO8u+qG3TTRKVci2a+Mpx1SwuPtMY2ZRj1NmYW3yBuu9pQnvlg=", true);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2000L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    hologram_d.remove();
                    this.cancel();
                    return;
                }
                if (SadanHuman.DIALOUGE_BOSS.containsKey((Object)entity)) {
                    hologram_d.setCustomNameVisible(true);
                    hologram_d.setCustomName(Sputnik.trans("&f&l" + (String)SadanHuman.DIALOUGE_BOSS.get((Object)entity)));
                }
                else {
                    hologram_d.setCustomNameVisible(false);
                    hologram_d.setCustomName("");
                }
                hologram_d.teleport(entity.getLocation().clone().add(0.0, height + 0.22, 0.0));
                hologram_d.teleport(entity.getLocation().clone().add(0.0, height + 0.22, 0.0));
                hologram_d.teleport(entity.getLocation().clone().add(0.0, height + 0.22, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void t(final ArmorStand respawnAnchor) {
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, null, null, null, null);
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    public void phase1(final Entity entity) {
        this.function(entity);
        SadanHuman.IsMusicPlaying.put((Object)entity.getWorld().getUID(), (Object)true);
        PlayJingle.play(PlayJingle.SkySimSong.DUNGEONS_DRAMA, (byte)100, new Location(entity.getWorld(), 191.0, 69.0, 266.0), 1000);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    SadanHuman.this.phase1 = false;
                    SadanHuman.SadanInterest.put((Object)entity.getWorld().getUID(), (Object)0);
                    SadanFunction.endPhase1(entity.getWorld());
                    this.cancel();
                    return;
                }
                if (!SadanHuman.SadanInterest.containsKey((Object)entity.getWorld().getUID())) {
                    SadanHuman.SadanInterest.put((Object)entity.getWorld().getUID(), (Object)105);
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 0) {
                    SadanHuman.this.phase1 = false;
                    SadanHuman.SadanInterest.put((Object)entity.getWorld().getUID(), (Object)0);
                    SadanFunction.endPhase1(entity.getWorld());
                    this.cancel();
                    return;
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 84 && (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) >= 75 && !SadanHuman.this.a) {
                    SadanHuman.this.a = true;
                    SadanHuman.this.sendDelayed(entity, "My Terracotta army uses some of the finest souls: old warriors and wizards!", 60);
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 63 && (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) >= 55 && !SadanHuman.this.b) {
                    SadanHuman.this.b = true;
                    SadanHuman.this.sendDelayed(entity, "You will be humbled, reckless humans!", 60);
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 42 && (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) >= 35 && !SadanHuman.this.c) {
                    SadanHuman.this.c = true;
                    SadanHuman.this.sendDelayed(entity, "Can you at least enjoy my science? pushing the boundaries of necromancy!", 60);
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 21 && (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) >= 15 && !SadanHuman.this.d) {
                    SadanHuman.this.d = true;
                    SadanHuman.this.sendDelayed(entity, "Corpses are deficient, full of weaknesses. Statues are stronger, faster, easier to repair.", 60);
                }
                if ((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) <= 10 && (int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) >= 5 && !SadanHuman.this.e) {
                    SadanHuman.this.e = true;
                    SadanHuman.this.sendDelayed(entity, "Can't you see there's no winning? This room will be your tomb.", 60);
                }
                SadanHuman.SadanInterest.put((Object)entity.getWorld().getUID(), (Object)((int)SadanHuman.SadanInterest.get((Object)entity.getWorld().getUID()) - 1));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
    
    public void f(final Entity entity) {
        if (entity.isDead()) {
            return;
        }
        if (SadanHuman.SadanGiantsCount.containsKey((Object)entity.getWorld().getUID())) {
            if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) == 4) {
                final int rand = SUtil.random(0, 2);
                if (SUtil.random(0, 2) == 0) {
                    this.sendDelayed(entity, "How many players does it take to kill a Giant?", 60);
                }
                else if (rand == 1) {
                    this.sendDelayed(entity, "Boulders, lasers, stomping, and giant swords! Oh my!", 60);
                }
                else {
                    this.sendDelayed(entity, "Which one is your favorite? Those laser eyes... beautiful!", 60);
                }
            }
            else if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) <= 3 && (int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) > 0) {
                this.sendDelayed(entity, "My first time using them, extremely interesting!", 60);
            }
        }
    }
    
    public void phase2(final Entity entity) {
        this.phase2 = true;
        this.sayPhase2Dialouge(entity);
        SUtil.delay(() -> SadanFunction.release(entity.getWorld()), 40L);
        SUtil.delay(() -> this.f(entity), SUtil.random(700, 1000));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) <= 0) {
                    this.cancel();
                    return;
                }
                if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) == 3 && !SadanHuman.this.f) {
                    SadanHuman.this.f = true;
                    SadanHuman.this.sendDelayed(entity, SadanHuman.this.randP1(), 60);
                }
                if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) == 2 && !SadanHuman.this.g) {
                    SadanHuman.this.g = true;
                    SadanHuman.this.sendDelayed(entity, SadanHuman.this.randP2(), 60);
                }
                if ((int)SadanHuman.SadanGiantsCount.get((Object)entity.getWorld().getUID()) == 1 && !SadanHuman.this.h) {
                    SadanHuman.this.h = true;
                    SadanHuman.this.sendDelayed(entity, SadanHuman.this.randP3(), 60);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public void function(final Entity e) {
        for (final Entity e2 : e.getWorld().getEntities()) {
            if (e2.hasMetadata("t_sadan_p1_1")) {
                e2.remove();
                new SEntity(new Location(e2.getWorld(), e2.getLocation().getX(), e2.getLocation().getY(), e2.getLocation().getZ(), e2.getLocation().getYaw(), 0.0f), SEntityType.TERRACOTTA_SADAN, new Object[0]);
            }
        }
    }
    
    public void phase3(final Entity entity) {
        this.phase3 = true;
        AnimationSequence.chainAnimation(entity.getWorld());
        this.sayPhase3Dialouge(entity);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (SadanHuman.SadanReach.containsKey((Object)entity.getWorld().getUID())) {
                    if (!(boolean)SadanHuman.SadanReach.get((Object)entity.getWorld().getUID())) {
                        this.cancel();
                        return;
                    }
                    if (SadanHuman.SadanReach.get((Object)entity.getWorld().getUID())) {
                        SadanHuman.this.a(entity);
                        SadanHuman.SadanReach.put((Object)entity.getWorld().getUID(), (Object)false);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void a(final Entity e) {
        e.setMetadata("RMHN", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final Location l = e.getLocation();
        l.setPitch(-27.0f);
        l.setYaw(-180.0f);
        new BukkitRunnable() {
            public void run() {
                final Vector teleportTo = l.getDirection().normalize().multiply(1);
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (String.valueOf(e.getLocation().getX()).contains((CharSequence)"191") && String.valueOf(e.getLocation().getZ()).contains((CharSequence)"285")) {
                    SadanHuman.this.b(e);
                    this.cancel();
                    return;
                }
                e.teleport(e.getLocation().add(teleportTo).multiply(1.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public void b(final Entity e) {
        final Location l = e.getLocation();
        l.setPitch(90.0f);
        l.setYaw(-180.0f);
        new BukkitRunnable() {
            public void run() {
                final Vector teleportTo = l.getDirection().normalize().multiply(1);
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (String.valueOf(e.getLocation().getY()).contains((CharSequence)"79")) {
                    new BukkitRunnable() {
                        public void run() {
                            new SEntity(new Location(e.getWorld(), e.getLocation().getX(), 69.0, e.getLocation().getZ(), 0.0f, 0.0f), SEntityType.GIANT_SADAN, new Object[0]);
                            e.getWorld().playSound(e.getLocation(), Sound.ENDERDRAGON_GROWL, 100.0f, 0.85f);
                            for (final Entity e1 : e.getWorld().getEntities()) {
                                if (e1.hasMetadata("dummyforphase3")) {
                                    e1.remove();
                                }
                            }
                            e.remove();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 3L);
                    SadanHuman.SadanReach.remove((Object)e.getWorld().getUID());
                    this.cancel();
                    return;
                }
                e.teleport(e.getLocation().add(teleportTo).multiply(1.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public void sayPhase1Dialouge(final Entity e) {
        this.say("So you made it all the way here...and you wish to defy me? Sadan?!", e);
        this.sd_("The audacity! I have been the ruler of these floors for a hundred years!", e, 60);
        this.sd_("I am the bridge between this realm and the world below! You shall not pass!", e, 120);
        SUtil.delay(() -> {
            final String s = (String)SadanHuman.DIALOUGE_BOSS.remove((Object)e);
        }, 180L);
    }
    
    public void sendDelayed(final Entity e, final String msg, final int delay) {
        this.say(msg, e);
        SUtil.delay(() -> {
            final String s = (String)SadanHuman.DIALOUGE_BOSS.remove((Object)e);
        }, delay);
    }
    
    public void sayPhase2Dialouge(final Entity e) {
        this.say("ENOUGH!", e);
        this.sd_("My giants! Unleashed!", e, 40);
        SUtil.delay(() -> {
            final String s = (String)SadanHuman.DIALOUGE_BOSS.remove((Object)e);
        }, 80L);
    }
    
    public void sayPhase3Dialouge(final Entity e) {
        this.say("You did it. I understand now, you have earned my respect.", e);
        this.sd_("If only you had become my disciples instead of this incompetent bunch.", e, 60);
        this.sd_("Maybe in another life. Until then, meet my ultimate corpse.", e, 120);
        this.sd_("I'm sorry but I need to concentrate. I wish it didn't have to come to this.", e, 180);
        SUtil.delay(() -> {
            final String s = (String)SadanHuman.DIALOUGE_BOSS.remove((Object)e);
        }, 240L);
    }
    
    public void sd_(final String message, final Entity e, final int delay) {
        if (e.isDead()) {
            return;
        }
        SUtil.delay(() -> this.say(message, e), delay);
    }
    
    public void say(final String message, final Entity e) {
        if (e.isDead()) {
            return;
        }
        SadanHuman.DIALOUGE_BOSS.put((Object)e, (Object)message);
        for (final Player p : e.getWorld().getPlayers()) {
            p.sendMessage(Sputnik.trans("&c[BOSS] Sadan&f: " + message));
        }
    }
    
    public void cd_(final Entity e) {
        SadanHuman.DIALOUGE_BOSS.remove((Object)e);
    }
    
    public String randP1() {
        String fin = "";
        final int rand = SUtil.random(0, 2);
        if (rand == 0) {
            fin = "You killed my weakest Giant!";
        }
        else if (rand == 1) {
            fin = "You've managed to kill a Giant! Good luck with the rest of them!";
        }
        else {
            fin = "That Giant was weak! I clearly screwed up during the necromancy process.";
        }
        return fin;
    }
    
    public String randP2() {
        String fin = "";
        final int rand = SUtil.random(0, 2);
        if (rand == 0) {
            fin = "2 of my Giants down?! No matter, these last two are tough!";
        }
        else if (rand == 1) {
            fin = "I'm starting to sweat!";
        }
        else {
            fin = "Uh oh! Half of my Giant creations have bit the dust! Whatever will I do?";
        }
        return fin;
    }
    
    public String randP3() {
        String fin = "";
        final int rand = SUtil.random(0, 1);
        if (rand == 0) {
            fin = "Whoops! There goes my third Giant!";
        }
        else if (rand == 1) {
            fin = "Down to one Giant!";
        }
        return fin;
    }
    
    public String randCom() {
        final String fin = "";
        return "";
    }
    
    public static BukkitTask playHBS(final World w) {
        return new BukkitRunnable() {
            public void run() {
                for (final Player p : w.getPlayers()) {
                    p.playSound(p.getLocation(), "mob.guardian.elder.hit", 1.0f, 0.0f);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 19L);
    }
    
    static {
        DIALOUGE_BOSS = (Map)new HashMap();
        SadanReach = (Map)new HashMap();
        SadanP1 = (Map)new HashMap();
        SadanP2 = (Map)new HashMap();
        SadanInterest = (Map)new HashMap();
        SadanGiantsCount = (Map)new HashMap();
        IsMusicPlaying = (Map)new HashMap();
        BBRunning = (Map)new HashMap();
        BossRun = (Map)new HashMap();
        SadanCurrentPhase = (Map)new HashMap();
        IsSadanDied = (Map)new HashMap();
    }
}
