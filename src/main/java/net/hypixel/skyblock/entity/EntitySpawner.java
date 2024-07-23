package net.hypixel.skyblock.entity;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Location;
import java.util.UUID;
import org.bukkit.scheduler.BukkitTask;
import java.util.List;

public class EntitySpawner
{
    private static final List<EntitySpawner> SPAWNER_CACHE;
    private static BukkitTask SPAWNER_TASK;
    private final UUID uuid;
    private final SEntityType type;
    private final Location location;
    private SEntity active;
    
    private EntitySpawner(final UUID uuid, final SEntityType type, final Location location) {
        this.uuid = uuid;
        this.type = type;
        this.location = location;
        this.save();
    }
    
    public EntitySpawner(final SEntityType type, final Location location) {
        this.uuid = UUID.randomUUID();
        this.type = type;
        this.location = location;
        EntitySpawner.SPAWNER_CACHE.add((Object)this);
        this.save();
    }
    
    public void delete() {
        final Config spawners = SkyBlock.getPlugin().spawners;
        EntitySpawner.SPAWNER_CACHE.remove((Object)this);
        spawners.set(this.uuid.toString(), (Object)null);
        spawners.save();
    }
    
    public void save() {
        final Config spawners = SkyBlock.getPlugin().spawners;
        spawners.set(this.uuid.toString() + ".type", (Object)this.type.name());
        spawners.set((Object)this.uuid + ".location", (Object)this.location);
        spawners.save();
    }
    
    @Override
    public String toString() {
        return "EntitySpawner{uuid=" + this.uuid.toString() + ", type=" + this.type.name() + ", location=" + this.location.toString() + "}";
    }
    
    public static EntitySpawner deserialize(final String key) {
        final Config spawners = SkyBlock.getPlugin().spawners;
        return new EntitySpawner(UUID.fromString(key), SEntityType.getEntityType(spawners.getString(key + ".type")), (Location)spawners.get(key + ".location"));
    }
    
    public static List<EntitySpawner> getSpawners() {
        if (EntitySpawner.SPAWNER_CACHE.size() == 0) {
            final Config spawners = SkyBlock.getPlugin().spawners;
            for (final String key : spawners.getKeys(false)) {
                EntitySpawner.SPAWNER_CACHE.add((Object)deserialize(key));
            }
        }
        return EntitySpawner.SPAWNER_CACHE;
    }
    
    public static void startSpawnerTask() {
        if (EntitySpawner.SPAWNER_TASK != null) {
            return;
        }
        EntitySpawner.SPAWNER_TASK = SkyBlock.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)SkyBlock.getPlugin(), () -> {
            final ArrayList<Location> locations = (ArrayList<Location>)new ArrayList(Bukkit.getOnlinePlayers().size());
            for (final Player player : Bukkit.getOnlinePlayers()) {
                locations.add((Object)player.getLocation());
            }
            for (final EntitySpawner spawner : getSpawners()) {
                boolean sp = false;
                for (final Location location : locations) {
                    if (!location.getWorld().getUID().equals((Object)spawner.location.getWorld().getUID())) {
                        continue;
                    }
                    if (location.distance(spawner.location) <= 60.0) {
                        sp = true;
                        break;
                    }
                }
                if (!sp) {
                    if (spawner.active == null || spawner.active.getEntity().isDead()) {
                        continue;
                    }
                    spawner.active.remove();
                }
                else {
                    if (spawner.active != null && !spawner.active.getEntity().isDead()) {
                        continue;
                    }
                    spawner.active = new SEntity(spawner.location, spawner.type, new Object[0]);
                    spawner.active.getEntity().setRemoveWhenFarAway(true);
                }
            }
        }, 0L, 400L);
    }
    
    public static void stopSpawnerTask() {
        EntitySpawner.SPAWNER_TASK.cancel();
        EntitySpawner.SPAWNER_TASK = null;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public SEntityType getType() {
        return this.type;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    static {
        SPAWNER_CACHE = (List)new ArrayList();
    }
}
