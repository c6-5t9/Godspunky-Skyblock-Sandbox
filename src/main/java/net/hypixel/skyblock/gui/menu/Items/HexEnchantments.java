package net.hypixel.skyblock.gui.menu.Items;

import org.bukkit.event.inventory.InventoryCloseEvent;
import net.hypixel.skyblock.util.Sputnik;
import java.util.Iterator;
import org.bukkit.Sound;
import net.hypixel.skyblock.gui.GUIItem;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.gui.GUIClickableItem;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.item.SMaterial;
import java.util.Objects;
import net.hypixel.skyblock.item.MaterialStatistics;
import net.hypixel.skyblock.util.PaginationList;
import java.util.ArrayList;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.gui.GUI;

public class HexEnchantments extends GUI
{
    private static final int[] INTERIOR;
    public boolean forceclose;
    SItem upgradeableItem;
    List<EnchantmentType> selected;
    List<EnchantmentType> exists;
    
    public HexEnchantments(final SItem item) {
        this(item, 1);
    }
    
    public HexEnchantments(final SItem item, final int page) {
        super("The Hex -> Enchantments", 54);
        this.forceclose = false;
        this.selected = (List<EnchantmentType>)new ArrayList();
        this.exists = (List<EnchantmentType>)new ArrayList();
        this.fill(HexEnchantments.BLACK_STAINED_GLASS_PANE);
        this.upgradeableItem = item;
        final PaginationList<SItem> pagedEnchantBooks = new PaginationList<SItem>(15);
        for (final EnchantmentType type : EnchantmentType.ENCHANTMENT_TYPE_CACHE.values()) {
            if (type.isUltimate()) {
                continue;
            }
            if (!type.getCompatibleTypes().contains((Object)((MaterialStatistics)Objects.requireNonNull((Object)item.getType().getStatistics())).getSpecificType())) {
                continue;
            }
            final SItem bookItem = SItem.of(SMaterial.ENCHANTED_BOOK);
            bookItem.addEnchantment(type, type.maxLvl);
            pagedEnchantBooks.add((Object)bookItem);
        }
        for (final Enchantment enchantment : item.getEnchantments()) {
            this.exists.add((Object)enchantment.getType());
        }
        final List<SItem> items = pagedEnchantBooks.getPage(page);
        if (items == null) {
            return;
        }
        if (page > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    HexEnchantments.this.forceclose = true;
                    new HexEnchantments(item, page - 1).open((Player)e.getWhoClicked());
                    HexEnchantments.this.upgradeableItem = null;
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
        if (page != pagedEnchantBooks.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    HexEnchantments.this.forceclose = true;
                    new HexEnchantments(item, page + 1).open((Player)e.getWhoClicked());
                    HexEnchantments.this.upgradeableItem = null;
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
            final int slot = HexEnchantments.INTERIOR[i];
            final int finalI = i;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    HexEnchantments.this.forceclose = true;
                    final Player player = (Player)e.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10.0f, 2.0f);
                    for (final Enchantment enchantment : ((SItem)items.get(finalI)).getEnchantments()) {
                        final EnchantmentType type = enchantment.getType();
                        if (HexEnchantments.this.exists.contains((Object)type)) {
                            HexEnchantments.this.exists.remove((Object)type);
                            item.removeEnchantment(type);
                            player.sendMessage((Object)ChatColor.RED + "You removed the enchantment: " + type.getName() + " from your item!");
                            return;
                        }
                        if (HexEnchantments.this.selected.contains((Object)type)) {
                            player.sendMessage((Object)ChatColor.RED + "You removed the Enchantment " + type.getName() + " from your selection!");
                            HexEnchantments.this.selected.remove((Object)type);
                            return;
                        }
                        if (!HexEnchantments.this.selected.contains((Object)type)) {
                            if (item.hasEnchantment(EnchantmentType.ONE_FOR_ALL)) {
                                player.sendMessage((Object)ChatColor.GREEN + "You added the Enchantment " + type.getName() + " to your selection but One For All was removed!");
                                HexEnchantments.this.selected.add((Object)type);
                                item.removeEnchantment(EnchantmentType.ONE_FOR_ALL);
                            }
                            else {
                                player.sendMessage((Object)ChatColor.GREEN + "You added the Enchantment " + type.getName() + " to your selection!");
                                HexEnchantments.this.selected.add((Object)type);
                            }
                        }
                    }
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
                for (final EnchantmentType type : HexEnchantments.this.selected) {
                    item.addEnchantment(type, type.maxLvl);
                }
                HexEnchantments.this.forceclose = true;
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lSUCCESS! &r&dYou enchanted your " + item.getFullName() + "!"));
                final Player p = (Player)e.getWhoClicked();
                p.playSound(p.getLocation(), Sound.ANVIL_USE, 10.0f, 1.0f);
                new HexGUI(p.getPlayer(), item).open(p);
                HexEnchantments.this.upgradeableItem = null;
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aEnchant Item"), Material.ENCHANTMENT_TABLE, (short)0, 1, Sputnik.trans("&7Add and remove enchantments from"), Sputnik.trans("&7the item in the slot above!"));
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
