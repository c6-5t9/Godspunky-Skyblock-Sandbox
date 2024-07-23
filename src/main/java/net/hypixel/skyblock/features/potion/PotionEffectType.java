package net.hypixel.skyblock.features.potion;

import org.bukkit.ChatColor;
import java.util.HashMap;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.Repeater;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.util.TriConsumer;
import org.bukkit.entity.Player;
import java.util.function.BiConsumer;
import java.util.Map;

public class PotionEffectType
{
    private static final Map<String, PotionEffectType> POTION_EFFECT_TYPE_CACHE;
    public static final PotionEffectType STRENGTH;
    public static final PotionEffectType FEROCITY;
    public static final PotionEffectType RABBIT;
    public static final PotionEffectType HEALING;
    public static final PotionEffectType JUMP_BOOST;
    public static final PotionEffectType SPEED;
    public static final PotionEffectType ARCHERY;
    public static final PotionEffectType MANA;
    public static final PotionEffectType ADRENALINE;
    public static final PotionEffectType CRITICAL;
    public static final PotionEffectType ABSORPTION;
    public static final PotionEffectType NIGHT_VISION;
    public static final PotionEffectType WATER_BREATH;
    public static final PotionEffectType FIRE_RESISTANCE;
    public static final PotionEffectType HASTE;
    public static final PotionEffectType RESISTANCE;
    public static final PotionEffectType TRUE_RESISTANCE;
    public static final PotionEffectType MAGIC_FIND;
    public static final PotionEffectType STAMINA;
    public static final PotionEffectType SPIRIT;
    private final String name;
    private final String namespace;
    private final String description;
    private final PotionColor color;
    private final BiConsumer<PotionEffect, Player> onDrink;
    private final TriConsumer<PlayerStatistics, Integer, Integer> statsUpdate;
    private final boolean instant;
    private final boolean buff;
    
    public PotionEffectType(final String name, final String namespace, final String description, final PotionColor color, final BiConsumer<PotionEffect, Player> onDrink, final TriConsumer<PlayerStatistics, Integer, Integer> statsUpdate, final boolean instant, final boolean buff) {
        this.name = name;
        this.namespace = namespace;
        this.description = description;
        this.color = color;
        this.onDrink = onDrink;
        this.statsUpdate = statsUpdate;
        this.instant = instant;
        this.buff = buff;
        PotionEffectType.POTION_EFFECT_TYPE_CACHE.put((Object)namespace, (Object)this);
    }
    
    public PotionEffectType(final String name, final String namespace, final String description, final PotionColor color, final TriConsumer<PlayerStatistics, Integer, Integer> statsUpdate, final boolean instant, final boolean buff) {
        this(name, namespace, description, color, null, statsUpdate, instant, buff);
    }
    
    public PotionEffectType(final String name, final String namespace, final String description, final PotionColor color, final BiConsumer<PotionEffect, Player> onDrink, final boolean instant, final boolean buff) {
        this(name, namespace, description, color, onDrink, null, instant, buff);
    }
    
    public PotionEffectType(final String name, final String namespace, final String description, final PotionColor color, final boolean instant, final boolean buff) {
        this(name, namespace, description, color, null, null, instant, buff);
    }
    
    public static PotionEffectType getByNamespace(final String namespace) {
        return (PotionEffectType)PotionEffectType.POTION_EFFECT_TYPE_CACHE.get((Object)namespace.toLowerCase());
    }
    
    public String getDescription(final Object... objects) {
        String description = this.description;
        for (final Object object : objects) {
            description = Sputnik.trans(description.replaceFirst("%s", String.valueOf(object)));
        }
        return description;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PotionEffectType && ((PotionEffectType)o).namespace.equals((Object)this.namespace);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public PotionColor getColor() {
        return this.color;
    }
    
    public BiConsumer<PotionEffect, Player> getOnDrink() {
        return this.onDrink;
    }
    
    public TriConsumer<PlayerStatistics, Integer, Integer> getStatsUpdate() {
        return this.statsUpdate;
    }
    
    public boolean isInstant() {
        return this.instant;
    }
    
    public boolean isBuff() {
        return this.buff;
    }
    
    static {
        POTION_EFFECT_TYPE_CACHE = (Map)new HashMap();
        STRENGTH = new PotionEffectType((Object)ChatColor.DARK_RED + "Strength", "strength", "Increases Strength by %s.", PotionColor.BLOOD_RED, (statistics, slot, level) -> statistics.getStrength().add((int)slot, Double.valueOf(SUtil.getOrDefault((java.util.List<Double>)Arrays.asList((Object[])new Double[] { 5.0, 12.0, 20.0, 30.0, 40.0, 50.0, 60.0, 75.0 }), level - 1, level * 10.0))), false, true);
        FEROCITY = new PotionEffectType((Object)ChatColor.RED + "Ferocity", "ferocity", "Increases Ferocity by %s.", PotionColor.RED, (statistics, slot, level) -> statistics.getFerocity().add((int)slot, Double.valueOf(SUtil.getOrDefault((java.util.List<Double>)Arrays.asList((Object[])new Double[] { 5.0, 12.0, 20.0, 30.0, 40.0, 50.0, 60.0, 75.0 }), level - 1, level * 10.0))), false, true);
        RABBIT = new PotionEffectType((Object)ChatColor.GREEN + "Rabbit", "rabbit", "Grants Jump Boost %s and +%s Speed.", PotionColor.DARK_GREEN, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, (int)effect.getDuration(), (effect.getLevel() % 2 == 0) ? (effect.getLevel() / 2) : ((effect.getLevel() + 1) / 2)))), (statistics, slot, level) -> statistics.getSpeed().add((int)slot, Double.valueOf(level * 10.0 / 100.0)), false, true);
        HEALING = new PotionEffectType((Object)ChatColor.RED + "Healing", "healing", "Grants an instant %s Health boost.", PotionColor.RED, (BiConsumer<PotionEffect, Player>)((effect, player) -> player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 50, 100, 150, 200, 300, 400, 500 }), effect.getLevel() - 1, (effect.getLevel() - 3) * 100)))), true, true);
        JUMP_BOOST = new PotionEffectType((Object)ChatColor.AQUA + "Jump Boost", "jump_boost", "Increases your jump height.", PotionColor.TWILIGHT_BLUE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, (int)effect.getDuration(), effect.getLevel() - 1))), false, true);
        SPEED = new PotionEffectType((Object)ChatColor.BLUE + "Speed", "speed", "Grants a %s Speed boost.", PotionColor.LIGHT_BLUE, (statistics, slot, level) -> statistics.getSpeed().add((int)slot, Double.valueOf(level * 5.0 / 100.0)), false, true);
        ARCHERY = new PotionEffectType((Object)ChatColor.AQUA + "Archery", "archery", "Increases Bow Damage by %s%.", PotionColor.LIGHT_BLUE, false, true);
        MANA = new PotionEffectType((Object)ChatColor.BLUE + "Mana", "mana", "Grants an instant %s Mana boost.", PotionColor.DARK_BLUE, (BiConsumer<PotionEffect, Player>)((effect, player) -> {
            final Integer n = (Integer)Repeater.MANA_MAP.put((Object)player.getUniqueId(), (Object)((int)Repeater.MANA_MAP.get((Object)player.getUniqueId()) + SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 25, 50, 75, 100, 150, 200, 300, 400 }), effect.getLevel() - 1, (effect.getLevel() - 4) * 100)));
        }), true, true);
        ADRENALINE = new PotionEffectType((Object)ChatColor.RED + "Adrenaline", "adrenaline", "Grants %s Absorption health and an increase of %s Speed.", PotionColor.PURPLE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.ABSORPTION, (int)effect.getDuration(), (int)(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), effect.getLevel() - 1, (effect.getLevel() - 5) * 100) * 0.25 - 1.0)))), (statistics, slot, level) -> statistics.getSpeed().add((int)slot, Double.valueOf(level * 5.0 / 100.0)), false, true);
        CRITICAL = new PotionEffectType((Object)ChatColor.DARK_RED + "Critical", "critical", "Gives a %s% increase in Crit Chance and a %s% increase in Crit Damage.", PotionColor.DARK_RED, (statistics, slot, level) -> {
            statistics.getCritChance().add((int)slot, Double.valueOf(0.05 + level * 5.0 / 100.0));
            statistics.getCritDamage().add((int)slot, Double.valueOf(level * 10.0 / 100.0));
            return;
        }, false, true);
        ABSORPTION = new PotionEffectType((Object)ChatColor.GOLD + "Absorption", "absorption", "Grants a boost of %s Absorption health.", PotionColor.ORANGE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.ABSORPTION, (int)effect.getDuration(), (int)(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), effect.getLevel() - 1, (effect.getLevel() - 5) * 100) * 0.25 - 1.0)))), false, true);
        NIGHT_VISION = new PotionEffectType((Object)ChatColor.DARK_PURPLE + "Night Vision", "night_vision", "Grants greater visibility at night.", PotionColor.DARK_BLUE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION, (int)effect.getDuration(), (int)(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), effect.getLevel() - 1, (effect.getLevel() - 5) * 100) * 0.25 - 1.0)))), false, true);
        WATER_BREATH = new PotionEffectType((Object)ChatColor.BLUE + "Water Breathing", "water_breath", "Grants ability to not taking drowning damage.", PotionColor.DARK_BLUE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.WATER_BREATHING, (int)effect.getDuration(), (int)(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), effect.getLevel() - 1, (effect.getLevel() - 5) * 100) * 0.25 - 1.0)))), false, true);
        FIRE_RESISTANCE = new PotionEffectType((Object)ChatColor.GOLD + "Fire Resistance", "fire_resistance", "Grants immunity to fire and lava.", PotionColor.ORANGE, (BiConsumer<PotionEffect, Player>)((effect, player) -> PlayerUtils.replacePotionEffect(player, new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE, (int)effect.getDuration(), (int)(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), effect.getLevel() - 1, (effect.getLevel() - 5) * 100) * 0.25 - 1.0)))), false, true);
        HASTE = new PotionEffectType((Object)ChatColor.YELLOW + "Haste", "haste", "Increases your mining speed.", PotionColor.ORANGE, (BiConsumer<PotionEffect, Player>)((effect, player) -> {
            player.removePotionEffect(org.bukkit.potion.PotionEffectType.FAST_DIGGING);
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FAST_DIGGING, (int)effect.getDuration(), effect.getLevel() - 1));
        }), false, true);
        RESISTANCE = new PotionEffectType((Object)ChatColor.GREEN + "Resistance", "resistance", "Increases Defense by %s.", PotionColor.DARK_GREEN, (statistics, slot, level) -> statistics.getDefense().add((int)slot, Double.valueOf(SUtil.getOrDefault((java.util.List<Double>)Arrays.asList((Object[])new Double[] { 5.0, 10.0, 15.0, 20.0, 30.0, 40.0, 50.0, 60.0 }), level - 1, level * 10.0 - 20.0))), false, true);
        TRUE_RESISTANCE = new PotionEffectType((Object)ChatColor.WHITE + "True Resistance", "true_resistance", "Increases True Defense by %s.", PotionColor.TWILIGHT_BLUE, (statistics, slot, level) -> statistics.getTrueDefense().add((int)slot, Double.valueOf(level * 5.0)), false, true);
        MAGIC_FIND = new PotionEffectType((Object)ChatColor.AQUA + "Magic Find", "magic_find", "Increases Magic Find by %s%.", PotionColor.LIGHT_BLUE, (statistics, slot, level) -> statistics.getMagicFind().add((int)slot, Double.valueOf(level * 0.02)), false, true);
        STAMINA = new PotionEffectType((Object)ChatColor.GREEN + "Stamina", "stamina", "Grants an instant %s Health and %s Mana boost.", PotionColor.TWILIGHT_BLUE, (BiConsumer<PotionEffect, Player>)((effect, player) -> {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 150, 215, 315, 515 }), effect.getLevel() - 1, (effect.getLevel() + 1) * 100 + 15)));
            Repeater.MANA_MAP.put((Object)player.getUniqueId(), (Object)((int)Repeater.MANA_MAP.get((Object)player.getUniqueId()) + 50 * effect.getLevel()));
        }), true, true);
        SPIRIT = new PotionEffectType((Object)ChatColor.AQUA + "Spirit", "spirit", "Grants a %s increase in Speed and a %s% increase in Crit Damage.", PotionColor.LIGHT_BLUE, (statistics, slot, level) -> {
            statistics.getSpeed().add((int)slot, Double.valueOf(level * 10.0 / 100.0));
            statistics.getCritDamage().add((int)slot, Double.valueOf(level * 10.0 / 100.0));
        }, false, true);
    }
}
