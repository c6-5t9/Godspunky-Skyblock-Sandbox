package net.hypixel.skyblock.item.bow;

import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.AbilityActivation;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.ToolStatistics;

public class RunaansBow implements ToolStatistics, BowFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Triple-shot";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Shoots 3 arrows at a time! The 2 extra arrows deal " + (Object)ChatColor.GREEN + "40% " + (Object)ChatColor.GRAY + "percent of the damage and home to targets.";
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public AbilityActivation getAbilityActivation() {
        return AbilityActivation.NO_ACTIVATION;
    }
    
    @Override
    public String getDisplayName() {
        return "Runaan's Bow";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }
    
    @Override
    public int getBaseDamage() {
        return 160;
    }
    
    @Override
    public double getBaseStrength() {
        return 50.0;
    }
    
    @Override
    public void onBowShoot(final SItem bow, final EntityShootBowEvent e) {
        final Player shooter = (Player)e.getEntity();
        final Location location = shooter.getEyeLocation().add(shooter.getEyeLocation().getDirection().toLocation(shooter.getWorld()));
        final float speed = e.getForce() * 3.0f;
        final Location l = location.clone();
        l.setYaw(location.getYaw() - 30.0f);
        shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter((ProjectileSource)shooter);
        l.setYaw(location.getYaw() + 30.0f);
        shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter((ProjectileSource)shooter);
    }
}
