package net.hypixel.skyblock.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import java.util.ArrayList;

public class AccessoryReforges extends GUI
{
    private static final int[] INTERIOR;
    
    public AccessoryReforges() {
        super("Accessory Reforges", 54);
        this.border(AccessoryReforges.BLACK_STAINED_GLASS_PANE);
        final List<SItem> stones = (List<SItem>)new ArrayList();
        for (final ReforgeType type : ReforgeType.values()) {
            if (type.getReforge().getCompatibleTypes().contains((Object)GenericItemType.ACCESSORY) && type.isAccessible()) {
                final SItem stone = SItem.of(SMaterial.REFORGE_STONE);
                stone.setReforge(type.getReforge());
                stone.setDisplayName("Reforge");
                stones.add((Object)stone);
            }
        }
        for (int i = 0; i < stones.size(); ++i) {
            final int slot = AccessoryReforges.INTERIOR[i];
            final SItem sItem = (SItem)stones.get(i);
            final ItemStack item = ((SItem)stones.get(i)).getStack();
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) throws IOException {
                    final Player player = (Player)e.getWhoClicked();
                    final List<SItem> accessories = PlayerUtils.getAccessories(player);
                    if (accessories == null) {
                        player.sendMessage(SUtil.color("&cYou do not have any accessories in your bag!"));
                        return;
                    }
                    player.sendMessage(SUtil.color("&aYou applied the " + sItem.getReforge().getName() + " reforge to all your accessories!"));
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 10.0f, 2.0f);
                    final List<ItemStack> stacks = (List<ItemStack>)new ArrayList();
                    for (final SItem accessory : accessories) {
                        if (accessory == null) {
                            continue;
                        }
                        accessory.setReforge(sItem.getReforge());
                        stacks.add((Object)accessory.getStack());
                    }
                }
                
                @Override
                public ItemStack getItem() {
                    return item;
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
            });
        }
        this.set(GUIClickableItem.getCloseItem(49));
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
