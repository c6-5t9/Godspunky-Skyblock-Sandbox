package net.hypixel.skyblock.gui;

import java.util.Iterator;
import net.hypixel.skyblock.features.collection.ItemCollectionRewards;
import java.util.List;
import net.hypixel.skyblock.features.collection.ItemCollectionReward;
import java.util.ArrayList;
import java.util.Arrays;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Collection;
import net.hypixel.skyblock.features.collection.ItemCollection;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.collection.ItemCollectionCategory;

public class CategoryCollectionGUI extends GUI
{
    private static final int[] INTERIOR;
    private final ItemCollectionCategory category;
    private int page;
    
    public CategoryCollectionGUI(final ItemCollectionCategory category, final int page) {
        super(category.getName() + " Collection", 54);
        this.category = category;
        this.page = page;
    }
    
    public CategoryCollectionGUI(final ItemCollectionCategory category) {
        this(category, 1);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.border(CategoryCollectionGUI.BLACK_STAINED_GLASS_PANE);
        final PaginationList<ItemCollection> paged = new PaginationList<ItemCollection>(28);
        paged.addAll((Collection)ItemCollection.getCategoryCollections(this.category));
        if (paged.size() == 0) {
            this.page = 0;
        }
        final int finalPage = this.page;
        if (this.page > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new CategoryCollectionGUI(CategoryCollectionGUI.this.category, finalPage - 1).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 45;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "<-");
                }
            });
        }
        if (this.page != paged.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new CategoryCollectionGUI(CategoryCollectionGUI.this.category, finalPage + 1).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "->");
                }
            });
        }
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.COLLECTION_MENU, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (Object)ChatColor.GRAY + "To Collection"));
        this.set(GUIClickableItem.getCloseItem(49));
        final List<ItemCollection> p = paged.getPage(this.page);
        if (p == null) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            final int slot = CategoryCollectionGUI.INTERIOR[i];
            final ItemCollection collection = (ItemCollection)p.get(i);
            final int amount = user.getCollection(collection);
            if (amount != 0) {
                final List<String> lore = (List<String>)new ArrayList((Collection)Arrays.asList((Object[])new String[] { (Object)ChatColor.GRAY + "View all your " + collection.getName() + " Collection", (Object)ChatColor.GRAY + "progress and rewards!", " " }));
                final int tier = collection.getTier(amount);
                final int next = tier + 1;
                final int nextReq = collection.getRequirementForTier(next);
                if (nextReq != -1) {
                    final String numeral = SUtil.toRomanNumeral(next);
                    lore.add((Object)SUtil.createProgressText("Progress to " + collection.getName() + " " + numeral, amount, nextReq));
                    lore.add((Object)SUtil.createLineProgressBar(20, ChatColor.DARK_GREEN, amount, nextReq));
                    lore.add((Object)" ");
                    final ItemCollectionRewards rewards = collection.getRewardsFor(next);
                    if (rewards != null && rewards.size() != 0) {
                        lore.add((Object)((Object)ChatColor.GRAY + collection.getName() + " " + numeral + " Reward" + ((rewards.size() != 1) ? "s" : "")));
                        for (final ItemCollectionReward reward : rewards) {
                            lore.add((Object)((Object)ChatColor.GRAY + " " + reward.toRewardString()));
                        }
                        lore.add((Object)" ");
                    }
                }
                lore.add((Object)((Object)ChatColor.YELLOW + "Click to view!"));
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        new ItemCollectionGUI(collection).open(player);
                    }
                    
                    @Override
                    public int getSlot() {
                        return slot;
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return SUtil.getStack((Object)ChatColor.YELLOW + collection.getName() + ((tier != 0) ? (" " + SUtil.toRomanNumeral(tier)) : ""), collection.getMaterial().getCraftMaterial(), collection.getData(), 1, lore);
                    }
                });
            }
            else {
                this.set(slot, SUtil.getStack((Object)ChatColor.RED + collection.getName(), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Find this item to add it to your", (Object)ChatColor.GRAY + "collection and unlock collection", (Object)ChatColor.GRAY + "rewards!"));
            }
        }
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
