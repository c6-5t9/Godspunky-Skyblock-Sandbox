package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import org.bukkit.Sound;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.Effect;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_8_R3.EntityLiving;
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

public class LASRGiant extends BaseZombie
{
    private static LivingEntity e;
    private boolean laserActiveCD;
    private boolean laserActive;
    
    public LASRGiant() {
        this.laserActiveCD = true;
        this.laserActive = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&4&lL.A.S.R.");
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
        LASRGiant.e = entity;
        ((CraftZombie)entity).setBaby(false);
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        SUtil.delay(() -> this.laserActiveCD = false, 10L);
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)60);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("highername", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Giant_", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final EntityLiving nmsr = ((CraftLivingEntity)entity).getHandle();
        nmsr.getBoundingBox().grow(5.0, 5.0, 5.0);
        new BukkitRunnable() {
            public void run() {
                final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                nmsr.getBoundingBox().grow(5.0, 5.0, 5.0);
                if (!LASRGiant.this.laserActiveCD && !LASRGiant.this.laserActive && SUtil.random(1, 120) <= 6 && target != null) {
                    LASRGiant.this.laserActiveCD = true;
                    LASRGiant.this.laserActive = true;
                    LASRGiant.this.laser(entity);
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
        return new SEntityEquipment(null, b(12228503, Material.LEATHER_HELMET), b(12228503, Material.LEATHER_CHESTPLATE), b(12228503, Material.LEATHER_LEGGINGS), b(12228503, Material.LEATHER_BOOTS));
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        Sputnik.zero(killed);
        if (SadanHuman.SadanGiantsCount.containsKey((Object)killed.getWorld().getUID())) {
            SadanHuman.SadanGiantsCount.put((Object)killed.getWorld().getUID(), (Object)((int)SadanHuman.SadanGiantsCount.get((Object)killed.getWorld().getUID()) - 1));
        }
    }
    
    public void setArmorHex(final LivingEntity e, final int i) {
        e.getEquipment().setHelmet(buildColorStackH(i));
        e.getEquipment().setChestplate(buildColorStackC(i));
        e.getEquipment().setLeggings(buildColorStackL(i));
        e.getEquipment().setBoots(buildColorStackB(i));
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
    
    public void laser(final LivingEntity e) {
        final int[] array_colors = { 12228503, 8739418, 6897985, 6042419, 5385260 };
        SUtil.delay(() -> this.setArmorHex(e, array_colors[0]), 20L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[1]), 40L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[2]), 60L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[3]), 80L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[4]), 100L);
        SUtil.delay(() -> this.laserAni(e), 105L);
        SUtil.delay(() -> this.laserActive = false, 250L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[4]), 270L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[3]), 290L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[2]), 310L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[1]), 330L);
        SUtil.delay(() -> this.setArmorHex(e, array_colors[0]), 350L);
        SUtil.delay(() -> this.laserActiveCD = false, 500L);
    }
    
    public void laserAni(final LivingEntity e) {
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!LASRGiant.this.laserActive) {
                    this.cancel();
                    return;
                }
                final LivingEntity target = (LivingEntity)((CraftZombie)e).getTarget();
                final float angle_1 = e.getEyeLocation().getYaw() / 60.0f;
                final Location loc1 = e.getEyeLocation().add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                final Location loc2 = e.getEyeLocation().subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                loc1.add(0.0, 9.5, 0.0);
                loc2.add(0.0, 9.5, 0.0);
                if (target != null) {
                    if (target.getLocation().distance(e.getLocation()) < 1.0 || target.getLocation().distance(e.getLocation()) > 100.0) {
                        return;
                    }
                    final Location loc1_ = target.getLocation();
                    final Location loc2_ = target.getLocation();
                    final Location en1 = loc1_.add(0.0, 0.5, 0.0);
                    final Location en2 = loc2_.add(0.0, 0.5, 0.0);
                    LASRGiant.drawLine(loc1, en1, 0.0);
                    LASRGiant.drawLine(loc2, en2, 0.0);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 5L);
        new BukkitRunnable() {
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (!LASRGiant.this.laserActive) {
                    this.cancel();
                    return;
                }
                final LivingEntity target = (LivingEntity)((CraftZombie)e).getTarget();
                final float angle_1 = e.getEyeLocation().getYaw() / 60.0f;
                final Location loc1 = e.getEyeLocation().add(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                final Location loc2 = e.getEyeLocation().subtract(Math.cos((double)angle_1), 0.0, Math.sin((double)angle_1));
                loc1.add(0.0, 9.5, 0.0);
                loc2.add(0.0, 9.5, 0.0);
                if (target != null) {
                    if (target.getLocation().distance(e.getLocation()) < 1.0 || target.getLocation().distance(e.getLocation()) > 100.0) {
                        return;
                    }
                    final Location loc1_ = target.getLocation();
                    final Location loc2_ = target.getLocation();
                    final Location en1 = loc1_.add(0.0, 0.5, 0.0);
                    final Location en2 = loc2_.add(0.0, 0.5, 0.0);
                    LASRGiant.getEntity(loc1, en1, e);
                    LASRGiant.getEntity(loc2, en2, e);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 10L);
    }
    
    public static ItemStack buildColorStackH(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack buildColorStackC(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack buildColorStackL(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack buildColorStackB(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(hexcolor));
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
    
    public static void drawLine(final Location point1, final Location point2, final double space) {
        final Location blockLocation = point1;
        final Location crystalLocation = point2;
        final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 90.0;
        for (int i = 1; i <= 90; ++i) {
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), Effect.COLOURED_DUST, 0, 1, 0.8627451f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
            point1.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), Effect.COLOURED_DUST, 0, 1, 1.0196079f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
        }
    }
    
    public static void getEntity(final Location finaldestination, final Location ended, final LivingEntity e) {
        final Location blockLocation = finaldestination;
        final Location crystalLocation = ended;
        final Vector vector = blockLocation.clone().add(0.1, 0.1, 0.1).toVector().subtract(crystalLocation.clone().toVector());
        final double count = 90.0;
        for (int i = 1; i <= 90; ++i) {
            for (final Entity entity : ended.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 90.0)), 0.17, 0.0, 0.17)) {
                if (entity instanceof Player) {
                    final Player p = (Player)entity;
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.getWorld().playEffect(p.getLocation().clone().add(0.0, 2.2, 0.0), Effect.LAVA_POP, 10);
                    p.sendMessage(Sputnik.trans("&4&lL.A.S.R. &chit you with &eLaser Eyes &cfor " + SUtil.commaify(SadanFunction.dmgc(15000, p, (Entity)e)) + " &cdamage."));
                    return;
                }
            }
        }
    }
    
    public static void applyEffect(final PotionEffectType e, final Entity en, final int ticks, final int amp) {
        ((LivingEntity)en).addPotionEffect(new PotionEffect(e, ticks, amp));
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_HURT, 1.0f, 0.0f);
    }
}
