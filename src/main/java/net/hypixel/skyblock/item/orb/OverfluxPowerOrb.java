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

public class OverfluxPowerOrb extends PowerOrb
{
    @Override
    public String getAbilityDescription() {
        return "Place an orb for " + (Object)ChatColor.GREEN + "60s " + (Object)ChatColor.GRAY + "buffing up to " + (Object)ChatColor.AQUA + "5 " + (Object)ChatColor.GRAY + "players within " + (Object)ChatColor.GREEN + "18 " + (Object)ChatColor.GRAY + "blocks. " + (Object)ChatColor.DARK_GRAY + "Costs " + (Object)ChatColor.DARK_GRAY + "50% of max mana. " + (Object)ChatColor.DARK_GRAY + "Only " + (Object)ChatColor.DARK_GRAY + "one orb applies per player.";
    }
    
    @Override
    public String getURL() {
        return "84859d0adfc93be19bb441e6edfd43f6bfe6912723033f963d009a11c4824510";
    }
    
    @Override
    public String getDisplayName() {
        return "Overflux Power Orb";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public String getBuffName() {
        return "Overflux";
    }
    
    @Override
    public String getBuffDescription() {
        return "Grants " + (Object)ChatColor.AQUA + "+100% " + (Object)ChatColor.GRAY + "base mana regen. Heals " + (Object)ChatColor.RED + "2.5% " + (Object)ChatColor.GRAY + "of max " + (Object)ChatColor.RED + "\u2764 " + (Object)ChatColor.GRAY + "per second. Increases all heals by " + (Object)ChatColor.GREEN + "5%" + (Object)ChatColor.GRAY + ". Grants " + (Object)ChatColor.RED + "+25 \u2741 Strength";
    }
    
    @Override
    protected void buff(final Player player) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getMaxHealth() * 0.025));
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
                return 25.0;
            }
        }, 20L);
        if (!player.hasMetadata("NPC")) {
            statistics.boostManaRegeneration(1.0, 20L);
            statistics.boostHealthRegeneration(0.05, 20L);
        }
    }
    
    @Override
    protected long getOrbLifeTicks() {
        return 1200L;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    protected void playEffect(final Location location) {
        location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 0, 1, 0.4f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
    }
}
