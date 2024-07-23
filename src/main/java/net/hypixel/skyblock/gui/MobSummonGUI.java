package net.hypixel.skyblock.gui;

import java.lang.reflect.Field;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.Sputnik;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.PaginationList;

public class MobSummonGUI extends GUI
{
    private static final int[] INTERIOR;
    
    public MobSummonGUI(final String query, int page) {
        super("Mob Browser", 54);
        this.border(MobSummonGUI.BLACK_STAINED_GLASS_PANE);
        final PaginationList<ItemStack> pagedMaterials = new PaginationList<ItemStack>(28);
        for (final SEntityType type : SEntityType.values()) {
            pagedMaterials.add((Object)this.createIcon(type));
        }
        if (!query.equals((Object)"")) {
            pagedMaterials.removeIf(type -> !type.getItemMeta().getDisplayName().toLowerCase().contains((CharSequence)query));
        }
        if (pagedMaterials.size() == 0) {
            page = 0;
        }
        this.set(GUIClickableItem.getCloseItem(50));
        this.title = "Mob Browser (" + page + "/" + pagedMaterials.getPageCount() + ")";
        if (page > 1) {
            final int finalPage = page;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new MobSummonGUI(finalPage - 1).open((Player)e.getWhoClicked());
                    ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.NOTE_PIANO, 1.0f, 1.0f);
                }
                
                @Override
                public int getSlot() {
                    return 45;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Pervious Page");
                }
            });
        }
        if (page != pagedMaterials.getPageCount()) {
            final int finalPage2 = page;
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new MobSummonGUI(finalPage2 + 1).open((Player)e.getWhoClicked());
                    ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.NOTE_PIANO, 1.0f, 1.0f);
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GRAY + "Next Page");
                }
            });
            this.set(new GUIQueryItem() {
                @Override
                public GUI onQueryFinish(final String query) {
                    return new MobSummonGUI(query);
                }
                
                @Override
                public void run(final InventoryClickEvent e) {
                }
                
                @Override
                public int getSlot() {
                    return 48;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.SIGN, (Object)ChatColor.GREEN + "Search");
                }
            });
            final List<ItemStack> p = pagedMaterials.getPage(page);
            if (p == null) {
                return;
            }
            for (int i = 0; i < p.size(); ++i) {
                final int slot = MobSummonGUI.INTERIOR[i];
                final ItemStack stack = (ItemStack)p.get(i);
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        final ItemStack stack = e.getCurrentItem();
                        final String name = MobSummonGUI.this.deserilize(stack.getItemMeta().getDisplayName());
                        final SEntityType type = SEntityType.getEntityType(name);
                        final SEntity entity = new SEntity((Entity)e.getWhoClicked(), type, new Object[0]);
                        e.getWhoClicked().closeInventory();
                    }
                    
                    @Override
                    public int getSlot() {
                        return slot;
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return stack;
                    }
                });
            }
        }
    }
    
    public MobSummonGUI(final String query) {
        this(query, 1);
    }
    
    public MobSummonGUI(final int page) {
        this("", page);
    }
    
    public MobSummonGUI() {
        this(1);
    }
    
    private ItemStack createIcon(final SEntityType type) {
        final ItemStack stack = getEntitySkull(type.getCraftType());
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(this.serilize(type.name()));
        final ArrayList<String> lore = (ArrayList<String>)new ArrayList();
        try {
            lore.add((Object)((Object)ChatColor.GRAY + "Health : " + (Object)ChatColor.RED + Sputnik.formatFull((float)type.getStatistics().getEntityMaxHealth()) + " \u2764"));
            lore.add((Object)((Object)ChatColor.GRAY + "Damage : " + (Object)ChatColor.RED + Sputnik.formatFull((float)type.getStatistics().getDamageDealt())));
            if (type.getStatistics().getMovementSpeed() > 0.0) {
                type.getStatistics().getMovementSpeed();
            }
            lore.add((Object)((Object)ChatColor.GRAY + "Level : " + (Object)ChatColor.BLUE + type.getStatistics().mobLevel()));
        }
        catch (final NullPointerException ex) {}
        lore.add((Object)" ");
        lore.add((Object)((Object)ChatColor.GOLD + "Click to summon"));
        meta.setLore((List)lore);
        stack.setItemMeta(meta);
        return stack;
    }
    
    private String serilize(final String name) {
        return name.replace((CharSequence)"_", (CharSequence)" ");
    }
    
    private String deserilize(final String name) {
        return name.replace((CharSequence)" ", (CharSequence)"_");
    }
    
    public static ItemStack getEntitySkull(final EntityType entityType) {
        final ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta skullMeta = (SkullMeta)skullItem.getItemMeta();
        skullMeta.setOwner(entityType.getName());
        skullItem.setItemMeta((ItemMeta)skullMeta);
        return skullItem;
    }
    
    public static ItemStack getSkull(final String texture, final String signature) {
        final ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta skullMeta = (SkullMeta)skullItem.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
        profile.getProperties().put((Object)"textures", (Object)new Property("textures", texture, signature));
        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set((Object)skullMeta, (Object)profile);
        }
        catch (final NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skullItem.setItemMeta((ItemMeta)skullMeta);
        return skullItem;
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
