package net.hypixel.skyblock.item.armor.necron;

import net.hypixel.skyblock.item.PlayerBoostStatistics;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.armor.ArmorSet;

public class NecronFullSet implements ArmorSet
{
    @Override
    public String getName() {
        return "Witherborn";
    }
    
    @Override
    public String getDescription() {
        return "Spawns a wither minion every " + (Object)ChatColor.YELLOW + "30 " + (Object)ChatColor.GRAY + "seconds up to a maximum of " + (Object)ChatColor.GREEN + "1 " + (Object)ChatColor.GRAY + "wither. Your withers will travel to and explode on nearby enemies. Reduces the damage you take from withers by " + (Object)ChatColor.GREEN + "10% " + (Object)ChatColor.GRAY + "per piece.";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return NecronHelmet.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return NecronChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return NecronLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return NecronBoots.class;
    }
    
    @Override
    public PlayerBoostStatistics whileHasFullSet(final Player player) {
        return null;
    }
}
