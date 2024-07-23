package net.hypixel.skyblock.features.dungeons.blessing;

import java.util.HashMap;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.block.Block;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.features.dungeons.stats.WrappedStats;
import net.hypixel.skyblock.user.TemporaryStats;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.World;

public class Blessings
{
    private final BlessingType type;
    private int level;
    private double buffPercent;
    private final World world;
    public static final SkyBlock sse;
    public static final Map<World, List<Blessings>> BLESSINGS_MAP;
    public static final Map<UUID, float[]> STAT_MAP;
    
    public Blessings(final BlessingType type, final int level, final World operatedWorld) {
        this.level = 1;
        this.type = type;
        this.world = operatedWorld;
        this.level = level;
    }
    
    public void active() {
        final World operatedWorld = this.world;
        if (Blessings.BLESSINGS_MAP.containsKey((Object)operatedWorld)) {
            for (final Blessings bs : (List)Blessings.BLESSINGS_MAP.get((Object)operatedWorld)) {
                if (bs.getType() == this.type) {
                    bs.setLevel(bs.getLevel() + this.level);
                    return;
                }
            }
            ((List)Blessings.BLESSINGS_MAP.get((Object)operatedWorld)).add((Object)this);
        }
        else {
            Blessings.BLESSINGS_MAP.put((Object)operatedWorld, (Object)new ArrayList());
            ((List)Blessings.BLESSINGS_MAP.get((Object)operatedWorld)).add((Object)this);
        }
    }
    
    public static List<Blessings> getFrom(final World w) {
        if (Blessings.BLESSINGS_MAP.containsKey((Object)w)) {
            return (List<Blessings>)Blessings.BLESSINGS_MAP.get((Object)w);
        }
        return null;
    }
    
    public void remove() {
        ((List)Blessings.BLESSINGS_MAP.get((Object)this.world)).remove((Object)this);
        for (final Player p : this.world.getPlayers()) {
            TemporaryStats ts = null;
            final User u = User.getUser(p.getUniqueId());
            if (null == u) {
                continue;
            }
            if (null != TemporaryStats.getFromPlayer(u.toBukkitPlayer())) {
                ts = TemporaryStats.getFromPlayer(u.toBukkitPlayer());
            }
            else {
                ts = new TemporaryStats(User.getUser(u.toBukkitPlayer().getUniqueId()));
            }
            ts.cleanStats();
        }
    }
    
    public static void resetForWorld(final World w) {
        Blessings.BLESSINGS_MAP.remove((Object)w);
        for (final Player p : w.getPlayers()) {
            TemporaryStats ts = null;
            final User u = User.getUser(p.getUniqueId());
            if (null == u) {
                continue;
            }
            if (null != TemporaryStats.getFromPlayer(u.toBukkitPlayer())) {
                ts = TemporaryStats.getFromPlayer(u.toBukkitPlayer());
            }
            else {
                ts = new TemporaryStats(User.getUser(u.toBukkitPlayer().getUniqueId()));
            }
            ts.cleanStats();
        }
    }
    
    public WrappedStats getBlessingStats(final User u) {
        TemporaryStats ts = null;
        if (null != TemporaryStats.getFromPlayer(u.toBukkitPlayer())) {
            ts = TemporaryStats.getFromPlayer(u.toBukkitPlayer());
        }
        else {
            ts = new TemporaryStats(User.getUser(u.toBukkitPlayer().getUniqueId()));
        }
        ts.cleanStats();
        final float[] statsarray = this.calculate(User.getUser(u.getUuid()));
        return new WrappedStats(statsarray);
    }
    
    private float[] calculate(final User u) {
        final float[] stats = new float[7];
        if (null == u) {
            return stats;
        }
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)u.getUuid());
        final double pci = 1.0 + this.level * 2.0 * 1.2 / 100.0;
        final double fic = this.level * 2.0 * 1.2;
        if (BlessingType.LIFE_BLESSINGS == this.type) {
            final double stat = statistics.getMaxHealth().addAll();
            stats[0] = (float)(1.2 * (stat * pci + fic - stat));
            stats[1] = (float)(this.level / 100);
        }
        else if (BlessingType.POWER_BLESSINGS == this.type) {
            final double stat_1 = statistics.getStrength().addAll();
            stats[2] = (float)(1.2 * (stat_1 * pci + fic - stat_1));
            final double stat_2 = statistics.getCritDamage().addAll() * 100.0;
            stats[3] = (float)(1.2 * (stat_2 * pci + fic - stat_2)) / 100.0f;
        }
        else if (BlessingType.WISDOM_BLESSINGS == this.type) {
            final double stat_1 = statistics.getIntelligence().addAll();
            stats[4] = (float)(1.2 * (stat_1 * pci + fic - stat_1));
            final double stat_2 = statistics.getSpeed().addAll() * 100.0;
            stats[5] = (float)(1.2 * (stat_2 * pci + fic - stat_2)) / 100.0f;
        }
        else if (BlessingType.STONE_BLESSINGS == this.type) {
            final double stat_1 = statistics.getDefense().addAll();
            stats[6] = (float)(1.2 * (stat_1 * pci + fic - stat_1));
            final double stat_2 = statistics.getStrength().addAll();
            stats[2] = (float)(1.2 * (stat_2 * pci + fic - stat_2));
        }
        else {
            final double stat_1 = statistics.getDefense().addAll();
            stats[6] = (float)(stat_1 + 1.2 * (stat_1 * pci + fic - stat_1));
            final double stat_2 = statistics.getStrength().addAll();
            stats[2] = (float)(stat_2 + 1.2 * (stat_2 * pci + fic - stat_2));
            final double stat2 = statistics.getMaxHealth().addAll();
            stats[0] = (float)(stat2 + 1.2 * (stat2 * pci + fic - stat2));
            final double stat_3 = statistics.getIntelligence().addAll();
            stats[4] = (float)(stat_3 + 1.2 * (stat_3 * pci + fic - stat_3));
        }
        return stats;
    }
    
    public String toText() {
        final String n = SUtil.toRomanNumeral(this.level);
        switch (this.type) {
            case LIFE_BLESSINGS: {
                return "Blessing of Life " + n;
            }
            case POWER_BLESSINGS: {
                return "Blessing of Power " + n;
            }
            case STONE_BLESSINGS: {
                return "Blessing of Stone " + n;
            }
            case TIME_BLESSINGS: {
                return "Blessing of Time " + n;
            }
            case WISDOM_BLESSINGS: {
                return "Blessing of Wisdom " + n;
            }
            default: {
                return "Blessing " + n;
            }
        }
    }
    
    public static void update() {
        if (Blessings.BLESSINGS_MAP.isEmpty()) {
            return;
        }
        for (final Player p : Bukkit.getOnlinePlayers()) {
            final World w = p.getWorld();
            if (null != User.getUser(p.getUniqueId())) {
                if (!Blessings.BLESSINGS_MAP.containsKey((Object)w)) {
                    continue;
                }
                final List<Blessings> bls = (List<Blessings>)Blessings.BLESSINGS_MAP.get((Object)w);
                final TemporaryStats ts = (null != TemporaryStats.getFromPlayer(p)) ? TemporaryStats.getFromPlayer(p) : new TemporaryStats(User.getUser(p.getUniqueId()));
                float def = 0.0f;
                float spd = 0.0f;
                float intel = 0.0f;
                float cd = 0.0f;
                float str = 0.0f;
                float hpg = 0.0f;
                float hp = 0.0f;
                for (final Blessings bl : bls) {
                    ts.cleanStats();
                    final float[] statsarray = bl.calculate(User.getUser(p.getUniqueId()));
                    hp += statsarray[0];
                    hpg += statsarray[1];
                    str += statsarray[2];
                    cd += statsarray[3];
                    intel += statsarray[4];
                    spd += statsarray[5];
                    def += statsarray[6];
                    Blessings.STAT_MAP.put((Object)p.getUniqueId(), (Object)statsarray);
                }
                ts.cleanStats();
                ts.setHealth(Math.max(0.0f, hp));
                ts.setDefense(def);
                ts.setStrength(str);
                ts.setSpeed(spd);
                ts.setCritDamage(cd);
                ts.setIntelligence(intel);
                ts.update();
            }
        }
    }
    
    public String buildPickupMessage(final User targetUser, final User picker) {
        final Blessings type = this;
        final StringBuilder sb = new StringBuilder();
        if (null == picker) {
            sb.append(Sputnik.trans("&6&lDUNGEON BUFF! &fA &d" + type.toText() + "&f have been picked up!\n"));
        }
        else if (targetUser.getUuid() == picker.getUuid()) {
            sb.append(Sputnik.trans("&6&lDUNGEON BUFF! &fYou found a &d" + type.toText() + "&f!\n"));
        }
        else {
            sb.append(Sputnik.trans("&6&lDUNGEON BUFF! &b" + picker.toBukkitPlayer().getName() + " &ffound a &d" + type.toText() + "&f!\n"));
        }
        sb.append(Sputnik.trans("   &7Granted you "));
        switch (type.getType()) {
            case LIFE_BLESSINGS: {
                sb.append(Sputnik.trans("&a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getHealth()) + " HP &7and &a+0% &7health generation."));
                break;
            }
            case POWER_BLESSINGS: {
                sb.append(Sputnik.trans("&a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getStrength()) + " &c\u2741 Strength &7and &a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getCritDamage() * 100.0f) + " &9\u2620 Crit Damage&7."));
                break;
            }
            case STONE_BLESSINGS: {
                sb.append(Sputnik.trans("&a" + Sputnik.roundComma(type.getBlessingStats(targetUser).getDefense()) + " \u2748 Defense &7and &a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getStrength()) + " &c\u2741 Strength&7."));
                break;
            }
            case TIME_BLESSINGS: {
                sb.append(Sputnik.trans("&a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getHealth()) + " HP&7, &a" + Sputnik.roundComma(type.getBlessingStats(targetUser).getDefense()) + " \u2748 Defense&7, &a" + Sputnik.roundComma(type.getBlessingStats(targetUser).getIntelligence()) + " &b\u270e Intelligence &7and &a+" + Sputnik.roundComma(type.getBlessingStats(targetUser).getStrength()) + " &c\u2741 Strength&7."));
                break;
            }
            case WISDOM_BLESSINGS: {
                sb.append(Sputnik.trans("&a" + Sputnik.roundComma(type.getBlessingStats(targetUser).getIntelligence()) + " &b\u270e Intelligence &7and &a" + Sputnik.roundComma(type.getBlessingStats(targetUser).getSpeed() * 100.0f) + " &f\u2726 Speed&7."));
                break;
            }
        }
        return sb.toString();
    }
    
    public static void openBlessingChest(final Block chest, final Blessings bless, final Player e) {
        final Location loc = chest.getLocation().add(0.5, 0.0, 0.5);
        final SEntity sEntity = new SEntity(loc.clone().add(0.0, -1.0, 0.0), SEntityType.VELOCITY_ARMOR_STAND, new Object[0]);
        final ArmorStand drop = (ArmorStand)sEntity.getEntity();
        drop.setVisible(false);
        drop.setCustomNameVisible(false);
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.getEquipment().setHelmet(SUtil.getSkullURLStack("asadas", "e93e2068617872c542ecda1d27df4ece91c699907bf327c4ddb85309412d3939", 1, new String[0]));
        final ArmorStand as = Sputnik.spawnStaticDialougeBox((Entity)drop, loc.clone().add(0.0, 1.65, 0.0));
        as.setCustomName(Sputnik.trans("&d" + bless.toText()));
        as.setCustomNameVisible(false);
        as.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.setVelocity(new Vector(0.0, 0.058, 0.0));
        SUtil.delay(() -> drop.remove(), 150L);
        SUtil.delay(() -> {
            bless.active();
            final User u = User.getUser(e.getUniqueId());
            if (null == u) {
                return;
            }
            for (final Player p : loc.getWorld().getPlayers()) {
                if (null == User.getUser(p.getUniqueId())) {
                    continue;
                }
                p.sendMessage(bless.buildPickupMessage(User.getUser(p.getUniqueId()), u));
            }
        }, 20L);
        new BukkitRunnable() {
            public void run() {
                if (drop.isDead()) {
                    as.remove();
                    this.cancel();
                    return;
                }
                as.setCustomNameVisible(true);
                final Vector velClone = drop.getVelocity().clone();
                drop.setVelocity(new Vector(0.0, (0.0 > velClone.getY()) ? 0.045 : -0.045, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
        new BukkitRunnable() {
            public void run() {
                if (drop.isDead()) {
                    as.remove();
                    this.cancel();
                    return;
                }
                final Location l = drop.getLocation();
                l.setYaw(l.getYaw() + 5.0f);
                drop.teleport(l);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (drop.isDead()) {
                    as.remove();
                    this.cancel();
                    return;
                }
                drop.getWorld().spigot().playEffect(drop.getLocation().clone().add(0.0, 1.5, 0.0), Effect.CLOUD, 21, 0, 0.3f, 0.0f, 0.3f, 0.01f, 1, 30);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public static void dropBlessingPickable(final Location loc, final Blessings bless) {
        final SEntity sEntity = new SEntity(loc.clone().add(0.0, -0.8, 0.0), SEntityType.VELOCITY_ARMOR_STAND, new Object[0]);
        final ArmorStand drop = (ArmorStand)sEntity.getEntity();
        drop.setVisible(false);
        drop.setCustomNameVisible(false);
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.getEquipment().setHelmet(SUtil.getSkullURLStack("asadas", "e93e2068617872c542ecda1d27df4ece91c699907bf327c4ddb85309412d3939", 1, new String[0]));
        final ArmorStand as = Sputnik.spawnStaticDialougeBox((Entity)drop, 2.35);
        as.setCustomName(Sputnik.trans("&d" + bless.toText()));
        as.setCustomNameVisible(true);
        as.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.setVelocity(new Vector(0.0, 0.03, 0.0));
        SUtil.delay(() -> {
            if (!drop.isDead()) {
                for (final Player p : loc.getWorld().getPlayers()) {
                    if (null == User.getUser(p.getUniqueId())) {
                        continue;
                    }
                    p.sendMessage(bless.buildPickupMessage(User.getUser(p.getUniqueId()), null));
                    bless.active();
                }
                drop.remove();
            }
        }, 2000L);
        new BukkitRunnable() {
            public void run() {
                if (drop.isDead()) {
                    as.remove();
                    this.cancel();
                    return;
                }
                final Vector velClone = drop.getVelocity().clone();
                drop.setVelocity(new Vector(0.0, (0.0 > velClone.getY()) ? 0.035 : -0.035, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 25L, 25L);
        new BukkitRunnable() {
            public void run() {
                if (drop.isDead()) {
                    as.remove();
                    this.cancel();
                    return;
                }
                if (!loc.getWorld().getEntities().contains((Object)drop)) {
                    this.cancel();
                    return;
                }
                final Location l = drop.getLocation();
                l.setYaw(l.getYaw() + 2.5f);
                drop.teleport(l);
                l.getWorld().spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                for (final Entity e : drop.getNearbyEntities(0.07, 0.07, 0.07)) {
                    if (e instanceof Player) {
                        final User u = User.getUser(e.getUniqueId());
                        if (null == u) {
                            continue;
                        }
                        for (final Player p : loc.getWorld().getPlayers()) {
                            if (null == User.getUser(p.getUniqueId())) {
                                continue;
                            }
                            p.sendMessage(bless.buildPickupMessage(User.getUser(p.getUniqueId()), u));
                        }
                        bless.active();
                        drop.getWorld().playEffect(loc, Effect.LAVA_POP, 0);
                        drop.getWorld().playEffect(loc, Effect.LAVA_POP, 0);
                        drop.getWorld().playSound(drop.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                        drop.remove();
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public BlessingType getType() {
        return this.type;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    static {
        sse = SkyBlock.getPlugin();
        BLESSINGS_MAP = (Map)new HashMap();
        STAT_MAP = (Map)new HashMap();
    }
}
