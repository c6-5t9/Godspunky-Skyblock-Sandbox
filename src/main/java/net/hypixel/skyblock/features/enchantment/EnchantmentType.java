package net.hypixel.skyblock.features.enchantment;

import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.ChatColor;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import net.hypixel.skyblock.item.SpecificItemType;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import java.util.Map;

public class EnchantmentType
{
    public static final Map<String, EnchantmentType> ENCHANTMENT_TYPE_CACHE;
    public static List<EnchantmentType> ENCHANTMENT_TYPE_LIST;
    public static EnchantmentType SHARPNESS;
    public static EnchantmentType LIFE_STEAL;
    public static EnchantmentType EXECUTE;
    public static EnchantmentType FIRE_ASPECT;
    public static EnchantmentType PROTECTION;
    public static EnchantmentType GROWTH;
    public static EnchantmentType AIMING;
    public static EnchantmentType POWER;
    public static EnchantmentType FLAME;
    public static EnchantmentType ENDER_SLAYER;
    public static EnchantmentType DRAGON_HUNTER;
    public static EnchantmentType EFFICIENCY;
    public static EnchantmentType KNOCKBACK;
    public static EnchantmentType AQUA_INFINITY;
    public static EnchantmentType VAMPIRISM;
    public static EnchantmentType FIRST_STRIKE;
    public static EnchantmentType VICIOUS;
    public static EnchantmentType SMITE;
    public static EnchantmentType BANE_OF_ARTHROPODS;
    public static EnchantmentType CRITICAL;
    public static EnchantmentType FATAL_TEMPO;
    public static EnchantmentType HARVESTING;
    public static EnchantmentType TELEKINESIS;
    public static EnchantmentType ULTIMATE_WISE;
    public static EnchantmentType LUCKINESS;
    public static EnchantmentType SOUL_EATER;
    public static EnchantmentType CHIMERA;
    public static EnchantmentType LEGION;
    public static EnchantmentType ONE_FOR_ALL;
    private final String name;
    private final String namespace;
    private final String description;
    private final boolean ultimate;
    private final Enchantment vanilla;
    private final List<SpecificItemType> compatibleTypes;
    public final int maxLvl;
    
    public EnchantmentType(final String name, final String namespace, final String description, final boolean ultimate, final Enchantment vanilla, final int maxLvl, final SpecificItemType... compatibleTypes) {
        this.name = name;
        this.namespace = namespace;
        this.description = description;
        this.ultimate = ultimate;
        this.vanilla = vanilla;
        this.maxLvl = maxLvl;
        this.compatibleTypes = (List<SpecificItemType>)new ArrayList((Collection)Arrays.asList((Object[])compatibleTypes));
        EnchantmentType.ENCHANTMENT_TYPE_CACHE.put((Object)namespace, (Object)this);
        EnchantmentType.ENCHANTMENT_TYPE_LIST.add((Object)this);
    }
    
    public EnchantmentType(final String name, final String namespace, final String description, final boolean ultimate, final int maxLvl, final SpecificItemType... compatibleTypes) {
        this(name, namespace, description, ultimate, null, maxLvl, compatibleTypes);
    }
    
    public EnchantmentType(final String name, final String namespace, final String description, final Enchantment vanilla, final int maxLvl, final SpecificItemType... compatibleTypes) {
        this(name, namespace, description, false, vanilla, maxLvl, compatibleTypes);
    }
    
    public EnchantmentType(final String name, final String namespace, final String description, final int maxLvl, final SpecificItemType... compatibleTypes) {
        this(name, namespace, description, false, maxLvl, compatibleTypes);
    }
    
    public static EnchantmentType getByNamespace(final String namespace) {
        return (EnchantmentType)EnchantmentType.ENCHANTMENT_TYPE_CACHE.get((Object)namespace.toLowerCase());
    }
    
    public String getDescription(final Object... objects) {
        String description = this.description;
        for (final Object object : objects) {
            description = description.replaceFirst("%s", String.valueOf(object));
        }
        return description;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof EnchantmentType && ((EnchantmentType)o).namespace.equals((Object)this.namespace);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public boolean isUltimate() {
        return this.ultimate;
    }
    
    public Enchantment getVanilla() {
        return this.vanilla;
    }
    
    public List<SpecificItemType> getCompatibleTypes() {
        return this.compatibleTypes;
    }
    
    static {
        EnchantmentType.ENCHANTMENT_TYPE_LIST = (List<EnchantmentType>)new ArrayList();
        ENCHANTMENT_TYPE_CACHE = (Map)new HashMap();
        EnchantmentType.SHARPNESS = new EnchantmentType("Sharpness", "sharpness", "Increases damage dealt by " + (Object)ChatColor.GREEN + "%s%", 600, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.LIFE_STEAL = new EnchantmentType("Life Steal", "life_steal", "Heals for " + (Object)ChatColor.GREEN + "%s%" + (Object)ChatColor.GRAY + " of your max health each time you hit a mob.", 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.EXECUTE = new EnchantmentType("Execute", "execute", "Increases damage by " + (Object)ChatColor.GREEN + "%s%" + Sputnik.trans(" &7for each percent of Health missing on your target. "), 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.FIRE_ASPECT = new EnchantmentType("Fire Aspect", "fire_aspect", "Gives whoever this weapon hits %s seconds of fire.", 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.PROTECTION = new EnchantmentType("Protection", "protection", "Grants " + (Object)ChatColor.GREEN + "+%s \u2748 Defense" + (Object)ChatColor.GRAY + ".", 4000, new SpecificItemType[] { SpecificItemType.HELMET, SpecificItemType.CHESTPLATE, SpecificItemType.LEGGINGS, SpecificItemType.BOOTS });
        EnchantmentType.GROWTH = new EnchantmentType("Growth", "growth", "Grants " + (Object)ChatColor.GREEN + "+%s " + (Object)ChatColor.RED + "\u2764 " + (Object)ChatColor.RED + "Health" + (Object)ChatColor.GRAY + ".", 1500, new SpecificItemType[] { SpecificItemType.HELMET, SpecificItemType.CHESTPLATE, SpecificItemType.LEGGINGS, SpecificItemType.BOOTS });
        EnchantmentType.AIMING = new EnchantmentType("Aiming", "aiming", "Arrows home towards nearby mobs if they are within %s blocks.", 30, new SpecificItemType[] { SpecificItemType.BOW });
        EnchantmentType.POWER = new EnchantmentType("Power", "power", "Increases bow damage by " + (Object)ChatColor.GREEN + "%s%", 320, new SpecificItemType[] { SpecificItemType.BOW });
        EnchantmentType.FLAME = new EnchantmentType("Flame", "flame", "Arrow ignites target for 3 seconds, dealing 5 damage every second.", 30, new SpecificItemType[] { SpecificItemType.BOW });
        EnchantmentType.ENDER_SLAYER = new EnchantmentType("Ender Slayer", "ender_slayer", "Increases damage dealt to Ender Dragons and Endermen by " + (Object)ChatColor.GREEN + "%s% " + (Object)ChatColor.GRAY, 250, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.DRAGON_HUNTER = new EnchantmentType("Dragon Hunter", "dragon_hunter", "Increases damage dealt to Ender Dragons by " + (Object)ChatColor.GREEN + "%s% " + (Object)ChatColor.GRAY, 250, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE, SpecificItemType.BOW });
        EnchantmentType.EFFICIENCY = new EnchantmentType("Efficiency", "efficiency", "Reduces the time in takes to mine.", Enchantment.DIG_SPEED, 5, new SpecificItemType[] { SpecificItemType.AXE, SpecificItemType.PICKAXE, SpecificItemType.SHOVEL });
        EnchantmentType.KNOCKBACK = new EnchantmentType("Knockback", "knockback", Sputnik.trans("Increases knockback by &a%s&7 blocks."), Enchantment.KNOCKBACK, 2, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.AQUA_INFINITY = new EnchantmentType("Aqua Infinity", "aqua_infinity", Sputnik.trans("Increases underwater mining rate to normal level mining rate."), Enchantment.WATER_WORKER, 5, new SpecificItemType[] { SpecificItemType.HELMET });
        EnchantmentType.VAMPIRISM = new EnchantmentType("Vampirism", "vampirism", Sputnik.trans("Heals for &a%s% &7of your missing Health per level whenever you kill an enemy."), 10, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.FIRST_STRIKE = new EnchantmentType("First Strike", "first_strike", Sputnik.trans("Increases the first melee damage dealt to a mob by &a%s%"), 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.VICIOUS = new EnchantmentType("Vicious", "vicious", Sputnik.trans("Grant &c+%s\u2afd Ferocity"), 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE, SpecificItemType.BOW });
        EnchantmentType.SMITE = new EnchantmentType("Smite", "smite", "Increases damage dealt to Zombies, Zombie Pigmen, Withers, Wither Skeletons, and Skeletons by " + (Object)ChatColor.GREEN + "%s% " + (Object)ChatColor.GRAY, 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.BANE_OF_ARTHROPODS = new EnchantmentType("Bane of Arthropods", "bane_of_arthropods", "Increases damage dealt to Cave Spiders, Spiders, and Silverfish by " + (Object)ChatColor.GREEN + "%s% " + (Object)ChatColor.GRAY, 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.CRITICAL = new EnchantmentType("Critical", "critical", "Increases " + (Object)ChatColor.BLUE + "\u2620 Crit Damage " + (Object)ChatColor.GRAY + "by " + (Object)ChatColor.GREEN + "%s%" + (Object)ChatColor.GRAY + ".", 30, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.FATAL_TEMPO = new EnchantmentType("Fatal Tempo", "fatal_tempo", Sputnik.trans("&7Attack increases your &c\u2afd &cFerocity &7by &c%s% &7per hit, capped at &c200% &7for 3 seconds after your &efirst &eattack &7that triggers the enchantment."), true, 10, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
        EnchantmentType.HARVESTING = new EnchantmentType("Harvesting", "harvesting", "Increases the chance for crops to drop double the amount of items by " + (Object)ChatColor.GREEN + "%s%" + (Object)ChatColor.GRAY + ".", 5, new SpecificItemType[] { SpecificItemType.HOE });
        EnchantmentType.TELEKINESIS = new EnchantmentType("Telekinesis", "telekinesis", "Blocks and mob drops go directly into your inventory.", 1, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.BOW, SpecificItemType.AXE });
        EnchantmentType.ULTIMATE_WISE = new EnchantmentType("Ultimate Wise", "ultimate_wise", "Reduces the Mana Cost of this item's ability by " + (Object)ChatColor.GREEN + "%s%" + (Object)ChatColor.GRAY + ".", true, 20, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.SHOVEL, SpecificItemType.SHEARS, SpecificItemType.PICKAXE, SpecificItemType.BOW, SpecificItemType.AXE, SpecificItemType.ROD, SpecificItemType.HOE, SpecificItemType.WAND });
        EnchantmentType.LUCKINESS = new EnchantmentType("Luckiness", "luckiness", Sputnik.trans("&7Grant &b+%s \u272f Magic Find"), 10, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.SHOVEL, SpecificItemType.SHEARS, SpecificItemType.PICKAXE, SpecificItemType.BOW, SpecificItemType.AXE, SpecificItemType.ROD, SpecificItemType.HOE, SpecificItemType.HELMET, SpecificItemType.CHESTPLATE, SpecificItemType.LEGGINGS, SpecificItemType.BOOTS });
        EnchantmentType.SOUL_EATER = new EnchantmentType("Soul Eater", "soul_eater", Sputnik.trans("Your weapon gains &c%sx&7 damage of the latest monster killed and applies it on your next hit."), true, 20, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE, SpecificItemType.BOW });
        EnchantmentType.CHIMERA = new EnchantmentType("Chimera", "chimera", Sputnik.trans("Copies &a%s% &7of your active pet's stats."), true, 20, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE, SpecificItemType.BOW });
        EnchantmentType.LEGION = new EnchantmentType("Legion", "legion", Sputnik.trans("Increases most of your player stats by &e+%s% &7per player per level within &b30 &7blocks of you, up to &a20 &7players."), true, 20, new SpecificItemType[] { SpecificItemType.HELMET, SpecificItemType.CHESTPLATE, SpecificItemType.LEGGINGS, SpecificItemType.BOOTS });
        EnchantmentType.ONE_FOR_ALL = new EnchantmentType("One for All", "one_for_all", Sputnik.trans("Removes all other enchants but increases your weapon damage by &a%s%"), true, 10, new SpecificItemType[] { SpecificItemType.SWORD, SpecificItemType.LONGSWORD, SpecificItemType.AXE });
    }
}
