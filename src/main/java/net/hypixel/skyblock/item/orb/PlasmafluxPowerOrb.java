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

public class PlasmafluxPowerOrb extends PowerOrb
{
    @Override
    public String getAbilityDescription() {
        return "Place an orb for " + (Object)ChatColor.GREEN + "60s " + (Object)ChatColor.GRAY + "buffing up to " + (Object)ChatColor.AQUA + "5 " + (Object)ChatColor.GRAY + "players within " + (Object)ChatColor.GREEN + "18 " + (Object)ChatColor.GRAY + "blocks. " + (Object)ChatColor.DARK_GRAY + "Costs " + (Object)ChatColor.DARK_GRAY + "50% of max mana. " + (Object)ChatColor.DARK_GRAY + "Only " + (Object)ChatColor.DARK_GRAY + "one orb applies per player.";
    }
    
    @Override
    public String getURL() {
        return "83ed4ce23933e66e04df16070644f7599eeb55307f7eafe8d92f40fb3520863c";
    }
    
    @Override
    public String getDisplayName() {
        return "Plasmaflux Power Orb";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public String getBuffName() {
        return "Plasmaflux";
    }
    
    @Override
    public String getBuffDescription() {
        return "Grants " + (Object)ChatColor.AQUA + "+125% " + (Object)ChatColor.GRAY + "base mana regen. Heals " + (Object)ChatColor.RED + "3% " + (Object)ChatColor.GRAY + "of max " + (Object)ChatColor.RED + "\u2764 " + (Object)ChatColor.GRAY + "per second. Increases all heals by " + (Object)ChatColor.GREEN + "7%" + (Object)ChatColor.GRAY + ". Grants " + (Object)ChatColor.RED + "+35 " + (Object)ChatColor.RED + "\u2741 Strength";
    }
    
    @Override
    public String getCustomOrbName() {
        return "" + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD + "Plasmaflux";
    }
    
    @Override
    protected void buff(final Player player) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getMaxHealth() * 0.03));
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
                return 35.0;
            }
        }, 20L);
        if (!player.hasMetadata("NPC")) {
            statistics.boostManaRegeneration(1.25, 20L);
            statistics.boostHealthRegeneration(0.075, 20L);
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
        location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 0, 1, 0.28235295f, 0.007843138f, 0.4f, 1.0f, 0, 64);
    }
}
