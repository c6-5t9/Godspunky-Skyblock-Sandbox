package net.hypixel.skyblock.gui.menu.Items;

import org.bukkit.event.inventory.InventoryCloseEvent;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.gui.GUIItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.gui.GUIClickableItem;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.gui.GUI;

public class HexModifiersGUI extends GUI
{
    SItem upgradeableItem;
    public boolean forceclose;
    
    public HexModifiersGUI(final SItem item) {
        super("The Hex -> Modifiers", 54);
        this.forceclose = false;
        this.fill(HexModifiersGUI.BLACK_STAINED_GLASS_PANE);
        this.upgradeableItem = item;
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                HexModifiersGUI.this.upgradeableItem = item;
                item.setRecombobulated(true);
                final Player player = (Player)e.getWhoClicked();
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10.0f, 2.0f);
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou recombobulated your " + item.getFullName() + "!"));
            }
            
            @Override
            public int getSlot() {
                return 23;
            }
            
            @Override
            public ItemStack getItem() {
                return SItem.of(SMaterial.RECOMBOBULATOR_3000).getStack();
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lSUCCESS! &r&dYou modified your " + item.getFullName() + "!"));
                HexModifiersGUI.this.forceclose = true;
                final Player p = (Player)e.getWhoClicked();
                p.playSound(p.getLocation(), Sound.ANVIL_USE, 10.0f, 1.0f);
                new HexGUI(p.getPlayer(), item).open(p);
                HexModifiersGUI.this.upgradeableItem = null;
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aApply Modifiers"), Material.ANVIL, (short)0, 1, Sputnik.trans5("&7Apply miscellaneous item", "&7modifiers like the", "&6Recombobulator 3000&7,", "&5Wither Scrolls&7, and &cMaster", "&cStars&7!"));
            }
        });
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(19, item.getStack());
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        if (!this.forceclose && this.upgradeableItem != null) {
            e.getPlayer().getInventory().addItem(new ItemStack[] { this.upgradeableItem.getStack() });
        }
    }
}
