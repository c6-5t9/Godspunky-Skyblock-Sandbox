package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.item.ShapelessRecipe;
import net.hypixel.skyblock.item.ShapedRecipe;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.event.SkyBlockCraftEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import java.util.List;
import net.hypixel.skyblock.item.MaterialQuantifiable;
import java.util.ArrayList;
import net.hypixel.skyblock.item.Recipe;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingTableGUI extends GUI implements BlockBasedGUI
{
    private static final ItemStack RECIPE_REQUIRED;
    private static final int[] CRAFT_SLOTS;
    private static final ItemStack LOCKED_RECIPE_ITEM;
    private static final int RESULT_SLOT = 24;
    
    public CraftingTableGUI() {
        super("Craft Item", 54);
        this.fill(CraftingTableGUI.BLACK_STAINED_GLASS_PANE, 13, 34);
        this.border(CraftingTableGUI.RED_STAINED_GLASS_PANE);
        this.border(CraftingTableGUI.BLACK_STAINED_GLASS_PANE, 0, 44);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final boolean shift = e.isShiftClick();
                final Inventory inventory = e.getClickedInventory();
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.BARRIER || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                final ItemStack result = inventory.getItem(24);
                if (result == null || result.getType() == Material.AIR) {
                    return;
                }
                final SItem item = SItem.find(result);
                item.setAmount(result.getAmount());
                if (!shift) {
                    if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                        SItem cursor = SItem.find(e.getCursor());
                        if (cursor == null) {
                            cursor = SItem.convert(e.getCursor());
                        }
                        if (!item.equals(cursor)) {
                            return;
                        }
                        if (e.getCursor().getAmount() + result.getAmount() > 64) {
                            return;
                        }
                        e.getCursor().setAmount(e.getCursor().getAmount() + result.getAmount());
                    }
                    else {
                        e.getWhoClicked().setItemOnCursor(result);
                    }
                }
                final Recipe<?> recipe = Recipe.parseRecipe(CraftingTableGUI.this.getCurrentRecipe(inventory));
                if (recipe == null) {
                    return;
                }
                final List<MaterialQuantifiable> ingredients = recipe.getIngredients();
                final List<MaterialQuantifiable> materials = (List<MaterialQuantifiable>)new ArrayList(ingredients.size());
                for (final MaterialQuantifiable ingredient : ingredients) {
                    materials.add((Object)ingredient.clone());
                }
                int max = Integer.MAX_VALUE;
                if (shift) {
                    for (final int slot : CraftingTableGUI.CRAFT_SLOTS) {
                        final ItemStack stack = inventory.getItem(slot);
                        final int ind = indexOf(recipe, materials, MaterialQuantifiable.of(stack));
                        if (ind != -1) {
                            final MaterialQuantifiable material = (MaterialQuantifiable)materials.get(ind);
                            final int m = stack.getAmount() / material.getAmount();
                            if (m < max) {
                                max = m;
                            }
                        }
                    }
                }
                for (final int slot : CraftingTableGUI.CRAFT_SLOTS) {
                    final ItemStack stack = inventory.getItem(slot);
                    final int ind = indexOf(recipe, materials, MaterialQuantifiable.of(stack));
                    if (ind != -1) {
                        final MaterialQuantifiable material = (MaterialQuantifiable)materials.get(ind);
                        int remaining = stack.getAmount() - material.getAmount();
                        if (shift) {
                            remaining = stack.getAmount() - material.getAmount() * max;
                        }
                        materials.remove(ind);
                        if (remaining <= 0) {
                            inventory.setItem(slot, new ItemStack(Material.AIR));
                        }
                        else {
                            material.setAmount(remaining);
                            stack.setAmount(remaining);
                        }
                    }
                }
                if (shift) {
                    final HashMap hashMap = e.getWhoClicked().getInventory().addItem(new ItemStack[] { SUtil.setStackAmount(result, result.getAmount() * max) });
                    for (final Object stack2 : hashMap.values()) {
                        e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getLocation(), (ItemStack)stack2).setVelocity(e.getWhoClicked().getLocation().getDirection());
                    }
                }
                final SkyBlockCraftEvent skyBlockCraftEvent = new SkyBlockCraftEvent(recipe, (Player)e.getWhoClicked());
                Bukkit.getPluginManager().callEvent((Event)skyBlockCraftEvent);
                CraftingTableGUI.this.update(inventory);
            }
            
            @Override
            public int getSlot() {
                return 24;
            }
            
            @Override
            public ItemStack getItem() {
                return CraftingTableGUI.RECIPE_REQUIRED;
            }
            
            @Override
            public boolean canPickup() {
                return false;
            }
        });
        this.set(GUIClickableItem.getCloseItem(49));
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final GUI gui = e.getOpened();
                final GUI current = (GUI)GUI.GUI_MAP.get((Object)e.getPlayer().getUniqueId());
                final InventoryView view = e.getPlayer().getOpenInventory();
                if (!(current instanceof CraftingTableGUI) || view == null) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = view.getTopInventory();
                new BukkitRunnable() {
                    public void run() {
                        final Recipe<?> recipe = Recipe.parseRecipe(CraftingTableGUI.this.getCurrentRecipe(inventory));
                        if (recipe == null) {
                            inventory.setItem(24, CraftingTableGUI.RECIPE_REQUIRED);
                            SUtil.border(inventory, gui, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                            SUtil.border(inventory, gui, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                            return;
                        }
                        if (!recipe.isUnlockedForPlayer(User.getUser(e.getPlayer().getUniqueId())) && !CraftingTableGUI.this.isVanilla(recipe)) {
                            inventory.setItem(24, CraftingTableGUI.LOCKED_RECIPE_ITEM);
                            return;
                        }
                        final SItem sItem = recipe.getResult();
                        inventory.setItem(24, sItem.getStack());
                        SUtil.border(inventory, gui, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                        SUtil.border(inventory, gui, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                    }
                }.runTaskLaterAsynchronously((Plugin)SkyBlock.getPlugin(), 1L);
            }
        }.runTaskTimerAsynchronously((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    @Override
    public Material getBlock() {
        return Material.WORKBENCH;
    }
    
    private ItemStack[] getCurrentRecipe(final Inventory inventory) {
        final ItemStack[] stacks = new ItemStack[9];
        for (int i = 0; i < CraftingTableGUI.CRAFT_SLOTS.length; ++i) {
            stacks[i] = inventory.getItem(CraftingTableGUI.CRAFT_SLOTS[i]);
        }
        return stacks;
    }
    
    private static int indexOf(final Recipe<?> recipe, final List<MaterialQuantifiable> ingredients, final MaterialQuantifiable search) {
        final List<SMaterial> exchangeables = Recipe.getExchangeablesOf(search.getMaterial());
        for (int i = 0; i < ingredients.size(); ++i) {
            final MaterialQuantifiable ingredient = (MaterialQuantifiable)ingredients.get(i);
            if (recipe.isUseExchangeables() && exchangeables != null && exchangeables.contains((Object)ingredient.getMaterial()) && search.getAmount() >= ingredient.getAmount()) {
                return i;
            }
            if (ingredient.getMaterial() == search.getMaterial() && search.getAmount() >= ingredient.getAmount()) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isVanilla(final Recipe<?> recipe) {
        if (recipe instanceof ShapedRecipe) {
            return ((ShapedRecipe)recipe).isVanilla();
        }
        return recipe instanceof ShapelessRecipe && ((ShapelessRecipe)recipe).isVanilla();
    }
    
    static {
        RECIPE_REQUIRED = SUtil.createNamedItemStack(Material.BARRIER, (Object)ChatColor.RED + "Recipe Required");
        LOCKED_RECIPE_ITEM = SUtil.createNamedItemStack(Material.BARRIER, (Object)ChatColor.RED + "Locked Recipe");
        CRAFT_SLOTS = new int[] { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
    }
}
