package net.hypixel.skyblock.entity.nms;

import java.util.HashMap;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Creature;
import org.bukkit.util.EulerAngle;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.entity.EntityDrop;
import org.bukkit.event.entity.CreatureSpawnEvent;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Enderman;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.api.block.BlockFallAPI;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Material;
import org.bukkit.Effect;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderman;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.block.Block;
import java.util.List;
import java.util.ArrayList;
import net.hypixel.skyblock.entity.SEntity;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import java.util.Map;
import net.hypixel.skyblock.entity.end.EndermanStatistics;
import net.hypixel.skyblock.entity.EntityFunction;
import net.minecraft.server.v1_8_R3.EntityEnderman;

public class VoidgloomSeraph extends EntityEnderman implements SNMSEntity, EntityFunction, SlayerBoss, EndermanStatistics
{
    private static final TieredValue<Double> MAX_HEALTH_VALUES;
    private static final TieredValue<Double> DAMAGE_VALUES;
    private static final TieredValue<Double> SPEED_VALUES;
    private static final TieredValue<Double> XP_DROPS;
    public static final Map<Entity, Player> BEACON_THROW;
    public static final Map<Entity, Integer> HIT_SHIELD;
    public static final Map<Entity, Integer> HIT_SHIELD_MAX;
    public static final Map<UUID, Entity> OWNER_BOSS;
    public static final Map<Entity, Player> NUKEKUBI_TARGET;
    public static final Map<Entity, Integer> NUKEKUBI_DAMAGE;
    private final int tier;
    private final long end;
    private boolean CooldownSkill;
    private boolean CooldownSkill2;
    private boolean CooldownSkill3;
    private boolean CooldownSkill4;
    private boolean activeskull;
    private boolean activebea;
    private boolean activehs;
    private boolean CooldownSkill5;
    private boolean HeartRadi;
    private boolean isBroken;
    private SEntity hologram;
    private SEntity hologram_name;
    private final ArrayList<Entity> Ar1;
    public static final Map<UUID, List<Entity>> LivingSkulls;
    private final ArrayList<Entity> Ar2;
    private final UUID spawnerUUID;
    static UUID spawnerUUID2;
    public static final Map<Entity, Block> CACHED_BLOCK;
    public static final Map<Entity, Integer> CACHED_BLOCK_ID;
    public static final Map<Entity, Byte> CACHED_BLOCK_DATA;
    
    public VoidgloomSeraph(final Integer tier, final UUID spawnerUUID) {
        super((World)((CraftWorld)Bukkit.getPlayer(spawnerUUID).getWorld()).getHandle());
        this.Ar1 = (ArrayList<Entity>)new ArrayList();
        this.Ar2 = (ArrayList<Entity>)new ArrayList();
        this.setSize(0.6f, 2.9f);
        this.S = 1.0f;
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.goalSelector.a(8, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.tier = tier;
        this.end = System.currentTimeMillis() + 240000L;
        this.spawnerUUID = spawnerUUID;
        this.activehs = false;
        this.activebea = false;
        this.HeartRadi = false;
        this.isBroken = false;
        this.CooldownSkill = true;
        this.CooldownSkill2 = true;
        this.CooldownSkill3 = true;
        this.CooldownSkill4 = true;
        this.CooldownSkill5 = true;
        this.activeskull = false;
        VoidgloomSeraph.spawnerUUID2 = spawnerUUID;
    }
    
    public VoidgloomSeraph(final World world) {
        super(world);
        this.Ar1 = (ArrayList<Entity>)new ArrayList();
        this.Ar2 = (ArrayList<Entity>)new ArrayList();
        this.setSize(0.6f, 2.9f);
        this.S = 1.0f;
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.goalSelector.a(8, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.tier = 1;
        this.activebea = false;
        this.activehs = false;
        this.HeartRadi = false;
        this.isBroken = false;
        this.end = System.currentTimeMillis() + 240000L;
        this.spawnerUUID = UUID.randomUUID();
        this.CooldownSkill = true;
        this.CooldownSkill2 = true;
        this.CooldownSkill3 = true;
        this.CooldownSkill4 = true;
        this.CooldownSkill5 = true;
        this.activeskull = false;
        VoidgloomSeraph.spawnerUUID2 = this.spawnerUUID;
    }
    
    public static Player getPlayer() {
        return Bukkit.getPlayer(VoidgloomSeraph.spawnerUUID2);
    }
    
    public void t_() {
        super.t_();
        ((CraftEnderman)this.bukkitEntity).getHandle();
        final Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (null == player) {
            return;
        }
        VoidgloomSeraph.OWNER_BOSS.put((Object)player.getUniqueId(), (Object)this.bukkitEntity);
        if (this.bukkitEntity.getWorld() == player.getWorld() && 50.0 <= this.getBukkitEntity().getLocation().distance(player.getLocation()) && 0 == SUtil.random(0, 10) && !this.HeartRadi) {
            this.getBukkitEntity().teleport(player.getLocation());
            this.getBukkitEntity().getWorld().spigot().playEffect(this.getBukkitEntity().getLocation(), Effect.SMOKE, 0, 1, (float)SUtil.random(-1.0, 1.0), 2.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 12);
        }
        if (!this.CooldownSkill5 && !this.HeartRadi && !this.activehs) {
            this.teleportSkill((Entity)this.bukkitEntity, player);
            this.CooldownSkill5 = true;
            SUtil.delay(() -> this.CooldownSkill5 = false, SUtil.random(170, 250));
        }
        final Location locPitch = this.bukkitEntity.getLocation();
        locPitch.setPitch(0.0f);
        if (!this.HeartRadi) {
            this.bukkitEntity.teleport(locPitch);
        }
        if (Material.AIR != this.bukkitEntity.getLocation().getBlock().getType() && Material.AIR != this.bukkitEntity.getLocation().add(0.0, 1.0, 0.0).getBlock().getType()) {
            this.bukkitEntity.teleport((Entity)player);
        }
        if (System.currentTimeMillis() > this.end) {
            final Entity stand = (Entity)Repeater.BEACON_THROW2.get((Object)player.getUniqueId());
            BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), player.getWorld());
            Sputnik.RemoveEntityArray(this.Ar1);
            Sputnik.RemoveEntityArray(this.Ar2);
            if (VoidgloomSeraph.LivingSkulls.containsKey((Object)this.spawnerUUID)) {
                final List<Entity> a = (List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)this.spawnerUUID);
                Sputnik.RemoveEntityArray(a);
            }
            if (null != stand) {
                stand.remove();
            }
            User.getUser(player.getUniqueId()).failSlayerQuest();
            ((Enderman)this.bukkitEntity).remove();
            this.hologram.remove();
            return;
        }
        final Entity stand = null;
        final Entity entity = (Entity)this.bukkitEntity;
        if (1 < this.tier && this.activebea && !this.isBroken) {
            final Entity stand2 = (Entity)Repeater.BEACON_THROW2.get((Object)player.getUniqueId());
            if (null != stand2) {
                if (stand2.isDead()) {
                    BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand2), entity.getWorld());
                    if (VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand2) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand2) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand2)) {
                        ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand2)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand2), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand2), true);
                    }
                }
                if (stand2.getNearbyEntities(0.5, 0.5, 0.5).contains((Object)player)) {
                    this.isBroken = true;
                    SUtil.delay(() -> {
                        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), entity.getWorld());
                        stand.remove();
                        entity.getWorld().playSound(stand.getLocation(), Sound.ITEM_BREAK, 1.0f, 0.5f);
                        entity.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION_LARGE, 1);
                        if (VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand)) {
                            ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand), true);
                        }
                    }, 4L);
                    return;
                }
            }
        }
        if (1 < this.tier && !this.CooldownSkill && 4 == SUtil.random(0, 100) && !this.activebea && !this.activehs) {
            Sputnik.endermanCarryBlock((Enderman)this.bukkitEntity, Material.BEACON);
            this.activebea = true;
            VoidgloomSeraph.BEACON_THROW.remove((Object)entity);
            Repeater.BEACON_THROW2.remove((Object)player.getUniqueId());
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        final Entity stand = (Entity)Repeater.BEACON_THROW2.get((Object)player.getUniqueId());
                        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), entity.getWorld());
                        if (null != stand) {
                            stand.remove();
                        }
                        this.cancel();
                        return;
                    }
                    final Location l1 = entity.getLocation();
                    l1.add(entity.getLocation().getDirection().normalize().multiply(1));
                    l1.setYaw((float)SUtil.random(0, 360));
                    final ArmorStand armorStand1 = (ArmorStand)entity.getWorld().spawnEntity(l1.add(0.0, 0.3, 0.0), EntityType.ARMOR_STAND);
                    armorStand1.getEquipment().setHelmet(SItem.of(SMaterial.BEACON).getStack());
                    armorStand1.setGravity(true);
                    armorStand1.setVisible(false);
                    armorStand1.setCustomName(ChatColor.translateAlternateColorCodes('&', ""));
                    final Vector vec = armorStand1.getLocation().getDirection().normalize().multiply(1.25);
                    armorStand1.setMetadata("BeaconSkill", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    vec.setY(0.55);
                    armorStand1.setVelocity(vec);
                    Sputnik.endermanCarryBlock((Enderman)entity, Material.AIR);
                    SUtil.delay(() -> player.playSound(player.getLocation(), Sound.PORTAL_TRIGGER, 0.4f, 0.81f), 10L);
                    SUtil.delay(() -> player.playSound(player.getLocation(), "mob.guardian.elder.idle", 0.2f, 0.85f), 15L);
                    SUtil.delay(() -> player.playSound(player.getLocation(), "mob.guardian.elder.idle", 0.2f, 0.85f), 40L);
                    SUtil.delay(() -> player.playSound(player.getLocation(), "mob.guardian.elder.idle", 0.2f, 0.85f), 40L);
                    VoidgloomSeraph.this.Ar1.add((Object)armorStand1);
                    Repeater.BEACON_OWNER.remove((Object)armorStand1);
                    Repeater.BEACON_OWNER.put((Object)armorStand1, (Object)player);
                    VoidgloomSeraph.a(armorStand1);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 80L);
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        final Entity stand = (Entity)Repeater.BEACON_THROW2.get((Object)player.getUniqueId());
                        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), entity.getWorld());
                        if (VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand)) {
                            ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand), true);
                        }
                        if (null != stand) {
                            stand.remove();
                        }
                        this.cancel();
                        return;
                    }
                    final Entity stand = (Entity)Repeater.BEACON_THROW2.get((Object)player.getUniqueId());
                    VoidgloomSeraph.this.Ar1.add((Object)stand);
                    if (!VoidgloomSeraph.this.isBroken) {
                        User.getUser(player.getUniqueId()).damage(player.getMaxHealth() * 3.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, entity);
                        player.damage(0.1, entity);
                        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), entity.getWorld());
                        if (null != stand) {
                            stand.remove();
                        }
                        VoidgloomSeraph.this.activebea = false;
                        entity.getWorld().playSound(stand.getLocation(), Sound.ITEM_BREAK, 1.0f, 0.5f);
                        entity.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION_LARGE, 1);
                        if (VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand)) {
                            ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand), true);
                        }
                    }
                    else {
                        VoidgloomSeraph.this.isBroken = false;
                        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), entity.getWorld());
                        if (VoidgloomSeraph.CACHED_BLOCK.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_ID.containsKey((Object)stand) && VoidgloomSeraph.CACHED_BLOCK_DATA.containsKey((Object)stand)) {
                            ((Block)VoidgloomSeraph.CACHED_BLOCK.get((Object)stand)).getLocation().getBlock().setTypeIdAndData((int)VoidgloomSeraph.CACHED_BLOCK_ID.get((Object)stand), (byte)VoidgloomSeraph.CACHED_BLOCK_DATA.get((Object)stand), true);
                        }
                        stand.remove();
                        VoidgloomSeraph.this.activebea = false;
                    }
                    VoidgloomSeraph.this.activebea = false;
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 185L);
            new BukkitRunnable() {
                public void run() {
                    if (VoidgloomSeraph.this.CooldownSkill) {
                        VoidgloomSeraph.this.CooldownSkill = false;
                    }
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 400L);
        }
        int amounths = 30;
        if (2 == this.tier) {
            amounths = 60;
        }
        if (3 == this.tier) {
            amounths = 120;
        }
        if (4 <= this.tier) {
            amounths = 200;
        }
        if (1 <= this.tier && !this.CooldownSkill2 && 2 == SUtil.random(0, 100) && !this.activehs && !this.HeartRadi && !this.activebea) {
            this.CooldownSkill2 = true;
            this.activehs = true;
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity, (Object)amounths);
            VoidgloomSeraph.HIT_SHIELD_MAX.put((Object)entity, (Object)amounths);
            EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
        }
        if (!VoidgloomSeraph.HIT_SHIELD.containsKey((Object)entity) && this.activehs) {
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity, (Object)amounths);
            VoidgloomSeraph.HIT_SHIELD_MAX.put((Object)entity, (Object)amounths);
        }
        if (this.activehs && 0 >= (int)VoidgloomSeraph.HIT_SHIELD.get((Object)entity) && !this.HeartRadi && !this.activebea) {
            this.CooldownSkill2 = true;
            VoidgloomSeraph.HIT_SHIELD.remove((Object)entity);
            VoidgloomSeraph.HIT_SHIELD_MAX.remove((Object)entity);
            player.playSound(player.getLocation(), Sound.ZOMBIE_REMEDY, 1.0f, 1.0f);
            EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)75);
            this.activehs = false;
            new BukkitRunnable() {
                public void run() {
                    if (VoidgloomSeraph.this.CooldownSkill2) {
                        VoidgloomSeraph.this.CooldownSkill2 = false;
                    }
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 850L);
        }
        if (this.bukkitEntity.getWorld() == player.getWorld() && 20.0 < this.getBukkitEntity().getLocation().distance(player.getLocation()) && this.HeartRadi) {
            User.getUser(player.getUniqueId()).damage(player.getMaxHealth() * 90.0 / 100.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, entity);
            player.damage(1.0E-4, entity);
        }
        final net.minecraft.server.v1_8_R3.Entity entity_ = this.getBukkitEntity().getHandle();
        final double height = entity_.getBoundingBox().e - entity_.getBoundingBox().b;
        this.hologram_name.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, height, 0.0));
        this.hologram_name.getEntity().setCustomName(Sputnik.trans(Sputnik.entityNameTag((LivingEntity)this.getBukkitEntity(), Sputnik.buildcustomString(this.getEntityName(), 0, true))));
        this.hologram.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, 3.2, 0.0));
        this.hologram.getEntity().setCustomName((Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
        ((Enderman)this.getBukkitEntity()).setTarget((LivingEntity)player);
        if (4 <= this.tier && !this.CooldownSkill3 && 10 >= SUtil.random(0, 100) && !this.HeartRadi && !this.activehs) {
            EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
            this.HeartRadi = true;
            this.CooldownSkill3 = true;
            final double x = entity.getLocation().getX();
            final double y = entity.getLocation().getY();
            final double z = entity.getLocation().getZ();
            final Location l = new Location(entity.getWorld(), x, y, z);
            entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_HUGE, 3);
            entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_HUGE, 3);
            final ArmorStand stand_sit = (ArmorStand)entity.getWorld().spawn(l, (Class)ArmorStand.class);
            stand_sit.setVisible(false);
            stand_sit.setGravity(true);
            stand_sit.setMarker(true);
            stand_sit.setPassenger(entity);
            this.Ar1.add((Object)stand_sit);
            this.Ar2.add((Object)stand_sit);
            new BukkitRunnable() {
                public void run() {
                    if (stand_sit.isDead()) {
                        this.cancel();
                        return;
                    }
                    stand_sit.setPassenger(entity);
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
            final ArmorStand stand3 = (ArmorStand)stand_sit.getWorld().spawn(stand_sit.getLocation().add(0.0, 0.0, 0.0), (Class)ArmorStand.class);
            stand3.setVisible(false);
            stand3.setGravity(false);
            stand3.setMarker(true);
            stand3.setMetadata("HeartRadiAS", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            this.Ar1.add((Object)stand3);
            this.Ar2.add((Object)stand3);
            final ArmorStand stand4 = (ArmorStand)entity.getWorld().spawn(stand3.getLocation().add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
            stand4.setVisible(false);
            stand4.setGravity(false);
            stand4.setMarker(true);
            this.Ar1.add((Object)stand4);
            this.Ar2.add((Object)stand4);
            stand4.setMetadata("HeartRadiAS", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            final ArmorStand stand5 = (ArmorStand)entity.getWorld().spawn(stand4.getLocation().add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
            stand5.setVisible(false);
            stand5.setGravity(false);
            stand5.setMarker(true);
            this.Ar1.add((Object)stand5);
            this.Ar2.add((Object)stand5);
            stand5.setMetadata("HeartRadiAS", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            final Location l2 = l;
            l2.setYaw(l.getYaw() + 90.0f);
            final ArmorStand stand1_ = (ArmorStand)stand_sit.getWorld().spawn(l2, (Class)ArmorStand.class);
            stand1_.setVisible(false);
            stand1_.setGravity(false);
            stand1_.setMarker(true);
            this.Ar1.add((Object)stand1_);
            this.Ar2.add((Object)stand1_);
            stand4.setMetadata("HeartRadiAS", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            final ArmorStand stand6 = (ArmorStand)entity.getWorld().spawn(stand1_.getLocation().add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
            stand6.setVisible(false);
            stand6.setGravity(false);
            stand6.setMarker(true);
            this.Ar1.add((Object)stand6);
            this.Ar2.add((Object)stand6);
            stand6.setMetadata("HeartRadiAS", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            final ArmorStand stand7 = (ArmorStand)entity.getWorld().spawn(stand6.getLocation().add(0.0, 1.5, 0.0), (Class)ArmorStand.class);
            stand7.setVisible(false);
            stand7.setGravity(false);
            stand7.setMarker(true);
            this.Ar1.add((Object)stand7);
            this.Ar2.add((Object)stand7);
            new BukkitRunnable() {
                public void run() {
                    player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1.0f, 0.5f);
                    Sputnik.playFuckingSoundOfVoidgloomThatTookForeverToMake(player, (Entity)stand_sit);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 15L);
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        Sputnik.RemoveEntityArray(VoidgloomSeraph.this.Ar2);
                        this.cancel();
                        return;
                    }
                    Sputnik.entityBeam(stand3, stand3.getLocation(), player, entity);
                    Sputnik.entityBeam(stand7, stand3.getLocation(), player, entity);
                    Sputnik.entityBeam(stand6, stand3.getLocation(), player, entity);
                    Sputnik.entityBeam(stand1_, stand3.getLocation(), player, entity);
                    Sputnik.entityBeam(stand5, stand3.getLocation(), player, entity);
                    Sputnik.entityBeam(stand4, stand3.getLocation(), player, entity);
                    new BukkitRunnable() {
                        public void run() {
                            if (stand7.isDead()) {
                                this.cancel();
                                return;
                            }
                            if (entity.isDead()) {
                                Sputnik.IsInsideTheBeam.remove((Object)player.getUniqueId());
                                this.cancel();
                                return;
                            }
                            if (Sputnik.IsInsideTheBeam.containsKey((Object)player.getUniqueId()) && (boolean)Sputnik.IsInsideTheBeam.get((Object)player.getUniqueId())) {
                                final Entity entity = (Entity)VoidgloomSeraph.OWNER_BOSS.get((Object)player.getUniqueId());
                                if (null == entity) {
                                    this.cancel();
                                    return;
                                }
                                User.getUser(player.getUniqueId()).damage(player.getMaxHealth() * 25.0 / 100.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, entity);
                                Sputnik.IsInsideTheBeam.remove((Object)player.getUniqueId());
                            }
                        }
                    }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 25L);
            new BukkitRunnable() {
                public void run() {
                    VoidgloomSeraph.this.HeartRadi = false;
                    Sputnik.RemoveEntityArray(VoidgloomSeraph.this.Ar2);
                    EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)75);
                    new BukkitRunnable() {
                        public void run() {
                            if (VoidgloomSeraph.this.CooldownSkill3) {
                                VoidgloomSeraph.this.CooldownSkill3 = false;
                            }
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 700L);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 200L);
        }
        if (this.activeskull) {
            int damagefin = 800;
            if (4 <= this.tier) {
                damagefin = 2000;
            }
            if (VoidgloomSeraph.LivingSkulls.containsKey((Object)player.getUniqueId()) && this.activeskull) {
                damageUpdate(damagefin * (2 * (((List)VoidgloomSeraph.LivingSkulls.get((Object)player.getUniqueId())).size() + 1)));
            }
        }
        if (VoidgloomSeraph.LivingSkulls.containsKey((Object)player.getUniqueId()) && this.activeskull) {
            this.activeskull = (0 < ((List)VoidgloomSeraph.LivingSkulls.get((Object)player.getUniqueId())).size());
        }
        if (3 <= this.tier && !this.CooldownSkill4 && 2 == SUtil.random(0, 100) && !this.activehs && !this.activeskull) {
            VoidgloomSeraph.LivingSkulls.remove((Object)player.getUniqueId());
            this.CooldownSkill4 = true;
            this.activeskull = true;
            spawnNukekubi(entity, player, 1, 3);
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        if (VoidgloomSeraph.LivingSkulls.containsKey((Object)player.getUniqueId())) {
                            Sputnik.RemoveEntityArray((List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)player.getUniqueId()));
                        }
                        this.cancel();
                        return;
                    }
                    if (VoidgloomSeraph.this.activeskull) {
                        VoidgloomSeraph.spawnNukekubi(entity, player, 1, 2);
                    }
                    else {
                        VoidgloomSeraph.updateSkill((List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)player.getUniqueId()));
                        new BukkitRunnable() {
                            public void run() {
                                if (VoidgloomSeraph.this.CooldownSkill4) {
                                    VoidgloomSeraph.this.CooldownSkill4 = false;
                                }
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 400L);
                    }
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 300L);
        }
    }
    
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Voidgloom", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("BOSS_OWNER_" + Bukkit.getPlayer(this.getSpawnerUUID()).getUniqueId().toString(), (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        VoidgloomSeraph.LivingSkulls.put((Object)this.spawnerUUID, (Object)new ArrayList());
        SUtil.delay(() -> entity.removeMetadata("GiantSword", (Plugin)SkyBlock.getPlugin()), 20L);
        SUtil.delay(() -> {
            int amounths = 30;
            if (2 == this.tier) {
                amounths = 60;
            }
            if (3 == this.tier) {
                amounths = 120;
            }
            if (4 <= this.tier) {
                amounths = 200;
            }
            this.CooldownSkill2 = true;
            this.activehs = true;
            VoidgloomSeraph.HIT_SHIELD.put((Object)entity, (Object)amounths);
            VoidgloomSeraph.HIT_SHIELD_MAX.put((Object)entity, (Object)amounths);
            EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
        }, 0L);
        SUtil.delay(() -> this.CooldownSkill5 = false, 70L);
        this.playBossParticle_1((Entity)entity);
        this.playBossParticle_2((Entity)entity);
        this.playShieldParticle((Entity)entity);
        SUtil.delay(() -> this.CooldownSkill = false, 150L);
        this.CooldownSkill2 = false;
        SUtil.delay(() -> this.CooldownSkill3 = false, 100L);
        SUtil.delay(() -> this.CooldownSkill4 = false, 300L);
        final net.minecraft.server.v1_8_R3.Entity entity_ = this.getBukkitEntity().getHandle();
        this.hologram = new SEntity(entity.getLocation().add(0.0, 3.2, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        VoidgloomSeraph.BEACON_THROW.put((Object)entity, (Object)Bukkit.getPlayer(this.spawnerUUID));
        ((ArmorStand)this.hologram.getEntity()).setVisible(false);
        ((ArmorStand)this.hologram.getEntity()).setGravity(false);
        this.hologram.getEntity().setCustomNameVisible(true);
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final net.minecraft.server.v1_8_R3.Entity e = this.getBukkitEntity().getHandle();
        final double height_ = e.getBoundingBox().e - e.getBoundingBox().b;
        this.hologram_name = new SEntity(entity.getLocation().add(0.0, height_, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        ((ArmorStand)this.hologram_name.getEntity()).setVisible(false);
        ((ArmorStand)this.hologram_name.getEntity()).setGravity(false);
        this.hologram_name.getEntity().setCustomNameVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    VoidgloomSeraph.this.hologram.remove();
                    this.cancel();
                    SUtil.delay(() -> VoidgloomSeraph.this.hologram_name.remove(), 20L);
                    Sputnik.RemoveEntityArray((List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)VoidgloomSeraph.this.getSpawnerUUID()));
                    Repeater.BEACON_THROW2.remove((Object)VoidgloomSeraph.this.getSpawnerUUID());
                    VoidgloomSeraph.OWNER_BOSS.remove((Object)VoidgloomSeraph.this.getSpawnerUUID());
                    if (null != VoidgloomSeraph.getPlayer()) {
                        VoidgloomSeraph.destroyArmorStandWithUUID(VoidgloomSeraph.this.getSpawnerUUID(), VoidgloomSeraph.getPlayer().getWorld());
                    }
                    VoidgloomSeraph.BEACON_THROW.remove((Object)entity);
                    VoidgloomSeraph.HIT_SHIELD.remove((Object)entity);
                    VoidgloomSeraph.HIT_SHIELD_MAX.remove((Object)entity);
                    Repeater.BEACON_OWNER.remove((Object)entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    Sputnik.RemoveEntityArray(VoidgloomSeraph.this.Ar2);
                    if (VoidgloomSeraph.LivingSkulls.containsKey((Object)VoidgloomSeraph.this.getSpawnerUUID())) {
                        final List<Entity> a = (List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)VoidgloomSeraph.this.getSpawnerUUID());
                        Sputnik.RemoveEntityArray(a);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (null == VoidgloomSeraph.getPlayer()) {
                    this.cancel();
                    entity.remove();
                    return;
                }
                if (!VoidgloomSeraph.this.HeartRadi) {
                    VoidgloomSeraph.getPlayer().damage((double)VoidgloomSeraph.DAMAGE_VALUES.getByNumber(VoidgloomSeraph.this.tier), (Entity)entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
    
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        final Entity en = (Entity)sEntity.getEntity();
        final Vector v = new Vector(0, 0, 0);
        SUtil.delay(() -> en.setVelocity(v), 1L);
    }
    
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        this.hologram.remove();
        SUtil.delay(() -> this.hologram_name.remove(), 20L);
        final Entity stand = (Entity)Repeater.BEACON_THROW2.get((Object)damager.getUniqueId());
        BlockFallAPI.removeBlock((EntityFallingBlock)Repeater.BEACON.get((Object)stand), killed.getWorld());
        Sputnik.RemoveEntityArray(this.Ar1);
        Sputnik.RemoveEntityArray(this.Ar2);
        if (VoidgloomSeraph.LivingSkulls.containsKey((Object)this.spawnerUUID)) {
            final List<Entity> a = (List<Entity>)VoidgloomSeraph.LivingSkulls.get((Object)this.spawnerUUID);
            Sputnik.RemoveEntityArray(a);
        }
        if (null != stand) {
            stand.remove();
        }
    }
    
    public String getEntityName() {
        return (Object)ChatColor.RED + "\u2620 " + (Object)ChatColor.AQUA + "Voidgloom Seraph";
    }
    
    public double getEntityMaxHealth() {
        return VoidgloomSeraph.MAX_HEALTH_VALUES.getByNumber(this.tier);
    }
    
    public double getDamageDealt() {
        return VoidgloomSeraph.DAMAGE_VALUES.getByNumber(this.tier);
    }
    
    public double getMovementSpeed() {
        return VoidgloomSeraph.SPEED_VALUES.getByNumber(this.tier);
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((net.minecraft.server.v1_8_R3.Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
    
    public List<EntityDrop> drops() {
        final List<EntityDrop> drops = (List<EntityDrop>)new ArrayList();
        int revFlesh = SUtil.random(1, 3);
        if (2 == this.tier) {
            revFlesh = SUtil.random(9, 18);
        }
        if (3 == this.tier) {
            revFlesh = SUtil.random(30, 50);
        }
        if (4 == this.tier) {
            revFlesh = SUtil.random(50, 64);
        }
        drops.add((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.NULL_SPHERE).getStack(), revFlesh), EntityDropType.GUARANTEED, 1.0));
        if (2 <= this.tier) {
            drops.add((Object)new EntityDrop(SMaterial.SUMMONING_EYE, EntityDropType.EXTRAORDINARILY_RARE, 0.0033333333333333335, Bukkit.getPlayer(this.getSpawnerUUID())));
        }
        if (3 <= this.tier) {
            drops.add((Object)new EntityDrop(SMaterial.HIDDEN_ETHERWARP_MERGER, EntityDropType.EXTRAORDINARILY_RARE, 5.0E-4, Bukkit.getPlayer(this.getSpawnerUUID())));
        }
        if (4 <= this.tier) {
            final SItem endBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            endBook.addEnchantment(EnchantmentType.ENDER_SLAYER, 15);
            drops.add((Object)new EntityDrop(endBook.getStack(), EntityDropType.CRAZY_RARE, 0.002, Bukkit.getPlayer(this.getSpawnerUUID())));
            final SItem legiBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            legiBook.addEnchantment(EnchantmentType.LEGION, SUtil.random(1, 2));
            drops.add((Object)new EntityDrop(legiBook.getStack(), EntityDropType.INSANE_RARE, 0.00125, Bukkit.getPlayer(this.getSpawnerUUID())));
            final SItem fatalBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            fatalBook.addEnchantment(EnchantmentType.FATAL_TEMPO, 1);
            drops.add((Object)new EntityDrop(fatalBook.getStack(), EntityDropType.CRAZY_RARE, 4.0E-4, Bukkit.getPlayer(this.getSpawnerUUID())));
            drops.add((Object)new EntityDrop(SMaterial.HIDDEN_ETHERWARP_CONDUIT, EntityDropType.CRAZY_RARE, 0.002, Bukkit.getPlayer(this.getSpawnerUUID())));
            drops.add((Object)new EntityDrop(SMaterial.JUDGEMENT_CORE, EntityDropType.CRAZY_RARE, 0.001, Bukkit.getPlayer(this.getSpawnerUUID())));
        }
        return drops;
    }
    
    public double getXPDropped() {
        return VoidgloomSeraph.XP_DROPS.getByNumber(this.tier);
    }
    
    public boolean isBaby() {
        return false;
    }
    
    public boolean isAngry() {
        return true;
    }
    
    public UUID getSpawnerUUID() {
        return this.spawnerUUID;
    }
    
    public static void spawnNukekubi(final Entity e, final Player p, final Integer damage, final Integer spawnCouples) {
        if (2 >= spawnCouples) {
            final Location loc1_ = p.getLocation();
            loc1_.setYaw(loc1_.getYaw() + SUtil.random(0, 360));
            final Location loc2_ = p.getLocation();
            loc2_.setYaw(loc1_.getYaw() + SUtil.random(0, 360));
            final Location loc1 = loc1_.add(loc1_.getDirection().multiply(5));
            final Location loc2 = loc2_.add(loc2_.getDirection().multiply(-5));
            moveHeadAround((Entity)spawnHeads(e, loc1, p), p, damage);
            moveHeadAround((Entity)spawnHeads(e, loc2, p), p, damage);
        }
        else {
            for (int i = 0; i < spawnCouples; ++i) {
                if (1 == SUtil.random(1, 2)) {
                    final Location loc1_2 = p.getLocation();
                    loc1_2.setYaw(loc1_2.getYaw() + SUtil.random(0, 360));
                    final Location loc2_2 = p.getLocation();
                    loc2_2.setYaw(loc1_2.getYaw() + SUtil.random(0, 360));
                    final Location loc3 = loc1_2.add(loc1_2.getDirection().multiply(5));
                    final Location loc4 = loc2_2.add(loc2_2.getDirection().multiply(-5));
                    moveHeadAround((Entity)spawnHeads(e, loc3, p), p, damage);
                }
                else {
                    final Location loc1_2 = p.getLocation();
                    loc1_2.setYaw(loc1_2.getYaw() + SUtil.random(0, 360));
                    final Location loc2_2 = p.getLocation();
                    loc2_2.setYaw(loc1_2.getYaw() + SUtil.random(0, 360));
                    final Location loc3 = loc1_2.add(loc1_2.getDirection().multiply(5));
                    final Location loc4 = loc2_2.add(loc2_2.getDirection().multiply(-5));
                    moveHeadAround((Entity)spawnHeads(e, loc4, p), p, damage);
                }
            }
        }
    }
    
    public static void destroyArmorStandWithUUID(final UUID uuid, final org.bukkit.World w) {
        final String uuidString = uuid.toString() + "_NUKEKUBI";
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata(uuidString)) {
                e.remove();
            }
        }
    }
    
    public static LivingEntity spawnHeads(final Entity e, final Location loc, final Player p) {
        final ArmorStand entity = (ArmorStand)loc.getWorld().spawnEntity(e.getLocation().add(e.getLocation().getDirection().normalize().multiply(-1)), EntityType.ARMOR_STAND);
        loc.setY(loc.getY() + SUtil.random(0.0, 0.6));
        entity.setCustomName(Sputnik.trans("&c\u2620 &bVoidgloom Seraph"));
        entity.setVisible(false);
        entity.setGravity(false);
        entity.getEquipment().setHelmet(SItem.of(SMaterial.NUKEKUBI).getStack());
        entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
        entity.setMetadata("Nukekubi", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata(p.getUniqueId().toString() + "_NUKEKUBI", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        VoidgloomSeraph.NUKEKUBI_TARGET.put((Object)entity, (Object)p);
        if (!VoidgloomSeraph.LivingSkulls.containsKey((Object)p.getUniqueId())) {
            VoidgloomSeraph.LivingSkulls.put((Object)p.getUniqueId(), (Object)new ArrayList());
        }
        ((List)VoidgloomSeraph.LivingSkulls.get((Object)p.getUniqueId())).add((Object)entity);
        moveToLoc((Entity)entity, loc, 3, 0, 1.0);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.hasMetadata("Nukekubi")) {
                    final Location l = entity.getLocation().setDirection(p.getLocation().toVector().subtract(entity.getLocation().toVector()));
                    entity.teleport(l);
                    double x = 0.0;
                    final double y = 0.0;
                    final double z = 0.0;
                    x = Math.toRadians((double)l.getPitch());
                    entity.setHeadPose(new EulerAngle(x, 0.0, 0.0));
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.1, 0.0), Effect.WITCH_MAGIC, 1);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.2, 0.0), Effect.WITCH_MAGIC, 1);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.2, 0.0), Effect.WITCH_MAGIC, 1);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.4, 0.0), Effect.WITCH_MAGIC, 1);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.4, 0.0), Effect.WITCH_MAGIC, 1);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        return (LivingEntity)entity;
    }
    
    public static void moveToLoc(final Entity e, final Location target, final int tickingRad, final int firstTickRad, final double jump) {
        final Location l = e.getLocation().setDirection(target.toVector().subtract(e.getLocation().toVector()));
        new BukkitRunnable() {
            public void run() {
                final Vector teleportTo = l.getDirection().normalize().multiply(jump);
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (e.getWorld().getNearbyEntities(target, 1.5, 1.5, 1.5).contains((Object)e)) {
                    this.cancel();
                    return;
                }
                e.teleport(e.getLocation().add(teleportTo).multiply(jump));
                e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.1, 0.0), Effect.FLAME, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FLAME, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FLAME, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), (long)firstTickRad, (long)tickingRad);
    }
    
    public static void moveHeadAround(final Entity head, final Player p, final Integer damage) {
        new BukkitRunnable() {
            public void run() {
                if (head.isDead()) {
                    this.cancel();
                    return;
                }
                int i1 = 0;
                if (VoidgloomSeraph.NUKEKUBI_DAMAGE.containsKey((Object)p)) {
                    i1 = (int)VoidgloomSeraph.NUKEKUBI_DAMAGE.get((Object)p);
                }
                if (head.getWorld().equals(p.getWorld())) {
                    Sputnik.drawLineforMovingPoints(head.getLocation().add(head.getLocation().getDirection().multiply(0.1)).add(0.0, 2.0, 0.0), p.getLocation().add(0.0, 1.8, 0.0), 20.0, p, damage, head);
                    Sputnik.dmgc(i1, p, head);
                }
                else {
                    head.remove();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L);
    }
    
    public static void damageUpdate(final double formula) {
        VoidgloomSeraph.NUKEKUBI_DAMAGE.put((Object)getPlayer(), (Object)(int)formula);
    }
    
    public static void targetSelect(final Entity entity, final LivingEntity target) {
        if (!(entity instanceof Creature)) {
            return;
        }
        ((Creature)entity).setTarget(target);
    }
    
    public static void updateSkill(final List<Entity> list) {
        for (final Entity e : list) {
            VoidgloomSeraph.NUKEKUBI_TARGET.remove((Object)e);
        }
    }
    
    public static BukkitTask a(final ArmorStand entity) {
        return new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final org.bukkit.World world = entity.getWorld();
                if (entity.hasMetadata("BeaconSkill") && entity.isOnGround()) {
                    entity.remove();
                    if (null != VoidgloomSeraph.getPlayer()) {
                        VoidgloomSeraph.getPlayer().getWorld().playSound(VoidgloomSeraph.getPlayer().getLocation(), Sound.ITEM_BREAK, 0.5f, 0.67f);
                    }
                    final Location loc = entity.getLocation().getBlock().getLocation().add(0.5, 0.0, 0.5);
                    final ArmorStand armorStand2 = (ArmorStand)entity.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                    VoidgloomSeraph.CACHED_BLOCK.put((Object)armorStand2, (Object)loc.getBlock());
                    VoidgloomSeraph.CACHED_BLOCK_ID.put((Object)armorStand2, (Object)loc.getBlock().getTypeId());
                    VoidgloomSeraph.CACHED_BLOCK_DATA.put((Object)armorStand2, (Object)loc.getBlock().getData());
                    armorStand2.setGravity(true);
                    armorStand2.setVisible(false);
                    armorStand2.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c\u2620 &fTEST"));
                    armorStand2.setMetadata("BeaconSkill2", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    loc.getBlock().setType(Material.BARRIER);
                    Repeater.BEACON_THROW2.put((Object)((Player)Repeater.BEACON_OWNER.get((Object)entity)).getUniqueId(), (Object)armorStand2);
                    VoidgloomSeraph.b((Entity)armorStand2);
                    Repeater.BEACON.put((Object)armorStand2, (Object)BlockFallAPI.sendBlockDestroyWithSignal(loc, Material.BEACON, (byte)0, world));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void b(final Entity armorStand2) {
        new BukkitRunnable() {
            public void run() {
                if (armorStand2.isDead()) {
                    this.cancel();
                    return;
                }
                final org.bukkit.World world = armorStand2.getWorld();
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
                world.playEffect(armorStand2.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 3);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void teleportSkill(final Entity e, final Player p) {
        final int LOR = SUtil.random(0, 1);
        new BukkitRunnable() {
            int cout = 0;
            float addedYaw = p.getLocation().getYaw();
            
            public void run() {
                if (7 <= this.cout) {
                    p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1.0f, 1.0f);
                    this.cancel();
                    return;
                }
                final Location a = p.getLocation();
                if (0 == LOR) {
                    this.addedYaw += 19.0f;
                }
                else {
                    this.addedYaw -= 19.0f;
                }
                a.setPitch(0.0f);
                a.setYaw(this.addedYaw);
                a.add(a.getDirection().normalize().multiply(1.7));
                a.setY(e.getLocation().getY());
                final Location tpl = a.clone();
                tpl.setYaw(e.getLocation().getYaw());
                e.teleport(tpl);
                VoidgloomSeraph.this.dP(a);
                a.getWorld().playSound(a, Sound.ENDERMAN_TELEPORT, 0.2f, 1.0f);
                ++this.cout;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void drawPointerAt(final Location loc) {
        loc.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        loc.getWorld().spigot().playEffect(loc, Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
        loc.getWorld().spigot().playEffect(loc, Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
        loc.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
    }
    
    public void dP(final Location loc) {
        this.drawPointerAt(loc.clone().add(0.0, 0.9, 0.0));
        this.drawPointerAt(loc.clone().add(0.0, 1.5, 0.0));
    }
    
    public void playShieldParticle(final Entity e) {
        new BukkitRunnable() {
            float cout = e.getLocation().getYaw();
            
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                final Location loc = e.getLocation();
                loc.setYaw(this.cout);
                loc.setPitch(0.0f);
                loc.add(loc.getDirection().normalize().multiply(0.8));
                if (VoidgloomSeraph.HIT_SHIELD.containsKey((Object)e)) {
                    final int hitshield = (int)VoidgloomSeraph.HIT_SHIELD.get((Object)e);
                    final int hitshieldmax = (int)VoidgloomSeraph.HIT_SHIELD_MAX.get((Object)e);
                    int stage = 3;
                    if (hitshield <= hitshieldmax / 2 && hitshield > hitshieldmax * 25 / 100) {
                        stage = 2;
                    }
                    else if (hitshield <= hitshieldmax * 25 / 100 && 1 != hitshield) {
                        stage = 1;
                    }
                    else if (1 == hitshield) {
                        stage = 1;
                    }
                    if (0 < (int)VoidgloomSeraph.HIT_SHIELD.get((Object)e)) {
                        e.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        e.getWorld().spigot().playEffect(loc.clone().add(0.0, 0.6, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        e.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        e.getWorld().spigot().playEffect(loc.clone().add(0.0, 0.6, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        if (2 <= stage) {
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.2, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.2, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        }
                        if (3 == stage) {
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 2.4, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 2.4, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.8, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                            e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.8, 0.0), Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                        }
                    }
                }
                this.cout += 18.0f;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void playBossParticle_1(final Entity e) {
        new BukkitRunnable() {
            float cout = e.getLocation().getYaw();
            
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                final Location loc = e.getLocation().clone().add(0.0, 0.3, 0.0);
                loc.setYaw(this.cout);
                loc.setPitch(0.0f);
                loc.add(loc.getDirection().normalize().multiply(0.4));
                e.getWorld().spigot().playEffect(loc, Effect.CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                e.getWorld().spigot().playEffect(loc, Effect.CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                this.cout += 9.0f;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void playBossParticle_2(final Entity e) {
        new BukkitRunnable() {
            float cout = e.getLocation().getYaw();
            
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                final Location loc = e.getLocation().clone().add(0.0, 0.3, 0.0);
                loc.setYaw(this.cout);
                loc.setPitch(0.0f);
                loc.add(loc.getDirection().normalize().multiply(0.4));
                e.getWorld().spigot().playEffect(loc.clone().add(0.0, 0.6, 0.0), Effect.CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                e.getWorld().spigot().playEffect(loc.clone().add(0.0, 0.6, 0.0), Effect.CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                this.cout -= 9.0f;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public int getTier() {
        return this.tier;
    }
    
    static {
        MAX_HEALTH_VALUES = new TieredValue<Double>(3.0E7, 1.5E8, 6.66E8, 1.0E9);
        DAMAGE_VALUES = new TieredValue<Double>(120000.0, 1000000.0, 1200000.0, 1500000.0);
        SPEED_VALUES = new TieredValue<Double>(0.2, 0.2, 0.2, 0.2);
        XP_DROPS = new TieredValue<Double>(750.0, 1600.0, 2900.4, 10900.0);
        BEACON_THROW = (Map)new HashMap();
        HIT_SHIELD = (Map)new HashMap();
        HIT_SHIELD_MAX = (Map)new HashMap();
        OWNER_BOSS = (Map)new HashMap();
        NUKEKUBI_TARGET = (Map)new HashMap();
        NUKEKUBI_DAMAGE = (Map)new HashMap();
        LivingSkulls = (Map)new HashMap();
        CACHED_BLOCK = (Map)new HashMap();
        CACHED_BLOCK_ID = (Map)new HashMap();
        CACHED_BLOCK_DATA = (Map)new HashMap();
    }
}
