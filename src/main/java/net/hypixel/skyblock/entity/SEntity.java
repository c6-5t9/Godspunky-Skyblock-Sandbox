package net.hypixel.skyblock.entity;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wolf;
import net.hypixel.skyblock.entity.wolf.WolfStatistics;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Ageable;
import org.bukkit.material.MaterialData;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import net.hypixel.skyblock.entity.end.EndermanStatistics;
import org.bukkit.entity.Slime;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import java.util.HashMap;
import net.hypixel.skyblock.entity.nms.SNMSEntity;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import java.util.UUID;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import java.util.Map;
import net.hypixel.skyblock.SkyBlock;

public class SEntity
{
    private static final SkyBlock plugin;
    public static final Map<Entity, Boolean> isStarred;
    private final SEntityType specType;
    private final LivingEntity entity;
    private final Map<UUID, Double> damageDealt;
    private BukkitTask task;
    private BukkitTask ticker;
    private final Object genericInstance;
    private final EntityStatistics statistics;
    private final EntityFunction function;
    
    public SEntity(final Location location, final SEntityType specType, final Object... params) {
        this.specType = specType;
        final Object instance = specType.instance(params);
        this.genericInstance = instance;
        final EntityFunction function = (EntityFunction)instance;
        final EntityStatistics statistics = (EntityStatistics)instance;
        this.function = function;
        this.statistics = statistics;
        if (instance instanceof SNMSEntity) {
            this.entity = ((SNMSEntity)instance).spawn(location);
        }
        else {
            this.entity = (LivingEntity)location.getWorld().spawnEntity(location, specType.getCraftType());
        }
        this.damageDealt = (Map<UUID, Double>)new HashMap();
        if (statistics.getMovementSpeed() != -1.0) {
            ((CraftLivingEntity)this.entity).getHandle().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(statistics.getMovementSpeed());
        }
        final Location move = this.entity.getLocation().clone();
        move.setYaw(location.getYaw());
        this.entity.teleport(move);
        final SEntityEquipment equipment = statistics.getEntityEquipment();
        final EntityEquipment ee = this.entity.getEquipment();
        if (equipment != null) {
            ee.setHelmet(equipment.getHelmet());
            ee.setChestplate(equipment.getChestplate());
            ee.setLeggings(equipment.getLeggings());
            ee.setBoots(equipment.getBoots());
            ee.setItemInHand(equipment.getItemInHand());
        }
        this.entity.setNoDamageTicks(15);
        this.entity.setMaximumNoDamageTicks(15);
        this.entity.setRemoveWhenFarAway(statistics.removeWhenFarAway());
        function.onSpawn(this.entity, this);
        if (function.tick(this.entity)) {
            this.ticker = new BukkitRunnable() {
                public void run() {
                    if (SEntity.this.entity.isDead()) {
                        this.cancel();
                    }
                    function.tick(SEntity.this.entity);
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        }
        if (statistics instanceof SlimeStatistics && this.entity instanceof Slime) {
            ((Slime)this.entity).setSize(((SlimeStatistics)statistics).getSize());
        }
        if (statistics instanceof EndermanStatistics && this.entity instanceof Enderman) {
            ((Enderman)this.entity).setCarriedMaterial((((EndermanStatistics)statistics).getCarriedMaterial() != null) ? ((EndermanStatistics)statistics).getCarriedMaterial() : new MaterialData(Material.AIR));
        }
        if (this.entity instanceof Ageable) {
            if (this.genericInstance instanceof net.hypixel.skyblock.entity.Ageable && ((net.hypixel.skyblock.entity.Ageable)this.genericInstance).isBaby()) {
                ((Ageable)this.entity).setBaby();
            }
            else {
                ((Ageable)this.entity).setAdult();
            }
        }
        if (statistics instanceof ZombieStatistics && this.entity instanceof Zombie) {
            ((Zombie)this.entity).setVillager(((ZombieStatistics)statistics).isVillager());
        }
        if (statistics instanceof JockeyStatistics) {
            this.entity.setPassenger((Entity)new SEntity(location, ((JockeyStatistics)statistics).getPassenger(), new Object[0]).getEntity());
        }
        if (statistics instanceof WolfStatistics && this.entity instanceof Wolf) {
            ((Wolf)this.entity).setAngry(((WolfStatistics)statistics).isAngry());
        }
        if (statistics instanceof SkeletonStatistics && this.entity instanceof Skeleton) {
            ((Skeleton)this.entity).setSkeletonType(((SkeletonStatistics)statistics).isWither() ? Skeleton.SkeletonType.WITHER : Skeleton.SkeletonType.NORMAL);
        }
        if (this.entity instanceof Ageable) {
            ((Ageable)this.entity).setAdult();
        }
        new BukkitRunnable() {
            public void run() {
                if (!statistics.isVisible()) {
                    ((CraftLivingEntity)SEntity.this.entity).getHandle().setInvisible(true);
                }
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 2L);
        int rand = 0;
        if (this.entity.hasMetadata("WATCHER_E")) {
            rand = SUtil.random(7, 12);
            rand *= 1000000;
            ((CraftZombie)this.entity).setBaby(false);
        }
        function.onSpawnNameTag(this.entity, this, specType, params);
        this.entity.setMaxHealth(statistics.getEntityMaxHealth() + rand);
        this.entity.setHealth(this.entity.getMaxHealth());
        this.entity.setMetadata("specEntityObject", (MetadataValue)new FixedMetadataValue((Plugin)SEntity.plugin, (Object)this));
        new BukkitRunnable() {
            public void run() {
                if (SEntity.this.entity.hasMetadata("upsidedown")) {
                    SEntity.this.entity.setCustomName("Dinnerbone");
                    SEntity.this.entity.setCustomNameVisible(false);
                }
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 2L);
    }
    
    public SEntity(final Entity e, final SEntityType type, final Object... params) {
        this(e.getLocation(), type, params);
    }
    
    public void addDamageFor(final Player player, double damage) {
        final UUID uuid = player.getUniqueId();
        if (this.damageDealt.containsKey((Object)uuid)) {
            damage += (double)this.damageDealt.get((Object)uuid);
        }
        this.damageDealt.remove((Object)uuid);
        this.damageDealt.put((Object)uuid, (Object)damage);
    }
    
    public void setVisible(final boolean visible) {
        new BukkitRunnable() {
            public void run() {
                ((CraftLivingEntity)SEntity.this.entity).getHandle().setInvisible(!visible);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 2L);
    }
    
    public void setTarget(final LivingEntity target) {
        if (!(this.entity instanceof Creature)) {
            return;
        }
        ((Creature)this.entity).setTarget(target);
    }
    
    public void remove() {
        if (this.ticker != null) {
            this.ticker.cancel();
        }
        if (this.task != null) {
            this.task.cancel();
        }
        this.entity.remove();
    }
    
    public void setHealth(final int health) {
    }
    
    public void setDefense(final double percent) {
    }
    
    public void setStarred(final boolean starred) {
        SEntity.isStarred.put((Object)this.entity, (Object)starred);
    }
    
    public void setMetadata(final Object metadata) {
        this.entity.setMetadata("specEntityObject", (MetadataValue)new FixedMetadataValue((Plugin)SEntity.plugin, (Object)this));
    }
    
    public void setDamage(final int damage) {
    }
    
    public static SEntity findSEntity(final Entity entity) {
        if (!entity.hasMetadata("specEntityObject") || entity.getMetadata("specEntityObject").isEmpty() || !(((MetadataValue)entity.getMetadata("specEntityObject").get(0)).value() instanceof SEntity)) {
            return null;
        }
        return (SEntity)((MetadataValue)entity.getMetadata("specEntityObject").get(0)).value();
    }
    
    public SEntityType getSpecType() {
        return this.specType;
    }
    
    public LivingEntity getEntity() {
        return this.entity;
    }
    
    public Map<UUID, Double> getDamageDealt() {
        return this.damageDealt;
    }
    
    public BukkitTask getTask() {
        return this.task;
    }
    
    public BukkitTask getTicker() {
        return this.ticker;
    }
    
    public Object getGenericInstance() {
        return this.genericInstance;
    }
    
    public EntityStatistics getStatistics() {
        return this.statistics;
    }
    
    public EntityFunction getFunction() {
        return this.function;
    }
    
    static {
        plugin = SkyBlock.getPlugin();
        isStarred = (Map)new HashMap();
    }
}
