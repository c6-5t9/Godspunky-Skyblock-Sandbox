package net.hypixel.skyblock.item.armor.eleganttux;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.armor.TickingSet;

public class ElegantTuxedo implements TickingSet
{
    @Override
    public String getName() {
        return "Dashing!";
    }
    
    @Override
    public String getDescription() {
        return "Max health set to " + (Object)ChatColor.RED + "250\u2764 " + (Object)ChatColor.GRAY + "Deal " + (Object)ChatColor.GREEN + "+150% " + (Object)ChatColor.GRAY + "more damage!";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return nullhelm.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return ElegantTuxChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return ElegantTuxLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return ElegantTuxBoots.class;
    }
    
    @Override
    public void tick(final Player owner, final SItem helmet, final SItem chestplate, final SItem leggings, final SItem boots, final List<AtomicInteger> counters) {
    }
}
