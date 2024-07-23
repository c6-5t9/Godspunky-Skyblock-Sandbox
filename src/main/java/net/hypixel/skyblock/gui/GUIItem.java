package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface GUIItem
{
    int getSlot();
    
    default ItemStack getItem() {
        return new ItemStack(Material.AIR);
    }
    
    default boolean canPickup() {
        return false;
    }
    
    default GUIItem createLoadingItem(final Material type, final String name, final int slot) {
        return new GUIItem() {
            @Override
            public int getSlot() {
                return slot;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSingleLoreStack(name, type, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Loading...");
            }
        };
    }
}
