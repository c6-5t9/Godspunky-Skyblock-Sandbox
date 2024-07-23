package net.hypixel.skyblock.entity.dungeons;

import de.slikey.effectlib.effect.ConeEffect;
import net.hypixel.skyblock.entity.SEntityEquipment;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.projectiles.ProjectileSource;
import java.util.Iterator;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.Player;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.texture.ItemTexture;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class YoungLostAdvNPC extends BaseZombie
{
    private boolean isEating;
    private boolean isBowing;
    private boolean EatingCooldown;
    private boolean CDDR;
    private final boolean naturallyHeal;
    
    public YoungLostAdvNPC() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
        this.CDDR = false;
        this.naturallyHeal = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lLost Adventurer");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 50000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "adventuure", null, false);
        final PlayerWatcher skywatch = pl.getWatcher();
        final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)60);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (YoungLostAdvNPC.this.isEating) {
                    entity.getWorld().playSound(entity.getLocation(), Sound.EAT, 1.0f, 1.0f);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 4L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (YoungLostAdvNPC.this.isEating) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    final Location loc = entity.getLocation();
                    loc.add(0.0, 1.4, 0.0);
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    loc.add(entity.getLocation().getDirection().multiply(0.5));
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (YoungLostAdvNPC.this.naturallyHeal) {
                    entity.setHealth(Math.min(entity.getMaxHealth(), entity.getMaxHealth() * 0.5 / 100.0));
                }
                if (entity.getHealth() < entity.getMaxHealth() * 3.0 / 4.0 && !YoungLostAdvNPC.this.EatingCooldown && !YoungLostAdvNPC.this.isEating) {
                    YoungLostAdvNPC.this.EatingCooldown = true;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    SUtil.delay(() -> YoungLostAdvNPC.this.isEating = true, 5L);
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    new BukkitRunnable() {
                        public void run() {
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            entity.getWorld().playSound(entity.getLocation(), Sound.BURP, 1.0f, 1.0f);
                            final double healamount = SUtil.random(30000000, 40000000);
                            if (!entity.isDead()) {
                                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healamount));
                            }
                            YoungLostAdvNPC.this.isEating = false;
                            SUtil.delay(() -> {
                                final Object val$entity = entity;
                                if (!YoungLostAdvNPC.this.isBowing) {
                                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                                }
                                else {
                                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                                }
                            }, 5L);
                            SUtil.delay(() -> YoungLostAdvNPC.this.EatingCooldown = false, SUtil.random(400, 500));
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
                    if (target1.getLocation().distance(entity.getLocation()) <= 5.0 && !YoungLostAdvNPC.this.isBowing && !YoungLostAdvNPC.this.isEating) {
                        if (SUtil.random(0, 100) > 30) {
                            return;
                        }
                        if (YoungLostAdvNPC.this.CDDR) {
                            return;
                        }
                        YoungLostAdvNPC.this.CDDR = true;
                        skywatch.setRightClicking(true);
                        YoungLostAdvNPC.this.playPar(entity.getEyeLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.0f);
                        for (final Entity e : target1.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(3.0)), 3.0, 3.0, 3.0)) {
                            if (e instanceof Player) {
                                final Player player = (Player)e;
                                player.sendMessage(Sputnik.trans("&cLost Adventurer &aused &6Dragon's Breath &aon you!"));
                                player.setVelocity(entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1.0).multiply(5.0));
                                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                                if (statistics == null) {
                                    return;
                                }
                                final double defense = statistics.getDefense().addAll();
                                final int dmglater = (int)Math.round(70000.0 - 70000.0 * (defense / (defense + 100.0)));
                                User.getUser(player.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)entity);
                                ((LivingEntity)e).damage(1.0E-6, (Entity)null);
                            }
                        }
                        SUtil.delay(() -> {
                            final Object val$skywatch = skywatch;
                            if (!YoungLostAdvNPC.this.isBowing) {
                                skywatch.setRightClicking(false);
                            }
                        }, 10L);
                        SUtil.delay(() -> YoungLostAdvNPC.this.CDDR = false, 250L);
                    }
                    if (target1.getLocation().distance(entity.getLocation()) >= 7.0) {
                        entity.teleport(entity.getLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                    }
                    if ((target1.getLocation().distance(entity.getLocation()) >= 7.0 & !YoungLostAdvNPC.this.isBowing) && !YoungLostAdvNPC.this.isEating) {
                        YoungLostAdvNPC.this.isBowing = true;
                        skywatch.setRightClicking(false);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 3));
                        entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                        SUtil.delay(() -> {
                            final Object val$skywatch2 = skywatch;
                            if (YoungLostAdvNPC.this.isBowing) {
                                skywatch.setRightClicking(true);
                            }
                        }, 5L);
                        SUtil.delay(() -> {
                            final Object val$skywatch3 = skywatch;
                            if (YoungLostAdvNPC.this.isBowing) {
                                skywatch.setRightClicking(false);
                            }
                        }, 20L);
                        SUtil.delay(() -> {
                            final Object val$entity = entity;
                            final Object val$skywatch4 = skywatch;
                            if (YoungLostAdvNPC.this.isBowing) {
                                final Location location = entity.getEyeLocation().add(entity.getEyeLocation().getDirection().toLocation(entity.getWorld()));
                                final Location l = location.clone();
                                l.setYaw(location.getYaw());
                                entity.getWorld().spawnArrow(l, l.getDirection(), 2.2f, 1.6f).setShooter((ProjectileSource)entity);
                                skywatch.setRightClicking(false);
                                YoungLostAdvNPC.this.isBowing = false;
                            }
                        }, 21L);
                    }
                    else if (target1.getLocation().distance(entity.getLocation()) < 7.0 && !YoungLostAdvNPC.this.isEating) {
                        SUtil.delay(() -> {
                            final Object val$entity2 = entity;
                            entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                        }, 0L);
                        YoungLostAdvNPC.this.isBowing = false;
                    }
                }
                else if (!YoungLostAdvNPC.this.isEating) {
                    YoungLostAdvNPC.this.isBowing = false;
                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (YoungLostAdvNPC.this.isEating) {
                        continue;
                    }
                    if (YoungLostAdvNPC.this.isBowing) {
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
        return 5570.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.4;
    }
    
    public void playPar(final Location l) {
        final ConeEffect Effect = new ConeEffect(SkyBlock.effectManager);
        Effect.setLocation(l.clone().add(l.getDirection().normalize().multiply(-0.25)).add(0.0, -0.1, 0.0));
        Effect.particle = de.slikey.effectlib.util.ParticleEffect.FLAME;
        Effect.angularVelocity = 0.39269908169872414;
        Effect.lengthGrow = 0.025f;
        Effect.particles = 30;
        Effect.period = 3;
        Effect.iterations = 5;
        Effect.start();
    }
}
