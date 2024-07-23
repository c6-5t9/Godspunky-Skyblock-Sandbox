package net.hypixel.skyblock.item.armor;

import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import org.bukkit.Material;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.TickingMaterial;

public class ObsidianChestplate implements LeatherArmorStatistics, TickingMaterial
{
    @Override
    public int getColor() {
        return 0;
    }
    
    @Override
    public String getDisplayName() {
        return "Obsidian Chestplate";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.CHESTPLATE;
    }
    
    @Override
    public double getBaseDefense() {
        return 250.0;
    }
    
    @Override
    public void tick(final SItem item, final Player owner) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)owner.getUniqueId());
        statistics.getSpeed().zero(9);
        int obsidian = 0;
        for (final Map.Entry<Integer, ? extends ItemStack> entry : owner.getInventory().all(Material.OBSIDIAN).entrySet()) {
            obsidian += ((ItemStack)entry.getValue()).getAmount();
        }
        statistics.getSpeed().add(9, Double.valueOf(obsidian / 20.0 / 100.0));
        new BukkitRunnable() {
            public void run() {
                final SItem chestplate = SItem.find(owner.getInventory().getChestplate());
                if (chestplate != null && chestplate.getType() == SMaterial.OBSIDIAN_CHESTPLATE) {
                    return;
                }
                statistics.getSpeed().zero(9);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 13L);
    }
    
    @Override
    public long getInterval() {
        return 10L;
    }
}
