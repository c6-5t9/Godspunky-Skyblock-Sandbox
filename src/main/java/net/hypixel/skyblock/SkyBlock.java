package net.hypixel.skyblock;

import net.hypixel.skyblock.nms.pingrep.PingEvent;
import java.io.IOException;
import dev.demeng.sentinel.wrapper.exception.InvalidPlatformException;
import dev.demeng.sentinel.wrapper.exception.InvalidProductException;
import dev.demeng.sentinel.wrapper.exception.ExcessiveIpsException;
import dev.demeng.sentinel.wrapper.exception.ExcessiveServersException;
import dev.demeng.sentinel.wrapper.exception.ConnectionMismatchException;
import dev.demeng.sentinel.wrapper.exception.BlacklistedLicenseException;
import dev.demeng.sentinel.wrapper.exception.ExpiredLicenseException;
import dev.demeng.sentinel.wrapper.exception.InvalidLicenseException;
import dev.demeng.sentinel.wrapper.SentinelClient;
import net.hypixel.skyblock.nms.packetevents.PluginMessageReceived;
import net.hypixel.skyblock.nms.packetevents.WrappedPluginMessage;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.nms.packetevents.SkySimServerPingEvent;
import net.hypixel.skyblock.nms.packetevents.PacketSentServerSideEvent;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.SentPacket;
import org.bukkit.event.Event;
import net.hypixel.skyblock.nms.packetevents.PacketReceiveServerSideEvent;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.ReceivedPacket;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.PacketHandler;
import net.hypixel.skyblock.features.auction.AuctionBid;
import net.hypixel.skyblock.util.SerialNBTTagCompound;
import net.hypixel.skyblock.features.auction.AuctionEscrow;
import net.hypixel.skyblock.user.AuctionSettings;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.pet.Pet;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import java.util.function.Predicate;
import net.hypixel.skyblock.entity.EntityPopulator;
import net.hypixel.skyblock.features.region.RegionType;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.features.punishment.JoinLeaveEvent;
import net.hypixel.skyblock.features.punishment.PlayerChat;
import net.hypixel.skyblock.listener.PlayerChatListener;
import net.hypixel.skyblock.listener.PacketListener;
import net.hypixel.skyblock.gui.GUIListener;
import net.hypixel.skyblock.item.ItemListener;
import net.hypixel.skyblock.listener.ServerPingListener;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.listener.BlockListener;
import java.lang.reflect.InvocationTargetException;
import net.hypixel.skyblock.command.SCommand;
import net.hypixel.skyblock.npc.sandbox.NPCJerry;
import net.hypixel.skyblock.npc.impl.SkyblockNPCManager;
import net.hypixel.skyblock.npc.sandbox.NPCFreeItems;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;
import com.skyblock.skyblock.reflections.Reflections;
import com.skyblock.skyblock.reflections.scanners.Scanner;
import java.util.Iterator;
import org.bukkit.block.Block;
import java.util.Map;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import net.hypixel.skyblock.entity.StaticDragonManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.World;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import java.lang.reflect.Field;
import net.hypixel.skyblock.util.CC;
import org.bukkit.command.CommandExecutor;
import net.hypixel.skyblock.features.ranks.SetRankCommand;
import net.hypixel.skyblock.listener.WorldListener;
import com.comphenix.protocol.ProtocolLibrary;
import net.hypixel.skyblock.api.placeholder.SkyblockPlaceholder;
import net.hypixel.skyblock.database.RecipeDatabase;
import net.hypixel.skyblock.item.Recipe;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.features.calendar.SkyBlockCalendar;
import net.hypixel.skyblock.features.merchant.MerchantItemHandler;
import net.hypixel.skyblock.features.auction.AuctionItem;
import net.hypixel.skyblock.features.region.Region;
import net.hypixel.skyblock.entity.EntitySpawner;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.metrics.Metrics;
import net.hypixel.skyblock.nms.pingrep.PingAPI;
import net.hypixel.skyblock.nms.nmsutil.apihelper.API;
import net.hypixel.skyblock.nms.nmsutil.apihelper.APIManager;
import net.hypixel.skyblock.database.DatabaseManager;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.api.worldmanager.SkyBlockWorldManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.SLog;
import java.util.Collections;
import java.util.List;
import net.hypixel.skyblock.util.BungeeChannel;
import net.hypixel.skyblock.command.CommandLoader;
import net.hypixel.skyblock.database.SQLWorldData;
import net.hypixel.skyblock.database.SQLRegionData;
import net.hypixel.skyblock.database.SQLDatabase;
import org.bukkit.command.CommandMap;
import net.hypixel.skyblock.features.quest.QuestLineHandler;
import net.hypixel.skyblock.config.Config;
import de.slikey.effectlib.EffectManager;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.PacketHelper;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlock extends JavaPlugin implements PluginMessageListener
{
    private static ProtocolManager protocolManager;
    private static SkyBlock plugin;
    private final PacketHelper packetInj;
    private boolean authenticated;
    public static final String[] DEVELOPERS;
    public boolean altarCooldown;
    public static EffectManager effectManager;
    public Config config;
    public Config heads;
    public Config blocks;
    public Config spawners;
    private Config databaseInfo;
    private QuestLineHandler questLineHandler;
    private int onlinePlayerAcrossServers;
    public CommandMap commandMap;
    public SQLDatabase sql;
    public SQLRegionData regionData;
    public SQLWorldData worldData;
    public CommandLoader cl;
    public Repeater repeater;
    private BungeeChannel bc;
    private String serverName;
    public List<String> bannedUUID;
    
    public SkyBlock() {
        this.packetInj = new PacketHelper();
        this.altarCooldown = false;
        this.serverName = "dev";
        this.bannedUUID = (List<String>)Collections.singletonList((Object)"");
    }
    
    public void onLoad() {
        SLog.info("Loading Bukkit-serializable classes...");
        this.loadSerializableClasses();
        this.authenticate();
    }
    
    public void onEnable() {
        (SkyBlock.plugin = this).sendMessage("&aEnabling Skyblock Core. Made by " + this.getDevelopersName());
        final long start = System.currentTimeMillis();
        new BukkitRunnable() {
            public void run() {
                SkyBlock.this.sendMessage("Cleaning up the JVM (This may cause a short lag spike!)");
                final long before = System.currentTimeMillis();
                System.gc();
                final long after = System.currentTimeMillis();
                SkyBlock.this.sendMessage("It took " + (after - before) + "ms to cleanup the JVM heap");
            }
        }.runTaskTimer((Plugin)this, 1L, 12000L);
        this.sendMessage("&aLoading SkyBlock worlds...");
        SkyBlockWorldManager.loadWorlds();
        this.sendMessage("&aLoading YAML data from disk...");
        this.config = new Config("config.yml");
        if (this.authenticated) {
            this.heads = new Config("heads.yml");
            this.blocks = new Config("blocks.yml");
            this.spawners = new Config("spawners.yml");
            this.databaseInfo = new Config("database.yml");
            this.sendMessage("&aLoading Command map...");
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                this.commandMap = (CommandMap)f.get((Object)Bukkit.getServer());
            }
            catch (final IllegalAccessException | NoSuchFieldException e) {
                SLog.severe("Couldn't load command map: ");
                e.printStackTrace();
            }
            this.sendMessage("&aLoading SQL database...");
            this.sql = new SQLDatabase();
            if (!this.config.getBoolean("Config")) {
                try {
                    DatabaseManager.connectToDatabase(this.databaseInfo.getString("uri"), this.databaseInfo.getString("name"));
                }
                catch (final Exception ex) {
                    SLog.warn("An error occurred while connecting to mongodb with uri : " + this.databaseInfo.getString("uri"));
                }
            }
            this.regionData = new SQLRegionData();
            this.worldData = new SQLWorldData();
            this.cl = new CommandLoader();
            this.sendMessage("&aBegin Protocol injection... (SkyBlockProtocol v0.6.2)");
            APIManager.registerAPI(this.packetInj, (Plugin)this);
            if (!this.packetInj.injected) {
                this.getLogger().warning("[FATAL ERROR] Protocol Injection failed. Disabling the plugin for safety...");
                Bukkit.getPluginManager().disablePlugin((Plugin)this);
                return;
            }
            this.sendMessage("&aInjecting...");
            PingAPI.register();
            new Metrics(this);
            APIManager.initAPI(PacketHelper.class);
            this.sendMessage("&aStarting server loop...");
            this.repeater = new Repeater();
            this.sendMessage("&aLoading commands...");
            this.loadCommands();
            this.sendMessage("&aLoading listeners...");
            this.loadListeners();
            this.sendMessage("&aInjecting Packet/Ping Listener into the core...");
            this.registerPacketListener();
            this.registerPingListener();
            this.sendMessage("&aStarting entity spawners...");
            EntitySpawner.startSpawnerTask();
            this.sendMessage("&aEstablishing player regions...");
            Region.cacheRegions();
            this.sendMessage("&aLoading NPCS...");
            this.registerNPCS();
            this.sendMessage("&aLoading auction items from disk...");
            SkyBlock.effectManager = new EffectManager((Plugin)this);
            AuctionItem.loadAuctionsFromDisk();
            this.sendMessage("&aLoading Quest!");
            this.initializeQuests();
            this.sendMessage("&aLoading merchants prices...");
            MerchantItemHandler.init();
            this.sendMessage("&aSynchronizing world time with calendar time and removing world entities...");
            SkyBlockCalendar.synchronize();
            this.sendMessage("&aLoading items...");
            SMaterial.loadItems();
            this.sendMessage("&aConverting CraftRecipes into custom recipes...");
            Recipe.loadRecipes();
            if (!this.config.getBoolean("Config")) {
                this.sendMessage("&aLoading recipes from database...");
                RecipeDatabase.loadRecipes();
            }
            this.sendMessage("&aHooking SkyBlockEngine to PlaceholderAPI and registering...");
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                new SkyblockPlaceholder().register();
                this.sendMessage("&aHooked to PAPI successfully!");
            }
            else {
                this.sendMessage("&aERROR! PlaceholderAPI plugin does not exist, disabing placeholder request!");
            }
            SkyBlock.protocolManager = ProtocolLibrary.getProtocolManager();
            WorldListener.init();
            this.sendMessage("&aSuccessfully enabled " + this.getDescription().getFullName());
            this.sendMessage("&a===================================");
            this.sendMessage("&aSkyBlock ENGINE - MADE BY " + this.getDevelopersName());
            this.sendMessage("&aPLUGIN ENABLED! HOOKED INTO SkyBlock!");
            this.sendMessage("&a ");
            this.sendMessage("&aThis plugin provide most of SkyBlock functions!");
            this.sendMessage("&aOriginally was made by super");
            this.sendMessage("&aAny illegal usage will be suppressed! DO NOT LEAK IT!");
            this.sendMessage("&a===================================");
            this.startPopulators();
            this.getCommand("setrank").setExecutor((CommandExecutor)new SetRankCommand());
            final long end = System.currentTimeMillis();
            this.sendMessage("&aSuccessfully enabled Hub Core in " + CC.getTimeDifferenceAndColor(start, end) + "&a.");
        }
        else {
            this.getServer().shutdown();
        }
    }
    
    public static SkyBlock getInstance() {
        return SkyBlock.plugin;
    }
    
    private void initializeQuests() {
        this.sendMessage("&aInitializing quests...");
        final long start = System.currentTimeMillis();
        this.questLineHandler = new QuestLineHandler();
        this.sendMessage("&aSuccessfully registered " + (Object)ChatColor.GREEN + this.questLineHandler.getQuests().size() + (Object)ChatColor.WHITE + " quests [" + SUtil.getTimeDifferenceAndColor(start, System.currentTimeMillis()) + (Object)ChatColor.WHITE + "]");
    }
    
    public void onDisable() {
        this.sendMessage("&aSaving Player data...");
        for (final User user : User.getCachedUsers()) {
            if (user == null) {
                continue;
            }
            if (user.getUuid() == null) {
                continue;
            }
            if (this.config.getBoolean("Config")) {
                user.configsave();
            }
            else {
                user.save().thenRun(user::kick);
            }
        }
        this.sendMessage("&aKilling all non-human entities...");
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (entity instanceof HumanEntity) {
                    continue;
                }
                entity.remove();
            }
        }
        if (this.repeater != null && EntitySpawner.class != null && EntitySpawner.class != null && StaticDragonManager.class != null && SkyBlockCalendar.class != null) {
            this.sendMessage("&aStopping server loop...");
            this.repeater.stop();
            this.sendMessage("&aUnloading ores from Dwarven Mines...");
            WorldListener.unloadBlocks();
            this.sendMessage("&aEjecting protocol channel...");
            APIManager.disableAPI(PacketHelper.class);
            this.sendMessage("&aCleaning HashSets...");
            for (final Map.Entry<Entity, Block> entry : VoidgloomSeraph.CACHED_BLOCK.entrySet()) {
                final Entity stand = (Entity)entry.getKey();
                if (stand != null && VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand)) {
                    ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand), true);
                }
            }
            this.sendMessage("&aStopping entity spawners...");
            EntitySpawner.stopSpawnerTask();
            this.sendMessage("&aEnding Dragons fight... (If one is currently active)");
            StaticDragonManager.endFight();
            this.sendMessage("&aSaving calendar time...");
            SkyBlockCalendar.saveElapsed();
            this.sendMessage("&aSaving auction data...");
            for (final AuctionItem item : AuctionItem.getAuctions()) {
                item.save();
            }
            SkyBlock.plugin = null;
        }
        this.sendMessage("&aDisabled " + this.getDescription().getFullName());
        this.sendMessage("&a===================================");
        this.sendMessage("&aSkyBlock ENGINE - MADE BY " + this.getDevelopersName());
        this.sendMessage("&aPLUGIN DISABLED!");
        this.sendMessage("&a===================================");
    }
    
    private void registerNPCS() {
        final Reflections reflections = new Reflections("net.hypixel.skyblock.npc.hub", new Scanner[0]);
        for (final Class<? extends SkyBlockNPC> npcClazz : reflections.getSubTypesOf(SkyBlockNPC.class)) {
            try {
                npcClazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.config.getBoolean("sandbox")) {
            SkyblockNPCManager.registerNPC(new NPCFreeItems());
            SkyblockNPCManager.registerNPC(new NPCJerry());
        }
        this.sendMessage("&aSuccessfully loaded &e" + SkyblockNPCManager.getNPCS().size() + "&a NPCs");
    }
    
    private void loadCommands() {
        final Reflections reflections = new Reflections("net.hypixel.skyblock.command", new Scanner[0]);
        this.sendMessage("&eRegistering commands...");
        int count = 0;
        for (final Class<? extends SCommand> command : reflections.getSubTypesOf(SCommand.class)) {
            try {
                this.cl.register((SCommand)command.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]));
                ++count;
            }
            catch (final NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
                SLog.severe("An exception occured when loading " + command.getSimpleName());
                SLog.severe(exception.getMessage());
            }
        }
        this.sendMessage("&eRegistered " + count + " commands");
    }
    
    private void loadListeners() {
        new BlockListener();
        new PlayerListener();
        new ServerPingListener();
        new ItemListener();
        new GUIListener();
        new PacketListener();
        new WorldListener();
        new PlayerChatListener();
        new PlayerChat();
        new JoinLeaveEvent();
    }
    
    private void startPopulators() {
        new EntityPopulator(5, 10, 200L, SEntityType.ENCHANTED_DIAMOND_SKELETON, RegionType.OBSIDIAN_SANCTUARY).start();
        new EntityPopulator(5, 10, 200L, SEntityType.ENCHANTED_DIAMOND_ZOMBIE, RegionType.OBSIDIAN_SANCTUARY).start();
        new EntityPopulator(5, 10, 200L, SEntityType.DIAMOND_ZOMBIE, RegionType.DIAMOND_RESERVE).start();
        new EntityPopulator(5, 10, 200L, SEntityType.DIAMOND_SKELETON, RegionType.DIAMOND_RESERVE).start();
        new EntityPopulator(5, 15, 200L, SEntityType.SMALL_SLIME, RegionType.SLIMEHILL).start();
        new EntityPopulator(5, 10, 200L, SEntityType.MEDIUM_SLIME, RegionType.SLIMEHILL).start();
        new EntityPopulator(5, 5, 400L, SEntityType.LARGE_SLIME, RegionType.SLIMEHILL).start();
        new EntityPopulator(5, 30, 400L, SEntityType.PIGMAN, RegionType.PIGMENS_DEN).start();
        new EntityPopulator(5, 30, 400L, SEntityType.LAPIS_ZOMBIE, RegionType.LAPIS_QUARRY).start();
        new EntityPopulator(5, 10, 400L, SEntityType.SNEAKY_CREEPER, RegionType.GUNPOWDER_MINES).start();
        new EntityPopulator(6, 20, 300L, SEntityType.WEAK_ENDERMAN, RegionType.THE_END_NEST).start();
        new EntityPopulator(6, 20, 300L, SEntityType.ENDERMAN, RegionType.THE_END_NEST).start();
        new EntityPopulator(6, 20, 300L, SEntityType.STRONG_ENDERMAN, RegionType.THE_END_NEST).start();
        new EntityPopulator(10, 30, 200L, SEntityType.ZEALOT, RegionType.DRAGONS_NEST).start();
        new EntityPopulator(1, 5, 1200L, SEntityType.ENDER_CHEST_ZEALOT, RegionType.DRAGONS_NEST).start();
        new EntityPopulator(5, 20, 200L, SEntityType.WATCHER, RegionType.DRAGONS_NEST).start();
        new EntityPopulator(5, 10, 200L, SEntityType.OBSIDIAN_DEFENDER, RegionType.DRAGONS_NEST).start();
        new EntityPopulator(5, 20, 300L, SEntityType.SPLITTER_SPIDER, RegionType.SPIDERS_DEN_HIVE).start();
        new EntityPopulator(5, 20, 300L, SEntityType.WEAVER_SPIDER, RegionType.SPIDERS_DEN_HIVE).start();
        new EntityPopulator(5, 20, 300L, SEntityType.VORACIOUS_SPIDER, RegionType.SPIDERS_DEN_HIVE).start();
        new EntityPopulator(5, 20, 300L, SEntityType.SPIDER_JOCKEY, RegionType.SPIDERS_DEN_HIVE).start();
        new EntityPopulator(5, 20, 300L, SEntityType.DASHER_SPIDER, RegionType.SPIDERS_DEN_HIVE).start();
        new EntityPopulator(5, 10, 300L, SEntityType.HIGH_LEVEL_SKELETON, RegionType.HIGH_LEVEL, (Predicate<World>)(world -> world.getTime() >= 13188L && world.getTime() <= 22812L)).start();
        new EntityPopulator(5, 15, 200L, SEntityType.ZOMBIE, RegionType.GRAVEYARD).start();
        new EntityPopulator(5, 15, 200L, SEntityType.ZOMBIE_VILLAGER, RegionType.GRAVEYARD).start();
        new EntityPopulator(5, 20, 200L, SEntityType.WOLF, RegionType.RUINS).start();
        new EntityPopulator(2, 4, 200L, SEntityType.OLD_WOLF, RegionType.RUINS).start();
        new EntityPopulator(5, 30, 200L, SEntityType.CRYPT_GHOUL, RegionType.COAL_MINE_CAVES).start();
        new EntityPopulator(1, 1, 200L, SEntityType.GOLDEN_GHOUL, RegionType.COAL_MINE_CAVES).start();
        new EntityPopulator(4, 4, 200L, SEntityType.SOUL_OF_THE_ALPHA, RegionType.HOWLING_CAVE).start();
        new EntityPopulator(5, 15, 200L, SEntityType.HOWLING_SPIRIT, RegionType.HOWLING_CAVE).start();
        new EntityPopulator(5, 15, 200L, SEntityType.PACK_SPIRIT, RegionType.HOWLING_CAVE).start();
    }
    
    private void loadSerializableClasses() {
        ConfigurationSerialization.registerClass((Class)SlayerQuest.class, "SlayerQuest");
        ConfigurationSerialization.registerClass((Class)Pet.PetItem.class, "PetItem");
        ConfigurationSerialization.registerClass((Class)SItem.class, "SItem");
        ConfigurationSerialization.registerClass((Class)AuctionSettings.class, "AuctionSettings");
        ConfigurationSerialization.registerClass((Class)AuctionEscrow.class, "AuctionEscrow");
        ConfigurationSerialization.registerClass((Class)SerialNBTTagCompound.class, "SerialNBTTagCompound");
        ConfigurationSerialization.registerClass((Class)AuctionBid.class, "AuctionBid");
    }
    
    private void registerPacketListener() {
        PacketHelper.addPacketHandler(new PacketHandler() {
            @Override
            public void onReceive(final ReceivedPacket packet) {
                final PacketReceiveServerSideEvent ev = new PacketReceiveServerSideEvent(packet);
                Bukkit.getPluginManager().callEvent((Event)ev);
            }
            
            @Override
            public void onSend(final SentPacket packet) {
                final PacketSentServerSideEvent ev = new PacketSentServerSideEvent(packet);
                Bukkit.getPluginManager().callEvent((Event)ev);
            }
        });
    }
    
    private void registerPingListener() {
        PingAPI.registerListener(event -> {
            final SkySimServerPingEvent e = new SkySimServerPingEvent(event);
            Bukkit.getPluginManager().callEvent((Event)e);
        });
    }
    
    public static Player findPlayerByIPAddress(final String ip) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getAddress().toString().contains((CharSequence)ip)) {
                return p;
            }
        }
        return null;
    }
    
    public String getDevelopersName() {
        final StringBuilder builder = new StringBuilder();
        for (final String name : SkyBlock.DEVELOPERS) {
            builder.append(name).append(" , ");
        }
        return builder.toString().substring(0, builder.length() - 2);
    }
    
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        final PluginMessageReceived e = new PluginMessageReceived(new WrappedPluginMessage(channel, player, message));
        Bukkit.getPluginManager().callEvent((Event)e);
    }
    
    private void authenticate() {
        final String licenseKey = this.getConfig().getString("licenseKey");
        final SentinelClient client = new SentinelClient("http://us3.gbnodes.host:25567/api/v1", "794keb5m86tr33ushl4r6nd4nb", "ncUU50MVVC8t8clfFaDH0B+X6jCgctxwS4PWx1HXjbQ=");
        this.authenticated = false;
        try {
            this.sendMessage("&aAuthenticating license key with our webserver.");
            this.sendMessage("&aPlease wait while the plugin is getting started....");
            client.getLicenseController().auth(licenseKey, "SkyblockCore", null, null, SentinelClient.getCurrentHwid(), SentinelClient.getCurrentIp());
            this.authenticated = true;
        }
        catch (final InvalidLicenseException e) {
            this.sendMessage("&c&lInvalid license key.");
        }
        catch (final ExpiredLicenseException e2) {
            this.sendMessage("&c&lExpired.");
        }
        catch (final BlacklistedLicenseException e3) {
            this.sendMessage("&c&lBlacklisted. Please contact us on discord if you think this is an error");
        }
        catch (final ConnectionMismatchException e4) {
            this.sendMessage("&c&lProvided connection does not match.");
        }
        catch (final ExcessiveServersException e5) {
            this.sendMessage("&c&lToo many servers. (Max: " + e5.getMaxServers() + ")");
        }
        catch (final ExcessiveIpsException e6) {
            this.sendMessage("&c&lToo many IPs. (Max: " + e6.getMaxIps() + ")");
        }
        catch (final InvalidProductException e7) {
            this.sendMessage("&c&lLicense is for different product.");
        }
        catch (final InvalidPlatformException e8) {
            this.sendMessage("&c&lProvided connection platform is invalid.");
        }
        catch (final IOException e9) {
            this.sendMessage("&c&lAn unexpected error occurred.");
        }
        if (this.authenticated) {
            this.sendMessage("&a&lSuccessfully authenticated. Thanks for purchasing this plugin :D");
        }
    }
    
    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', "&7[&aHypixel&bSkyblock&dCore&7] &f");
    }
    
    public void sendMessage(final String message) {
        Bukkit.getConsoleSender().sendMessage(this.getPrefix() + CC.translate(message));
    }
    
    public static ProtocolManager getProtocolManager() {
        return SkyBlock.protocolManager;
    }
    
    public static SkyBlock getPlugin() {
        return SkyBlock.plugin;
    }
    
    public Config getDatabaseInfo() {
        return this.databaseInfo;
    }
    
    public QuestLineHandler getQuestLineHandler() {
        return this.questLineHandler;
    }
    
    public void setOnlinePlayerAcrossServers(final int onlinePlayerAcrossServers) {
        this.onlinePlayerAcrossServers = onlinePlayerAcrossServers;
    }
    
    public int getOnlinePlayerAcrossServers() {
        return this.onlinePlayerAcrossServers;
    }
    
    public BungeeChannel getBc() {
        return this.bc;
    }
    
    public void setServerName(final String serverName) {
        this.serverName = serverName;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    static {
        DEVELOPERS = new String[] { "Hamza", "EpicPortal", "Dumbo" };
    }
}
