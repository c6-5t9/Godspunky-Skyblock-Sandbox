package net.hypixel.skyblock.item.armor.lapis;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.item.armor.ArmorSet;

public class LapisArmorSet implements ArmorSet
{
    @Override
    public String getName() {
        return "Health";
    }
    
    @Override
    public String getDescription() {
        return "Increases the wearer's maximum Health by 60.";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return LapisArmorHelmet.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return LapisArmorChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return LapisArmorLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return LapisArmorBoots.class;
    }
    
    @Override
    public PlayerBoostStatistics whileHasFullSet(final Player player) {
        return new PlayerBoostStatistics() {
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
            public double getBaseHealth() {
                return 60.0;
            }
        };
    }
}
