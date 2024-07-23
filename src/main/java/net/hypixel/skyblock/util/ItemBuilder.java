package net.hypixel.skyblock.util;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.Color;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder
{
    private ItemStack item;
    private final ItemMeta meta;
    
    public ItemBuilder() {
        this.item = new ItemStack(Material.DIRT);
        this.meta = this.item.getItemMeta();
    }
    
    public ItemBuilder(final ItemStack stack) {
        this.item = stack;
        this.meta = this.item.getItemMeta();
    }
    
    public ItemBuilder(final String name) {
        this.item = new ItemStack(Material.DIRT);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }
    
    public ItemBuilder(final String name, final int amount) {
        this.item = new ItemStack(Material.DIRT, amount);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder(final String name, final Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder(final String name, final Material material, final short dmg) {
        this.item = new ItemStack(material, 1, dmg);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder(final String name, final Material material, final int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder(final String name, final Material material, final int amount, final short damage) {
        this.item = new ItemStack(material, amount, damage);
        this.meta = this.item.getItemMeta();
        this.setDisplayName(name);
    }
    
    public ItemBuilder setDamage(final int damage) {
        this.item.setDurability((short)damage);
        return this;
    }
    
    public ItemBuilder setDamage(final short damage) {
        this.item.setDurability(damage);
        return this;
    }
    
    public ItemBuilder setAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }
    
    public ItemBuilder setDisplayName(final String name) {
        this.meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder setMaterial(final Material material) {
        this.item.setType(material);
        return this;
    }
    
    public ItemBuilder setLore(final List<String> lore) {
        this.meta.setLore((List)lore);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder setLore(final String... lore) {
        final List<String> ls = (List<String>)new ArrayList();
        for (final String s : lore) {
            ls.add((Object)ChatColor.translateAlternateColorCodes('&', s));
        }
        this.meta.setLore((List)ls);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addLore(final List<String> lore) {
        List<String> ls;
        if (this.meta.getLore() != null) {
            ls = (List<String>)this.meta.getLore();
        }
        else {
            ls = (List<String>)new ArrayList();
        }
        ls.addAll((Collection)lore);
        this.meta.setLore((List)ls);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addLore(final String lore) {
        List<String> ls;
        if (this.meta.getLore() != null) {
            ls = (List<String>)this.meta.getLore();
        }
        else {
            ls = (List<String>)new ArrayList();
        }
        ls.add((Object)ChatColor.translateAlternateColorCodes('&', lore));
        this.meta.setLore((List)ls);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addLore(final String... lore) {
        List<String> ls;
        if (this.meta.getLore() != null) {
            ls = (List<String>)this.meta.getLore();
        }
        else {
            ls = (List<String>)new ArrayList();
        }
        for (final String s : lore) {
            ls.add((Object)ChatColor.translateAlternateColorCodes('&', s));
        }
        this.meta.setLore((List)ls);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addEnchantment(final Enchantment enchantment) {
        this.meta.addEnchant(enchantment, 1, false);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addEnchantment(final Enchantment enchantment, final int level) {
        this.meta.addEnchant(enchantment, level, false);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addEnchantment(final Enchantment enchantment, final int level, final boolean ignoreMaxLevel) {
        this.meta.addEnchant(enchantment, level, ignoreMaxLevel);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder removeEnchantment(final Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addEnchantmentGlint() {
        this.meta.addEnchant(Enchantment.LUCK, 1, false);
        this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder removeEnchantmentGlint() {
        this.meta.removeEnchant(Enchantment.LUCK);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addItemFlag(final ItemFlag itemFlag) {
        this.meta.addItemFlags(new ItemFlag[] { itemFlag });
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder addItemFlags(final ItemFlag... itemFlags) {
        this.meta.addItemFlags(itemFlags);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder removeItemFlag(final ItemFlag itemFlag) {
        this.meta.removeItemFlags(new ItemFlag[] { itemFlag });
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder removeItemFlags(final ItemFlag... itemFlags) {
        this.meta.removeItemFlags(itemFlags);
        this.item.setItemMeta(this.meta);
        return this;
    }
    
    public ItemBuilder dyeColor(final Color color) {
        final LeatherArmorMeta leather = (LeatherArmorMeta)this.meta;
        leather.setColor(color);
        this.item.setItemMeta((ItemMeta)leather);
        return this;
    }
    
    public ItemBuilder addNBT(final String key, final String value) {
        final NBTItem nbt = new NBTItem(this.item);
        nbt.setString(key, value);
        this.item = nbt.getItem();
        return this;
    }
    
    public ItemBuilder addNBT(final String key, final int value) {
        final NBTItem nbt = new NBTItem(this.item);
        nbt.setInteger(key, Integer.valueOf(value));
        this.item = nbt.getItem();
        return this;
    }
    
    public ItemBuilder addNBT(final String key, final double value) {
        final NBTItem nbt = new NBTItem(this.item);
        nbt.setDouble(key, Double.valueOf(value));
        this.item = nbt.getItem();
        return this;
    }
    
    public ItemBuilder addNBT(final String key, final boolean value) {
        final NBTItem nbt = new NBTItem(this.item);
        nbt.setBoolean(key, Boolean.valueOf(value));
        this.item = nbt.getItem();
        return this;
    }
    
    public ItemBuilder setSkullID(final String id) {
        this.item = SUtil.idToSkull(this.item, id);
        return this;
    }
    
    public ItemStack toItemStack() {
        return this.item;
    }
}
