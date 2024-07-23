package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.inventory.InventoryCloseEvent;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import net.hypixel.skyblock.user.User;
import org.bukkit.Material;
import org.bukkit.ChatColor;

public class QuiverGUI extends GUI
{
    public QuiverGUI() {
        super("Quiver", 36);
        this.fill(QuiverGUI.BLACK_STAINED_GLASS_PANE, 27, 35);
        this.set(GUIClickableItem.getCloseItem(31));
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, e.getPlayer(), (Object)ChatColor.GREEN + "Go Back", 30, Material.ARROW, new String[0]));
        final User user = User.getUser(e.getPlayer().getUniqueId());
        final Inventory inventory = e.getInventory();
        for (final Map.Entry<SMaterial, Integer> entry : user.getQuiver().entrySet()) {
            inventory.addItem(new ItemStack[] { SUtil.setStackAmount(SItem.of((SMaterial)entry.getKey()).getStack(), (int)entry.getValue()) });
        }
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player p = (Player)e.getWhoClicked();
                if (p == null) {
                    return;
                }
                if (PlayerUtils.getCookieDurationTicks(p) <= 0L) {
                    p.sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }
                for (int a = 0; a < 27; ++a) {
                    e.getInventory().setItem(a, (ItemStack)null);
                }
                p.closeInventory();
                p.sendMessage(Sputnik.trans("&aSuccessfully cleared your Quiver!"));
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 34;
            }
            
            @Override
            public ItemStack getItem() {
                ItemStack isBuilder = new ItemStack(Material.BEDROCK, 1);
                String a = (Object)ChatColor.YELLOW + "Click to proceed";
                if (PlayerUtils.getCookieDurationTicks(player) <= 0L) {
                    a = (Object)ChatColor.RED + "Requires Cookie Buff!";
                }
                isBuilder = SUtil.getStack((Object)ChatColor.RED + "Clear Quiver", Material.LAVA_BUCKET, (short)0, 1, (Object)ChatColor.GRAY + "Click to clear your Quiver", (Object)ChatColor.GRAY + "instantly.", " ", a);
                return isBuilder;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player p = (Player)e.getWhoClicked();
                if (p == null) {
                    return;
                }
                if (PlayerUtils.getCookieDurationTicks(p) <= 0L) {
                    p.sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }
                for (int a = 0; a < 27; ++a) {
                    final ItemStack arrow = SItem.of(SMaterial.ARROW).getStack();
                    arrow.setAmount(64);
                    e.getInventory().setItem(a, arrow);
                }
                p.closeInventory();
                p.sendMessage(Sputnik.trans("&aSuccessfully filled your Quiver!"));
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 35;
            }
            
            @Override
            public ItemStack getItem() {
                ItemStack isBuilder = new ItemStack(Material.BEDROCK, 1);
                String a = (Object)ChatColor.YELLOW + "Click to proceed";
                if (PlayerUtils.getCookieDurationTicks(player) <= 0L) {
                    a = (Object)ChatColor.RED + "Requires Cookie Buff!";
                }
                isBuilder = SUtil.getStack((Object)ChatColor.GREEN + "Fill Quiver", Material.CHEST, (short)0, 1, (Object)ChatColor.GRAY + "Click to fill your Quiver", (Object)ChatColor.GRAY + "instantly.", " ", a);
                return isBuilder;
            }
        });
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        final User user = User.getUser(e.getPlayer().getUniqueId());
        final Inventory inventory = e.getInventory();
        user.clearQuiver();
        for (int i = 0; i < 27; ++i) {
            final ItemStack stack = inventory.getItem(i);
            SItem sItem = SItem.find(stack);
            if (sItem == null) {
                sItem = SItem.of(stack);
                if (sItem == null) {
                    continue;
                }
            }
            user.addToQuiver(sItem.getType(), stack.getAmount());
        }
        if (user != null) {
            if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                user.configsave();
            }
            else {
                user.save();
            }
        }
    }
}
