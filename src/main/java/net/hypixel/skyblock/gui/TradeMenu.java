package net.hypixel.skyblock.gui;

import java.util.HashMap;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import net.hypixel.skyblock.features.sequence.SoundSequenceType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.TradeUtil;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.Map;

public class TradeMenu
{
    public static final Map<UUID, Boolean> tradeClose;
    public static final Map<UUID, Player> tradeClosePlayerName;
    public static final Map<UUID, Integer> tradeP1Countdown;
    public static final Map<UUID, Integer> tradeP2Countdown;
    public static final Map<UUID, Boolean> tradeP1Ready;
    public static final Map<UUID, Boolean> tradeP2Ready;
    public static final Map<UUID, Boolean> successTrade;
    public static final Map<UUID, Boolean> player1TradeUUID;
    public static final Map<UUID, Boolean> player2TradeUUID;
    private final Player p1;
    private final Player p2;
    private final UUID tradeUUID;
    
    public TradeMenu(final Player player1, final Player player2, final UUID uuid) {
        this.p1 = player1;
        this.p2 = player2;
        this.tradeUUID = uuid;
    }
    
    public static void triggerCloseEvent(final UUID tradeUUID, final boolean isSuccess, final Player player) {
        if (TradeMenu.tradeClose.containsKey((Object)tradeUUID)) {
            return;
        }
        if (!isSuccess) {
            TradeMenu.tradeClose.put((Object)tradeUUID, (Object)isSuccess);
            TradeMenu.tradeClosePlayerName.put((Object)tradeUUID, (Object)player);
        }
        else {
            TradeMenu.tradeClose.put((Object)tradeUUID, (Object)isSuccess);
            TradeMenu.tradeClosePlayerName.put((Object)tradeUUID, (Object)player);
        }
    }
    
    public void open() {
        if (this.p1.getUniqueId() == this.p2.getUniqueId()) {
            return;
        }
        TradeUtil.trading.put((Object)this.p1.getUniqueId(), (Object)true);
        TradeUtil.trading.put((Object)this.p2.getUniqueId(), (Object)true);
        TradeGUI.player1.put((Object)this.tradeUUID, (Object)this.p1);
        TradeGUI.player2.put((Object)this.tradeUUID, (Object)this.p2);
        new TradeGUI(this.tradeUUID).open(this.p1);
        new TradeGUIInvert(this.tradeUUID).open(this.p2);
        new BukkitRunnable() {
            public void run() {
                if (!TradeMenu.this.p1.isOnline() || !TradeMenu.this.p2.isOnline()) {
                    if (!TradeMenu.this.p1.isOnline()) {
                        TradeMenu.triggerCloseEvent(TradeMenu.this.tradeUUID, false, TradeMenu.this.p1);
                    }
                    else if (!TradeMenu.this.p2.isOnline()) {
                        TradeMenu.triggerCloseEvent(TradeMenu.this.tradeUUID, false, TradeMenu.this.p2);
                    }
                }
                if (TradeMenu.tradeClose.containsKey((Object)TradeMenu.this.tradeUUID)) {
                    this.cancel();
                    if (!(boolean)TradeMenu.tradeClose.get((Object)TradeMenu.this.tradeUUID)) {
                        if (TradeMenu.tradeClosePlayerName.get((Object)TradeMenu.this.tradeUUID) == TradeMenu.this.p1) {
                            TradeMenu.this.p1.sendMessage(Sputnik.trans("&cYou cancelled the trade!"));
                            TradeMenu.this.p2.sendMessage(Sputnik.trans("&b" + TradeMenu.this.p1.getName() + " &ccancelled the trade!"));
                            TradeMenu.this.p2.closeInventory();
                        }
                        else {
                            TradeMenu.this.p2.sendMessage(Sputnik.trans("&cYou cancelled the trade!"));
                            TradeMenu.this.p1.sendMessage(Sputnik.trans("&b" + TradeMenu.this.p2.getName() + " &ccancelled the trade!"));
                            TradeMenu.this.p1.closeInventory();
                        }
                        TradeMenu.this.clean();
                    }
                    else if (TradeMenu.successTrade.containsKey((Object)TradeMenu.this.tradeUUID)) {
                        if (TradeMenu.successTrade.get((Object)TradeMenu.this.tradeUUID)) {
                            final List<ItemStack> itemlist1 = (List<ItemStack>)TradeGUI.itemOfferP1.get((Object)TradeMenu.this.tradeUUID);
                            final List<ItemStack> itemlist2 = (List<ItemStack>)TradeGUI.itemOfferP2.get((Object)TradeMenu.this.tradeUUID);
                            TradeGUI.itemOfferP1.put((Object)TradeMenu.this.tradeUUID, (Object)itemlist2);
                            TradeGUI.itemOfferP2.put((Object)TradeMenu.this.tradeUUID, (Object)itemlist1);
                        }
                        final List<ItemStack> itemlist1 = (List<ItemStack>)TradeGUI.itemOfferP1.get((Object)TradeMenu.this.tradeUUID);
                        final List<ItemStack> itemlist2 = (List<ItemStack>)TradeGUI.itemOfferP2.get((Object)TradeMenu.this.tradeUUID);
                        final StringBuilder sb1 = new StringBuilder();
                        sb1.append("&6Trade completed with &r" + TradeMenu.this.p2.getDisplayName() + "&6!");
                        for (final ItemStack itemRece : itemlist1) {
                            if (!CraftItemStack.asNMSCopy(itemRece).getTag().hasKey("data_bits")) {
                                sb1.append("\n &a&l+ &8" + itemRece.getAmount() + "x &r" + itemRece.getItemMeta().getDisplayName());
                            }
                            else {
                                sb1.append("\n &a&l+ &8" + itemRece.getItemMeta().getDisplayName());
                            }
                        }
                        for (final ItemStack itemTaken : itemlist2) {
                            if (!CraftItemStack.asNMSCopy(itemTaken).getTag().hasKey("data_bits")) {
                                sb1.append("\n &c&l- &8" + itemTaken.getAmount() + "x &r" + itemTaken.getItemMeta().getDisplayName());
                            }
                            else {
                                sb1.append("\n &c&l- &8" + itemTaken.getItemMeta().getDisplayName());
                            }
                        }
                        TradeMenu.this.p1.sendMessage(Sputnik.trans(sb1.toString()));
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append("&6Trade completed with " + TradeMenu.this.p1.getDisplayName() + "&6!");
                        for (final ItemStack itemRece2 : itemlist2) {
                            if (!CraftItemStack.asNMSCopy(itemRece2).getTag().hasKey("data_bits")) {
                                sb2.append("\n &a&l+ &8" + itemRece2.getAmount() + "x &r" + itemRece2.getItemMeta().getDisplayName());
                            }
                            else {
                                sb2.append("\n &a&l+ &8" + itemRece2.getItemMeta().getDisplayName());
                            }
                        }
                        for (final ItemStack itemTaken2 : itemlist1) {
                            if (!CraftItemStack.asNMSCopy(itemTaken2).getTag().hasKey("data_bits")) {
                                sb2.append("\n &c&l- &8" + itemTaken2.getAmount() + "x &r" + itemTaken2.getItemMeta().getDisplayName());
                            }
                            else {
                                sb2.append("\n &c&l- &8" + itemTaken2.getItemMeta().getDisplayName());
                            }
                        }
                        TradeMenu.this.p2.sendMessage(Sputnik.trans(sb2.toString()));
                        SoundSequenceType.TRADE_COMPLETE.play(TradeMenu.this.p1);
                        SoundSequenceType.TRADE_COMPLETE.play(TradeMenu.this.p2);
                        TradeMenu.this.p1.closeInventory();
                        TradeMenu.this.p2.closeInventory();
                        TradeMenu.this.clean();
                        TradeGUI.itemOfferP1.remove((Object)TradeMenu.this.p1.getUniqueId());
                        TradeGUI.itemOfferP2.remove((Object)TradeMenu.this.p2.getUniqueId());
                    }
                    TradeMenu.this.returnToAllPlayers(TradeMenu.this.p1, TradeMenu.this.p2);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void clean() {
        SUtil.delay(() -> {
            TradeMenu.player1TradeUUID.remove((Object)this.p1.getUniqueId());
            TradeMenu.player2TradeUUID.remove((Object)this.p2.getUniqueId());
            TradeMenu.tradeClose.remove((Object)this.tradeUUID);
            TradeMenu.tradeClosePlayerName.remove((Object)this.tradeUUID);
            TradeMenu.tradeP1Countdown.remove((Object)this.tradeUUID);
            TradeMenu.tradeP2Countdown.remove((Object)this.tradeUUID);
            TradeMenu.tradeP1Ready.remove((Object)this.tradeUUID);
            TradeMenu.tradeP2Ready.remove((Object)this.tradeUUID);
            TradeGUI.itemOfferP1.remove((Object)this.p1.getUniqueId());
            TradeGUI.itemOfferP2.remove((Object)this.p2.getUniqueId());
            TradeUtil.resetTrade(this.p1);
            TradeUtil.resetTrade(this.p2);
            TradeUtil.trading.put((Object)this.p1.getUniqueId(), (Object)false);
            TradeUtil.trading.put((Object)this.p2.getUniqueId(), (Object)false);
        }, 2L);
    }
    
    public void returnToAllPlayers(final Player player1, final Player player2) {
        for (final ItemStack i : (List)TradeGUI.itemOfferP1.get((Object)this.tradeUUID)) {
            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
            if (!nmsStack.getTag().hasKey("data_bits")) {
                Sputnik.smartGiveItem(i, player1);
            }
            else {
                User.getUser(player1.getUniqueId()).addBits(nmsStack.getTag().getLong("data_bits"));
            }
        }
        for (final ItemStack i : (List)TradeGUI.itemOfferP2.get((Object)this.tradeUUID)) {
            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
            if (!nmsStack.getTag().hasKey("data_bits")) {
                Sputnik.smartGiveItem(i, player2);
            }
            else {
                User.getUser(player2.getUniqueId()).addBits(nmsStack.getTag().getLong("data_bits"));
            }
        }
    }
    
    public Player getP1() {
        return this.p1;
    }
    
    public Player getP2() {
        return this.p2;
    }
    
    static {
        tradeClose = (Map)new HashMap();
        tradeClosePlayerName = (Map)new HashMap();
        tradeP1Countdown = (Map)new HashMap();
        tradeP2Countdown = (Map)new HashMap();
        tradeP1Ready = (Map)new HashMap();
        tradeP2Ready = (Map)new HashMap();
        successTrade = (Map)new HashMap();
        player1TradeUUID = (Map)new HashMap();
        player2TradeUUID = (Map)new HashMap();
    }
}
