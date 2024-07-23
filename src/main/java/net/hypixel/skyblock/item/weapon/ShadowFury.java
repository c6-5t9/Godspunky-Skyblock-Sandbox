package net.hypixel.skyblock.item.weapon;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.hypixel.skyblock.util.FerocityCalculation;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.user.User;
import org.bukkit.Sound;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import java.util.ArrayList;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class ShadowFury implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 310;
    }
    
    @Override
    public double getBaseStrength() {
        return 130.0;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.3;
    }
    
    @Override
    public String getDisplayName() {
        return "Shadow Fury";
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
        return "";
    }
    
    @Override
    public String getAbilityName() {
        return "Shadow Fury";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Rapidly teleports you to up to " + (Object)ChatColor.AQUA + "5 " + (Object)ChatColor.GRAY + "enemies within " + (Object)ChatColor.YELLOW + "12 " + (Object)ChatColor.GRAY + "blocks, rooting each of them and allowing you to hit them.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        final int count1 = 0;
        final List<Entity> inRange = (List<Entity>)player.getNearbyEntities(12.0, 12.0, 12.0);
        final List<Entity> filteredList = (List<Entity>)new ArrayList();
        for (final Entity e : inRange) {
            if (e instanceof Damageable && e != player && !(e instanceof ArmorStand) && !(e instanceof Player) && !e.hasMetadata("NPC") && !e.hasMetadata("GiantSword")) {
                if (filteredList.size() >= 5) {
                    break;
                }
                if (filteredList.size() < 0) {
                    break;
                }
                filteredList.add((Object)e);
            }
        }
        if (inRange.size() != 0) {
            new BukkitRunnable() {
                private int run = 0;
                
                public void run() {
                    if (this.run < filteredList.size()) {
                        if (!((Entity)filteredList.get(this.run)).isDead()) {
                            player.teleport(((Entity)filteredList.get(this.run)).getLocation().add(((Entity)filteredList.get(this.run)).getLocation().getDirection().multiply(-1)));
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 1.0f);
                            final User user = User.getUser(player.getUniqueId());
                            final Entity e = (Entity)filteredList.get(this.run);
                            final Object[] atp = Sputnik.calculateDamage(player, player, sItem.getStack(), (LivingEntity)e, false);
                            final double finalDamage1 = (float)atp[0];
                            PlayerListener.spawnDamageInd(e, (float)atp[2], (boolean)atp[1]);
                            FerocityCalculation.activeFerocityTimes(player, (LivingEntity)e, (int)finalDamage1, (boolean)atp[1]);
                            user.damageEntity((Damageable)e, finalDamage1);
                            for (final Player p : player.getWorld().getPlayers()) {
                                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)player).getHandle(), 0));
                            }
                        }
                        ++this.run;
                    }
                    else {
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 5L);
        }
        else {
            player.sendMessage((Object)ChatColor.RED + "No nearby target found.");
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 50;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
}
