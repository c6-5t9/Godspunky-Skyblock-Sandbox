package net.hypixel.skyblock.item.armor.sorrow;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.item.armor.TickingSet;

public class SorrowArmorSet implements TickingSet
{
    @Override
    public String getName() {
        return "Mist Aura";
    }
    
    @Override
    public String getDescription() {
        return "Hides the wearer in a guise of mist.";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return SorrowArmorHelmet.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return SorrowArmorChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return SorrowArmorLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return SorrowArmorBoots.class;
    }
    
    @Override
    public void tick(final Player owner, final SItem helmet, final SItem chestplate, final SItem leggings, final SItem boots, final List<AtomicInteger> counters) {
    }
}
