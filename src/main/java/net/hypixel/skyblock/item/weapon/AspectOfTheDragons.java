package net.hypixel.skyblock.item.weapon;

import org.bukkit.inventory.PlayerInventory;
import java.util.Iterator;
import org.bukkit.entity.Damageable;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
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
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.requirement.SkillRequirement;
import net.hypixel.skyblock.features.requirement.enums.SkillType;
import net.hypixel.skyblock.features.requirement.AbstractRequirement;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class AspectOfTheDragons implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 225;
    }
    
    @Override
    public double getBaseStrength() {
        return 100.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Aspect of the Dragons";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
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
    public AbstractRequirement getRequirement() {
        return new SkillRequirement(SkillType.COMBAT, 12);
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Dragon Rage";
    }
    
    @Override
    public String getAbilityDescription() {
        return "All Monsters in front of you take " + (Object)ChatColor.RED + "5,000 " + (Object)ChatColor.GRAY + "damage. Hit monsters take large knockback.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 5.0f, 5.0f);
        for (final Entity entity : player.getWorld().getNearbyEntities(player.getLocation().add(player.getLocation().getDirection().multiply(3.0)), 3.0, 3.0, 3.0)) {
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
            final User user = User.getUser(player.getUniqueId());
            entity.setVelocity(player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(-1.0).multiply(8.0));
            int baseMagicDmg = 10000;
            baseMagicDmg += (int)(baseMagicDmg * (statistics.getAbilityDamage().addAll() / 100.0));
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
            final double baseDamage = baseMagicDmg * (manaPool / 100 * 0.1 + 1.0);
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
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 100;
    }
    
    @Override
    public int getManaCost() {
        return 100;
    }
}
