package net.hypixel.skyblock.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.Inventory;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.event.inventory.InventoryCloseEvent;
import net.hypixel.skyblock.database.RecipeDatabase;
import java.util.Iterator;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.ShapelessRecipe;
import net.hypixel.skyblock.util.Groups;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RecipeCreatorGUI extends GUI
{
    private static final int[] GRID;
    
    public RecipeCreatorGUI() {
        super("Admin Recipe Creator", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(RecipeCreatorGUI.BLACK_STAINED_GLASS_PANE, 13, 34);
        this.border(RecipeCreatorGUI.RED_STAINED_GLASS_PANE);
        this.border(RecipeCreatorGUI.BLACK_STAINED_GLASS_PANE, 0, 44);
        this.set(24, null);
        final Player player = e.getPlayer();
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent event) {
                player.closeInventory();
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return new ItemBuilder(Material.BARRIER).setDisplayName((Object)ChatColor.RED + "close").toItemStack();
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent event) {
                final ItemStack result = event.getInventory().getItem(24);
                if (result == null) {
                    player.sendMessage((Object)ChatColor.RED + "You should specify result item");
                    return;
                }
                final ShapelessRecipe recipe = new ShapelessRecipe(SItem.find(result), Groups.EXCHANGEABLE_RECIPE_RESULTS.contains((Object)result.getType()));
                recipe.getResult().setAmount(result.getAmount());
                for (final ItemStack stack : RecipeCreatorGUI.this.getGridItems(e.getInventory())) {
                    recipe.add(SItem.find(stack).getType(), stack.getAmount());
                }
                SUtil.runAsync(() -> RecipeDatabase.save(recipe));
                player.sendMessage((Object)ChatColor.GREEN + "Successfully Saved " + recipe.getResult().getDisplayName() + " Recipe");
                User.getUser(player.getUniqueId()).getUnlockedRecipes().add((Object)recipe.getResult().getDisplayName());
            }
            
            @Override
            public int getSlot() {
                return 50;
            }
            
            @Override
            public ItemStack getItem() {
                return new ItemBuilder(Material.EMERALD).setDisplayName((Object)ChatColor.GREEN + "Save").toItemStack();
            }
        });
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        if (e.getInventory().getItem(24) != null) {
            Sputnik.smartGiveItem(e.getInventory().getItem(24), (Player)e.getPlayer());
        }
        for (final ItemStack stack : this.getGridItems(e.getInventory())) {
            Sputnik.smartGiveItem(stack, (Player)e.getPlayer());
        }
    }
    
    @Override
    public void onTopClick(final InventoryClickEvent e) {
        for (final int slot : RecipeCreatorGUI.GRID) {
            if (slot == e.getSlot() || e.getSlot() == 24) {
                e.setCancelled(false);
                break;
            }
        }
    }
    
    public List<ItemStack> getGridItems(final Inventory inventory) {
        final ArrayList<ItemStack> stacks = (ArrayList<ItemStack>)new ArrayList();
        for (final int slot : RecipeCreatorGUI.GRID) {
            if (inventory.getItem(slot) != null) {
                stacks.add((Object)inventory.getItem(slot));
            }
        }
        return (List<ItemStack>)stacks;
    }
    
    static {
        GRID = new int[] { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
    }
}
