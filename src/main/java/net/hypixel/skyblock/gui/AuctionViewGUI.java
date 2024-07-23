package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.features.auction.AuctionBid;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.auction.AuctionItem;

public class AuctionViewGUI extends GUI
{
    private final AuctionItem item;
    private final GUI ret;
    private final long bid;
    
    public AuctionViewGUI(final AuctionItem item, final GUI ret, final long bid) {
        super((item.isBin() ? "BIN " : "") + "Auction View", 54);
        this.item = item;
        this.ret = ret;
        this.bid = bid;
        this.fill(AuctionViewGUI.BLACK_STAINED_GLASS_PANE);
    }
    
    public AuctionViewGUI(final AuctionItem item, final GUI ret) {
        this(item, ret, item.nextBid());
    }
    
    public AuctionViewGUI(final AuctionItem item) {
        this(item, null);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final boolean personal = this.item.getOwner().getUuid().equals((Object)user.getUuid());
        this.set(13, this.item.getDisplayItem(false, user.getUuid().equals((Object)this.item.getOwner().getUuid())));
        if (this.item.isBin()) {
            for (final GUIItem item : this.getBINItems(player)) {
                this.set(item);
            }
        }
        else {
            for (final GUIItem item : this.getAuctionItems(player)) {
                this.set(item);
            }
        }
        if (this.ret != null) {
            this.set(GUIClickableItem.createGUIOpenerItem(this.ret, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (short)0, (Object)ChatColor.GRAY + "To " + this.ret.getTitle()));
        }
        else {
            this.set(GUIClickableItem.getCloseItem(49));
        }
    }
    
    private List<GUIItem> getBINItems(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final boolean personal = this.item.getOwner().getUuid().equals((Object)user.getUuid());
        final List<GUIItem> items = (List<GUIItem>)new ArrayList();
        if (this.item.isExpired()) {
            final long topBid = this.item.getTopBidAmount();
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    AuctionViewGUI.this.item.claim(player);
                    player.closeInventory();
                }
                
                @Override
                public int getSlot() {
                    return 31;
                }
                
                @Override
                public ItemStack getItem() {
                    final List<String> lore = (List<String>)new ArrayList();
                    lore.add((Object)" ");
                    if (AuctionViewGUI.this.item.getBids().size() == 0) {
                        if (AuctionViewGUI.this.item.isBin()) {
                            lore.add((Object)((Object)ChatColor.GRAY + "No one has bought your item."));
                        }
                        else {
                            lore.add((Object)((Object)ChatColor.GRAY + "No one has bid on your item."));
                        }
                        lore.add((Object)((Object)ChatColor.GREEN + "You may pick it back up."));
                        lore.add((Object)" ");
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to pick up item!"));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + "Item sold to " + (Object)ChatColor.GREEN + (Object)Bukkit.getOfflinePlayer(AuctionViewGUI.this.item.getTopBidder().getUuid())));
                        lore.add((Object)((Object)ChatColor.GRAY + "for " + (Object)ChatColor.GOLD + topBid + " coin" + ((topBid != 1L) ? "s" : "") + (Object)ChatColor.GRAY + "!"));
                        lore.add((Object)" ");
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to collect coins!"));
                    }
                    return SUtil.getStack((Object)ChatColor.GOLD + "Collect Auction", (AuctionViewGUI.this.item.getBids().size() != 0) ? Material.GOLD_BLOCK : Material.GOLD_NUGGET, (short)0, 1, lore);
                }
            });
        }
        else {
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (personal) {
                        player.sendMessage((Object)ChatColor.RED + "This is your own auction!");
                        return;
                    }
                    if (AuctionViewGUI.this.item.isExpired()) {
                        player.sendMessage((Object)ChatColor.RED + "The item you are trying to bid on has already expired!");
                        player.closeInventory();
                        return;
                    }
                    final User top = AuctionViewGUI.this.item.getTopBidder();
                    if (top != null && top.getUuid().equals((Object)user.getUuid())) {
                        player.sendMessage((Object)ChatColor.GREEN + "You are already top bid!");
                        return;
                    }
                    if (user.getCoins() < AuctionViewGUI.this.bid) {
                        player.sendMessage((Object)ChatColor.RED + "You cannot afford this bid!");
                        return;
                    }
                    new ConfirmBidGUI(AuctionViewGUI.this.item, AuctionViewGUI.this.bid).open(player);
                }
                
                @Override
                public int getSlot() {
                    return (personal && AuctionViewGUI.this.item.getBids().size() == 0) ? 29 : 31;
                }
                
                @Override
                public ItemStack getItem() {
                    final List<String> lore = (List<String>)new ArrayList();
                    lore.add((Object)" ");
                    if (AuctionViewGUI.this.item.isBin()) {
                        lore.add((Object)((Object)ChatColor.GRAY + "Price: " + (Object)ChatColor.GOLD + SUtil.commaify(AuctionViewGUI.this.item.getStarter()) + " coin" + ((AuctionViewGUI.this.item.getStarter() != 1L) ? "s" : "")));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + "New bid: " + (Object)ChatColor.GOLD + SUtil.commaify(AuctionViewGUI.this.bid) + " coin" + ((AuctionViewGUI.this.bid != 1L) ? "s" : "")));
                        final AuctionBid bid = AuctionViewGUI.this.item.getBid(user);
                        if (bid != null) {
                            lore.add((Object)((Object)ChatColor.GRAY + "Your previous bid: " + (Object)ChatColor.YELLOW + SUtil.commaify(bid.getAmount()) + " coin" + ((bid.getAmount() != 1L) ? "s" : "")));
                        }
                    }
                    lore.add((Object)" ");
                    final User top = AuctionViewGUI.this.item.getTopBidder();
                    if (personal) {
                        lore.add((Object)((Object)ChatColor.GREEN + "This is your own auction!"));
                    }
                    else if (top != null && top.getUuid().equals((Object)user.getUuid())) {
                        lore.add((Object)((Object)ChatColor.GREEN + "Already top bid!"));
                    }
                    else if (user.getCoins() < AuctionViewGUI.this.bid) {
                        lore.add((Object)((Object)ChatColor.RED + "Cannot afford bid!"));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to " + (AuctionViewGUI.this.item.isBin() ? "buy" : "bid") + "!"));
                    }
                    Material icon = (user.getCoins() < AuctionViewGUI.this.bid || personal) ? Material.POTATO_ITEM : Material.GOLD_NUGGET;
                    if (top != null && top.getUuid().equals((Object)user.getUuid())) {
                        icon = Material.GOLD_BLOCK;
                    }
                    return SUtil.getStack((Object)ChatColor.GOLD + (AuctionViewGUI.this.item.isBin() ? "Buy Item Right Now" : "Submit Bid"), icon, (short)0, 1, lore);
                }
            });
        }
        if (personal && this.item.getBids().size() == 0 && !this.item.isExpired()) {
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (AuctionViewGUI.this.item.isExpired()) {
                        player.sendMessage((Object)ChatColor.RED + "The auction you are trying to cancel has already expired!");
                        player.closeInventory();
                        return;
                    }
                    if (AuctionViewGUI.this.item.getBids().size() != 0) {
                        player.sendMessage((Object)ChatColor.RED + "Someone has bid on this item and it cannot be removed!");
                        return;
                    }
                    player.closeInventory();
                    player.getInventory().addItem(new ItemStack[] { AuctionViewGUI.this.item.getItem().getStack() });
                    AuctionViewGUI.this.item.delete();
                    player.sendMessage((Object)ChatColor.GREEN + "Your auction has been successfully cancelled!");
                }
                
                @Override
                public int getSlot() {
                    return 33;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.RED + "Cancel Auction", Material.STAINED_CLAY, (short)14, 1, (Object)ChatColor.GRAY + "You may cancel auctions as", (Object)ChatColor.GRAY + "long as they have " + (Object)ChatColor.RED + "0" + (Object)ChatColor.GRAY + " bids!", " ", (Object)ChatColor.YELLOW + "Click to cancel auction!");
                }
            });
        }
        return items;
    }
    
    private List<GUIItem> getAuctionItems(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final boolean personal = this.item.getOwner().getUuid().equals((Object)user.getUuid());
        final List<GUIItem> items = (List<GUIItem>)new ArrayList();
        if (this.item.isExpired()) {
            final long topBid = this.item.getTopBidAmount();
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    AuctionViewGUI.this.item.claim(player);
                    player.closeInventory();
                }
                
                @Override
                public int getSlot() {
                    return 29;
                }
                
                @Override
                public ItemStack getItem() {
                    final List<String> lore = (List<String>)new ArrayList();
                    lore.add((Object)" ");
                    if (AuctionViewGUI.this.item.getBids().size() == 0) {
                        if (AuctionViewGUI.this.item.isBin()) {
                            lore.add((Object)((Object)ChatColor.GRAY + "No one has bought your item."));
                        }
                        else {
                            lore.add((Object)((Object)ChatColor.GRAY + "No one has bid on your item."));
                        }
                        lore.add((Object)((Object)ChatColor.GREEN + "You may pick it back up."));
                        lore.add((Object)" ");
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to pick up item!"));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + "Item sold to " + (Object)ChatColor.GREEN + (Object)Bukkit.getOfflinePlayer(AuctionViewGUI.this.item.getTopBidder().getUuid())));
                        lore.add((Object)((Object)ChatColor.GRAY + "for " + (Object)ChatColor.GOLD + topBid + " coin" + ((topBid != 1L) ? "s" : "") + (Object)ChatColor.GRAY + "!"));
                        lore.add((Object)" ");
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to collect coins!"));
                    }
                    return SUtil.getStack((Object)ChatColor.GOLD + "Collect Auction", (AuctionViewGUI.this.item.getBids().size() != 0) ? Material.GOLD_BLOCK : Material.GOLD_NUGGET, (short)0, 1, lore);
                }
            });
        }
        else {
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (personal) {
                        player.sendMessage((Object)ChatColor.RED + "This is your own auction!");
                        return;
                    }
                    if (AuctionViewGUI.this.item.isExpired()) {
                        player.sendMessage((Object)ChatColor.RED + "The item you are trying to bid on has already expired!");
                        player.closeInventory();
                        return;
                    }
                    final User top = AuctionViewGUI.this.item.getTopBidder();
                    if (top != null && top.getUuid().equals((Object)user.getUuid())) {
                        player.sendMessage((Object)ChatColor.GREEN + "You are already top bid!");
                        return;
                    }
                    if (user.getCoins() < AuctionViewGUI.this.bid) {
                        player.sendMessage((Object)ChatColor.RED + "You cannot afford this bid!");
                        return;
                    }
                    new ConfirmBidGUI(AuctionViewGUI.this.item, AuctionViewGUI.this.bid).open(player);
                }
                
                @Override
                public int getSlot() {
                    return 29;
                }
                
                @Override
                public ItemStack getItem() {
                    final List<String> lore = (List<String>)new ArrayList();
                    lore.add((Object)" ");
                    if (AuctionViewGUI.this.item.isBin()) {
                        lore.add((Object)((Object)ChatColor.GRAY + "Price: " + (Object)ChatColor.GOLD + SUtil.commaify(AuctionViewGUI.this.item.getStarter()) + " coin" + ((AuctionViewGUI.this.item.getStarter() != 1L) ? "s" : "")));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + "New bid: " + (Object)ChatColor.GOLD + SUtil.commaify(AuctionViewGUI.this.bid) + " coin" + ((AuctionViewGUI.this.bid != 1L) ? "s" : "")));
                    }
                    lore.add((Object)" ");
                    if (personal) {
                        lore.add((Object)((Object)ChatColor.GREEN + "This is your own auction!"));
                    }
                    else if (user.getCoins() < AuctionViewGUI.this.bid) {
                        lore.add((Object)((Object)ChatColor.RED + "Cannot afford purchase!"));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.YELLOW + "Click to " + (AuctionViewGUI.this.item.isBin() ? "buy" : "bid") + "!"));
                    }
                    return SUtil.getStack((Object)ChatColor.GOLD + (AuctionViewGUI.this.item.isBin() ? "Buy Item Right Now" : "Submit Bid"), (user.getCoins() < AuctionViewGUI.this.bid || personal) ? Material.POTATO_ITEM : Material.GOLD_NUGGET, (short)0, 1, lore);
                }
            });
        }
        if (personal && this.item.getBids().size() == 0 && !this.item.isExpired()) {
            items.add((Object)new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (AuctionViewGUI.this.item.isExpired()) {
                        player.sendMessage((Object)ChatColor.RED + "The auction you are trying to cancel has already expired!");
                        player.closeInventory();
                        return;
                    }
                    if (AuctionViewGUI.this.item.getBids().size() != 0) {
                        player.sendMessage((Object)ChatColor.RED + "Someone has bid on this item and it cannot be removed!");
                        return;
                    }
                    player.closeInventory();
                    player.getInventory().addItem(new ItemStack[] { AuctionViewGUI.this.item.getItem().getStack() });
                    AuctionViewGUI.this.item.delete();
                    player.sendMessage((Object)ChatColor.GREEN + "Your auction has been successfully cancelled!");
                }
                
                @Override
                public int getSlot() {
                    return 31;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.RED + "Cancel Auction", Material.STAINED_CLAY, (short)14, 1, (Object)ChatColor.GRAY + "You may cancel auctions as", (Object)ChatColor.GRAY + "long as they have " + (Object)ChatColor.RED + "0" + (Object)ChatColor.GRAY + " bids!", " ", (Object)ChatColor.YELLOW + "Click to cancel auction!");
                }
            });
        }
        if (!personal && !this.item.isExpired() && this.item.nextBid() <= user.getCoins()) {
            items.add((Object)new GUIQueryItem() {
                @Override
                public GUI onQueryFinish(final String query) {
                    if (AuctionViewGUI.this.item.isExpired()) {
                        player.sendMessage((Object)ChatColor.RED + "The auction you are trying to bid on has already expired!");
                        player.closeInventory();
                    }
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
                    if (l < AuctionViewGUI.this.item.nextBid()) {
                        player.sendMessage((Object)ChatColor.RED + "That bid is less than the minimum!");
                        return new AuctionViewGUI(AuctionViewGUI.this.item, AuctionViewGUI.this.ret, AuctionViewGUI.this.bid);
                    }
                    if (AuctionViewGUI.this.bid > user.getCoins()) {
                        player.sendMessage((Object)ChatColor.RED + "You cannot afford that bid!");
                        return new AuctionViewGUI(AuctionViewGUI.this.item, AuctionViewGUI.this.ret, AuctionViewGUI.this.bid);
                    }
                    return new AuctionViewGUI(AuctionViewGUI.this.item, AuctionViewGUI.this.ret, l);
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
                    final String display = (Object)ChatColor.GOLD + SUtil.commaify(AuctionViewGUI.this.bid) + " coin" + ((AuctionViewGUI.this.bid != 1L) ? "s" : "");
                    return SUtil.getStack((Object)ChatColor.WHITE + "Bid Amount: " + display, Material.GOLD_INGOT, (short)0, 1, (Object)ChatColor.GRAY + "You need to bid at least", display + (Object)ChatColor.GRAY + " to hold", (Object)ChatColor.GRAY + "the top bid on this", (Object)ChatColor.GRAY + "auction.", " ", (Object)ChatColor.GRAY + "The " + (Object)ChatColor.YELLOW + "top bid" + (Object)ChatColor.GRAY + " on auction", (Object)ChatColor.GRAY + "ends wins the item.", " ", (Object)ChatColor.GRAY + "If you do not win, you can", (Object)ChatColor.GRAY + "claim your bid coins back.", " ", (Object)ChatColor.YELLOW + "Click to edit amount!");
                }
            });
        }
        final List<String> lore = (List<String>)new ArrayList();
        if (this.item.getBids().size() > 0) {
            lore.add((Object)((Object)ChatColor.GRAY + "Total bids: " + (Object)ChatColor.GREEN + this.item.getBids().size() + " bid" + ((this.item.getBids().size() != 1) ? "s" : "")));
            for (int i = this.item.getBids().size() - 1; i >= 0; --i) {
                final AuctionBid bid = (AuctionBid)this.item.getBids().get(i);
                lore.add((Object)((Object)ChatColor.GRAY + "" + (Object)ChatColor.STRIKETHROUGH + "------------"));
                lore.add((Object)((Object)ChatColor.GRAY + "Bid: " + (Object)ChatColor.GOLD + bid.getAmount() + " coin" + ((bid.getAmount() != 1L) ? "s" : "")));
                lore.add((Object)((Object)ChatColor.GRAY + "By: " + (Object)ChatColor.GREEN + Bukkit.getOfflinePlayer(bid.getBidder()).getName()));
                lore.add((Object)((Object)ChatColor.AQUA + SUtil.getAuctionSetupFormattedTime(bid.timeSinceBid()).toLowerCase() + " ago"));
            }
        }
        else {
            lore.add((Object)((Object)ChatColor.GRAY + "No bids have been placed on"));
            lore.add((Object)((Object)ChatColor.GRAY + "this item yet."));
            lore.add((Object)" ");
            lore.add((Object)((Object)ChatColor.GRAY + "Be the first to bid on it!"));
        }
        items.add((Object)new GUIItem() {
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.WHITE + "Bid History", Material.MAP, (short)0, 1, lore);
            }
        });
        return items;
    }
}
