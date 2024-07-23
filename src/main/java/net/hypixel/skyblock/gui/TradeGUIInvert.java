package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.item.Untradeable;
import org.bukkit.event.inventory.InventoryCloseEvent;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import java.util.UUID;

public class TradeGUIInvert extends GUI
{
    private final int[] ls;
    private final int[] rs;
    private final UUID tradeUUID;
    
    public void fillFrom(final Inventory i, final int startFromSlot, final int height, final ItemStack stacc) {
        i.setItem(startFromSlot, stacc);
        i.setItem(startFromSlot + 9, stacc);
        i.setItem(startFromSlot + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9, stacc);
    }
    
    public TradeGUIInvert() {
        this(UUID.randomUUID());
    }
    
    public TradeGUIInvert(final UUID uuid) {
        super("You                  " + ((Player)TradeGUI.player1.get((Object)uuid)).getName(), 45);
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
        TradeMenu.tradeP2Ready.put((Object)this.tradeUUID, (Object)false);
        new BukkitRunnable() {
            public void run() {
                if (TradeGUIInvert.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (TradeMenu.tradeP2Countdown.containsKey((Object)TradeGUIInvert.this.tradeUUID) && (int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) > 0) {
                    TradeMenu.tradeP2Countdown.put((Object)TradeGUIInvert.this.tradeUUID, (Object)((int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) - 1));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (TradeMenu.tradeP2Countdown.containsKey((Object)TradeGUIInvert.this.tradeUUID) && (int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) <= 0 && (((List)TradeGUI.itemOfferP1.get((Object)TradeGUIInvert.this.tradeUUID)).size() > 0 || ((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).size() > 0) && !(boolean)TradeMenu.tradeP2Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                    TradeMenu.tradeP2Ready.put((Object)TradeGUIInvert.this.tradeUUID, (Object)true);
                    ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
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
                final ItemStack stack = SUtil.getStack(Sputnik.trans("&eNew deal"), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getName());
                return stack;
            }
        });
        new BukkitRunnable() {
            public void run() {
                if (TradeGUIInvert.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (TradeMenu.tradeP1Ready.containsKey((Object)TradeGUIInvert.this.tradeUUID) && TradeMenu.tradeP2Countdown.containsKey((Object)TradeGUIInvert.this.tradeUUID)) {
                    if (TradeMenu.tradeP1Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&aOther player confirmed!"), Material.INK_SACK, (short)10, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "Waiting for you to confirm..."));
                    }
                    else if ((int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) > 0 && !(boolean)TradeMenu.tradeP2Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&eDeal timer..."), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "The trade changed recently."));
                    }
                    else if (TradeMenu.tradeP2Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&ePending their confirm."), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getName() + ".", (Object)ChatColor.GRAY + "Waiting for them to confirm."));
                    }
                    else {
                        i.setItem(41, SUtil.getStack(Sputnik.trans("&eNew deal"), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Trading with " + ((Player)TradeGUI.player1.get((Object)TradeGUIInvert.this.tradeUUID)).getName() + "."));
                    }
                }
                if (TradeMenu.tradeP2Countdown.containsKey((Object)TradeGUIInvert.this.tradeUUID)) {
                    if ((int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) > 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eDeal timer! &7(&e" + TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID) + "&7)"), Material.STAINED_CLAY, (short)4, (int)TradeMenu.tradeP2Countdown.get((Object)TradeGUIInvert.this.tradeUUID), (Object)ChatColor.GRAY + "The trade recently changed.", (Object)ChatColor.GRAY + "Please review it before", (Object)ChatColor.GRAY + "accepting."));
                        TradeMenu.tradeP2Ready.put((Object)TradeGUIInvert.this.tradeUUID, (Object)false);
                    }
                    else if (TradeMenu.tradeP2Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&aDeal accepted!"), Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "You accepted the trade.", (Object)ChatColor.GRAY + "wait for the other party to", (Object)ChatColor.GRAY + "accept."));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUIInvert.this.tradeUUID)).size() <= 0 && ((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).size() <= 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&aTrading!"), Material.STAINED_CLAY, (short)13, 1, (Object)ChatColor.GRAY + "Click an item in your", (Object)ChatColor.GRAY + "inventory to offer it for", (Object)ChatColor.GRAY + "trade."));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUIInvert.this.tradeUUID)).size() <= 0 && !(boolean)TradeMenu.tradeP2Ready.get((Object)TradeGUIInvert.this.tradeUUID)) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eWarning!"), Material.STAINED_CLAY, (short)1, 1, (Object)ChatColor.GRAY + "You are offering items", (Object)ChatColor.GRAY + "without getting anything in", (Object)ChatColor.GRAY + "return.", " ", (Object)ChatColor.YELLOW + "Click to accept anyway!"));
                    }
                    else if (((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).size() <= 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&bGift!"), Material.STAINED_CLAY, (short)11, 1, (Object)ChatColor.GRAY + "You are receiving items", (Object)ChatColor.GRAY + "without offering anything in", (Object)ChatColor.GRAY + "return.", " ", (Object)ChatColor.YELLOW + "Click to accept!"));
                    }
                    else if (((List)TradeGUI.itemOfferP1.get((Object)TradeGUIInvert.this.tradeUUID)).size() > 0 && ((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).size() > 0) {
                        i.setItem(39, SUtil.getStack(Sputnik.trans("&eDeal!"), Material.STAINED_CLAY, (short)5, 1, (Object)ChatColor.GRAY + "All trades are final and", (Object)ChatColor.GRAY + "cannot be reverted.", " ", (Object)ChatColor.GREEN + "Make sure to review the", (Object)ChatColor.GREEN + "trade before accepting", " ", (Object)ChatColor.YELLOW + "Click to accept the trade!"));
                    }
                }
                final List<ItemStack> stl1 = (List<ItemStack>)TradeGUI.itemOfferP1.get((Object)TradeGUIInvert.this.tradeUUID);
                final List<ItemStack> stl2 = (List<ItemStack>)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID);
                final ItemStack stk = SUtil.getSingleLoreStack((Object)ChatColor.GRAY + "\u21e6 Your stuff", Material.STAINED_GLASS_PANE, (short)0, 1, (Object)ChatColor.GRAY + "Their stuff \u21e8");
                stk.setDurability((short)7);
                TradeGUIInvert.this.fillFrom(i, 4, 5, stk);
                int a = -1;
                for (final int slot : TradeGUIInvert.this.rs) {
                    if (a < stl1.size() - 1) {
                        ++a;
                        if (SItem.find((ItemStack)stl1.get(a)) != null) {
                            i.setItem(slot, User.getUser(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getUniqueId()).updateItemBoost(SItem.find((ItemStack)stl1.get(a))));
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
                for (final int slot2 : TradeGUIInvert.this.ls) {
                    if (b < stl2.size() - 1) {
                        ++b;
                        if (SItem.find((ItemStack)stl2.get(b)) != null) {
                            i.setItem(slot2, User.getUser(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getUniqueId()).updateItemBoost(SItem.find((ItemStack)stl2.get(b))));
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
        this.set(new GUISignItem() {
            @Override
            public GUI onSignClose(final String query, final Player target) {
                if (target != TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)) {
                    return null;
                }
                if (query == "$canc") {
                    return new TradeGUI(TradeGUIInvert.this.tradeUUID);
                }
                try {
                    final long add = Long.parseLong(query);
                    final double cur = (double)User.getUser(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getUniqueId()).getCoins();
                    if (add <= 0L) {
                        ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                        player.sendMessage((Object)ChatColor.RED + "Couldn't validate this Coins amount!");
                        return new TradeGUIInvert(TradeGUIInvert.this.tradeUUID);
                    }
                    if (add > cur) {
                        ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                        player.sendMessage((Object)ChatColor.RED + "You don't have that much Coins for this.");
                        return new TradeGUIInvert(TradeGUIInvert.this.tradeUUID);
                    }
                    if (((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).size() < 16) {
                        User.getUser(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getUniqueId()).subCoins(add);
                        ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        final long stackamount = Math.min(64L, Math.max(10000L, add) / 10000L);
                        ItemStack coinsStack = SUtil.getSkullURLStack((Object)ChatColor.AQUA + Sputnik.formatFull((float)add) + " Coins", "7b951fed6a7b2cbc2036916dec7a46c4a56481564d14f945b6ebc03382766d3b", (int)stackamount, (Object)ChatColor.GRAY + "Lump-sum amount");
                        final net.minecraft.server.v1_8_R3.ItemStack tagStack = CraftItemStack.asNMSCopy(coinsStack);
                        final NBTTagCompound tagCompound = tagStack.hasTag() ? tagStack.getTag() : new NBTTagCompound();
                        tagCompound.set("data_bits", (NBTBase)new NBTTagLong(add));
                        tagStack.setTag(tagCompound);
                        coinsStack = CraftItemStack.asBukkitCopy(tagStack);
                        ((List)TradeGUI.itemOfferP2.get((Object)TradeGUIInvert.this.tradeUUID)).add((Object)coinsStack);
                        TradeMenu.tradeP1Countdown.put((Object)TradeGUIInvert.this.tradeUUID, (Object)3);
                        TradeMenu.tradeP2Countdown.put((Object)TradeGUIInvert.this.tradeUUID, (Object)3);
                        player.sendMessage((Object)ChatColor.RED + "An unexpected error occured while attempting to access the economy!");
                    }
                    return new TradeGUIInvert(TradeGUIInvert.this.tradeUUID);
                }
                catch (final NumberFormatException ex) {
                    player.sendMessage((Object)ChatColor.RED + "Couldn't parse this Coins amount!");
                    ((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)TradeGUIInvert.this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    return new TradeGUIInvert(TradeGUIInvert.this.tradeUUID);
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
                return SUtil.getSkullURLStack((Object)ChatColor.AQUA + "coins transaction", "7b951fed6a7b2cbc2036916dec7a46c4a56481564d14f945b6ebc03382766d3b", 1, (Object)ChatColor.GRAY + " ", (Object)ChatColor.YELLOW + "Click to add bits!");
            }
            
            @Override
            public UUID inti() {
                return TradeGUIInvert.this.tradeUUID;
            }
        });
    }
    
    @Override
    public void onClose(final InventoryCloseEvent e) {
        ((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
        TradeMenu.triggerCloseEvent(this.tradeUUID, false, (Player)e.getPlayer());
        GUIListener.QUERY_MAP.remove((Object)e.getPlayer().getUniqueId());
        GUIListener.QUERY_MAPPING.remove((Object)e.getPlayer().getUniqueId());
    }
    
    @Override
    public void onBottomClick(final InventoryClickEvent e) {
        if (e.getSlot() < 0) {
            e.setCancelled(true);
            return;
        }
        ItemStack cs = null;
        if (((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getInventory().getItem(e.getSlot()) != null) {
            cs = ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getInventory().getItem(e.getSlot());
        }
        if (cs != null) {
            final SItem sItem = SItem.find(cs);
            if (sItem != null && SItem.isSpecItem(cs)) {
                if (!(sItem.getType().getGenericInstance() instanceof Untradeable)) {
                    if (((List)TradeGUI.itemOfferP2.get((Object)this.tradeUUID)).size() < 16) {
                        ((Player)TradeGUI.player1.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player1.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                        ((List)TradeGUI.itemOfferP2.get((Object)this.tradeUUID)).add((Object)cs);
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getInventory().setItem(e.getSlot(), (ItemStack)null);
                        TradeMenu.tradeP2Countdown.put((Object)this.tradeUUID, (Object)3);
                        TradeMenu.tradeP1Countdown.put((Object)this.tradeUUID, (Object)3);
                    }
                    else {
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&c&lIT'S FULL! &7Your trade window is full!"));
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                        ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    }
                }
                else {
                    ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&cYou cannot trade this item!"));
                    ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                }
            }
            else {
                ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).sendMessage(Sputnik.trans("&cYou cannot trade this item!"));
                ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            }
        }
    }
    
    @Override
    public void onTopClick(final InventoryClickEvent e) {
        if (((List)TradeGUI.itemOfferP2.get((Object)this.tradeUUID)).contains((Object)e.getInventory().getItem(e.getSlot())) && TradeGUI.isContain(this.ls, e.getSlot())) {
            ((Player)TradeGUI.player2.get((Object)this.tradeUUID)).playSound(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
            ((List)TradeGUI.itemOfferP2.get((Object)this.tradeUUID)).remove((Object)e.getInventory().getItem(e.getSlot()));
            final ItemStack stack = e.getInventory().getItem(e.getSlot());
            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
            if (!nmsStack.getTag().hasKey("data_bits")) {
                Sputnik.smartGiveItem(stack, (Player)TradeGUI.player2.get((Object)this.tradeUUID));
            }
            else {
                User.getUser(((Player)TradeGUI.player2.get((Object)this.tradeUUID)).getUniqueId()).addCoins(nmsStack.getTag().getLong("data_bits"));
            }
            TradeMenu.tradeP2Countdown.put((Object)this.tradeUUID, (Object)3);
            TradeMenu.tradeP1Countdown.put((Object)this.tradeUUID, (Object)3);
        }
    }
}
