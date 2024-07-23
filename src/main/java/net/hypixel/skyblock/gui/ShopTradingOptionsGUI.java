package net.hypixel.skyblock.gui;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SItem;

public class ShopTradingOptionsGUI extends GUI
{
    private final SItem item;
    private final GUI ret;
    
    public ShopTradingOptionsGUI(final SItem item, final GUI ret) {
        super("Shop Trading Options", 54);
        this.item = item;
        this.ret = ret;
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        this.fill(ShopTradingOptionsGUI.BLACK_STAINED_GLASS_PANE);
        this.set(createTrade(this.item, 20, 1, player));
        this.set(createTrade(this.item, 21, 5, player));
        this.set(createTrade(this.item, 22, 10, player));
        this.set(createTrade(this.item, 23, 32, player));
        this.set(createTrade(this.item, 24, 64, player));
        this.set(GUIClickableItem.createGUIOpenerItem(this.ret, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (short)0, (Object)ChatColor.GRAY + "To " + this.ret.getTitle()));
        this.set(GUIClickableItem.getCloseItem(49));
    }
    
    private static GUIClickableItem createTrade(final SItem item, final int slot, final int amount, final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final SItem display = item.clone();
        display.getStack().setAmount(amount);
        final ItemMeta meta = display.getStack().getItemMeta();
        if (amount != 1) {
            meta.setDisplayName(meta.getDisplayName() + (Object)ChatColor.DARK_GRAY + " x" + amount);
        }
        final List<String> lore = (List<String>)meta.getLore();
        lore.add((Object)" ");
        lore.add((Object)((Object)ChatColor.GRAY + "Cost"));
        if (item.getPrice() == null) {
            return null;
        }
        final long price = item.getPrice() * amount;
        lore.add((Object)((Object)ChatColor.GOLD + SUtil.commaify(price) + " Coin" + ((price != 1L) ? "s" : "")));
        lore.add((Object)" ");
        lore.add((Object)((Object)ChatColor.YELLOW + "Click to purchase!"));
        meta.setLore((List)lore);
        display.getStack().setItemMeta(meta);
        return new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                if (price > user.getCoins()) {
                    player.sendMessage((Object)ChatColor.RED + "You don't have enough coins!");
                    return;
                }
                final Map<Integer, ItemStack> m = (Map<Integer, ItemStack>)player.getInventory().addItem(new ItemStack[] { SUtil.setSItemAmount(item.clone(), amount).getStack() });
                if (m.size() != 0) {
                    player.sendMessage((Object)ChatColor.RED + "Free up inventory space to purchase this!");
                    return;
                }
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
                user.subCoins(price);
            }
            
            @Override
            public int getSlot() {
                return slot;
            }
            
            @Override
            public ItemStack getItem() {
                return display.getStack();
            }
        };
    }
}
