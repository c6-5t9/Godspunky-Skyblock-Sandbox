package net.hypixel.skyblock.gui;

import java.util.HashMap;
import org.bukkit.event.inventory.InventoryCloseEvent;
import net.hypixel.skyblock.item.Untradeable;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TradeGUI extends GUI
{
    private final UUID tradeUUID;
    public static final Map<UUID, List<ItemStack>> itemOfferP1;
    public static final Map<UUID, List<ItemStack>> itemOfferP2;
    public static final Map<UUID, Player> player1;
    public static final Map<UUID, Player> player2;
    public static final Map<UUID, Integer> tradeCountdown;
    private final int[] ls;
    private final int[] rs;
    
    public void fillFrom(final Inventory i, final int startFromSlot, final int height, final ItemStack stacc) {
        i.setItem(startFromSlot, stacc);
        i.setItem(startFromSlot + 9, stacc);
        i.setItem(startFromSlot + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9, stacc);
    }
    
    public TradeGUI() {
        this(UUID.randomUUID());
    }
    
    public TradeGUI(final UUID uuid) {
        super("You                  " + ((Player)TradeGUI.player2.get((Object)uuid)).getName(), 45);
        this.ls = new int[] { 0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30 };
        this.rs = new int[] { 5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35 };
        this.tradeUUID = uuid;
        if (!TradeGUI.itemOfferP1.containsKey((Object)uuid) && TradeGUI.itemOfferP1.get((Object)uuid) == null) {
            TradeGUI.itemOfferP1.put((Object)uuid, (Object)new ArrayList());
        }
        if (!TradeGUI.itemOfferP2.containsKey((Object)uuid) && TradeGUI.itemOfferP2.get((Object)uuid) == null) {
            TradeGUI.itemOfferP2.put((Object)uuid, (Object)new ArrayList());
        }
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final Inventory i = e.getInventory();
        final ItemStack stk = SUtil.getSingleLoreStack((Object)ChatColor.GRAY + "\u21e6 Your stuff", Material.STAINED_GLASS_PANE, (short)0, 1, (Object)ChatColor.GRAY + "Their stuff \u21e8");
        stk.setDurability((short)7);
        this.fillFrom(i, 4, 5, stk);
        TradeMenu.tradeP1Ready.put((Object)this.tradeUUID, (Object)false);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (TradeMenu.tradeP1Countdown.containsKey((Object)TradeGUI.this.tradeUUID) && (int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) <= 0 && (((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).size() > 0 || ((List)TradeGUI.itemOfferP2.get((Object)TradeGUI.this.tradeUUID)).size() > 0) && !(boolean)TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID)) {
                    TradeMenu.tradeP1Ready.put((Object)TradeGUI.this.tradeUUID, (Object)true);
                    ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    ((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                }
            }
            
            @Override
            public int getSlot() {
                return 39;
            }
            
            @Override
            public ItemStack getItem() {
                final ItemStack stack = SUtil.getStack(Sputnik.trans("&aTrading!"), Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "Click an item in your", (Object)ChatColor.GRAY + "inventory to offer it for", (Object)ChatColor.GRAY + "trade.");
                return stack;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
            }
            
            @Override
            public int getSlot() {
                return 41;
            }
            
            @Override
            public ItemStack getItem() {
                final ItemStack stack = SUtil.getStack(Sputnik.trans("&eNew deal"), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getName() + ".");
                return stack;
            }
        });
        new BukkitRunnable() {
            public void run() {
                if (TradeGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (TradeMenu.tradeP1Countdown.containsKey((Object)TradeGUI.this.tradeUUID) && (int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) > 0) {
                    TradeMenu.tradeP1Countdown.put((Object)TradeGUI.this.tradeUUID, (Object)((int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) - 1));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
        new BukkitRunnable() {
            public void run() {
                if (TradeGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (TradeMenu.tradeP2Ready.containsKey((Object)TradeGUI.this.tradeUUID) && TradeMenu.tradeP1Countdown.containsKey((Object)TradeGUI.this.tradeUUID)) {
                    if (TradeMenu.tradeP2Ready.get((Object)TradeGUI.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&aOther player confirmed!"), Material.INK_SACK, (short)10, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "Waiting for you to confirm..."));
                    }
                    else if ((int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) > 0 && !(boolean)TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&eDeal timer..."), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "The trade changed recently."));
                    }
                    else if (TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&ePending their confirm."), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "Waiting for them to confirm."));
                    }
                    else {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&eNew deal"), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getName() + "."));
                    }
                }
                if (TradeMenu.tradeP1Countdown.containsKey((Object)TradeGUI.this.tradeUUID)) {
                    if ((int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) > 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eDeal timer! &7(&e" + TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID) + "&7)"), Material.STAINED_CLAY, (short)4, (int)TradeMenu.tradeP1Countdown.get((Object)TradeGUI.this.tradeUUID), (Object)ChatColor.GRAY + "The trade recently changed.", (Object)ChatColor.GRAY + "Please review it before", (Object)ChatColor.GRAY + "accepting."));
                        TradeMenu.tradeP1Ready.put((Object)TradeGUI.this.tradeUUID, (Object)false);
                    }
                    else if (TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID)) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&aDeal accepted!"), Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "You accepted the trade.", (Object)ChatColor.GRAY + "wait for the other party to", (Object)ChatColor.GRAY + "accept."));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).size() <= 0 && ((List)TradeGUI.itemOfferP2.get((Object)TradeGUI.this.tradeUUID)).size() <= 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&aTrading!"), Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "Click an item in your", (Object)ChatColor.GRAY + "inventory to offer it for", (Object)ChatColor.GRAY + "trade."));
                    }
                    else if (((List)TradeGUI.itemOfferP2.get((Object)TradeGUI.this.tradeUUID)).size() <= 0 && !(boolean)TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID)) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eWarning!"), Material.STAINED_CLAY, (short)1, 1, (Object)ChatColor.GRAY + "You are offering items", (Object)ChatColor.GRAY + "without getting anything in", (Object)ChatColor.GRAY + "return.", " ", (Object)ChatColor.YELLOW + "Click to accept anyway!"));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).size() <= 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&bGift!"), Material.STAINED_CLAY, (short)11, 1, (Object)ChatColor.GRAY + "You are receiving items", (Object)ChatColor.GRAY + "without offering anything in", (Object)ChatColor.GRAY + "return.", " ", (Object)ChatColor.YELLOW + "Click to accept!"));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).size() > 0 && ((List)TradeGUI.itemOfferP2.get((Object)TradeGUI.this.tradeUUID)).size() > 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eDeal!"), Material.STAINED_CLAY, (short)5, 1, (Object)ChatColor.GRAY + "All trades are final and", (Object)ChatColor.GRAY + "cannot be reverted.", " ", (Object)ChatColor.GREEN + "Make sure to review the", (Object)ChatColor.GREEN + "trade before accepting", " ", (Object)ChatColor.YELLOW + "Click to accept the trade!"));
                    }
                }
                if ((boolean)TradeMenu.tradeP1Ready.get((Object)TradeGUI.this.tradeUUID) && (boolean)TradeMenu.tradeP2Ready.get((Object)TradeGUI.this.tradeUUID)) {
                    this.cancel();
                    TradeMenu.successTrade.put((Object)TradeGUI.this.tradeUUID, (Object)true);
                    TradeMenu.triggerCloseEvent(TradeGUI.this.tradeUUID, true, e.getPlayer());
                }
                final List<ItemStack> stl1 = (List<ItemStack>)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID);
                final List<ItemStack> stl2 = (List<ItemStack>)TradeGUI.itemOfferP2.get((Object)TradeGUI.this.tradeUUID);
                final ItemStack stk = SUtil.getSingleLoreStack((Object)ChatColor.GRAY + "\u21e6 Your stuff", Material.STAINED_GLASS_PANE, (short)0, 1, (Object)ChatColor.GRAY + "Their stuff \u21e8");
                stk.setDurability((short)7);
                TradeGUI.this.fillFrom(i, 4, 5, stk);
                int a = -1;
                for (final int slot : TradeGUI.this.ls) {
                    if (a < stl1.size() - 1) {
                        ++a;
                        if (SItem.find((ItemStack)stl1.get(a)) != null) {
                            i.setItem(slot, User.getUser(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getUniqueId()).updateItemBoost(SItem.find((ItemStack)stl1.get(a))));
                        }
                        else {
                            i.setItem(slot, (ItemStack)stl1.get(a));
                        }
                    }
                    else {
                        i.setItem(slot, (ItemStack)null);
                    }
                }
                int b = -1;
                for (final int slot2 : TradeGUI.this.rs) {
                    if (b < stl2.size() - 1) {
                        ++b;
                        if (SItem.find((ItemStack)stl2.get(b)) != null) {
                            i.setItem(slot2, User.getUser(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getUniqueId()).updateItemBoost(SItem.find((ItemStack)stl2.get(b))));
                        }
                        else {
                            i.setItem(slot2, (ItemStack)stl2.get(b));
                        }
                    }
                    else {
                        i.setItem(slot2, (ItemStack)null);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (!((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).isOnline() || !((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getWorld().equals(((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getWorld())) {
                    this.cancel();
                    TradeMenu.triggerCloseEvent(TradeGUI.this.tradeUUID, false, (Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID));
                }
                else if (!((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).isOnline() || !((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getWorld().equals(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getWorld())) {
                    this.cancel();
                    TradeMenu.triggerCloseEvent(TradeGUI.this.tradeUUID, false, (Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        this.set(new GUISignItem() {
            @Override
            public GUI onSignClose(final String query, final Player target) {
                if (target != TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)) {
                    return null;
                }
                if (query == "$canc") {
                    return new TradeGUI(TradeGUI.this.tradeUUID);
                }
                try {
                    final long add = Long.parseLong(query);
                    final double cur = (double)User.getUser(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getUniqueId()).getBits();
                    if (add <= 0L) {
                        ((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                        player.sendMessage((Object)ChatColor.RED + "Couldn't validate this Coins amount!");
                        return new TradeGUI(TradeGUI.this.tradeUUID);
                    }
                    if (add > cur) {
                        ((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                        player.sendMessage((Object)ChatColor.RED + "You don't have that much Coins for this.");
                        return new TradeGUI(TradeGUI.this.tradeUUID);
                    }
                    if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).size() < 16) {
                        User.getUser(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getUniqueId()).subCoins(add);
                        ((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        final long stackamount = Math.min(64L, Math.max(10000L, add) / 10000L);
                        ItemStack coinsStack = SUtil.getSkullURLStack((Object)ChatColor.AQUA + Sputnik.formatFull((float)add) + " Coins", "7b951fed6a7b2cbc2036916dec7a46c4a56481564d14f945b6ebc03382766d3b", (int)stackamount, (Object)ChatColor.GRAY + "Lump-sum amount");
                        final net.minecraft.server.v1_8_R3.ItemStack tagStack = CraftItemStack.asNMSCopy(coinsStack);
                        final NBTTagCompound tagCompound = tagStack.hasTag() ? tagStack.getTag() : new NBTTagCompound();
                        tagCompound.set("data_bits", (NBTBase)new NBTTagLong(add));
                        tagStack.setTag(tagCompound);
                        coinsStack = CraftItemStack.asBukkitCopy(tagStack);
                        ((List)TradeGUI.itemOfferP1.get((Object)TradeGUI.this.tradeUUID)).add((Object)coinsStack);
                        TradeMenu.tradeP1Countdown.put((Object)TradeGUI.this.tradeUUID, (Object)3);
                        TradeMenu.tradeP2Countdown.put((Object)TradeGUI.this.tradeUUID, (Object)3);
                    }
                    return new TradeGUI(TradeGUI.this.tradeUUID);
                }
                catch (final NumberFormatException ex) {
                    player.sendMessage((Object)ChatColor.RED + "Couldn't parse this Coins amount!");
                    ((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUI.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    return new TradeGUI(TradeGUI.this.tradeUUID);
                }
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
                player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 36;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "Coins transaction", "7b951fed6a7b2cbc2036916dec7a46c4a56481564d14f945b6ebc03382766d3b", 1, (Object)ChatColor.GRAY + " ", (Object)ChatColor.YELLOW + "Click to add bits!");
            }
            
            @Override
            public UUID inti() {
                return TradeGUI.this.tradeUUID;
            }
        });
    }
    
    @Override
    public void onBottomClick(final InventoryClickEvent e) {
        if (e.getSlot() < 0) {
            e.setCancelled(true);
            return;
        }
        ItemStack cs = null;
        if (((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getInventory().getItem(e.getSlot()) != null) {
            cs = ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getInventory().getItem(e.getSlot());
        }
        if (cs != null) {
            final SItem sItem = SItem.find(cs);
            if (sItem != null && SItem.isSpecItem(cs)) {
                if (!(sItem.getType().getGenericInstance() instanceof Untradeable)) {
                    if (((List)TradeGUI.itemOfferP1.get((Object)this.tradeUUID)).size() < 16) {
                        ((List)TradeGUI.itemOfferP1.get((Object)this.tradeUUID)).add((Object)cs);
                        ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getInventory().setItem(e.getSlot(), (ItemStack)null);
                        TradeMenu.tradeP1Countdown.put((Object)this.tradeUUID, (Object)3);
                        TradeMenu.tradeP2Countdown.put((Object)this.tradeUUID, (Object)3);
                    }
                    else {
                        ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                        ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&c&lIT'S FULL! &7Your trade window is full!"));
                    }
                }
                else {
                    ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&cYou cannot trade this item!"));
                    ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                }
            }
            else {
                ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&cYou cannot trade this item!"));
                ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            }
        }
    }
    
    @Override
    public void onTopClick(final InventoryClickEvent e) {
        if (((List)TradeGUI.itemOfferP1.get((Object)this.tradeUUID)).contains((Object)e.getInventory().getItem(e.getSlot())) && isContain(this.ls, e.getSlot())) {
            ((List)TradeGUI.itemOfferP1.get((Object)this.tradeUUID)).remove((Object)e.getInventory().getItem(e.getSlot()));
            final ItemStack stack = e.getInventory().getItem(e.getSlot());
            ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
            if (!nmsStack.getTag().hasKey("data_bits")) {
                Sputnik.smartGiveItem(stack, (Player)TradeGUI.player1.get((Object)this.tradeUUID));
            }
            else {
                User.getUser(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getUniqueId()).addCoins(nmsStack.getTag().getLong("data_bits"));
            }
            TradeMenu.tradeP1Countdown.put((Object)this.tradeUUID, (Object)3);
            TradeMenu.tradeP2Countdown.put((Object)this.tradeUUID, (Object)3);
        }
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        TradeMenu.triggerCloseEvent(this.tradeUUID, false, (Player)e.getPlayer());
        ((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
        GUIListener.QUERY_MAP.remove((Object)e.getPlayer().getUniqueId());
        GUIListener.QUERY_MAPPING.remove((Object)e.getPlayer().getUniqueId());
    }
    
    public static boolean isContain(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
    
    static {
        itemOfferP1 = (Map)new HashMap();
        itemOfferP2 = (Map)new HashMap();
        player1 = (Map)new HashMap();
        player2 = (Map)new HashMap();
        tradeCountdown = (Map)new HashMap();
    }
}
