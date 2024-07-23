package net.hypixel.skyblock.gui;

import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;
import org.bukkit.Material;
import org.bukkit.ChatColor;

public class WithdrawalGUI extends GUI
{
    public WithdrawalGUI() {
        super("Bank Withdrawal", 36);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(WithdrawalGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.BANKER, player, (Object)ChatColor.GREEN + "Go Back", 31, Material.ARROW, (Object)ChatColor.GRAY + "To Personal Bank Account"));
        final User user = User.getUser(player.getUniqueId());
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final long coins = user.getBankCoins();
                user.addCoins(coins);
                user.subBankCoins(coins);
                if (user != null) {
                    if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                        user.configsave();
                    }
                    else {
                        user.save();
                    }
                }
                player.sendMessage((Object)ChatColor.GREEN + "You have withdrawn " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + " coins" + (Object)ChatColor.GREEN + "! You now have " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()) + " coins " + (Object)ChatColor.GREEN + "in your account!");
                GUIType.BANKER.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Everything in the account", Material.DROPPER, (short)0, 64, (Object)ChatColor.DARK_GRAY + "Bank withdrawal", " ", (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), (Object)ChatColor.GRAY + "Amount to withdraw: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), " ", (Object)ChatColor.YELLOW + "Click to withdraw coins!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final long coins = user.getBankCoins() / 2L;
                user.addCoins(coins);
                user.subBankCoins(coins);
                if (user != null) {
                    if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                        user.configsave();
                    }
                    else {
                        user.save();
                    }
                }
                player.sendMessage((Object)ChatColor.GREEN + "You have withdrawn " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + " coins" + (Object)ChatColor.GREEN + "! You now have " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()) + " coins " + (Object)ChatColor.GREEN + "in your account!");
                GUIType.BANKER.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Half the account", Material.DROPPER, (short)0, 32, (Object)ChatColor.DARK_GRAY + "Bank withdrawal", " ", (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), (Object)ChatColor.GRAY + "Amount to withdraw: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins() / 2L), " ", (Object)ChatColor.YELLOW + "Click to withdraw coins!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final long coins = Math.round(user.getBankCoins() * 0.2);
                user.addCoins(coins);
                user.subBankCoins(coins);
                if (user != null) {
                    if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                        user.configsave();
                    }
                    else {
                        user.save();
                    }
                }
                player.sendMessage((Object)ChatColor.GREEN + "You have withdrawn " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + " coins" + (Object)ChatColor.GREEN + "! You now have " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()) + " coins " + (Object)ChatColor.GREEN + "in your account!");
                GUIType.BANKER.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Withdraw 20%", Material.DROPPER, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Bank withdrawal", " ", (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), (Object)ChatColor.GRAY + "Amount to withdraw: " + (Object)ChatColor.GOLD + SUtil.commaify(Math.round(user.getBankCoins() * 0.2)), " ", (Object)ChatColor.YELLOW + "Click to withdraw coins!");
            }
        });
        this.set(new GUIQueryItem() {
            @Override
            public GUI onQueryFinish(final String query) {
                try {
                    final long coins = Long.parseLong(query);
                    if (coins < 0L) {
                        player.sendMessage((Object)ChatColor.RED + "Enter a positive number!");
                        return null;
                    }
                    if (coins > user.getBankCoins()) {
                        player.sendMessage((Object)ChatColor.RED + "You do not have that many coins!");
                        return null;
                    }
                    user.addCoins(coins);
                    user.subBankCoins(coins);
                    if (user != null) {
                        if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                            user.configsave();
                        }
                        else {
                            user.save();
                        }
                    }
                    player.sendMessage((Object)ChatColor.GREEN + "You have withdrawn " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + " coins" + (Object)ChatColor.GREEN + "! You now have " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()) + " coins " + (Object)ChatColor.GREEN + "in your account!");
                }
                catch (final NumberFormatException ex) {
                    player.sendMessage((Object)ChatColor.RED + "That is not a valid number!");
                    return null;
                }
                return new BankerGUI();
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 16;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Specific amount", Material.SIGN, (short)0, 1, (Object)ChatColor.GRAY + "Current balance: " + (Object)ChatColor.GOLD + SUtil.commaify(user.getBankCoins()), " ", (Object)ChatColor.YELLOW + "Click to withdraw coins!");
            }
        });
    }
}
