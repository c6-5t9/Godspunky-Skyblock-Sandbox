package net.hypixel.skyblock.item.weapon;

import org.bukkit.Effect;
import org.bukkit.util.Vector;
import java.util.Iterator;
import org.bukkit.Location;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Sound;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.util.EntityManager;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Material;
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

public class Excrarion implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 300;
    }
    
    @Override
    public double getBaseStrength() {
        return 140.0;
    }
    
    @Override
    public double getBaseFerocity() {
        return 10.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 1000.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Excrarion";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.MYTHIC;
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
    public String getAbilityName() {
        return "Dimoon Impact";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Teleports " + (Object)ChatColor.GREEN + "10 blocks" + (Object)ChatColor.GRAY + " ahead of you. Then implode dealing " + (Object)ChatColor.RED + "20,000" + (Object)ChatColor.GRAY + " damage to nearby enemies. Also applies the " + (Object)ChatColor.GOLD + "ultra" + (Object)ChatColor.GOLD + " wither shield" + (Object)ChatColor.GRAY + " scroll ability reducing damage taken and granting an " + (Object)ChatColor.GOLD + "\u2764 " + (Object)ChatColor.GOLD + "Absorption" + (Object)ChatColor.GRAY + " shield for " + (Object)ChatColor.YELLOW + "5" + (Object)ChatColor.GRAY + " seconds.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        double entityTotal = 0.0;
        int j = 0;
        try {
            int f_ = 10;
            for (int range = 1; range < 10; ++range) {
                final Location location = player.getTargetBlock((Set)null, range).getLocation();
                if (location.getBlock().getType() != Material.AIR) {
                    f_ = range;
                    break;
                }
            }
            final Location location2 = player.getTargetBlock((Set)null, f_ - 1).getLocation();
            location2.setYaw(player.getLocation().getYaw());
            location2.setPitch(player.getLocation().getPitch());
            location2.add(0.5, 0.0, 0.5);
            if (f_ != 10) {
                player.sendMessage((Object)ChatColor.RED + "There are blocks in the way!");
            }
            if (f_ > 1) {
                Sputnik.teleport(player, location2);
            }
            else {
                Sputnik.teleport(player, player.getLocation());
            }
            Sputnik.witherShieldActive2(player);
            for (final Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 6.0, 6.0, 6.0)) {
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
                double baseDamage = Sputnik.calMagicDamage(player, 20000, 0.3);
                final User user = User.getUser(player.getUniqueId());
                if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity)) {
                    int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity);
                    if (defensepercent > 100) {
                        defensepercent = 100;
                    }
                    baseDamage -= baseDamage * defensepercent / 100.0;
                }
                user.damageEntityIgnoreShield((Damageable)entity, (int)baseDamage);
                PlayerListener.spawnDamageInd(entity, baseDamage, false);
                entityTotal += baseDamage;
            }
        }
        catch (final IllegalStateException ex) {}
        player.playSound(player.getLocation(), Sound.EXPLODE, 3.0f, 2.0f);
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float)player.getLocation().getX(), (float)player.getLocation().getY(), (float)player.getLocation().getZ(), 0.0f, 0.0f, 0.0f, 7.0f, 6, new int[0]);
        for (final Player online : player.getWorld().getPlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
        if (j > 0) {
            if (j == 1) {
                player.sendMessage((Object)ChatColor.GRAY + "Your Dimoon Implosion hit " + (Object)ChatColor.RED + j + (Object)ChatColor.GRAY + " enemy for " + (Object)ChatColor.RED + SUtil.commaify(entityTotal) + (Object)ChatColor.GRAY + " damage.");
            }
            else {
                player.sendMessage((Object)ChatColor.GRAY + "Your Dimoon Implosion hit " + (Object)ChatColor.RED + j + (Object)ChatColor.GRAY + " enemies for " + (Object)ChatColor.RED + SUtil.commaify(entityTotal) + (Object)ChatColor.GRAY + " damage.");
            }
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 3;
    }
    
    @Override
    public int getManaCost() {
        return 600;
    }
    
    private void createCircle(final Player player, final double radius, final int distance) {
        final Vector dist = player.getEyeLocation().getDirection().multiply(distance);
        final Location mid = player.getEyeLocation().add(dist);
        for (int particles = 18, i = 0; i < particles; ++i) {
            final double angle = 6.283185307179586 * i / particles;
            final double x = Math.cos(angle) * radius;
            final double y = Math.sin(angle) * radius;
            Vector v = this.rotateAroundAxisX(new Vector(x, y, 0.0), player.getEyeLocation().getPitch());
            v = this.rotateAroundAxisY(v, player.getEyeLocation().getYaw());
            final Location temp = mid.clone().add(v);
            player.getWorld().spigot().playEffect(temp, Effect.FLAME, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
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
}
