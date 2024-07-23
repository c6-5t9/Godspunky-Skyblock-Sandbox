package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.itemeditor.EditableItem;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;

public class ItemEditor extends GUI
{
    public ItemEditor() {
        super("Item Editor (incomplete)", 45);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        if (e.getPlayer().getItemInHand() == null) {
            e.getPlayer().sendMessage((Object)ChatColor.RED + "Please hold a item!");
            e.getPlayer().closeInventory();
            return;
        }
        this.border(ItemEditor.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.getCloseItem(40));
        final User user = User.getUser(player.getUniqueId());
        final EditableItem editableItem = new EditableItem(SItem.find(player.getItemInHand()));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.REFORGE_ANVIL.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.AQUA + "Reforge", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Reforge your items.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "/enc <enchant type> <level>");
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.YELLOW + "Enchant", Material.ENCHANTMENT_TABLE, (short)0, 1, (Object)ChatColor.GRAY + "Enchant your items.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                editableItem.addStar(1);
            }
            
            @Override
            public int getSlot() {
                return 21;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.RED + "Add Star", "8216ee40593c0981ed28f5bd674879781c425ce0841b687481c4f7118bb5c3b1", 1, (Object)ChatColor.GRAY + "Add or remove stars", (Object)ChatColor.GRAY + "from your items.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.DUNGEON_CRAFTING.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 23;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GOLD + "Convert to Dungeon Item", "5a6314eac34416ce10ab22c2e1c4dcb472a3feb98d4e04d3fbbb85a9a471b18", 1, (Object)ChatColor.GRAY + "Convert your items ", (Object)ChatColor.GRAY + "to dungeon items.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                editableItem.recom(true);
            }
            
            @Override
            public int getSlot() {
                return 34;
            }
            
            @Override
            public ItemStack getItem() {
                return SItem.of(SMaterial.RECOMBOBULATOR_3000).getStack();
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.LIGHT_PURPLE + "Change Rarity", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.GRAY + "Change rarity of your items.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (editableItem.getHandle().getHPBs() == 20) {
                    player.sendMessage(Sputnik.trans("&cYou have already reached the Maximum limit!"));
                }
                editableItem.addhpb();
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Add Hot Potato Book", Material.BOOK, (short)0, 1, (Object)ChatColor.GRAY + "Add hot potato book to item.");
            }
        });
    }
}
