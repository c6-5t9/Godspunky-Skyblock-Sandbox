package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.features.region.RegionType;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.features.region.Region;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.Rarity;

public class FarmingTalisman implements AccessoryStatistics, AccessoryFunction
{
    @Override
    public String getDisplayName() {
        return "Farming Talisman";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public String getURL() {
        return "9af328c87b068509aca9834eface197705fe5d4f0871731b7b21cd99b9fddc";
    }
    
    @Override
    public void update(final SItem instance, final Player player, final int accessorySlot) {
        final Region region = Region.getQuickRegionOfEntity((Entity)player);
        if (region == null) {
            return;
        }
        if (region.getType() != RegionType.THE_BARN && region.getType() != RegionType.MUSHROOM_DESERT) {
            return;
        }
        PlayerUtils.addBoostStatistics((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId()), accessorySlot, new PlayerBoostStatistics() {
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
                return 0.1;
            }
        });
    }
}
