package net.hypixel.skyblock.gui;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import java.io.IOException;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.Material;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public abstract class GUI
{
    public static final ItemStack BLACK_STAINED_GLASS_PANE;
    public static final ItemStack RED_STAINED_GLASS_PANE;
    public static final ItemStack LIME_STAINED_GLASS_PANE;
    public static final ItemStack GRAY_STAINED_GLASS_PANE;
    public static final Map<UUID, GUI> GUI_MAP;
    protected String title;
    protected int size;
    protected List<GUIItem> items;
    
    public GUI(final String title, final int size) {
        this.title = title;
        this.size = size;
        this.items = (List<GUIItem>)new ArrayList();
    }
    
    public GUI(final String title) {
        this(title, 27);
    }
    
    public void set(final GUIItem item) {
        this.items.removeIf(i -> i.getSlot() == item.getSlot());
        this.items.add((Object)item);
    }
    
    public void set(final int slot, final ItemStack stack, final boolean pickup) {
        if (stack == null) {
            this.items.removeIf(i -> i.getSlot() == slot);
            return;
        }
        this.set(new GUIItem() {
            @Override
            public int getSlot() {
                return slot;
            }
            
            @Override
            public ItemStack getItem() {
                return stack;
            }
            
            @Override
            public boolean canPickup() {
                return pickup;
            }
        });
    }
    
    public void set(final int slot, final ItemStack stack) {
        this.set(slot, stack, false);
    }
    
    public GUIItem get(final int slot) {
        for (final GUIItem item : this.items) {
            if (item.getSlot() == slot) {
                return item;
            }
        }
        return null;
    }
    
    public void fill(final ItemStack stack, final int cornerSlot, final int cornerSlot2, final boolean overwrite, final boolean pickup) {
        if (cornerSlot < 0 || cornerSlot > this.size) {
            throw new IllegalArgumentException("Corner 1 of the border described is out of bounds");
        }
        if (cornerSlot2 < 0 || cornerSlot2 > this.size) {
            throw new IllegalArgumentException("Corner 2 of the border described is out of bounds");
        }
        int topLeft;
        int bottomRight;
        int topRight;
        for (topLeft = Math.min(cornerSlot, cornerSlot2), topRight = (bottomRight = Math.max(cornerSlot, cornerSlot2)); topRight > topLeft; topRight -= 9) {}
        int bottomLeft;
        for (bottomLeft = topLeft; bottomLeft < bottomRight; bottomLeft += 9) {}
        topRight += 9;
        bottomLeft -= 9;
        for (int y = topLeft; y <= bottomLeft; y += 9) {
            for (int x = y; x <= topRight - topLeft + y; ++x) {
                final int f = x;
                if (this.items.stream().filter(item -> item.getSlot() == f).toArray().length == 0 || overwrite) {
                    this.set(x, stack, pickup);
                }
            }
        }
    }
    
    public void fill(final ItemStack stack, final int cornerSlot, final int cornerSlot2, final boolean pickup) {
        this.fill(stack, cornerSlot, cornerSlot2, true, pickup);
    }
    
    public void fill(final ItemStack stack, final int cornerSlot, final int cornerSlot2) {
        this.fill(stack, cornerSlot, cornerSlot2, false);
    }
    
    public void fill(final ItemStack stack) {
        this.fill(stack, 0, this.size - 1);
    }
    
    public void fill(final Material material) {
        this.fill(new ItemStack(material));
    }
    
    public void border(final ItemStack stack, final int cornerSlot, final int cornerSlot2, final boolean overwrite, final boolean pickup) {
        if (cornerSlot < 0 || cornerSlot > this.size) {
            throw new IllegalArgumentException("Corner 1 of the border described is out of bounds");
        }
        if (cornerSlot2 < 0 || cornerSlot2 > this.size) {
            throw new IllegalArgumentException("Corner 2 of the border described is out of bounds");
        }
        int topLeft;
        int bottomRight;
        int topRight;
        for (topLeft = Math.min(cornerSlot, cornerSlot2), topRight = (bottomRight = Math.max(cornerSlot, cornerSlot2)); topRight > topLeft; topRight -= 9) {}
        int bottomLeft;
        for (bottomLeft = topLeft; bottomLeft < bottomRight; bottomLeft += 9) {}
        topRight += 9;
        bottomLeft -= 9;
        for (int y = topLeft; y <= bottomLeft; y += 9) {
            for (int x = y; x <= topRight - topLeft + y; ++x) {
                final int f = x;
                if (this.items.stream().filter(item -> item.getSlot() == f).toArray().length == 0 || overwrite) {
                    if (y == topLeft || y == bottomLeft) {
                        this.set(x, stack, pickup);
                    }
                    if (x == y || x == topRight - topLeft + y) {
                        this.set(x, stack, pickup);
                    }
                }
            }
        }
    }
    
    public void border(final ItemStack stack, final int cornerSlot, final int cornerSlot2, final boolean pickup) {
        this.border(stack, cornerSlot, cornerSlot2, true, pickup);
    }
    
    public void border(final ItemStack stack, final int cornerSlot, final int cornerSlot2) {
        this.border(stack, cornerSlot, cornerSlot2, false);
    }
    
    public void border(final ItemStack stack) {
        this.border(stack, 0, this.size - 1);
    }
    
    public void add(final SMaterial material, final byte variant, final int amount, final boolean pickup) {
        for (int i = 0; i < amount / 64; ++i) {
            final int first = this.firstEmpty();
            if (first == -1) {
                return;
            }
            this.set(first, SUtil.setStackAmount(SItem.of(material, variant).getStack(), 64), pickup);
        }
        final int first2 = this.firstEmpty();
        if (first2 == -1) {
            return;
        }
        this.set(first2, SUtil.setStackAmount(SItem.of(material, variant).getStack(), amount % 64), pickup);
    }
    
    public int firstEmpty() {
        for (int i = 0; i < this.size; ++i) {
            final int finalI = i;
            final long found = this.items.stream().filter(item -> item.getSlot() == finalI).count();
            if (found == 0L) {
                return i;
            }
        }
        return -1;
    }
    
    public void open(final Player player) {
        this.early(player);
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)player, this.size, this.title);
        final GUIOpenEvent openEvent = new GUIOpenEvent(player, this, inventory);
        SkyBlock.getPlugin().getServer().getPluginManager().callEvent((Event)openEvent);
        if (openEvent.isCancelled()) {
            return;
        }
        for (final GUIItem item : this.items) {
            inventory.setItem(item.getSlot(), item.getItem());
        }
        player.openInventory(inventory);
        GUI.GUI_MAP.remove((Object)player.getUniqueId());
        GUI.GUI_MAP.put((Object)player.getUniqueId(), (Object)this);
    }
    
    public void update(final Inventory inventory) {
    }
    
    public void onOpen(final GUIOpenEvent e) throws IOException {
    }
    
    public void onClose(final InventoryCloseEvent e) {
    }
    
    public void early(final Player player) {
    }
    
    public void onBottomClick(final InventoryClickEvent e) throws IOException {
    }
    
    public void onTopClick(final InventoryClickEvent e) {
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public List<GUIItem> getItems() {
        return this.items;
    }
    
    static {
        BLACK_STAINED_GLASS_PANE = SUtil.createColoredStainedGlassPane((short)15, " ");
        RED_STAINED_GLASS_PANE = SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " ");
        LIME_STAINED_GLASS_PANE = SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " ");
        GRAY_STAINED_GLASS_PANE = SUtil.createColoredStainedGlassPane((short)7, (Object)ChatColor.RESET + " ");
        GUI_MAP = (Map)new HashMap();
    }
}
