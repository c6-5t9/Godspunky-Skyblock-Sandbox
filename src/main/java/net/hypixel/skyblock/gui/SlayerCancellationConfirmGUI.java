package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.User;

public class SlayerCancellationConfirmGUI extends GUI
{
    public SlayerCancellationConfirmGUI(final User user) {
        super("Confirm", 27);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                user.setSlayerQuest(null);
                e.getWhoClicked().sendMessage((Object)ChatColor.GREEN + "Your Slayer Quest has been cancelled!");
                GUIType.SLAYER.getGUI().open((Player)e.getWhoClicked());
                user.removeAllSlayerBosses();
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Confirm", Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.RED + "Clears " + (Object)ChatColor.GRAY + "progress towards", (Object)ChatColor.GRAY + "the current Slayer Quest to", (Object)ChatColor.GRAY + "let you pick a different", (Object)ChatColor.GRAY + "one.", "", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "CANCELLING THE QUEST!", (Object)ChatColor.YELLOW + "Click to cancel the quest!");
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
