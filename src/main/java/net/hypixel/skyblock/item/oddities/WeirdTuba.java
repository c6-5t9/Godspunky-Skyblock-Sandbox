package net.hypixel.skyblock.item.oddities;

import java.util.Iterator;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class WeirdTuba implements MaterialStatistics, MaterialFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Howl";
    }
    
    @Override
    public String getAbilityDescription() {
        return "You and 4 nearby players gain:";
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 400;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        player.getWorld().playSound(player.getLocation(), Sound.WOLF_HOWL, 1.0f, 1.0f);
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
                return 0.3;
            }
            
            @Override
            public double getBaseStrength() {
                return 30.0;
            }
        }, 400L);
        for (final Entity e : player.getNearbyEntities(4.0, 4.0, 4.0)) {
            if (e instanceof LivingEntity && e instanceof Player) {
                PlayerUtils.boostPlayer((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)e.getUniqueId()), new PlayerBoostStatistics() {
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
                        return 0.3;
                    }
                    
                    @Override
                    public double getBaseStrength() {
                        return 30.0;
                    }
                }, 400L);
            }
        }
    }
    
    @Override
    public int getManaCost() {
        return 150;
    }
    
    @Override
    public String getDisplayName() {
        return "Weird Tuba";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
}
