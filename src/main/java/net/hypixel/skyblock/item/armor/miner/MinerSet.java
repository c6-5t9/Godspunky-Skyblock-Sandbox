package net.hypixel.skyblock.item.armor.miner;

import net.hypixel.skyblock.user.DoublePlayerStatistic;
import net.hypixel.skyblock.util.Groups;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.features.region.Region;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.item.armor.TickingSet;

public class MinerSet implements TickingSet
{
    @Override
    public String getName() {
        return "Regeneration";
    }
    
    @Override
    public String getDescription() {
        return "Regenerates 5% of your max Health every second if you have been out of combat for the last 8 seconds.";
    }
    
    @Override
    public Class<? extends MaterialStatistics> getHelmet() {
        return MinerHelmet.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getChestplate() {
        return MinerChestplate.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getLeggings() {
        return MinerLeggings.class;
    }
    
    @Override
    public Class<? extends MaterialStatistics> getBoots() {
        return MinerBoots.class;
    }
    
    @Override
    public void tick(final Player owner, final SItem helmet, final SItem chestplate, final SItem leggings, final SItem boots, final List<AtomicInteger> counters) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)owner.getUniqueId());
        final DoublePlayerStatistic defense = statistics.getDefense();
        final PlayerListener.CombatAction action = PlayerListener.getLastCombatAction(owner);
        ((AtomicInteger)counters.get(0)).incrementAndGet();
        if ((action == null || (action.getTimeStamp() + 8000L <= System.currentTimeMillis() && helmet != null && chestplate != null && leggings != null && boots != null)) && ((AtomicInteger)counters.get(0)).get() >= 2) {
            owner.setHealth(Math.min(owner.getMaxHealth(), owner.getHealth() + owner.getMaxHealth() * 0.05));
            ((AtomicInteger)counters.get(0)).set(0);
        }
        final Region region = Region.getRegionOfEntity((Entity)owner);
        if (region == null) {
            return;
        }
        if (!Groups.DEEP_CAVERNS_REGIONS.contains((Object)region.getType())) {
            return;
        }
        if (helmet != null) {
            defense.add(8, Double.valueOf(45.0));
        }
        if (chestplate != null) {
            defense.add(8, Double.valueOf(95.0));
        }
        if (leggings != null) {
            defense.add(8, Double.valueOf(70.0));
        }
        if (boots != null) {
            defense.add(8, Double.valueOf(45.0));
        }
    }
}
