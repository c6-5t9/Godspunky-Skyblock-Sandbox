package net.hypixel.skyblock.item.orb;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;

public class RadiantPowerOrb extends PowerOrb
{
    @Override
    public String getAbilityDescription() {
        return "Place an orb for " + (Object)ChatColor.GREEN + "30s " + (Object)ChatColor.GRAY + "buffing up to " + (Object)ChatColor.AQUA + "5" + (Object)ChatColor.GRAY + " players within " + (Object)ChatColor.GREEN + "18 " + (Object)ChatColor.GRAY + "blocks. " + (Object)ChatColor.DARK_GRAY + "Costs " + (Object)ChatColor.DARK_GRAY + "50% of max mana. " + (Object)ChatColor.DARK_GRAY + "Only " + (Object)ChatColor.DARK_GRAY + "one orb applies per player.";
    }
    
    @Override
    public String getURL() {
        return "7ab4c4d6ee69bc24bba2b8faf67b9f704a06b01aa93f3efa6aef7a9696c4feef";
    }
    
    @Override
    public String getDisplayName() {
        return "Radiant Power Orb";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public String getBuffName() {
        return "Radiant";
    }
    
    @Override
    public String getBuffDescription() {
        return "Heals " + (Object)ChatColor.RED + "1% " + (Object)ChatColor.GRAY + "of max " + (Object)ChatColor.RED + "\u2764 " + (Object)ChatColor.GRAY + "per second.";
    }
    
    @Override
    protected void buff(final Player player) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getMaxHealth() * 0.01));
    }
    
    @Override
    protected long getOrbLifeTicks() {
        return 600L;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    protected void playEffect(final Location location) {
        location.getWorld().playEffect(location, Effect.HAPPY_VILLAGER, (Object)Effect.HAPPY_VILLAGER.getData());
    }
}
