package net.hypixel.skyblock.gui;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface GUIClickableItem extends GUIItem
{
    void run(final InventoryClickEvent p0) throws IOException;
    
    default GUIClickableItem getCloseItem(final int slot) {
        return new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
            }
            
            @Override
            public int getSlot() {
                return slot;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.createNamedItemStack(Material.BARRIER, (Object)ChatColor.RED + "Close");
            }
        };
    }
    
    default GUIClickableItem createGUIOpenerItem(final GUI gui, final Player player, final String name, final int slot, final Material type, final short data, final String... lore) {
        return new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(name, type, data, 1, lore);
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                if (gui == null) {
                    return;
                }
                gui.open(player);
            }
            
            @Override
            public int getSlot() {
                return slot;
            }
        };
    }
    
    default GUIClickableItem createGUIOpenerItemHead(final GUI gui, final Player player, final String name, final int slot, final String headURL, final int amount, final String... lore) {
        return new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack(name, headURL, amount, lore);
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                if (gui == null) {
                    return;
                }
                gui.open(player);
            }
            
            @Override
            public int getSlot() {
                return slot;
            }
        };
    }
    
    default GUIClickableItem createGUIOpenerItemStack(final GUI gui, final Player player, final int slot, final ItemStack stack) {
        return new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return stack;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                if (gui == null) {
                    return;
                }
                gui.open(player);
            }
            
            @Override
            public int getSlot() {
                return slot;
            }
        };
    }
    
    default GUIClickableItem createGUIOpenerItem(final GUIType guiType, final Player player, final String name, final int slot, final Material type, final short data, final String... lore) {
        return createGUIOpenerItem(guiType.getGUI(), player, name, slot, type, data, lore);
    }
    
    default GUIClickableItem createGUIOpenerItem(final GUIType guiType, final Player player, final String name, final int slot, final Material type, final String... lore) {
        return createGUIOpenerItem(guiType, player, name, slot, type, (short)0, lore);
    }
}
