package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.item.pet.Pet;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.InventoryView;
import java.util.Arrays;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Iterator;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import net.hypixel.skyblock.features.calendar.SkyBlockCalendar;
import org.bukkit.Sound;
import net.hypixel.skyblock.features.collection.ItemCollectionCategory;
import net.hypixel.skyblock.features.collection.ItemCollection;
import org.bukkit.Material;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;

public class SkyBlockMenuGUI extends GUI
{
    public SkyBlockMenuGUI() {
        super("SkyBlock Menu", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(SkyBlockMenuGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SKYBLOCK_PROFILE.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                ItemStack itemstack = null;
                Double visualcap = statistics.getCritChance().addAll() * 100.0;
                if (visualcap > 100.0) {
                    visualcap = 100.0;
                }
                final String feroDisplay = "";
                itemstack = ((statistics.getFerocity().addAll() > 0.0) ? SUtil.getSkullStack((Object)ChatColor.GREEN + "Your SkySim Profile", player.getName(), 1, (Object)ChatColor.RED + "  \u2764 Health " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getMaxHealth().addAll().intValue()) + " HP", (Object)ChatColor.GREEN + "  \u2748 Defense " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getDefense().addAll().intValue()), (Object)ChatColor.RED + "  \u2741 Strength " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getStrength().addAll().intValue()), (Object)ChatColor.WHITE + "  \u2726 Speed " + SUtil.commaify((statistics.getSpeed().addAll() * 100.0).intValue()), (Object)ChatColor.BLUE + "  \u2623 Crit Chance " + (Object)ChatColor.WHITE + SUtil.commaify(visualcap.intValue()) + "%", (Object)ChatColor.BLUE + "  \u2620 Crit Damage " + (Object)ChatColor.WHITE + SUtil.commaify((statistics.getCritDamage().addAll() * 100.0).intValue()) + "%", (Object)ChatColor.AQUA + "  \u270e Intelligence " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getIntelligence().addAll().intValue()), (Object)ChatColor.YELLOW + "  \u2694 Bonus Attack Speed " + (Object)ChatColor.WHITE + SUtil.commaify(Math.min(100.0, (double)statistics.getAttackSpeed().addAll()).intValue()) + "%", (Object)ChatColor.DARK_AQUA + "  \u03b1 Sea Creature Chance " + (Object)ChatColor.RED + "\u2717", (Object)ChatColor.LIGHT_PURPLE + "  \u2663 Pet Luck " + (Object)ChatColor.RED + "\u2717", (Object)ChatColor.AQUA + "  \u272f Magic Find " + (Object)ChatColor.WHITE + SUtil.commaify((statistics.getMagicFind().addAll() * 100.0).intValue()), (Object)ChatColor.RED + "  \u2afd Ferocity " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getFerocity().addAll().intValue()), (Object)ChatColor.RED + "  \u0e51 Ability Damage " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getAbilityDamage().addAll().intValue()) + "%", " ", (Object)ChatColor.YELLOW + "Click to view your profile!") : SUtil.getSkullStack((Object)ChatColor.GREEN + "Your SkySim Profile", player.getName(), 1, (Object)ChatColor.RED + "  \u2764 Health " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getMaxHealth().addAll().intValue()) + " HP", (Object)ChatColor.GREEN + "  \u2748 Defense " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getDefense().addAll().intValue()), (Object)ChatColor.RED + "  \u2741 Strength " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getStrength().addAll().intValue()), (Object)ChatColor.WHITE + "  \u2726 Speed " + SUtil.commaify((statistics.getSpeed().addAll() * 100.0).intValue()), (Object)ChatColor.BLUE + "  \u2623 Crit Chance " + (Object)ChatColor.WHITE + SUtil.commaify(visualcap.intValue()) + "%", (Object)ChatColor.BLUE + "  \u2620 Crit Damage " + (Object)ChatColor.WHITE + SUtil.commaify((statistics.getCritDamage().addAll() * 100.0).intValue()) + "%", (Object)ChatColor.AQUA + "  \u270e Intelligence " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getIntelligence().addAll().intValue()), (Object)ChatColor.YELLOW + "  \u2694 Bonus Attack Speed " + (Object)ChatColor.WHITE + SUtil.commaify(Math.min(100.0, (double)statistics.getAttackSpeed().addAll()).intValue()) + "%", (Object)ChatColor.DARK_AQUA + "  \u03b1 Sea Creature Chance " + (Object)ChatColor.RED + "\u2717", (Object)ChatColor.LIGHT_PURPLE + "  \u2663 Pet Luck " + (Object)ChatColor.RED + "\u2717", (Object)ChatColor.AQUA + "  \u272f Magic Find " + (Object)ChatColor.WHITE + SUtil.commaify((statistics.getMagicFind().addAll() * 100.0).intValue()), (Object)ChatColor.RED + "  \u0e51 Ability Damage " + (Object)ChatColor.WHITE + SUtil.commaify(statistics.getAbilityDamage().addAll().intValue()) + "%", " ", (Object)ChatColor.YELLOW + "Click to view your profile!"));
                return itemstack;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SKILL_MENU.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 19;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Your Skills", Material.DIAMOND_SWORD, (short)0, 1, (Object)ChatColor.GRAY + "View your Skill progression and", (Object)ChatColor.GRAY + "rewards.", " ", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        final String[] progress = ItemCollection.getProgress(player, null);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.COLLECTION_MENU.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 20;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Collection", Material.PAINTING, (short)0, 1, (Object)ChatColor.GRAY + "View all of the items available", (Object)ChatColor.GRAY + "in SkyBlock. Collect more of an", (Object)ChatColor.GRAY + "item to unlock rewards on your", (Object)ChatColor.GRAY + "way to becoming a master of", (Object)ChatColor.GRAY + "SkyBlock!", " ", (Object)ChatColor.YELLOW + "Click to view!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0f, 0.0f);
                player.openInventory(player.getEnderChest());
            }
            
            @Override
            public int getSlot() {
                return 25;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Ender Chest", Material.ENDER_CHEST, (short)0, 1, (Object)ChatColor.GRAY + "Store global items that you want", (Object)ChatColor.GRAY + "to access at any time from", (Object)ChatColor.GRAY + "anywhere here.", " ", (Object)ChatColor.YELLOW + "Click to open!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "Calender coming soon!");
            }
            
            @Override
            public int getSlot() {
                return 24;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Calendar and Events", Material.WATCH, (short)0, 1, (Object)ChatColor.GRAY + "In-game Calendar", (Object)ChatColor.GRAY + SkyBlockCalendar.getMonthName() + " " + SUtil.ntify(SkyBlockCalendar.getDay()), " ", (Object)ChatColor.YELLOW + "Coming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "Quests coming soon!");
            }
            
            @Override
            public int getSlot() {
                return 23;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Quest Log", Material.BOOK_AND_QUILL, (short)0, 1, (Object)ChatColor.GRAY + "View your active quests.", " ", (Object)ChatColor.YELLOW + "Coming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "Coming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 22;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Trades", Material.EMERALD, (short)0, 1, (Object)ChatColor.GRAY + "View your available Trades.", " ", (Object)ChatColor.YELLOW + "Coming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new RecipeBookListGUI(player).open(player);
            }
            
            @Override
            public int getSlot() {
                return 21;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Recipe Book", Material.BOOK, (short)0, 1, (Object)ChatColor.GRAY + "View your available crafting.", (Object)ChatColor.GRAY + "recipes", " ", (Object)ChatColor.YELLOW + "Click to open");
            }
        });
        if (user.getEffects().size() > 0) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    GUIType.ACTIVE_EFFECTS.getGUI().open(player);
                }
                
                @Override
                public int getSlot() {
                    return 29;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Active Effects", Material.POTION, (short)0, 1, (Object)ChatColor.GRAY + "View and manage all of your", (Object)ChatColor.GRAY + "active potion effects.", " ", (Object)ChatColor.GRAY + "Drink Potions or splash them", (Object)ChatColor.GRAY + "on the ground to buff yourself!", " ", (Object)ChatColor.GRAY + "Currently Active: " + (Object)ChatColor.YELLOW + user.getEffects().size());
                }
            });
        }
        if (user.getPets().size() > 0) {
            final Pet.PetItem active = user.getActivePet();
            String name;
            if (active == null) {
                name = (Object)ChatColor.RED + "None";
            }
            else {
                name = (Object)active.getRarity().getColor() + active.getType().getDisplayName(active.getType().getData());
            }
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    GUIType.PETS.getGUI().open(player);
                }
                
                @Override
                public int getSlot() {
                    return 30;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Pets", Material.BONE, (short)0, 1, (Object)ChatColor.GRAY + "View and manage all of your", (Object)ChatColor.GRAY + "Pets.", " ", (Object)ChatColor.GRAY + "Level up your pets faster by", (Object)ChatColor.GRAY + "gaining XP in their favorite", (Object)ChatColor.GRAY + "skill!", " ", (Object)ChatColor.GRAY + "Selected pet: " + name, " ", (Object)ChatColor.YELLOW + "Click to view!");
                }
            });
        }
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.CRAFTING_TABLE.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Crafting Table", Material.WORKBENCH, (short)0, 1, (Object)ChatColor.GRAY + "Opens the crafting grid.", " ", (Object)ChatColor.YELLOW + "Click to open!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "Wardrobe is being reworked due to serve performance issues! If you lost your armors set stored inside, we're sorry, you can ask staff members to re-make it for you.");
            }
            
            @Override
            public int getSlot() {
                return 32;
            }
            
            @Override
            public ItemStack getItem() {
                final ItemStack i = new ItemStack(Material.LEATHER_CHESTPLATE);
                final LeatherArmorMeta im1 = (LeatherArmorMeta)i.getItemMeta();
                im1.setDisplayName((Object)ChatColor.GREEN + "Wardrobe");
                im1.setColor(Color.fromRGB(127, 63, 178));
                final ArrayList<String> lore = (ArrayList<String>)new ArrayList();
                lore.add((Object)((Object)ChatColor.GRAY + "Store armors and quickly"));
                lore.add((Object)((Object)ChatColor.GRAY + "swap between them!"));
                lore.add((Object)((Object)ChatColor.GRAY + ""));
                lore.add((Object)((Object)ChatColor.RED + "Disabled!"));
                im1.setLore((List)lore);
                i.setItemMeta((ItemMeta)im1);
                return i;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.BANKER.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Personal Bank", "bf75d1b785d18d47b3ea8f0a7e0fd4a1fae9e7d323cf3b138c8c78cfe24ee59", 1, (Object)ChatColor.GRAY + "Contact your Banker from", (Object)ChatColor.GRAY + "anywhere", (Object)ChatColor.GRAY + "Cooldown: " + (Object)ChatColor.GREEN + "No Cooldown", (Object)ChatColor.GRAY + " ", (Object)ChatColor.GRAY + "Banker Status:", (Object)ChatColor.GREEN + "Avalible", " ", (Object)ChatColor.YELLOW + "Click to open!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.QUIVER.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 53;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Quiver", "4cb3acdc11ca747bf710e59f4c8e9b3d949fdd364c6869831ca878f0763d1787", 1, (Object)ChatColor.GRAY + "A masterfully crafted Quiver", (Object)ChatColor.GRAY + "which holds any kind of", (Object)ChatColor.GRAY + "projectile you can think of!", " ", (Object)ChatColor.YELLOW + "Click to open!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new AccessoryReforges().open((Player)e.getWhoClicked());
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&6Accessory Bag Reforge"), Material.ANVIL, (short)0, 1, Sputnik.trans("&7A special Anvil which can reforge your accessories"));
            }
            
            @Override
            public int getSlot() {
                return 52;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player = (Player)e.getWhoClicked();
                final List<SItem> accessories = PlayerUtils.getAccessories(player);
                if (accessories == null) {
                    player.sendMessage(SUtil.color("&cYour accessory bag is empty!"));
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                    return;
                }
                for (final SItem accessory : accessories) {
                    accessory.setRecombobulated(true);
                }
                player.sendMessage(SUtil.color("&aYou recombobulated all your accessories!"));
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 10.0f, 2.0f);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack(Sputnik.trans("&aRecombobulate All"), "57ccd36dc8f72adcb1f8c8e61ee82cd96ead140cf2a16a1366be9b5a8e3cc3fc", 1, Sputnik.trans4("&7Automatically recombobulates all", "&7your &dAccessories&7 in one click!", "&7", "&eClick to Upgrade!"));
            }
            
            @Override
            public int getSlot() {
                return 44;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.chat("/warp");
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "Fast Travel", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56", 1, (Object)ChatColor.GRAY + "Teleport to islands that are", (Object)ChatColor.GRAY + "available to the public.", " ", (Object)ChatColor.YELLOW + "Click to pick location!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().sendMessage((Object)ChatColor.RED + "This feature is under development!");
            }
            
            @Override
            public int getSlot() {
                return 50;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Settings", Material.REDSTONE_TORCH_ON, (short)0, 1, Sputnik.trans("&c&lCOMING SOON!"));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.COOKIE_GUI.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 49;
            }
            
            @Override
            public ItemStack getItem() {
                String text_ = "&7Status";
                if (PlayerUtils.getCookieDurationTicks(player) > 0L) {
                    text_ = "&7Duration";
                }
                return SUtil.enchant(SUtil.getStack((Object)ChatColor.GOLD + "Booster Cookie", Material.COOKIE, (short)0, 1, Sputnik.trans("&7Obtain the &dCookie Buff"), Sputnik.trans("&7from booster cookies in the"), Sputnik.trans("&7hub's community shop."), " ", Sputnik.trans(text_ + "&7: " + PlayerUtils.getCookieDurationDisplayGUI(player)), " ", (Object)ChatColor.YELLOW + "Click to get all the info!"));
            }
        });
        new BukkitRunnable() {
            public void run() {
                String text_ = "&7Status";
                if (PlayerUtils.getCookieDurationTicks(player) > 0L) {
                    text_ = "&7Duration";
                }
                if (SkyBlockMenuGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final InventoryView stackInventory = player.getOpenInventory();
                final ItemStack craftStack = stackInventory.getItem(49);
                final ItemMeta meta = craftStack.getItemMeta();
                meta.setLore(Arrays.asList((Object[])new String[] { Sputnik.trans("&7Obtain the &dCookie Buff"), Sputnik.trans("&7from booster cookies in the"), Sputnik.trans("&7hub's community shop."), " ", Sputnik.trans(text_ + "&7: " + PlayerUtils.getCookieDurationDisplayGUI(player)), " ", (Object)ChatColor.YELLOW + "Click to get all the info!" }));
                craftStack.setItemMeta(meta);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
}
