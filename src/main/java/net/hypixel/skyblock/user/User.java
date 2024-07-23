package net.hypixel.skyblock.user;

import net.hypixel.skyblock.features.auction.AuctionBid;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.features.reforge.Reforge;
import net.hypixel.skyblock.features.dungeons.stats.ItemSerial;
import net.hypixel.skyblock.item.GenericItemType;
import net.minecraft.server.v1_8_R3.Packet;
import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.hypixel.skyblock.features.auction.AuctionItem;
import org.bukkit.Location;
import org.bukkit.block.Block;
import net.hypixel.skyblock.entity.StaticWardenManager;
import org.bukkit.metadata.MetadataValue;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanHuman;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanFunction;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.SputnikPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.GameMode;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Arrow;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.EntityType;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.features.slayer.SlayerBossType;
import net.hypixel.skyblock.features.skill.BerserkSkill;
import net.hypixel.skyblock.features.skill.MageSkill;
import net.hypixel.skyblock.features.skill.HealerSkill;
import net.hypixel.skyblock.features.skill.TankSkill;
import net.hypixel.skyblock.features.skill.ArcherSkill;
import net.hypixel.skyblock.features.skill.CatacombsSkill;
import net.hypixel.skyblock.features.skill.EnchantingSkill;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.features.collection.ItemCollectionRewards;
import net.hypixel.skyblock.features.collection.ItemCollectionReward;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.features.requirement.enums.SkillType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.Collection;
import de.tr7zw.nbtapi.NBTItem;
import java.util.Arrays;
import net.hypixel.skyblock.database.DatabaseManager;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import java.util.stream.Collectors;
import org.bukkit.inventory.Inventory;
import net.hypixel.skyblock.api.serializer.BukkitSerializeClass;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Sound;
import net.hypixel.skyblock.features.region.RegionType;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.quest.QuestLine;
import net.hypixel.skyblock.gui.PetsGUI;
import java.util.Iterator;
import net.hypixel.skyblock.features.potion.PotionEffect;
import net.hypixel.skyblock.features.potion.PotionEffectType;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.ArrayList;
import net.hypixel.skyblock.config.Config;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.ranks.PlayerRank;
import net.hypixel.skyblock.features.auction.AuctionEscrow;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.features.potion.ActivePotionEffect;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.features.region.Region;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.hypixel.skyblock.features.collection.ItemCollection;
import org.bson.Document;
import net.hypixel.skyblock.SkyBlock;
import java.util.UUID;
import java.util.Map;
import java.io.File;

public class User
{
    private static final File USER_FOLDER;
    public static final int ISLAND_SIZE = 125;
    public static final Map<UUID, User> USER_CACHE;
    private static final SkyBlock plugin;
    public final Map<String, Object> dataCache;
    public long sadancollections;
    public long totalfloor6run;
    private UUID uuid;
    private Document dataDocument;
    public final Map<ItemCollection, Integer> collections;
    private long coins;
    private long bits;
    private long bankCoins;
    private List<ItemStack> stashedItems;
    private int cooldownAltar;
    private boolean headShot;
    private static final boolean multiServer = false;
    private boolean playingSong;
    private boolean inDanger;
    private Region lastRegion;
    private final Map<SMaterial, Integer> quiver;
    private final List<ActivePotionEffect> effects;
    private List<String> talkedNPCs;
    private List<String> talkedVillagers;
    double farmingXP;
    private boolean boneToZeroDamage;
    private boolean cooldownAPI;
    double miningXP;
    double combatXP;
    private double enchantXP;
    private double archerXP;
    private double cataXP;
    private double berserkXP;
    private double healerXP;
    private double tankXP;
    private double mageXP;
    double foragingXP;
    final int[] highestSlayers;
    final int[] slayerXP;
    private final int[] crystalLVL;
    private boolean saveable;
    private int bonusFerocity;
    private boolean fatalActive;
    private boolean permanentCoins;
    private SlayerQuest slayerQuest;
    List<Pet.PetItem> pets;
    private List<String> unlockedRecipes;
    AuctionSettings auctionSettings;
    private boolean auctionCreationBIN;
    private AuctionEscrow auctionEscrow;
    private boolean voidlingWardenActive;
    private boolean waitingForSign;
    private String signContent;
    private boolean isCompletedSign;
    public List<String> completedQuests;
    public List<String> completedObjectives;
    private Double islandX;
    private Double islandZ;
    public PlayerRank rank;
    public Player player;
    private Config config;
    public static Config sbc;
    public List<String> foundzone;
    public boolean hasIsland;
    
    private User(final UUID uuid) {
        this.stashedItems = (List<ItemStack>)new ArrayList();
        this.cooldownAltar = 0;
        this.headShot = false;
        this.playingSong = false;
        this.inDanger = false;
        this.dataCache = (Map<String, Object>)new HashMap();
        this.player = Bukkit.getPlayer(uuid);
        this.boneToZeroDamage = false;
        this.rank = PlayerRank.DEFAULT;
        this.cooldownAPI = false;
        this.talkedVillagers = (List<String>)new CopyOnWriteArrayList();
        this.saveable = true;
        this.waitingForSign = false;
        this.signContent = null;
        this.isCompletedSign = false;
        this.uuid = uuid;
        this.hasIsland = false;
        this.foundzone = (List<String>)new ArrayList();
        this.collections = ItemCollection.getDefaultCollections();
        this.totalfloor6run = 0L;
        this.coins = 0L;
        this.bits = 0L;
        this.bankCoins = 0L;
        this.sadancollections = 0L;
        this.lastRegion = null;
        this.talkedNPCs = (List<String>)new CopyOnWriteArrayList();
        this.quiver = (Map<SMaterial, Integer>)new HashMap();
        this.effects = (List<ActivePotionEffect>)new ArrayList();
        this.unlockedRecipes = (List<String>)new ArrayList();
        this.completedQuests = (List<String>)new ArrayList();
        this.completedObjectives = (List<String>)new ArrayList();
        this.farmingXP = 0.0;
        this.miningXP = 0.0;
        this.combatXP = 0.0;
        this.foragingXP = 0.0;
        this.enchantXP = 0.0;
        this.highestSlayers = new int[4];
        this.slayerXP = new int[4];
        this.crystalLVL = new int[8];
        this.permanentCoins = false;
        this.pets = (List<Pet.PetItem>)new ArrayList();
        this.auctionSettings = new AuctionSettings();
        this.auctionCreationBIN = false;
        this.auctionEscrow = new AuctionEscrow();
        if (User.sbc.getBoolean("Config")) {
            final String path = uuid.toString() + ".yml";
            if (!User.USER_FOLDER.exists()) {
                User.USER_FOLDER.mkdirs();
            }
            final File configFile = new File(User.USER_FOLDER, path);
            boolean save = false;
            try {
                if (!configFile.exists()) {
                    save = true;
                    configFile.createNewFile();
                }
            }
            catch (final IOException ex) {
                ex.printStackTrace();
            }
            this.config = new Config(User.USER_FOLDER, path);
            if (save) {
                this.configsave();
            }
            this.configload();
        }
        User.USER_CACHE.put((Object)uuid, (Object)this);
    }
    
    public Config getConfig() {
        return this.config;
    }
    
    public void unload() {
        User.USER_CACHE.remove((Object)this.uuid);
    }
    
    public void configload() {
        this.uuid = UUID.fromString(this.config.getString("uuid"));
        if (this.config.contains("collections")) {
            for (final String identifier : this.config.getConfigurationSection("collections").getKeys(false)) {
                this.collections.put((Object)ItemCollection.getByIdentifier(identifier), (Object)this.config.getInt("collections." + identifier));
            }
        }
        this.islandX = (this.config.contains("island.x") ? Double.valueOf(this.config.getDouble("island.x")) : null);
        this.islandZ = (this.config.contains("island.z") ? Double.valueOf(this.config.getDouble("island.z")) : null);
        this.rank = PlayerRank.valueOf(this.config.getString("rank"));
        this.hasIsland = this.config.getBoolean("hasIsland");
        this.coins = this.config.getLong("coins");
        this.bits = this.config.getLong("bits");
        this.bankCoins = this.config.getLong("bankCoins");
        final Region lastRegion = (this.config.getString("lastRegion") != null) ? Region.get(this.config.getString("lastRegion")) : null;
        this.lastRegion = lastRegion;
        final Region region = lastRegion;
        if (this.config.contains("quiver")) {
            for (final String m : this.config.getConfigurationSection("quiver").getKeys(false)) {
                this.quiver.put((Object)SMaterial.getMaterial(m), (Object)this.config.getInt("quiver." + m));
            }
        }
        if (this.config.contains("effects")) {
            for (final String key : this.config.getConfigurationSection("effects").getKeys(false)) {
                this.effects.add((Object)new ActivePotionEffect(new PotionEffect(PotionEffectType.getByNamespace(key), this.config.getInt("effects." + key + ".level"), this.config.getLong("effects." + key + ".duration")), this.config.getLong("effects." + key + ".remaining")));
            }
        }
        this.totalfloor6run = this.config.getLong("dungeons.floor6.run");
        this.sadancollections = this.config.getLong("dungeons.boss.sadan");
        this.farmingXP = this.config.getDouble("xp.farming");
        this.miningXP = this.config.getDouble("xp.mining");
        this.combatXP = this.config.getDouble("xp.combat");
        this.foragingXP = this.config.getDouble("xp.foraging");
        this.enchantXP = this.config.getDouble("xp.enchant");
        this.cataXP = this.config.getDouble("xp.dungeons.cata");
        this.archerXP = this.config.getDouble("xp.dungeons.arch");
        this.mageXP = this.config.getDouble("xp.dungeons.mage");
        this.tankXP = this.config.getDouble("xp.dungeons.tank");
        this.berserkXP = this.config.getDouble("xp.dungeons.bers");
        this.healerXP = this.config.getDouble("xp.dungeons.heal");
        this.highestSlayers[0] = this.config.getInt("slayer.revenantHorror.highest");
        this.highestSlayers[1] = this.config.getInt("slayer.tarantulaBroodfather.highest");
        this.highestSlayers[2] = this.config.getInt("slayer.svenPackmaster.highest");
        this.highestSlayers[3] = this.config.getInt("slayer.voidgloomSeraph.highest");
        this.slayerXP[0] = this.config.getInt("xp.slayer.revenantHorror");
        this.slayerXP[1] = this.config.getInt("xp.slayer.tarantulaBroodfather");
        this.slayerXP[2] = this.config.getInt("xp.slayer.svenPackmaster");
        this.slayerXP[3] = this.config.getInt("xp.slayer.voidgloomSeraph");
        this.permanentCoins = this.config.getBoolean("permanentCoins");
        this.slayerQuest = (SlayerQuest)this.config.get("slayer.quest");
        if (this.config.contains("pets")) {
            this.pets = (List<Pet.PetItem>)this.config.getList("pets");
        }
        if (this.config.contains("unlockedRecipes")) {
            this.unlockedRecipes = (List<String>)this.config.getList("unlockedRecipes");
        }
        if (this.config.contains("talked_npcs")) {
            this.talkedNPCs = (List<String>)this.config.getList("talked_npcs");
        }
        if (this.config.contains("talked_villagers")) {
            this.talkedVillagers = (List<String>)this.config.getList("talked_villagers");
        }
        if (this.config.contains("foundzones")) {
            this.foundzone = (List<String>)this.config.getList("foundzones");
        }
        if (this.config.contains("completedQuest")) {
            this.completedQuests = (List<String>)this.config.getList("completedQuest");
        }
        if (this.config.contains("completedObjectives")) {
            this.completedObjectives = (List<String>)this.config.getList("completedObjectives");
        }
        this.auctionSettings = (AuctionSettings)this.config.get("auction.settings");
        if (this.auctionSettings == null) {
            this.auctionSettings = new AuctionSettings();
        }
        this.auctionCreationBIN = this.config.getBoolean("auction.creationBIN");
        this.auctionEscrow = (AuctionEscrow)this.config.get("auction.escrow");
        if (this.auctionEscrow == null) {
            this.auctionEscrow = new AuctionEscrow();
        }
    }
    
    public void configsave() {
        this.config.set("uuid", (Object)this.uuid.toString());
        this.config.set("rank", (Object)this.rank.toString());
        this.config.set("collections", (Object)null);
        this.config.set("island.x", (Object)this.islandX);
        this.config.set("island.z", (Object)this.islandZ);
        for (final Map.Entry<ItemCollection, Integer> entry : this.collections.entrySet()) {
            this.config.set("collections." + ((ItemCollection)entry.getKey()).getIdentifier(), entry.getValue());
        }
        this.config.set("hasIsland", (Object)this.hasIsland);
        this.config.set("coins", (Object)this.coins);
        this.config.set("bits", (Object)this.bits);
        this.config.set("bankCoins", (Object)this.bankCoins);
        if (this.lastRegion != null) {
            this.config.set("lastRegion", (Object)this.lastRegion.getName());
        }
        this.config.set("quiver", (Object)null);
        for (final Map.Entry<SMaterial, Integer> entry2 : this.quiver.entrySet()) {
            this.config.set("quiver." + ((SMaterial)entry2.getKey()).name().toLowerCase(), entry2.getValue());
        }
        this.config.set("effects", (Object)null);
        for (final ActivePotionEffect activePotionEffect : this.effects) {
            final PotionEffectType type = activePotionEffect.getEffect().getType();
            this.config.set("effects." + type.getNamespace() + ".level", (Object)activePotionEffect.getEffect().getLevel());
            this.config.set("effects." + type.getNamespace() + ".duration", (Object)activePotionEffect.getEffect().getDuration());
            this.config.set("effects." + type.getNamespace() + ".remaining", (Object)activePotionEffect.getRemaining());
        }
        this.config.set("dungeons.floor6.run", (Object)this.totalfloor6run);
        this.config.set("dungeons.boss.sadan", (Object)this.sadancollections);
        this.config.set("xp.farming", (Object)this.farmingXP);
        this.config.set("xp.mining", (Object)this.miningXP);
        this.config.set("xp.combat", (Object)this.combatXP);
        this.config.set("xp.foraging", (Object)this.foragingXP);
        this.config.set("xp.enchant", (Object)this.enchantXP);
        this.config.set("xp.dungeons.cata", (Object)this.cataXP);
        this.config.set("xp.dungeons.arch", (Object)this.archerXP);
        this.config.set("xp.dungeons.bers", (Object)this.berserkXP);
        this.config.set("xp.dungeons.heal", (Object)this.healerXP);
        this.config.set("xp.dungeons.mage", (Object)this.mageXP);
        this.config.set("xp.dungeons.tank", (Object)this.tankXP);
        this.config.set("slayer.revenantHorror.highest", (Object)this.highestSlayers[0]);
        this.config.set("slayer.tarantulaBroodfather.highest", (Object)this.highestSlayers[1]);
        this.config.set("slayer.svenPackmaster.highest", (Object)this.highestSlayers[2]);
        this.config.set("slayer.voidgloomSeraph.highest", (Object)this.highestSlayers[3]);
        this.config.set("xp.slayer.revenantHorror", (Object)this.slayerXP[0]);
        this.config.set("xp.slayer.tarantulaBroodfather", (Object)this.slayerXP[1]);
        this.config.set("xp.slayer.svenPackmaster", (Object)this.slayerXP[2]);
        this.config.set("xp.slayer.voidgloomSeraph", (Object)this.slayerXP[3]);
        this.config.set("permanentCoins", (Object)this.permanentCoins);
        this.config.set("slayer.quest", (Object)this.slayerQuest);
        this.config.set("pets", (Object)this.pets);
        this.config.set("unlockedRecipes", (Object)this.unlockedRecipes);
        this.config.set("talked_npcs", (Object)this.talkedNPCs);
        this.config.set("talked_villagers", (Object)this.talkedVillagers);
        this.config.set("completedObjectives", (Object)this.completedObjectives);
        this.config.set("completedQuest", (Object)this.completedQuests);
        this.config.set("unlockedRecipes", (Object)this.unlockedRecipes);
        this.config.set("foundzones", (Object)this.foundzone);
        this.config.set("auction.settings", (Object)this.auctionSettings);
        this.config.set("auction.creationBIN", (Object)this.auctionCreationBIN);
        this.config.set("auction.escrow", (Object)this.auctionEscrow);
        if (Bukkit.getPlayer(this.uuid) != null && Bukkit.getPlayer(this.uuid).isOnline()) {
            this.config.set("configures.showPets", (Object)PetsGUI.getShowPet(Bukkit.getPlayer(this.uuid)));
            this.config.set("configures.autoSlayer", (Object)PlayerUtils.isAutoSlayer(Bukkit.getPlayer(this.uuid)));
        }
        this.config.save();
    }
    
    public synchronized void addTalkedNPC(final String name) {
        if (!this.talkedNPCs.contains((Object)name)) {
            this.talkedNPCs.add((Object)name);
        }
    }
    
    public synchronized void addTalkedVillager(final String name) {
        if (!this.talkedVillagers.contains((Object)name)) {
            this.talkedVillagers.add((Object)name);
        }
    }
    
    public List<String> getdiscoveredzones() {
        return this.foundzone;
    }
    
    public void addnewzone(final String q) {
        this.foundzone.add((Object)q);
    }
    
    public void addCompletedQuest(final String questName) {
        this.completedQuests.add((Object)questName);
    }
    
    public void addCompletedObjectives(final String questName) {
        this.completedObjectives.add((Object)questName);
    }
    
    public QuestLine getQuestLine() {
        return SkyBlock.getPlugin().getQuestLineHandler().getFromPlayer(this);
    }
    
    public void send(final String message, final Object... args) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        player.sendMessage(Sputnik.trans(String.format(message, args)));
    }
    
    public void onNewZone(final RegionType zone, final String... features) {
        this.send("");
        this.send("§6§l NEW AREA DISCOVERED!");
        this.send("§7  \u23e3 " + (Object)zone.getColor() + zone.getName());
        this.send("");
        if (features.length > 0) {
            for (final String feature : features) {
                this.send("§7   \u2b1b §f%s", feature);
            }
        }
        else {
            this.send("§7   \u2b1b §cNot much yet!");
        }
        this.send("");
        Bukkit.getPlayer(this.uuid).playSound(Bukkit.getPlayer(this.uuid).getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
        final Player player = Bukkit.getPlayer(this.uuid);
        SUtil.sendTypedTitle(player, (Object)zone.getColor() + zone.getName(), PacketPlayOutTitle.EnumTitleAction.TITLE);
        SUtil.sendTypedTitle(player, "§6§lNEW AREA DISCOVERED!", PacketPlayOutTitle.EnumTitleAction.SUBTITLE);
    }
    
    public Region getRegion() {
        if (this.isOnIsland() || this.isOnUserIsland()) {
            return Region.getIslandRegion();
        }
        final String worldName = this.player.getWorld().getName();
        if (worldName.startsWith("f6")) {
            return Region.get("Catacombs (F6)");
        }
        return Region.getRegionOfEntity((Entity)Bukkit.getPlayer(this.uuid));
    }
    
    public void asyncSavingData() {
        if (SkyBlock.getPlugin().config.getBoolean("Config")) {
            this.configsave();
        }
        else {
            this.save();
        }
    }
    
    public void loadStatic() {
        final Player player = Bukkit.getPlayer(this.uuid);
        final User user = getUser(this.uuid);
        PlayerUtils.AUTO_SLAYER.put((Object)player.getUniqueId(), (Object)true);
        PetsGUI.setShowPets(player, true);
    }
    
    public void kick() {
        if (this.toBukkitPlayer().isOnline()) {
            this.toBukkitPlayer().kickPlayer("server restart...");
        }
    }
    
    public CompletableFuture<Void> save() {
        final CompletableFuture<Void> future = (CompletableFuture<Void>)new CompletableFuture();
        SUtil.runAsync((Runnable)new Runnable() {
            public void run() {
                User.this.setDataProperty("uuid", User.this.uuid.toString());
                User.this.setDataProperty("coins", User.this.coins);
                User.this.setDataProperty("bits", User.this.bits);
                User.this.setDataProperty("bankCoins", User.this.bankCoins);
                User.this.setDataProperty("rank", User.this.rank.toString());
                User.this.setDataProperty("islandX", User.this.islandX);
                User.this.setDataProperty("islandZ", User.this.islandZ);
                User.this.setDataProperty("hasIsland", User.this.hasIsland);
                final HashMap<String, Integer> collectionsData = (HashMap<String, Integer>)new HashMap();
                for (final ItemCollection collection : ItemCollection.getCollections()) {
                    collectionsData.put((Object)collection.getIdentifier(), (Object)User.this.getCollection(collection));
                }
                User.this.setDataProperty("collections", collectionsData);
                if (User.this.lastRegion != null) {
                    User.this.setDataProperty("lastRegion", User.this.getLastRegion().getName());
                }
                final HashMap quiverData = new HashMap();
                User.this.getQuiver().forEach((key, value) -> quiverData.put((Object)key.name(), (Object)value));
                User.this.setDataProperty("quiver", quiverData);
                final ArrayList<Document> effects = (ArrayList<Document>)new ArrayList();
                for (final ActivePotionEffect effect : User.this.getEffects()) {
                    final Document effectDocument = new Document().append("key", (Object)effect.getEffect().getType().getNamespace()).append("level", (Object)effect.getEffect().getLevel()).append("duration", (Object)effect.getEffect().getDuration()).append("remaining", (Object)effect.getRemaining());
                    effects.add((Object)effectDocument);
                }
                final HashMap<String, Object> data = (HashMap<String, Object>)new HashMap();
                data.put((Object)"armor", (Object)BukkitSerializeClass.itemStackArrayToBase64(User.this.player.getInventory().getArmorContents()));
                data.put((Object)"enderchest", (Object)User.this.getPureListFrom(User.this.player.getEnderChest()));
                data.put((Object)"minecraft_xp", (Object)Sputnik.getTotalExperience(User.this.player));
                data.put((Object)"lastslot", (Object)User.this.player.getInventory().getHeldItemSlot());
                data.put((Object)"inventory", (Object)User.this.getPureListFrom((Inventory)User.this.player.getInventory()));
                ItemStack[] is = new ItemStack[User.this.stashedItems.size()];
                is = (ItemStack[])User.this.stashedItems.toArray((Object[])is);
                data.put((Object)"stash", (Object)BukkitSerializeClass.itemStackArrayToBase64(is));
                User.this.setDataProperty("data", data);
                final HashMap<String, List<String>> quest = (HashMap<String, List<String>>)new HashMap();
                quest.put((Object)"talkedvillager", (Object)User.this.talkedVillagers);
                quest.put((Object)"completedQuests", (Object)User.this.completedQuests);
                quest.put((Object)"completedObjectives", (Object)User.this.completedObjectives);
                User.this.setDataProperty("quest", quest);
                User.this.setDataProperty("totalfloor6run", User.this.totalfloor6run);
                User.this.setDataProperty("sadanCollections", User.this.sadancollections);
                User.this.setDataProperty("effects", effects);
                User.this.setDataProperty("skillFarmingXp", User.this.farmingXP);
                User.this.setDataProperty("skillMiningXp", User.this.miningXP);
                User.this.setDataProperty("skillCombatXp", User.this.combatXP);
                User.this.setDataProperty("skillForagingXp", User.this.foragingXP);
                User.this.setDataProperty("skillEnchantXp", User.this.enchantXP);
                User.this.setDataProperty("slayerRevenantHorrorHighest", User.this.highestSlayers[0]);
                User.this.setDataProperty("slayerTarantulaBroodfatherHighest", User.this.highestSlayers[1]);
                User.this.setDataProperty("slayerSvenPackmasterHighest", User.this.highestSlayers[2]);
                User.this.setDataProperty("slayerVoidgloomSeraphHighest", User.this.highestSlayers[3]);
                User.this.setDataProperty("permanentCoins", User.this.isPermanentCoins());
                User.this.setDataProperty("xpSlayerRevenantHorror", User.this.slayerXP[0]);
                User.this.setDataProperty("xpSlayerTarantulaBroodfather", User.this.slayerXP[1]);
                User.this.setDataProperty("xpSlayerSvenPackmaster", User.this.slayerXP[2]);
                User.this.setDataProperty("xpSlayerVoidgloomSeraph", User.this.slayerXP[3]);
                if (User.this.slayerQuest != null) {
                    User.this.setDataProperty("slayerQuest", User.this.getSlayerQuest().serialize());
                }
                if (!User.this.pets.isEmpty()) {
                    final List petsSerialized = (List)User.this.pets.stream().map(Pet.PetItem::serialize).collect(Collectors.toList());
                    User.this.setDataProperty("pets", petsSerialized);
                }
                else {
                    User.this.setDataProperty("pets", new ArrayList());
                }
                if (User.this.auctionSettings != null) {
                    User.this.setDataProperty("auctionSettings", User.this.auctionSettings.serialize());
                }
                User.this.setDataProperty("auctionCreationBIN", User.this.isAuctionCreationBIN());
                if (User.this.auctionEscrow != null) {
                    User.this.setDataProperty("auctionEscrow", User.this.auctionEscrow.serialize());
                }
                User.this.setDataProperty("unlockedRecipes", User.this.unlockedRecipes);
                User.this.setDataProperty("talkedNPCs", User.this.talkedNPCs);
                User.this.setDataProperty("foundZone", User.this.foundzone);
                if (Bukkit.getPlayer(User.this.uuid) != null && Bukkit.getPlayer(User.this.uuid).isOnline()) {
                    User.this.setDataProperty("showPets", PetsGUI.getShowPet(Bukkit.getPlayer(User.this.uuid)));
                    User.this.setDataProperty("autoSlayer", PlayerUtils.isAutoSlayer(Bukkit.getPlayer(User.this.uuid)));
                    if (PlayerUtils.COOKIE_DURATION_CACHE.containsKey((Object)User.this.uuid)) {
                        User.this.setDataProperty("cookieDuration", PlayerUtils.getCookieDurationTicks(Bukkit.getPlayer(User.this.uuid)));
                    }
                }
                final Document query = new Document("_id", (Object)User.this.uuid.toString());
                UserDatabase.collectionFuture.thenApply(userCollection -> {
                    final Document found = (Document)userCollection.find((Bson)query).first();
                    if (found != null) {
                        final Document updated = new Document((Map)found);
                        User.this.dataCache.forEach(updated::append);
                        userCollection.replaceOne((Bson)found, (Object)updated);
                    }
                    else {
                        final Document newDocument = new Document("_id", (Object)User.this.uuid.toString());
                        User.this.dataCache.forEach(newDocument::append);
                        userCollection.insertOne((Object)newDocument);
                    }
                    future.complete((Object)null);
                    return future;
                });
            }
        });
        return future;
    }
    
    public void loadPlayerData() throws IllegalArgumentException, IOException {
        final Player player = Bukkit.getPlayer(this.uuid);
        final MongoCollection<Document> collection = (MongoCollection<Document>)DatabaseManager.getCollection("users").join();
        final Document query;
        final Document document = (Document)collection.find((Bson)(query = new Document("_id", (Object)this.uuid.toString()))).first();
        final Document databaseDocument = (Document)(document.containsKey((Object)"data") ? document.get((Object)"data") : document);
        if (databaseDocument.containsKey((Object)"inventory")) {
            if (player != null) {
                player.getInventory().setContents(BukkitSerializeClass.itemStackArrayFromBase64(databaseDocument.getString((Object)"inventory")));
            }
        }
        else {
            player.getInventory().setContents(new ItemStack[player.getInventory().getSize()]);
        }
        if (databaseDocument.containsKey((Object)"enderchest")) {
            if (player != null) {
                player.getEnderChest().setContents(BukkitSerializeClass.itemStackArrayFromBase64(databaseDocument.getString((Object)"enderchest")));
            }
        }
        else {
            player.getInventory().setContents(new ItemStack[player.getEnderChest().getSize()]);
        }
        if (databaseDocument.containsKey((Object)"armor")) {
            if (player != null) {
                player.getInventory().setArmorContents(BukkitSerializeClass.itemStackArrayFromBase64(databaseDocument.getString((Object)"armor")));
            }
        }
        else {
            player.getInventory().setContents(new ItemStack[player.getInventory().getArmorContents().length]);
        }
        if (databaseDocument.containsKey((Object)"minecraft_xp") && player != null) {
            Sputnik.setTotalExperience(player, databaseDocument.getInteger((Object)"minecraft_xp"));
        }
        if (document.containsKey((Object)"stash")) {
            if (player != null) {
                ItemStack[] arr = new ItemStack[0];
                try {
                    arr = BukkitSerializeClass.itemStackArrayFromBase64(databaseDocument.getString((Object)"stash"));
                }
                catch (final IOException e) {
                    throw new RuntimeException((Throwable)e);
                }
                this.stashedItems = (List<ItemStack>)Arrays.asList((Object[])arr);
            }
        }
        else {
            this.stashedItems = (List<ItemStack>)new ArrayList();
        }
        if (document.containsKey((Object)"lastslot") && player != null) {
            player.getInventory().setHeldItemSlot((int)document.getInteger((Object)"lastslot"));
        }
    }
    
    public String getPureListFrom(final Inventory piv) {
        final ItemStack[] ist = piv.getContents();
        final List<ItemStack> arraylist = (List<ItemStack>)Arrays.asList((Object[])ist);
        for (int i = 0; i < ist.length; ++i) {
            final ItemStack stack = ist[i];
            if (stack != null) {
                final NBTItem nbti;
                if ((nbti = new NBTItem(stack)).hasKey("dontSaveToProfile")) {
                    arraylist.remove(i);
                }
            }
        }
        final ItemStack[] arrl = (ItemStack[])arraylist.toArray();
        return BukkitSerializeClass.itemStackArrayToBase64(arrl);
    }
    
    public void load() {
        SUtil.runAsync((Runnable)new Runnable() {
            public void run() {
                User.this.loadDocument();
                User.this.uuid = UUID.fromString(User.this.getString("uuid", null));
                User.this.coins = User.this.getLong("coins", 0);
                User.this.bits = User.this.getLong("bits", 0);
                User.this.bankCoins = User.this.getLong("bankCoins", 0);
                final Map coll = (Map)User.this.get("collections", new HashMap());
                coll.forEach((key, value) -> {
                    final Integer n = (Integer)User.this.collections.put((Object)ItemCollection.getByIdentifier((String)key), (Object)value);
                });
                User.this.setRank(PlayerRank.valueOf(User.this.getString("rank", "DEFAULT")));
                User.this.islandX = User.this.getDouble("islandX", 0);
                User.this.islandZ = User.this.getDouble("islandZ", 0);
                User.this.hasIsland = User.this.getBoolean("hasIsland", false);
                if (User.this.getString("lastRegion", "none").equals((Object)"none")) {
                    User.this.setLastRegion(null);
                }
                else {
                    User.this.setLastRegion(Region.get(User.this.getString("lastRegion", "none")));
                }
                User.this.totalfloor6run = User.this.getLong("totalfloor6run", 0);
                User.this.sadancollections = User.this.getLong("sadanCollections", 0);
                User.this.farmingXP = User.this.getDouble("skillFarmingXp", 0.0);
                User.this.miningXP = User.this.getDouble("skillMiningXp", 0.0);
                User.this.combatXP = User.this.getDouble("skillCombatXp", 0.0);
                User.this.foragingXP = User.this.getDouble("skillForagingXp", 0.0);
                User.this.enchantXP = User.this.getDouble("skillEnchantXp", 0.0);
                User.this.highestSlayers[0] = User.this.getInt("slayerRevenantHorrorHighest", 0);
                User.this.highestSlayers[1] = User.this.getInt("slayerTarantulaBroodfatherHighest", 0);
                User.this.highestSlayers[2] = User.this.getInt("slayerSvenPackmasterHighest", 0);
                User.this.highestSlayers[3] = User.this.getInt("slayerVoidgloomSeraphHighest", 0);
                User.this.slayerXP[0] = User.this.getInt("xpSlayerRevenantHorror", 0);
                User.this.slayerXP[1] = User.this.getInt("xpSlayerTarantulaBroodfather", 0);
                User.this.slayerXP[2] = User.this.getInt("xpSlayerSvenPackmaster", 0);
                User.this.slayerXP[3] = User.this.getInt("xpSlayerVoidgloomSeraph", 0);
                User.this.talkedNPCs = User.this.getStringList("talkedNPCs", (List<String>)new ArrayList());
                User.this.unlockedRecipes = User.this.getStringList("unlockedRecipes", (List<String>)new ArrayList());
                User.this.foundzone = User.this.getStringList("foundZone", (List<String>)new ArrayList());
                User.this.setPermanentCoins(User.this.getBoolean("permanentCoins", false));
                final Map quiv = (Map)User.this.get("quiver", new HashMap());
                quiv.forEach((key, value) -> {
                    final Integer n = (Integer)User.this.quiver.put((Object)SMaterial.getMaterial((String)key), (Object)value);
                });
                try {
                    final SlayerQuest quest = SlayerQuest.deserialize((Map<String, Object>)User.this.get("slayerQuest", new HashMap()));
                    User.this.setSlayerQuest(quest);
                }
                catch (final Exception ignored) {
                    User.this.setSlayerQuest(null);
                }
                final ArrayList listOfPetObjects = new ArrayList((Collection)User.this.get("pets", new ArrayList()));
                listOfPetObjects.forEach(item -> {
                    final Pet.PetItem petitem = Pet.PetItem.deserialize((Map<String, Object>)item);
                    User.this.pets.add((Object)petitem);
                });
                try {
                    User.this.auctionSettings = AuctionSettings.deserialize((Map<String, Object>)User.this.get("auctionSettings", new HashMap()));
                }
                catch (final Exception ignored2) {
                    User.this.auctionSettings = new AuctionSettings();
                }
                try {
                    User.this.setAuctionEscrow(AuctionEscrow.deserialize((Map<String, Object>)User.this.get("auctionEscrow", new HashMap())));
                }
                catch (final Exception ignored2) {
                    User.this.setAuctionEscrow(new AuctionEscrow());
                }
                final Document questData = (Document)User.this.get("quests", new Document());
                final List<String> completedQuests = (List<String>)questData.getList((Object)"completedQuests", (Class)String.class, (List)new ArrayList());
                final List<String> completedObjectives = (List<String>)questData.getList((Object)"completedObjectives", (Class)String.class, (List)new ArrayList());
                final List<String> talkedto = (List<String>)questData.getList((Object)"talkedto", (Class)String.class, (List)new ArrayList());
                User.this.setCompletedQuests(completedQuests);
                User.this.setCompletedObjectives(completedObjectives);
                User.this.talkedVillagers = talkedto;
                User.this.setAuctionCreationBIN(User.this.getBoolean("auctionCreationBIN", false));
                final List<Document> effectsDocuments = (List<Document>)User.this.dataDocument.getList((Object)"effects", (Class)Document.class);
                for (final Document effectData : effectsDocuments) {
                    final String key2 = effectData.getString((Object)"key");
                    final Integer level = effectData.getInteger((Object)"level");
                    final Long duration = effectData.getLong((Object)"duration");
                    final Long remaining = effectData.getLong((Object)"remaining");
                    if (key2 != null && level != null && duration != null && remaining != null) {
                        final PotionEffectType potionEffectType;
                        if ((potionEffectType = PotionEffectType.getByNamespace(key2)) == null) {
                            continue;
                        }
                        User.this.getEffects().add((Object)new ActivePotionEffect(new PotionEffect(potionEffectType, level, duration), remaining));
                    }
                }
                try {
                    PlayerUtils.setCookieDurationTicks(User.this.player, User.this.getLong("cookieDuration", 0));
                }
                catch (final NullPointerException ex) {}
                try {
                    User.this.loadPlayerData();
                }
                catch (final IOException e) {
                    throw new RuntimeException((Throwable)e);
                }
            }
        });
    }
    
    public void setDataProperty(final String key, final Object value) {
        this.dataCache.put((Object)key, value);
    }
    
    public List<String> getStringList(final String key, final List<String> def) {
        final Object value = this.get(key, def);
        if (value instanceof List) {
            return (List<String>)new ArrayList((Collection)value);
        }
        final ArrayList<String> list = (ArrayList<String>)new ArrayList();
        list.add((Object)value.toString());
        return (List<String>)list;
    }
    
    public Object get(final String key, final Object def) {
        if (this.dataDocument != null && this.dataDocument.get((Object)key) != null) {
            return this.dataDocument.get((Object)key);
        }
        return def;
    }
    
    public String getString(final String key, final Object def) {
        return this.get(key, def).toString();
    }
    
    public int getInt(final String key, final Object def) {
        return (int)this.get(key, def);
    }
    
    public boolean getBoolean(final String key, final Object def) {
        return Boolean.parseBoolean(this.get(key, def).toString());
    }
    
    public double getDouble(final String key, final Object def) {
        return Double.parseDouble(this.getString(key, def));
    }
    
    public long getLong(final String key, Object def) {
        if (def.equals(0.0)) {
            def = 0L;
        }
        return Long.parseLong(this.getString(key, def));
    }
    
    public void loadDocument() {
        final Document query = new Document("_id", (Object)this.uuid.toString());
        final MongoCollection<Document> collection = (MongoCollection<Document>)DatabaseManager.getCollection("users").join();
        Document found = (Document)collection.find((Bson)query).first();
        if (found == null) {
            this.save();
            found = (Document)collection.find((Bson)query).first();
        }
        this.dataDocument = found;
    }
    
    public void sendMessage(final Object message) {
        if (message instanceof String) {
            this.player.sendMessage(message.toString().replace('&', '§'));
        }
        else if (message instanceof TextComponent) {
            this.player.spigot().sendMessage(new BaseComponent[] { (BaseComponent)message });
        }
    }
    
    public void sendMessages(final Object... messages) {
        for (final Object message : messages) {
            this.sendMessage(message);
        }
    }
    
    public boolean addBits(final long value) {
        if (value > 0L) {
            this.bits += value;
            return true;
        }
        return false;
    }
    
    public boolean subBits(final long value) {
        if (this.bits > value && value > 0L) {
            this.bits -= value;
            return true;
        }
        return false;
    }
    
    public int getSkillLevel(final SkillType type) {
        final Skill skill = type.getSkill();
        final double xp = this.getSkillXP(skill);
        return Skill.getLevel(xp, skill.hasSixtyLevels());
    }
    
    public void addCoins(final long coins) {
        this.coins += coins;
    }
    
    public void subCoins(final long coins) {
        this.coins -= coins;
    }
    
    public void addBankCoins(final long bankCoins) {
        this.bankCoins += bankCoins;
    }
    
    public void subBankCoins(final long bankCoins) {
        this.bankCoins -= bankCoins;
    }
    
    public void addBCollection(final int a) {
        this.sadancollections += a;
    }
    
    public void setBCollection(final int a) {
        this.sadancollections = a;
    }
    
    public void subBCollection(final int a) {
        this.sadancollections -= a;
    }
    
    public long getBCollection() {
        return this.sadancollections;
    }
    
    public void addBRun6(final int a) {
        this.totalfloor6run += a;
    }
    
    public void subBRun6(final int a) {
        this.totalfloor6run -= a;
    }
    
    public long getBRun6() {
        return this.totalfloor6run;
    }
    
    public void addToCollection(final ItemCollection collection, final int amount) {
        final int prevTier = collection.getTier(this.getCollection(collection));
        final int i = (int)this.collections.getOrDefault((Object)collection, (Object)0);
        this.collections.put((Object)collection, (Object)(i + amount));
        this.updateCollection(collection, prevTier);
    }
    
    public void addToCollection(final ItemCollection collection) {
        this.addToCollection(collection, 1);
    }
    
    public void setCollection(final ItemCollection collection, final int amount) {
        final int prevTier = collection.getTier(this.getCollection(collection));
        this.collections.put((Object)collection, (Object)amount);
        this.updateCollection(collection, prevTier);
    }
    
    public void zeroCollection(final ItemCollection collection) {
        final int prevTier = collection.getTier(this.getCollection(collection));
        this.collections.put((Object)collection, (Object)0);
        this.updateCollection(collection, prevTier);
    }
    
    private void updateCollection(final ItemCollection collection, final int prevTier) {
        final int tier = collection.getTier(this.getCollection(collection));
        if (prevTier != tier) {
            final Player player = Bukkit.getPlayer(this.uuid);
            if (player != null) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.YELLOW).append((Object)ChatColor.BOLD).append("------------------------------------------\n");
            builder.append((Object)ChatColor.GOLD).append((Object)ChatColor.BOLD).append("  COLLECTION LEVEL UP ").append((Object)ChatColor.RESET).append((Object)ChatColor.YELLOW).append(collection.getName()).append(" ");
            if (prevTier != 0) {
                builder.append((Object)ChatColor.DARK_GRAY).append(SUtil.toRomanNumeral(prevTier)).append("\u279c");
            }
            builder.append((Object)ChatColor.YELLOW).append(SUtil.toRomanNumeral(tier)).append("\n");
            final ItemCollectionRewards rewards = collection.getRewardsFor(tier);
            if (rewards != null && rewards.size() != 0) {
                builder.append(" \n");
                builder.append((Object)ChatColor.GREEN).append((Object)ChatColor.BOLD).append("  REWARD");
                if (rewards.size() != 1) {
                    builder.append("S");
                }
                builder.append((Object)ChatColor.RESET);
                for (final ItemCollectionReward reward : rewards) {
                    reward.onAchieve(player);
                    builder.append("\n    ").append(reward.toRewardString());
                }
            }
            builder.append((Object)ChatColor.YELLOW).append((Object)ChatColor.BOLD).append("------------------------------------------");
            this.send(builder.toString());
        }
    }
    
    public int getCollection(final ItemCollection collection) {
        return (int)this.collections.get((Object)collection);
    }
    
    public boolean hasCollection(final ItemCollection collection, final int tier) {
        return collection.getTier(this.getCollection(collection)) >= tier;
    }
    
    public void addToQuiver(final SMaterial material, final int amount) {
        final int i = (int)this.quiver.getOrDefault((Object)material, (Object)0);
        this.setQuiver(material, i + amount);
    }
    
    public void addToQuiver(final SMaterial material) {
        this.addToQuiver(material, 1);
    }
    
    public void setQuiver(final SMaterial material, final int amount) {
        if (amount == 0) {
            this.quiver.remove((Object)material);
            return;
        }
        this.quiver.put((Object)material, (Object)amount);
    }
    
    public int getQuiver(final SMaterial material) {
        return (int)this.quiver.get((Object)material);
    }
    
    public void subFromQuiver(final SMaterial material, final int amount) {
        if (!this.quiver.containsKey((Object)material)) {
            return;
        }
        this.setQuiver(material, (int)this.quiver.get((Object)material) - amount);
    }
    
    public void addtoQuiver(final SMaterial material, final int amount) {
        if (!this.quiver.containsKey((Object)material)) {
            return;
        }
        this.setQuiver(material, (int)this.quiver.get((Object)material) + amount);
    }
    
    public void subFromQuiver(final SMaterial material) {
        this.subFromQuiver(material, 1);
    }
    
    public void addtoQuiver(final SMaterial material) {
        this.addtoQuiver(material, 1);
    }
    
    public boolean hasQuiverItem(final SMaterial material) {
        return this.quiver.containsKey((Object)material);
    }
    
    public void clearQuiver() {
        this.quiver.clear();
    }
    
    public void addPet(final SItem item) {
        this.pets.add((Object)new Pet.PetItem(item.getType(), item.getRarity(), item.getData().getDouble("xp")));
    }
    
    public void equipPet(final Pet.PetItem pet) {
        for (final Pet.PetItem p : this.pets) {
            if (!p.isActive()) {
                continue;
            }
            p.setActive(false);
            break;
        }
        pet.setActive(true);
    }
    
    public void removePet(final Pet.PetItem pet) {
        final Iterator<Pet.PetItem> iter = (Iterator<Pet.PetItem>)this.pets.iterator();
        while (iter.hasNext()) {
            final Pet.PetItem p = (Pet.PetItem)iter.next();
            if (!pet.equals(p)) {
                continue;
            }
            iter.remove();
            break;
        }
    }
    
    public Pet.PetItem getActivePet() {
        for (final Pet.PetItem pet : this.pets) {
            if (!pet.isActive()) {
                continue;
            }
            return pet;
        }
        return null;
    }
    
    public Pet getActivePetClass() {
        final Pet.PetItem item = this.getActivePet();
        if (item == null) {
            return null;
        }
        return (Pet)item.getType().getGenericInstance();
    }
    
    public double getSkillXP(final Skill skill) {
        if (skill instanceof FarmingSkill) {
            return this.farmingXP;
        }
        if (skill instanceof MiningSkill) {
            return this.miningXP;
        }
        if (skill instanceof CombatSkill) {
            return this.combatXP;
        }
        if (skill instanceof ForagingSkill) {
            return this.foragingXP;
        }
        if (skill instanceof EnchantingSkill) {
            return this.enchantXP;
        }
        if (skill instanceof CatacombsSkill) {
            return this.cataXP;
        }
        if (skill instanceof ArcherSkill) {
            return this.archerXP;
        }
        if (skill instanceof TankSkill) {
            return this.tankXP;
        }
        if (skill instanceof HealerSkill) {
            return this.healerXP;
        }
        if (skill instanceof MageSkill) {
            return this.mageXP;
        }
        if (skill instanceof BerserkSkill) {
            return this.berserkXP;
        }
        return 0.0;
    }
    
    public void setSkillXP(final Skill skill, final double xp) {
        double prev = 0.0;
        if (skill instanceof FarmingSkill) {
            prev = this.farmingXP;
            this.farmingXP = xp;
        }
        if (skill instanceof MiningSkill) {
            prev = this.miningXP;
            this.miningXP = xp;
        }
        if (skill instanceof CombatSkill) {
            prev = this.combatXP;
            this.combatXP = xp;
        }
        if (skill instanceof ForagingSkill) {
            prev = this.foragingXP;
            this.foragingXP = xp;
        }
        if (skill instanceof EnchantingSkill) {
            prev = this.enchantXP;
            this.enchantXP = xp;
        }
        if (skill instanceof CatacombsSkill) {
            prev = this.cataXP;
            this.cataXP = xp;
        }
        if (skill instanceof TankSkill) {
            prev = this.tankXP;
            this.tankXP = xp;
        }
        if (skill instanceof ArcherSkill) {
            prev = this.archerXP;
            this.archerXP = xp;
        }
        if (skill instanceof BerserkSkill) {
            prev = this.berserkXP;
            this.berserkXP = xp;
        }
        if (skill instanceof MageSkill) {
            prev = this.mageXP;
            this.mageXP = xp;
        }
        if (skill instanceof HealerSkill) {
            prev = this.healerXP;
            this.healerXP = xp;
        }
        skill.onSkillUpdate(this, prev);
    }
    
    public void addSkillXP(final Skill skill, final double xp) {
        this.setSkillXP(skill, this.getSkillXP(skill) + xp);
    }
    
    public int getHighestRevenantHorror() {
        return this.highestSlayers[0];
    }
    
    public void setHighestRevenantHorror(final int tier) {
        this.highestSlayers[0] = tier;
    }
    
    public int getHighestTarantulaBroodfather() {
        return this.highestSlayers[1];
    }
    
    public void setHighestTarantulaBroodfather(final int tier) {
        this.highestSlayers[1] = tier;
    }
    
    public int getHighestSvenPackmaster() {
        return this.highestSlayers[2];
    }
    
    public void setHighestSvenPackmaster(final int tier) {
        this.highestSlayers[2] = tier;
    }
    
    public int getHighestVoidgloomSeraph() {
        return this.highestSlayers[3];
    }
    
    public void setHighestVoidgloomSeraph(final int tier) {
        this.highestSlayers[3] = tier;
    }
    
    public int getZombieSlayerXP() {
        return this.slayerXP[0];
    }
    
    public void setZombieSlayerXP(final int xp) {
        this.slayerXP[0] = xp;
    }
    
    public int getSpiderSlayerXP() {
        return this.slayerXP[1];
    }
    
    public void setSpiderSlayerXP(final int xp) {
        this.slayerXP[1] = xp;
    }
    
    public int getWolfSlayerXP() {
        return this.slayerXP[2];
    }
    
    public void setWolfSlayerXP(final int xp) {
        this.slayerXP[2] = xp;
    }
    
    public int getEndermanSlayerXP() {
        return this.slayerXP[3];
    }
    
    public void setEndermanSlayerXP(final int xp) {
        this.slayerXP[3] = xp;
    }
    
    public void setSlayerXP(final SlayerBossType.SlayerMobType type, final int xp) {
        this.slayerXP[type.ordinal()] = xp;
    }
    
    public int getSlayerXP(final SlayerBossType.SlayerMobType type) {
        return this.slayerXP[type.ordinal()];
    }
    
    public int getCrystalLVL(final int i) {
        if (i > 7) {
            SLog.severe("Out of bound on action taking data from database!");
            return 0;
        }
        return this.crystalLVL[i];
    }
    
    public void setCrystalLVL(final int i, final int a) {
        if (i > 7) {
            SLog.severe("Out of bound on action taking data from database!");
            return;
        }
        this.crystalLVL[i] = a;
    }
    
    public int getSlayerCombatXPBuff() {
        int buff = 0;
        for (final int highest : this.highestSlayers) {
            buff += ((highest == 4) ? 5 : highest);
        }
        return buff;
    }
    
    public void startSlayerQuest(final SlayerBossType type) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        this.slayerQuest = new SlayerQuest(type, System.currentTimeMillis());
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 2.0f);
        player.sendMessage("  " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "SLAYER QUEST STARTED!");
        player.sendMessage("   " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "» " + (Object)ChatColor.GRAY + "Slay " + (Object)ChatColor.RED + SUtil.commaify(type.getSpawnXP()) + " Combat XP" + (Object)ChatColor.GRAY + " worth of " + type.getType().getPluralName() + ".");
    }
    
    public void failSlayerQuest() {
        if (this.slayerQuest == null) {
            return;
        }
        if (this.slayerQuest.getDied() != 0L) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        this.slayerQuest.setDied(System.currentTimeMillis());
        if (this.slayerQuest.getEntity() != null) {
            this.slayerQuest.getEntity().remove();
            this.slayerQuest.getEntity().getFunction().onDeath(this.slayerQuest.getEntity(), (Entity)this.slayerQuest.getEntity().getEntity(), (Entity)player);
        }
        SUtil.delay(() -> {
            this.removeAllSlayerBosses();
            player.sendMessage("  " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "SLAYER QUEST FAILED!");
            player.sendMessage("   " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "» " + (Object)ChatColor.GRAY + "You need to learn how to play this game first!");
        }, 2L);
    }
    
    public void removeAllSlayerBosses() {
        for (final Entity e : Bukkit.getPlayer(this.uuid).getWorld().getEntities()) {
            if (e.hasMetadata("BOSS_OWNER_" + this.uuid.toString())) {
                if (!e.hasMetadata("SlayerBoss")) {
                    continue;
                }
                e.remove();
            }
        }
    }
    
    public void send(final String message) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        player.sendMessage(Sputnik.trans(message));
    }
    
    public void damageEntity(final Damageable entity1, final double damageBase) {
        if (entity1.isDead()) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.uuid);
        double damage = damageBase;
        if (VoidgloomSeraph.HIT_SHIELD.containsKey((Object)entity1)) {
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity1, (Object)((int)VoidgloomSeraph.HIT_SHIELD.get((Object)entity1) - 1));
            entity1.getWorld().playSound(entity1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        }
        if (entity1.getType() != EntityType.ENDER_DRAGON) {
            if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity1)) {
                int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity1);
                if (defensepercent > 100) {
                    defensepercent = 100;
                }
                damage -= damage * defensepercent / 100.0;
            }
            PlayerUtils.handleSpecEntity((Entity)entity1, player, new AtomicDouble(damage));
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        else {
            final double formula = damage / entity1.getMaxHealth() * 100.0;
            damage = ((formula < 10.0) ? (damage *= 1.0) : ((formula > 10.0 && formula < 15.0) ? (damage -= damage * 90.0 / 100.0) : ((formula > 15.0 && formula < 20.0) ? (damage -= damage * 99.0 / 100.0) : ((formula > 20.0 && formula <= 25.0) ? (damage -= damage * 99.9 / 100.0) : ((formula > 25.0) ? (damage *= 0.0) : (damage *= 1.0))))));
            PlayerUtils.handleSpecEntity((Entity)entity1, player, new AtomicDouble(damage));
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        final double health = entity1.getMaxHealth();
        if (player == null) {
            return;
        }
        entity1.damage(1.0E-5);
        final SItem sitem;
        if (player.getItemInHand() != null && (sitem = SItem.find(player.getItemInHand())) != null && sitem.getEnchantment(EnchantmentType.VAMPIRISM) != null) {
            double lvl = sitem.getEnchantment(EnchantmentType.VAMPIRISM).getLevel();
            if (lvl > 100.0) {
                lvl = 100.0;
            }
            final double aB = player.getHealth() + lvl / 100.0 * (player.getMaxHealth() - player.getHealth());
            final double aC = Math.min(player.getMaxHealth(), aB);
            player.setHealth(aC);
        }
    }
    
    public void damageEntityIgnoreShield(final Damageable entity1, final double damageBase) {
        if (entity1.isDead()) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.uuid);
        double damage = damageBase;
        if (VoidgloomSeraph.HIT_SHIELD.containsKey((Object)entity1)) {
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity1, (Object)((int)VoidgloomSeraph.HIT_SHIELD.get((Object)entity1) - 1));
            entity1.getWorld().playSound(entity1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        }
        if (entity1.getType() != EntityType.ENDER_DRAGON) {
            PlayerUtils.handleSpecEntity((Entity)entity1, player, new AtomicDouble(damage));
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        else {
            final double formula = damage / entity1.getMaxHealth() * 100.0;
            damage = ((formula < 10.0) ? (damage *= 1.0) : ((formula > 10.0 && formula < 15.0) ? (damage -= damage * 90.0 / 100.0) : ((formula > 15.0 && formula < 20.0) ? (damage -= damage * 99.0 / 100.0) : ((formula > 20.0 && formula <= 25.0) ? (damage -= damage * 99.9 / 100.0) : ((formula > 25.0) ? (damage *= 0.0) : (damage *= 1.0))))));
            PlayerUtils.handleSpecEntity((Entity)entity1, player, new AtomicDouble(damage));
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        final double health = entity1.getMaxHealth();
        if (player == null) {
            return;
        }
        entity1.damage(1.0E-5);
        final SItem sitem;
        if (player.getItemInHand() != null && (sitem = SItem.find(player.getItemInHand())) != null && sitem.getEnchantment(EnchantmentType.VAMPIRISM) != null) {
            double lvl = sitem.getEnchantment(EnchantmentType.VAMPIRISM).getLevel();
            if (lvl > 100.0) {
                lvl = 100.0;
            }
            final double aB = player.getHealth() + lvl / 100.0 * (player.getMaxHealth() - player.getHealth());
            final double aC = Math.min(player.getMaxHealth(), aB);
            player.setHealth(aC);
        }
    }
    
    public static File getDataDirectory() {
        return User.USER_FOLDER;
    }
    
    public void damageEntityBowEman(final Damageable entity1, double damage, final Player player, final Arrow a) {
        if (VoidgloomSeraph.HIT_SHIELD.containsKey((Object)entity1)) {
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity1, (Object)((int)VoidgloomSeraph.HIT_SHIELD.get((Object)entity1) - 1));
            entity1.getWorld().playSound(entity1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        }
        final double health = entity1.getMaxHealth();
        if (player == null) {
            return;
        }
        if (entity1.getType() != EntityType.ENDER_DRAGON) {
            if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity1)) {
                int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity1);
                if (defensepercent > 100) {
                    defensepercent = 100;
                }
                damage -= damage * defensepercent / 100.0;
            }
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        else {
            final double formula = damage / entity1.getMaxHealth() * 100.0;
            damage = ((formula < 10.0) ? (damage *= 1.0) : ((formula > 10.0 && formula < 15.0) ? (damage -= damage * 90.0 / 100.0) : ((formula > 15.0 && formula < 20.0) ? (damage -= damage * 99.0 / 100.0) : ((formula > 20.0 && formula <= 25.0) ? (damage -= damage * 99.9 / 100.0) : ((formula > 25.0) ? (damage *= 0.0) : (damage *= 1.0))))));
            entity1.setHealth(Math.max(0.0, entity1.getHealth() - damage));
        }
        PlayerUtils.handleSpecEntity((Entity)entity1, player, new AtomicDouble(damage));
        a.remove();
    }
    
    public void damageEntity(final LivingEntity entity) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        entity.damage(0.0, (Entity)player);
    }
    
    public void damage(double d, final EntityDamageEvent.DamageCause cause, final Entity entity) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        final EntityHuman human = ((CraftHumanEntity)player).getHandle();
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final double trueDefense = statistics.getTrueDefense().addAll();
        final double health = statistics.getMaxHealth().addAll();
        d -= d * (trueDefense / (trueDefense + 100.0));
        if (player.getHealth() + SputnikPlayer.getCustomAbsorptionHP(player) - d <= 0.0) {
            this.kill(cause, entity);
            return;
        }
        final float ab = (float)Math.max(0.0, SputnikPlayer.getCustomAbsorptionHP(player) - d);
        double actual = Math.max(0.0, d - SputnikPlayer.getCustomAbsorptionHP(player));
        SputnikPlayer.setCustomAbsorptionHP(player, ab);
        if (cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA) {
            final boolean damage = false;
            if (cause == EntityDamageEvent.DamageCause.FIRE || (cause == EntityDamageEvent.DamageCause.FIRE_TICK && cause != EntityDamageEvent.DamageCause.LAVA)) {
                actual = 5.0;
            }
            else if (cause == EntityDamageEvent.DamageCause.LAVA) {
                actual = 20.0;
            }
        }
        if (cause == EntityDamageEvent.DamageCause.DROWNING) {
            actual = health * 5.0 / 100.0;
        }
        if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
            actual = health * 5.0 / 100.0;
        }
        if (cause == EntityDamageEvent.DamageCause.CONTACT) {
            actual = 5.0;
        }
        if (cause == EntityDamageEvent.DamageCause.WITHER || cause == EntityDamageEvent.DamageCause.POISON) {
            actual = 20.0;
        }
        player.setHealth(Math.max(0.0, player.getHealth() - actual));
        if (cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.GOLD);
        }
        else if (cause == EntityDamageEvent.DamageCause.FALL || cause == EntityDamageEvent.DamageCause.CONTACT || cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.GRAY);
        }
        else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.DARK_AQUA);
        }
        else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.RED);
        }
        else if (cause == EntityDamageEvent.DamageCause.POISON) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.DARK_GREEN);
        }
        else if (cause == EntityDamageEvent.DamageCause.WITHER) {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.BLACK);
        }
        else {
            PlayerListener.spawnSpecialDamageInd((Entity)player, actual, ChatColor.WHITE);
        }
        if (player.getHealth() <= 0.0) {
            player.setFireTicks(0);
            this.kill(cause, entity);
        }
    }
    
    public void damage(final double d) {
        this.damage(d, EntityDamageEvent.DamageCause.CUSTOM, null);
    }
    
    public void kill(final EntityDamageEvent.DamageCause cause, final Entity entity) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        player.setHealth(player.getMaxHealth());
        for (int i = 0; i < player.getInventory().getSize(); ++i) {
            final ItemStack stack = player.getInventory().getItem(i);
            final SItem sItem = SItem.find(stack);
            if (sItem != null) {}
        }
        player.setFireTicks(0);
        player.setVelocity(new Vector(0, 0, 0));
        player.setFallDistance(0.0f);
        SputnikPlayer.AbsHP.put((Object)player.getUniqueId(), (Object)0);
        if (!player.getWorld().getName().contains((CharSequence)"f6")) {
            this.sendToSpawn();
            if (!PlayerUtils.cookieBuffActive(player)) {
                this.clearPotionEffects();
            }
        }
        else {
            final World w = player.getWorld();
            for (final Entity en : player.getWorld().getEntities()) {
                if (en instanceof HumanEntity) {
                    continue;
                }
                en.remove();
            }
            SadanFunction.sendReMsg(false, w);
            SadanHuman.IsMusicPlaying.put((Object)w.getUID(), (Object)false);
            SadanHuman.BossRun.put((Object)w.getUID(), (Object)false);
            SadanFunction.endRoom2(w);
            final BukkitTask bkt = SadanHuman.playHBS(w);
            new BukkitRunnable() {
                public void run() {
                    if (w == null || w.getPlayers().size() == 0) {
                        bkt.cancel();
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
            SUtil.sendTitle(player, (Object)ChatColor.YELLOW + "You became a ghost!");
            SUtil.sendSubtitle(player, (Object)ChatColor.GRAY + "Hopefully your teammates can revive you.");
            if (cause == EntityDamageEvent.DamageCause.VOID) {
                this.sendToSpawn();
            }
        }
        String name = null;
        if (entity != null) {
            final SEntity sEntity = SEntity.findSEntity(entity);
            name = ((sEntity != null) ? sEntity.getStatistics().getEntityName() : entity.getCustomName());
        }
        String message = "You died";
        String out = "%s died";
        switch (cause) {
            case VOID: {
                message = "You fell into the void";
                out = "%s fell into the void";
                break;
            }
            case FALL: {
                message = "You fell to your death";
                out = "%s fell to their death";
                break;
            }
            case ENTITY_ATTACK: {
                message = "You were killed by " + name + (Object)ChatColor.GRAY;
                out = "%s was killed by " + name + (Object)ChatColor.GRAY;
                break;
            }
            case ENTITY_EXPLOSION: {
                message = "You were killed by " + name + (Object)ChatColor.GRAY + "'s explosion";
                out = "%s was killed by " + name + (Object)ChatColor.GRAY + "'s explosion";
                break;
            }
            case FIRE:
            case FIRE_TICK:
            case LAVA: {
                message = "You burned to death";
                out = "%s burned to death";
                break;
            }
            case MAGIC: {
                message = "You died by magic";
                out = "%s was killed by magic";
                break;
            }
            case POISON: {
                message = "You died by poisoning";
                out = "%s was killed by poisoning";
                break;
            }
            case LIGHTNING: {
                message = "You were struck by lightning and died";
                out = "%s was struck by lightning and killed";
                break;
            }
            case DROWNING: {
                message = "You drowned";
                out = "%s drowned";
                break;
            }
            case SUFFOCATION: {
                message = "You suffocated";
                out = "%s suffocated";
                break;
            }
        }
        final Object user;
        final SlayerQuest quest;
        if (this.slayerQuest != null && this.slayerQuest.getKilled() == 0L && (quest = ((User)(user = getUser(player.getUniqueId()))).getSlayerQuest()).getXp() >= quest.getType().getSpawnXP()) {
            this.failSlayerQuest();
        }
        player.playSound(player.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
        if (!player.getWorld().getName().equalsIgnoreCase("limbo") && !player.getWorld().getName().contains((CharSequence)"f6")) {
            player.sendMessage((Object)ChatColor.RED + " \u2620 " + (Object)ChatColor.GRAY + message + (Object)ChatColor.GRAY + ".");
            SUtil.broadcastExcept((Object)ChatColor.RED + " \u2620 " + (Object)ChatColor.GRAY + String.format(out, new Object[] { player.getName() }) + (Object)ChatColor.GRAY + ".", player);
        }
        if (player.getWorld().getName().contains((CharSequence)"f6")) {
            player.playSound(player.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
            player.sendMessage((Object)ChatColor.RED + " \u2620 " + (Object)ChatColor.GRAY + message + (Object)ChatColor.GRAY + " and became a ghost.");
            SUtil.broadcastExcept((Object)ChatColor.RED + " \u2620 " + (Object)ChatColor.GRAY + String.format(out, new Object[] { player.getName() }) + (Object)ChatColor.GRAY + " and became a ghost.", player);
        }
        for (final Entity e : player.getWorld().getEntities()) {
            if (e.hasMetadata("owner")) {
                if (!((MetadataValue)e.getMetadata("owner").get(0)).asString().equals((Object)player.getUniqueId().toString())) {
                    continue;
                }
                e.remove();
                player.sendMessage((Object)ChatColor.RED + "\u2620 Your Voidling's Warden Boss has been despawned since you died!");
                StaticWardenManager.endFight();
            }
        }
        if (PlayerUtils.cookieBuffActive(player)) {
            player.sendMessage((Object)ChatColor.RED + "You died!");
            return;
        }
        if ((this.isOnIsland() && cause == EntityDamageEvent.DamageCause.VOID) || this.permanentCoins || player.getWorld().getName().equalsIgnoreCase("limbo") || player.getWorld().getName().contains((CharSequence)"f6")) {
            return;
        }
        final int piggyIndex = PlayerUtils.getSpecItemIndex(player, SMaterial.PIGGY_BANK);
        if (piggyIndex != -1 && this.coins >= 20000L) {
            final SItem cracked = SItem.of(SMaterial.CRACKED_PIGGY_BANK);
            final SItem piggy = SItem.find(player.getInventory().getItem(piggyIndex));
            if (piggy.getReforge() != null) {
                cracked.setReforge(piggy.getReforge());
            }
            player.getInventory().setItem(piggyIndex, cracked.getStack());
            player.sendMessage((Object)ChatColor.RED + "You died and your piggy bank cracked!");
            return;
        }
        player.playSound(player.getLocation(), Sound.ZOMBIE_METAL, 1.0f, 2.0f);
        final int crackedPiggyIndex = PlayerUtils.getSpecItemIndex(player, SMaterial.CRACKED_PIGGY_BANK);
        if (crackedPiggyIndex != -1 && this.coins >= 20000L) {
            final SItem broken = SItem.of(SMaterial.BROKEN_PIGGY_BANK);
            final SItem crackedPiggy = SItem.find(player.getInventory().getItem(crackedPiggyIndex));
            if (crackedPiggy.getReforge() != null) {
                broken.setReforge(crackedPiggy.getReforge());
            }
            player.getInventory().setItem(crackedPiggyIndex, broken.getStack());
            final long sub = (long)(this.coins * 0.25);
            player.sendMessage((Object)ChatColor.RED + "You died, lost " + SUtil.commaify(sub) + " coins, and your piggy bank broke!");
            this.coins -= sub;
            if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                this.configsave();
            }
            else {
                this.save();
            }
            return;
        }
        final long sub2 = this.coins / 2L;
        player.sendMessage((Object)ChatColor.RED + "You died and lost " + SUtil.commaify(sub2) + " coins!");
        this.coins -= sub2;
        this.save();
    }
    
    public void setIslandLocation(final double x, final double z) {
        this.islandX = x;
        this.islandZ = z;
    }
    
    public void addPotionEffect(final PotionEffect effect) {
        this.effects.add((Object)new ActivePotionEffect(effect, effect.getDuration()));
    }
    
    public void removePotionEffect(final PotionEffectType type) {
        for (final ActivePotionEffect effect : this.effects) {
            if (effect.getEffect().getType() != type) {
                continue;
            }
            effect.setRemaining(0L);
        }
    }
    
    public ActivePotionEffect getPotionEffect(final PotionEffectType type) {
        for (final ActivePotionEffect effect : this.effects) {
            if (effect.getEffect().getType() != type) {
                continue;
            }
            return effect;
        }
        return null;
    }
    
    public boolean hasPotionEffect(final PotionEffectType type) {
        return this.effects.stream().filter(effect -> effect.getEffect().getType() == type).toArray().length != 0;
    }
    
    public void clearPotionEffects() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player != null) {
            for (final org.bukkit.potion.PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        for (final ActivePotionEffect effect2 : this.effects) {
            effect2.setRemaining(0L);
        }
    }
    
    public boolean isOnIsland() {
        final Player player = Bukkit.getPlayer(this.uuid);
        return player != null && this.isOnIsland(player.getLocation());
    }
    
    public boolean isOnIsland(final Block block) {
        return this.isOnIsland(block.getLocation());
    }
    
    public boolean isOnIsland(final Location location) {
        final World world = Bukkit.getWorld("islands");
        if (world == null) {
            return false;
        }
        final double x = location.getX();
        final double z = location.getZ();
        return world.getUID().equals((Object)location.getWorld().getUID()) && x >= this.islandX - 125.0 && x <= this.islandX + 125.0 && z >= this.islandZ - 125.0 && z <= this.islandZ + 125.0;
    }
    
    public boolean isOnUserIsland() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return false;
        }
        final World world = Bukkit.getWorld("islands");
        if (world == null) {
            return false;
        }
        final double x = player.getLocation().getX();
        final double z = player.getLocation().getZ();
        return world.getUID().equals((Object)player.getWorld().getUID()) && x < this.islandX - 125.0 && x > this.islandX + 125.0 && z < this.islandZ - 125.0 && z > this.islandZ + 125.0;
    }
    
    public List<AuctionItem> getBids() {
        return (List<AuctionItem>)AuctionItem.getAuctions().stream().filter(item -> {
            for (final AuctionBid bid : item.getBids()) {
                if (bid.getBidder().equals((Object)this.uuid)) {
                    if (!item.getParticipants().contains((Object)this.uuid)) {
                        continue;
                    }
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
    
    public List<AuctionItem> getAuctions() {
        return (List<AuctionItem>)AuctionItem.getAuctions().stream().filter(item -> item.getOwner().getUuid().equals((Object)this.uuid) && item.getParticipants().contains((Object)this.uuid)).collect(Collectors.toList());
    }
    
    public Player toBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
    
    public EntityPlayer toNMSPlayer() {
        return ((CraftPlayer)Bukkit.getPlayer(this.uuid)).getHandle();
    }
    
    public void sendToSpawn() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        if (this.isOnIsland()) {
            PlayerUtils.sendToIsland(player);
        }
        else if (this.lastRegion != null) {
            switch (this.lastRegion.getType()) {
                case BANK: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case FARM:
                case RUINS: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case FOREST:
                case LIBRARY:
                case COAL_MINE:
                case COAL_MINE_CAVES:
                case MOUNTAIN:
                case VILLAGE: {
                    final Location l = new Location(Bukkit.getWorld("world"), -2.5, 70.0, -68.5, 180.0f, 0.0f);
                    player.teleport(l);
                }
                case HIGH_LEVEL: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case BLACKSMITH: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case AUCTION_HOUSE: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case WILDERNESS:
                case BAZAAR_ALLEY:
                case COLOSSEUM:
                case GRAVEYARD: {
                    player.teleport(player.getWorld().getSpawnLocation());
                    break;
                }
                case SPIDERS_DEN: {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                case SPIDERS_DEN_HIVE: {
                    player.teleport(player.getWorld().getSpawnLocation());
                    break;
                }
                default: {
                    player.teleport(player.getWorld().getSpawnLocation());
                    break;
                }
            }
        }
        else {
            final Location l = new Location(Bukkit.getWorld("world"), -2.5, 70.0, -68.5, 180.0f, 0.0f);
            player.teleport(l);
        }
    }
    
    public static String generateRandom() {
        final int leftLimit = 97;
        final int rightLimit = 122;
        final int targetStringLength = SUtil.random(7, 7);
        final Random random = new Random();
        final String generatedString = ((StringBuilder)random.ints(97, 123).limit((long)targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)).toString();
        return generatedString;
    }
    
    public static User getUser(final UUID uuid) {
        if (uuid == null) {
            return null;
        }
        if (User.USER_CACHE.containsKey((Object)uuid)) {
            return (User)User.USER_CACHE.get((Object)uuid);
        }
        return new User(uuid);
    }
    
    public static User getUser(final Player player) {
        if (player == null) {
            return null;
        }
        return getUser(player.getUniqueId());
    }
    
    public void sendPacket(final Packet<?> packet) {
        this.toNMSPlayer().playerConnection.sendPacket((Packet)packet);
    }
    
    public static Collection<User> getCachedUsers() {
        return (Collection<User>)User.USER_CACHE.values();
    }
    
    public boolean hasPermission(final String permission) {
        return true;
    }
    
    public static Map<UUID, User> getHash() {
        return User.USER_CACHE;
    }
    
    public ItemStack updateItemBoost(final SItem sitem) {
        if (sitem.getDataBoolean("dungeons_item") && sitem.getType().getStatistics().getType() != GenericItemType.ITEM && sitem.getType().getStatistics().getType() != GenericItemType.PET && sitem.getType().getStatistics().getType() != GenericItemType.BLOCK && sitem.getType().getStatistics().getType() != GenericItemType.ACCESSORY) {
            final int itemstar = sitem.getDataInt("itemStar");
            double hpbboostweapons = 0.0;
            double hpbboosthp = 0.0;
            double hpbboostdef = 0.0;
            final PlayerBoostStatistics hs = sitem.getType().getBoostStatistics();
            final ItemSerial is = ItemSerial.getItemBoostStatistics(sitem);
            final Reforge reforge = (sitem.getReforge() == null) ? Reforge.blank() : sitem.getReforge();
            double bonusEn = 0.0;
            if (sitem.getType().getStatistics().getType() == GenericItemType.WEAPON && sitem.getEnchantment(EnchantmentType.ONE_FOR_ALL) != null) {
                final Enchantment e = sitem.getEnchantment(EnchantmentType.ONE_FOR_ALL);
                bonusEn = hs.getBaseDamage() * (e.getLevel() * 210) / 100;
            }
            if (sitem.getType().getStatistics().getType() == GenericItemType.WEAPON || sitem.getType().getStatistics().getType() == GenericItemType.RANGED_WEAPON) {
                hpbboostweapons = sitem.getDataInt("hpb") * 2;
            }
            else if (sitem.getType().getStatistics().getType() == GenericItemType.ARMOR) {
                hpbboosthp = sitem.getDataInt("hpb") * 4;
                hpbboostdef = sitem.getDataInt("hpb") * 2;
            }
            is.setDamage(this.getFinal(hs.getBaseDamage() + hpbboostweapons + bonusEn, itemstar));
            is.setStrength(this.getFinal(hs.getBaseStrength() + (hpbboostweapons + reforge.getStrength().getForRarity(sitem.getRarity())), itemstar));
            is.setCritchance(this.getFinal(hs.getBaseCritChance() + reforge.getCritChance().getForRarity(sitem.getRarity()), itemstar));
            is.setCritdamage(this.getFinal(hs.getBaseCritDamage() + reforge.getCritDamage().getForRarity(sitem.getRarity()), itemstar));
            is.setIntelligence(this.getFinal(hs.getBaseIntelligence() + reforge.getIntelligence().getForRarity(sitem.getRarity()), itemstar));
            is.setFerocity(this.getFinal(hs.getBaseFerocity() + reforge.getFerocity().getForRarity(sitem.getRarity()), itemstar));
            is.setSpeed(this.getFinal(hs.getBaseSpeed(), itemstar));
            is.setAtkSpeed(this.getFinal(hs.getBaseAttackSpeed() + reforge.getAttackSpeed().getForRarity(sitem.getRarity()), itemstar));
            is.setMagicFind(this.getFinal(hs.getBaseMagicFind(), itemstar));
            double health = hs.getBaseHealth();
            double defense = hs.getBaseDefense();
            if (sitem.isEnchantable()) {
                for (final Enchantment enchantment : sitem.getEnchantments()) {
                    if (enchantment.getType() == EnchantmentType.GROWTH) {
                        health += 15.0 * enchantment.getLevel();
                    }
                    if (enchantment.getType() != EnchantmentType.PROTECTION) {
                        continue;
                    }
                    defense += 3.0 * enchantment.getLevel();
                }
            }
            is.setHealth(this.getFinal(health + hpbboosthp, itemstar));
            is.setDefense(this.getFinal(defense + hpbboostdef, itemstar));
            is.saveTo(sitem);
            return sitem.getStack();
        }
        return sitem.getStack();
    }
    
    public double getFinal(final double stat, final int starNum) {
        final int cataLVL = Skill.getLevel(this.getCataXP(), false);
        final int cataBuffPercentage = cataLVL * 5;
        int percentMstars = (starNum - 5) * 5;
        if (starNum <= 5) {
            percentMstars *= 0;
        }
        final double d = 1.0 + percentMstars / 100.0;
        return stat * ((1 + percentMstars / 100) * (1.0 + 0.1 * Math.min(5, starNum)) * (1 + cataBuffPercentage / 100) * d);
    }
    
    public void sendClickableMessage(final String message, final TextComponent[] hover, final String commandToRun) {
        final TextComponent tcp = new TextComponent(Sputnik.trans(message));
        if (hover != null) {
            tcp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (BaseComponent[])hover));
        }
        if (commandToRun != null) {
            tcp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandToRun));
        }
        this.toBukkitPlayer().spigot().sendMessage((BaseComponent)tcp);
    }
    
    public boolean isCompletedSign() {
        return this.isCompletedSign;
    }
    
    public void setCompletedSign(final boolean isCompletedSign) {
        this.isCompletedSign = isCompletedSign;
    }
    
    public long getSadancollections() {
        return this.sadancollections;
    }
    
    public long getTotalfloor6run() {
        return this.totalfloor6run;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public Map<ItemCollection, Integer> getCollections() {
        return this.collections;
    }
    
    public void setCoins(final long coins) {
        this.coins = coins;
    }
    
    public long getCoins() {
        return this.coins;
    }
    
    public long getBits() {
        return this.bits;
    }
    
    public void setBits(final long bits) {
        this.bits = bits;
    }
    
    public void setBankCoins(final long bankCoins) {
        this.bankCoins = bankCoins;
    }
    
    public long getBankCoins() {
        return this.bankCoins;
    }
    
    public void setStashedItems(final List<ItemStack> stashedItems) {
        this.stashedItems = stashedItems;
    }
    
    public List<ItemStack> getStashedItems() {
        return this.stashedItems;
    }
    
    public void setCooldownAltar(final int cooldownAltar) {
        this.cooldownAltar = cooldownAltar;
    }
    
    public int getCooldownAltar() {
        return this.cooldownAltar;
    }
    
    public void setHeadShot(final boolean headShot) {
        this.headShot = headShot;
    }
    
    public boolean isHeadShot() {
        return this.headShot;
    }
    
    public void setPlayingSong(final boolean playingSong) {
        this.playingSong = playingSong;
    }
    
    public boolean isPlayingSong() {
        return this.playingSong;
    }
    
    public void setInDanger(final boolean inDanger) {
        this.inDanger = inDanger;
    }
    
    public boolean isInDanger() {
        return this.inDanger;
    }
    
    public void setLastRegion(final Region lastRegion) {
        this.lastRegion = lastRegion;
    }
    
    public Region getLastRegion() {
        return this.lastRegion;
    }
    
    public Map<SMaterial, Integer> getQuiver() {
        return this.quiver;
    }
    
    public List<ActivePotionEffect> getEffects() {
        return this.effects;
    }
    
    public List<String> getTalkedNPCs() {
        return this.talkedNPCs;
    }
    
    public List<String> getTalkedVillagers() {
        return this.talkedVillagers;
    }
    
    public double getFarmingXP() {
        return this.farmingXP;
    }
    
    public boolean isBoneToZeroDamage() {
        return this.boneToZeroDamage;
    }
    
    public void setBoneToZeroDamage(final boolean boneToZeroDamage) {
        this.boneToZeroDamage = boneToZeroDamage;
    }
    
    public boolean isCooldownAPI() {
        return this.cooldownAPI;
    }
    
    public void setCooldownAPI(final boolean cooldownAPI) {
        this.cooldownAPI = cooldownAPI;
    }
    
    public double getMiningXP() {
        return this.miningXP;
    }
    
    public double getCombatXP() {
        return this.combatXP;
    }
    
    public double getEnchantXP() {
        return this.enchantXP;
    }
    
    public double getArcherXP() {
        return this.archerXP;
    }
    
    public double getCataXP() {
        return this.cataXP;
    }
    
    public double getBerserkXP() {
        return this.berserkXP;
    }
    
    public double getHealerXP() {
        return this.healerXP;
    }
    
    public double getTankXP() {
        return this.tankXP;
    }
    
    public double getMageXP() {
        return this.mageXP;
    }
    
    public double getForagingXP() {
        return this.foragingXP;
    }
    
    public boolean isSaveable() {
        return this.saveable;
    }
    
    public void setSaveable(final boolean saveable) {
        this.saveable = saveable;
    }
    
    public int getBonusFerocity() {
        return this.bonusFerocity;
    }
    
    public void setBonusFerocity(final int bonusFerocity) {
        this.bonusFerocity = bonusFerocity;
    }
    
    public boolean isFatalActive() {
        return this.fatalActive;
    }
    
    public void setFatalActive(final boolean fatalActive) {
        this.fatalActive = fatalActive;
    }
    
    public boolean isPermanentCoins() {
        return this.permanentCoins;
    }
    
    public void setPermanentCoins(final boolean permanentCoins) {
        this.permanentCoins = permanentCoins;
    }
    
    public void setSlayerQuest(final SlayerQuest slayerQuest) {
        this.slayerQuest = slayerQuest;
    }
    
    public SlayerQuest getSlayerQuest() {
        return this.slayerQuest;
    }
    
    public List<Pet.PetItem> getPets() {
        return this.pets;
    }
    
    public List<String> getUnlockedRecipes() {
        return this.unlockedRecipes;
    }
    
    public AuctionSettings getAuctionSettings() {
        return this.auctionSettings;
    }
    
    public boolean isAuctionCreationBIN() {
        return this.auctionCreationBIN;
    }
    
    public void setAuctionCreationBIN(final boolean auctionCreationBIN) {
        this.auctionCreationBIN = auctionCreationBIN;
    }
    
    public AuctionEscrow getAuctionEscrow() {
        return this.auctionEscrow;
    }
    
    public void setAuctionEscrow(final AuctionEscrow auctionEscrow) {
        this.auctionEscrow = auctionEscrow;
    }
    
    public boolean isVoidlingWardenActive() {
        return this.voidlingWardenActive;
    }
    
    public void setVoidlingWardenActive(final boolean voidlingWardenActive) {
        this.voidlingWardenActive = voidlingWardenActive;
    }
    
    public boolean isWaitingForSign() {
        return this.waitingForSign;
    }
    
    public void setWaitingForSign(final boolean waitingForSign) {
        this.waitingForSign = waitingForSign;
    }
    
    public void setSignContent(final String signContent) {
        this.signContent = signContent;
    }
    
    public String getSignContent() {
        return this.signContent;
    }
    
    public List<String> getCompletedQuests() {
        return this.completedQuests;
    }
    
    public void setCompletedQuests(final List<String> completedQuests) {
        this.completedQuests = completedQuests;
    }
    
    public List<String> getCompletedObjectives() {
        return this.completedObjectives;
    }
    
    public void setCompletedObjectives(final List<String> completedObjectives) {
        this.completedObjectives = completedObjectives;
    }
    
    public Double getIslandX() {
        return this.islandX;
    }
    
    public void setIslandX(final Double islandX) {
        this.islandX = islandX;
    }
    
    public Double getIslandZ() {
        return this.islandZ;
    }
    
    public void setIslandZ(final Double islandZ) {
        this.islandZ = islandZ;
    }
    
    public void setRank(final PlayerRank rank) {
        this.rank = rank;
    }
    
    public PlayerRank getRank() {
        return this.rank;
    }
    
    public boolean isHasIsland() {
        return this.hasIsland;
    }
    
    static {
        User.sbc = SkyBlock.getInstance().config;
        USER_CACHE = (Map)new HashMap();
        plugin = SkyBlock.getPlugin();
        USER_FOLDER = new File(User.plugin.getDataFolder(), "./users");
    }
}
