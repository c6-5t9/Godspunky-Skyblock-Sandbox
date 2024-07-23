package net.hypixel.skyblock.gui;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.InventoryView;
import java.util.Arrays;
import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.User;

public class CookieGUI extends GUI
{
    public CookieGUI(final String query, final int page) {
        super("Booster Cookie", 36);
        this.fill(CookieGUI.BLACK_STAINED_GLASS_PANE);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        String text_ = "&7Status";
        if (0L < PlayerUtils.getCookieDurationTicks(player)) {
            text_ = "&7Duration";
        }
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SKYBLOCK_MENU.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aGo Back"), Material.ARROW, (short)0, 1, (Object)ChatColor.GRAY + "To SkyBlock Menu");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.ANVIL.getGUI().open((Player)e.getWhoClicked());
                }
                else {
                    ((Player)e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }
            
            @Override
            public int getSlot() {
                return 30;
            }
            
            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to check it out!";
                }
                return SUtil.getStack(Sputnik.trans("&6Anvil"), Material.ANVIL, (short)0, 1, (Object)ChatColor.DARK_GRAY + "/av", "", (Object)ChatColor.GRAY + "Access an Anvil from anywhere in", (Object)ChatColor.GRAY + "SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    e.getWhoClicked().sendMessage((Object)ChatColor.RED + "Disabled bruh.");
                }
                else {
                    ((Player)e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }
            
            @Override
            public int getSlot() {
                return 32;
            }
            
            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to check it out!";
                }
                return SUtil.getStack(Sputnik.trans("&6Auction House"), Material.GOLD_BARDING, (short)0, 1, (Object)ChatColor.DARK_GRAY + "/auh", "", (Object)ChatColor.GRAY + "Access the Auction House menu", (Object)ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.TRASH.getGUI().open((Player)e.getWhoClicked());
                }
                else {
                    ((Player)e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }
            
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to open!";
                }
                return SUtil.getStack(Sputnik.trans("&6Trash Bin"), Material.CAULDRON_ITEM, (short)0, 1, (Object)ChatColor.DARK_GRAY + "/bin", "", (Object)ChatColor.GRAY + "Access your personal Trash Bin", (Object)ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.FARM_MERCHANT.getGUI().open((Player)e.getWhoClicked());
                }
                else {
                    ((Player)e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }
            
            @Override
            public int getSlot() {
                return 29;
            }
            
            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to open!";
                }
                return SUtil.getStack(Sputnik.trans("&6Farm Merchant"), Material.APPLE, (short)0, 1, (Object)ChatColor.DARK_GRAY + "/fm", "", (Object)ChatColor.GRAY + "Access the Farm Merchant menu", (Object)ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(11, SUtil.enchant(SUtil.getStack((Object)ChatColor.GOLD + "Booster Cookie", Material.COOKIE, (short)0, 1, (Object)ChatColor.GRAY + "Aquire booster cookies from", (Object)ChatColor.GRAY + "the community shop in the hub.", "", Sputnik.trans("&dCookie Buff:"), Sputnik.trans("&8\u25b6 &b+35% &7Bonus Combat XP"), Sputnik.trans("&8\u25b6 &b+30\u272f &7Bonus Magic Find"), Sputnik.trans("&8\u25b6 &c+100\u2741 &7Bonus Strength"), Sputnik.trans("&8\u25b6 &a+200\u2748 &7Bonus Defense"), Sputnik.trans("&8\u25b6 &9+25\u2620 &7Bonus Crit Damage"), Sputnik.trans("&8\u25b6 &c+35\u2afd &7Bonus Ferocity"), Sputnik.trans("&8\u25b6 &b+2000\u270e &7Bonus Intelligence"), Sputnik.trans("&8\u25b6 &7Keep &6coins &7and &deffects &7on death"), Sputnik.trans("&8\u25b6 &7Access to &6/auh &7and &6/fm"), Sputnik.trans("&8\u25b6 &7Access to &6/av &7and &6/bin &7(Trash Bin)"), Sputnik.trans("&8\u25b6 &7A shiny &e\u272a &6Badge &7on your &aname tag."), "", Sputnik.trans("&7" + text_ + ": " + PlayerUtils.getCookieDurationDisplayGUI(player)), "", Sputnik.trans("&8NOTE: All effects listed above given"), Sputnik.trans("&8by the cookie will NOT stack!"))));
        this.set(13, SUtil.getStack((Object)ChatColor.AQUA + "Bits", Material.DIAMOND, (short)0, 1, (Object)ChatColor.GRAY + "You can earn Bits from killing", (Object)ChatColor.GRAY + "mobs with melee weapons. Only.", "", Sputnik.trans("&7Bits Purse: &b" + PlaceholderAPI.setPlaceholders(player, "%royaleeconomy_balance_purse%")), "", Sputnik.trans("&8Bits can be used to purchase Booster"), Sputnik.trans("&8Cookie and various items in the Community"), Sputnik.trans("&8Shop.")));
        this.set(15, SUtil.enchant(SUtil.getStack((Object)ChatColor.GOLD + "Fame Rank", Material.GOLD_HELMET, (short)0, 1, (Object)ChatColor.GRAY + "This feature is not available.", "", "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "NOT COMING SOON!", "", Sputnik.trans("&8Not planned release."))));
        new BukkitRunnable() {
            public void run() {
                String text_ = "&7Status";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text_ = "&7Duration";
                }
                if (CookieGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final InventoryView stackInventory = player.getOpenInventory();
                final ItemStack craftStack = stackInventory.getItem(11);
                final ItemMeta meta = craftStack.getItemMeta();
                meta.setLore(Arrays.asList((Object[])new String[] { (Object)ChatColor.GRAY + "Aquire booster cookies from", (Object)ChatColor.GRAY + "the community shop in the hub.", "", Sputnik.trans("&dCookie Buff:"), Sputnik.trans("&8\u25b6 &b+35% &7Bonus Combat XP"), Sputnik.trans("&8\u25b6 &b+30\u272f &7Bonus Magic Find"), Sputnik.trans("&8\u25b6 &c+100\u2741 &7Bonus Strength"), Sputnik.trans("&8\u25b6 &a+200\u2748 &7Bonus Defense"), Sputnik.trans("&8\u25b6 &9+25\u2620 &7Bonus Crit Damage"), Sputnik.trans("&8\u25b6 &c+35\u2afd &7Bonus Ferocity"), Sputnik.trans("&8\u25b6 &b+2000\u270e &7Bonus Intelligence"), Sputnik.trans("&8\u25b6 &7Keep &6coins &7and &deffects &7on death"), Sputnik.trans("&8\u25b6 &7Access to &6/auh &7and &6/fm"), Sputnik.trans("&8\u25b6 &7Access to &6/av &7and &6/bin &7(Trash Bin)"), Sputnik.trans("&8\u25b6 &7A shiny &e\u272a &6Badge &7on your &aname tag."), "", Sputnik.trans("&7" + text_ + ": " + PlayerUtils.getCookieDurationDisplayGUI(player)), "", Sputnik.trans("&8NOTE: All effects listed above given"), Sputnik.trans("&8by the cookie will NOT stack!") }));
                craftStack.setItemMeta(meta);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
    
    public CookieGUI(final String query) {
        this(query, 1);
    }
    
    public CookieGUI(final int page) {
        this("", page);
    }
    
    public CookieGUI() {
        this(1);
    }
}
