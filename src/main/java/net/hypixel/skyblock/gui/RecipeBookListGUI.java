package net.hypixel.skyblock.gui;

import java.util.List;
import java.util.Iterator;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.ShapedRecipe;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.PaginationList;
import org.bukkit.entity.Player;

public class RecipeBookListGUI extends GUI
{
    private static final int[] INTERIOR;
    
    public RecipeBookListGUI(final String query, int page, final Player player) {
        super("Recipe Book", 54);
        this.border(RecipeBookListGUI.BLACK_STAINED_GLASS_PANE);
        if (player == null) {
            return;
        }
        final PaginationList<SItem> pagedMaterials = new PaginationList<SItem>(28);
        for (final ShapedRecipe sr : ShapedRecipe.CACHED_RECIPES) {
            final String lc = sr.getResult().getType().toString().toLowerCase();
            if (sr.isUnlockedForPlayer(User.getUser(player.getUniqueId())) && !sr.isVanilla()) {
                pagedMaterials.add((Object)sr.getResult());
            }
        }
        if (pagedMaterials.isEmpty()) {
            page = 0;
        }
        this.title = "Recipe Book (" + page + "/" + pagedMaterials.getPageCount() + ")";
        final int finalPage;
        if ((finalPage = page) > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new RecipeBookListGUI(finalPage - 1).open((Player)e.getWhoClicked());
                    ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.NOTE_PIANO, 1.0f, 1.0f);
                }
                
                @Override
                public int getSlot() {
                    return 45;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Pervious Page");
                }
            });
        }
        if (page != pagedMaterials.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new RecipeBookListGUI(finalPage + 1).open((Player)e.getWhoClicked());
                    ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.NOTE_PIANO, 1.0f, 1.0f);
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Next Page");
                }
            });
        }
        this.set(GUIClickableItem.getCloseItem(49));
        final List<SItem> p = pagedMaterials.getPage(page);
        if (p == null) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            final int slot = RecipeBookListGUI.INTERIOR[i];
            final SItem sItem = (SItem)p.get(i);
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    final Player player = (Player)e.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
                    new RecipeBookGUI(sItem).open(player);
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
                
                @Override
                public ItemStack getItem() {
                    return sItem.getStack();
                }
            });
        }
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (Object)ChatColor.GRAY + "To SkyBlock Menu"));
    }
    
    public RecipeBookListGUI(final String query, final Player player) {
        this(query, 1, player);
    }
    
    public RecipeBookListGUI(final int page) {
        this("", page, null);
    }
    
    public RecipeBookListGUI() {
        this("", 1, null);
    }
    
    public RecipeBookListGUI(final Player player) {
        this("", 1, player);
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
