package net.hypixel.skyblock.util;

import java.util.Collections;
import java.util.Arrays;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.entity.EntityType;
import net.hypixel.skyblock.features.region.RegionType;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.Material;
import java.util.List;

public final class Groups
{
    public static final List<Material> SWORDS;
    public static final List<Material> PICKAXES;
    public static final List<Material> AXES;
    public static final List<Material> SHOVELS;
    public static final List<Material> HOES;
    public static final List<Material> LEATHER_ARMOR;
    public static final List<Material> IRON_ARMOR;
    public static final List<Material> GOLD_ARMOR;
    public static final List<Material> DIAMOND_ARMOR;
    public static final List<InventoryAction> PICKUP_FROM_INVENTORY;
    public static final List<SMaterial> WITHER_HELMETS;
    public static final List<SMaterial> WITHER_CHESTPLATES;
    public static final List<SMaterial> WITHER_LEGGINGS;
    public static final List<SMaterial> WITHER_BOOTS;
    public static final List<RegionType> MINING_REGIONS;
    public static final List<RegionType> DWARVENS_MINE;
    public static final List<RegionType> DEEP_CAVERNS_REGIONS;
    public static final List<EntityType> UNDEAD_MOBS;
    public static final List<EntityType> ENDERMAN;
    public static final List<EntityType> ENDERDRAGON;
    public static final List<EntityType> ARTHROPODS;
    public static final List<RegionType> FORAGING_REGIONS;
    public static final List<RegionType> FARMING_REGIONS;
    public static final List<Material> FARMING_MATERIALS;
    public static final List<SEntityType> END_MOBS;
    public static final List<Material> EXCHANGEABLE_RECIPE_RESULTS;
    
    static {
        SWORDS = Arrays.asList((Object[])new Material[] { Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD });
        PICKAXES = Arrays.asList((Object[])new Material[] { Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE });
        AXES = Arrays.asList((Object[])new Material[] { Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE });
        SHOVELS = Arrays.asList((Object[])new Material[] { Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE });
        HOES = Arrays.asList((Object[])new Material[] { Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE });
        LEATHER_ARMOR = Arrays.asList((Object[])new Material[] { Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS });
        IRON_ARMOR = Arrays.asList((Object[])new Material[] { Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS });
        GOLD_ARMOR = Arrays.asList((Object[])new Material[] { Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS });
        DIAMOND_ARMOR = Arrays.asList((Object[])new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS });
        PICKUP_FROM_INVENTORY = Arrays.asList((Object[])new InventoryAction[] { InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_SOME, InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.HOTBAR_SWAP, InventoryAction.SWAP_WITH_CURSOR, InventoryAction.COLLECT_TO_CURSOR });
        WITHER_HELMETS = Arrays.asList((Object[])new SMaterial[] { SMaterial.STORM_HELMET, SMaterial.NECRON_HELMET });
        WITHER_CHESTPLATES = Arrays.asList((Object[])new SMaterial[] { SMaterial.STORM_CHESTPLATE, SMaterial.NECRON_CHESTPLATE });
        WITHER_LEGGINGS = Arrays.asList((Object[])new SMaterial[] { SMaterial.STORM_LEGGINGS, SMaterial.NECRON_LEGGINGS });
        WITHER_BOOTS = Arrays.asList((Object[])new SMaterial[] { SMaterial.STORM_BOOTS, SMaterial.NECRON_BOOTS });
        MINING_REGIONS = Arrays.asList((Object[])new RegionType[] { RegionType.COAL_MINE, RegionType.GOLD_MINE, RegionType.DEEP_CAVERN, RegionType.GUNPOWDER_MINES, RegionType.LAPIS_QUARRY, RegionType.PIGMENS_DEN, RegionType.SLIMEHILL, RegionType.DIAMOND_RESERVE, RegionType.OBSIDIAN_SANCTUARY, RegionType.THE_END, RegionType.DRAGONS_NEST, RegionType.BLAZING_FORTRESS, RegionType.DWARVEN_MINES_VILLAGE, RegionType.DWARVEN_CLIFFSIDE_VEINS, RegionType.DWARVEN_GREAT_ICE_WALL, RegionType.DWARVEN_RAMPARTS_QUARRY, RegionType.DWARVEN_MINES_GATE, RegionType.DWARVEN_MINES });
        DWARVENS_MINE = Arrays.asList((Object[])new RegionType[] { RegionType.DWARVEN_MINES_VILLAGE, RegionType.DWARVEN_CLIFFSIDE_VEINS, RegionType.DWARVEN_GREAT_ICE_WALL, RegionType.DWARVEN_RAMPARTS_QUARRY, RegionType.DWARVEN_MINES_GATE, RegionType.DWARVEN_MINES });
        DEEP_CAVERNS_REGIONS = Arrays.asList((Object[])new RegionType[] { RegionType.DEEP_CAVERN, RegionType.GUNPOWDER_MINES, RegionType.LAPIS_QUARRY, RegionType.PIGMENS_DEN, RegionType.SLIMEHILL, RegionType.DIAMOND_RESERVE, RegionType.OBSIDIAN_SANCTUARY });
        UNDEAD_MOBS = Arrays.asList((Object[])new EntityType[] { EntityType.ZOMBIE, EntityType.PIG_ZOMBIE, EntityType.WITHER, EntityType.WITHER_SKULL, EntityType.SKELETON });
        ENDERMAN = Collections.singletonList((Object)EntityType.ENDERMAN);
        ENDERDRAGON = Collections.singletonList((Object)EntityType.ENDER_DRAGON);
        ARTHROPODS = Arrays.asList((Object[])new EntityType[] { EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.SILVERFISH });
        FORAGING_REGIONS = Arrays.asList((Object[])new RegionType[] { RegionType.FOREST, RegionType.BIRCH_PARK, RegionType.SPRUCE_WOODS, RegionType.DARK_THICKET, RegionType.SAVANNA_WOODLAND, RegionType.JUNGLE_ISLAND });
        FARMING_REGIONS = Arrays.asList((Object[])new RegionType[] { RegionType.THE_BARN, RegionType.FARM, RegionType.MUSHROOM_DESERT });
        FARMING_MATERIALS = Arrays.asList((Object[])new Material[] { Material.CROPS, Material.POTATO, Material.CARROT, Material.MELON_BLOCK, Material.PUMPKIN, Material.SUGAR_CANE_BLOCK, Material.CACTUS, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.COCOA });
        END_MOBS = Arrays.asList((Object[])new SEntityType[] { SEntityType.WATCHER, SEntityType.ZEALOT, SEntityType.ENDER_CHEST_ZEALOT, SEntityType.SPECIAL_ZEALOT, SEntityType.WEAK_ENDERMAN, SEntityType.ENDERMAN, SEntityType.STRONG_ENDERMAN, SEntityType.OBSIDIAN_DEFENDER, SEntityType.OLD_DRAGON, SEntityType.PROTECTOR_DRAGON, SEntityType.STRONG_DRAGON, SEntityType.SUPERIOR_DRAGON, SEntityType.UNSTABLE_DRAGON, SEntityType.WISE_DRAGON, SEntityType.YOUNG_DRAGON });
        EXCHANGEABLE_RECIPE_RESULTS = Arrays.asList((Object[])new Material[] { Material.WORKBENCH, Material.CHEST, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_AXE, Material.WOOD_SWORD, Material.WOOD_SPADE, Material.WOOD_PLATE, Material.STICK });
    }
}
