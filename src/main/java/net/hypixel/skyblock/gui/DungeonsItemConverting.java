package net.hypixel.skyblock.gui;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.HumanEntity;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.user.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.UUID;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class DungeonsItemConverting extends GUI
{
    private static final ItemStack DEFAULT_REFORGE_ITEM;
    private static final ItemStack ANVIL_BARRIER;
    private static final Map<Rarity, Integer> COST_MAP;
    private static final List<UUID> COOLDOWN;
    
    public void fillFrom(final Inventory i, final int startFromSlot, final int height, final ItemStack stacc) {
        i.setItem(startFromSlot, stacc);
        i.setItem(startFromSlot + 9, stacc);
        i.setItem(startFromSlot + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9 + 9, stacc);
    }
    
    public DungeonsItemConverting() {
        super("Dungeons Crafting", 54);
        this.fill(DungeonsItemConverting.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(13, null);
        this.set(new GUIClickableItem() {
            @Override
            public int getSlot() {
                return 22;
            }
            
            @Override
            public ItemStack getItem() {
                return DungeonsItemConverting.DEFAULT_REFORGE_ITEM;
            }
            
            @Override
            public boolean canPickup() {
                return false;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                final SItem sItem = SItem.find(e.getClickedInventory().getItem(13));
                if (sItem == null) {
                    return;
                }
                final Player player = (Player)e.getWhoClicked();
                if (!sItem.isStarrable()) {
                    player.sendMessage((Object)ChatColor.RED + "That item cannot be upgraded!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    return;
                }
                if (sItem.getStar() >= 5) {
                    player.sendMessage((Object)ChatColor.RED + "That item cannot be upgraded any further!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    return;
                }
                if (e.getClickedInventory().getItem(31) != null && e.getClickedInventory().getItem(31).getType() != Material.BARRIER) {
                    final long add = (int)DungeonsItemConverting.COST_MAP.get((Object)sItem.getRarity()) * (long)(sItem.getStar() + 1);
                    final double cur = (double)User.getUser(player.getUniqueId()).getCoins();
                    if (!sItem.isStarrable() || sItem.getStar() >= 5) {
                        player.sendMessage((Object)ChatColor.RED + "You cannot upgrade this item.");
                        return;
                    }
                    if (add > cur) {
                        player.sendMessage((Object)ChatColor.RED + "You cannot afford to upgrade this.");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
                    User.getUser(player.getUniqueId()).subCoins(add);
                    e.getClickedInventory().setItem(13, (ItemStack)null);
                    final SItem build = sItem;
                    if (build.isDungeonsItem()) {
                        build.setStarAmount(build.getStar() + 1);
                    }
                    else {
                        build.setDungeonsItem(true);
                        build.setStarAmount(0);
                    }
                    build.setDataString("owner", e.getWhoClicked().getUniqueId().toString());
                    final ItemStack st = User.getUser(e.getWhoClicked().getUniqueId()).updateItemBoost(build);
                    e.getClickedInventory().setItem(31, st);
                }
            }
        });
    }
    
    @Override
    public void update(final Inventory inventory) {
        new BukkitRunnable() {
            public void run() {
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    inventory.setItem(22, DungeonsItemConverting.DEFAULT_REFORGE_ITEM);
                    if (SItem.find(inventory.getItem(31)) != null && SItem.find(inventory.getItem(31)).getDataBoolean("dummyItem")) {
                        inventory.setItem(22, DungeonsItemConverting.DEFAULT_REFORGE_ITEM);
                        inventory.setItem(31, DungeonsItemConverting.ANVIL_BARRIER);
                    }
                    return;
                }
                if (inventory.getItem(31) == null) {
                    inventory.setItem(22, DungeonsItemConverting.DEFAULT_REFORGE_ITEM);
                    inventory.setItem(31, DungeonsItemConverting.ANVIL_BARRIER);
                }
                ItemStack stack = SUtil.getStack((Object)ChatColor.GREEN + "Upgrade Item", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Upgrades the above items to a", (Object)ChatColor.GRAY + sItem.getFullName() + Sputnik.createStarStringFromAmount(sItem.getStar() + 1) + (Object)ChatColor.GRAY + "!", (Object)ChatColor.GRAY + "This grant an additional " + (Object)ChatColor.GREEN + (sItem.getStar() + 1) * 10 + "%", (Object)ChatColor.GRAY + "stat boost while in Dungeons!", " ", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.AQUA + SUtil.commaify((int)DungeonsItemConverting.COST_MAP.get((Object)sItem.getRarity()) * (sItem.getStar() + 1)) + " Bits", " ", (Object)ChatColor.YELLOW + "Click to upgrade!");
                if (!sItem.isDungeonsItem()) {
                    stack = SUtil.getStack((Object)ChatColor.GREEN + "Upgrade Item", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Converts the above items into a", (Object)ChatColor.GRAY + "Dungeon item! This grants the", (Object)ChatColor.GRAY + "item a stat boost while in ", (Object)ChatColor.GRAY + "Dungeons!", " ", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.AQUA + SUtil.commaify((int)DungeonsItemConverting.COST_MAP.get((Object)sItem.getRarity()) * (sItem.getStar() + 1)) + " Coins", " ", (Object)ChatColor.YELLOW + "Click to upgrade!");
                }
                ItemStack barrierStack = DungeonsItemConverting.ANVIL_BARRIER;
                if (!sItem.isStarrable() || sItem.getStar() >= 5) {
                    stack = DungeonsItemConverting.DEFAULT_REFORGE_ITEM;
                    if (!sItem.isStarrable()) {
                        barrierStack = SUtil.getStack((Object)ChatColor.RED + "Error!", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "This item cannot be upgraded");
                    }
                    else if (sItem.getStar() >= 5) {
                        barrierStack = SUtil.getStack((Object)ChatColor.RED + "Error!", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "This item cannot be upgraded any", (Object)ChatColor.GRAY + "further!");
                    }
                }
                else {
                    final SItem build = sItem.clone();
                    if (build.isDungeonsItem()) {
                        build.setStarAmount(build.getStar() + 1);
                    }
                    else {
                        build.setDungeonsItem(true);
                        build.setStarAmount(0);
                    }
                    build.setDataBoolean("dummyItem", true);
                    ItemStack st = build.getStack();
                    if (inventory.getViewers().get(0) != null) {
                        st = User.getUser(((HumanEntity)inventory.getViewers().get(0)).getUniqueId()).updateItemBoost(build);
                        build.setDataString("owner", ((HumanEntity)inventory.getViewers().get(0)).getUniqueId().toString());
                    }
                    final ItemMeta mt = build.getStack().getItemMeta();
                    final List<String> s = (List<String>)mt.getLore();
                    s.add((Object)" ");
                    s.add((Object)((Object)ChatColor.GRAY + "Cost"));
                    s.add((Object)((Object)ChatColor.AQUA + SUtil.commaify((int)DungeonsItemConverting.COST_MAP.get((Object)sItem.getRarity()) * (sItem.getStar() + 1)) + " Coins"));
                    s.add((Object)" ");
                    s.add((Object)((Object)ChatColor.YELLOW + "Click on the item above to"));
                    s.add((Object)((Object)ChatColor.YELLOW + "upgrade!"));
                    mt.setLore((List)s);
                    st.setItemMeta(mt);
                    barrierStack = st;
                }
                inventory.setItem(22, stack);
                inventory.setItem(31, barrierStack);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player player = e.getPlayer();
                if (DungeonsItemConverting.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = e.getInventory();
                DungeonsItemConverting.this.update(inventory);
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    DungeonsItemConverting.this.fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "));
                    DungeonsItemConverting.this.fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "));
                    return;
                }
                if (sItem.isStarrable() && sItem.getStar() < 5) {
                    DungeonsItemConverting.this.fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "));
                    DungeonsItemConverting.this.fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final ItemStack current = e.getCurrentItem();
                if (current == null) {
                    return;
                }
                if (e.getCurrentItem().getType() == Material.BARRIER) {
                    e.setCancelled(true);
                    return;
                }
                final Inventory inventory = e.getClickedInventory();
                if (!SUtil.isAir(inventory.getItem(13))) {
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage((Object)ChatColor.RED + "Click on the anvil above to upgrade!");
                    return;
                }
                new BukkitRunnable() {
                    public void run() {
                        inventory.setItem(e.getSlot(), DungeonsItemConverting.ANVIL_BARRIER);
                    }
                }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public boolean canPickup() {
                return true;
            }
            
            @Override
            public ItemStack getItem() {
                return DungeonsItemConverting.ANVIL_BARRIER;
            }
        });
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getPlayer();
        final GUI gui = (GUI)DungeonsItemConverting.GUI_MAP.get((Object)player.getUniqueId());
        if (gui == null) {
            return;
        }
        gui.onClose(e);
        DungeonsItemConverting.GUI_MAP.remove((Object)player.getUniqueId());
    }
    
    static {
        DEFAULT_REFORGE_ITEM = SUtil.getStack((Object)ChatColor.RED + "Upgrade Item", Material.ANVIL, (short)0, 1, Sputnik.trans("&7Upgrades items using"), Sputnik.trans("&bBits &7place a weapon or"), Sputnik.trans("&7armor piece above to upgrade it"), Sputnik.trans("&7to a &dDungeon item&7, improving"), Sputnik.trans("&7its stats while in Dungeons. You"), Sputnik.trans("&7can also upgrade an existing"), Sputnik.trans("&7Dungeon item's stats!"));
        ANVIL_BARRIER = SUtil.getStack((Object)ChatColor.RED + "Upgrade Item", Material.BARRIER, (short)0, 1, Sputnik.trans("&7Upgrades items using"), Sputnik.trans("&bBits &7place a weapon or"), Sputnik.trans("&7armor piece above to upgrade it"), Sputnik.trans("&7to a &dDungeon item&7, improving"), Sputnik.trans("&7its stats while in Dungeons. You"), Sputnik.trans("&7can also upgrade an existing"), Sputnik.trans("&7Dungeon item's stats!"));
        COST_MAP = (Map)new HashMap();
        COOLDOWN = (List)new ArrayList();
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.COMMON, (Object)400);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.UNCOMMON, (Object)750);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.RARE, (Object)1050);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.EPIC, (Object)1470);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.LEGENDARY, (Object)1820);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.MYTHIC, (Object)2150);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.SUPREME, (Object)4100);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.SPECIAL, (Object)4600);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.VERY_SPECIAL, (Object)7000);
        DungeonsItemConverting.COST_MAP.put((Object)Rarity.EXCLUSIVE, (Object)100000);
    }
}
