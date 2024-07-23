package net.hypixel.skyblock.entity;

import java.util.HashMap;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Skeleton;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import java.util.List;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.UUID;
import java.util.ArrayList;
import org.bukkit.entity.Entity;
import java.util.Map;

public interface EntityFunction
{
    public static final Map<Entity, ArrayList<UUID>> FIRST_STRIKE_MAP = new HashMap();
    
    default void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
    }
    
    default void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
    }
    
    default List<EntityDrop> drops() {
        return (List<EntityDrop>)new ArrayList();
    }
    
    default boolean tick(final LivingEntity entity) {
        return false;
    }
    
    default void onSpawnNameTag(final LivingEntity entity, final SEntity sEntity, final SEntityType specType, final Object... params) {
        final ArmorStand hologram1 = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, 0.0, 0.0), (Class)ArmorStand.class);
        hologram1.setVisible(false);
        hologram1.setGravity(false);
        hologram1.setSmall(true);
        hologram1.setMarker(true);
        hologram1.setBasePlate(false);
        hologram1.setCustomNameVisible(false);
        entity.setPassenger((Entity)hologram1);
        if (!entity.hasMetadata("LD")) {
            hologram1.remove();
        }
        EntityFunction.FIRST_STRIKE_MAP.put((Object)entity, (Object)new ArrayList());
        if (entity.hasMetadata("notDisplay")) {
            return;
        }
        if (entity.getType() == EntityType.ARMOR_STAND) {
            return;
        }
        if (entity.getType() == EntityType.ENDER_DRAGON) {
            return;
        }
        if (entity.getType() == EntityType.ENDER_CRYSTAL) {
            return;
        }
        final net.minecraft.server.v1_8_R3.Entity e = ((CraftEntity)entity).getHandle();
        double height_ = e.getBoundingBox().e - e.getBoundingBox().b;
        final Object instance = specType.instance(params);
        final EntityStatistics statistics = (EntityStatistics)instance;
        if (entity.getType() == EntityType.SKELETON || entity.hasMetadata("SKEL")) {
            height_ += 0.2;
        }
        if (entity.getType() == EntityType.SKELETON && ((CraftSkeleton)entity).getSkeletonType() == Skeleton.SkeletonType.WITHER) {
            height_ -= 0.2;
        }
        if (entity.hasMetadata("LD")) {
            height_ -= 0.144;
        }
        if (entity.hasMetadata("highername")) {
            height_ += 10.5;
        }
        if (entity.hasMetadata("NNP")) {
            height_ += 0.8;
        }
        if (entity.hasMetadata("NNPS")) {
            height_ += 0.8;
        }
        final double height = height_;
        final ArmorStand hologram2 = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, height, 0.0), (Class)ArmorStand.class);
        hologram2.setVisible(false);
        hologram2.setGravity(false);
        hologram2.setSmall(false);
        hologram2.setMarker(true);
        hologram2.setBasePlate(false);
        hologram2.setCustomNameVisible(true);
        final boolean hideLVL;
        final boolean hideLVLA = hideLVL = entity.hasMetadata("WATCHER_E");
        new BukkitRunnable() {
            public void run() {
                hologram2.setCustomNameVisible(!entity.hasPotionEffect(PotionEffectType.INVISIBILITY));
                if (!hologram2.getLocation().getChunk().isLoaded()) {
                    hologram2.remove();
                    this.cancel();
                    return;
                }
                if (entity.isDead()) {
                    if (entity.hasMetadata("NNP")) {
                        hologram2.remove();
                    }
                    if (entity.hasMetadata("h_sadan")) {
                        hologram2.remove();
                    }
                    EntityFunction.FIRST_STRIKE_MAP.remove((Object)entity);
                    hologram2.setCustomName(Sputnik.trans(Sputnik.entityNameTag(entity, Sputnik.buildcustomString(statistics.getEntityName(), statistics.mobLevel(), hideLVL))));
                    new BukkitRunnable() {
                        public void run() {
                            hologram2.remove();
                            hologram1.remove();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
                }
                if (hologram2.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.hasMetadata("RMHN")) {
                    hologram1.remove();
                }
                else {
                    entity.setPassenger((Entity)hologram1);
                }
                hologram2.teleport(entity.getLocation().clone().add(0.0, height, 0.0));
                hologram2.teleport(entity.getLocation().clone().add(0.0, height, 0.0));
                if (!entity.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    if (entity.hasMetadata("Boss")) {
                        hologram2.setCustomName(Sputnik.trans("&e\ufd3e " + Sputnik.entityNameTag(entity, Sputnik.buildcustomString(statistics.getEntityName(), statistics.mobLevel(), true)) + " &e\ufd3f"));
                    }
                    else if (entity.hasMetadata("NNP")) {
                        hologram2.setCustomName(statistics.getEntityName());
                    }
                    else {
                        hologram2.setCustomName(Sputnik.trans(Sputnik.entityNameTag(entity, Sputnik.buildcustomString(statistics.getEntityName(), statistics.mobLevel(), hideLVL))));
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 0L);
    }
    
    default void onSpawn(final LivingEntity entity, final SEntity sEntity) {
    }
    
    default void onAttack(final EntityDamageByEntityEvent e) {
    }
    
    default void onTarget(final SEntity sEntity, final EntityTargetLivingEntityEvent e) {
    }
    
    default void onSpawnNameTag(final LivingEntity entity, final SEntityType specType, final Object... params) {
        this.onSpawnNameTag(entity, SEntity.findSEntity((Entity)entity), specType, params);
    }
    
    default void onSpawn(final LivingEntity entity) {
        this.onSpawn(entity, SEntity.findSEntity((Entity)entity));
    }
}
