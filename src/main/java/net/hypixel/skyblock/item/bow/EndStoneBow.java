package net.hypixel.skyblock.item.bow;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.AbilityActivation;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class EndStoneBow implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Extreme Focus";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Consumes all your Mana, and your next hit will deal that much more Damage!";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        player.sendMessage((Object)ChatColor.GRAY + "Incomplete ability.");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return -1;
    }
    
    @Override
    public AbilityActivation getAbilityActivation() {
        return AbilityActivation.NO_ACTIVATION;
    }
    
    @Override
    public String getDisplayName() {
        return "End Stone Bow";
    }
    
    @Override
    public int getBaseDamage() {
        return 140;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }
}
