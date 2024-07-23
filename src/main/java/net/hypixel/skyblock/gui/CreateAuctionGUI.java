package net.hypixel.skyblock.gui;

import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.SUtil;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.features.auction.AuctionEscrow;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;

public class CreateAuctionGUI extends GUI
{
    private CreateAuctionGUI(final String title) {
        super(title, 54);
        this.fill(CreateAuctionGUI.BLACK_STAINED_GLASS_PANE);
    }
    
    public CreateAuctionGUI() {
        this("Create Auction");
    }
    
    @Override
    public void early(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        if (user.isAuctionCreationBIN()) {
            this.title = "Create BIN Auction";
        }
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final boolean bin = user.isAuctionCreationBIN();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.AUCTION_HOUSE, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (Object)ChatColor.GRAY + "To Auction House"));
        final AuctionEscrow escrow = user.getAuctionEscrow();
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (user.getAuctionEscrow().getItem() == null) {
                    return;
                }
                player.getInventory().addItem(new ItemStack[] { user.getAuctionEscrow().getItem().getStack() });
                user.getAuctionEscrow().setItem(null);
                new CreateAuctionGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                if (user.getAuctionEscrow().getItem() != null) {
                    final SItem display = user.getAuctionEscrow().getItem().clone();
                    final ItemMeta meta = display.getStack().getItemMeta();
                    meta.setDisplayName((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + (Object)ChatColor.UNDERLINE + "AUCTION FOR ITEM:");
                    final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
                    lore.add((Object)((Object)ChatColor.GRAY + "" + display.getStack().getAmount() + "x " + escrow.getItem().getFullName()));
                    lore.add((Object)display.getRarity().getDisplay());
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to pickup!"));
                    meta.setLore((List)lore);
                    display.getStack().setItemMeta(meta);
                    return display.getStack();
                }
                return SUtil.getStack((Object)ChatColor.YELLOW + "Click an item in your inventory!", Material.STONE_BUTTON, (short)0, 1, (Object)ChatColor.GRAY + "Selects it for auction");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (escrow.getItem() == null) {
                    return;
                }
                if (user.getCoins() < escrow.getCreationFee(bin)) {
                    player.sendMessage((Object)ChatColor.RED + "You don't have enough coins to afford this!");
                    return;
                }
                new AuctionConfirmGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 29;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList();
                if (escrow.getItem() == null) {
                    lore.add((Object)((Object)ChatColor.GRAY + "No item selected!"));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Click an item in your"));
                    lore.add((Object)((Object)ChatColor.GRAY + "inventory to select it for"));
                    lore.add((Object)((Object)ChatColor.GRAY + "this auction."));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "This item will be added to"));
                    lore.add((Object)((Object)ChatColor.GRAY + "the auction house for other"));
                    lore.add((Object)((Object)ChatColor.GRAY + "players to purchase."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Item: " + escrow.getItem().getStack().getAmount() + "x " + escrow.getItem().getFullName()));
                    lore.add((Object)((Object)ChatColor.GRAY + "Auction duration: " + (Object)ChatColor.YELLOW + SUtil.getAuctionSetupFormattedTime(escrow.getDuration())));
                    lore.add((Object)((Object)ChatColor.GRAY + "Starting bid: " + (Object)ChatColor.GOLD + SUtil.commaify(escrow.getStarter()) + " coins"));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Creation fee: " + (Object)ChatColor.GOLD + SUtil.commaify(escrow.getCreationFee(user.isAuctionCreationBIN())) + " coins"));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to submit!"));
                }
                return SUtil.getStack((Object)((escrow.getItem() != null) ? ChatColor.GREEN : ChatColor.RED) + "Create Auction", Material.STAINED_CLAY, (short)((escrow.getItem() != null) ? 13 : 14), 1, lore);
            }
        });
        this.set(new GUIQueryItem() {
            @Override
            public GUI onQueryFinish(final String query) {
                long l;
                try {
                    l = Long.parseLong(query);
                    if (l <= 0L) {
                        player.sendMessage((Object)ChatColor.RED + "Could not read this number!");
                        return null;
                    }
                }
                catch (final NumberFormatException ex) {
                    player.sendMessage((Object)ChatColor.RED + "Could not read this number!");
                    return null;
                }
                user.getAuctionEscrow().setStarter(l);
                return new CreateAuctionGUI();
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList();
                if (bin) {
                    lore.add((Object)((Object)ChatColor.GRAY + "The price at which you want"));
                    lore.add((Object)((Object)ChatColor.GRAY + "to sell this item."));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "The minimum price a player"));
                    lore.add((Object)((Object)ChatColor.GRAY + "can offer to obtain your"));
                    lore.add((Object)((Object)ChatColor.GRAY + "item."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Once a player bids for your"));
                    lore.add((Object)((Object)ChatColor.GRAY + "item, other players will"));
                    lore.add((Object)((Object)ChatColor.GRAY + "have until the auction ends"));
                    lore.add((Object)((Object)ChatColor.GRAY + "to make a higher bid."));
                }
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.GRAY + "Extra fee: " + (Object)ChatColor.GOLD + "+" + SUtil.commaify(escrow.getCreationFee(user.isAuctionCreationBIN())) + " coins " + (Object)ChatColor.YELLOW + "(" + (bin ? 1 : 5) + "%)"));
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to edit!"));
                return SUtil.getStack((Object)ChatColor.WHITE + (bin ? "Item price: " : "Starting bid: ") + (Object)ChatColor.GOLD + SUtil.commaify(escrow.getStarter()) + " coins", bin ? Material.GOLD_INGOT : Material.POWERED_RAIL, (short)0, 1, lore);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new AuctionDurationGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList();
                if (bin) {
                    lore.add((Object)((Object)ChatColor.GRAY + "How long the item will be"));
                    lore.add((Object)((Object)ChatColor.GRAY + "up for sale."));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "How long players will be"));
                    lore.add((Object)((Object)ChatColor.GRAY + "able to place bids for."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Note: Bids automatically"));
                    lore.add((Object)((Object)ChatColor.GRAY + "increase the duration of"));
                    lore.add((Object)((Object)ChatColor.GRAY + "auctions."));
                }
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to edit!"));
                return SUtil.getStack((Object)ChatColor.WHITE + "Duration: " + (Object)ChatColor.YELLOW + SUtil.getAuctionSetupFormattedTime(escrow.getDuration()), Material.WATCH, (short)0, 1, lore);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                user.setAuctionCreationBIN(!user.isAuctionCreationBIN());
                new CreateAuctionGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                if (bin) {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Click to Auction", Material.POWERED_RAIL, (short)0, 1, (Object)ChatColor.GRAY + "With traditional auctions,", (Object)ChatColor.GRAY + "multiple buyers compete for the", (Object)ChatColor.GRAY + "item by bidding turn by turn.", " ", (Object)ChatColor.YELLOW + "Click to switch!");
                }
                return SUtil.getStack((Object)ChatColor.GREEN + "Switch to BIN", Material.GOLD_INGOT, (short)0, 1, (Object)ChatColor.GRAY + "BIN Auctions are simple.", " ", (Object)ChatColor.GRAY + "Set a price, then one player may", (Object)ChatColor.GRAY + "buy the item at that price.", " ", (Object)ChatColor.DARK_GRAY + "(BIN means Buy It Now)", " ", (Object)ChatColor.YELLOW + "Click to switch!");
            }
        });
    }
    
    @Override
    public void onBottomClick(final InventoryClickEvent e) {
        final ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (current.getType() == Material.AIR) {
            return;
        }
        if (current.getType() == Material.NETHER_STAR) {
            return;
        }
        SItem item = SItem.find(current);
        if (item == null) {
            item = SItem.convert(current);
        }
        e.setCancelled(true);
        final Player player = (Player)e.getWhoClicked();
        final User user = User.getUser(player.getUniqueId());
        if (user.getAuctionEscrow().getItem() != null) {
            return;
        }
        user.getAuctionEscrow().setItem(item);
        player.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
        new CreateAuctionGUI().open(player);
    }
}
