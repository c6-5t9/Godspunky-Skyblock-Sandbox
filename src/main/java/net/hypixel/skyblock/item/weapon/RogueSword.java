package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class RogueSword implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Speed Boost";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Increases your Speed by +20 for 30 seconds.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
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
            public GenericItemType getType() {
                return null;
            }
            
            @Override
            public double getBaseSpeed() {
                return 0.2;
            }
        }, 600L);
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
    public String getDisplayName() {
        return "Rogue Sword";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
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
    public int getBaseDamage() {
        return 20;
    }
}
