package net.hypixel.skyblock.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;

public class BankerGUI extends GUI
{
    public BankerGUI() {
        super("Bank", 36);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(e.getPlayer().getUniqueId());
        this.fill(BankerGUI.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(31));
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Deposit Coins", Material.CHEST, (short)0, 1, (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), " ", (Object)ChatColor.GRAY + "Store coins in the bank to", (Object)ChatColor.GRAY + "keep them safe while you go", (Object)ChatColor.GRAY + "on adventures!", " ", (Object)ChatColor.GRAY + "You will earn " + (Object)ChatColor.AQUA + "2%" + (Object)ChatColor.GRAY + " interest every", (Object)ChatColor.GRAY + "season for your first " + (Object)ChatColor.GOLD + "10 million", (Object)ChatColor.GRAY + "banked coins.", " ", (Object)ChatColor.GRAY + "Until interest: " + (Object)ChatColor.RED + "Unavailable", " ", (Object)ChatColor.YELLOW + "Click to make a deposit!");
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.BANKER_DEPOSIT.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Withdraw Coins", Material.DROPPER, (short)0, 1, (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), " ", (Object)ChatColor.GRAY + "Take your coins out of the", (Object)ChatColor.GRAY + "bank in order to spend", (Object)ChatColor.GRAY + "them.", " ", (Object)ChatColor.YELLOW + "Click to withdraw coins!");
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.BANKER_WITHDRAWAL.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
        });
        this.set(15, SUtil.getStack((Object)ChatColor.GREEN + "Recent transactions", Material.MAP, (short)0, 1, " ", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "COMING SOON"));
        this.set(32, SUtil.getStack((Object)ChatColor.GREEN + "Information", Material.REDSTONE_TORCH_ON, (short)0, 1, (Object)ChatColor.GRAY + "Keep your coins safe in the bank!", (Object)ChatColor.GRAY + "You lose half the coins in your", (Object)ChatColor.GRAY + "purse when dying in combat.", " ", (Object)ChatColor.GRAY + "Balance limit: " + (Object)ChatColor.GOLD + "50 Million", " ", (Object)ChatColor.GRAY + "The banker rewards you every 31", (Object)ChatColor.GRAY + "hours with " + (Object)ChatColor.AQUA + "interest" + (Object)ChatColor.GRAY + " for the", (Object)ChatColor.GRAY + "coins in your bank balance.", " ", (Object)ChatColor.GRAY + "Interest " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "COMING SOON"));
    }
}
