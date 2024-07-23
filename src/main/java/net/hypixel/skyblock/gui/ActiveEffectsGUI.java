package net.hypixel.skyblock.gui;

import java.util.Iterator;
import java.util.List;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.Collections;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Collection;
import net.hypixel.skyblock.features.potion.ActivePotionEffect;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.user.User;

public class ActiveEffectsGUI extends GUI
{
    private static final int[] INTERIOR;
    private int page;
    
    public ActiveEffectsGUI(final int page) {
        super("Active Effects", 54);
        this.page = page;
    }
    
    public ActiveEffectsGUI() {
        this(1);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.border(ActiveEffectsGUI.BLACK_STAINED_GLASS_PANE);
        final PaginationList<ActivePotionEffect> paged = new PaginationList<ActivePotionEffect>(28);
        paged.addAll((Collection)user.getEffects());
        if (paged.size() == 0) {
            this.page = 0;
        }
        final int finalPage = this.page;
        this.title = "(" + this.page + "/" + paged.getPageCount() + ") Active Effects";
        if (this.page > 1) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new ActiveEffectsGUI(finalPage - 1).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 45;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Go Back");
                }
            });
        }
        if (this.page != paged.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new ActiveEffectsGUI(finalPage + 1).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Next Page");
                }
            });
        }
        this.set(4, SUtil.getStack((Object)ChatColor.GREEN + "Active Effects", Material.POTION, (short)0, 1, (Object)ChatColor.GRAY + "View and manage all of your", (Object)ChatColor.GRAY + "active potion effects.", " ", (Object)ChatColor.GRAY + "Drink Potions or splash them", (Object)ChatColor.GRAY + "on the ground to buff yourself!", " ", (Object)ChatColor.GRAY + "Currently Active: " + (Object)ChatColor.YELLOW + user.getEffects().size()));
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (Object)ChatColor.GRAY + "To SkyBlock Menu"));
        this.set(GUIClickableItem.getCloseItem(49));
        final List<ActivePotionEffect> p = paged.getPage(this.page);
        if (p == null) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            final int slot = ActiveEffectsGUI.INTERIOR[i];
            final ActivePotionEffect effect = (ActivePotionEffect)p.get(i);
            final List<String> lore = (List<String>)new ArrayList((Collection)Collections.singletonList((Object)" "));
            for (final String line : SUtil.splitByWordAndLength(effect.getEffect().getDescription(), 20, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + line));
            }
            lore.add((Object)" ");
            lore.add((Object)((Object)ChatColor.GRAY + "Remaining: " + (Object)ChatColor.WHITE + effect.getRemainingDisplay()));
            lore.add((Object)" ");
            lore.add((Object)SUtil.findPotionRarity(effect.getEffect().getLevel()).getDisplay());
            this.set(slot, SUtil.getStack(effect.getEffect().getType().getName() + " " + SUtil.toRomanNumeral(effect.getEffect().getLevel()), Material.POTION, effect.getEffect().getType().getColor().getData(), 1, lore));
        }
        new BukkitRunnable() {
            public void run() {
                if (ActiveEffectsGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    return;
                }
                new ActiveEffectsGUI(ActiveEffectsGUI.this.page).open(player);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
