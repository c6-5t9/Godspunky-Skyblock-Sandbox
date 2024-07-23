package net.hypixel.skyblock.entity.dungeons.minibosses;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.Potion;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import net.hypixel.skyblock.entity.SEntityEquipment;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.entity.Arrow;
import org.bukkit.projectiles.ProjectileSource;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Random;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Effect;
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

public class AngryArchaeologist extends BaseZombie
{
    private boolean isSplashing;
    private boolean isBowing;
    private boolean startedBattle;
    private boolean SplashCooldown;
    private boolean s;
    
    public AngryArchaeologist() {
        this.isSplashing = false;
        this.isBowing = false;
        this.startedBattle = false;
        this.SplashCooldown = false;
        this.s = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lAngry Archaeologist");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.5E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 5300000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTYxMjAzMDY5NDA5OSwKICAicHJvZmlsZUlkIiA6ICJmNWQwYjFhZTQxNmU0YTE5ODEyMTRmZGQzMWU3MzA1YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDYXRjaFRoZVdhdmUxMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNDhjNzgzNDU4ZTRjZjg1MThlOGFiNTg2M2ZiYzRjYjk0OGY5MDU2OGVlYjlhNjBkMTZjNGZkZTJiOTZjMDMzIgogICAgfQogIH0KfQ==", "h0EcebQKYgqarHvlkbkkkRN798ir/crHJD4PUtLWNgohxOCk0WbtPu5YxQpmCL75Y6I2Y0vVQvic7x2r4vfMUu5z0O5dfjUXwpXQ6zWYdmHIbeg796EqUsdr1VJlPHMY/PVYle+NoYflwssIXYqLOWqswaBB4cz6qfyinujYoU6wVhGbONns7h/mpCM1r+gyua0hP9g+kjgslGebpDtkQRtv/kZpJ5+19cM5KT12KmjBGlTwsmiP+RfEINt5oGv2p12wqwv0CC5TFqB+/SM1yjYcEdWXQfzmsnC9nzIfgEHpNquKiX2pcGfVPvKgjkkLkO23nnQ0e2KOfIhLKHlyHcESd/lwGP9Ea/i+JVtZMEMUmuU3lQU+ywDMCQiGNEnB9MFlDdA6LBc2mwZKYShyQNgEveXxV2V1j8dt5ctKe7ANBMrCKXRjIO0TcHv2q/PJ9GwEuSfRNwdZp88gkbb79VV+7R4nkzAzEmNpRUxpB4P0qYDpMNCaC+NYEjHrzUr3hiD3tyHQzWHqvOJYYkor5kxGBoE19lZNxfVOEv9K6dIiSAPAtyYbRc9PVL9DUwlshfQO+kYwymSfZqVqW8CUafIA2NtIpIshsuOigqPMYIJv/p1HfGZVSGZ2B1Zmb/DQ9QoTLPqv+ExZ/zMMAqLQB+aB1DL5qfMABNOYhyfSQ2c=", true);
        final PlayerWatcher skywatch = pl.getWatcher();
        final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)95);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (int i = 0; 20 > i; ++i) {
                    entity.getWorld().spigot().playEffect(entity.getLocation().clone().add(0.0, 0.25, 0.0), Effect.MAGIC_CRIT, 0, 1, (float)SUtil.random(-0.5, 0.5), (float)SUtil.random(0.0, 0.6), (float)SUtil.random(-0.5, 0.5), 0.0f, 1, 20);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 15L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (null != ((CraftZombie)entity).getTarget() && !AngryArchaeologist.this.s) {
                    final Random random = new Random();
                    AngryArchaeologist.this.s = true;
                    entity.getEquipment().setItemInHand(AngryArchaeologist.getStrPot());
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    final BukkitTask bkt = new BukkitRunnable() {
                        public void run() {
                            entity.getEquipment().setItemInHand(AngryArchaeologist.getStrPot());
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                            entity.getWorld().playSound(entity.getLocation(), Sound.DRINK, 0.5f + 0.5f * random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
                        }
                    }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 4L);
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        entity.getEquipment().setItemInHand(new ItemStack(Material.GLASS_BOTTLE, 1));
                        bkt.cancel();
                    }, 60L);
                    SUtil.delay(() -> {
                        final Object val$entity2 = entity;
                        entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.DIAMOND_SWORD).getStack()));
                        entity.removePotionEffect(PotionEffectType.SLOW);
                        AngryArchaeologist.this.startedBattle = true;
                    }, 65L);
                }
                if (AngryArchaeologist.this.isSplashing) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 6));
                    entity.getEquipment().setItemInHand(AngryArchaeologist.getPot());
                    final Location h = entity.getLocation();
                    h.setPitch(90.0f);
                    entity.teleport(h);
                    AngryArchaeologist.sendHeadRotation((Entity)entity, entity.getLocation().getYaw(), 90.0f);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.getHealth() < entity.getMaxHealth() / 2.0 && !AngryArchaeologist.this.SplashCooldown && !AngryArchaeologist.this.isSplashing) {
                    final float perv = entity.getLocation().getPitch();
                    AngryArchaeologist.this.SplashCooldown = true;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 6));
                    AngryArchaeologist.this.isBowing = false;
                    final Location h = entity.getLocation();
                    h.setPitch(90.0f);
                    entity.teleport(h);
                    SUtil.delay(() -> AngryArchaeologist.this.isSplashing = true, 5L);
                    entity.getEquipment().setItemInHand(AngryArchaeologist.getPot());
                    final Location loc = entity.getEyeLocation();
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        if (!entity.isDead()) {
                            AngryArchaeologist.spawnHealthPotion(entity.getEyeLocation(), (Entity)entity);
                        }
                    }, 5L);
                    SUtil.delay(() -> {
                        final Object val$entity2 = entity;
                        if (!entity.isDead()) {
                            AngryArchaeologist.spawnHealthPotion(entity.getEyeLocation(), (Entity)entity);
                        }
                    }, 10L);
                    SUtil.delay(() -> {
                        final Object val$entity3 = entity;
                        if (!entity.isDead()) {
                            AngryArchaeologist.spawnHealthPotion(entity.getEyeLocation(), (Entity)entity);
                        }
                    }, 15L);
                    SUtil.delay(() -> {
                        final Object val$entity4 = entity;
                        if (!entity.isDead()) {
                            AngryArchaeologist.spawnHealthPotion(entity.getEyeLocation(), (Entity)entity);
                        }
                    }, 20L);
                    SUtil.delay(() -> {
                        final Object val$entity5 = entity;
                        if (!entity.isDead()) {
                            AngryArchaeologist.spawnHealthPotion(entity.getEyeLocation(), (Entity)entity);
                        }
                    }, 25L);
                    new BukkitRunnable() {
                        public void run() {
                            if (entity.isDead()) {
                                return;
                            }
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            AngryArchaeologist.this.isSplashing = false;
                            AngryArchaeologist.sendHeadRotation((Entity)entity, entity.getLocation().getYaw(), perv);
                            final Location h = entity.getLocation();
                            h.setPitch(perv);
                            entity.teleport(h);
                            SUtil.delay(() -> {
                                final Object val$entity = entity;
                                if (!AngryArchaeologist.this.isBowing) {
                                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.DIAMOND_SWORD).getStack()));
                                }
                                else {
                                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                                }
                            }, 3L);
                            SUtil.delay(() -> AngryArchaeologist.this.SplashCooldown = false, SUtil.random(500, 900));
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 40L);
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
                if (!AngryArchaeologist.this.startedBattle) {
                    return;
                }
                final LivingEntity target1 = (LivingEntity)((CraftZombie)entity).getTarget();
                if (null != target1) {
                    if (6.0 <= target1.getLocation().distance(entity.getLocation()) && 16.0 > target1.getLocation().distance(entity.getLocation()) && !AngryArchaeologist.this.isSplashing) {
                        entity.teleport(entity.getLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        Sputnik.sendHeadRotation((Entity)entity, entity.getLocation().getYaw(), entity.getLocation().getPitch());
                    }
                    if ((6.0 > target1.getLocation().distance(entity.getLocation()) || 16.0 < target1.getLocation().distance(entity.getLocation())) && !AngryArchaeologist.this.isSplashing) {
                        SUtil.delay(() -> {
                            final Object val$entity = entity;
                            entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.DIAMOND_SWORD).getStack()));
                        }, 0L);
                        AngryArchaeologist.this.isBowing = false;
                    }
                    if (6.0 <= target1.getLocation().distance(entity.getLocation()) && 16.0 > target1.getLocation().distance(entity.getLocation()) && !AngryArchaeologist.this.isBowing && !AngryArchaeologist.this.isSplashing) {
                        AngryArchaeologist.this.isBowing = true;
                        skywatch.setRightClicking(false);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 4));
                        entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                        new BukkitRunnable() {
                            int t = 0;
                            int atkCharge = 20;
                            double bowPower = 2.2;
                            boolean crit = true;
                            
                            public void run() {
                                if (10.0 >= target1.getLocation().distance(entity.getLocation())) {
                                    this.atkCharge = 10;
                                    this.bowPower = 1.1;
                                    this.crit = false;
                                }
                                ++this.t;
                                if (!AngryArchaeologist.this.isBowing) {
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    this.cancel();
                                    return;
                                }
                                if (5 == this.t) {
                                    if (!AngryArchaeologist.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(true);
                                }
                                if (this.t == this.atkCharge) {
                                    if (!AngryArchaeologist.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(false);
                                }
                                if (this.t >= this.atkCharge + 1) {
                                    if (!AngryArchaeologist.this.isBowing) {
                                        return;
                                    }
                                    final Location location = entity.getEyeLocation().add(entity.getEyeLocation().getDirection().toLocation(entity.getWorld()));
                                    final Location l = location.clone();
                                    l.setYaw(location.getYaw());
                                    final Arrow arr = entity.getWorld().spawnArrow(l, l.getDirection(), (float)this.bowPower, 1.6f);
                                    arr.setShooter((ProjectileSource)entity);
                                    if (!this.crit) {
                                        arr.setCritical(1 == SUtil.random(0, 1));
                                    }
                                    else {
                                        arr.setCritical(true);
                                    }
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    AngryArchaeologist.this.isBowing = false;
                                }
                            }
                        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
                    }
                }
                else if (!AngryArchaeologist.this.isSplashing) {
                    AngryArchaeologist.this.isBowing = false;
                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.DIAMOND_SWORD).getStack()));
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
                if (null == ((CraftZombie)entity).getTarget()) {
                    return;
                }
                if (((CraftZombie)entity).getTarget().getWorld() != entity.getWorld()) {
                    return;
                }
                if (4.0 >= ((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation()) || AngryArchaeologist.this.isSplashing || AngryArchaeologist.this.isBowing || !AngryArchaeologist.this.startedBattle) {
                    return;
                }
                if (0.2 <= this.loc.distance(loc2)) {
                    this.nms.setSprinting(true);
                    if (entity.isOnGround() && 0.5 <= this.loc.distance(loc2)) {
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
                    if (AngryArchaeologist.this.isSplashing) {
                        continue;
                    }
                    if (AngryArchaeologist.this.isBowing) {
                        continue;
                    }
                    if (!AngryArchaeologist.this.startedBattle) {
                        continue;
                    }
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target = (Player)entities;
                    if (GameMode.CREATIVE == target.getGameMode()) {
                        continue;
                    }
                    if (GameMode.SPECTATOR == target.getGameMode()) {
                        continue;
                    }
                    if (target.hasMetadata("NPC")) {
                        continue;
                    }
                    if (7 == target.getNoDamageTicks()) {
                        continue;
                    }
                    if (8 < SUtil.random(0, 10)) {
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
        return new SEntityEquipment(SUtil.enchant(SItem.of(SMaterial.DIAMOND_SWORD).getStack()), SUtil.enchant(SItem.of(SMaterial.DIAMOND_HELMET).getStack()), SUtil.enchant(SItem.of(SMaterial.DIAMOND_CHESTPLATE).getStack()), SUtil.enchant(SItem.of(SMaterial.DIAMOND_LEGGINGS).getStack()), SUtil.enchant(SItem.of(SMaterial.DIAMOND_BOOTS).getStack()));
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
        return 0.35;
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
    
    public static void spawnHealthPotion(final Location location, final Entity en) {
        final World world = location.getWorld();
        final ItemStack item = new ItemStack(Material.POTION, 1);
        final Potion pot = new Potion(1);
        pot.setType(PotionType.INSTANT_HEAL);
        pot.setSplash(true);
        pot.apply(item);
        final ThrownPotion thrownPotion = (ThrownPotion)world.spawnEntity(location.clone().add(0.0, -0.5, 0.0), EntityType.SPLASH_POTION);
        thrownPotion.setShooter((ProjectileSource)en);
        thrownPotion.setItem(item);
    }
    
    public static ItemStack getPot() {
        final ItemStack item = new ItemStack(Material.POTION, 1);
        final Potion pot = new Potion(1);
        pot.setType(PotionType.INSTANT_HEAL);
        pot.setSplash(true);
        pot.apply(item);
        return item;
    }
    
    public static ItemStack getStrPot() {
        final ItemStack item = new ItemStack(Material.POTION, 1);
        final Potion pot = new Potion(1);
        pot.setType(PotionType.STRENGTH);
        pot.setSplash(false);
        pot.apply(item);
        return item;
    }
    
    public static void sendHeadRotation(final Entity e, final float yaw, final float pitch) {
        final net.minecraft.server.v1_8_R3.Entity pl = (net.minecraft.server.v1_8_R3.Entity)((CraftZombie)e).getHandle();
        pl.setLocation(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), yaw, pitch);
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(pl);
        Sputnik.sendPacket(e.getWorld(), (Packet)packet);
    }
}
