package net.hypixel.skyblock.features.slayer;

import org.bukkit.Material;
import net.hypixel.skyblock.util.Sputnik;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.entity.EntityType;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import java.util.Arrays;
import net.hypixel.skyblock.entity.SEntityType;
import java.util.List;

public class SlayerBossType
{
    private static final List<SlayerBossType> TYPES;
    private static final SlayerAbility LIFE_DRAIN;
    private static final SlayerAbility PESTILENCE;
    private static final SlayerAbility ENRAGE;
    private static final SlayerAbility COMBAT_JUMP;
    private static final SlayerAbility NOXIOUS;
    private static final SlayerAbility AGILE;
    private static final SlayerAbility TRUE_DAMAGE;
    private static final SlayerAbility CALL_THE_PUPS;
    private static final SlayerAbility DISSONANCE;
    private static final SlayerAbility HEAL;
    private static final SlayerAbility EXPLOSION;
    private static final SlayerAbility ONEHIT;
    private static final SlayerAbility COMING_SOON;
    private static final SlayerAbility NUKEKUBI;
    private static final SlayerAbility YANG_GLYPHS;
    private static final SlayerAbility HITSHIELD;
    private static final SlayerAbility HEART_RADI;
    public static final SlayerBossType REVENANT_HORROR_I;
    public static final SlayerBossType REVENANT_HORROR_II;
    public static final SlayerBossType REVENANT_HORROR_III;
    public static final SlayerBossType REVENANT_HORROR_IV;
    public static final SlayerBossType REVENANT_HORROR_V;
    public static final SlayerBossType TARANTULA_BROODFATHER_I;
    public static final SlayerBossType TARANTULA_BROODFATHER_II;
    public static final SlayerBossType TARANTULA_BROODFATHER_III;
    public static final SlayerBossType TARANTULA_BROODFATHER_IV;
    public static final SlayerBossType SVEN_PACKMASTER_I;
    public static final SlayerBossType SVEN_PACKMASTER_II;
    public static final SlayerBossType SVEN_PACKMASTER_III;
    public static final SlayerBossType SVEN_PACKMASTER_IV;
    public static final SlayerBossType VOIDGLOOM_SERAPH_I;
    public static final SlayerBossType VOIDGLOOM_SERAPH_II;
    public static final SlayerBossType VOIDGLOOM_SERAPH_III;
    public static final SlayerBossType VOIDGLOOM_SERAPH_IV;
    private final String namespace;
    private final String name;
    private final SEntityType specType;
    private final int tier;
    private final String description;
    private final SlayerMobType type;
    private final int health;
    private final int dps;
    private final int tdps;
    private final int rewardXP;
    private final int spawnXP;
    private final int cost;
    private final List<SlayerAbility> abilities;
    
    SlayerBossType(final String namespace, final String name, final SEntityType specType, final int tier, final String description, final SlayerMobType type, final int health, final int dps, final int tdps, final int rewardXP, final int spawnXP, final int cost, final SlayerAbility... abilities) {
        this.namespace = namespace;
        this.name = name;
        this.specType = specType;
        this.tier = tier;
        this.description = description;
        this.type = type;
        this.health = health;
        this.dps = dps;
        this.tdps = tdps;
        this.rewardXP = rewardXP;
        this.spawnXP = spawnXP;
        this.cost = cost;
        this.abilities = (List<SlayerAbility>)Arrays.asList((Object[])abilities);
        SlayerBossType.TYPES.add((Object)this);
    }
    
    SlayerBossType(final String namespace, final String name, final SEntityType specType, final int tier, final String description, final SlayerMobType type, final int health, final int dps, final int rewardXP, final int spawnXP, final int cost, final SlayerAbility... abilities) {
        this(namespace, name, specType, tier, description, type, health, dps, 0, rewardXP, spawnXP, cost, abilities);
    }
    
    public static ChatColor getColorForTier(final int tier) {
        switch (tier) {
            case 2: {
                return ChatColor.YELLOW;
            }
            case 3: {
                return ChatColor.RED;
            }
            case 4: {
                return ChatColor.DARK_RED;
            }
            case 5: {
                return ChatColor.DARK_PURPLE;
            }
            default: {
                return ChatColor.GREEN;
            }
        }
    }
    
    public int getXPReqForLevel(final int level) {
        switch (this.type) {
            case ZOMBIE: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 15, 200, 1000, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case SPIDER: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 25, 200, 1000, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case WOLF: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case ENDERMAN: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            default: {
                return 1000000;
            }
        }
    }
    
    public static int staticGetXPReqForLevel(final int level, final EntityType type) {
        switch (type) {
            case ZOMBIE: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 15, 200, 1000, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case SPIDER: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 25, 200, 1000, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case WOLF: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            case ENDERMAN: {
                return SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000 }), level, 1000000);
            }
            default: {
                return 1000000;
            }
        }
    }
    
    public static SlayerBossType getByNamespace(final String namespace) {
        for (final SlayerBossType type : SlayerBossType.TYPES) {
            if (namespace.equalsIgnoreCase(type.namespace)) {
                return type;
            }
        }
        return null;
    }
    
    public String getDisplayName() {
        return (Object)getColorForTier(this.tier) + this.name + " " + SUtil.toRomanNumeral(this.tier);
    }
    
    public List<String> asLore(final boolean affordable) {
        final List<String> lore = (List<String>)new ArrayList();
        lore.add((Object)((Object)ChatColor.DARK_GRAY + this.description));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Health: " + (Object)ChatColor.RED + SUtil.commaify(this.health) + "\u2764"));
        lore.add((Object)((Object)ChatColor.GRAY + "Damage: " + (Object)ChatColor.RED + SUtil.commaify(this.dps) + (Object)ChatColor.GRAY + " per second"));
        if (this.tdps != 0) {
            lore.add((Object)((Object)ChatColor.GRAY + "True Damage: " + (Object)ChatColor.WHITE + SUtil.commaify(this.tdps) + (Object)ChatColor.GRAY + " per second"));
        }
        for (final SlayerAbility ability : this.abilities) {
            lore.add((Object)"");
            lore.add((Object)ability.getName());
            for (final String line : ability.getDescription()) {
                lore.add((Object)((Object)ChatColor.GRAY + line));
            }
        }
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Reward: " + (Object)ChatColor.LIGHT_PURPLE + this.rewardXP + " " + this.type.getName() + " Slayer XP"));
        lore.add((Object)((Object)ChatColor.DARK_GRAY + "  + Boss drops"));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Cost to start: " + (Object)ChatColor.GOLD + SUtil.commaify(this.cost) + " coins"));
        lore.add((Object)"");
        lore.add((Object)(affordable ? ((Object)ChatColor.YELLOW + "Click to slay!") : ((Object)ChatColor.RED + "Cannot afford quest!")));
        return lore;
    }
    
    public List<String> asHiddenLore(final boolean affordable) {
        final List<String> lore = (List<String>)new ArrayList();
        lore.add((Object)((Object)ChatColor.DARK_GRAY + this.description));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Health: " + (Object)ChatColor.DARK_GRAY + "???"));
        lore.add((Object)((Object)ChatColor.GRAY + "Damage: " + (Object)ChatColor.DARK_GRAY + "???"));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.DARK_GRAY + "???"));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Reward: " + (Object)ChatColor.LIGHT_PURPLE + this.rewardXP + " " + this.type.getName() + " Slayer XP"));
        lore.add((Object)((Object)ChatColor.DARK_GRAY + "  + Boss drops"));
        lore.add((Object)"");
        lore.add((Object)((Object)ChatColor.GRAY + "Cost to start: " + (Object)ChatColor.GOLD + SUtil.commaify(this.cost) + " coins"));
        lore.add((Object)"");
        lore.add((Object)(affordable ? ((Object)ChatColor.YELLOW + "Click to slay!") : ((Object)ChatColor.RED + "Cannot afford quest!")));
        return lore;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getName() {
        return this.name;
    }
    
    public SEntityType getSpecType() {
        return this.specType;
    }
    
    public int getTier() {
        return this.tier;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public SlayerMobType getType() {
        return this.type;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public int getDps() {
        return this.dps;
    }
    
    public int getTdps() {
        return this.tdps;
    }
    
    public int getRewardXP() {
        return this.rewardXP;
    }
    
    public int getSpawnXP() {
        return this.spawnXP;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public List<SlayerAbility> getAbilities() {
        return this.abilities;
    }
    
    static {
        TYPES = (List)new ArrayList();
        LIFE_DRAIN = new SlayerAbility((Object)ChatColor.RED + "Life Drain", new String[] { "Drains health every few seconds." });
        PESTILENCE = new SlayerAbility((Object)ChatColor.GREEN + "Pestilence", new String[] { "Deals AOE damage every second,", "shredding armor by 25%." });
        ENRAGE = new SlayerAbility((Object)ChatColor.RED + "Enrage", new String[] { "Gets real mad once in a while." });
        COMBAT_JUMP = new SlayerAbility((Object)ChatColor.YELLOW + "Combat Jump", new String[] { "The spider will often attempt to", "jump behind you." });
        NOXIOUS = new SlayerAbility((Object)ChatColor.RED + "Noxious", new String[] { "Deals AOE damage every second,", "reducing your healing by 50%." });
        AGILE = new SlayerAbility((Object)ChatColor.GREEN + "Agile", new String[] { "The wolf is small and fast, making", "it hard to hit." });
        TRUE_DAMAGE = new SlayerAbility((Object)ChatColor.WHITE + "True Damage", new String[] { "Ignores your defense. Very painful." });
        CALL_THE_PUPS = new SlayerAbility((Object)ChatColor.AQUA + "Call the pups!", new String[] { "At 50% health, calls its deadly pack", "of pups." });
        DISSONANCE = new SlayerAbility((Object)ChatColor.RED + "Dissonance", new String[] { "Once in a while, teleport behind ", "or to the sides of the player." });
        HEAL = new SlayerAbility((Object)ChatColor.GREEN + "Redemption", new String[] { "Heals rapidly." });
        EXPLOSION = new SlayerAbility((Object)ChatColor.RED + "Explosive Assault", new String[] { "Throws explosive TNT." });
        ONEHIT = new SlayerAbility((Object)ChatColor.DARK_PURPLE + "Thermonuclear", new String[] { "Charges up and releases a massive", "explosion." });
        COMING_SOON = new SlayerAbility((Object)ChatColor.RED + "Coming Soon!", new String[] { "More abilities coming soon!" });
        NUKEKUBI = new SlayerAbility((Object)ChatColor.YELLOW + "Nukekubi Fixations", new String[] { "Spawn weird heads.", "Clear them by looking at them or", "suffer damage." });
        YANG_GLYPHS = new SlayerAbility((Object)ChatColor.RED + "Yang Glyphs", new String[] { "Throw glyphs down.", "Stand next to them or die." });
        HITSHIELD = new SlayerAbility((Object)ChatColor.GREEN + "Malevolent Hitshield", new String[] { "Immunity shield dropped by hits", "regardless of damage." });
        HEART_RADI = new SlayerAbility((Object)ChatColor.LIGHT_PURPLE + "Broken Heart Radiation", new String[] { "Becomes immune to damage and", "casts dangerous moving beams.", "", Sputnik.trans("Touching a beam deals &c25% &7of"), Sputnik.trans("your &c\u2764&7 as true damage."), "", Sputnik.trans("Touching a beam also decreases"), Sputnik.trans("your incoming healing by &212%"), Sputnik.trans("for &a90s&7.") });
        REVENANT_HORROR_I = new SlayerBossType("revenant_horror_i", "Revenant Horror", SEntityType.REVENANT_HORROR, 1, "Beginner", SlayerMobType.ZOMBIE, 500, 15, 5, 150, 100, new SlayerAbility[] { SlayerBossType.LIFE_DRAIN });
        REVENANT_HORROR_II = new SlayerBossType("revenant_horror_ii", "Revenant Horror", SEntityType.REVENANT_HORROR, 2, "Strong", SlayerMobType.ZOMBIE, 20000, 50, 25, 1440, 2000, new SlayerAbility[] { SlayerBossType.LIFE_DRAIN, SlayerBossType.PESTILENCE });
        REVENANT_HORROR_III = new SlayerBossType("revenant_horror_iii", "Revenant Horror", SEntityType.REVENANT_HORROR, 3, "Challenging", SlayerMobType.ZOMBIE, 400000, 300, 100, 2400, 10000, new SlayerAbility[] { SlayerBossType.LIFE_DRAIN, SlayerBossType.PESTILENCE, SlayerBossType.ENRAGE });
        REVENANT_HORROR_IV = new SlayerBossType("revenant_horror_iv", "Revenant Horror", SEntityType.REVENANT_HORROR, 4, "Deadly", SlayerMobType.ZOMBIE, 1500000, 1000, 500, 4800, 50000, new SlayerAbility[] { SlayerBossType.LIFE_DRAIN, SlayerBossType.PESTILENCE, SlayerBossType.ENRAGE });
        REVENANT_HORROR_V = new SlayerBossType("revenant_horror_v", "Revenant Horror", SEntityType.ATONED_HORROR, 5, "Excruiating", SlayerMobType.ZOMBIE, 10000000, 3400, 1500, 6000, 95000, new SlayerAbility[] { SlayerBossType.HEAL, SlayerBossType.EXPLOSION, SlayerBossType.ONEHIT });
        TARANTULA_BROODFATHER_I = new SlayerBossType("tarantula_broodfather_i", "Tarantula Broodfather", SEntityType.TARANTULA_BROODFATHER, 1, "Beginner", SlayerMobType.SPIDER, 750, 35, 5, 250, 100, new SlayerAbility[] { SlayerBossType.COMBAT_JUMP });
        TARANTULA_BROODFATHER_II = new SlayerBossType("tarantula_broodfather_ii", "Tarantula Broodfather", SEntityType.TARANTULA_BROODFATHER, 2, "Strong", SlayerMobType.SPIDER, 30000, 110, 25, 600, 2000, new SlayerAbility[] { SlayerBossType.COMBAT_JUMP, SlayerBossType.NOXIOUS });
        TARANTULA_BROODFATHER_III = new SlayerBossType("tarantula_broodfather_iii", "Tarantula Broodfather", SEntityType.TARANTULA_BROODFATHER, 3, "Challenging", SlayerMobType.SPIDER, 900000, 525, 100, 1000, 10000, new SlayerAbility[] { SlayerBossType.COMBAT_JUMP, SlayerBossType.NOXIOUS });
        TARANTULA_BROODFATHER_IV = new SlayerBossType("tarantula_broodfather_iv", "Tarantula Broodfather", SEntityType.TARANTULA_BROODFATHER, 4, "Debilitating", SlayerMobType.SPIDER, 2400000, 1325, 500, 2000, 50000, new SlayerAbility[] { SlayerBossType.COMBAT_JUMP, SlayerBossType.NOXIOUS });
        SVEN_PACKMASTER_I = new SlayerBossType("sven_packmaster_i", "Sven Packmaster", SEntityType.SVEN_PACKMASTER, 1, "Beginner", SlayerMobType.WOLF, 2000, 60, 5, 250, 100, new SlayerAbility[] { SlayerBossType.AGILE });
        SVEN_PACKMASTER_II = new SlayerBossType("sven_packmaster_ii", "Sven Packmaster", SEntityType.SVEN_PACKMASTER, 2, "Strong", SlayerMobType.WOLF, 40000, 200, 10, 25, 600, 2000, new SlayerAbility[] { SlayerBossType.AGILE, SlayerBossType.TRUE_DAMAGE });
        SVEN_PACKMASTER_III = new SlayerBossType("sven_packmaster_iii", "Sven Packmaster", SEntityType.SVEN_PACKMASTER, 3, "Challenging", SlayerMobType.WOLF, 750000, 450, 50, 100, 1500, 10000, new SlayerAbility[] { SlayerBossType.AGILE, SlayerBossType.TRUE_DAMAGE, SlayerBossType.CALL_THE_PUPS });
        SVEN_PACKMASTER_IV = new SlayerBossType("sven_packmaster_iv", "Sven Packmaster", SEntityType.SVEN_PACKMASTER, 4, "Truly Painful", SlayerMobType.WOLF, 2000000, 1100, 200, 500, 3000, 50000, new SlayerAbility[] { SlayerBossType.AGILE, SlayerBossType.TRUE_DAMAGE, SlayerBossType.CALL_THE_PUPS });
        VOIDGLOOM_SERAPH_I = new SlayerBossType("voidgloom_seraph_i", "Voidgloom Seraph", SEntityType.VOIDGLOOM_SERAPH, 1, "Beginner", SlayerMobType.ENDERMAN, 30000000, 120000, 5, 2750, 100, new SlayerAbility[] { SlayerBossType.DISSONANCE, SlayerBossType.HITSHIELD });
        VOIDGLOOM_SERAPH_II = new SlayerBossType("voidgloom_seraph_ii", "Voidgloom Seraph", SEntityType.VOIDGLOOM_SERAPH, 2, "Strong", SlayerMobType.ENDERMAN, 150000000, 1000000, 25, 6600, 2000, new SlayerAbility[] { SlayerBossType.DISSONANCE, SlayerBossType.HITSHIELD, SlayerBossType.YANG_GLYPHS });
        VOIDGLOOM_SERAPH_III = new SlayerBossType("voidgloom_seraph_iii", "Voidgloom Seraph", SEntityType.VOIDGLOOM_SERAPH, 3, "Challenging", SlayerMobType.ENDERMAN, 666000000, 1200000, 100, 11000, 10000, new SlayerAbility[] { SlayerBossType.DISSONANCE, SlayerBossType.HITSHIELD, SlayerBossType.YANG_GLYPHS, SlayerBossType.NUKEKUBI });
        VOIDGLOOM_SERAPH_IV = new SlayerBossType("voidgloom_seraph_iv", "Voidgloom Seraph", SEntityType.VOIDGLOOM_SERAPH, 4, "Guaranteed Doom", SlayerMobType.ENDERMAN, 1000000000, 1500000, 500, 22000, 50000, new SlayerAbility[] { SlayerBossType.DISSONANCE, SlayerBossType.HITSHIELD, SlayerBossType.YANG_GLYPHS, SlayerBossType.NUKEKUBI, SlayerBossType.HEART_RADI });
    }
    
    public enum SlayerMobType
    {
        ZOMBIE, 
        SPIDER, 
        WOLF, 
        ENDERMAN;
        
        public String getName() {
            switch (this) {
                case ZOMBIE: {
                    return "Zombie";
                }
                case SPIDER: {
                    return "Spider";
                }
                case WOLF: {
                    return "Wolf";
                }
                case ENDERMAN: {
                    return "Enderman";
                }
                default: {
                    return "Unknown";
                }
            }
        }
        
        public String getPluralName() {
            switch (this) {
                case ZOMBIE: {
                    return "Zombies";
                }
                case SPIDER: {
                    return "Spiders";
                }
                case WOLF: {
                    return "Wolves";
                }
                case ENDERMAN: {
                    return "Enderman";
                }
                default: {
                    return "Unknown";
                }
            }
        }
        
        public EntityType getEntityType() {
            switch (this) {
                case ZOMBIE: {
                    return EntityType.ZOMBIE;
                }
                case SPIDER: {
                    return EntityType.SPIDER;
                }
                case WOLF: {
                    return EntityType.WOLF;
                }
                case ENDERMAN: {
                    return EntityType.ENDERMAN;
                }
                default: {
                    return null;
                }
            }
        }
        
        public Material getIcon() {
            switch (this) {
                case ZOMBIE: {
                    return Material.ROTTEN_FLESH;
                }
                case SPIDER: {
                    return Material.WEB;
                }
                case WOLF: {
                    return Material.MUTTON;
                }
                case ENDERMAN: {
                    return Material.ENDER_PEARL;
                }
                default: {
                    return Material.AIR;
                }
            }
        }
        
        public int getLevelForXP(final int xp) {
            if (xp >= 1000000) {
                return 9;
            }
            if (xp >= 400000) {
                return 8;
            }
            if (xp >= 100000) {
                return 7;
            }
            if (xp >= 20000) {
                return 6;
            }
            if (xp >= 5000) {
                return 5;
            }
            switch (this) {
                case ZOMBIE: {
                    if (xp >= 1000) {
                        return 4;
                    }
                    if (xp >= 200) {
                        return 3;
                    }
                    if (xp >= 15) {
                        return 2;
                    }
                    if (xp >= 5) {
                        return 1;
                    }
                    break;
                }
                case SPIDER: {
                    if (xp >= 1000) {
                        return 4;
                    }
                    if (xp >= 200) {
                        return 3;
                    }
                    if (xp >= 25) {
                        return 2;
                    }
                    if (xp >= 5) {
                        return 1;
                    }
                    break;
                }
                case WOLF: {
                    if (xp >= 1500) {
                        return 4;
                    }
                    if (xp >= 250) {
                        return 3;
                    }
                    if (xp >= 30) {
                        return 2;
                    }
                    if (xp >= 10) {
                        return 1;
                    }
                    break;
                }
                case ENDERMAN: {
                    if (xp >= 1500) {
                        return 4;
                    }
                    if (xp >= 250) {
                        return 3;
                    }
                    if (xp >= 30) {
                        return 2;
                    }
                    if (xp >= 10) {
                        return 1;
                    }
                    break;
                }
            }
            return 0;
        }
    }
    
    private static class SlayerAbility
    {
        private final String name;
        private final String[] description;
        
        public SlayerAbility(final String name, final String... description) {
            this.name = name;
            this.description = description;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String[] getDescription() {
            return this.description;
        }
    }
}
