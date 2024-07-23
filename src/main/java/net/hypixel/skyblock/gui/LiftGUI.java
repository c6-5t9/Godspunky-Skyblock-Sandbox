package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class LiftGUI extends GUI
{
    public LiftGUI() {
        super("Lift", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(LiftGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 150.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Gunpowder Mines", Material.GOLD_INGOT, (short)0, 1, (Object)ChatColor.GRAY + "Teleports you to the " + (Object)ChatColor.AQUA + "Gunpowder", (Object)ChatColor.AQUA + "Mines" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 121.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Lapis Quarry", Material.INK_SACK, (short)4, 1, (Object)ChatColor.GRAY + "Teleports you to the " + (Object)ChatColor.AQUA + "Lapis", (Object)ChatColor.AQUA + "Quarry" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 101.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Pigmen's Den", Material.REDSTONE, (short)0, 1, (Object)ChatColor.GRAY + "Teleports you to the " + (Object)ChatColor.AQUA + "Pigmen's", (Object)ChatColor.AQUA + "Den" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 66.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 16;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Slimehill", Material.EMERALD, (short)0, 1, (Object)ChatColor.GRAY + "Teleports you to the ", (Object)ChatColor.AQUA + "Slimehill" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 38.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Diamond Reserve", Material.DIAMOND, (short)0, 1, (Object)ChatColor.GRAY + "Teleports you to the " + (Object)ChatColor.AQUA + "Diamond", (Object)ChatColor.AQUA + "Reserve" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), 44.5, 13.0, -559.5, 90.0f, 0.0f));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 30;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Obsidian Sanctuary", Material.OBSIDIAN, (short)0, 1, (Object)ChatColor.GRAY + "Teleports you to the " + (Object)ChatColor.AQUA + "Obsidian", (Object)ChatColor.AQUA + "Sanctuary" + (Object)ChatColor.GRAY + "!", " ", (Object)ChatColor.YELLOW + "Click to travel!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                player.sendMessage((Object)ChatColor.RED + "The Dwarven Mines are not available yet!");
            }
            
            @Override
            public int getSlot() {
                return 32;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Dwarven Mines", Material.PRISMARINE, (short)0, 1, (Object)ChatColor.RED + "Not available yet!");
            }
        });
    }
}
