package net.hypixel.skyblock.item;

import java.util.Arrays;
import org.bukkit.Sound;
import org.bukkit.Location;
import java.util.Set;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SerialNBTTagCompound;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Color;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import org.bukkit.Material;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import net.hypixel.skyblock.features.reforge.Reforge;
import net.hypixel.skyblock.features.potion.PotionColor;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.potion.PotionEffectType;
import net.hypixel.skyblock.features.potion.PotionEffect;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SItem implements Cloneable, ConfigurationSerializable
{
    private static final List<String> GLOBAL_NBT_TAGS;
    private static final List<String> GLOBAL_DATA_KEYS;
    private final SMaterial type;
    private final short variant;
    private final ItemStack stack;
    private final ItemLore lore;
    private Rarity rarity;
    private ItemOrigin origin;
    private boolean recombobulated;
    private final NBTTagCompound data;
    
    protected SItem(final SMaterial type, final short variant, final ItemStack stack, final Rarity rarity, final ItemOrigin origin, final boolean recombobulated, final NBTTagCompound data, final boolean overwrite) {
        this.type = type;
        this.variant = variant;
        this.stack = stack;
        this.rarity = rarity;
        this.data = data;
        this.lore = new ItemLore(this);
        this.origin = origin;
        this.recombobulated = recombobulated;
        if (overwrite) {
            final ItemMeta meta = this.stack.getItemMeta();
            if (!(type.getStatistics() instanceof LoreableMaterialStatistics)) {
                meta.setLore((List)this.lore.asBukkitLore());
            }
            else {
                meta.setLore((List)((LoreableMaterialStatistics)type.getStatistics()).getCustomLore(this));
            }
            meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS });
            meta.spigot().setUnbreakable(true);
            this.stack.setItemMeta(meta);
            this.update();
        }
    }
    
    public void setBonusDefense(final Integer nextInteger) {
        this.data.setInt("bonusDefense", (int)nextInteger);
        this.update();
    }
    
    public void setNextDefense(final Integer nextInteger) {
        this.data.setInt("nextDefense", (int)nextInteger);
        this.update();
    }
    
    public void setRequiredKills(final Integer kills) {
        this.data.setInt("requiredKills", (int)kills);
        this.update();
    }
    
    public void setProgressKills(final Integer kills) {
        this.data.setInt("progressKills", (int)kills);
    }
    
    public int getBonusDefense() {
        return this.data.getInt("bonusDefense");
    }
    
    public int getRequiredKills() {
        return this.data.getInt("requiredKills");
    }
    
    public int getNextDefense() {
        return this.data.getInt("nextDefense");
    }
    
    public int getProgressKills() {
        return this.data.getInt("progressKills");
    }
    
    public void enchant(final boolean enchant) {
        if (enchant) {
            if (this.stack.getItemMeta().hasEnchants()) {
                return;
            }
            final ItemMeta meta = this.stack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.stack.setItemMeta(meta);
        }
        else {
            if (!this.stack.getItemMeta().hasEnchants()) {
                return;
            }
            final ItemMeta meta = this.stack.getItemMeta();
            meta.removeEnchant(Enchantment.DURABILITY);
            this.stack.setItemMeta(meta);
        }
    }
    
    public boolean addEnchantment(final EnchantmentType type, final int level) {
        if (!this.isEnchantable()) {
            return false;
        }
        final List<net.hypixel.skyblock.features.enchantment.Enchantment> enchantments = this.getEnchantments();
        final net.hypixel.skyblock.features.enchantment.Enchantment enchantment = new net.hypixel.skyblock.features.enchantment.Enchantment(type, level);
        this.removeEnchantment(type);
        enchantments.add((Object)enchantment);
        if (null != type.getVanilla()) {
            final ItemMeta meta = this.stack.getItemMeta();
            meta.addEnchant(type.getVanilla(), level, true);
            this.stack.setItemMeta(meta);
        }
        final NBTTagCompound es = this.data.getCompound("enchantments");
        for (final net.hypixel.skyblock.features.enchantment.Enchantment e : enchantments) {
            es.setInt(e.getType().getNamespace(), e.getLevel());
        }
        this.data.set("enchantments", (NBTBase)es);
        this.update();
        return true;
    }
    
    public boolean removeEnchantment(final EnchantmentType type) {
        if (!this.isEnchantable()) {
            return false;
        }
        final List<net.hypixel.skyblock.features.enchantment.Enchantment> enchantments = this.getEnchantments();
        final boolean removeIf = enchantments.removeIf(e -> e.getType().equals(type));
        if (null != type.getVanilla()) {
            final ItemMeta meta = this.stack.getItemMeta();
            meta.removeEnchant(type.getVanilla());
            this.stack.setItemMeta(meta);
        }
        final NBTTagCompound es = new NBTTagCompound();
        for (final net.hypixel.skyblock.features.enchantment.Enchantment enchantment : enchantments) {
            es.setInt(enchantment.getType().getNamespace(), enchantment.getLevel());
        }
        this.data.set("enchantments", (NBTBase)es);
        this.update();
        return removeIf;
    }
    
    public Long getPrice() {
        if (!this.data.hasKey("price")) {
            return null;
        }
        return this.data.getLong("price");
    }
    
    public Long getItemValue() {
        if (!this.data.hasKey("itemValue")) {
            return null;
        }
        return this.data.getLong("itemValue");
    }
    
    public void setItemValue(final Long value) {
        this.data.setLong("itemValue", (long)value);
        this.update();
    }
    
    public void setPrice(final Long value) {
        this.data.setLong("price", (long)value);
        this.update();
    }
    
    public boolean hasEnchantment(final EnchantmentType type) {
        if (!this.isEnchantable()) {
            return false;
        }
        final List<net.hypixel.skyblock.features.enchantment.Enchantment> enchantments = this.getEnchantments();
        for (final net.hypixel.skyblock.features.enchantment.Enchantment enchantment : enchantments) {
            if (enchantment.getType() == type) {
                return true;
            }
        }
        return false;
    }
    
    public net.hypixel.skyblock.features.enchantment.Enchantment getEnchantment(final EnchantmentType type) {
        if (!this.isEnchantable()) {
            return null;
        }
        final List<net.hypixel.skyblock.features.enchantment.Enchantment> enchantments = this.getEnchantments();
        for (final net.hypixel.skyblock.features.enchantment.Enchantment enchantment : enchantments) {
            if (enchantment.getType() == type) {
                return enchantment;
            }
        }
        return null;
    }
    
    public List<net.hypixel.skyblock.features.enchantment.Enchantment> getEnchantments() {
        if (!this.isEnchantable()) {
            return null;
        }
        final NBTTagCompound es = this.data.hasKey("enchantments") ? this.data.getCompound("enchantments") : new NBTTagCompound();
        final List<net.hypixel.skyblock.features.enchantment.Enchantment> enchantments = (List<net.hypixel.skyblock.features.enchantment.Enchantment>)new ArrayList();
        for (final String key : es.c()) {
            enchantments.add((Object)new net.hypixel.skyblock.features.enchantment.Enchantment(EnchantmentType.getByNamespace(key), es.getInt(key)));
        }
        return enchantments;
    }
    
    public boolean addPotionEffect(final PotionEffect effect) {
        if (!this.isPotion()) {
            return false;
        }
        final List<PotionEffect> effects = this.getPotionEffects();
        this.removePotionEffect(effect.getType());
        effects.add((Object)effect);
        final NBTTagCompound es = this.data.getCompound("effects");
        for (final PotionEffect e : effects) {
            es.set(e.getType().getNamespace(), (NBTBase)e.toCompound());
        }
        this.data.set("effects", (NBTBase)es);
        this.update();
        return true;
    }
    
    public boolean removePotionEffect(final PotionEffectType type) {
        if (!this.isPotion()) {
            return false;
        }
        final List<PotionEffect> effects = this.getPotionEffects();
        final boolean removeIf = effects.removeIf(e -> e.getType().equals(type));
        final PotionColor top = SUtil.getTopColor(this);
        this.stack.setDurability((short)((null != top) ? (this.isSplashPotion() ? top.getSplashData() : top.getData()) : 0));
        final NBTTagCompound es = this.data.getCompound("effects");
        for (final PotionEffect e : effects) {
            es.set(e.getType().getNamespace(), (NBTBase)e.toCompound());
        }
        this.data.set("effects", (NBTBase)es);
        this.update();
        return removeIf;
    }
    
    public PotionEffect getPotionEffect(final PotionEffectType type) {
        if (!this.isPotion()) {
            return null;
        }
        final List<PotionEffect> effects = this.getPotionEffects();
        for (final PotionEffect effect : effects) {
            if (effect.getType() == type) {
                return effect;
            }
        }
        return null;
    }
    
    public List<PotionEffect> getPotionEffects() {
        if (!this.isPotion()) {
            return null;
        }
        final NBTTagCompound es = this.data.hasKey("effects") ? this.data.getCompound("effects") : new NBTTagCompound();
        final List<PotionEffect> effects = (List<PotionEffect>)new ArrayList();
        for (final String key : es.c()) {
            effects.add((Object)PotionEffect.ofCompound(key, es.getCompound(key)));
        }
        return effects;
    }
    
    public boolean isEnchantable() {
        return this.type.getGenericInstance() instanceof Enchantable;
    }
    
    public boolean isReforgable() {
        return this.type.getGenericInstance() instanceof Reforgable;
    }
    
    public boolean isStarrable() {
        return GenericItemType.WEAPON == this.getType().getStatistics().getType() || GenericItemType.ARMOR == this.getType().getStatistics().getType() || GenericItemType.RANGED_WEAPON == this.getType().getStatistics().getType();
    }
    
    public boolean isDungeonsItem() {
        return this.getDataBoolean("dungeons_item");
    }
    
    public boolean isPotion() {
        return SMaterial.WATER_BOTTLE == this.type;
    }
    
    public int getStar() {
        return this.getDataInt("itemStar");
    }
    
    public boolean isSplashPotion() {
        return this.isPotion() && this.data.getBoolean("splash");
    }
    
    public void setAnvilUses(final int anvilUses) {
        if (!(this.type.getGenericInstance() instanceof Enchantable)) {
            throw new UnsupportedOperationException("You cannot set the anvil uses on an unenchantable item");
        }
        this.data.setInt("anvil", anvilUses);
        this.update();
    }
    
    public void setKills(final Integer kills) {
        this.data.setInt("kills", (int)kills);
        this.update();
    }
    
    public void setHPBs(final Integer hpbs) {
        this.data.setInt("hpb", (int)hpbs);
        this.update();
    }
    
    public int getHPBs() {
        return this.getDataInt("hpb");
    }
    
    public void setCoinsBid(final Integer coins) {
        if (!this.type.getStatistics().displayCoins()) {
            throw new UnsupportedOperationException("You cannot display coins bidded on this item");
        }
        this.data.setInt("coins", (int)coins);
        this.update();
    }
    
    public void setRarity(final Rarity rarity, final boolean instanceUpdate) {
        this.rarity = rarity;
        this.update(instanceUpdate);
    }
    
    public void setRarity(final Rarity rarity) {
        this.setRarity(rarity, true);
    }
    
    public void setAmount(final int amount) {
        this.stack.setAmount(amount);
    }
    
    public void upgradeRarity() {
        this.rarity = this.rarity.upgrade();
        this.update();
    }
    
    public void downgradeRarity() {
        this.rarity = this.rarity.downgrade();
        this.update();
    }
    
    public void setReforge(final Reforge reforge) {
        if (!(this.type.getGenericInstance() instanceof Reforgable)) {
            throw new UnsupportedOperationException("You cannot set the reforge of an unreforgable item");
        }
        this.data.setString("reforge", ReforgeType.getByClass(reforge.getClass()).name());
        this.update();
    }
    
    public void setOrigin(final ItemOrigin origin) {
        this.origin = origin;
        this.update();
    }
    
    public void setRecombobulated(final boolean recombobulated) {
        this.recombobulated = recombobulated;
        if (recombobulated) {
            this.setRarity(this.type.getStatistics().getRarity().upgrade());
        }
        else {
            this.setRarity(this.type.getStatistics().getRarity());
        }
        this.update();
    }
    
    public Reforge getReforge() {
        if (!(this.type.getGenericInstance() instanceof Reforgable)) {
            return null;
        }
        if (!this.data.hasKey("reforge")) {
            return null;
        }
        return ReforgeType.getReforgeType(this.data.getString("reforge")).getReforge();
    }
    
    public String getFullName() {
        return (Object)this.rarity.getColor() + (this.data.hasKey("reforge") ? (ReforgeType.getReforgeType(this.data.getString("reforge")).getReforge().getName() + " ") : "") + this.getType().getDisplayName(this.variant);
    }
    
    public String getDisplayName() {
        return this.getType().getDisplayName(this.variant);
    }
    
    public boolean isReforged() {
        return this.data.hasKey("reforge");
    }
    
    public String getDataString(final String key) {
        return this.data.getString(key);
    }
    
    public void setDungeonsItem(final boolean bol) {
        this.setDataBoolean("dungeons_item", bol);
    }
    
    public int getDataInt(final String key) {
        return this.data.getInt(key);
    }
    
    public long getDataLong(final String key) {
        return this.data.getLong(key);
    }
    
    public boolean getDataBoolean(final String key) {
        return this.data.getBoolean(key);
    }
    
    public NBTTagCompound getDataCompound(final String key) {
        return this.data.getCompound(key);
    }
    
    public void setDataString(final String key, final String value) {
        this.data.setString(key, value);
        this.update();
    }
    
    public void setDataInt(final String key, final int value) {
        this.data.setInt(key, value);
        this.update();
    }
    
    public void setDataDouble(final String key, final double value) {
        this.data.setDouble(key, value);
        this.update();
    }
    
    public void setDataFloat(final String key, final float value) {
        this.data.setFloat(key, value);
        this.update();
    }
    
    public void setDataLong(final String key, final long value) {
        this.data.setLong(key, value);
        this.update();
    }
    
    public void setDataBoolean(final String key, final boolean value) {
        this.data.setBoolean(key, value);
        this.update();
    }
    
    public void setDataCompound(final String key, final NBTTagCompound value) {
        this.data.set(key, (NBTBase)value);
        this.update();
    }
    
    public void removeData(final String key) {
        this.data.remove(key);
        this.update();
    }
    
    public boolean hasDataFor(final String key) {
        return this.data.hasKey(key);
    }
    
    public void setDisplayName(final String name) {
        Reforge reforge = null;
        if (this.data.hasKey("reforge")) {
            reforge = ReforgeType.getReforgeType(this.data.getString("reforge")).getReforge();
        }
        final ItemMeta meta = this.stack.getItemMeta();
        if (null == reforge) {
            meta.setDisplayName((Object)this.rarity.getColor() + name + Sputnik.createStarStringFrom(this));
        }
        else {
            meta.setDisplayName((Object)this.rarity.getColor() + reforge.getName() + " " + name + Sputnik.createStarStringFrom(this));
        }
        this.stack.setItemMeta(meta);
    }
    
    public void update(final boolean instanceUpdate) {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.stack);
        if (null == nmsStack) {
            return;
        }
        final NBTTagCompound compound = (null != nmsStack.getTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.remove("type");
        compound.remove("variant");
        compound.remove("ExtraAttributes");
        compound.remove("enchantments");
        compound.remove("anvil");
        compound.remove("rarity");
        compound.remove("kills");
        compound.remove("coins");
        compound.remove("reforge");
        compound.remove("origin");
        compound.remove("recombobulated");
        for (final String key : this.data.c()) {
            compound.remove(key);
            if (!this.data.get(key).isEmpty()) {
                compound.set(key, this.data.get(key));
            }
        }
        compound.remove("amount");
        compound.setString("type", this.type.name());
        final NBTTagCompound extraAttributesTag = new NBTTagCompound();
        extraAttributesTag.setString("id", this.type.name());
        compound.set("ExtraAttributes", (NBTBase)extraAttributesTag);
        if (0 != this.variant) {
            compound.setShort("variant", this.variant);
        }
        if (this.rarity != this.type.getStatistics().getRarity()) {
            compound.setString("rarity", this.rarity.name());
        }
        if (ItemOrigin.UNKNOWN != this.origin) {
            compound.setString("origin", this.origin.name());
        }
        if (this.recombobulated) {
            compound.setBoolean("recombobulated", true);
        }
        if (!this.getType().getStatistics().isStackable() && !compound.hasKey("uuid")) {
            compound.setString("uuid", UUID.randomUUID().toString());
        }
        nmsStack.setTag(compound);
        this.stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        final ItemMeta meta = this.stack.getItemMeta();
        final MaterialStatistics statistics = this.type.getStatistics();
        Reforge reforge = null;
        if (this.data.hasKey("reforge")) {
            reforge = ReforgeType.getReforgeType(this.data.getString("reforge")).getReforge();
        }
        if (null == reforge) {
            meta.setDisplayName((Object)this.rarity.getColor() + this.type.getDisplayName(this.variant) + Sputnik.createStarStringFrom(this));
        }
        else {
            meta.setDisplayName((Object)this.rarity.getColor() + reforge.getName() + " " + this.type.getDisplayName(this.variant) + Sputnik.createStarStringFrom(this));
        }
        if (this.isPotion() && 0 < this.getPotionEffects().size()) {
            this.stack.setDurability(this.isSplashPotion() ? SUtil.getTopColor(this).getSplashData() : SUtil.getTopColor(this).getData());
        }
        if (!(statistics instanceof LoreableMaterialStatistics)) {
            meta.setLore((List)this.lore.asBukkitLore());
        }
        else {
            meta.setLore((List)((LoreableMaterialStatistics)statistics).getCustomLore(this));
        }
        this.stack.setItemMeta(meta);
        if (this.type.getGenericInstance() instanceof Enchantable || statistics.isEnchanted()) {
            this.enchant(0 != this.data.getCompound("enchantments").c().size() || statistics.isEnchanted());
        }
        final MaterialFunction function = this.type.getFunction();
        if (null != function && instanceUpdate) {
            this.type.getFunction().onInstanceUpdate(this);
        }
    }
    
    public void update() {
        this.update(true);
    }
    
    public static SItem of(final SMaterial specMaterial, final short variant, final ItemOrigin origin) {
        final ItemStack stack = new ItemStack(specMaterial.getCraftMaterial(), 1, variant);
        final MaterialStatistics statistics = specMaterial.getStatistics();
        if (Material.SKULL_ITEM == specMaterial.getCraftMaterial() && statistics instanceof SkullStatistics) {
            stack.setDurability((short)3);
            SUtil.getSkull(((SkullStatistics)statistics).getURL(), stack, specMaterial);
        }
        if (statistics instanceof LeatherArmorStatistics) {
            SUtil.applyColorToLeatherArmor(stack, Color.fromRGB(((LeatherArmorStatistics)statistics).getColor()));
        }
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName((Object)statistics.getRarity().getColor() + specMaterial.getDisplayName(variant));
        stack.setItemMeta(meta);
        return new SItem(specMaterial, variant, stack, statistics.getRarity(), origin, false, (null != specMaterial.getItemData()) ? specMaterial.getItemData().getData() : new NBTTagCompound(), true);
    }
    
    public static SItem of(final SMaterial specMaterial, final ItemOrigin origin) {
        return of(specMaterial, specMaterial.getData(), origin);
    }
    
    public static SItem of(final SMaterial specMaterial, final short variant) {
        return of(specMaterial, variant, ItemOrigin.UNKNOWN);
    }
    
    public static SItem of(final SMaterial specMaterial) {
        return of(specMaterial, specMaterial.getData());
    }
    
    public void setStarAmount(final int star) {
        if (!this.getDataBoolean("dungeons_item")) {
            return;
        }
        this.setDataInt("itemStar", star);
    }
    
    public static SItem of(final ItemStack stack, final ItemOrigin origin) {
        if (null == stack) {
            return null;
        }
        final SMaterial material = SMaterial.getSpecEquivalent(stack.getType(), stack.getDurability());
        if (null == material) {
            return null;
        }
        if (null == Item.getById(material.getCraftMaterial().getId())) {
            return null;
        }
        final SItem n = of(material, stack.getDurability(), origin);
        n.getStack().setAmount(stack.getAmount());
        return n;
    }
    
    public static SItem of(final ItemStack stack) {
        return of(stack, ItemOrigin.UNKNOWN);
    }
    
    public static boolean isSpecItem(final ItemStack stack) {
        if (null == stack) {
            return false;
        }
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        if (null == nmsStack) {
            return false;
        }
        if (!nmsStack.hasTag()) {
            return false;
        }
        final NBTTagCompound compound = nmsStack.getTag();
        return compound.hasKey("type");
    }
    
    public SItem clone() {
        return new SItem(this.type, this.variant, this.stack.clone(), this.rarity, this.origin, this.recombobulated, this.data, true);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SItem)) {
            return false;
        }
        final SItem item = (SItem)o;
        return this.type == item.type && this.variant == item.variant && this.stack.equals((Object)item.stack) && this.rarity == item.rarity && this.origin == item.origin && this.recombobulated == item.recombobulated && this.data.equals((Object)item.data);
    }
    
    public NBTTagCompound toCompound() {
        final NBTTagCompound compound = new NBTTagCompound();
        for (final String key : this.data.c()) {
            compound.remove(key);
            compound.set(key, this.data.get(key));
        }
        compound.setString("type", this.type.name());
        final NBTTagCompound extraAttributesTag = new NBTTagCompound();
        extraAttributesTag.setString("id", this.type.name());
        compound.set("ExtraAttributes", (NBTBase)extraAttributesTag);
        if (0 != this.variant) {
            compound.setShort("variant", this.variant);
        }
        compound.setInt("amount", this.stack.getAmount());
        if (this.rarity != this.type.getStatistics().getRarity()) {
            compound.setString("rarity", this.rarity.name());
        }
        if (ItemOrigin.UNKNOWN != this.origin) {
            compound.setString("origin", this.origin.name());
        }
        if (this.recombobulated) {
            compound.setBoolean("recombobulated", true);
        }
        if (!this.getType().getStatistics().isStackable() && !compound.hasKey("uuid")) {
            compound.setString("uuid", UUID.randomUUID().toString());
        }
        return compound;
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"type", (Object)this.type.name());
        map.put((Object)"variant", (Object)this.variant);
        map.put((Object)"amount", (Object)this.stack.getAmount());
        map.put((Object)"rarity", (Object)this.rarity.name());
        map.put((Object)"origin", (Object)this.origin.name());
        map.put((Object)"recombobulated", (Object)this.recombobulated);
        for (final String k : this.data.c()) {
            if (k.equals((Object)"display")) {
                continue;
            }
            if (this.data.get(k) instanceof NBTTagCompound) {
                final SerialNBTTagCompound serial = new SerialNBTTagCompound(this.data.getCompound(k));
                for (final Map.Entry<String, Object> entry : serial.serialize().entrySet()) {
                    map.put((Object)(k + "." + (String)entry.getKey()), entry.getValue());
                }
            }
            else {
                map.put((Object)k, SUtil.getObjectFromCompound(this.data, k));
            }
        }
        return map;
    }
    
    public static SItem deserialize(final Map<String, Object> map) {
        final NBTTagCompound data = new NBTTagCompound();
        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            if (SItem.GLOBAL_DATA_KEYS.contains(entry.getKey())) {
                continue;
            }
            String key = (String)entry.getKey();
            final String[] dir = ((String)entry.getKey()).split("\\.");
            if (2 <= dir.length) {
                key = dir[dir.length - 1];
                NBTTagCompound track = data;
                for (int i = 0; i < dir.length - 1; ++i) {
                    if (!track.hasKey(dir[i])) {
                        track.set(dir[i], (NBTBase)new NBTTagCompound());
                    }
                    track = track.getCompound(dir[i]);
                }
                track.set(key, SUtil.getBaseFromObject(entry.getValue()));
            }
            else {
                data.set(key, SUtil.getBaseFromObject(entry.getValue()));
            }
        }
        final SMaterial material = SMaterial.getMaterial((String)map.get((Object)"type"));
        final short variant = Short.valueOf((String)map.get((Object)"variant"));
        return new SItem(material, variant, new ItemStack(material.getCraftMaterial(), (int)map.get((Object)"amount"), variant), Rarity.getRarity((String)map.get((Object)"rarity")), ItemOrigin.valueOf((String)map.get((Object)"origin")), (boolean)map.get((Object)"recombobulated"), data, true);
    }
    
    @Override
    public String toString() {
        return "SItem{type=" + this.type.name() + ", variant=" + (int)this.variant + ", stack=" + this.stack.toString() + ", rarity=" + this.rarity.name() + ", origin=" + this.origin.name() + ", recombobulated=" + this.recombobulated + ", data=" + this.data.toString() + "}";
    }
    
    public static SItem find(final ItemStack stack) {
        if (null == stack) {
            return null;
        }
        if (!isSpecItem(stack)) {
            return null;
        }
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        final NBTTagCompound compound = nmsStack.getTag();
        if (null == compound) {
            return null;
        }
        return of(compound, stack);
    }
    
    public static SItem of(final NBTTagCompound compound) {
        final SMaterial type = SMaterial.getMaterial(compound.getString("type"));
        final ItemStack stack = new ItemStack(type.getCraftMaterial(), compound.hasKey("amount") ? compound.getInt("amount") : 1, type.getData());
        final MaterialStatistics statistics = type.getStatistics();
        if (Material.SKULL_ITEM == type.getCraftMaterial() && statistics instanceof SkullStatistics) {
            stack.setDurability((short)3);
            SUtil.getSkull(((SkullStatistics)statistics).getURL(), stack, type);
        }
        if (statistics instanceof LeatherArmorStatistics) {
            SUtil.applyColorToLeatherArmor(stack, Color.fromRGB(((LeatherArmorStatistics)statistics).getColor()));
        }
        final ItemMeta meta = stack.getItemMeta();
        final short variant = (short)(compound.hasKey("variant") ? compound.getShort("variant") : 0);
        meta.setDisplayName((Object)statistics.getRarity().getColor() + type.getDisplayName(variant));
        stack.setItemMeta(meta);
        final NBTTagCompound data = new NBTTagCompound();
        for (final String key : compound.c()) {
            if (SItem.GLOBAL_NBT_TAGS.contains((Object)key)) {
                continue;
            }
            data.set(key, compound.get(key));
        }
        return new SItem(type, variant, stack, compound.hasKey("rarity") ? Rarity.getRarity(compound.getString("rarity")) : statistics.getRarity(), compound.hasKey("origin") ? ItemOrigin.valueOf(compound.getString("origin")) : ItemOrigin.UNKNOWN, compound.getBoolean("recombobulated"), data, true);
    }
    
    public static SItem convert(final ItemStack stack) {
        return SUtil.setSItemAmount(of(SMaterial.getSpecEquivalent(stack.getType(), stack.getDurability())), stack.getAmount());
    }
    
    public static SItem find(final ItemStack stack, final int amount) {
        return SUtil.setSItemAmount(find(stack), stack.getAmount());
    }
    
    private static SItem of(final NBTTagCompound compound, final ItemStack stack) {
        final SMaterial type = SMaterial.getMaterial(compound.getString("type"));
        final NBTTagCompound data = new NBTTagCompound();
        for (final String key : compound.c()) {
            if (SItem.GLOBAL_NBT_TAGS.contains((Object)key)) {
                continue;
            }
            data.set(key, compound.get(key));
        }
        return new SItem(type, (short)(compound.hasKey("variant") ? compound.getShort("variant") : 0), stack, compound.hasKey("rarity") ? Rarity.getRarity(compound.getString("rarity")) : type.getStatistics().getRarity(), compound.hasKey("origin") ? ItemOrigin.valueOf(compound.getString("origin")) : ItemOrigin.UNKNOWN, compound.hasKey("recombobulated"), data, false);
    }
    
    public static boolean isAbleToDoEtherWarpTeleportation(final Player player, final SItem sitem) {
        boolean haveBlockInRange = false;
        try {
            for (int range = 1; 57 > range; ++range) {
                final Location location = player.getTargetBlock((Set)null, range).getLocation();
                if (Material.AIR != location.getBlock().getType()) {
                    haveBlockInRange = true;
                    break;
                }
            }
        }
        catch (final IllegalStateException ex) {}
        return haveBlockInRange;
    }
    
    public static void etherWarpTeleportation(final Player player, final SItem sitem) {
        boolean haveBlockInRange = false;
        try {
            for (int range = 1; 57 > range; ++range) {
                final Location location = player.getTargetBlock((Set)null, range).getLocation();
                if (Material.AIR != location.getBlock().getType()) {
                    haveBlockInRange = true;
                    break;
                }
            }
        }
        catch (final IllegalStateException ex) {}
        if (!haveBlockInRange) {
            return;
        }
        try {
            int f_ = 57;
            for (int range2 = 1; 57 > range2; ++range2) {
                final Location location2 = player.getTargetBlock((Set)null, range2).getLocation();
                if (Material.AIR != location2.getBlock().getType()) {
                    f_ = range2;
                    break;
                }
            }
            Location location = player.getTargetBlock((Set)null, f_).getLocation().getBlock().getLocation();
            if (Material.AIR != location.clone().add(0.0, 1.0, 0.0).getBlock().getType() && Material.AIR != location.clone().add(0.0, 2.0, 0.0).getBlock().getType()) {
                location = player.getTargetBlock((Set)null, f_ - 1).getLocation().getBlock().getLocation();
            }
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            location.add(0.5, 1.0, 0.5);
            if (1 < f_) {
                Sputnik.teleport(player, location);
            }
            else {
                Sputnik.teleport(player, player.getLocation());
            }
        }
        catch (final IllegalStateException ex2) {}
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 1.0f);
    }
    
    public SMaterial getType() {
        return this.type;
    }
    
    public short getVariant() {
        return this.variant;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public ItemLore getLore() {
        return this.lore;
    }
    
    public Rarity getRarity() {
        return this.rarity;
    }
    
    public ItemOrigin getOrigin() {
        return this.origin;
    }
    
    public boolean isRecombobulated() {
        return this.recombobulated;
    }
    
    public NBTTagCompound getData() {
        return this.data;
    }
    
    static {
        GLOBAL_NBT_TAGS = Arrays.asList((Object[])new String[] { "type", "rarity", "origin", "recombobulated", "ExtraAttributes" });
        GLOBAL_DATA_KEYS = Arrays.asList((Object[])new String[] { "type", "variant", "stack", "rarity", "origin", "recombobulated", "ExtraAttributes" });
    }
}
