package net.hypixel.skyblock.gui;

import java.util.List;
import java.util.Collections;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.item.ItemCategory;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.AuctionSettings;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.features.auction.AuctionItem;
import java.util.Collection;
import net.hypixel.skyblock.user.User;

public class AuctionsBrowserGUI extends GUI
{
    private static final int[] INTERIOR;
    private int page;
    
    public AuctionsBrowserGUI(final int page) {
        super("Auctions Browser", 54);
        this.page = page;
    }
    
    public AuctionsBrowserGUI() {
        this(1);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final AuctionSettings settings = user.getAuctionSettings();
        final ItemCategory category = settings.getCategory();
        PaginationList<AuctionItem> result;
        try {
            result = new PaginationList<AuctionItem>((java.util.Collection<AuctionItem>)AuctionItem.search(settings).get(), 24);
        }
        catch (final InterruptedException | ExecutionException ex) {
            player.closeInventory();
            player.sendMessage((Object)ChatColor.RED + "Something went wrong while talking to the Auction House service!");
            return;
        }
        final String browsing = (Object)ChatColor.GREEN + "Currently browsing!";
        final String view = (Object)ChatColor.YELLOW + "Click to view items!";
        List<AuctionItem> items = result.getPage(this.page);
        if (items == null) {
            items = (List<AuctionItem>)new ArrayList();
        }
        if (items.size() == 0) {
            this.page = 0;
        }
        final int finalPage = this.page;
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.WEAPONS);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 0;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Weapons", Material.GOLD_SWORD, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Swords", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Bows", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Axes", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Magic Weapons", " ", (category == ItemCategory.WEAPONS) ? browsing : view);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.ARMOR);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 9;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.AQUA + "Armor", Material.DIAMOND_CHESTPLATE, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Hats", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Chestplates", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Leggings", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Boots", " ", (category == ItemCategory.ARMOR) ? browsing : view);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.ACCESSORIES);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 18;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.DARK_GREEN + "Accessories", "f36b821c1afdd5a5d14e3b3bd0a32263c8df5df5db6e1e88bf65e97b27a8530", 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Talismans", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Rings", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Orbs", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Artifacts", " ", (category == ItemCategory.ACCESSORIES) ? browsing : view);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.CONSUMABLES);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 27;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "Consumables", Material.APPLE, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Potions", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Food", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Books", " ", (category == ItemCategory.CONSUMABLES) ? browsing : view);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.BLOCKS);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 36;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.YELLOW + "Blocks", Material.COBBLESTONE, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Dirt", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Stone", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Any blocks really", " ", (category == ItemCategory.BLOCKS) ? browsing : view);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setCategory(ItemCategory.TOOLS_MISC);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 45;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.LIGHT_PURPLE + "Tools & Misc", Material.STICK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Category", " ", (Object)ChatColor.GRAY + "Examples:", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Tools", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Specials", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Magic", (Object)ChatColor.DARK_GRAY + "\u25fc " + (Object)ChatColor.GRAY + "Staff items", " ", (category == ItemCategory.TOOLS_MISC) ? browsing : view);
            }
        });
        this.border(category.getStainedGlassPane(), 1, 53);
        if (this.page > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (e.isRightClick()) {
                        new AuctionsBrowserGUI(0).open(player);
                    }
                    else {
                        new AuctionsBrowserGUI(finalPage - 1).open(player);
                    }
                }
                
                @Override
                public int getSlot() {
                    return 46;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Previous Page", Material.ARROW, (short)0, 1, (Object)ChatColor.GRAY + "(" + finalPage + "/" + result.getPageCount() + ")", " ", (Object)ChatColor.AQUA + "Right-Click to skip!", (Object)ChatColor.YELLOW + "Click to turn page!");
                }
            });
        }
        if (this.page != result.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (e.isRightClick()) {
                        new AuctionsBrowserGUI(result.getPageCount()).open(player);
                    }
                    else {
                        new AuctionsBrowserGUI(finalPage + 1).open(player);
                    }
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Next Page", Material.ARROW, (short)0, 1, (Object)ChatColor.GRAY + "(" + finalPage + "/" + result.getPageCount() + ")", " ", (Object)ChatColor.AQUA + "Right-Click to skip!", (Object)ChatColor.YELLOW + "Click to turn page!");
                }
            });
        }
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                settings.setQuery(null);
                settings.setSort(AuctionSettings.Sort.HIGHEST_BID);
                settings.setTier(null);
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 47;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Reset Settings", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Clears your auction", (Object)ChatColor.GRAY + "browsing settings except", (Object)ChatColor.GRAY + "BIN Filter.", " ", (Object)ChatColor.YELLOW + "Click to clear!");
            }
        });
        this.set(new GUIQueryItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public GUI onQueryFinish(final String query) {
                settings.setQuery(query);
                return new AuctionsBrowserGUI();
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Search", Material.SIGN, (short)0, 1, (Object)ChatColor.GRAY + "Find items by name, type,", (Object)ChatColor.GRAY + "lore or enchants.", " ", (Object)ChatColor.YELLOW + "Click to search!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (e.isRightClick()) {
                    settings.setSort(settings.getSort().previous());
                }
                else {
                    settings.setSort(settings.getSort().next());
                }
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 50;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
                for (final AuctionSettings.Sort s : AuctionSettings.Sort.values()) {
                    if (settings.getSort() == s) {
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
                final Rarity tier = settings.getTier();
                if (e.isRightClick()) {
                    if (tier == null) {
                        settings.setTier(Rarity.values()[Rarity.values().length - 1]);
                    }
                    else if (tier.ordinal() == 0) {
                        settings.setTier(null);
                    }
                    else {
                        settings.setTier(tier.downgrade());
                    }
                }
                else if (tier == null) {
                    settings.setTier(Rarity.values()[0]);
                }
                else if (tier.ordinal() == Rarity.values().length - 1) {
                    settings.setTier(null);
                }
                else {
                    settings.setTier(tier.upgrade());
                }
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 51;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
                if (settings.getTier() == null) {
                    lore.add((Object)((Object)ChatColor.DARK_GRAY + "\u25b6 No filter"));
                }
                else {
                    lore.add((Object)((Object)ChatColor.GRAY + "No filter"));
                }
                for (final Rarity rarity : Rarity.values()) {
                    final String normal = SUtil.toNormalCase(rarity.name());
                    if (settings.getTier() == rarity) {
                        lore.add((Object)((Object)rarity.getColor() + "\u25b6 " + normal));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + normal));
                    }
                }
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.AQUA + "Right-Click to go backwards!"));
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to switch rarity!"));
                return SUtil.getStack((Object)ChatColor.GREEN + "Item Tier", Material.EYE_OF_ENDER, (short)0, 1, lore);
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (e.isRightClick()) {
                    settings.setType(settings.getType().previous());
                }
                else {
                    settings.setType(settings.getType().next());
                }
                new AuctionsBrowserGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 52;
            }
            
            @Override
            public ItemStack getItem() {
                final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
                for (final AuctionSettings.Type t : AuctionSettings.Type.values()) {
                    if (settings.getType() == t) {
                        lore.add((Object)((Object)ChatColor.DARK_AQUA + "\u25b6 " + t.getDisplay()));
                    }
                    else {
                        lore.add((Object)((Object)ChatColor.GRAY + t.getDisplay()));
                    }
                }
                lore.add((Object)" ");
                lore.add((Object)((Object)ChatColor.AQUA + "Right-Click to go backwards!"));
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to switch filter!"));
                return SUtil.getStack((Object)ChatColor.GREEN + "BIN Filter", Material.GOLD_BLOCK, (short)0, 1, lore);
            }
        });
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.AUCTION_HOUSE, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (Object)ChatColor.GRAY + "To Auction House"));
        for (int i = 0; i < items.size(); ++i) {
            final int slot = AuctionsBrowserGUI.INTERIOR[i];
            final AuctionItem item = (AuctionItem)items.get(i);
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new AuctionViewGUI(item, AuctionsBrowserGUI.this).open(player);
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
                
                @Override
                public ItemStack getItem() {
                    return item.getDisplayItem(true, user.getUuid().equals((Object)item.getOwner().getUuid()));
                }
            });
        }
    }
    
    static {
        INTERIOR = new int[] { 11, 12, 13, 14, 15, 16, 20, 21, 22, 23, 24, 25, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42, 43 };
    }
}
