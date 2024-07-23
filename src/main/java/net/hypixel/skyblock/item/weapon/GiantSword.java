package net.hypixel.skyblock.item.weapon;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Effect;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import java.util.Set;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class GiantSword implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 500;
    }
    
    @Override
    public String getDisplayName() {
        return "Giant's Sword";
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
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Giant's Slam";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Slam your sword into the ground dealing " + (Object)ChatColor.RED + "100,000 " + (Object)ChatColor.GRAY + "damage to nearby enemies.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        int i = 0;
        double j = 0.0;
        final Location location = player.getTargetBlock((Set)null, 6).getLocation();
        final Giant sword = (Giant)player.getWorld().spawnEntity(location, EntityType.GIANT);
        sword.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
        EntityManager.noAI((Entity)sword);
        EntityManager.noHit((Entity)sword);
        EntityManager.shutTheFuckUp((Entity)sword);
        sword.setCustomName("Dinnerbone");
        sword.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        sword.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final ArmorStand stand = (ArmorStand)player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(true);
        stand.setPassenger((Entity)sword);
        sword.getEquipment().setItemInHand(SItem.of(SMaterial.IRON_SWORD).getStack());
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10.0f, 0.0f);
        player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 0.9f, 0.35f);
        player.getWorld().playEffect(sword.getLocation().add(sword.getLocation().getDirection().multiply(3.0)).add(0.0, -1.0, 0.0), Effect.EXPLOSION_HUGE, 1);
        player.getWorld().playEffect(sword.getLocation().add(sword.getLocation().getDirection().multiply(3.0)).add(0.0, -1.0, 0.0), Effect.EXPLOSION_HUGE, 1);
        player.getWorld().playEffect(sword.getLocation().add(sword.getLocation().getDirection().multiply(3.0)).add(0.0, -1.0, 0.0), Effect.EXPLOSION_HUGE, 1);
        for (final Entity entity : sword.getWorld().getNearbyEntities(sword.getLocation().add(sword.getLocation().getDirection().multiply(3.0)), 6.0, 6.0, 6.0)) {
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
            final User user = User.getUser(player.getUniqueId());
            final double baseDamage = Sputnik.calculateMagicDamage(entity, player, 100000, 0.05);
            user.damageEntityIgnoreShield((Damageable)entity, baseDamage);
            ++i;
            j += baseDamage;
            PlayerListener.spawnDamageInd(entity, baseDamage, false);
        }
        if (i > 0) {
            if (i == 1) {
                player.sendMessage((Object)ChatColor.GRAY + "Your Implosion hit " + (Object)ChatColor.RED + i + (Object)ChatColor.GRAY + " enemy for " + (Object)ChatColor.RED + SUtil.commaify(j) + (Object)ChatColor.GRAY + " damage.");
            }
            else {
                player.sendMessage((Object)ChatColor.GRAY + "Your Implosion hit " + (Object)ChatColor.RED + i + (Object)ChatColor.GRAY + " enemies for " + (Object)ChatColor.RED + SUtil.commaify(j) + (Object)ChatColor.GRAY + " damage.");
            }
        }
        new BukkitRunnable() {
            public void run() {
                sword.remove();
                stand.remove();
                this.cancel();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 135L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 400;
    }
    
    @Override
    public int getManaCost() {
        return 100;
    }
}
