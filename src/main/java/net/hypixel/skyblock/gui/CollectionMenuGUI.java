package net.hypixel.skyblock.gui;

import java.util.Iterator;
import java.util.Collection;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.collection.ItemCollectionCategory;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.collection.ItemCollection;
import java.util.concurrent.atomic.AtomicInteger;
import net.hypixel.skyblock.user.User;

public class CollectionMenuGUI extends GUI
{
    public CollectionMenuGUI() {
        super("Collection", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(CollectionMenuGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.set(GUIClickableItem.getCloseItem(49));
        final AtomicInteger found = new AtomicInteger();
        final Collection<ItemCollection> collections = ItemCollection.getCollections();
        for (final ItemCollection collection : collections) {
            if (user.getCollection(collection) > 0) {
                found.incrementAndGet();
            }
        }
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (Object)ChatColor.GRAY + "To SkyBlock Menu"));
        final String[] progress = ItemCollection.getProgress(player, null);
        this.set(4, SUtil.getStack((Object)ChatColor.GREEN + "Collection", Material.PAINTING, (short)0, 1, (Object)ChatColor.GRAY + "View all of the items available", (Object)ChatColor.GRAY + "in SkyBlock. Collect more of an", (Object)ChatColor.GRAY + "item to unlock rewards on your", (Object)ChatColor.GRAY + "way to becoming a master of", (Object)ChatColor.GRAY + "SkyBlock!", " ", progress[0], progress[1]));
        this.set(createCollectionClickable(new CategoryCollectionGUI(ItemCollectionCategory.FARMING), ItemCollectionCategory.FARMING, Material.GOLD_HOE, 20, player));
        this.set(createCollectionClickable(new CategoryCollectionGUI(ItemCollectionCategory.MINING), ItemCollectionCategory.MINING, Material.STONE_PICKAXE, 21, player));
        this.set(createCollectionClickable(new CategoryCollectionGUI(ItemCollectionCategory.COMBAT), ItemCollectionCategory.COMBAT, Material.STONE_SWORD, 22, player));
        this.set(createCollectionClickable(new CategoryCollectionGUI(ItemCollectionCategory.FORAGING), ItemCollectionCategory.FORAGING, Material.SAPLING, (short)3, 23, player));
        this.set(createCollectionClickable(new CategoryCollectionGUI(ItemCollectionCategory.FISHING), ItemCollectionCategory.FISHING, Material.FISHING_ROD, 24, player));
    }
    
    private static GUIClickableItem createCollectionClickable(final GUI gui, final ItemCollectionCategory category, final Material icon, final short data, final int slot, final Player player) {
        final String[] progress = ItemCollection.getProgress(player, category);
        return GUIClickableItem.createGUIOpenerItem(gui, player, (Object)ChatColor.GREEN + category.getName() + " Collection", slot, icon, data, (Object)ChatColor.GRAY + "View your " + category.getName() + " Collection!", " ", progress[0], progress[1], " ", (Object)ChatColor.YELLOW + "Click to view!");
    }
    
    private static GUIClickableItem createCollectionClickable(final GUI gui, final ItemCollectionCategory category, final Material icon, final int slot, final Player player) {
        return createCollectionClickable(gui, category, icon, (short)0, slot, player);
    }
}
