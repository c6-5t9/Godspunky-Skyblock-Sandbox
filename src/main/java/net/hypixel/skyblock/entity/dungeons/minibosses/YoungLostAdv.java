package net.hypixel.skyblock.entity.dungeons.minibosses;

import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import net.hypixel.skyblock.entity.SEntityEquipment;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.EntityLiving;
import java.util.Iterator;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class YoungLostAdv extends BaseZombie
{
    private boolean isEating;
    private boolean isBowing;
    private boolean EatingCooldown;
    private boolean CDDR;
    
    public YoungLostAdv() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
        this.CDDR = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lLost Adventurer");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 5.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 4280000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "adventuure", null, false);
        final PlayerWatcher skywatch = pl.getWatcher();
        final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)80);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (YoungLostAdv.this.isEating) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    Sputnik.sendEatingAnimation(entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 4L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.getHealth() < entity.getMaxHealth() / 2.0 && !YoungLostAdv.this.EatingCooldown && !YoungLostAdv.this.isEating) {
                    YoungLostAdv.this.EatingCooldown = true;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    YoungLostAdv.this.isBowing = false;
                    SUtil.delay(() -> YoungLostAdv.this.isEating = true, 5L);
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    new BukkitRunnable() {
                        public void run() {
                            if (entity.isDead()) {
                                return;
                            }
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            entity.getWorld().playSound(entity.getLocation(), Sound.BURP, 1.0f, 1.0f);
                            final double healamount = YoungLostAdv.this.getEntityMaxHealth() * SUtil.random(40, 60) / 100.0;
                            if (!entity.isDead()) {
                                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healamount));
                            }
                            YoungLostAdv.this.isEating = false;
                            SUtil.delay(() -> {
                                final Object val$entity = entity;
                                if (!YoungLostAdv.this.isBowing) {
                                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                                }
                                else {
                                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                                }
                            }, 5L);
                            SUtil.delay(() -> YoungLostAdv.this.EatingCooldown = false, SUtil.random(600, 800));
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 60L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 10L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    Sputnik.zero((Entity)entity);
                    this.cancel();
                    return;
                }
                final LivingEntity target1 = (LivingEntity)((CraftZombie)entity).getTarget();
                if (target1 != null) {
                    if (target1.getLocation().distance(entity.getLocation()) >= 6.0 && target1.getLocation().distance(entity.getLocation()) < 16.0) {
                        entity.teleport(entity.getLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        Sputnik.sendHeadRotation((Entity)entity, entity.getLocation().getYaw(), entity.getLocation().getPitch());
                    }
                    if ((target1.getLocation().distance(entity.getLocation()) < 6.0 || target1.getLocation().distance(entity.getLocation()) > 16.0) && !YoungLostAdv.this.isEating) {
                        SUtil.delay(() -> {
                            final Object val$entity = entity;
                            entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                        }, 0L);
                        YoungLostAdv.this.isBowing = false;
                    }
                    if (target1.getLocation().distance(entity.getLocation()) >= 6.0 && target1.getLocation().distance(entity.getLocation()) < 16.0 && !YoungLostAdv.this.isBowing && !YoungLostAdv.this.isEating) {
                        YoungLostAdv.this.isBowing = true;
                        skywatch.setRightClicking(false);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 4));
                        entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                        new BukkitRunnable() {
                            int t = 0;
                            int atkCharge = 20;
                            double bowPower = 2.2;
                            boolean crit = true;
                            
                            public void run() {
                                if (target1.getLocation().distance(entity.getLocation()) <= 10.0) {
                                    this.atkCharge = 10;
                                    this.bowPower = 1.1;
                                    this.crit = false;
                                }
                                ++this.t;
                                if (!YoungLostAdv.this.isBowing) {
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    this.cancel();
                                    return;
                                }
                                if (this.t == 5) {
                                    if (!YoungLostAdv.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(true);
                                }
                                if (this.t == this.atkCharge) {
                                    if (!YoungLostAdv.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(false);
                                }
                                if (this.t >= this.atkCharge + 1) {
                                    if (!YoungLostAdv.this.isBowing) {
                                        return;
                                    }
                                    final Location location = entity.getEyeLocation().add(entity.getEyeLocation().getDirection().toLocation(entity.getWorld()));
                                    final Location l = location.clone();
                                    l.setYaw(location.getYaw());
                                    final Arrow arr = entity.getWorld().spawnArrow(l, l.getDirection(), (float)this.bowPower, 1.6f);
                                    arr.setShooter((ProjectileSource)entity);
                                    if (!this.crit) {
                                        arr.setCritical(SUtil.random(0, 1) == 1);
                                    }
                                    else {
                                        arr.setCritical(true);
                                    }
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    YoungLostAdv.this.isBowing = false;
                                }
                            }
                        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
                    }
                    if (target1.getLocation().distance(entity.getLocation()) <= 5.0 && !YoungLostAdv.this.isBowing && !YoungLostAdv.this.isEating) {
                        if (SUtil.random(0, 100) > 30) {
                            return;
                        }
                        if (YoungLostAdv.this.CDDR) {
                            return;
                        }
                        YoungLostAdv.this.CDDR = true;
                        skywatch.setRightClicking(true);
                        YoungLostAdv.this.playPar(entity.getEyeLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.0f);
                        for (final Entity e : target1.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 3.0, 3.0, 3.0)) {
                            if (e instanceof Player) {
                                final Player player = (Player)e;
                                player.sendMessage(Sputnik.trans("&cLost Adventurer &aused &6Dragon's Breath &aon you!"));
                                player.setVelocity(entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1.0).multiply(4.0));
                                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                                if (statistics == null) {
                                    return;
                                }
                                final double defense = statistics.getDefense().addAll();
                                final int dmglater = (int)Math.round(YoungLostAdv.this.getDamageDealt() * 3.0 - YoungLostAdv.this.getDamageDealt() * 3.0 * (defense / (defense + 100.0)));
                                User.getUser(player.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)entity);
                                ((LivingEntity)e).damage(1.0E-6, (Entity)null);
                            }
                        }
                        SUtil.delay(() -> {
                            final Object val$skywatch = skywatch;
                            if (!YoungLostAdv.this.isBowing) {
                                skywatch.setRightClicking(false);
                            }
                        }, 20L);
                        SUtil.delay(() -> YoungLostAdv.this.CDDR = false, 250L);
                    }
                }
                else if (!YoungLostAdv.this.isEating) {
                    YoungLostAdv.this.isBowing = false;
                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
        new BukkitRunnable() {
            Location loc = entity.getLocation();
            final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
            
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                this.loc.setY(0.0);
                this.nms.setSprinting(false);
                final Location loc2 = entity.getLocation();
                loc2.setY(0.0);
                if (entity.hasMetadata("frozen")) {
                    return;
                }
                if (((CraftZombie)entity).getTarget() == null) {
                    return;
                }
                if (((CraftZombie)entity).getTarget().getWorld() != entity.getWorld()) {
                    return;
                }
                if (((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation()) <= 4.0 || YoungLostAdv.this.isEating || YoungLostAdv.this.isBowing) {
                    return;
                }
                if (this.loc.distance(loc2) >= 0.2) {
                    this.nms.setSprinting(true);
                    if (entity.isOnGround() && this.loc.distance(loc2) >= 0.5) {
                        double motY = 0.4199999868869782;
                        double motX = 0.0;
                        double motZ = 0.0;
                        if (this.nms.hasEffect(MobEffectList.JUMP)) {
                            motY += (this.nms.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.2f;
                        }
                        if (this.nms.isSprinting()) {
                            final float f = this.nms.yaw * 0.01745329f;
                            motX -= MathHelper.sin(f) * 0.9f;
                            motZ += MathHelper.cos(f) * 0.9f;
                        }
                        entity.setVelocity(new Vector(motX, motY, motZ));
                    }
                    this.loc = entity.getLocation().clone();
                    return;
                }
                this.nms.setSprinting(false);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 7L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (YoungLostAdv.this.isEating) {
                        continue;
                    }
                    if (YoungLostAdv.this.isBowing) {
                        continue;
                    }
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
                    if (target.getNoDamageTicks() == 7) {
                        continue;
                    }
                    if (SUtil.random(0, 10) > 8) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entities.getLocation()).toVector()));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        final Entity en = (Entity)sEntity.getEntity();
        final Vector v = new Vector(0, 0, 0);
        SUtil.delay(() -> en.setVelocity(v), 1L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()), SUtil.enchant(SItem.of(SMaterial.YOUNG_DRAGON_HELMET).getStack()), SUtil.enchant(SItem.of(SMaterial.YOUNG_DRAGON_CHESTPLATE).getStack()), SUtil.enchant(SItem.of(SMaterial.YOUNG_DRAGON_LEGGINGS).getStack()), SUtil.enchant(SItem.of(SMaterial.YOUNG_DRAGON_BOOTS).getStack()));
    }
    
    @Override
    public boolean isBaby() {
        return false;
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
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.45;
    }
    
    public void playPar(final Location l) {
        final ConeEffect Effect = new ConeEffect(SkyBlock.effectManager);
        Effect.setLocation(l.clone().add(l.getDirection().normalize().multiply(-0.25)).add(0.0, -0.1, 0.0));
        Effect.particle = ParticleEffect.FLAME;
        Effect.angularVelocity = 0.39269908169872414;
        Effect.lengthGrow = 0.025f;
        Effect.particles = 30;
        Effect.period = 3;
        Effect.iterations = 5;
        Effect.start();
    }
}
