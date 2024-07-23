package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.gui.CookieConfirmGUI;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Untradeable;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class BoosterCookie implements MaterialStatistics, MaterialFunction, Ability, Untradeable
{
    @Override
    public String getDisplayName() {
        return "Booster Cookie";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public String getAbilityName() {
        return "Drink!";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("&7Consume to gain the &dCookie Buff &7for &b2 &7days:");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 8;
    }
    
    @Override
    public boolean displayCooldown() {
        return false;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        new CookieConfirmGUI(player.getInventory().getHeldItemSlot(), player.getInventory().getItemInHand()).open(player);
    }
}
