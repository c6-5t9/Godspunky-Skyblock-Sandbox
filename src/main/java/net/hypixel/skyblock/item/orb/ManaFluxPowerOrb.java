package net.hypixel.skyblock.item.orb;

import org.bukkit.Effect;
import org.bukkit.Location;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;

public class ManaFluxPowerOrb extends PowerOrb
{
    @Override
    public String getAbilityDescription() {
        return "Place an orb for " + (Object)ChatColor.GREEN + "30s " + (Object)ChatColor.GRAY + "buffing up to " + (Object)ChatColor.AQUA + "5 " + (Object)ChatColor.GRAY + "players within " + (Object)ChatColor.GREEN + "18 " + (Object)ChatColor.GRAY + "blocks. " + (Object)ChatColor.DARK_GRAY + "Costs " + (Object)ChatColor.DARK_GRAY + "50% of max mana. " + (Object)ChatColor.DARK_GRAY + "Only " + (Object)ChatColor.DARK_GRAY + "one orb applies per player.";
    }
    
    @Override
    public String getURL() {
        return "82ada1c7fcc8cf35defeb944a4f8ffa9a9d260560fc7f5f5826de8085435967c";
    }
    
    @Override
    public String getDisplayName() {
        return "Mana Flux Power Orb";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public String getBuffName() {
        return "Mana Flux";
    }
    
    @Override
    public String getBuffDescription() {
        return "Grants " + (Object)ChatColor.AQUA + "+50% " + (Object)ChatColor.GRAY + "base mana regen. Heals " + (Object)ChatColor.RED + "2% " + (Object)ChatColor.GRAY + "of max " + (Object)ChatColor.RED + "\u2764 " + (Object)ChatColor.GRAY + "per second. Grants " + (Object)ChatColor.RED + "+10 \u2741 " + (Object)ChatColor.RED + "Strength";
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    protected void buff(final Player player) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getMaxHealth() * 0.02));
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        PlayerUtils.boostPlayer(statistics, new PlayerBoostStatistics() {
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
            public double getBaseStrength() {
                return 10.0;
            }
        }, 20L);
        if (!player.hasMetadata("NPC")) {
            statistics.boostManaRegeneration(0.5, 20L);
        }
    }
    
    @Override
    protected long getOrbLifeTicks() {
        return 600L;
    }
    
    @Override
    protected void playEffect(final Location location) {
        location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 0, 1, 0.1882353f, 0.5411765f, 0.8509804f, 1.0f, 0, 64);
    }
}
