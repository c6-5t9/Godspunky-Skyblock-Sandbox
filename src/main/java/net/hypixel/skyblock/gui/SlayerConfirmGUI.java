package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.features.slayer.SlayerBossType;

public class SlayerConfirmGUI extends GUI
{
    public SlayerConfirmGUI(final SlayerBossType type, final Runnable onConfirm) {
        super("Confirm", 27);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                onConfirm.run();
                e.getWhoClicked().closeInventory();
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Confirm", Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "Kill " + type.getType().getPluralName() + " to spawn the", (Object)ChatColor.GRAY + "boss!", "", (Object)ChatColor.YELLOW + "Click to start quest!");
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "Cancel", Material.STAINED_CLAY, (short)14, 1, new String[0]);
            }
            
            @Override
            public int getSlot() {
                return 15;
            }
        });
    }
}
