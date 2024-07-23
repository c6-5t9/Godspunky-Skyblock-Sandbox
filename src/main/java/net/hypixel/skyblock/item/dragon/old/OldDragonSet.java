package net.hypixel.skyblock.item.dragon.old;

import net.hypixel.skyblock.item.PlayerBoostStatistics;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.item.armor.ArmorSet;

public class OldDragonSet implements ArmorSet
{
    @Override
    public String getName() {
        return "Old Blood";
    }
    
    @Override
    public String getDescription() {
        return "Increases the strength of Growth, Protection, Feather Falling, Sugar Rush, and True Protection enchantments while worn.";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return OldDragonHelmet.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return OldDragonChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return OldDragonLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return OldDragonBoots.class;
    }
    
    @Override
    public PlayerBoostStatistics whileHasFullSet(final Player player) {
        return null;
    }
}
