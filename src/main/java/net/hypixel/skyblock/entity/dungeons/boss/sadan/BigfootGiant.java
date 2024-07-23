package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Location;
import net.hypixel.skyblock.api.block.BlockFallAPI;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Sound;
import org.bukkit.Effect;
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
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.GameMode;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class BigfootGiant extends BaseZombie
{
    private static LivingEntity e;
    private boolean shockWave;
    private boolean shockWaveCD;
    
    public BigfootGiant() {
        this.shockWave = false;
        this.shockWaveCD = true;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&c&lBigfoot");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.5E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 30000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        BigfootGiant.e = entity;
        ((CraftZombie)entity).setBaby(false);
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        SUtil.delay(() -> this.shockWaveCD = false, 150L);
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)25);
        entity.setMetadata("Giant_", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("highername", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (!BigfootGiant.this.shockWave && !BigfootGiant.this.shockWaveCD && SUtil.random(1, 100) <= 5) {
                    BigfootGiant.this.shockWaveCD = true;
                    BigfootGiant.this.shockWave = true;
                    final Vector vec = new Vector(0, 0, 0);
                    vec.setY(2);
                    BigfootGiant.e.setVelocity(vec);
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        BigfootGiant.this.jumpAni(entity);
                    }, 10L);
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
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 8L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, null, null, null, b(8991025, Material.LEATHER_BOOTS));
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
        return 0.35;
    }
    
    public void jumpAni(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!BigfootGiant.this.shockWave) {
                    this.cancel();
                    return;
                }
                if (e.isOnGround()) {
                    BigfootGiant.this.shockWave = false;
                    SUtil.delay(() -> BigfootGiant.this.shockWaveCD = false, 400L);
                    e.getWorld().playEffect(e.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 3);
                    e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION_HUGE, 3);
                    e.getWorld().playSound(e.getLocation(), Sound.EXPLODE, 3.0f, 0.0f);
                    SUtil.delay(() -> {
                        final Object val$e = e;
                        e.getWorld().playSound(e.getLocation(), Sound.EXPLODE, 10.0f, 0.0f);
                    }, 5L);
                    for (final Entity entities : e.getNearbyEntities(15.0, 10.0, 15.0)) {
                        final Vector vec = new Vector(0, 0, 0);
                        if (entities.hasMetadata("NPC")) {
                            continue;
                        }
                        if (entities instanceof ArmorStand) {
                            continue;
                        }
                        if (entities.hasMetadata("highername")) {
                            continue;
                        }
                        if (!(entities instanceof Player)) {
                            continue;
                        }
                        if (entities.getLocation().distance(e.getLocation()) <= 5.0) {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lBigfoot &chit you with &eStomp &cfor " + SUtil.commaify(Math.round((float)SadanFunction.dmgc(40000, p, (Entity)e))) + " &cdamage."));
                        }
                        else {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&c&lBigfoot &chit you with &eStomp &cfor " + SUtil.commaify(Math.round((float)SadanFunction.dmgc(35000, p, (Entity)e))) + " &cdamage."));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
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
    
    public static void createBlockTornado(final Entity e, final Material mat, final byte id) {
        for (int i = 0; i <= 30; ++i) {
            final int random = SUtil.random(0, 3);
            double range = 0.0;
            final Location loc = e.getLocation().clone();
            loc.setYaw((float)SUtil.random(0, 360));
            if (random == 1) {
                range = 0.6;
            }
            if (random == 2) {
                range = 0.7;
            }
            if (random == 3) {
                range = 0.8;
            }
            final Vector vec = loc.getDirection().normalize().multiply(range);
            vec.setY(1.1);
            BlockFallAPI.sendVelocityBlock(e.getLocation(), mat, id, e.getWorld(), 70, vec);
        }
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_HURT, 1.0f, 0.0f);
    }
}
