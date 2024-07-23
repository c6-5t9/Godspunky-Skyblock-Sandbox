package net.hypixel.skyblock.gui;

import java.util.List;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.util.PaginationList;

public class AdminItemBrowser extends GUI
{
    private static final int[] INTERIOR;
    
    public AdminItemBrowser(final String query, int page) {
        super("Admin Item Browser", 54);
        this.border(AdminItemBrowser.BLACK_STAINED_GLASS_PANE);
        final PaginationList<SMaterial> pagedMaterials = new PaginationList<SMaterial>(28);
        for (final SMaterial mat : SMaterial.values()) {
            if (mat.name().toLowerCase().contains((CharSequence)"dwarven_") || mat.name().toLowerCase().contains((CharSequence)"hidden_") || mat.name().toLowerCase().contains((CharSequence)"bzb") || mat.name().toLowerCase().contains((CharSequence)"god_pot")) {
                pagedMaterials.add((Object)mat);
            }
        }
        if (!query.isEmpty()) {
            pagedMaterials.removeIf(material -> !material.name().toLowerCase().contains((CharSequence)query.replaceAll(" ", "_").toLowerCase()));
        }
        if (pagedMaterials.isEmpty()) {
            page = 0;
        }
        this.title = "Admin Item Browser (" + page + "/" + pagedMaterials.getPageCount() + ")";
        if (page > 1) {
            final int finalPage = page;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new AdminItemBrowser(finalPage - 1).open((Player)e.getWhoClicked());
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
            final int finalPage2 = page;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new AdminItemBrowser(finalPage2 + 1).open((Player)e.getWhoClicked());
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
        this.set(new GUIQueryItem() {
            @Override
            public GUI onQueryFinish(final String query) {
                return new AdminItemBrowser(query);
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.createNamedItemStack(Material.SIGN, (Object)ChatColor.GREEN + "Search");
            }
        });
        this.set(GUIClickableItem.getCloseItem(50));
        final List<SMaterial> p = pagedMaterials.getPage(page);
        if (p != null) {
            for (int i = 0; i < p.size(); ++i) {
                final int slot = AdminItemBrowser.INTERIOR[i];
                final SItem sItem = SItem.of((SMaterial)p.get(i), ((SMaterial)p.get(i)).getData());
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        final Player player = (Player)e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
                        player.sendMessage((Object)ChatColor.GOLD + sItem.getType().getDisplayName(sItem.getVariant()) + (Object)ChatColor.GREEN + " has been added to your inventory.");
                        player.getInventory().addItem(new ItemStack[] { SItem.of(sItem.getType(), sItem.getVariant()).getStack() });
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
    }
    
    public AdminItemBrowser(final String query) {
        this(query, 1);
    }
    
    public AdminItemBrowser(final int page) {
        this("", page);
    }
    
    public AdminItemBrowser() {
        this(1);
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
