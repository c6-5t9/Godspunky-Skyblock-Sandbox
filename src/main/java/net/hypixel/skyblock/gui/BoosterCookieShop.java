package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.User;

public class BoosterCookieShop extends GUI
{
    public BoosterCookieShop() {
        super("Community Shop", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(BoosterCookieShop.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 1;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "City Project", Material.GOLD_BARDING, (short)0, 1, (Object)ChatColor.GRAY + "Participate with the whole", (Object)ChatColor.GRAY + "SkyBlock community to upgrade", (Object)ChatColor.GRAY + "the village and more.", " ", (Object)ChatColor.AQUA + "Contribute " + (Object)ChatColor.GRAY + "to various", (Object)ChatColor.GRAY + "project to obtain unique", (Object)ChatColor.GRAY + "perks.", "", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 2;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.LIGHT_PURPLE + "Account & Profile Upgrades", Material.HOPPER, (short)0, 1, (Object)ChatColor.GRAY + "Upgrade your current profile and", (Object)ChatColor.GRAY + "your SkyBlock account with", (Object)ChatColor.GRAY + "permanent upgrades.", " ", (Object)ChatColor.GRAY + "Profile: " + (Object)ChatColor.DARK_GRAY + "Nothing going on!", (Object)ChatColor.GRAY + "Account:" + (Object)ChatColor.DARK_GRAY + "None underway!", "", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 3;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Booster Cookie", Material.COOKIE, (short)0, 1, (Object)ChatColor.GRAY + "Obtain a temporary buff", (Object)ChatColor.GRAY + "letting you earn " + (Object)ChatColor.AQUA + "bits", (Object)ChatColor.GRAY + "as well as " + (Object)ChatColor.LIGHT_PURPLE + "tons of perks.", " ", (Object)ChatColor.GREEN + "Currently selected!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 4;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.AQUA + "Bits Shop", Material.DIAMOND, (short)0, 1, (Object)ChatColor.GRAY + "Spend" + (Object)ChatColor.AQUA + "bits" + (Object)ChatColor.GRAY + "on a variety of", (Object)ChatColor.GRAY + "powerful items.", "", (Object)ChatColor.GRAY + "Earn bits from " + (Object)ChatColor.GOLD + "Booster Cookie.", " ", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 5;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(ChatColor.translateAlternateColorCodes('&', "&6\u2668 &c&lFIRE &cSales &6\u2668"), Material.BLAZE_POWDER, (short)0, 1, (Object)ChatColor.GRAY + "Acquire " + (Object)ChatColor.GOLD + "exclusive" + (Object)ChatColor.GRAY + "cosmetics", (Object)ChatColor.GRAY + "which are only available in", (Object)ChatColor.RED + "limited quantity" + (Object)ChatColor.GRAY + " across all", (Object)ChatColor.GRAY + "of SkyBlock", " ", (Object)ChatColor.DARK_GRAY + "No ongoing sale!", "", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 7;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.YELLOW + "GodSpunky Ranks", Material.EMERALD, (short)0, 1, (Object)ChatColor.GRAY + "Browse the SkyBlock perks of", (Object)ChatColor.GRAY + "our " + (Object)ChatColor.YELLOW + "server-wide" + (Object)ChatColor.GRAY + "ranks such", (Object)ChatColor.GRAY + "as the " + (Object)ChatColor.GOLD + "[MVP" + (Object)ChatColor.RED + "++" + (Object)ChatColor.GOLD + "]" + (Object)ChatColor.GRAY + " rank.", (Object)ChatColor.GRAY + "of SkyBlock", " ", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)7, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)7, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)7, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)7, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 16;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)7, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_GRAY + "\u25b2 " + (Object)ChatColor.GRAY + "Categories", Material.STAINED_GLASS_PANE, (short)5, 1, (Object)ChatColor.DARK_GRAY + "\u25bc " + (Object)ChatColor.GRAY + "Items");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final ItemStack stack = SItem.of(SMaterial.HIDDEN_BOOSTER_COOKIE).getStack();
                Sputnik.smartGiveItem(stack, player);
            }
            
            @Override
            public int getSlot() {
                return 29;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Single Cookie", Material.COOKIE, (short)0, 1, "", (Object)ChatColor.GOLD + "Booster Cookie " + (Object)ChatColor.DARK_GRAY + "x1", ChatColor.translateAlternateColorCodes('&', "&7Consume to gain the &dCookie Buff"), ChatColor.translateAlternateColorCodes('&', "&7for &b5 &7days:"), " ", ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Ability to gain &bBis&7!"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+20% &7 Skill XP"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+15 &7Magic Find"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Keep &6coins &7and &beffects &7on death"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &ePermafly &7 on private islands"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access &6/ah &7and &6/bazaar &7 anywhere"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Sell items directly to the trades menu"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7AFK &aimmunity &7on your island"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Toggle specific &dpotion effects"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access to &6/anvil &7and &6/etable"), "", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "LEGENDARY", "", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.GREEN + "FREE", "", (Object)ChatColor.GRAY + "You have: ");
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
                return SUtil.getStack((Object)ChatColor.GOLD + "Hafl-Dozen Cookie", Material.COOKIE, (short)0, 1, "", (Object)ChatColor.GOLD + "Booster Cookie " + (Object)ChatColor.DARK_GRAY + "x6", ChatColor.translateAlternateColorCodes('&', "&7Consume to gain the &dCookie Buff"), ChatColor.translateAlternateColorCodes('&', "&7for &b5 &7days:"), " ", ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Ability to gain &bBis&7!"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+20% &7 Skill XP"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+15 &7Magic Find"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Keep &6coins &7and &beffects &7on death"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &ePermafly &7 on private islands"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access &6/ah &7and &6/bazaar &7 anywhere"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Sell items directly to the trades menu"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7AFK &aimmunity &7on your island"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Toggle specific &dpotion effects"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access to &6/anvil &7and &6/etable"), "", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "LEGENDARY", "", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.GREEN + "FREE", "", (Object)ChatColor.GRAY + "You have: 0", (Object)ChatColor.RED + "Coming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "A Dozen Cookie", Material.COOKIE, (short)0, 1, "", (Object)ChatColor.GOLD + "Booster Cookie " + (Object)ChatColor.DARK_GRAY + "x12", ChatColor.translateAlternateColorCodes('&', "&7Consume to gain the &dCookie Buff"), ChatColor.translateAlternateColorCodes('&', "&7for &b5 &7days:"), " ", ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Ability to gain &bBis&7!"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+20% &7 Skill XP"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &b+15 &7Magic Find"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Keep &6coins &7and &beffects &7on death"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &ePermafly &7 on private islands"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access &6/ah &7and &6/bazaar &7 anywhere"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Sell items directly to the trades menu"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7AFK &aimmunity &7on your island"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Toggle specific &dpotion effects"), ChatColor.translateAlternateColorCodes('&', "&8\u25ba &7Access to &6/anvil &7and &6/etable"), "", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "LEGENDARY", "", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.GREEN + "3,900 SkyBlock Gems", "", (Object)ChatColor.GRAY + "You have: 0", (Object)ChatColor.RED + "Coming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.AQUA + "https://godspunky.store/");
            }
            
            @Override
            public int getSlot() {
                return 49;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Community Shop", Material.EMERALD, (short)0, 1, "", (Object)ChatColor.DARK_GRAY + "Elizabeth", "", ChatColor.translateAlternateColorCodes('&', "&7Gems: "), ChatColor.translateAlternateColorCodes('&', "&8Purchase on godspunky.store"), " ", ChatColor.translateAlternateColorCodes('&', "&7Bits: &bN/A"), ChatColor.translateAlternateColorCodes('&', "&8Earn from Booster Cookies!"), "", ChatColor.translateAlternateColorCodes('&', "&7Fame Rank: &eNew player"), ChatColor.translateAlternateColorCodes('&', "&8rank up by spending gems &"), ChatColor.translateAlternateColorCodes('&', "&8bits!"), "", (Object)ChatColor.GRAY + "Store: " + (Object)ChatColor.AQUA + "godspunky.store", (Object)ChatColor.YELLOW + "Click to get link!");
            }
        });
    }
}
