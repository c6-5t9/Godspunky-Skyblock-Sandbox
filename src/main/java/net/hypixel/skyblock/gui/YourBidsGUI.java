package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.auction.AuctionItem;
import java.util.List;

public class YourBidsGUI extends GUI
{
    private static final int[] INTERIOR;
    private List<AuctionItem> items;
    
    public YourBidsGUI() {
        super("Your Bids", 27);
        this.border(YourBidsGUI.BLACK_STAINED_GLASS_PANE);
    }
    
    @Override
    public void early(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        this.items = user.getBids();
        this.size = Math.max(54, Math.ceil(this.items.size() / 7.0).intValue() * 9 + 18);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        if (this.items == null) {
            return;
        }
        int ended = 0;
        for (final AuctionItem item : this.items) {
            if (item.isExpired()) {
                ++ended;
            }
        }
        if (ended != 0) {
            final int finalEnded = ended;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    for (final AuctionItem item : YourBidsGUI.this.items) {
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
                    lore.add((Object)((Object)ChatColor.GRAY + "claim items/reclaim bids."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to claim!"));
                    return SUtil.getStack((Object)ChatColor.GREEN + "Claim All", Material.CAULDRON_ITEM, (short)0, 1, lore);
                }
            });
        }
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.AUCTION_HOUSE, player, (Object)ChatColor.GREEN + "Go Back", 22, Material.ARROW, (Object)ChatColor.GRAY + "To Auction House"));
        for (int i = 0; i < this.items.size(); ++i) {
            final AuctionItem item = (AuctionItem)this.items.get(i);
            final int slot = YourBidsGUI.INTERIOR[i];
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new AuctionViewGUI(item, YourBidsGUI.this).open(player);
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
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
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
