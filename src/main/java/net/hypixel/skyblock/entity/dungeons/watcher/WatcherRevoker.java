package net.hypixel.skyblock.entity.dungeons.watcher;

import org.bukkit.util.Vector;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.entity.SEntityEquipment;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.util.SUtil;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class WatcherRevoker extends BaseZombie
{
    private boolean isBowing;
    
    public WatcherRevoker() {
        this.isBowing = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&4&lMaster Revoker");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.5E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 1450000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final HeadsOnWall h = new HeadsOnWall(EnumWatcherType.REVOKER);
        final PlayerDisguise p = Sputnik.applyPacketNPC((Entity)entity, h.value, h.signature, true);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)99);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("WATCHER_E", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        p.setReplaceSounds(false);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (((CraftZombie)entity).getTarget() != null) {
                    entity.teleport(((CraftZombie)entity).getTarget().getLocation());
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 100L, 100L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    Sputnik.zero((Entity)entity);
                    this.cancel();
                    return;
                }
                final PlayerWatcher skywatch = p.getWatcher();
                final LivingEntity target1 = (LivingEntity)((CraftZombie)entity).getTarget();
                if (target1 != null) {
                    if (target1.getLocation().distance(entity.getLocation()) >= 6.0 && target1.getLocation().distance(entity.getLocation()) < 16.0) {
                        entity.teleport(entity.getLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        Sputnik.sendHeadRotation((Entity)entity, entity.getLocation().getYaw(), entity.getLocation().getPitch());
                    }
                    if (target1.getLocation().distance(entity.getLocation()) < 6.0 || target1.getLocation().distance(entity.getLocation()) > 16.0) {
                        SUtil.delay(() -> {
                            final Object val$entity = entity;
                            entity.getEquipment().setItemInHand(SItem.of(SMaterial.GOLD_SWORD).getStack());
                        }, 0L);
                        WatcherRevoker.this.isBowing = false;
                    }
                    if (target1.getLocation().distance(entity.getLocation()) >= 6.0 && target1.getLocation().distance(entity.getLocation()) < 16.0 && !WatcherRevoker.this.isBowing) {
                        WatcherRevoker.this.isBowing = true;
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
                                if (!WatcherRevoker.this.isBowing) {
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    this.cancel();
                                    return;
                                }
                                if (this.t == 5) {
                                    if (!WatcherRevoker.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(true);
                                }
                                if (this.t == this.atkCharge) {
                                    if (!WatcherRevoker.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(false);
                                }
                                if (this.t >= this.atkCharge + 1) {
                                    if (!WatcherRevoker.this.isBowing) {
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
                                    WatcherRevoker.this.isBowing = false;
                                }
                            }
                        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
                    }
                }
                else {
                    WatcherRevoker.this.isBowing = false;
                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.GOLD_SWORD).getStack());
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
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target = (Player)entities;
                    if (WatcherRevoker.this.isBowing) {
                        continue;
                    }
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
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SItem.of(SMaterial.GOLD_SWORD).getStack(), null, SItem.of(SMaterial.IRON_CHESTPLATE).getStack(), SItem.of(SMaterial.LEATHER_LEGGINGS).getStack(), SItem.of(SMaterial.IRON_BOOTS).getStack());
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        final Entity en = (Entity)sEntity.getEntity();
        final Vector v = new Vector(0, 0, 0);
        SUtil.delay(() -> en.setVelocity(v), 1L);
    }
    
    @Override
    public void onAttack(final EntityDamageByEntityEvent e) {
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
        return 155.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.3;
    }
    
    @Override
    public int mobLevel() {
        return 540;
    }
}
