package net.hypixel.skyblock.item.pet;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Effect;
import org.bukkit.Location;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.util.Vector;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.item.Rarity;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import org.bukkit.entity.Arrow;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import java.util.Map;

public class TankPet extends Pet
{
    public static final Map<Player, Boolean> COOLDOWN;
    
    @Override
    public List<PetAbility> getPetAbilities(final SItem instance) {
        final int level = Pet.getLevel(instance);
        final BigDecimal tankCannon = new BigDecimal(level * 0.3).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal toml = new BigDecimal(level * 0.1).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal buffstat = new BigDecimal(level * 0.2).setScale(1, RoundingMode.HALF_EVEN);
        final List<PetAbility> abilities = (List<PetAbility>)new ArrayList((Collection)Collections.singletonList((Object)new PetAbility() {
            @Override
            public String getName() {
                return "Super Tank Cannon";
            }
            
            @Override
            public List<String> getDescription(final SItem instance) {
                return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7Grant &e" + tankCannon.toPlainString() + "% &7chance"), Sputnik.trans("&7to shoot a &cCannon Bullet &7that deal"), Sputnik.trans("&62x &7of your last damage to all mobs"), Sputnik.trans("&7within &a4 &7blocks of the impact."), Sputnik.trans("&810s cooldown.") });
            }
            
            @Override
            public void onDamage(final EntityDamageByEntityEvent e) {
                final double r = SUtil.random(0.0, 1.0);
                final double c = 0.3 * level / 100.0;
                if (r < c) {
                    if (e.getDamager() instanceof Arrow) {
                        TankPet.this.spawnBullet((Player)((Arrow)e.getDamager()).getShooter());
                    }
                    else {
                        TankPet.this.spawnBullet((Player)e.getDamager());
                    }
                }
            }
        }));
        if (instance.getRarity().isAtLeast(Rarity.EPIC)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Tank of the Motherland";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7Grant &e" + toml.toPlainString() + "% &7chance to immune"), Sputnik.trans("&7to damage (except True Damage).") });
                }
                
                @Override
                public void onHurt(final EntityDamageByEntityEvent e, final Entity damager) {
                    final double r = SUtil.random(0.0, 1.0);
                    final double c = 0.1 * level / 100.0;
                    if (r < c) {
                        final BigDecimal dmgabs = BigDecimal.valueOf(e.getDamage()).setScale(1, RoundingMode.HALF_EVEN);
                        ((Player)e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
                        e.getEntity().sendMessage(Sputnik.trans("&eTank of the Motherland ability has absorbed &c" + SUtil.commaify(dmgabs.doubleValue()) + " &edamage for you!"));
                        e.setDamage(0.0);
                    }
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.LEGENDARY)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Forever Stading";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Collections.singletonList((Object)"Immune to any type of knockback.");
                }
                
                @Override
                public void onHurt(final EntityDamageByEntityEvent e, final Entity damager) {
                    final Entity en = e.getEntity();
                    final Vector v = new Vector(0, 0, 0);
                    SUtil.delay(() -> en.setVelocity(v), 0L);
                }
            });
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Power of the USSR";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Collections.singletonList((Object)("Increases most stats by " + (Object)ChatColor.GREEN + buffstat.toPlainString() + "%"));
                }
            });
        }
        return abilities;
    }
    
    @Override
    public Skill getSkill() {
        return CombatSkill.INSTANCE;
    }
    
    @Override
    public String getURL() {
        return "b9ae2dfb5d44e97e3a95af9071711a3d0fb6899cd3a568df1c00daf4dabe918f";
    }
    
    @Override
    public String getDisplayName() {
        return "Mini T-34";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.PET;
    }
    
    @Override
    public double getPerDefense() {
        return 3.0;
    }
    
    @Override
    public double getPerTrueDefense() {
        return 1.0;
    }
    
    @Override
    public double getPerCritDamage() {
        return 0.01;
    }
    
    @Override
    public double getPerStrength() {
        return 2.0;
    }
    
    @Override
    public double getPerCritChance() {
        return 0.001;
    }
    
    @Override
    public double getPerFerocity() {
        return 0.25;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public void particleBelowA(final Player p, final Location l) {
        p.spigot().playEffect(l, Effect.SMALL_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 0.003921569f, 0.003921569f, 0.003921569f, 1.0f, 0, 64);
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 0.45882353f, 0.45882353f, 0.45882353f, 1.0f, 0, 64);
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 0.003921569f, 0.003921569f, 0.003921569f, 1.0f, 0, 64);
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 0.45882353f, 0.45882353f, 0.45882353f, 1.0f, 0, 64);
    }
    
    public void spawnBullet(final Player player) {
        if (TankPet.COOLDOWN.containsKey((Object)player) && (boolean)TankPet.COOLDOWN.get((Object)player)) {
            return;
        }
        player.getWorld().playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
        player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 2.0f);
        final Location location = player.getLocation().add(0.0, -0.7, 0.0);
        final ArmorStand stand = (ArmorStand)player.getWorld().spawn(location.add(player.getLocation().getDirection().multiply(2)), (Class)ArmorStand.class);
        stand.setVisible(false);
        stand.setMarker(true);
        stand.setGravity(false);
        TankPet.COOLDOWN.put((Object)player, (Object)true);
        SUtil.delay(() -> {
            TankPet.COOLDOWN.put((Object)player, (Object)false);
            player.sendMessage(Sputnik.trans("&6Super Tank Cannon &ais now available."));
        }, 200L);
        stand.getEquipment().setHelmet(SUtil.getSkullURLStack("ITEM", "3e1e5c81fb9d64b74996fd171489deadbb8cb772d52cf8b566e3bc102301044", 1, ""));
        double baseDMG = 100.0;
        if (PlayerListener.LAST_DAMAGE_DEALT.containsKey((Object)player)) {
            baseDMG = (double)PlayerListener.LAST_DAMAGE_DEALT.get((Object)player);
        }
        final double base = baseDMG;
        new BukkitRunnable() {
            int c = 0;
            
            public void run() {
                if (stand.isDead()) {
                    stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 0);
                    this.cancel();
                    return;
                }
                if (stand.getLocation().add(0.0, 2.0, 0.0).getBlock().getType() != Material.AIR) {
                    stand.remove();
                    TankPet.this.playParticleAndSound((Entity)stand);
                    TankPet.this.dmgEntityInRange((Entity)stand, player, base * 2.0);
                    this.cancel();
                    return;
                }
                if (TankPet.scanEntityNear((Entity)stand, 1, 1, 1).size() > 0) {
                    stand.remove();
                    TankPet.this.playParticleAndSound((Entity)stand);
                    TankPet.this.dmgEntityInRange((Entity)stand, player, base * 2.0);
                    this.cancel();
                    return;
                }
                if (this.c >= 50) {
                    stand.remove();
                    stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 0);
                    this.cancel();
                    return;
                }
                ++this.c;
                stand.getWorld().spigot().playEffect(stand.getLocation().add(0.0, 2.0, 0.0), Effect.FLAME, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().add(0.0, 2.0, 0.0), Effect.FLAME, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().add(0.0, 2.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                stand.getWorld().spigot().playEffect(stand.getLocation().add(0.0, 2.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                stand.teleport(stand.getLocation().add(stand.getLocation().getDirection().normalize().multiply(1)));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void dmgEntityInRange(final Entity e, final Player owner, double damage) {
        final List<Entity> le = (List<Entity>)new ArrayList();
        int td = 0;
        for (final Entity entity1 : e.getNearbyEntities(4.0, 4.0, 4.0)) {
            if (entity1.isDead()) {
                continue;
            }
            if (!(entity1 instanceof LivingEntity)) {
                continue;
            }
            if (entity1 instanceof Player || entity1 instanceof EnderDragonPart || entity1 instanceof Villager || entity1 instanceof ArmorStand) {
                continue;
            }
            if (entity1 instanceof Item) {
                continue;
            }
            if (entity1 instanceof ItemFrame) {
                continue;
            }
            if (entity1.hasMetadata("GiantSword")) {
                continue;
            }
            if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity1)) {
                int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity1);
                if (defensepercent > 100) {
                    defensepercent = 100;
                }
                damage -= damage * defensepercent / 100.0;
            }
            User.getUser(owner.getUniqueId()).damageEntity((Damageable)entity1, damage);
            PlayerListener.customDMGIND(entity1, Sputnik.trans("&c" + Math.round(damage) + "\u273a"));
            PlayerListener.customDMGIND(entity1, Sputnik.trans("&c&lBOOM!"));
            le.add((Object)entity1);
            td += (int)damage;
        }
        if (le.size() > 0) {
            owner.playSound(owner.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 0.0f);
            if (le.size() > 1) {
                owner.sendMessage(Sputnik.trans("&7Your Super Tank Cannon hit &c" + le.size() + " &7enemies for &c" + SUtil.commaify(Math.round((float)td)) + " &7damage."));
            }
            else {
                owner.sendMessage(Sputnik.trans("&7Your Super Tank Cannon hit &c" + le.size() + " &7enemy for &c" + SUtil.commaify(Math.round((float)td)) + " &7damage."));
            }
        }
    }
    
    public static List<Entity> scanEntityNear(final Entity e, final int arg0, final int arg1, final int arg2) {
        final List<Entity> re = (List<Entity>)new ArrayList();
        for (final Entity entity1 : e.getNearbyEntities((double)arg0, (double)arg1, (double)arg2)) {
            if (entity1.isDead()) {
                continue;
            }
            if (!(entity1 instanceof LivingEntity)) {
                continue;
            }
            if (entity1 instanceof Player || entity1 instanceof EnderDragonPart || entity1 instanceof Villager || entity1 instanceof ArmorStand) {
                continue;
            }
            if (entity1 instanceof Item) {
                continue;
            }
            if (entity1 instanceof ItemFrame) {
                continue;
            }
            if (entity1.hasMetadata("GiantSword")) {
                continue;
            }
            if (entity1.hasMetadata("NoAffect")) {
                continue;
            }
            re.add((Object)entity1);
        }
        return re;
    }
    
    public void playParticleAndSound(final Entity e) {
        e.getLocation().getWorld().playSound(e.getLocation(), Sound.EXPLODE, 2.0f, 1.3f);
        e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION_HUGE, 0);
    }
    
    static {
        COOLDOWN = (Map)new HashMap();
    }
}
