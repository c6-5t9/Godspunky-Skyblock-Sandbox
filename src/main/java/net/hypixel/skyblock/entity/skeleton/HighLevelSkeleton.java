package net.hypixel.skyblock.entity.skeleton;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Location;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Effect;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;

public class HighLevelSkeleton implements EntityStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Skeleton";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 200.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 47.0;
    }
    
    @Override
    public double getXPDropped() {
        return 6.0;
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        final Item item = sEntity.getEntity().getWorld().dropItem(sEntity.getEntity().getLocation(), new ItemStack(Material.BONE, 2));
        new BukkitRunnable() {
            public void run() {
                if (item.isDead()) {
                    return;
                }
                final Location spawn = item.getLocation().clone().add(0.0, 1.0, 0.0);
                for (int i = 0; i < 5; ++i) {
                    item.getWorld().spigot().playEffect(spawn, Effect.PARTICLE_SMOKE, 0, 1, 0.0f, 0.0f, 0.0f, 0.0f, 1, 20);
                }
                new SEntity(spawn, SEntityType.HIGH_LEVEL_SKELETON, new Object[0]);
                item.remove();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
    }
}
