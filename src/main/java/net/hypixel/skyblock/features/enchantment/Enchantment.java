package net.hypixel.skyblock.features.enchantment;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Enchantment implements ConfigurationSerializable
{
    private final EnchantmentType type;
    private final int level;
    
    public Enchantment(final EnchantmentType type, final int level) {
        this.type = type;
        this.level = level;
    }
    
    @Override
    public String toString() {
        return this.type.getName() + " " + ((this.level <= 3000) ? SUtil.toRomanNumeral(this.level) : SUtil.commaify(this.level));
    }
    
    public String getDisplayName() {
        return (this.type.isUltimate() ? ("" + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD) : ChatColor.BLUE) + this.toString();
    }
    
    public String toIdentifiableString() {
        return this.type.getNamespace() + "." + this.level;
    }
    
    public String getDescription() {
        if (this.type == EnchantmentType.SHARPNESS) {
            return this.type.getDescription(5 * this.level);
        }
        if (this.type == EnchantmentType.FIRE_ASPECT) {
            return this.type.getDescription(2 + this.level);
        }
        if (this.type == EnchantmentType.GROWTH) {
            return this.type.getDescription(15 * this.level);
        }
        if (this.type == EnchantmentType.AIMING) {
            return this.type.getDescription(2 * this.level);
        }
        if (this.type == EnchantmentType.POWER || this.type == EnchantmentType.SMITE || this.type == EnchantmentType.BANE_OF_ARTHROPODS || this.type == EnchantmentType.DRAGON_HUNTER) {
            return this.type.getDescription(8 * this.level);
        }
        if (this.type == EnchantmentType.ULTIMATE_WISE || this.type == EnchantmentType.CRITICAL) {
            return this.type.getDescription(10 * this.level);
        }
        if (this.type == EnchantmentType.KNOCKBACK) {
            return this.type.getDescription(3 * this.level);
        }
        if (this.type == EnchantmentType.VAMPIRISM) {
            return this.type.getDescription(this.level);
        }
        if (this.type == EnchantmentType.FIRST_STRIKE) {
            return this.type.getDescription(this.level * 25);
        }
        if (this.type == EnchantmentType.VICIOUS) {
            return this.type.getDescription(this.level);
        }
        if (this.type == EnchantmentType.CHIMERA) {
            return this.type.getDescription(this.level * 20);
        }
        if (this.type == EnchantmentType.HARVESTING) {
            return this.type.getDescription(SUtil.commaify(12.5 * this.level));
        }
        if (this.type == EnchantmentType.PROTECTION) {
            return this.type.getDescription(3 * this.level);
        }
        if (this.type == EnchantmentType.ENDER_SLAYER) {
            return this.type.getDescription(12 * this.level);
        }
        if (this.type == EnchantmentType.LUCKINESS) {
            return this.type.getDescription(2 * this.level);
        }
        if (this.type == EnchantmentType.FATAL_TEMPO) {
            return this.type.getDescription(5 * this.level);
        }
        if (this.type == EnchantmentType.LEGION) {
            return this.type.getDescription(Math.round(0.07 * this.level * 100.0) / 100.0);
        }
        if (this.type == EnchantmentType.SOUL_EATER) {
            return this.type.getDescription(2 * this.level);
        }
        if (this.type == EnchantmentType.ONE_FOR_ALL) {
            return this.type.getDescription(210 * this.level);
        }
        if (this.type == EnchantmentType.EXECUTE) {
            return this.type.getDescription(Math.round(0.2 * this.level * 100.0) / 100.0);
        }
        if (this.type == EnchantmentType.LIFE_STEAL) {
            return this.type.getDescription(Math.round(0.5 * this.level * 100.0) / 100.0);
        }
        return this.type.getDescription(new Object[0]);
    }
    
    public static Enchantment getByIdentifiable(final String identifiable) {
        final String[] spl = identifiable.split("\\.");
        return new Enchantment(EnchantmentType.getByNamespace(spl[0]), Integer.parseInt(spl[1]));
    }
    
    public boolean equalsType(final Enchantment enchantment) {
        return enchantment.type.equals(this.type);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Enchantment)) {
            return false;
        }
        final Enchantment enchantment = (Enchantment)o;
        return enchantment.level == this.level && enchantment.type.equals(this.type);
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"type", (Object)this.type.getNamespace());
        map.put((Object)"level", (Object)this.level);
        return null;
    }
    
    public static List<Enchantment> ultimateEnchantsListFromList(final List<Enchantment> list) {
        final List<Enchantment> filteredList_ultimate = (List<Enchantment>)new ArrayList();
        for (final Enchantment enchantment : list) {
            if (enchantment.getDisplayName().contains((CharSequence)ChatColor.LIGHT_PURPLE.toString())) {
                filteredList_ultimate.add((Object)enchantment);
            }
        }
        return filteredList_ultimate;
    }
    
    public static List<Enchantment> normalEnchantsListFromList(final List<Enchantment> list) {
        final List<Enchantment> filteredList_normal = (List<Enchantment>)new ArrayList();
        for (final Enchantment enchantment : list) {
            if (!enchantment.getDisplayName().contains((CharSequence)ChatColor.LIGHT_PURPLE.toString())) {
                filteredList_normal.add((Object)enchantment);
            }
        }
        return filteredList_normal;
    }
    
    public EnchantmentType getType() {
        return this.type;
    }
    
    public int getLevel() {
        return this.level;
    }
}
