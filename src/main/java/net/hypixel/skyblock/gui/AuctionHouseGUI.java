package net.hypixel.skyblock.gui;

import java.util.Iterator;
import net.hypixel.skyblock.features.auction.AuctionItem;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;

public class AuctionHouseGUI extends GUI
{
    public AuctionHouseGUI() {
        super("Auction House", 36);
        this.fill(AuctionHouseGUI.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(31));
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Auctions Browser", Material.GOLD_BLOCK, (short)0, 1, (Object)ChatColor.GRAY + "Find items for sale by", (Object)ChatColor.GRAY + "players across SkySim!", " ", (Object)ChatColor.GRAY + "Items offered here are for", (Object)ChatColor.GOLD + "auction" + (Object)ChatColor.GRAY + ", meaning you", (Object)ChatColor.GRAY + "have to place the top bid", (Object)ChatColor.GRAY + "to acquire them!", " ", (Object)ChatColor.YELLOW + "Click to browse!");
            }
        });
        final List<AuctionItem> bids = user.getBids();
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (bids.size() == 0) {
                    return;
                }
                new YourBidsGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                final ArrayList<String> lore = (ArrayList<String>)new ArrayList();
                final long held = bids.stream().filter(item -> item.getTopBidder().getUuid().equals((Object)user.getUuid())).count();
                final ChatColor chatColor;
                final ChatColor color = chatColor = ((bids.size() == held) ? ChatColor.GREEN : ChatColor.RED);
                if (bids.size() == 0) {
                    lore.add((Object)((Object)ChatColor.GRAY + "You don't have any"));
                    lore.add((Object)((Object)ChatColor.GRAY + "outstanding bids."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Use the " + (Object)ChatColor.GOLD + "Auctions Browser"));
                    lore.add((Object)((Object)ChatColor.GRAY + "to find some cool items."));
                }
                else if (bids.size() == 1 && ((AuctionItem)bids.get(0)).getTopBidder().getUuid().equals((Object)user.getUuid())) {
                    lore.add((Object)((Object)ChatColor.GRAY + "You have the " + (Object)ChatColor.GREEN + "top bid" + (Object)ChatColor.GRAY + " on"));
                    lore.add((Object)((Object)ChatColor.GRAY + "a pending auction."));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "You placed " + (Object)ChatColor.GREEN + bids.size() + " bid" + ((bids.size() != 1) ? "s" : "") + (Object)ChatColor.GRAY + " on"));
                    lore.add((Object)((Object)ChatColor.GRAY + "pending auctions."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "You hold the top bid on"));
                    lore.add((Object)((Object)ChatColor.GRAY + "(" + (Object)color + held + (Object)ChatColor.GRAY + "/" + (Object)color + bids.size() + (Object)ChatColor.GRAY + ") of these"));
                    lore.add((Object)((Object)ChatColor.GRAY + "auctions."));
                }
                for (final AuctionItem item2 : bids) {
                    if (item2.getTopBidder().getUuid().equals((Object)user.getUuid())) {
                        if (!item2.isExpired()) {
                            continue;
                        }
                        lore.add((Object)" ");
                        lore.add((Object)((Object)ChatColor.AQUA + "* You have items/coins to pickup!"));
                        break;
                    }
                }
                if (bids.size() > 0) {
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to view!"));
                }
                return SUtil.getStack((Object)((bids.size() == 0) ? ChatColor.YELLOW : ChatColor.GREEN) + "View Bids", Material.GOLDEN_CARROT, (short)0, 1, (List<String>)lore);
            }
        });
        final List<AuctionItem> auctions = user.getAuctions();
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (auctions.size() == 0) {
                    new CreateAuctionGUI().open(player);
                }
                else {
                    new ManageAuctionsGUI().open(player);
                }
            }
            
            @Override
            public int getSlot() {
                return 15;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList();
                if (auctions.size() == 0) {
                    lore.add((Object)((Object)ChatColor.GRAY + "Set your own items on"));
                    lore.add((Object)((Object)ChatColor.GRAY + "auction for other players"));
                    lore.add((Object)((Object)ChatColor.GRAY + "to purchase."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to become rich!"));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "You own " + (Object)ChatColor.YELLOW + auctions.size() + " auction" + ((auctions.size() != 1) ? "s" : "") + (Object)ChatColor.GRAY + " in"));
                    lore.add((Object)((Object)ChatColor.GRAY + "progress or which recently"));
                    lore.add((Object)((Object)ChatColor.GRAY + "ended."));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.GRAY + "Players can find your auctions"));
                    lore.add((Object)((Object)ChatColor.GRAY + "using the " + (Object)ChatColor.GOLD + "Auctions Browser"));
                    lore.add((Object)((Object)ChatColor.GRAY + "or typing " + (Object)ChatColor.GREEN + "/ah " + player.getName() + (Object)ChatColor.GRAY + "!"));
                    lore.add((Object)" ");
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to manage!"));
                }
                return SUtil.getStack((Object)ChatColor.GREEN + ((auctions.size() == 0) ? "Create Auction" : "Manage Auctions"), Material.GOLD_BARDING, (short)0, 1, lore);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 32;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Auction Stats", Material.EMPTY_MAP, (short)0, 1, (Object)ChatColor.GRAY + "View various statistics", (Object)ChatColor.GRAY + "about you and the Auction", (Object)ChatColor.GRAY + "House.", " ", (Object)ChatColor.RED + "Coming soon!");
            }
        });
    }
}
