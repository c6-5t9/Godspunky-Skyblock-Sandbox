package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import org.bukkit.entity.FallingBlock;
import org.bukkit.Sound;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import java.util.ArrayList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.GameMode;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class JollyPinkGiant extends BaseZombie
{
    private static LivingEntity e;
    private boolean terToss;
    private boolean terTossCD;
    
    public JollyPinkGiant() {
        this.terToss = false;
        this.terTossCD = true;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lJolly Pink Giant");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.5E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 25000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        JollyPinkGiant.e = entity;
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        ((CraftZombie)entity).setBaby(false);
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        SUtil.delay(() -> this.terTossCD = false, 60L);
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)30);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("highername", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Giant_", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (!JollyPinkGiant.this.terToss && !JollyPinkGiant.this.terTossCD && SUtil.random(1, 150) <= 4 && target != null) {
                    JollyPinkGiant.this.terTossCD = true;
                    JollyPinkGiant.this.terToss = true;
                    SUtil.delay(() -> JollyPinkGiant.this.terToss = false, 400L);
                    JollyPinkGiant.this.launchTerrain(entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
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
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, b(14751108, Material.LEATHER_HELMET), b(14751108, Material.LEATHER_CHESTPLATE), b(14751108, Material.LEATHER_LEGGINGS), b(14751108, Material.LEATHER_BOOTS));
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        Sputnik.zero(killed);
        if (SadanHuman.SadanGiantsCount.containsKey((Object)killed.getWorld().getUID())) {
            SadanHuman.SadanGiantsCount.put((Object)killed.getWorld().getUID(), (Object)((int)SadanHuman.SadanGiantsCount.get((Object)killed.getWorld().getUID()) - 1));
        }
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
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.3;
    }
    
    public void launchTerrain(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!JollyPinkGiant.this.terToss) {
                    SUtil.delay(() -> JollyPinkGiant.this.terTossCD = false, 200L);
                    this.cancel();
                    return;
                }
                final LivingEntity t = (LivingEntity)((CraftZombie)e).getTarget();
                if (t != null) {
                    JollyPinkGiant.this.throwTerrain(e, (Entity)t);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 30L);
    }
    
    public static ItemStack buildColorStack(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack c(final Material m) {
        final ItemStack stack = new ItemStack(m);
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static void applyEffect(final PotionEffectType e, final Entity en, final int ticks, final int amp) {
        ((LivingEntity)en).addPotionEffect(new PotionEffect(e, ticks, amp));
    }
    
    public static void damagePlayer(final Player p) {
        p.sendMessage(Sputnik.trans("&d&lJolly Pink Giant &chit you with &eBoulder Toss &cfor " + SUtil.commaify(SadanFunction.dmgc(30000, p, (Entity)JollyPinkGiant.e)) + " &cdamage."));
    }
    
    public void throwTerrain(final LivingEntity e, final Entity target) {
        final Block b = target.getLocation().getBlock();
        final World world = e.getWorld();
        final Location startBlock = e.getLocation().add(e.getLocation().getDirection().multiply(2));
        final List<Location> locationList = (List<Location>)new ArrayList();
        final List<Location> endList = (List<Location>)new ArrayList();
        final List<Material> blockTypes = (List<Material>)new ArrayList();
        final List<Material> launchTypes = (List<Material>)new ArrayList();
        for (int length = -1; length < 2; ++length) {
            for (int height = -1; height < 2; ++height) {
                final Location loc = startBlock.clone().add((double)length, 0.0, (double)height);
                final Location end = b.getLocation().clone().add((double)length, 0.0, (double)height);
                locationList.add((Object)loc);
                endList.add((Object)end);
            }
        }
        locationList.add((Object)startBlock.clone().add(0.0, 0.0, 2.0));
        locationList.add((Object)startBlock.clone().add(0.0, 0.0, -2.0));
        locationList.add((Object)startBlock.clone().add(2.0, 0.0, 0.0));
        locationList.add((Object)startBlock.clone().add(-2.0, 0.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(0.0, 0.0, 2.0));
        endList.add((Object)b.getLocation().clone().add(0.0, 0.0, -2.0));
        endList.add((Object)b.getLocation().clone().add(2.0, 0.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(-2.0, 0.0, 0.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(1.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(-1.0, -1.0, 0.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, 1.0));
        locationList.add((Object)startBlock.clone().add(0.0, -1.0, -1.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(1.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(-1.0, -1.0, 0.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, 1.0));
        endList.add((Object)b.getLocation().clone().add(0.0, -1.0, -1.0));
        final Byte blockData = 0;
        locationList.forEach(block -> {
            final Location loc2 = block.getBlock().getLocation().clone().subtract(0.0, 1.0, 0.0);
            Material mat = loc2.getBlock().getType();
            if (mat == Material.AIR) {
                mat = Material.STONE;
            }
            launchTypes.add((Object)mat);
            blockTypes.add((Object)block.getBlock().getType());
        });
        locationList.forEach(location -> {
            final Material material = (Material)launchTypes.get(locationList.indexOf((Object)location));
            final Location origin = location.clone().add(0.0, 7.0, 0.0);
            final int pos = locationList.indexOf((Object)location);
            final Location endPos = (Location)endList.get(pos);
            final FallingBlock block2 = world.spawnFallingBlock(origin, material, (byte)blockData);
            block2.setDropItem(false);
            block2.setMetadata("t", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            block2.setVelocity(Sputnik.calculateVelocityBlock(origin.toVector(), endPos.toVector(), 3));
        });
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_HURT, 1.0f, 0.0f);
    }
}
