package net.hypixel.skyblock.gui;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.SkyBlock;

public class WarpGUI extends GUI
{
    private SkyBlock plugin;
    
    public WarpGUI() {
        super("Fast Travel", 54);
        this.plugin = SkyBlock.getPlugin();
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(WarpGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        final User user = User.getUser(e.getPlayer().getUniqueId());
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SKYBLOCK_MENU.getGUI().open(player);
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Go Back", Material.ARROW, (short)0, 1, (Object)ChatColor.GRAY + "To SkyBlock Menu");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.RED + "Private islands are temporarily unavailable at the moment.");
                if (player.getOpenInventory() != null) {
                    player.closeInventory();
                }
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "Private Island", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56", 1, (Object)ChatColor.DARK_GRAY + "/is", " ", (Object)ChatColor.GRAY + "Your very own chunk of SkyBlock.", (Object)ChatColor.GRAY + "Nice housing for your minions.", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final World hub = Bukkit.getWorld("world");
                player.teleport(new Location(Bukkit.getWorld("world"), -2.5, 70.0, -68.5, 180.0f, 0.0f));
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "Skyblock Hub", "cf40942f364f6cbceffcf1151796410286a48b1aeba77243e218026c09cd1", 1, (Object)ChatColor.DARK_GRAY + "/hub", " ", (Object)ChatColor.GRAY + "Where everything happens and", (Object)ChatColor.GRAY + "anything is possible.", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage((Object)ChatColor.YELLOW + "The Dungeon hub has not been added yet. Stay tuned for updates!");
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Dungeon Hub " + (Object)ChatColor.DARK_GRAY + "- " + (Object)ChatColor.AQUA + "Spawn", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.DARK_GRAY + "/dungeon_hub", " ", (Object)ChatColor.GRAY + "Group with friends and take on", (Object)ChatColor.GRAY + "challenging Dungeons.");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World the_barn = Bukkit.getWorld("world");
                player1.teleport(new Location(the_barn, 114.0, 71.0, -207.0));
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "The Barn" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "4d3a6bd98ac1833c664c4909ff8d2dc62ce887bdcf3cc5b3848651ae5af6b", 1, (Object)ChatColor.DARK_GRAY + "/warp barn", " ", (Object)ChatColor.GRAY + "Collect basic farming resource", (Object)ChatColor.GRAY + "from plentiful crops or the local", (Object)ChatColor.GRAY + "animal population.", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Farming", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "I", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World park = Bukkit.getWorld("world");
                player1.teleport(new Location(park, -276.0, 82.0, -12.0));
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "The Park" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "b76c7f96f862243c5a6fe727aec0b8657cd2c65a463fd816c94efe4c622c055a", 1, (Object)ChatColor.DARK_GRAY + "/warp park", " ", (Object)ChatColor.GRAY + "Chop down trees and explore to", (Object)ChatColor.GRAY + "meet various characters across", (Object)ChatColor.GRAY + "different biome layers.", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Foraging", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "I", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World gold_mine = Bukkit.getWorld("world");
                player1.teleport(new Location(gold_mine, -4.0, 74.0, -273.0));
            }
            
            @Override
            public int getSlot() {
                return 15;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Gold Mine" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "73bc965d579c3c6039f0a17eb7c2e6faf538c7a5de8e60ec7a719360d0a857a9", 1, (Object)ChatColor.DARK_GRAY + "/warp gold", " ", (Object)ChatColor.GRAY + "our first stop for extended", (Object)ChatColor.GRAY + "mining related activities and home", (Object)ChatColor.GRAY + "to SkyBlock's local janitor Rusty.", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Mining", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "I", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World deep_caverns = Bukkit.getWorld("world");
                player1.teleport(new Location(deep_caverns, -2.0, 178.0, -458.0));
            }
            
            @Override
            public int getSlot() {
                return 16;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Deep Caverns" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "569a1f114151b4521373f34bc14c2963a5011cdc25a6554c48c708cd96ebfc", 1, (Object)ChatColor.DARK_GRAY + "/warp deep", " ", (Object)ChatColor.GRAY + "Collect basic farming resource", (Object)ChatColor.GRAY + "from plentiful crops or the local", (Object)ChatColor.GRAY + "animal population.", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Farming", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "I", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 20;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Dwarven Mines" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.DARK_GRAY + "/warp dwarves", " ", (Object)ChatColor.GRAY + "Discover new ores and minerals and", (Object)ChatColor.GRAY + "level up your heart of the", (Object)ChatColor.GRAY + "Mountain whilst completing", (Object)ChatColor.GRAY + "commissions from the Dwarven King", (Object)ChatColor.GRAY + "himself.", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Mining", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "III", " ", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 21;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Crystal Hollows" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Entrance", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.DARK_GRAY + "/warp crystals", " ", (Object)ChatColor.GRAY + "A vast series of caves and random", (Object)ChatColor.GRAY + "structures with tougher Stone and", (Object)ChatColor.GRAY + "special gems!", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Mining", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "IV", " ", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World spider_mine = Bukkit.getWorld("world");
                player1.teleport(new Location(spider_mine, -201.0, 84.0, -232.0));
            }
            
            @Override
            public int getSlot() {
                return 22;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Spider's Den" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "21c5840736229db9d9645bf9b409e73e706e3dc4fc30d78eb2079d20d929db9e", 1, (Object)ChatColor.DARK_GRAY + "/warp spider", " ", (Object)ChatColor.GRAY + "Explore a dangerous nest, discover", (Object)ChatColor.GRAY + "the Bestiary, hunt for Relics, and", (Object)ChatColor.GRAY + "fight all kinds of Spiders!", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Combat", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "I", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World the_end = Bukkit.getWorld("world");
                player1.teleport(new Location(the_end, -499.0, 101.0, -275.0));
            }
            
            @Override
            public int getSlot() {
                return 23;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "The End" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "7840b87d52271d2a755dedc82877e0ed3df67dcc42ea479ec146176b02779a5", 1, (Object)ChatColor.DARK_GRAY + "/warp end", " ", (Object)ChatColor.GRAY + "Fight Zealots, mine End Stone, and", (Object)ChatColor.GRAY + "defeat ancient Dragons!", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Combat", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "III", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                final World nether = Bukkit.getWorld("world");
                player1.teleport(new Location(nether, -310.0, 83.0, -381.0));
            }
            
            @Override
            public int getSlot() {
                return 24;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Crimson Isle" + (Object)ChatColor.GRAY + " - " + (Object)ChatColor.AQUA + "Spawn", "721d0930bd61fea4cb9027b00e94e13d62029c524ea0b3260c747457ba1bcfa1", 1, (Object)ChatColor.DARK_GRAY + "/warp nether", " ", (Object)ChatColor.GRAY + "Fight challenging bosses, discover", (Object)ChatColor.GRAY + "new Sea Creatures, complete epic", (Object)ChatColor.GRAY + "quests, and join your favourite", (Object)ChatColor.GRAY + "faction!", " ", (Object)ChatColor.GRAY + "Main Skill: " + (Object)ChatColor.AQUA + "Combat", (Object)ChatColor.GRAY + "Island Tier" + (Object)ChatColor.YELLOW + "IV", " ", (Object)ChatColor.YELLOW + "Click to warp!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 30;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.GREEN + "Garden", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.DARK_GRAY + "/warp garden", " ", (Object)ChatColor.GRAY + "Spawn on your very own " + (Object)ChatColor.GREEN + "Garden.", " ", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 32;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "Warp to: " + (Object)ChatColor.RED + "Jerry's Workshop", "1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55", 1, (Object)ChatColor.DARK_GRAY + "Teleports you to " + (Object)ChatColor.RED + "Jerry's", (Object)ChatColor.RED + "Workshop. " + (Object)ChatColor.GRAY + "Available for a", (Object)ChatColor.GRAY + "limited time!", " ", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 45;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Island Browser", Material.BLAZE_POWDER, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Check out the most popular", (Object)ChatColor.GRAY + "islands in Skyblock! Filter by", (Object)ChatColor.GRAY + "category tags to explore various", (Object)ChatColor.GRAY + "types of islands.", " ", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player player1 = (Player)e.getWhoClicked();
                player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.0f);
                player1.sendMessage("Comming Soon!");
            }
            
            @Override
            public int getSlot() {
                return 50;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Advanced Mode", Material.INK_SACK, (short)10, 1, (Object)ChatColor.DARK_GRAY + "Show additional convenient fast", (Object)ChatColor.GRAY + "travel options such as quick", (Object)ChatColor.GRAY + "Right-Click warping and extra", (Object)ChatColor.GRAY + "warps obtained from " + (Object)ChatColor.DARK_PURPLE + "EPIC", (Object)ChatColor.GRAY + "scrolls", " ", (Object)ChatColor.GRAY + "Enabled: " + (Object)ChatColor.GREEN + "ON", (Object)ChatColor.RED + "Comming Soon!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                player.sendMessage("Gui not added yet");
            }
            
            @Override
            public int getSlot() {
                return 53;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Paper Icon", Material.EMPTY_MAP, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Use paper icons, which may load this", (Object)ChatColor.GRAY + "menu faster on your computer.", " ", (Object)ChatColor.GRAY + "Enabled: " + (Object)ChatColor.RED + "OFF", (Object)ChatColor.YELLOW + "Click to toggle!");
            }
        });
    }
}
