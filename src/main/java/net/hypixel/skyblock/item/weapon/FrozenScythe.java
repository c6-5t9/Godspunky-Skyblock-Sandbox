package net.hypixel.skyblock.item.weapon;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import org.bukkit.Location;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.Effect;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class FrozenScythe implements ToolStatistics, MaterialFunction, Ability
{
    String ACT3;
    
    public FrozenScythe() {
        this.ACT3 = "true";
    }
    
    @Override
    public int getBaseDamage() {
        return 80;
    }
    
    @Override
    public String getDisplayName() {
        return "Frozen Scythe";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Ice Bolt";
    }
    
    @Override
    public String getAbilityDescription() {
        return ChatColor.translateAlternateColorCodes('&', "&7Shoots &a1 &7Ice Bolt that deals &c1,000 &7damage and slows enemies hit for &a5 &7seconds");
    }
    
    @Override
    public void onAbilityUse(final Player p, final SItem sItem) {
        p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 10.0f);
        Location loc = p.getLocation();
        loc.setY(loc.getY() + 1.0);
        final Vector vecTo1 = loc.getDirection().normalize().multiply(0.3);
        final Vector vecTo2 = loc.getDirection().normalize().multiply(1);
        final Vector back = loc.getDirection().normalize().multiply(-1);
        final ItemStack item = new ItemStack(Material.ICE);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("ice");
        item.setItemMeta(meta);
        loc = loc.add(vecTo1);
        final Entity ent1 = p.getWorld().spawn(loc, (Class)ArmorStand.class);
        final ArmorStand stand1 = (ArmorStand)ent1;
        stand1.setVisible(false);
        stand1.setArms(true);
        stand1.setGravity(false);
        stand1.setItemInHand(item);
        stand1.setSmall(true);
        item.setType(Material.PACKED_ICE);
        loc = loc.add(vecTo1);
        final Entity ent2 = p.getWorld().spawn(loc, (Class)ArmorStand.class);
        final ArmorStand stand2 = (ArmorStand)ent2;
        stand2.setVisible(false);
        stand2.setArms(true);
        stand2.setGravity(false);
        stand2.setItemInHand(item);
        stand2.setSmall(true);
        item.setType(Material.ICE);
        loc = loc.add(vecTo1);
        final Entity ent3 = p.getWorld().spawn(loc, (Class)ArmorStand.class);
        final ArmorStand stand3 = (ArmorStand)ent3;
        stand3.setVisible(false);
        stand3.setArms(true);
        stand3.setGravity(false);
        stand3.setItemInHand(item);
        stand3.setSmall(true);
        item.setType(Material.PACKED_ICE);
        loc = loc.add(vecTo1);
        final Entity ent4 = p.getWorld().spawn(loc, (Class)ArmorStand.class);
        final ArmorStand stand4 = (ArmorStand)ent4;
        stand4.setVisible(false);
        stand4.setArms(true);
        stand4.setGravity(false);
        stand4.setItemInHand(item);
        stand4.setSmall(true);
        item.setType(Material.ICE);
        loc = loc.add(vecTo1);
        final Entity ent5 = p.getWorld().spawn(loc, (Class)ArmorStand.class);
        final ArmorStand stand5 = (ArmorStand)ent5;
        stand5.setVisible(false);
        stand5.setArms(true);
        stand5.setGravity(false);
        stand5.setItemInHand(item);
        stand5.setSmall(true);
        stand2.teleport(stand2.getLocation().add(vecTo2));
        stand3.teleport(stand3.getLocation().add(vecTo2.clone().multiply(2)));
        stand4.teleport(stand4.getLocation().add(vecTo2.clone().multiply(3)));
        stand5.teleport(stand5.getLocation().add(vecTo2.clone().multiply(4)));
        stand1.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
        stand2.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
        stand3.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
        stand4.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
        stand5.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
        stand1.setMarker(true);
        stand2.setMarker(true);
        stand3.setMarker(true);
        stand4.setMarker(true);
        stand5.setMarker(true);
        throwIce(stand1, stand2, stand3, stand4, stand5, p, vecTo2, back);
        new BukkitRunnable() {
            public void run() {
                if (Material.AIR == stand1.getLocation().getBlock().getType() || stand1.getLocation().getBlock().getType().isTransparent()) {
                    if (!stand1.isDead()) {
                        final Location loc = stand1.getLocation();
                        loc.setY(loc.getY() + 0.5);
                        p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                        p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                        p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                    }
                    stand1.teleport(stand1.getLocation().add(vecTo2));
                    stand2.teleport(stand2.getLocation().add(vecTo2));
                    stand3.teleport(stand3.getLocation().add(vecTo2));
                    stand4.teleport(stand4.getLocation().add(vecTo2));
                    stand5.teleport(stand5.getLocation().add(vecTo2));
                    for (final Entity entity : stand1.getWorld().getNearbyEntities(stand1.getLocation().add(stand1.getLocation().getDirection().multiply(1.0)), 0.3, 0.3, 0.3)) {
                        if (entity.isDead()) {
                            continue;
                        }
                        if (!(entity instanceof LivingEntity)) {
                            continue;
                        }
                        if (entity.hasMetadata("GiantSword")) {
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
                        final User user = User.getUser(p.getUniqueId());
                        ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                        final double baseDamage = Sputnik.calculateMagicDamage(entity, p, 1000, 0.3);
                        user.damageEntityIgnoreShield((Damageable)entity, (int)baseDamage);
                        if (PlayerUtils.Debugmsg.debugmsg) {
                            SLog.info("[DEBUG] " + p.getName() + " have dealt " + (float)baseDamage + " damage! (Frozen Scythe Ability)");
                        }
                        PlayerListener.spawnDamageInd(entity, baseDamage, false);
                    }
                }
                else if (!stand1.isDead()) {
                    final Location loc = stand1.getLocation();
                    loc.setY(loc.getY() + 0.5);
                    p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                    p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                    p.getWorld().spigot().playEffect(loc, Effect.SNOW_SHOVEL, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 40);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 0L);
    }
    
    public static void throwIce(final ArmorStand stand1, final ArmorStand stand2, final ArmorStand stand3, final ArmorStand stand4, final ArmorStand stand5, final Player p, final Vector vecTo, final Vector back) {
        new BukkitRunnable() {
            public void run() {
                stand1.remove();
                stand2.remove();
                stand3.remove();
                stand4.remove();
                stand5.remove();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 80L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 5;
    }
    
    @Override
    public int getManaCost() {
        return 50;
    }
}
