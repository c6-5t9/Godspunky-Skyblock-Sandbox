package net.hypixel.skyblock.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUpdate
{
    public static void removeInventoryItems(final PlayerInventory inv, final Material type, int amount) {
        for (final ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                final int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                }
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) {
                    break;
                }
            }
        }
    }
    
    public static void removeInventoryItemStack(final PlayerInventory inv, final String txt, int amount) {
        for (final ItemStack is : inv.getContents()) {
            if (is != null && is.getItemMeta().getDisplayName().contains((CharSequence)txt)) {
                final int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                }
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) {
                    break;
                }
            }
        }
    }
}
