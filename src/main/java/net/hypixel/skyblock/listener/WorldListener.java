package net.hypixel.skyblock.listener;

import java.util.HashMap;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitTask;
import net.hypixel.skyblock.features.region.RegionType;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Slime;
import net.hypixel.skyblock.entity.SlimeStatistics;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.World;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import net.hypixel.skyblock.entity.dragon.Dragon;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import java.util.Iterator;
import java.util.Collection;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SBlock;
import net.hypixel.skyblock.item.ItemOrigin;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.item.ExperienceRewardStatistics;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.util.Groups;
import org.bukkit.GameMode;
import net.hypixel.skyblock.features.region.Region;
import net.hypixel.skyblock.user.User;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import net.hypixel.skyblock.entity.caverns.CreeperFunction;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.event.CreeperIgniteEvent;
import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.entity.Fireball;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.block.Block;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.block.BlockState;
import java.util.List;
import java.util.UUID;
import java.util.Map;

public class WorldListener extends PListener
{
    private static final Map<UUID, List<BlockState>> RESTORER;
    private static final List<UUID> ALREADY_TELEPORTING;
    public static final Map<UUID, EnumWrappers.PlayerDigType> isCM;
    public static final Map<UUID, Boolean> isSWI;
    public static final Map<UUID, Integer> miningSpeed;
    public static final Map<UUID, Integer> breakingPower;
    public static final Map<Block, Integer> CACHED_BLOCK_ID;
    public static final Map<Block, Byte> CACHED_BLOCK_BYTE;
    public static ArrayList<Material> blb;
    public static ArrayList<Block> changed_blocks;
    
    @EventHandler
    public void onCreatureSpawn(final CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }
        if (e.getEntity() instanceof FallingBlock) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityChangeBlock(final EntityChangeBlockEvent e) {
        if (e.getEntity().getType() == EntityType.ENDERMAN) {
            e.setCancelled(true);
        }
        if (e.getBlock().getType() == Material.SOIL && e.getTo() == Material.DIRT) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent e) {
        final Entity entity = e.getEntity();
        if (entity instanceof EnderDragonPart || entity instanceof EnderDragon || entity instanceof Creeper) {
            e.blockList().clear();
        }
    }
    
    @EventHandler
    public void onBlockIgnite(final BlockIgniteEvent e) {
        if (e.getIgnitingEntity() instanceof Fireball) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockFade(final BlockFadeEvent e) {
        if (e.getNewState().getType() == Material.DIRT || e.getNewState().getType() == Material.GRASS) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        final Entity entity = (Entity)e.getEntity();
        if (!entity.hasMetadata("specEntityObject")) {
            return;
        }
        e.getDrops().clear();
        final PlayerInventory inventory = e.getEntity().getKiller().getInventory();
        SItem helmet = null;
        SItem chestplate = null;
        SItem leggings = null;
        SItem boots = null;
        if (inventory.getHelmet() != null) {
            helmet = SItem.find(inventory.getHelmet());
        }
        if (inventory.getChestplate() != null) {
            chestplate = SItem.find(inventory.getChestplate());
        }
        if (inventory.getLeggings() != null) {
            leggings = SItem.find(inventory.getLeggings());
        }
        if (inventory.getBoots() != null) {
            boots = SItem.find(inventory.getBoots());
        }
        if (e.getEntity().getType() == EntityType.ENDERMAN && helmet.getType() == SMaterial.VOIDBANE_HELMET && leggings.getType() == SMaterial.VOIDBANE_LEGGINGS && chestplate.getType() == SMaterial.VOIDBANE_CHESTPLATE && boots.getType() == SMaterial.VOIDBANE_BOOTS) {
            helmet.setKills(helmet.getDataInt("kills") + 1);
            chestplate.setKills(chestplate.getDataInt("kills") + 1);
            leggings.setKills(leggings.getDataInt("kills") + 1);
            boots.setKills(boots.getDataInt("kills") + 1);
        }
    }
    
    @EventHandler
    public void onCreeperIgnite(final CreeperIgniteEvent e) {
        final Creeper creeper = e.getEntity();
        final SEntity sEntity = SEntity.findSEntity((Entity)creeper);
        if (sEntity == null) {
            return;
        }
        if (sEntity.getFunction() instanceof CreeperFunction) {
            ((CreeperFunction)sEntity.getFunction()).onCreeperIgnite(e, sEntity);
        }
    }
    
    @EventHandler
    public void onLeafDecay(final LeavesDecayEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        final Block block = e.getBlock();
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final SMaterial equiv = SMaterial.getSpecEquivalent(block.getType(), block.getData());
        final Region region = Region.getRegionOfBlock(block);
        final Collection<ItemStack> drops = (Collection<ItemStack>)block.getDrops(e.getPlayer().getItemInHand());
        if (player.getGameMode() != GameMode.CREATIVE) {
            boolean allowBreak = false;
            if (region != null) {
                if (Groups.FORAGING_REGIONS.contains((Object)region.getType()) && (block.getType() == Material.LOG || block.getType() == Material.LOG_2 || block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2)) {
                    allowBreak = true;
                    final int level = Skill.getLevel(user.getSkillXP(ForagingSkill.INSTANCE), ForagingSkill.INSTANCE.hasSixtyLevels());
                    final double d = ForagingSkill.INSTANCE.getDoubleDropChance(level);
                    final double t = ForagingSkill.INSTANCE.getTripleDropChance(level);
                    extraDrops(drops, d, t, block);
                    addToRestorer(block, player);
                }
                if (Groups.FARMING_REGIONS.contains((Object)region.getType()) && Groups.FARMING_MATERIALS.contains((Object)block.getType())) {
                    allowBreak = true;
                    final int level = Skill.getLevel(user.getSkillXP(FarmingSkill.INSTANCE), FarmingSkill.INSTANCE.hasSixtyLevels());
                    final double d = FarmingSkill.INSTANCE.getDoubleDropChance(level);
                    extraDrops(drops, d, 0.0, block);
                }
                if (Groups.MINING_REGIONS.contains((Object)region.getType())) {
                    final Material type = block.getType();
                    switch (type) {
                        case COAL_ORE:
                        case DIAMOND_BLOCK:
                        case DIAMOND_ORE:
                        case EMERALD_ORE:
                        case GOLD_ORE:
                        case IRON_ORE:
                        case LAPIS_ORE:
                        case REDSTONE_ORE: {
                            block.setType(Material.STONE);
                            break;
                        }
                        case STONE: {
                            if (block.getData() != 0) {
                                break;
                            }
                            block.setType(Material.COBBLESTONE);
                            break;
                        }
                        case OBSIDIAN:
                        case ENDER_STONE:
                        case NETHERRACK:
                        case COBBLESTONE: {
                            block.setType(Material.BEDROCK);
                            regenerateLater(block, 60L, region.getType());
                            break;
                        }
                    }
                    if (type != block.getType()) {
                        e.setCancelled(true);
                        if (equiv.getStatistics() instanceof ExperienceRewardStatistics) {
                            Skill.reward(((ExperienceRewardStatistics)equiv.getStatistics()).getRewardedSkill(), ((ExperienceRewardStatistics)equiv.getStatistics()).getRewardXP(), player);
                        }
                        final int level2 = Skill.getLevel(user.getSkillXP(MiningSkill.INSTANCE), MiningSkill.INSTANCE.hasSixtyLevels());
                        final double d2 = MiningSkill.INSTANCE.getDoubleDropChance(level2);
                        final double t2 = MiningSkill.INSTANCE.getTripleDropChance(level2);
                        for (final ItemStack drop : drops) {
                            final SItem conv = SItem.convert(drop);
                            conv.setOrigin(ItemOrigin.NATURAL_BLOCK);
                            block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5, 0.5, 0.5), conv.getStack());
                        }
                        extraDrops(drops, d2, t2, block);
                    }
                    if (block.getType() == Material.GLOWSTONE) {
                        allowBreak = true;
                        addToRestorer(block, player);
                    }
                }
            }
            if (user.isOnIsland(block)) {
                allowBreak = true;
            }
            if (!allowBreak) {
                e.setCancelled(true);
            }
        }
        if (equiv.getStatistics() instanceof ExperienceRewardStatistics && !e.isCancelled()) {
            Skill.reward(((ExperienceRewardStatistics)equiv.getStatistics()).getRewardedSkill(), ((ExperienceRewardStatistics)equiv.getStatistics()).getRewardXP(), player);
        }
        final SBlock sBlock = SBlock.getBlock(e.getBlock().getLocation());
        if (sBlock != null && !e.isCancelled()) {
            sBlock.delete();
        }
        if (e.isCancelled() || player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        e.setCancelled(true);
        for (final ItemStack drop2 : drops) {
            final SItem conv2 = SItem.convert(drop2);
            conv2.setOrigin(ItemOrigin.NATURAL_BLOCK);
            block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5, 0.5, 0.5), conv2.getStack());
        }
        block.setType(Material.AIR);
    }
    
    @EventHandler
    public void onFarmlandDecay(final BlockPhysicsEvent e) {
        if (e.getChangedType() == Material.SOIL) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityTarget(final EntityTargetLivingEntityEvent e) {
        final Entity entity = e.getEntity();
        final SEntity sEntity = SEntity.findSEntity(entity);
        if (sEntity == null) {
            return;
        }
        sEntity.getFunction().onTarget(sEntity, e);
        if (!(sEntity.getGenericInstance() instanceof Dragon)) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPortalEnter(final EntityPortalEnterEvent e) {
        final Material portalType = e.getLocation().getBlock().getType();
        final Entity entity = e.getEntity();
        if (WorldListener.ALREADY_TELEPORTING.contains((Object)entity.getUniqueId())) {
            return;
        }
        if (portalType == Material.PORTAL) {
            final World hub = Bukkit.getWorld("world");
            if (hub == null) {
                entity.sendMessage((Object)ChatColor.RED + "Could not find a hub world to teleport you to!");
                return;
            }
            WorldListener.ALREADY_TELEPORTING.add((Object)entity.getUniqueId());
            SUtil.delay(() -> WorldListener.ALREADY_TELEPORTING.remove((Object)entity.getUniqueId()), 15L);
            entity.sendMessage((Object)ChatColor.GRAY + "Sending you to the hub...");
            entity.teleport(hub.getSpawnLocation());
        }
        else {
            if (!(entity instanceof Player)) {
                return;
            }
            WorldListener.ALREADY_TELEPORTING.add((Object)entity.getUniqueId());
            SUtil.delay(() -> WorldListener.ALREADY_TELEPORTING.remove((Object)entity.getUniqueId()), 15L);
            entity.sendMessage((Object)ChatColor.GRAY + "Sending you to your island...");
            PlayerUtils.sendToIsland(Bukkit.getPlayer(e.getEntity().getUniqueId()));
        }
    }
    
    @EventHandler
    public void onPortal(final PlayerPortalEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPortalCreate(final EntityCreatePortalEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSlimeSplit(final SlimeSplitEvent e) {
        final Slime slime = e.getEntity();
        final SEntity sEntity = SEntity.findSEntity((Entity)slime);
        if (sEntity != null && sEntity.getStatistics() instanceof SlimeStatistics && !((SlimeStatistics)sEntity.getStatistics()).split()) {
            e.setCancelled(true);
        }
    }
    
    private static void addToRestorer(final Block block, final Player player) {
        if (WorldListener.RESTORER.containsKey((Object)player.getUniqueId())) {
            ((List)WorldListener.RESTORER.get((Object)player.getUniqueId())).add((Object)block.getState());
        }
        else {
            WorldListener.RESTORER.put((Object)player.getUniqueId(), (Object)new ArrayList());
            ((List)WorldListener.RESTORER.get((Object)player.getUniqueId())).add((Object)block.getState());
            new BukkitRunnable() {
                public void run() {
                    for (final BlockState state : (List)WorldListener.RESTORER.get((Object)player.getUniqueId())) {
                        state.getBlock().setType(state.getType());
                        state.setRawData(state.getRawData());
                        state.update();
                    }
                    WorldListener.RESTORER.remove((Object)player.getUniqueId());
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1200L);
        }
    }
    
    private static void extraDrops(final Collection<ItemStack> drops, final double d, final double t, final Block block) {
        for (final ItemStack drop : drops) {
            int amount = 0;
            if (SUtil.random(0.0, 1.0) < t) {
                amount = 2;
            }
            else if (SUtil.random(0.0, 1.0) < d) {
                amount = 1;
            }
            if (amount == 0) {
                continue;
            }
            block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5, 0.5, 0.5), SUtil.setStackAmount(drop, amount));
        }
    }
    
    private static BukkitTask regenerateLater(final Block block, final long ticks, final RegionType type) {
        return new BukkitRunnable() {
            public void run() {
                if (block.getType() != Material.BEDROCK) {
                    return;
                }
                final int r5 = SUtil.random(1, 5);
                switch (type) {
                    case COAL_MINE: {
                        if (SUtil.random(1, 15) == 1) {
                            block.setType(Material.COAL_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case GOLD_MINE:
                    case GUNPOWDER_MINES: {
                        if (SUtil.random(1, 20) == 1) {
                            block.setType(Material.GOLD_ORE);
                            break;
                        }
                        if (r5 == 1) {
                            block.setType(Material.IRON_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case LAPIS_QUARRY: {
                        if (r5 == 1) {
                            block.setType(Material.LAPIS_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case PIGMENS_DEN: {
                        if (r5 == 1) {
                            block.setType(Material.REDSTONE_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case SLIMEHILL: {
                        if (r5 == 1) {
                            block.setType(Material.EMERALD_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case DIAMOND_RESERVE: {
                        if (r5 == 1) {
                            block.setType(Material.DIAMOND_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case OBSIDIAN_SANCTUARY: {
                        if (SUtil.random(1, 40) == 1) {
                            block.setType(Material.DIAMOND_BLOCK);
                            break;
                        }
                        if (SUtil.random(1, 30) == 1) {
                            block.setType(Material.OBSIDIAN);
                            break;
                        }
                        if (r5 == 1) {
                            block.setType(Material.DIAMOND_ORE);
                            break;
                        }
                        block.setType(Material.STONE);
                        break;
                    }
                    case THE_END:
                    case DRAGONS_NEST: {
                        block.setType(Material.ENDER_STONE);
                        break;
                    }
                    case BLAZING_FORTRESS: {
                        block.setType(Material.NETHERRACK);
                        break;
                    }
                    default: {
                        block.setType(Material.STONE);
                        break;
                    }
                }
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), ticks);
    }
    
    @EventHandler
    public void bpe(final BlockPlaceEvent e) {
        if (e.getBlock().getType() != Material.PRISMARINE) {
            return;
        }
        e.getBlock().setMetadata("block_hardness", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)1200));
        e.getBlock().setMetadata("block_power", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)4));
    }
    
    public static void init() {
        WorldListener.blb.add((Object)Material.BEDROCK);
        WorldListener.blb.add((Object)Material.COMMAND);
        WorldListener.blb.add((Object)Material.BARRIER);
        WorldListener.blb.add((Object)Material.ENDER_PORTAL_FRAME);
        WorldListener.blb.add((Object)Material.ENDER_PORTAL);
        SkyBlock.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(SkyBlock.getPlugin(), ListenerPriority.HIGHEST, new PacketType[] { PacketType.Play.Client.BLOCK_DIG }) {
            public void onPacketReceiving(final PacketEvent event) {
                final PacketContainer packet = event.getPacket();
                final EnumWrappers.PlayerDigType digType = (EnumWrappers.PlayerDigType)packet.getPlayerDigTypes().getValues().get(0);
                if (event.getPlayer() != null) {
                    WorldListener.isCM.put((Object)event.getPlayer().getUniqueId(), (Object)digType);
                }
            }
        });
    }
    
    public static void unloadBlocks() {
        if (WorldListener.changed_blocks.isEmpty()) {
            return;
        }
        for (final Block block : WorldListener.changed_blocks) {
            if (WorldListener.CACHED_BLOCK_ID.containsKey((Object)block) && WorldListener.CACHED_BLOCK_BYTE.containsKey((Object)block)) {
                final int id = (int)WorldListener.CACHED_BLOCK_ID.get((Object)block);
                final byte data = (byte)WorldListener.CACHED_BLOCK_BYTE.get((Object)block);
                block.setTypeIdAndData(id, data, true);
                if (!WorldListener.changed_blocks.contains((Object)block)) {
                    continue;
                }
                WorldListener.changed_blocks.remove((Object)block);
            }
        }
    }
    
    public double findDivFor(final double a) {
        final double cumB = a / 10.0 / a;
        final double cumTongHopA = a * cumB;
        return 10.0 / cumTongHopA;
    }
    
    public int getPlayerMiningSpeed(final Player p) {
        if (WorldListener.miningSpeed.containsKey((Object)p.getUniqueId())) {
            return (int)WorldListener.miningSpeed.get((Object)p.getUniqueId());
        }
        WorldListener.miningSpeed.put((Object)p.getUniqueId(), (Object)100);
        return 600;
    }
    
    public int getPlayerBreakingPower(final Player p) {
        if (WorldListener.breakingPower.containsKey((Object)p.getUniqueId())) {
            return (int)WorldListener.breakingPower.get((Object)p.getUniqueId());
        }
        WorldListener.breakingPower.put((Object)p.getUniqueId(), (Object)8);
        return 8;
    }
    
    public double findMiningSpeedFor(final Player p, final Block b) {
        double finalResult = 0.0;
        double blockHardness = 15.0;
        double blockPower = 0.0;
        final List<MetadataValue> a = (List<MetadataValue>)b.getMetadata("block_hardness");
        final Iterator<MetadataValue> iterator = (Iterator<MetadataValue>)a.iterator();
        if (iterator.hasNext()) {
            final MetadataValue mv = (MetadataValue)iterator.next();
            blockHardness = mv.asInt();
            p.sendMessage("" + blockHardness);
        }
        final List<MetadataValue> aA = (List<MetadataValue>)b.getMetadata("block_power");
        final Iterator<MetadataValue> iterator2 = (Iterator<MetadataValue>)aA.iterator();
        if (iterator2.hasNext()) {
            final MetadataValue mv2 = (MetadataValue)iterator2.next();
            blockPower = mv2.asInt();
            p.sendMessage("" + blockPower);
        }
        if (blockHardness == 0.0) {
            blockHardness = 15.0;
        }
        if (WorldListener.blb.contains((Object)b.getType())) {
            blockHardness = 2.0E10;
        }
        finalResult = blockHardness * 30.0 / this.getPlayerMiningSpeed(p);
        if (blockPower > this.getPlayerBreakingPower(p)) {
            finalResult = 1.0E8;
        }
        return finalResult;
    }
    
    public double findMSIS(final Player p, final Block b) {
        double finalResult = 0.0;
        double blockHardness = 100.0;
        double blockPower = 4.0;
        if (b.getType() == Material.WOOL) {
            if (b.getData() == 3) {
                blockPower = 4.0;
                blockHardness = 1500.0;
            }
            else {
                if (b.getData() != 7) {
                    return -1.0;
                }
                blockPower = 4.0;
                blockHardness = 500.0;
            }
        }
        else if (b.getType() == Material.PRISMARINE) {
            blockPower = 4.0;
            blockHardness = 800.0;
        }
        else if (b.getType() == Material.STAINED_CLAY) {
            if (b.getData() != 9) {
                return -1.0;
            }
            blockPower = 4.0;
            blockHardness = 500.0;
        }
        else if (b.getType() == Material.STONE) {
            if (b.getData() == 4) {
                blockPower = 5.0;
                blockHardness = 2000.0;
            }
            else if (b.getData() == 0) {
                blockHardness = this.miningValueForMaterial(b.getType());
                blockPower = 4.0;
            }
        }
        else {
            blockHardness = this.miningValueForMaterial(b.getType());
        }
        if (WorldListener.blb.contains((Object)b.getType())) {
            blockHardness = 2.0E10;
        }
        finalResult = blockHardness * 30.0 / this.getPlayerMiningSpeed(p);
        if (blockPower > this.getPlayerBreakingPower(p)) {
            finalResult = 1.0E8;
        }
        return finalResult;
    }
    
    public int miningValueForMaterial(final Material m) {
        switch (m) {
            case STONE: {
                return 15;
            }
            case COBBLESTONE: {
                return 20;
            }
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case ENDER_STONE:
            case QUARTZ_ORE: {
                return 60;
            }
            case DIAMOND_BLOCK:
            case EMERALD_BLOCK: {
                return 100;
            }
            case OBSIDIAN: {
                return 1000;
            }
            default: {
                return -1;
            }
        }
    }
    
    public int breakingPowerForMaterial(final Material m) {
        switch (m) {
            case STONE: {
                return 15;
            }
            case COBBLESTONE: {
                return 20;
            }
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case ENDER_STONE:
            case QUARTZ_ORE: {
                return 60;
            }
            case DIAMOND_BLOCK:
            case EMERALD_BLOCK: {
                return 100;
            }
            case OBSIDIAN: {
                return 1000;
            }
            default: {
                return 0;
            }
        }
    }
    
    @EventHandler
    public void placeBlockEvent(final BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld() && p.getLocation().distance(player.getLocation()) <= 50.0 && p != player) {
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)player).getHandle(), 0));
            }
        }
    }
    
    static {
        RESTORER = (Map)new HashMap();
        ALREADY_TELEPORTING = (List)new ArrayList();
        isCM = (Map)new HashMap();
        isSWI = (Map)new HashMap();
        miningSpeed = (Map)new HashMap();
        breakingPower = (Map)new HashMap();
        CACHED_BLOCK_ID = (Map)new HashMap();
        CACHED_BLOCK_BYTE = (Map)new HashMap();
        WorldListener.blb = (ArrayList<Material>)new ArrayList();
        WorldListener.changed_blocks = (ArrayList<Block>)new ArrayList();
    }
}
