package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.Sound;
import net.hypixel.skyblock.entity.SEntityEquipment;
import org.bukkit.entity.Item;
import net.hypixel.skyblock.util.MagicFlowerPot;
import org.bukkit.World;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.texture.ItemTexture;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Effect;
import org.bukkit.Material;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.ArmorStand;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import net.hypixel.skyblock.gui.SkySimBrainCell;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.GameMode;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class TerracottaSadan extends BaseZombie
{
    private double y;
    
    public TerracottaSadan() {
        this.y = 0.85;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&6&lTerracotta");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.4E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 20000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        ((CraftZombie)entity).setBaby(false);
        Sputnik.applyPacketNPC((Entity)entity, "Ethelian", null, false);
        SUtil.delay(() -> entity.getEquipment().setItemInHand(SItem.of(SMaterial.FLOWER_OF_TRUTH).getStack()), 10L);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)30);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("t_sadan", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
                if (target != null && entity.getTicksLived() > 10) {
                    if (target.getWorld() != entity.getWorld()) {
                        return;
                    }
                    if (target.getLocation().distance(entity.getLocation()) >= 5.0) {
                        TerracottaSadan.this.throwRose(entity);
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 30L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                Sputnik.applyPacketNPC((Entity)entity, "Ethelian", null, false);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1000L);
        final SkySimBrainCell sbc = SkySimBrainCell.loadFromDB("localhost:27786/skysim/artifical_intelligence/cloud/");
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.MELEE);
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.MOVEMENT);
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.ABILITY_USAGE);
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.BOW_ATTACK);
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.ENTITY_TRACKER);
        sbc.accessAIFrom(SkySimBrainCell.BrainCellFor.ATTACK_PLAYER);
        SkySimBrainCell.applyAIToNMSPlayer(this, 140, sbc);
        new BukkitRunnable() {
            Location loc = entity.getLocation();
            final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
            
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                this.loc.setY(0.0);
                this.nms.setSprinting(false);
                final Location loc2 = entity.getLocation();
                loc2.setY(0.0);
                if (entity.hasMetadata("frozen")) {
                    return;
                }
                if (((CraftZombie)entity).getTarget() == null) {
                    return;
                }
                if (((CraftZombie)entity).getTarget().getWorld() != entity.getWorld()) {
                    return;
                }
                if (((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation()) <= 4.0) {
                    return;
                }
                if (this.loc.distance(loc2) >= 0.2) {
                    this.nms.setSprinting(true);
                    if (entity.isOnGround() && this.loc.distance(loc2) >= 0.5) {
                        double motY = 0.4199999868869782;
                        double motX = 0.0;
                        double motZ = 0.0;
                        if (this.nms.hasEffect(MobEffectList.JUMP)) {
                            motY += (this.nms.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.2f;
                        }
                        if (this.nms.isSprinting()) {
                            final float f = this.nms.yaw * 0.01745329f;
                            motX -= MathHelper.sin(f) * 0.6f;
                            motZ += MathHelper.cos(f) * 0.6f;
                        }
                        entity.setVelocity(new Vector(motX, motY, motZ));
                    }
                    this.loc = entity.getLocation().clone();
                    return;
                }
                this.nms.setSprinting(false);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 7L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target = (Player)entities;
                    if (target.getGameMode() == GameMode.CREATIVE) {
                        continue;
                    }
                    if (target.getGameMode() == GameMode.SPECTATOR) {
                        continue;
                    }
                    if (target.hasMetadata("NPC")) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target.getLocation().toVector().subtract(target.getLocation().toVector())));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    public void t(final ArmorStand respawnAnchor) {
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        Sputnik.zero(killed);
        Location l1 = killed.getLocation();
        if (l1.getBlock().getType() != Material.AIR) {
            final double x = l1.getBlockX();
            final double y = l1.getBlockY();
            final double z = l1.getBlockZ();
            Location l2 = new Location(l1.getWorld(), 194.0, y, z);
            if (l2.getBlock().getType() != Material.AIR) {
                l2 = new Location(l1.getWorld(), 188.0, y, z);
                if (l2.getBlock().getType() != Material.AIR) {
                    l2 = new Location(l1.getWorld(), 194.0, y + 1.0, z);
                    if (l2.getBlock().getType() != Material.AIR) {
                        l2 = new Location(l1.getWorld(), 188.0, y + 1.0, z);
                    }
                }
            }
            l1 = l2;
        }
        if (SadanHuman.SadanInterest.containsKey((Object)killed.getWorld().getUID())) {
            SadanHuman.SadanInterest.put((Object)killed.getWorld().getUID(), (Object)((int)SadanHuman.SadanInterest.get((Object)killed.getWorld().getUID()) - 1));
        }
        killed.remove();
        final ArmorStand respawnAnchor = (ArmorStand)killed.getWorld().spawn(this.a(l1), (Class)ArmorStand.class);
        new BukkitRunnable() {
            public void run() {
                if (respawnAnchor.isDead()) {
                    this.cancel();
                    return;
                }
                respawnAnchor.getLocation().getBlock().getLocation().clone().getWorld().spigot().playEffect(respawnAnchor.getLocation().clone().add(0.0, TerracottaSadan.this.y, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                respawnAnchor.getLocation().getBlock().getLocation().clone().getWorld().spigot().playEffect(respawnAnchor.getLocation().clone().add(0.0, TerracottaSadan.this.y, 0.0), Effect.WITCH_MAGIC, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
        respawnAnchor.setVisible(false);
        respawnAnchor.setMetadata("t_sadan", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        respawnAnchor.setGravity(false);
        respawnAnchor.setMarker(true);
        this.f(respawnAnchor.getLocation().add(0.0, this.y, 0.0));
        new BukkitRunnable() {
            public void run() {
                if (respawnAnchor.isDead()) {
                    this.cancel();
                    return;
                }
                TerracottaSadan.this.f(respawnAnchor.getLocation().clone().add(0.0, TerracottaSadan.this.y + 0.2, 0.0));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 66L);
        new BukkitRunnable() {
            public void run() {
                if (respawnAnchor.isDead()) {
                    this.cancel();
                    return;
                }
                TerracottaSadan.this.f(respawnAnchor.getLocation().clone().add(0.0, TerracottaSadan.this.y + 0.2, 0.0));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 131L);
        new BukkitRunnable() {
            public void run() {
                if (respawnAnchor.isDead()) {
                    this.cancel();
                    return;
                }
                TerracottaSadan.this.f(respawnAnchor.getLocation().clone().add(0.0, TerracottaSadan.this.y + 0.2, 0.0));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 201L);
        this.sendPacketBlock(respawnAnchor.getLocation().getBlock().getLocation().clone().add(0.0, 0.0, 0.0), respawnAnchor.getWorld(), 0, killed.getLocation().getYaw(), (LivingEntity)killed, respawnAnchor);
        SUtil.delay(() -> respawnAnchor.remove(), 202L);
        new BukkitRunnable() {
            public void run() {
                if (respawnAnchor.isDead()) {
                    this.cancel();
                    return;
                }
                new SEntity(respawnAnchor.getLocation().clone(), SEntityType.TERRACOTTA_SADAN, new Object[0]);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 201L);
    }
    
    public void summonOnLoc() {
    }
    
    public void f(final Location loc) {
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.4, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.4, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.4, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.SOUL_SAND))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
    }
    
    public Location getBlockLoc(final Block b) {
        final Location l = b.getLocation();
        double x = l.getX() + 0.5;
        double z = l.getZ() + 0.5;
        if (l.getX() < 0.0) {
            x = l.getX() - 0.5;
        }
        if (l.getZ() < 0.0) {
            z = l.getZ() - 0.5;
        }
        final Location loc = new Location(l.getWorld(), x, l.getY(), z);
        return loc;
    }
    
    public Location a(final Location l) {
        final double x = l.getBlockX();
        final double z = l.getBlockZ();
        final Location loc = new Location(l.getWorld(), x, l.getY(), z);
        final Location loc2 = this.getBlockLoc2(loc);
        return loc2;
    }
    
    public Location getBlockLoc2(final Location l) {
        double x = l.getX() + 0.5;
        double z = l.getZ() + 0.5;
        if (l.getX() < 0.0) {
            x = l.getX() - 0.5;
        }
        if (l.getZ() < 0.0) {
            z = l.getZ() - 0.5;
        }
        final Location loc = new Location(l.getWorld(), x, l.getY(), z);
        return loc;
    }
    
    public void spawnHeadBlock(final Location loc, final LivingEntity e, final ArmorStand s) {
        final Material perviousblock = loc.getBlock().getType();
        final byte data = loc.getBlock().getData();
        if (perviousblock != Material.AIR) {
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (s.isDead()) {
                    TerracottaSadan.this.r(loc.getBlock().getLocation(), perviousblock, data);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        double rot = (e.getLocation().getYaw() - 90.0f) % 360.0f;
        if (rot < 0.0) {
            rot += 360.0;
        }
        BlockFace facingDirection = null;
        if (0.0 <= rot && rot < 22.5) {
            facingDirection = BlockFace.NORTH;
        }
        else if (22.5 <= rot && rot < 67.5) {
            facingDirection = BlockFace.NORTH_EAST;
        }
        else if (67.5 <= rot && rot < 112.5) {
            facingDirection = BlockFace.EAST;
        }
        else if (112.5 <= rot && rot < 157.5) {
            facingDirection = BlockFace.SOUTH_EAST;
        }
        else if (157.5 <= rot && rot < 202.5) {
            facingDirection = BlockFace.SOUTH;
        }
        else if (202.5 <= rot && rot < 247.5) {
            facingDirection = BlockFace.SOUTH_WEST;
        }
        else if (247.5 <= rot && rot < 292.5) {
            facingDirection = BlockFace.WEST;
        }
        else if (292.5 <= rot && rot < 337.5) {
            facingDirection = BlockFace.NORTH_WEST;
        }
        else if (337.5 <= rot && rot < 360.0) {
            facingDirection = BlockFace.NORTH;
        }
        else {
            facingDirection = null;
        }
        final Block b = loc.getBlock();
        b.setTypeIdAndData(Material.SKULL.getId(), (byte)1, true);
        final Skull skull = (Skull)b.getState();
        skull.setSkullType(SkullType.PLAYER);
        skull.setOwner("Ethelian");
        skull.setRotation(facingDirection);
        skull.update(true);
        SUtil.delay(() -> b.getLocation().getBlock().setType(Material.AIR), 70L);
        SUtil.delay(() -> this.r(loc.getBlock().getLocation(), perviousblock, data), 70L);
    }
    
    public void sendPacketBlock(final Location loc, final World w, final int type, final float yaw, final LivingEntity e, final ArmorStand s) {
        final Material perviousblock = loc.getBlock().getType();
        final byte data = loc.getBlock().getData();
        new BukkitRunnable() {
            public void run() {
                if (s.isDead()) {
                    TerracottaSadan.this.r(loc.getBlock().getLocation(), perviousblock, data);
                    TerracottaSadan.this.removeItemOnGround(loc.getBlock().getLocation(), Material.RED_ROSE);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        if (s.isDead()) {
            this.r(loc.getBlock().getLocation(), perviousblock, data);
            return;
        }
        loc.getBlock().setType(Material.FLOWER_POT);
        MagicFlowerPot.changePot(loc.getBlock(), true, s);
        loc.getBlock().getState().update(true);
        SUtil.delay(() -> this.removeItemOnGround(loc.getBlock().getLocation(), Material.RED_ROSE), 65L);
        SUtil.delay(() -> loc.getBlock().setType(Material.AIR), 64L);
        SUtil.delay(() -> this.spawnHeadBlock(loc.getBlock().getLocation().clone().add(0.0, 1.0, 0.0).getBlock().getLocation(), e, s), 130L);
        new BukkitRunnable() {
            public void run() {
                if (s.isDead()) {
                    this.cancel();
                    return;
                }
                loc.getBlock().setTypeIdAndData(159, (byte)12, true);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 65L);
        SUtil.delay(() -> this.y = 1.2, 65L);
        SUtil.delay(() -> this.y = 1.7, 130L);
        SUtil.delay(() -> this.y = 0.85, 200L);
        SUtil.delay(() -> this.r(loc.getBlock().getLocation(), perviousblock, data), 200L);
    }
    
    public void r(final Location loc, final Material perviousblock, final byte data) {
        if (perviousblock != Material.FLOWER_POT && perviousblock != Material.SKULL) {
            loc.getBlock().setType(perviousblock);
            SUtil.delay(() -> loc.getBlock().setData(data), 1L);
        }
    }
    
    public void removeItemOnGround(final Location l, final Material mat) {
        for (final Entity e : l.getWorld().getNearbyEntities(l, 4.0, 4.0, 4.0)) {
            if (e instanceof Item && ((Item)e).getItemStack().getType() == mat) {
                e.remove();
            }
        }
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, null, null, null, null);
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public boolean hasNameTag() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return false;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.37;
    }
    
    public void throwRose(final LivingEntity e) {
        final Location throwLoc = e.getLocation().add(0.0, 0.2, 0.0);
        final Vector throwVec = e.getLocation().add(e.getLocation().getDirection().multiply(10)).toVector().subtract(e.getLocation().toVector()).normalize().multiply(1.2);
        e.getWorld().playSound(e.getLocation(), Sound.EAT, 1.0f, 1.0f);
        final ArmorStand armorStand1 = (ArmorStand)e.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand1.setMetadata("ftd", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        armorStand1.getEquipment().setHelmet(SItem.of(SMaterial.RED_ROSE).getStack());
        armorStand1.setHeadPose(new EulerAngle(-92.55000305175781, 0.0, 0.0));
        armorStand1.setGravity(false);
        armorStand1.setMarker(true);
        armorStand1.setVisible(false);
        final Vector[] previousVector = { throwVec };
        final Collection<Entity> damaged = (Collection<Entity>)new ArrayList();
        new BukkitRunnable() {
            private int run = -1;
            private int ticklived = 0;
            
            public void run() {
                final Vector teleportTo = armorStand1.getLocation().getDirection().normalize().multiply(1);
                ++this.ticklived;
                final int ran;
                final int i = ran = 0;
                final int num = 90;
                final Location loc = null;
                ++this.run;
                final Location locof = armorStand1.getLocation();
                locof.setY(locof.getY() + 1.0);
                if (this.ticklived >= 18) {
                    armorStand1.remove();
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 3);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    this.cancel();
                    return;
                }
                if (armorStand1.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity e2 : armorStand1.getNearbyEntities(20.0, 20.0, 20.0)) {
                    if (e2 instanceof Player) {
                        if (((Player)e2).getGameMode() == GameMode.CREATIVE) {
                            continue;
                        }
                        if (((CraftPlayer)e2).getGameMode() == GameMode.SPECTATOR) {
                            continue;
                        }
                        if (e2.hasMetadata("NPC")) {
                            continue;
                        }
                        armorStand1.teleport(armorStand1.getLocation().setDirection(e2.getLocation().toVector().subtract(armorStand1.getLocation().toVector())));
                    }
                }
                if (i % 2 == 0 && i < 13) {
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                }
                else if (i % 2 == 0) {
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                }
                for (final Entity e3 : armorStand1.getNearbyEntities(0.5, 0.5, 0.5)) {
                    if (e3 instanceof Player) {
                        final Player p = (Player)e3;
                        p.damage(55000.0, (Entity)e);
                        armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 3);
                        armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                        armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                        armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                        armorStand1.remove();
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 3L);
    }
}
