package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.ShapedRecipe;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.Location;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Material;
import java.util.Set;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.math.BigDecimal;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class AspectOfTheEnd implements ToolStatistics, MaterialFunction, Ability
{
    public BigDecimal a;
    
    @Override
    public int getBaseDamage() {
        return 100;
    }
    
    @Override
    public double getBaseStrength() {
        return 100.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Aspect of the End";
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
        return "Instant Transmission";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Teleports you " + (Object)ChatColor.GREEN + "8 blocks " + (Object)ChatColor.GRAY + "ahead and gain " + (Object)ChatColor.GREEN + "+50 " + (Object)ChatColor.WHITE + "\u2726 " + (Object)ChatColor.WHITE + "Speed " + (Object)ChatColor.GRAY + "for " + (Object)ChatColor.GREEN + "3 seconds.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        try {
            int f_ = 8;
            for (int range = 1; range < 8; ++range) {
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
            if (f_ != 8) {
                player.sendMessage((Object)ChatColor.RED + "There are blocks in the way!");
            }
            if (f_ > 1) {
                Sputnik.teleport(player, location2);
            }
            else {
                Sputnik.teleport(player, player.getLocation());
            }
        }
        catch (final IllegalStateException ex) {}
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 1.0f);
        PlayerUtils.boostPlayer((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId()), new PlayerBoostStatistics() {
            @Override
            public String getDisplayName() {
                return null;
            }
            
            @Override
            public Rarity getRarity() {
                return null;
            }
            
            @Override
            public String getLore() {
                return null;
            }
            
            @Override
            public GenericItemType getType() {
                return null;
            }
            
            @Override
            public double getBaseSpeed() {
                return 0.5;
            }
        }, 60L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 50;
    }
    
    @Override
    public void load() {
        final ShapedRecipe recipe = new ShapedRecipe(SMaterial.ASPECT_OF_THE_END);
        recipe.shape("a", "b", "c");
        recipe.set('a', SMaterial.ENCHANTED_EYE_OF_ENDER, 16);
        recipe.set('b', SMaterial.ENCHANTED_EYE_OF_ENDER, 16);
        recipe.set('c', SMaterial.ENCHANTED_DIAMOND);
    }
}
