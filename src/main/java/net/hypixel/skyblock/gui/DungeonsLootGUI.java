package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;

public class DungeonsLootGUI extends GUI
{
    private final Block bl;
    
    public DungeonsLootGUI(final ItemStack loot, final Block loc) {
        super("Chest", 27);
        this.bl = loc;
        this.set(13, loot, true);
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        final ItemStack[] se = e.getInventory().getContents();
        for (int i = 0; i < se.length; ++i) {
            if (se[i] != null) {
                this.bl.getWorld().dropItemNaturally(this.bl.getLocation().clone().add(0.5, 0.8, 0.5), se[i]);
            }
        }
    }
    
    @Override
    public void onBottomClick(final InventoryClickEvent e) {
        if (e.getAction() != InventoryAction.PLACE_ALL) {
            e.setCancelled(true);
        }
        else {
            SUtil.delay(() -> e.getWhoClicked().closeInventory(), 2L);
        }
    }
    
    @Override
    public void onTopClick(final InventoryClickEvent e) {
        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            SUtil.delay(() -> e.getWhoClicked().closeInventory(), 2L);
        }
    }
}
