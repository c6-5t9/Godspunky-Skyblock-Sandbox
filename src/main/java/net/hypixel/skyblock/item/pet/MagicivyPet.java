package net.hypixel.skyblock.item.pet;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.util.EulerAngle;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.Location;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.Skill;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.Arrays;
import net.hypixel.skyblock.item.Rarity;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import net.hypixel.skyblock.util.Sputnik;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import java.util.Map;

public class MagicivyPet extends Pet
{
    public static final Map<Player, Boolean> COOLDOWN;
    
    @Override
    public List<PetAbility> getPetAbilities(final SItem instance) {
        final int level = Pet.getLevel(instance);
        final BigDecimal flameArrow = new BigDecimal(level * 0.2).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal darkbli = new BigDecimal(level * 0.2).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal blessed = new BigDecimal(level).setScale(1, RoundingMode.HALF_EVEN);
        final List<PetAbility> abilities = (List<PetAbility>)new ArrayList((Collection)Collections.singletonList((Object)new PetAbility() {
            @Override
            public String getName() {
                return "Wizarding World";
            }
            
            @Override
            public List<String> getDescription(final SItem instance) {
                return (List<String>)Collections.singletonList((Object)Sputnik.trans("&7Grants &a+" + blessed.toPlainString() + " &c\u0e51 Ability Damage"));
            }
        }));
        if (instance.getRarity().isAtLeast(Rarity.EPIC)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Dark Blizzard";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7While taking damage from mobs, grants &a" + darkbli.toPlainString() + "%"), Sputnik.trans("&7to cast a &dmagic &7spell that &bfreeze &7all"), Sputnik.trans("&7mobs in &a8 blocks&7 radius for &a10s"), Sputnik.trans("&815s cooldown.") });
                }
                
                @Override
                public void onHurt(final EntityDamageByEntityEvent e, final Entity damager) {
                    final double r = SUtil.random(0.0, 1.0);
                    final double c = 0.2 * level / 100.0;
                    if (r < c) {
                        MagicivyPet.this.spawnIceCube((Player)e.getEntity());
                    }
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.EPIC)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Fantastic Mage!";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7Increase your weapon's &ebase &cAbility"), Sputnik.trans("&c Damage &7by &a" + blessed.toPlainString() + "%") });
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.LEGENDARY)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Power of SkyBlock";
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
        }
        return abilities;
    }
    
    @Override
    public Skill getSkill() {
        return CombatSkill.INSTANCE;
    }
    
    @Override
    public String getURL() {
        return "f41e6e4bcd2667bb284fb0dde361894840ea782efbfb717f6244e06b951c2b3f";
    }
    
    @Override
    public String getDisplayName() {
        return "Magicivy";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.PET;
    }
    
    @Override
    public double getPerHealth() {
        return 2.0;
    }
    
    @Override
    public double getPerStrength() {
        return 1.0;
    }
    
    @Override
    public double getPerIntelligence() {
        return 150.0;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public void particleBelowA(final Player p, final Location l) {
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
    }
    
    public void spawnIceCube(final Player player) {
        if (MagicivyPet.COOLDOWN.containsKey((Object)player) && (boolean)MagicivyPet.COOLDOWN.get((Object)player)) {
            return;
        }
        MagicivyPet.COOLDOWN.put((Object)player, (Object)true);
        SUtil.delay(() -> {
            final Boolean b = (Boolean)MagicivyPet.COOLDOWN.put((Object)player, (Object)false);
        }, 100L);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 5.0f, 0.7f);
        for (int i = 1; i < 9; ++i) {
            final int j = i;
            SUtil.delay(() -> this.createCircle(player.getLocation(), j), i);
        }
        for (int i = 0; i < 40; ++i) {
            player.getWorld().spigot().playEffect(player.getLocation().clone().add(0.0, 0.25, 0.0), Effect.SNOW_SHOVEL, 0, 1, (float)SUtil.random(-0.5, 0.5), (float)SUtil.random(0.0, 2.0), (float)SUtil.random(-0.5, 0.5), 0.0f, 1, 20);
        }
        player.playSound(player.getLocation(), Sound.EXPLODE, 5.0f, 0.0f);
        for (final Entity entity : player.getNearbyEntities(8.0, 8.0, 8.0)) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            if (entity instanceof Player || entity instanceof EnderDragon || entity instanceof EnderDragonPart) {
                continue;
            }
            if (entity instanceof Villager) {
                continue;
            }
            if (entity instanceof ArmorStand) {
                continue;
            }
            if (entity.hasMetadata("GiantSword")) {
                continue;
            }
            if (entity.hasMetadata("NoAffect")) {
                continue;
            }
            if (entity.isDead()) {
                continue;
            }
            final User user = User.getUser(player.getUniqueId());
            entity.setMetadata("frozen", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            double b = 0.0;
            for (int k = 0; k < 2; ++k) {
                final int d;
                if ((d = k) == 0) {
                    b = 0.2;
                }
                else if (k == 1) {
                    b = 0.4;
                }
                else if (k == 2) {
                    b = 0.6;
                }
                final ArmorStand stands = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, b + 1.0, 0.0), (Class)ArmorStand.class);
                stands.setCustomNameVisible(false);
                stands.setVisible(false);
                stands.setArms(true);
                stands.setMarker(true);
                stands.setGravity(false);
                stands.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
                stands.getEquipment().setItemInHand(new ItemStack(Material.PACKED_ICE));
                SUtil.delay(() -> stands.remove(), 100L);
                new BukkitRunnable() {
                    public void run() {
                        double c = 0.0;
                        if (d == 0) {
                            c = 0.2;
                        }
                        else if (d == 1) {
                            c = 0.4;
                        }
                        else if (d == 2) {
                            c = 0.6;
                        }
                        if (stands.isDead()) {
                            ((LivingEntity)entity).removePotionEffect(PotionEffectType.SLOW);
                            entity.removeMetadata("frozen", (Plugin)SkyBlock.getPlugin());
                            entity.getWorld().playSound(entity.getLocation(), Sound.GLASS, 1.0f, 1.0f);
                            this.cancel();
                            return;
                        }
                        if (entity.isDead()) {
                            stands.remove();
                        }
                        ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 20));
                        stands.teleport(entity.getLocation().add(0.0, c + 1.0, 0.0));
                    }
                }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
            }
        }
    }
    
    private void createCircle(final Location l, final double radius) {
        l.setPitch(90.0f);
        final Vector dist = l.getDirection();
        final Location mid = l.add(dist);
        for (int particles = (int)(radius * 8.0), i = 0; i < particles; ++i) {
            final double angle = 6.283185307179586 * i / particles;
            final double x = Math.cos(angle) * radius;
            final double y = Math.sin(angle) * radius;
            Vector v = this.rotateAroundAxisX(new Vector(x, y, 0.0), l.getPitch());
            v = this.rotateAroundAxisY(v, l.getYaw());
            final Location temp = mid.clone().add(v);
            l.getWorld().spigot().playEffect(temp, Effect.CLOUD, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
            l.getWorld().spigot().playEffect(temp, Effect.CLOUD, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
            l.getWorld().spigot().playEffect(temp, Effect.CLOUD, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        }
    }
    
    private Vector rotateAroundAxisX(final Vector v, double angle) {
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }
    
    private Vector rotateAroundAxisY(final Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
    
    static {
        COOLDOWN = (Map)new HashMap();
    }
}
