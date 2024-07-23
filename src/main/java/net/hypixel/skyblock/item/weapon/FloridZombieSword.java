package net.hypixel.skyblock.item.weapon;

import java.util.Iterator;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.util.ZSHash;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class FloridZombieSword implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 150;
    }
    
    @Override
    public double getBaseStrength() {
        return 80.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 100.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Florid Zombie Sword";
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
        return "Instant Heal";
    }
    
    @Override
    public String getAbilityDescription() {
        return ChatColor.translateAlternateColorCodes('&', "&7Heal yourself for &c168 &7+ &c5%\u2764 &7and players within &a7 &7blocks for &c56\u2764");
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        if (!ZSHash.Charges.containsKey((Object)player1.getUniqueId())) {
            ZSHash.Charges.put((Object)player1.getUniqueId(), (Object)5);
        }
        if ((int)ZSHash.Charges.get((Object)player1.getUniqueId()) > 0) {
            final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player1.getUniqueId())).getIntelligence().addAll() + 100.0);
            final int manaCost = 70;
            final int cost = PlayerUtils.getFinalManaCost(player1, sItem, 70);
            final boolean take = PlayerUtils.takeMana(player1, cost);
            if (!take) {
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                final long c = System.currentTimeMillis();
                Repeater.MANA_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new ManaReplacement() {
                    @Override
                    public String getReplacement() {
                        return "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "NOT ENOUGH MANA";
                    }
                    
                    @Override
                    public long getEnd() {
                        return c + 1500L;
                    }
                });
                return;
            }
            final long c = System.currentTimeMillis();
            Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new DefenseReplacement() {
                @Override
                public String getReplacement() {
                    return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + FloridZombieSword.this.getAbilityName() + (Object)ChatColor.AQUA + ")";
                }
                
                @Override
                public long getEnd() {
                    return c + 2000L;
                }
            });
            ZSHash.Charges.put((Object)player1.getUniqueId(), (Object)((int)ZSHash.Charges.get((Object)player1.getUniqueId()) - 1));
            if ((int)ZSHash.Charges.get((Object)player1.getUniqueId()) == 0) {
                ZSHash.Cooldown.put((Object)player1.getUniqueId(), (Object)15);
            }
            player1.playSound(player1.getLocation(), Sound.ZOMBIE_REMEDY, 0.5f, 1.0f);
            player1.playSound(player1.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 1.0f);
            final double healamount = 168.0 + player1.getMaxHealth() * 5.0 / 100.0;
            player1.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lYou healed yourself for " + SUtil.commaify((int)healamount) + " health!"));
            if (player1.getMaxHealth() < player1.getHealth() + healamount) {
                player1.setHealth(player1.getMaxHealth());
            }
            else {
                player1.setHealth(player1.getHealth() + healamount);
            }
            for (final Entity e : player1.getNearbyEntities(8.0, 8.0, 8.0)) {
                if (e instanceof LivingEntity && e instanceof Player) {
                    if (((LivingEntity)e).getMaxHealth() < ((LivingEntity)e).getHealth() + 48.0) {
                        ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    }
                    else {
                        ((LivingEntity)e).setHealth(player1.getHealth() + 56.0);
                    }
                    e.sendMessage("" + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + player1.getName() + " healed you for 56 health!");
                }
            }
        }
        else {
            player1.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo more charges, next one in &e" + ZSHash.Cooldown.get((Object)player1.getUniqueId()) + "s"));
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
}
