package net.hypixel.skyblock.item.armor;

import java.util.HashMap;
import java.util.Iterator;
import net.md_5.bungee.api.ChatColor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import net.hypixel.skyblock.util.Groups;
import net.hypixel.skyblock.item.SItem;
import java.util.UUID;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Player;

public class Witherborn
{
    private final Player p;
    private final int size;
    private Wither w;
    private boolean isTargetting;
    public Entity withersTarget;
    public static final Map<UUID, Witherborn> WITHER_MAP;
    public static final Map<UUID, Boolean> WITHER_COOLDOWN;
    
    public Witherborn(final Player p) {
        this.size = 790;
        this.isTargetting = false;
        this.withersTarget = null;
        this.p = p;
        this.isTargetting = false;
        Witherborn.WITHER_MAP.put((Object)p.getUniqueId(), (Object)this);
    }
    
    public boolean checkCondition() {
        final SItem helm = SItem.find(this.p.getInventory().getHelmet());
        final SItem chest = SItem.find(this.p.getInventory().getChestplate());
        final SItem leg = SItem.find(this.p.getInventory().getLeggings());
        final SItem boots = SItem.find(this.p.getInventory().getBoots());
        if (helm == null || chest == null || leg == null || boots == null) {
            Witherborn.WITHER_MAP.remove((Object)this.p.getUniqueId());
            this.w.remove();
            return false;
        }
        if (Groups.WITHER_HELMETS.contains((Object)helm.getType()) && Groups.WITHER_CHESTPLATES.contains((Object)chest.getType()) && Groups.WITHER_LEGGINGS.contains((Object)leg.getType()) && Groups.WITHER_BOOTS.contains((Object)boots.getType())) {
            return true;
        }
        Witherborn.WITHER_MAP.remove((Object)this.p.getUniqueId());
        this.w.remove();
        return false;
    }
    
    public void spawnWither() {
        final Wither w = (Wither)this.p.getWorld().spawn(this.p.getLocation(), (Class)Wither.class);
        EntityManager.noAI((Entity)w);
        EntityManager.setNBTTag((Entity)w, "Invul", this.size);
        EntityManager.noHit((Entity)w);
        EntityManager.shutTheFuckUp((Entity)w);
        w.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)0));
        w.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)0));
        w.setMetadata("Ire", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)0));
        this.w = w;
        new BukkitRunnable() {
            public void run() {
                if (!Witherborn.this.p.isOnline() || w.isDead() || Witherborn.getWitherbornInstance(Witherborn.this.p) == null) {
                    this.cancel();
                    return;
                }
                if (Witherborn.this.withersTarget == null) {
                    final List<Entity> er = (List<Entity>)w.getNearbyEntities(10.0, 10.0, 10.0);
                    er.removeIf(en -> en.hasMetadata("GiantSword") || en.hasMetadata("NPC"));
                    er.removeIf(en -> !(en instanceof LivingEntity));
                    er.removeIf(en -> en instanceof Player);
                    er.removeIf(en -> en instanceof ArmorStand);
                    er.removeIf(en -> en instanceof Villager);
                    er.removeIf(Entity::isDead);
                    if (!er.isEmpty()) {
                        final LivingEntity le = (LivingEntity)er.get(SUtil.random(0, er.size() - 1));
                        Witherborn.this.setWithersTarget((Entity)le);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 100L, 100L);
        new BukkitRunnable() {
            float cout = Witherborn.this.p.getLocation().getYaw();
            
            public void run() {
                if (!Witherborn.this.p.isOnline() || w.isDead() || Witherborn.getWitherbornInstance(Witherborn.this.p) == null) {
                    w.remove();
                    Witherborn.this.p.playSound(Witherborn.this.p.getLocation(), Sound.WITHER_DEATH, 0.5f, 1.0f);
                    this.cancel();
                    return;
                }
                Witherborn.this.checkCondition();
                if (Witherborn.this.withersTarget != null) {
                    if (Witherborn.this.withersTarget instanceof LivingEntity) {
                        if (!Witherborn.this.isTargetting) {
                            final LivingEntity r1 = (LivingEntity)Witherborn.this.withersTarget;
                            Witherborn.this.selfSacrificeHeroAction(w, (Entity)r1);
                            Witherborn.this.isTargetting = true;
                        }
                    }
                    else {
                        Witherborn.this.withersTarget = null;
                    }
                }
                final Location loc = Witherborn.this.p.getLocation();
                loc.setYaw(this.cout);
                loc.setPitch(0.0f);
                loc.add(loc.getDirection().normalize().multiply(1.45));
                if (Witherborn.this.withersTarget == null && !Witherborn.this.isTargetting) {
                    w.teleport(loc);
                }
                this.cout += 7.0f;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 2L, 2L);
    }
    
    public void selfSacrificeHeroAction(final Wither w, final Entity e) {
        new BukkitRunnable() {
            public void run() {
                if (!Witherborn.this.p.isOnline() || w.isDead()) {
                    w.remove();
                    w.getWorld().playEffect(w.getLocation(), Effect.EXPLOSION_LARGE, Witherborn.this.size);
                    w.getWorld().playEffect(w.getLocation(), Effect.EXPLOSION_LARGE, Witherborn.this.size);
                    w.getWorld().playSound(w.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                    w.getWorld().playSound(w.getLocation(), Sound.WITHER_DEATH, 0.3f, 1.0f);
                    this.cancel();
                    return;
                }
                if (e.isDead()) {
                    Witherborn.this.isTargetting = false;
                    Witherborn.this.withersTarget = null;
                    this.cancel();
                    return;
                }
                if (w.getNearbyEntities(0.2, 0.2, 0.2).contains((Object)e)) {
                    Witherborn.WITHER_COOLDOWN.put((Object)Witherborn.this.p.getUniqueId(), (Object)true);
                    SUtil.delay(() -> {
                        if (Witherborn.this.checkCondition()) {
                            Witherborn.WITHER_MAP.remove((Object)Witherborn.this.p.getUniqueId());
                            final Witherborn a = new Witherborn(Witherborn.this.p);
                            a.spawnWither();
                        }
                        Witherborn.WITHER_COOLDOWN.put((Object)Witherborn.this.p.getUniqueId(), (Object)false);
                    }, 600L);
                    w.remove();
                    int j = 0;
                    double d = 0.0;
                    for (final Entity entity : w.getNearbyEntities(3.0, 3.0, 3.0)) {
                        if (entity.isDead()) {
                            continue;
                        }
                        if (!(entity instanceof LivingEntity)) {
                            continue;
                        }
                        if (entity.hasMetadata("GiantSword")) {
                            continue;
                        }
                        if (entity.hasMetadata("NoAffect")) {
                            continue;
                        }
                        if (entity instanceof Player || entity instanceof EnderDragonPart) {
                            continue;
                        }
                        if (entity instanceof Villager) {
                            continue;
                        }
                        if (entity instanceof ArmorStand) {
                            continue;
                        }
                        ++j;
                        final double damage = 1000000.0;
                        double find = 0.0;
                        final int combatLevel = Skill.getLevel(User.getUser(Witherborn.this.p.getUniqueId()).getCombatXP(), false);
                        final double damageMultiplier = 1.0 + combatLevel * 0.04;
                        find = 1000000.0 * damageMultiplier;
                        if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity)) {
                            int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity);
                            if (defensepercent > 100) {
                                defensepercent = 100;
                            }
                            find -= find * defensepercent / 100.0;
                        }
                        PlayerListener.spawnDamageInd(entity, find, false);
                        User.getUser(Witherborn.this.p.getUniqueId()).damageEntityIgnoreShield((Damageable)entity, find);
                        d += find;
                    }
                    d = new BigDecimal(d).setScale(1, RoundingMode.HALF_EVEN).doubleValue();
                    if (j > 0) {
                        if (j == 1) {
                            Witherborn.this.p.sendMessage((Object)ChatColor.GRAY + "Your Witherborn hit " + (Object)ChatColor.RED + j + (Object)ChatColor.GRAY + " enemy for " + (Object)ChatColor.RED + SUtil.commaify(d) + (Object)ChatColor.GRAY + " damage.");
                        }
                        else {
                            Witherborn.this.p.sendMessage((Object)ChatColor.GRAY + "Your Witherborn hit " + (Object)ChatColor.RED + j + (Object)ChatColor.GRAY + " enemies for " + (Object)ChatColor.RED + SUtil.commaify(d) + (Object)ChatColor.GRAY + " damage.");
                        }
                    }
                }
                Witherborn.this.withersTarget = e;
                final Location r = w.getLocation().setDirection(e.getLocation().toVector().subtract(w.getLocation().toVector()));
                w.teleport(r);
                w.teleport(w.getLocation().add(w.getLocation().getDirection().normalize().multiply(0.3)));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 2L, 1L);
    }
    
    public static Witherborn getWitherbornInstance(final Player p) {
        return (Witherborn)Witherborn.WITHER_MAP.get((Object)p.getUniqueId());
    }
    
    public void setWithersTarget(final Entity withersTarget) {
        this.withersTarget = withersTarget;
    }
    
    public Entity getWithersTarget() {
        return this.withersTarget;
    }
    
    static {
        WITHER_MAP = (Map)new HashMap();
        WITHER_COOLDOWN = (Map)new HashMap();
    }
}
