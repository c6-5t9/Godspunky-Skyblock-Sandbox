package net.hypixel.skyblock.features.slayer;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.World;
import net.hypixel.skyblock.util.SUtil;
import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.features.sequence.SoundSequenceType;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SlayerQuest implements ConfigurationSerializable
{
    private final SlayerBossType type;
    private final long started;
    private double xp;
    private long spawned;
    private long killed;
    private long died;
    private SEntityType lastKilled;
    private SEntity entity;
    
    public SlayerQuest(final SlayerBossType type, final long started) {
        this.type = type;
        this.started = started;
        this.entity = null;
    }
    
    private SlayerQuest(final SlayerBossType type, final long started, final double xp, final long spawned, final long killed, final long died, final SEntityType lastKilled) {
        this.type = type;
        this.started = started;
        this.xp = xp;
        this.spawned = spawned;
        this.killed = killed;
        this.died = died;
        this.lastKilled = null;
        this.entity = null;
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"type", (Object)this.type.getNamespace());
        map.put((Object)"started", (Object)this.started);
        map.put((Object)"xp", (Object)this.xp);
        map.put((Object)"spawned", (Object)this.spawned);
        map.put((Object)"killed", (Object)this.killed);
        map.put((Object)"died", (Object)this.died);
        map.put((Object)"lastKilled", (Object)null);
        return map;
    }
    
    public static SlayerQuest deserialize(final Map<String, Object> map) {
        return new SlayerQuest(SlayerBossType.getByNamespace(String.valueOf(map.get((Object)"type"))), ((Number)map.get((Object)"started")).longValue(), ((Number)map.get((Object)"xp")).doubleValue(), ((Number)map.get((Object)"spawned")).longValue(), ((Number)map.get((Object)"killed")).longValue(), ((Number)map.get((Object)"died")).longValue(), null);
    }
    
    public static SlayerQuest deserializeSlayerQuest(final Document document) {
        final SlayerBossType type = SlayerBossType.getByNamespace(document.getString((Object)"type"));
        final long started = document.getLong((Object)"started");
        final double xp = document.getDouble((Object)"xp");
        final long spawned = document.getLong((Object)"spawned");
        final long killed = document.getLong((Object)"killed");
        final long died = document.getLong((Object)"died");
        return new SlayerQuest(type, started, xp, spawned, killed, died, null);
    }
    
    public static void playMinibossSpawn(final Location location, final Entity sound) {
        final Location clone = location.clone();
        final World world = location.getWorld();
        if (sound != null) {
            SoundSequenceType.SLAYER_MINIBOSS_SPAWN.play(sound);
        }
        else {
            SoundSequenceType.SLAYER_MINIBOSS_SPAWN.play(clone);
        }
        final AtomicDouble additive = new AtomicDouble();
        SUtil.runIntervalForTicks(() -> world.spigot().playEffect(clone.clone().add(0.0, additive.getAndAdd(0.5), 0.0), Effect.EXPLOSION_LARGE, 1, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 16), 3L, 12L);
    }
    
    public static void playBossSpawn(final Location location, final Entity sound) {
        final Location clone = location.clone();
        final World world = location.getWorld();
        if (sound != null) {
            SoundSequenceType.SLAYER_BOSS_SPAWN.play(sound);
        }
        else {
            SoundSequenceType.SLAYER_BOSS_SPAWN.play(clone);
        }
        SUtil.runIntervalForTicks(() -> {
            for (int i = 0; i < 50; ++i) {
                world.playEffect(clone.clone().add(0.0, -0.2, 0.0), Effect.WITCH_MAGIC, (Object)Effect.SPELL.getData());
                world.playEffect(clone, Effect.SPELL, (Object)Effect.SPELL.getData());
                world.playEffect(clone, Effect.FLYING_GLYPH, (Object)Effect.FLYING_GLYPH.getData());
                world.playEffect(clone.clone().add(0.0, -0.2, 0.0), Effect.FLYING_GLYPH, (Object)Effect.FLYING_GLYPH.getData());
                world.playEffect(clone, Effect.WITCH_MAGIC, (Object)Effect.WITCH_MAGIC.getData());
            }
        }, 5L, 28L);
        new BukkitRunnable() {
            public void run() {
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
                world.playEffect(clone, Effect.EXPLOSION_HUGE, (Object)Effect.EXPLOSION_HUGE.getData());
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 28L);
    }
    
    public SlayerBossType getType() {
        return this.type;
    }
    
    public long getStarted() {
        return this.started;
    }
    
    public double getXp() {
        return this.xp;
    }
    
    public long getSpawned() {
        return this.spawned;
    }
    
    public long getKilled() {
        return this.killed;
    }
    
    public long getDied() {
        return this.died;
    }
    
    public SEntityType getLastKilled() {
        return this.lastKilled;
    }
    
    public SEntity getEntity() {
        return this.entity;
    }
    
    public void setXp(final double xp) {
        this.xp = xp;
    }
    
    public void setSpawned(final long spawned) {
        this.spawned = spawned;
    }
    
    public void setKilled(final long killed) {
        this.killed = killed;
    }
    
    public void setDied(final long died) {
        this.died = died;
    }
    
    public void setLastKilled(final SEntityType lastKilled) {
        this.lastKilled = lastKilled;
    }
    
    public void setEntity(final SEntity entity) {
        this.entity = entity;
    }
}
