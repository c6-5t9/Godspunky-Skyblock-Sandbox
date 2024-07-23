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

public class AnvilReforgeGUI extends GUI
{
    private static final ItemStack ANVIL_BARRIER;
    private static final ItemStack DEFAULT_COMBINE_ITEMS;
    private static final String CANNOT_COMBINE;
    private boolean isApplied;
    
    public AnvilReforgeGUI() {
        super("Reforge Anvil", 54);
        this.isApplied = false;
        this.fill(AnvilReforgeGUI.BLACK_STAINED_GLASS_PANE);
        this.fill(AnvilReforgeGUI.RED_STAINED_GLASS_PANE, 45, 53);
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
                if (current == null) {
                    return;
                }
                if (e.getCurrentItem().getType() == Material.BARRIER) {
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
                        inventory.setItem(e.getSlot(), AnvilReforgeGUI.ANVIL_BARRIER);
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
                return AnvilReforgeGUI.ANVIL_BARRIER;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final HumanEntity entity = e.getWhoClicked();
                final Inventory inventory = e.getClickedInventory();
                final ItemStack upgrade = inventory.getItem(29);
                final ItemStack sacrifice = inventory.getItem(33);
                if (upgrade == null || sacrifice == null) {
                    entity.sendMessage(AnvilReforgeGUI.CANNOT_COMBINE);
                    return;
                }
                final SItem specUpgrade = SItem.find(upgrade);
                final SItem specSacrifice = SItem.find(sacrifice);
                if (inventory.getItem(11).getData().getData() == 14 || inventory.getItem(14).getData().getData() == 14) {
                    entity.sendMessage(AnvilReforgeGUI.CANNOT_COMBINE);
                    return;
                }
                if (specSacrifice.getType() == SMaterial.HOT_POTATO_BOOK && specUpgrade.getDataInt("hpb") == 9) {
                    entity.sendMessage(Sputnik.trans("&7You have already applied the maximum number of Hot Potato books to this item! &eFuming Hot Potato Book coming soon!"));
                }
                if (specSacrifice.getType() == SMaterial.ENCHANTED_BOOK) {
                    for (final Enchantment enchantment : specSacrifice.getEnchantments()) {
                        Skill.reward(EnchantingSkill.INSTANCE, enchantment.getLevel() * 2, (Player)e.getWhoClicked());
                    }
                }
                inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                AnvilReforgeGUI.this.isApplied = true;
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
                return AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS;
            }
        });
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player player = e.getPlayer();
                if (AnvilReforgeGUI.this != GUI.GUI_MAP.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = e.getInventory();
                AnvilReforgeGUI.this.update(inventory);
                if (inventory.getItem(11).getData().getData() == 14 || inventory.getItem(14).getData().getData() == 14) {
                    SUtil.border(inventory, AnvilReforgeGUI.this, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                    SUtil.border(inventory, AnvilReforgeGUI.this, SUtil.createColoredStainedGlassPane((short)14, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                }
                else {
                    SUtil.border(inventory, AnvilReforgeGUI.this, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 45, 48, true, false);
                    SUtil.border(inventory, AnvilReforgeGUI.this, SUtil.createColoredStainedGlassPane((short)5, (Object)ChatColor.RESET + " "), 50, 53, true, false);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
    }
    
    @Override
    public void update(final Inventory inventory) {
        new BukkitRunnable() {
            public void run() {
                if (inventory.getItem(13) == null) {
                    inventory.setItem(13, SUtil.getStack((Object)ChatColor.RED + "Loading...", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "If this appear for too long", (Object)ChatColor.GRAY + "contact admins!"));
                }
                if (inventory.getItem(29) == null && inventory.getItem(33) == null && inventory.getItem(13).getType() == Material.BARRIER && AnvilReforgeGUI.this.isApplied) {
                    AnvilReforgeGUI.this.isApplied = false;
                }
                if ((inventory.getItem(29) != null || inventory.getItem(33) != null) && AnvilReforgeGUI.this.isApplied) {
                    Sputnik.smartGiveItem(inventory.getItem(13), (Player)inventory.getViewers().get(0));
                    inventory.setItem(13, SUtil.getStack((Object)ChatColor.RED + "Loading...", Material.BARRIER, (short)0, 1, (Object)ChatColor.GRAY + "If this appear for too long", (Object)ChatColor.GRAY + "contact admins!"));
                    AnvilReforgeGUI.this.isApplied = false;
                }
                final ItemStack select = inventory.getItem(13);
                if (select != null && select.getType() != Material.BARRIER && SUtil.isAir(inventory.getItem(29)) && SUtil.isAir(inventory.getItem(33))) {
                    return;
                }
                final SItem specUpgrade = SItem.find(inventory.getItem(29));
                final SItem specSacrifice = SItem.find(inventory.getItem(33));
                boolean upgradeGreen = false;
                boolean sacrificeGreen = false;
                if (specSacrifice == null && specUpgrade != null) {
                    upgradeGreen = true;
                }
                if (specUpgrade != null) {
                    if (SUtil.isEnchantable(specUpgrade) || SUtil.isHotPotatoAble(specUpgrade)) {
                        upgradeGreen = true;
                    }
                    if (specUpgrade.getType() == SMaterial.HIDDEN_ETHERWARP_CONDUIT) {
                        upgradeGreen = true;
                    }
                    if (specUpgrade.getType() == SMaterial.HOT_POTATO_BOOK) {
                        upgradeGreen = false;
                    }
                }
                if (specSacrifice != null) {
                    if (SUtil.isEnchantable(specSacrifice)) {
                        sacrificeGreen = true;
                    }
                    else if (specSacrifice.getType() == SMaterial.HOT_POTATO_BOOK) {
                        sacrificeGreen = true;
                    }
                    else if (specSacrifice.getType().toString().contains((CharSequence)"HIDDEN_ETHERWARP")) {
                        sacrificeGreen = true;
                    }
                }
                if (specUpgrade != null && specSacrifice != null) {
                    if (!SUtil.isHotPotatoAble(specUpgrade) && specSacrifice.getType() == SMaterial.HOT_POTATO_BOOK) {
                        upgradeGreen = false;
                        sacrificeGreen = false;
                    }
                    if (!SUtil.isEnchantable(specUpgrade) && specSacrifice.getType() == SMaterial.ENCHANTED_BOOK) {
                        upgradeGreen = false;
                        sacrificeGreen = false;
                    }
                    if (specUpgrade.getType() == SMaterial.HIDDEN_ETHERWARP_CONDUIT && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_MERGER) {
                        upgradeGreen = true;
                        sacrificeGreen = true;
                    }
                    if ((specUpgrade.getType() == SMaterial.ASPECT_OF_THE_VOID || specUpgrade.getType() == SMaterial.ASPECT_OF_THE_END) && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_TRANSCODER) {
                        upgradeGreen = true;
                        sacrificeGreen = true;
                    }
                }
                setItemTo(true, upgradeGreen, inventory);
                setItemTo(false, sacrificeGreen, inventory);
                if (!upgradeGreen || !sacrificeGreen) {
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (specUpgrade.getType() == SMaterial.HIDDEN_ETHERWARP_CONDUIT && specSacrifice.getType() != SMaterial.HIDDEN_ETHERWARP_MERGER) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if ((specUpgrade.getType() == SMaterial.ASPECT_OF_THE_END || specUpgrade.getType() == SMaterial.ASPECT_OF_THE_VOID) && specSacrifice.getType() != SMaterial.HIDDEN_ETHERWARP_TRANSCODER && specSacrifice.getType() != SMaterial.ENCHANTED_BOOK && specSacrifice.getType() != SMaterial.HOT_POTATO_BOOK) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (specUpgrade.getType() != SMaterial.ASPECT_OF_THE_END && specUpgrade.getType() != SMaterial.ASPECT_OF_THE_VOID && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_TRANSCODER) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (specUpgrade.getType() != specSacrifice.getType() && specSacrifice.getType() != SMaterial.ENCHANTED_BOOK && specSacrifice.getType() != SMaterial.HOT_POTATO_BOOK && specSacrifice.getType() != SMaterial.HIDDEN_ETHERWARP_MERGER && specSacrifice.getType() != SMaterial.HIDDEN_ETHERWARP_TRANSCODER) {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if ((specUpgrade.getType() == SMaterial.ASPECT_OF_THE_END || specUpgrade.getType() == SMaterial.ASPECT_OF_THE_VOID) && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_TRANSCODER && specUpgrade.getDataString("etherwarp_trans") == "true") {
                    setItemTo(false, false, inventory);
                    inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                    inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                    return;
                }
                if (specUpgrade.getType() != SMaterial.ENCHANTED_BOOK && specSacrifice.getType() != SMaterial.HOT_POTATO_BOOK && !specSacrifice.getType().toString().contains((CharSequence)"ETHER")) {
                    for (final Enchantment enchantment : specSacrifice.getEnchantments()) {
                        if (!enchantment.getType().getCompatibleTypes().contains((Object)specUpgrade.getType().getStatistics().getSpecificType())) {
                            setItemTo(true, false, inventory);
                            inventory.setItem(13, AnvilReforgeGUI.ANVIL_BARRIER);
                            inventory.setItem(22, AnvilReforgeGUI.DEFAULT_COMBINE_ITEMS);
                            return;
                        }
                    }
                }
                SItem display = specUpgrade.clone();
                if (specSacrifice.getType() != SMaterial.HOT_POTATO_BOOK && !specSacrifice.getType().toString().contains((CharSequence)"ETHER")) {
                    for (final Enchantment enchantment2 : specSacrifice.getEnchantments()) {
                        final Enchantment equiv = display.getEnchantment(enchantment2.getType());
                        if (equiv != null && equiv.getLevel() == enchantment2.getLevel()) {
                            display.removeEnchantment(enchantment2.getType());
                            if (enchantment2.getType() == EnchantmentType.ONE_FOR_ALL || enchantment2.getType() == EnchantmentType.TELEKINESIS) {
                                display.addEnchantment(enchantment2.getType(), enchantment2.getLevel());
                            }
                            else if (enchantment2.getLevel() < 5) {
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
                    if (specSacrifice.getEnchantment(EnchantmentType.ONE_FOR_ALL) != null && specUpgrade.getType() != SMaterial.ENCHANTED_BOOK) {
                        for (final Enchantment enchantment2 : specUpgrade.getEnchantments()) {
                            if (enchantment2.getType() != EnchantmentType.TELEKINESIS) {
                                display.removeEnchantment(enchantment2.getType());
                                display.addEnchantment(EnchantmentType.ONE_FOR_ALL, specSacrifice.getEnchantment(EnchantmentType.ONE_FOR_ALL).getLevel());
                            }
                        }
                    }
                    if (display.getEnchantment(EnchantmentType.ONE_FOR_ALL) != null && display.getType() != SMaterial.ENCHANTED_BOOK && display.getType().getStatistics().getType() == GenericItemType.WEAPON) {
                        for (final Enchantment enchantment2 : display.getEnchantments()) {
                            if (enchantment2.getType() != EnchantmentType.TELEKINESIS && enchantment2.getType() != EnchantmentType.ONE_FOR_ALL) {
                                display.removeEnchantment(enchantment2.getType());
                            }
                        }
                    }
                    if (display.getType() != SMaterial.ENCHANTED_BOOK && display.getEnchantments() != null) {
                        final List<Enchantment> enchL = Enchantment.ultimateEnchantsListFromList(display.getEnchantments());
                        if (enchL.size() > 1) {
                            for (int i = 1; i < enchL.size(); ++i) {
                                display.removeEnchantment(((Enchantment)enchL.get(i)).getType());
                            }
                        }
                    }
                }
                else if (specSacrifice.getType() == SMaterial.HOT_POTATO_BOOK) {
                    display.setDataInt("hpb", display.getDataInt("hpb") + 1);
                }
                else if (specUpgrade.getType() == SMaterial.HIDDEN_ETHERWARP_CONDUIT && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_MERGER) {
                    display = SItem.of(SMaterial.HIDDEN_ETHERWARP_TRANSCODER);
                }
                else if ((specUpgrade.getType() == SMaterial.ASPECT_OF_THE_VOID || specUpgrade.getType() == SMaterial.ASPECT_OF_THE_END) && specSacrifice.getType() == SMaterial.HIDDEN_ETHERWARP_TRANSCODER) {
                    display.setDataString("etherwarp_trans", "true");
                }
                inventory.setItem(13, display.getStack());
                inventory.setItem(22, getCombineItemsForXP(0));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 0L);
    }
    
    private static void setItemTo(final boolean upgrade, final boolean green, final Inventory inventory) {
        for (final int i : upgrade ? Arrays.asList((Object[])new Integer[] { 11, 12, 20 }) : Arrays.asList((Object[])new Integer[] { 14, 15, 24 })) {
            inventory.setItem(i, SUtil.getSingleLoreStack((Object)ChatColor.GOLD + "Item to " + (upgrade ? "Upgrade" : "Sacrifice"), Material.STAINED_GLASS_PANE, (short)(green ? 5 : 14), 1, upgrade ? "The item you want to upgrade should be placed in the slot on this side." : "The item you are sacrificing in order to upgrade the item on the left should be placed in the slot on this side."));
        }
    }
    
    private static ItemStack getCombineItemsForXP(final int levels) {
        return SUtil.getStack((Object)ChatColor.GREEN + "Combine Items", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Combine the items in the slots", (Object)ChatColor.GRAY + "to the left and right below.", "", (Object)ChatColor.GRAY + "Cost", (Object)ChatColor.DARK_AQUA + "" + levels + " Exp Level" + ((levels != 1) ? "s" : ""), "", (Object)ChatColor.YELLOW + "Click to combine!");
    }
    
    static {
        ANVIL_BARRIER = SUtil.getSingleLoreStack((Object)ChatColor.RED + "Reforge Anvil", Material.BARRIER, (short)0, 1, "Place a target item in the left slot and a sacrifice item in the right slot to combine Reforge Stones!");
        DEFAULT_COMBINE_ITEMS = SUtil.getStack((Object)ChatColor.GREEN + "Combine Items", Material.ANVIL, (short)0, 1, (Object)ChatColor.GRAY + "Combine the items in the slots", (Object)ChatColor.GRAY + "to the left and right below.");
        CANNOT_COMBINE = (Object)ChatColor.RED + "These items cannot be combined!";
    }
}
