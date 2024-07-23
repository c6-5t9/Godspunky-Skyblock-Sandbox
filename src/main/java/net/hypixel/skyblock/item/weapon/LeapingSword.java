package net.hypixel.skyblock.item.weapon;

import org.bukkit.inventory.PlayerInventory;
import java.util.Iterator;
import org.bukkit.entity.Damageable;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class LeapingSword implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 150;
    }
    
    @Override
    public double getBaseStrength() {
        return 100.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.25;
    }
    
    @Override
    public String getDisplayName() {
        return "Leaping Sword";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
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
        return "Leap";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Leap into the air and deal " + (Object)ChatColor.RED + "350 " + (Object)ChatColor.GRAY + "damage to any nearby enemies upon landing on the ground. Damaged enemies will also be frozen for 1 second.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        if (!Sputnik.tpAbilUsable(player)) {
            return;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(3).setY(2));
        new BukkitRunnable() {
            public void run() {
                if (player.getLocation().subtract(0.0, 0.5, 0.0).getBlock().getType() == Material.AIR) {
                    return;
                }
                player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 2.0f, 1.0f);
                player.playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, (Object)Effect.EXPLOSION_LARGE.getData());
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
                for (final Entity entity : player.getWorld().getNearbyEntities(player.getLocation().add(player.getLocation().getDirection().multiply(3.0)), 5.0, 5.0, 5.0)) {
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
                    final User user = User.getUser(player.getUniqueId());
                    int baseMagicDmg = 350;
                    final PlayerInventory inv = player.getInventory();
                    final SItem helmet = SItem.find(inv.getHelmet());
                    if (helmet != null) {
                        if (helmet.getType() == SMaterial.DARK_GOGGLES) {
                            baseMagicDmg += baseMagicDmg * 25 / 100;
                        }
                        else if (helmet.getType() == SMaterial.SHADOW_GOGGLES) {
                            baseMagicDmg += baseMagicDmg * 35 / 100;
                        }
                        else if (helmet.getType() == SMaterial.WITHER_GOGGLES) {
                            baseMagicDmg += baseMagicDmg * 45 / 100;
                        }
                    }
                    final double baseDamage = baseMagicDmg * (manaPool / 100 + 1);
                    final ArmorStand stands = (ArmorStand)new SEntity(entity.getLocation().clone().add(SUtil.random(-1.5, 1.5), 1.0, SUtil.random(-1.5, 1.5)), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]).getEntity();
                    stands.setCustomName("" + (Object)ChatColor.GRAY + (int)baseDamage);
                    stands.setCustomNameVisible(true);
                    stands.setGravity(false);
                    stands.setVisible(false);
                    new BukkitRunnable() {
                        public void run() {
                            stands.remove();
                            this.cancel();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
                    user.damageEntity((Damageable)entity, baseDamage);
                }
                this.cancel();
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 10L, 2L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 20;
    }
    
    @Override
    public int getManaCost() {
        return 50;
    }
}
