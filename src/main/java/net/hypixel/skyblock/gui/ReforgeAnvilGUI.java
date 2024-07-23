package net.hypixel.skyblock.gui;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.stream.Collectors;
import java.util.Arrays;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import net.hypixel.skyblock.item.Reforgable;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.UUID;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;

public class ReforgeAnvilGUI extends GUI implements Listener
{
    private static final ItemStack DEFAULT_REFORGE_ITEM;
    private static final Map<Rarity, Integer> COST_MAP;
    private static final List<UUID> COOLDOWN;
    
    public void fillFrom(final Inventory i, final int startFromSlot, final int height, final ItemStack stacc) {
        i.setItem(startFromSlot, stacc);
        i.setItem(startFromSlot + 9, stacc);
        i.setItem(startFromSlot + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9, stacc);
    }
    
    public ReforgeAnvilGUI() {
        super("Reforge Item", 45);
        this.fill(ReforgeAnvilGUI.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(40));
        this.set(new GUIClickableItem() {
            @Override
            public int getSlot() {
                return 22;
            }
            
            @Override
            public ItemStack getItem() {
                return ReforgeAnvilGUI.DEFAULT_REFORGE_ITEM;
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
                if (!(sItem.getType().getGenericInstance() instanceof Reforgable)) {
                    return;
                }
                final List<ReforgeType> possible = (List<ReforgeType>)Arrays.stream((Object[])ReforgeType.values()).filter(type -> type.getReforge().getCompatibleTypes().contains((Object)sItem.getType().getStatistics().getType()) && type.isAccessible()).collect(Collectors.toList());
                final Player player = (Player)e.getWhoClicked();
                if (possible.size() == 0) {
                    player.sendMessage((Object)ChatColor.RED + "That item cannot be reforged!");
                    return;
                }
                if (ReforgeAnvilGUI.COOLDOWN.contains((Object)player.getUniqueId())) {
                    player.sendMessage((Object)ChatColor.RED + "Please wait a little bit before doing this!");
                    return;
                }
                final User user = User.getUser(player.getUniqueId());
                final int cost = (int)ReforgeAnvilGUI.COST_MAP.get((Object)sItem.getRarity());
                if (user.getCoins() - cost < 0L) {
                    player.sendMessage((Object)ChatColor.RED + "You cannot afford to reforge this!");
                    return;
                }
                final String prev = sItem.getFullName();
                user.subCoins(cost);
                sItem.setReforge(((ReforgeType)possible.get(SUtil.random(0, possible.size() - 1))).getReforge());
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
                player.sendMessage((Object)ChatColor.GREEN + "You reforged your " + prev + (Object)ChatColor.GREEN + " into a " + sItem.getFullName() + (Object)ChatColor.GREEN + "!");
                ReforgeAnvilGUI.COOLDOWN.add((Object)player.getUniqueId());
                new BukkitRunnable() {
                    public void run() {
                        ReforgeAnvilGUI.COOLDOWN.remove((Object)player.getUniqueId());
                    }
                }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
            }
        });
        this.set(13, null);
    }
    
    @Override
    public void update(final Inventory inventory) {
        new BukkitRunnable() {
            public void run() {
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    inventory.setItem(22, ReforgeAnvilGUI.DEFAULT_REFORGE_ITEM);
                    return;
                }
                ItemStack stack = SUtil.getStack((Object)ChatColor.GREEN + "Reforge Item", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Reforges the above item, giving", (Object)ChatColor.GRAY + "it a random item modifier that", (Object)ChatColor.GRAY + "boosts its stats.", " ", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.GOLD + SUtil.commaify((int)ReforgeAnvilGUI.COST_MAP.get((Object)sItem.getRarity())) + " Coins", " ", (Object)ChatColor.YELLOW + "Click to reforge!");
                if (!sItem.isReforgable()) {
                    stack = SUtil.getStack((Object)ChatColor.RED + "Error!", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "You cannot reforge this item!");
                }
                inventory.setItem(22, stack);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player player = e.getPlayer();
                if (ReforgeAnvilGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = e.getInventory();
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    ReforgeAnvilGUI.this.fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "));
                    ReforgeAnvilGUI.this.fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "));
                    return;
                }
                if (sItem.isReforgable()) {
                    ReforgeAnvilGUI.this.fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "));
                    ReforgeAnvilGUI.this.fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getPlayer();
        final GUI gui = (GUI)ReforgeAnvilGUI.GUI_MAP.get((Object)player.getUniqueId());
        if (gui == null) {
            return;
        }
        gui.onClose(e);
        ReforgeAnvilGUI.GUI_MAP.remove((Object)player.getUniqueId());
    }
    
    static {
        DEFAULT_REFORGE_ITEM = SUtil.getStack((Object)ChatColor.YELLOW + "Reforge Item", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Place an item above to reforge", (Object)ChatColor.GRAY + "it! Reforging items adds a", (Object)ChatColor.GRAY + "random modifier to the item that", (Object)ChatColor.GRAY + "grants stat boosts.");
        COST_MAP = (Map)new HashMap();
        COOLDOWN = (List)new ArrayList();
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.COMMON, (Object)250);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.UNCOMMON, (Object)500);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.RARE, (Object)1000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.EPIC, (Object)2500);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.LEGENDARY, (Object)5000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.MYTHIC, (Object)10000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.SUPREME, (Object)15000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.SPECIAL, (Object)25000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.VERY_SPECIAL, (Object)50000);
        ReforgeAnvilGUI.COST_MAP.put((Object)Rarity.EXCLUSIVE, (Object)1000000);
    }
}
