package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class JerryChineGun implements ToolStatistics, MaterialFunction, Ability
{
    String ACT;
    
    public JerryChineGun() {
        this.ACT = "true";
    }
    
    @Override
    public int getBaseDamage() {
        return 80;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 200.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Jerry-chine Gun (Removed)";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Rapid-fire" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + " REMOVED!";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Shoots a Jerry bullet, dealing " + (Object)ChatColor.RED + "500 " + (Object)ChatColor.GRAY + "damage on impact and knocking you back.";
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        player1.sendMessage((Object)ChatColor.RED + "This item ability has been removed from the game!");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
}
