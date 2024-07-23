package net.hypixel.skyblock.gui;

import java.util.List;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import java.util.Iterator;
import org.bukkit.entity.HumanEntity;
import org.bukkit.Sound;
import net.hypixel.skyblock.features.skill.Skill;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.skill.EnchantingSkill;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;

public class AnvilGUI extends GUI implements BlockBasedGUI
{
    private static final ItemStack ANVIL_BARRIER;
    private static final ItemStack DEFAULT_COMBINE_ITEMS;
    private static final String CANNOT_COMBINE;
    private boolean isApplied;
    
    public AnvilGUI() {
        super("Anvil", 54);
        this.isApplied = false;
        this.fill(AnvilGUI.BLACK_STAINED_GLASS_PANE);
        this.fill(AnvilGUI.RED_STAINED_GLASS_PANE, 45, 53);
        this.set(GUIClickableItem.getCloseItem(49));
        for (final int i : Arrays.asList((Object[])new Integer[] { 11, 12, 20 })) {
            this.set(i, SUtil.getSingleLoreStack((Object)ChatColor.GOLD + "Item to Upgrade", Material.STAINED_GLASS_PANE, (short)14, 1, "The item you want to upgrade should be placed in the slot on this side."));
        }
        for (final int i : Arrays.asList((Object[])new Integer[] { 14, 15, 24 })) {
            this.set(i, SUtil.getSingleLoreStack((Object)ChatColor.GOLD + "Item to Sacrifice", Material.STAINED_GLASS_PANE, (short)14, 1, "The item you are sacrificing in order to upgrade the item on the left should be placed in the slot on this side."));
        }
        this.set(29, null);
        this.set(33, null);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final ItemStack current = e.getCurrentItem();
                if (null == current) {
                    return;
                }
                if (Material.BARRIER == e.getCurrentItem().getType()) {
                    e.setCancelled(true);
                    return;
                }
                final Inventory inventory = e.getClickedInventory();
                if (!SUtil.isAir(inventory.getItem(29)) || !SUtil.isAir(inventory.getItem(33))) {
                    e.setCancelled(true);
                    return;
                }
                new BukkitRunnable() {
                    public void run() {
                        inventory.setItem(e.getSlot(), AnvilGUI.ANVIL_BARRIER);
                    }
                }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public boolean canPickup() {
                return true;
            }
            
            @Override
            public ItemStack getItem() {
                return AnvilGUI.ANVIL_BARRIER;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final HumanEntity entity = e.getWhoClicked();
                final Inventory inventory = e.getClickedInventory();
                final ItemStack upgrade = inventory.getItem(29);
                final ItemStack sacrifice = inventory.getItem(33);
                if (null == upgrade || null == sacrifice) {
                    entity.sendMessage(AnvilGUI.CANNOT_COMBINE);
                    return;
                }
                final SItem specUpgrade = SItem.find(upgrade);
                final SItem specSacrifice = SItem.find(sacrifice);
                if (14 == inventory.getItem(11).getData().getData() || 14 == inventory.getItem(14).getData().getData()) {
                    entity.sendMessage(AnvilGUI.CANNOT_COMBINE);
                    return;
                }
                if (SMaterial.HOT_POTATO_BOOK == specSacrifice.getType() && 9 == specUpgrade.getDataInt("hpb")) {
                    entity.sendMessage(Sputnik.trans("&7You have already applied the maximum number of Hot Potato books to this item! &eFuming Hot Potato Book coming soon!"));
                }
                if (SMaterial.ENCHANTED_BOOK == specSacrifice.getType()) {
                    for (final Enchantment enchantment : specSacrifice.getEnchantments()) {
                        Skill.reward(EnchantingSkill.INSTANCE, enchantment.getLevel() * 2, (Player)e.getWhoClicked());
                    }
                }
                inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                AnvilGUI.this.isApplied = true;
                setItemTo(true, false, inventory);
                setItemTo(false, false, inventory);
                inventory.setItem(29, (ItemStack)null);
                inventory.setItem(33, (ItemStack)null);
                entity.getWorld().playSound(entity.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
            }
            
            @Override
            public int getSlot() {
                return 22;
            }
            
            @Override
            public ItemStack getItem() {
                return AnvilGUI.DEFAULT_COMBINE_ITEMS;
            }
        });
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player player = e.getPlayer();
                if (AnvilGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = e.getInventory();
                AnvilGUI.this.update(inventory);
                if (14 == inventory.getItem(11).getData().getData() || 14 == inventory.getItem(14).getData().getData()) {
                    SUtil.border(inventory, AnvilGUI.this, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                    SUtil.border(inventory, AnvilGUI.this, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                }
                else {
                    SUtil.border(inventory, AnvilGUI.this, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                    SUtil.border(inventory, AnvilGUI.this, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
    }
    
    @Override
    public void update(final Inventory inventory) {
        new BukkitRunnable() {
            public void run() {
                if (null == inventory.getItem(13)) {
                    inventory.setItem(13, SUtil.getStack((Object)ChatColor.RED + "Loading...", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "If this appear for too long", (Object)ChatColor.GRAY + "contact admins!"));
                }
                if (null == inventory.getItem(29) && null == inventory.getItem(33) && Material.BARRIER == inventory.getItem(13).getType() && AnvilGUI.this.isApplied) {
                    AnvilGUI.this.isApplied = false;
                }
                if ((null != inventory.getItem(29) || null != inventory.getItem(33)) && AnvilGUI.this.isApplied) {
                    Sputnik.smartGiveItem(inventory.getItem(13), (Player)inventory.getViewers().get(0));
                    inventory.setItem(13, SUtil.getStack((Object)ChatColor.RED + "Loading...", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "If this appear for too long", (Object)ChatColor.GRAY + "contact admins!"));
                    AnvilGUI.this.isApplied = false;
                }
                final ItemStack select = inventory.getItem(13);
                if (null != select && Material.BARRIER != select.getType() && SUtil.isAir(inventory.getItem(29)) && SUtil.isAir(inventory.getItem(33))) {
                    return;
                }
                final SItem specUpgrade = SItem.find(inventory.getItem(29));
                final SItem specSacrifice = SItem.find(inventory.getItem(33));
                boolean upgradeGreen = false;
                boolean sacrificeGreen = false;
                if (null == specSacrifice && null != specUpgrade) {
                    upgradeGreen = true;
                }
                if (null != specUpgrade) {
                    if (SUtil.isEnchantable(specUpgrade) || SUtil.isHotPotatoAble(specUpgrade)) {
                        upgradeGreen = true;
                    }
                    if (SMaterial.HIDDEN_ETHERWARP_CONDUIT == specUpgrade.getType()) {
                        upgradeGreen = true;
                    }
                    if (SMaterial.HOT_POTATO_BOOK == specUpgrade.getType()) {
                        upgradeGreen = false;
                    }
                }
                if (null != specSacrifice) {
                    if (SUtil.isEnchantable(specSacrifice)) {
                        sacrificeGreen = true;
                    }
                    else if (SMaterial.HOT_POTATO_BOOK == specSacrifice.getType()) {
                        sacrificeGreen = true;
                    }
                    else if (specSacrifice.getType().toString().contains((CharSequence)"HIDDEN_ETHERWARP")) {
                        sacrificeGreen = true;
                    }
                }
                if (null != specUpgrade && null != specSacrifice) {
                    if (!SUtil.isHotPotatoAble(specUpgrade) && SMaterial.HOT_POTATO_BOOK == specSacrifice.getType()) {
                        upgradeGreen = false;
                        sacrificeGreen = false;
                    }
                    if (!SUtil.isEnchantable(specUpgrade) && SMaterial.ENCHANTED_BOOK == specSacrifice.getType()) {
                        upgradeGreen = false;
                        sacrificeGreen = false;
                    }
                    if (SMaterial.HIDDEN_ETHERWARP_CONDUIT == specUpgrade.getType() && SMaterial.HIDDEN_ETHERWARP_MERGER == specSacrifice.getType()) {
                        upgradeGreen = true;
                        sacrificeGreen = true;
                    }
                    if ((SMaterial.ASPECT_OF_THE_VOID == specUpgrade.getType() || SMaterial.ASPECT_OF_THE_END == specUpgrade.getType()) && SMaterial.HIDDEN_ETHERWARP_TRANSCODER == specSacrifice.getType()) {
                        upgradeGreen = true;
                        sacrificeGreen = true;
                    }
                }
                setItemTo(true, upgradeGreen, inventory);
                setItemTo(false, sacrificeGreen, inventory);
                if (!upgradeGreen || !sacrificeGreen) {
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (SMaterial.HIDDEN_ETHERWARP_CONDUIT == specUpgrade.getType() && SMaterial.HIDDEN_ETHERWARP_MERGER != specSacrifice.getType()) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if ((SMaterial.ASPECT_OF_THE_END == specUpgrade.getType() || SMaterial.ASPECT_OF_THE_VOID == specUpgrade.getType()) && SMaterial.HIDDEN_ETHERWARP_TRANSCODER != specSacrifice.getType() && SMaterial.ENCHANTED_BOOK != specSacrifice.getType() && SMaterial.HOT_POTATO_BOOK != specSacrifice.getType()) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (SMaterial.ASPECT_OF_THE_END != specUpgrade.getType() && SMaterial.ASPECT_OF_THE_VOID != specUpgrade.getType() && SMaterial.HIDDEN_ETHERWARP_TRANSCODER == specSacrifice.getType()) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (specUpgrade.getType() != specSacrifice.getType() && SMaterial.ENCHANTED_BOOK != specSacrifice.getType() && SMaterial.HOT_POTATO_BOOK != specSacrifice.getType() && SMaterial.HIDDEN_ETHERWARP_MERGER != specSacrifice.getType() && SMaterial.HIDDEN_ETHERWARP_TRANSCODER != specSacrifice.getType()) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if ((SMaterial.ASPECT_OF_THE_END == specUpgrade.getType() || SMaterial.ASPECT_OF_THE_VOID == specUpgrade.getType()) && SMaterial.HIDDEN_ETHERWARP_TRANSCODER == specSacrifice.getType() && "true" == specUpgrade.getDataString("etherwarp_trans")) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (SMaterial.ENCHANTED_BOOK != specUpgrade.getType() && SMaterial.HOT_POTATO_BOOK != specSacrifice.getType() && !specSacrifice.getType().toString().contains((CharSequence)"ETHER")) {
                    for (final Enchantment enchantment : specSacrifice.getEnchantments()) {
                        if (!enchantment.getType().getCompatibleTypes().contains((Object)specUpgrade.getType().getStatistics().getSpecificType())) {
                            setItemTo(true, false, inventory);
                            inventory.setItem(13, AnvilGUI.ANVIL_BARRIER);
                            inventory.setItem(22, AnvilGUI.DEFAULT_COMBINE_ITEMS);
                            return;
                        }
                    }
                }
                SItem display = specUpgrade.clone();
                if (SMaterial.HOT_POTATO_BOOK != specSacrifice.getType() && !specSacrifice.getType().toString().contains((CharSequence)"ETHER")) {
                    for (final Enchantment enchantment2 : specSacrifice.getEnchantments()) {
                        final Enchantment equiv = display.getEnchantment(enchantment2.getType());
                        if (null != equiv && equiv.getLevel() == enchantment2.getLevel()) {
                            display.removeEnchantment(enchantment2.getType());
                            if (enchantment2.getType() == EnchantmentType.ONE_FOR_ALL || enchantment2.getType() == EnchantmentType.TELEKINESIS) {
                                display.addEnchantment(enchantment2.getType(), enchantment2.getLevel());
                            }
                            else if (5 > enchantment2.getLevel()) {
                                display.addEnchantment(enchantment2.getType(), enchantment2.getLevel() + 1);
                            }
                            else {
                                display.addEnchantment(enchantment2.getType(), enchantment2.getLevel());
                            }
                        }
                        else {
                            display.addEnchantment(enchantment2.getType(), enchantment2.getLevel());
                        }
                    }
                    if (null != specSacrifice.getEnchantment(EnchantmentType.ONE_FOR_ALL) && SMaterial.ENCHANTED_BOOK != specUpgrade.getType()) {
                        for (final Enchantment enchantment2 : specUpgrade.getEnchantments()) {
                            if (enchantment2.getType() != EnchantmentType.TELEKINESIS) {
                                display.removeEnchantment(enchantment2.getType());
                                display.addEnchantment(EnchantmentType.ONE_FOR_ALL, specSacrifice.getEnchantment(EnchantmentType.ONE_FOR_ALL).getLevel());
                            }
                        }
                    }
                    if (null != display.getEnchantment(EnchantmentType.ONE_FOR_ALL) && SMaterial.ENCHANTED_BOOK != display.getType() && GenericItemType.WEAPON == display.getType().getStatistics().getType()) {
                        for (final Enchantment enchantment2 : display.getEnchantments()) {
                            if (enchantment2.getType() != EnchantmentType.TELEKINESIS && enchantment2.getType() != EnchantmentType.ONE_FOR_ALL) {
                                display.removeEnchantment(enchantment2.getType());
                            }
                        }
                    }
                    if (SMaterial.ENCHANTED_BOOK != display.getType() && null != display.getEnchantments()) {
                        final List<Enchantment> enchL = Enchantment.ultimateEnchantsListFromList(display.getEnchantments());
                        if (1 < enchL.size()) {
                            for (int i = 1; i < enchL.size(); ++i) {
                                display.removeEnchantment(((Enchantment)enchL.get(i)).getType());
                            }
                        }
                    }
                }
                else if (SMaterial.HOT_POTATO_BOOK == specSacrifice.getType()) {
                    display.setDataInt("hpb", display.getDataInt("hpb") + 1);
                }
                else if (SMaterial.HIDDEN_ETHERWARP_CONDUIT == specUpgrade.getType() && SMaterial.HIDDEN_ETHERWARP_MERGER == specSacrifice.getType()) {
                    display = SItem.of(SMaterial.HIDDEN_ETHERWARP_TRANSCODER);
                }
                else if ((SMaterial.ASPECT_OF_THE_VOID == specUpgrade.getType() || SMaterial.ASPECT_OF_THE_END == specUpgrade.getType()) && SMaterial.HIDDEN_ETHERWARP_TRANSCODER == specSacrifice.getType()) {
                    display.setDataString("etherwarp_trans", "true");
                }
                inventory.setItem(13, display.getStack());
                inventory.setItem(22, getCombineItemsForXP(0));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 0L);
    }
    
    @Override
    public Material getBlock() {
        return Material.ANVIL;
    }
    
    private static void setItemTo(final boolean upgrade, final boolean green, final Inventory inventory) {
        for (final int i : upgrade ? Arrays.asList((Object[])new Integer[] { 11, 12, 20 }) : Arrays.asList((Object[])new Integer[] { 14, 15, 24 })) {
            inventory.setItem(i, SUtil.getSingleLoreStack((Object)ChatColor.GOLD + "Item to " + (upgrade ? "Upgrade" : "Sacrifice"), Material.STAINED_GLASS_PANE, (short)(green ? 5 : 14), 1, upgrade ? "The item you want to upgrade should be placed in the slot on this side." : "The item you are sacrificing in order to upgrade the item on the left should be placed in the slot on this side."));
        }
    }
    
    private static ItemStack getCombineItemsForXP(final int levels) {
        return SUtil.getStack((Object)ChatColor.GREEN + "Combine Items", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Combine the items in the slots", (Object)ChatColor.GRAY + "to the left and right below.", "", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.DARK_AQUA + "" + levels + " Exp Level" + ((1 != levels) ? "s" : ""), "", (Object)ChatColor.YELLOW + "Click to combine!");
    }
    
    static {
        ANVIL_BARRIER = SUtil.getSingleLoreStack((Object)ChatColor.RED + "Anvil", Material.BARRIER, (short)0, 1, "Place a target item in the left slot and a sacrifice item in the right slot to combine Enchantments!");
        DEFAULT_COMBINE_ITEMS = SUtil.getStack((Object)ChatColor.GREEN + "Combine Items", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Combine the items in the slots", (Object)ChatColor.GRAY + "to the left and right below.");
        CANNOT_COMBINE = (Object)ChatColor.RED + "These items cannot be combined!";
    }
}
