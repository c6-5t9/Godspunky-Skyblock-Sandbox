package net.hypixel.skyblock.entity.dungeons.minibosses;

import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
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

public class HolyLostAdv extends BaseZombie
{
    private boolean isEating;
    private boolean isBowing;
    private boolean EatingCooldown;
    private boolean CDDR;
    
    public HolyLostAdv() {
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
        return 6.1E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 5800000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTU4OTEzOTA2Mjc5NSwKICAicHJvZmlsZUlkIiA6ICJmZDYwZjM2ZjU4NjE0ZjEyYjNjZDQ3YzJkODU1Mjk5YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWFkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNhMjZjZjY2YWY0MmE0OGY2YmU1MmEwYTQ4YThlYjU5OGYzYTIzNjM5YjdhNWE4ZWUxNzFjMTM5OTllZTk0YzkiCiAgICB9CiAgfQp9", "CNO1y2pwfuWv79KqIVVbE7ZGWvHbOJe1zFWLzTH+kScgOpuj2ihu1ETrhtSpNYV48x/pSHBcZmlzOBtXCa/GBuS1BjKlb7rzo4naI5vWbJ2R2X7c2rFHficym50VPPQmh12NTuEYhrZbhfaMBZNkcDfKLLi2r89lrYGZhSDGsa1LExlWmdIWPQx4B3hfC+MTBYrIraB2TDWZd2SonwkxRWdMbQgSKQv1SPTrOcQ80+tu9m3QpmldHYvpkVohDb/30xdmqa7Q+b3K4H8+yPRaIuxod+6pNsceyGpF+Ih5W17qpn0xNt/Gyb2SkvcJMvSwZ15wtifRI7unKKXdEVMmATqAcUwUTRxO64mO1nVyJO9eodeKABWqePBdGHH7xQZ2wj5Wmx0Rva5ydKsyxNy1bjL/dnNwAlkbtZmCRsIEdgf2z58URlHLJHbLw/luMYJ9+B4uWs2QqzPVQQFNOnvUw9wvURtqof8zSKL4XoVCLKMIpy/gSOmRdZn2PbM83zNucQe/HFPstUZ19O1GakE4evuujijHcMEjhS9HSyZCCKUn4KttBcLvycPc0vloTkyZG07fN+aZbj+Q7KiLdHMn4z++gB54whE6ClHCFYwN8oV/ngHtmz0uWkS8A+ci1kxKu0TToO0lohYn/lzznT4Vv9gC0QkM846tGqn6JjAwQ8Y=", true);
        final PlayerWatcher skywatch = pl.getWatcher();
        final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)90);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (HolyLostAdv.this.isEating) {
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
                if (entity.getHealth() < entity.getMaxHealth() / 2.0 && !HolyLostAdv.this.EatingCooldown && !HolyLostAdv.this.isEating) {
                    HolyLostAdv.this.EatingCooldown = true;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    HolyLostAdv.this.isBowing = false;
                    SUtil.delay(() -> HolyLostAdv.this.isEating = true, 5L);
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    new BukkitRunnable() {
                        public void run() {
                            if (entity.isDead()) {
                                return;
                            }
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            entity.getWorld().playSound(entity.getLocation(), Sound.BURP, 1.0f, 1.0f);
                            final double healamount = HolyLostAdv.this.getEntityMaxHealth() * SUtil.random(50, 70) / 100.0;
                            if (!entity.isDead()) {
                                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healamount));
                            }
                            HolyLostAdv.this.isEating = false;
                            SUtil.delay(() -> {
                                final Object val$entity = entity;
                                if (!HolyLostAdv.this.isBowing) {
                                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                                }
                                else {
                                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                                }
                            }, 5L);
                            SUtil.delay(() -> HolyLostAdv.this.EatingCooldown = false, SUtil.random(600, 800));
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
                    if ((target1.getLocation().distance(entity.getLocation()) < 6.0 || target1.getLocation().distance(entity.getLocation()) > 16.0) && !HolyLostAdv.this.isEating) {
                        SUtil.delay(() -> {
                            final Object val$entity = entity;
                            entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                        }, 0L);
                        HolyLostAdv.this.isBowing = false;
                    }
                    if (target1.getLocation().distance(entity.getLocation()) >= 6.0 && target1.getLocation().distance(entity.getLocation()) < 16.0 && !HolyLostAdv.this.isBowing && !HolyLostAdv.this.isEating) {
                        HolyLostAdv.this.isBowing = true;
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
                                if (!HolyLostAdv.this.isBowing) {
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    this.cancel();
                                    return;
                                }
                                if (this.t == 5) {
                                    if (!HolyLostAdv.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(true);
                                }
                                if (this.t == this.atkCharge) {
                                    if (!HolyLostAdv.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(false);
                                }
                                if (this.t >= this.atkCharge + 1) {
                                    if (!HolyLostAdv.this.isBowing) {
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
                                    HolyLostAdv.this.isBowing = false;
                                }
                            }
                        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
                    }
                    if (target1.getLocation().distance(entity.getLocation()) <= 5.0 && !HolyLostAdv.this.isBowing && !HolyLostAdv.this.isEating) {
                        if (SUtil.random(0, 100) > 30) {
                            return;
                        }
                        if (HolyLostAdv.this.CDDR) {
                            return;
                        }
                        HolyLostAdv.this.CDDR = true;
                        skywatch.setRightClicking(true);
                        HolyLostAdv.this.playPar(entity.getEyeLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
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
                                final int dmglater = (int)Math.round(HolyLostAdv.this.getDamageDealt() * 3.0 - HolyLostAdv.this.getDamageDealt() * 3.0 * (defense / (defense + 100.0)));
                                User.getUser(player.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)entity);
                                ((LivingEntity)e).damage(1.0E-6, (Entity)null);
                            }
                        }
                        SUtil.delay(() -> {
                            final Object val$skywatch = skywatch;
                            if (!HolyLostAdv.this.isBowing) {
                                skywatch.setRightClicking(false);
                            }
                        }, 20L);
                        SUtil.delay(() -> HolyLostAdv.this.CDDR = false, 250L);
                    }
                }
                else if (!HolyLostAdv.this.isEating) {
                    HolyLostAdv.this.isBowing = false;
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
                if (((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation()) <= 4.0 || HolyLostAdv.this.isEating || HolyLostAdv.this.isBowing) {
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
                    if (HolyLostAdv.this.isEating) {
                        continue;
                    }
                    if (HolyLostAdv.this.isBowing) {
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
        return new SEntityEquipment(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()), SUtil.enchant(SUtil.getSkullURLStack("Holy Dragon Helmet", "d13ad4fa48118d10a1ef42fd6d585472203bd88a98a087ab43182aa0493ea842", 1, "")), SUtil.enchant(st(4706631, Material.LEATHER_CHESTPLATE, "Holy Dragon Chestplate")), SUtil.enchant(st(4706631, Material.LEATHER_LEGGINGS, "Holy Dragon Leggings")), SUtil.enchant(st(4706631, Material.LEATHER_BOOTS, "Holy Dragon Boots")));
    }
    
    public static ItemStack st(final int hexcolor, final Material m, final String name) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
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
        return 0.25;
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
