package net.hypixel.skyblock.gui.menu.Items;

import org.bukkit.event.inventory.InventoryCloseEvent;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import java.util.List;
import net.hypixel.skyblock.gui.GUIItem;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.gui.GUIClickableItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.features.reforge.Reforge;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.gui.GUI;

public class HexReforgesGUI extends GUI
{
    SItem upgradeableItem;
    Reforge selected;
    public boolean forceclose;
    private static final int[] INTERIOR;
    
    public HexReforgesGUI(final SItem item) {
        this(item, 1);
    }
    
    public HexReforgesGUI(final SItem item, final int page) {
        super("The Hex -> Reforges", 54);
        this.forceclose = false;
        this.fill(HexReforgesGUI.BLACK_STAINED_GLASS_PANE);
        final int finalPage = page;
        final PaginationList<SItem> pagedReforgeStones = new PaginationList<SItem>(15);
        for (final ReforgeType type : ReforgeType.values()) {
            if (type.getReforge().getCompatibleTypes().contains((Object)item.getType().getStatistics().getType()) && type.isAccessible()) {
                if (type.getReforge().getSpecificTypes().contains((Object)item.getType().getStatistics().getSpecificType())) {
                    final SItem stone = SItem.of(SMaterial.REFORGE_STONE);
                    stone.setReforge(type.getReforge());
                    stone.setDisplayName("Reforge");
                    pagedReforgeStones.add((Object)stone);
                }
            }
        }
        final List<SItem> items = pagedReforgeStones.getPage(page);
        if (items == null) {
            return;
        }
        if (page > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new HexReforgesGUI(item, finalPage - 1).open((Player)e.getWhoClicked());
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
        if (page != pagedReforgeStones.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new HexReforgesGUI(item, finalPage + 1).open((Player)e.getWhoClicked());
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
        for (int i = 0; i < items.size(); ++i) {
            final int slot = HexReforgesGUI.INTERIOR[i];
            final int finalI = i;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    final Player player = (Player)e.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10.0f, 2.0f);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou selected the reforge " + SItem.find(e.getCurrentItem()).getReforge().getName() + " to be applied on your item!"));
                    HexReforgesGUI.this.selected = SItem.find(e.getCurrentItem()).getReforge();
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
                
                @Override
                public ItemStack getItem() {
                    return ((SItem)items.get(finalI)).getStack();
                }
            });
        }
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(19, item.getStack());
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (HexReforgesGUI.this.selected == null) {
                    e.getWhoClicked().getInventory().setItem(e.getWhoClicked().getInventory().firstEmpty(), item.getStack());
                    e.getWhoClicked().closeInventory();
                    return;
                }
                final Player p = (Player)e.getWhoClicked();
                p.playSound(p.getLocation(), Sound.ANVIL_USE, 10.0f, 1.0f);
                HexReforgesGUI.this.forceclose = true;
                item.setReforge(HexReforgesGUI.this.selected);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou applied the reforge " + HexReforgesGUI.this.selected.getName() + " to your " + item.getFullName() + "!"));
                new HexGUI(p.getPlayer(), item).open(p);
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aApply Reforges"), Material.ANVIL, (short)0, 1, Sputnik.trans3("&7Apply &aReforges&7 to your item", "&7with &aReforge Stones&7 or by", "&7rolling a &brandom&7 reforge."));
            }
        });
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        if (!this.forceclose && this.upgradeableItem != null) {
            e.getPlayer().getInventory().addItem(new ItemStack[] { this.upgradeableItem.getStack() });
        }
    }
    
    static {
        INTERIOR = new int[] { 12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 30, 31, 32, 33, 34 };
    }
}
