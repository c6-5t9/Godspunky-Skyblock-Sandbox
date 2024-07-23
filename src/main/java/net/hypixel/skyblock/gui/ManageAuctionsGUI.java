package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.features.auction.AuctionBid;
import java.util.stream.Stream;
import java.util.Collection;
import java.util.Collections;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.auction.AuctionItem;
import java.util.stream.Collectors;
import java.util.List;
import net.hypixel.skyblock.user.User;

public class ManageAuctionsGUI extends GUI
{
    private static final int[] INTERIOR;
    private final Sort sort;
    
    public ManageAuctionsGUI(final Sort sort) {
        super("Manage Auctions", 27);
        this.sort = sort;
        this.border(ManageAuctionsGUI.BLACK_STAINED_GLASS_PANE);
    }
    
    public ManageAuctionsGUI() {
        this(Sort.RECENTLY_UPDATED);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        Stream<AuctionItem> stream = (Stream<AuctionItem>)user.getAuctions().stream();
        switch (this.sort) {
            case RECENTLY_UPDATED: {
                stream = (Stream<AuctionItem>)stream.sorted((i1, i2) -> {
                    final AuctionBid b1 = i1.getRecentBid();
                    final AuctionBid b2 = i2.getRecentBid();
                    if (b1 == null) {
                        return 1;
                    }
                    if (b2 == null) {
                        return -1;
                    }
                    final long t1 = b1.getTimestamp();
                    final long t2 = b2.getTimestamp();
                    return Long.compare(t2, t1);
                });
                break;
            }
            case HIGHEST_BID: {
                stream = (Stream<AuctionItem>)stream.sorted((i1, i2) -> {
                    final AuctionBid b3 = i1.getTopBid();
                    final AuctionBid b4 = i2.getTopBid();
                    if (b3 == null) {
                        return 1;
                    }
                    if (b4 == null) {
                        return -1;
                    }
                    return Long.compare(b3.getAmount(), b4.getAmount());
                });
                break;
            }
            case MOST_BIDS: {
                stream = (Stream<AuctionItem>)stream.sorted((i1, i2) -> Integer.compare(i2.getBids().size(), i1.getBids().size()));
                break;
            }
        }
        final List<AuctionItem> items = (List<AuctionItem>)stream.collect(Collectors.toList());
        int ended = 0;
        for (final AuctionItem item : items) {
            if (item.isExpired()) {
                ++ended;
            }
        }
        if (ended != 0) {
            final int finalEnded = ended;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    for (final AuctionItem item : items) {
                        if (item.isExpired()) {
                            item.claim(player);
                        }
                    }
                    player.closeInventory();
                }
                
                @Override
                public int getSlot() {
                    return 21;
                }
                
                @Override
                public ItemStack getItem() {
                    final List<String> lore = (List<String>)new ArrayList();
                    lore.add((Object)((Object)ChatColor.DARK_GRAY + "Ended Auctions"));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "You got " + (Object)ChatColor.GREEN + finalEnded + " item" + ((finalEnded != 1) ? "s" : "") + (Object)ChatColor.GRAY + " to"));
                    lore.add((Object)((Object)ChatColor.GRAY + "collect sales/reclaim items."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to claim!"));
                    return SUtil.getStack((Object)ChatColor.GREEN + "Claim All", Material.CAULDRON_ITEM, (short)0, 1, lore);
                }
            });
        }
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.AUCTION_HOUSE, player, (Object)ChatColor.GREEN + "Go Back", 22, Material.ARROW, (Object)ChatColor.GRAY + "To Auction House"));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (e.isRightClick()) {
                    new ManageAuctionsGUI(ManageAuctionsGUI.this.sort.previous()).open(player);
                }
                else {
                    new ManageAuctionsGUI(ManageAuctionsGUI.this.sort.next()).open(player);
                }
            }
            
            @Override
            public int getSlot() {
                return 23;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
                for (final Sort s : Sort.values()) {
                    if (ManageAuctionsGUI.this.sort == s) {
                        lore.add((Object)((Object)ChatColor.AQUA + "\u25b6 " + s.getDisplay()));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + s.getDisplay()));
                    }
                }
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.AQUA + "Right-Click to go backwards!"));
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to switch sort!"));
                return SUtil.getStack((Object)ChatColor.GREEN + "Sort", Material.HOPPER, (short)0, 1, lore);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (items.size() >= 7) {
                    player.sendMessage((Object)ChatColor.RED + "You cannot create any more auctions!");
                    return;
                }
                new CreateAuctionGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 24;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Create Auction", Material.GOLD_BARDING, (short)0, 1, (Object)ChatColor.GRAY + "Set your own items on", (Object)ChatColor.GRAY + "auction for other players", (Object)ChatColor.GRAY + "to purchase.", " ", (Object)ChatColor.YELLOW + "Click to become rich!");
            }
        });
        for (int i = 0; i < items.size(); ++i) {
            final AuctionItem item = (AuctionItem)items.get(i);
            final int slot = ManageAuctionsGUI.INTERIOR[i];
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new AuctionViewGUI(item, ManageAuctionsGUI.this).open(player);
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
                
                @Override
                public ItemStack getItem() {
                    return item.getDisplayItem(true, true);
                }
            });
        }
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16 };
    }
    
    private enum Sort
    {
        RECENTLY_UPDATED("Recently Updated"), 
        HIGHEST_BID("Highest Bid"), 
        MOST_BIDS("Most Bids");
        
        private final String display;
        
        private Sort(final String display) {
            this.display = display;
        }
        
        public String getDisplay() {
            return this.display;
        }
        
        public Sort previous() {
            final int prev = this.ordinal() - 1;
            if (prev < 0) {
                return values()[values().length - 1];
            }
            return values()[prev];
        }
        
        public Sort next() {
            final int nex = this.ordinal() + 1;
            if (nex > values().length - 1) {
                return values()[0];
            }
            return values()[nex];
        }
    }
}
