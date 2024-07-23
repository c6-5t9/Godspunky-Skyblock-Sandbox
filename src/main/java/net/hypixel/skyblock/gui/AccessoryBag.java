package net.hypixel.skyblock.gui;

import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.Sound;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.stream.IntStream;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import org.bukkit.Material;
import java.io.IOException;
import java.util.Arrays;
import lombok.NonNull;
import java.util.List;
import net.hypixel.skyblock.api.serializer.BukkitSerializeClass;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import java.util.ArrayList;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.util.SLog;
import java.io.File;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.ItemListener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

public class AccessoryBag extends GUI
{
    private int page;
    private int maxSlot;
    private int closePos;
    private Player player;
    private Inventory inventory;
    
    public AccessoryBag(final int page, final int size, final int maxSlot, final int closePos) {
        super("Accessory Bag", size);
        this.page = page;
        this.maxSlot = maxSlot;
        for (int i = 0; i < size; ++i) {
            if (i >= maxSlot) {
                this.set(i, AccessoryBag.BLACK_STAINED_GLASS_PANE);
            }
        }
        this.closePos = closePos;
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.inventory = e.getInventory();
        this.player = e.getPlayer();
        this.loadPage();
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        try {
            this.savePage();
            ItemListener.updateStatistics(this.player);
        }
        catch (final Throwable $ex) {
            throw $ex;
        }
    }
    
    @Override
    public void update(final Inventory inventory) {
        this.inventory = inventory;
        this.savePage();
        ItemListener.updateStatistics(this.player);
    }
    
    private void savePage() {
        try {
            if (this.inventory == null) {
                return;
            }
            final User user = User.getUser(this.player);
            final File home = User.getDataDirectory();
            final File raw = new File(home, (Object)user.getUuid() + "_accessory-bag.yml");
            if (!raw.exists()) {
                raw.createNewFile();
                SLog.info("Creating new Accessory Bag for player " + (Object)user.getUuid());
            }
            final Config file = new Config(home, (Object)user.getUuid() + "_accessory-bag.yml");
            final List<ItemStack> items = (List<ItemStack>)new ArrayList();
            for (int i = 0; i < this.inventory.getSize(); ++i) {
                if (i <= this.maxSlot && i != this.closePos && i != this.closePos - 1) {
                    if (i != this.closePos + 1) {
                        items.add((Object)this.inventory.getItem(i));
                    }
                }
            }
            file.set("bag.page." + SUtil.numToStr[this.page], (Object)BukkitSerializeClass.itemStackArrayToBase64((ItemStack[])items.toArray((Object[])new ItemStack[0])));
            file.save();
        }
        catch (final Throwable $ex) {
            throw $ex;
        }
    }
    
    public static void setAccessories(@NonNull final Player player, final int page, final List<ItemStack> accessories) {
        try {
            if (player == null) {
                throw new NullPointerException("player is marked non-null but is null");
            }
            final User user = User.getUser(player);
            final File home = User.getDataDirectory();
            final File raw = new File(home, (Object)user.getUuid() + "_accessory-bag.yml");
            if (!raw.exists()) {
                raw.createNewFile();
            }
            final Config file = new Config(home, (Object)user.getUuid() + "_accessory-bag.yml");
            file.set("bag.page." + SUtil.numToStr[page], (Object)BukkitSerializeClass.itemStackArrayToBase64((ItemStack[])accessories.toArray((Object[])new ItemStack[0])));
            file.save();
            ItemListener.updateStatistics(player);
        }
        catch (final Throwable $ex) {
            throw $ex;
        }
    }
    
    @Nullable
    public static List<ItemStack> getAccessories(@NonNull final Player player, final int page) {
        if (player == null) {
            throw new NullPointerException("player is marked non-null but is null");
        }
        final User user = User.getUser(player);
        final File home = User.getDataDirectory();
        final File raw = new File(home, (Object)user.getUuid() + "_accessory-bag.yml");
        if (!raw.exists()) {
            return null;
        }
        final Config file = new Config(home, (Object)user.getUuid() + "_accessory-bag.yml");
        List<ItemStack> items = null;
        try {
            items = (List<ItemStack>)Arrays.asList((Object[])BukkitSerializeClass.itemStackArrayFromBase64(file.getString("bag.page." + SUtil.numToStr[page])));
        }
        catch (final IOException e) {
            throw new RuntimeException((Throwable)e);
        }
        if (items == null || items.isEmpty()) {
            return null;
        }
        final List<ItemStack> filtered = (List<ItemStack>)new ArrayList();
        for (final ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (!item.getType().equals((Object)Material.SKULL_ITEM)) {
                continue;
            }
            filtered.add((Object)item);
        }
        return filtered;
    }
    
    private void loadPage() {
        final User user = User.getUser(this.player);
        final File home = User.getDataDirectory();
        final File raw = new File(home, (Object)user.getUuid() + "_accessory-bag.yml");
        if (!raw.exists()) {
            return;
        }
        final Config file = new Config(home, (Object)user.getUuid() + "_accessory-bag.yml");
        List<ItemStack> items = null;
        try {
            items = (List<ItemStack>)Arrays.asList((Object[])BukkitSerializeClass.itemStackArrayFromBase64(file.getString("bag.page." + SUtil.numToStr[this.page])));
        }
        catch (final IOException e) {
            throw new RuntimeException((Throwable)e);
        }
        try {
            final List<ItemStack> finalItems = items;
            IntStream.range(0, this.maxSlot).forEach(slot -> {
                final ItemStack vanilla = (ItemStack)finalItems.get(slot);
                this.set(new GUIClickableItem() {
                    final /* synthetic */ ItemStack val$vanilla;
                    final /* synthetic */ int val$slot;
                    
                    @Override
                    public void run(final InventoryClickEvent e) {
                        AccessoryBag.this.update(AccessoryBag.this.inventory);
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return this.val$vanilla;
                    }
                    
                    @Override
                    public boolean canPickup() {
                        return true;
                    }
                    
                    @Override
                    public int getSlot() {
                        return this.val$slot;
                    }
                });
            });
        }
        catch (final ArrayIndexOutOfBoundsException e2) {
            SLog.info(e2);
        }
        this.set(GUIClickableItem.getCloseItem(this.closePos));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
                SUtil.delay(() -> new AccessoryReforges().open(AccessoryBag.this.player), 5L);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aReforge All"), Material.IRON_INGOT, (short)0, 1, Sputnik.trans4("&7Reforge all your &dAccessories&7 all", "&7in one click!", "&7", "&eClick to Open!"));
            }
            
            @Override
            public int getSlot() {
                return AccessoryBag.this.closePos + 1;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player = (Player)e.getWhoClicked();
                final List<SItem> accessories = PlayerUtils.getAccessories(player);
                if (accessories == null) {
                    player.sendMessage(SUtil.color("&cYour accessory bag is empty!"));
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                    return;
                }
                final List<ItemStack> stacks = (List<ItemStack>)new ArrayList();
                for (final SItem accessory : accessories) {
                    accessory.setRecombobulated(true);
                    stacks.add((Object)accessory.getStack());
                }
                player.sendMessage(SUtil.color("&aYou recombobulated all your accessories!"));
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 10.0f, 2.0f);
                AccessoryBag.this.savePage();
                player.closeInventory();
                SUtil.delay(() -> AccessoryBag.setAccessories(player, AccessoryBag.this.page, stacks), 10L);
                SUtil.delay(() -> new AccessoryBag(AccessoryBag.this.page, AccessoryBag.this.size, AccessoryBag.this.maxSlot, AccessoryBag.this.closePos).open(player), 15L);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack(Sputnik.trans("&aRecombobulate All"), "57ccd36dc8f72adcb1f8c8e61ee82cd96ead140cf2a16a1366be9b5a8e3cc3fc", 1, Sputnik.trans4("&7Automatically recombobulates all", "&7your &dAccessories&7 in one click!", "&7", "&eClick to Upgrade!"));
            }
            
            @Override
            public int getSlot() {
                return AccessoryBag.this.closePos - 1;
            }
        });
    }
    
    @Override
    public void onBottomClick(final InventoryClickEvent e) {
        final ItemStack selected = e.getCurrentItem();
        if (selected == null) {
            return;
        }
        if (selected.getType() == Material.AIR) {
            return;
        }
        SItem item = SItem.find(selected);
        if (item == null) {
            item = SItem.convert(selected);
        }
        if (!SUtil.getItemType(item.getType()).equals((Object)GenericItemType.ACCESSORY)) {
            e.setCancelled(true);
            this.player.sendMessage(SUtil.color("&cYou cannot put this item in the accessory bag!"));
            this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
        }
        final List<ItemStack> accessories = getAccessories(this.player, this.page);
        if (accessories != null) {
            for (final ItemStack stack : accessories) {
                if (stack == null) {
                    continue;
                }
                if (stack.getType().equals((Object)Material.AIR)) {
                    continue;
                }
                final SItem accessory = SItem.find(stack);
                if (item.getType().equals((Object)accessory.getType())) {
                    e.setCancelled(true);
                    this.player.sendMessage(SUtil.color("&cAn accessory of this type is already in the bag!"));
                    this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                final PlayerInventory inv = e.getWhoClicked().getInventory();
                inv.remove(item.getStack());
                this.inventory.addItem(new ItemStack[] { item.getStack() });
            }
        }
    }
}
