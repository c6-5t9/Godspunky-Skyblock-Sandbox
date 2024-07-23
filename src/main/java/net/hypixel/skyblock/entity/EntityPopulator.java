package net.hypixel.skyblock.entity;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Location;
import java.util.Iterator;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.region.Region;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import org.bukkit.scheduler.BukkitTask;
import net.hypixel.skyblock.features.region.RegionType;
import org.bukkit.World;
import java.util.function.Predicate;
import java.util.List;

public class EntityPopulator
{
    private static final List<EntityPopulator> POPULATORS;
    private final int amount;
    private final int max;
    private final long delay;
    private final SEntityType type;
    private final Predicate<World> condition;
    private final RegionType regionType;
    private BukkitTask task;
    private final List<SEntity> spawned;
    
    public static List<EntityPopulator> getPopulators() {
        return EntityPopulator.POPULATORS;
    }
    
    public EntityPopulator(final int amount, final int max, final long delay, final SEntityType type, final RegionType regionType, final Predicate<World> condition) {
        this.amount = amount;
        this.max = max;
        this.delay = delay;
        this.type = type;
        this.regionType = regionType;
        this.spawned = (List<SEntity>)new ArrayList();
        this.condition = condition;
        EntityPopulator.POPULATORS.add((Object)this);
    }
    
    public EntityPopulator(final int amount, final int max, final long delay, final SEntityType type, final RegionType regionType) {
        this(amount, max, delay, type, regionType, null);
    }
    
    public void start() {
        this.task = new BukkitRunnable() {
            public void run() {
                EntityPopulator.this.spawned.removeIf(sEntity -> sEntity.getEntity().isDead());
                final List<Region> regions = Region.getRegionsOfType(EntityPopulator.this.regionType);
                if (regions.isEmpty()) {
                    return;
                }
                if (Region.getPlayersWithinRegionType(EntityPopulator.this.regionType).isEmpty()) {
                    for (final SEntity s : EntityPopulator.this.spawned) {
                        s.remove();
                    }
                    EntityPopulator.this.spawned.clear();
                    return;
                }
                if (EntityPopulator.this.condition != null && !EntityPopulator.this.condition.test((Object)SUtil.getRandom(regions).getFirstLocation().getWorld())) {
                    return;
                }
                if (EntityPopulator.this.spawned.size() >= EntityPopulator.this.max) {
                    return;
                }
                for (int i = 0; i < EntityPopulator.this.amount; ++i) {
                    int attempts = 0;
                    Location available;
                    do {
                        available = SUtil.getRandom(regions).getRandomAvailableLocation();
                        ++attempts;
                    } while (available == null && attempts <= 150);
                    if (available != null) {
                        final SEntity sEntity = new SEntity(available.clone().add(0.5, 0.0, 0.5), EntityPopulator.this.type, new Object[0]);
                        EntityPopulator.this.spawned.add((Object)sEntity);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, this.delay);
        new BukkitRunnable() {
            public void run() {
                if (EntityPopulator.this.spawned.isEmpty()) {
                    return;
                }
                if (Region.getPlayersWithinRegionType(EntityPopulator.this.regionType).isEmpty()) {
                    for (final SEntity s : EntityPopulator.this.spawned) {
                        s.remove();
                    }
                    EntityPopulator.this.spawned.clear();
                }
            }
        }.runTaskTimerAsynchronously((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
    
    public void stop() {
        if (this.task == null) {
            return;
        }
        this.task.cancel();
    }
    
    public static void stopAll() {
        for (final EntityPopulator populator : EntityPopulator.POPULATORS) {
            populator.stop();
        }
    }
    
    public RegionType getRegionType() {
        return this.regionType;
    }
    
    static {
        POPULATORS = (List)new ArrayList();
    }
}
