package net.hypixel.skyblock.entity.nether;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMagmaCube;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SlimeStatistics;

public class LargeMagmaCube implements SlimeStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Magma Cube";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 300.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 150.0;
    }
    
    @Override
    public double getXPDropped() {
        return 4.0;
    }
    
    @Override
    public void onTarget(final SEntity sEntity, final EntityTargetLivingEntityEvent e) {
        final LivingEntity entity = (LivingEntity)e.getEntity();
        final Entity found = (Entity)e.getTarget();
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final Entity target = (Entity)((CraftMagmaCube)entity).getHandle().getGoalTarget().getBukkitEntity();
                if (!found.equals(target)) {
                    this.cancel();
                    return;
                }
                for (int i = 0; i < 3; ++i) {
                    new BukkitRunnable() {
                        public void run() {
                            if (entity.isDead()) {
                                this.cancel();
                                return;
                            }
                            final Fireball fireball = (Fireball)entity.getWorld().spawn(entity.getEyeLocation().add(entity.getEyeLocation().getDirection().multiply(3.0)), (Class)Fireball.class);
                            fireball.setMetadata("magma", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)sEntity));
                            fireball.setDirection(target.getLocation().getDirection().multiply(-1.0).normalize());
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), (long)((i + 1) * 10));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 60L, 100L);
    }
    
    @Override
    public int getSize() {
        return 6;
    }
    
    @Override
    public boolean split() {
        return false;
    }
}
