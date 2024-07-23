package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Giant;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
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

public class DiamondGiant extends BaseZombie
{
    private static LivingEntity e;
    private boolean swordActiv;
    private boolean swordSlamCD;
    
    public DiamondGiant() {
        this.swordActiv = false;
        this.swordSlamCD = true;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&3&lThe Diamond Giant");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.5E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 45000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        DiamondGiant.e = entity;
        ((CraftZombie)entity).setBaby(false);
        final Player p = (Player)entity.getWorld().getPlayers().get(SUtil.random(0, entity.getWorld().getPlayers().size() - 1));
        if (p != null && p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            ((CraftZombie)entity).setTarget((LivingEntity)p);
        }
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(500.0);
        SUtil.delay(() -> this.swordSlamCD = false, 100L);
        entity.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)50);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("highername", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("RedNameTag", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("Giant_", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                final LivingEntity target = (LivingEntity)((CraftZombie)entity).getTarget();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (!DiamondGiant.this.swordSlamCD && !DiamondGiant.this.swordActiv && SUtil.random(1, 140) <= 7 && target != null) {
                    DiamondGiant.this.swordActiv = true;
                    DiamondGiant.this.swordSlamCD = true;
                    DiamondGiant.this.swordSlamAC(entity, target);
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
        return new SEntityEquipment(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)), c(Material.DIAMOND_HELMET), c(Material.DIAMOND_CHESTPLATE), c(Material.DIAMOND_LEGGINGS), c(Material.DIAMOND_BOOTS));
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
    
    public void swordSlamAC(final LivingEntity e, final LivingEntity tar) {
        SUtil.delay(() -> this.swordSlamF(e, tar), 60L);
    }
    
    public void swordSlamF(final LivingEntity e, final LivingEntity tar) {
        this.swordSlam(e, tar);
    }
    
    public void swordSlam(final LivingEntity e, final LivingEntity player) {
        e.getEquipment().setItemInHand((ItemStack)null);
        final Giant armorStand = (Giant)player.getWorld().spawn(e.getLocation().add(0.0, 12.0, 0.0), (Class)Giant.class);
        armorStand.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
        Sputnik.applyPacketGiant((Entity)armorStand);
        armorStand.setCustomName("Dinnerbone");
        armorStand.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        armorStand.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.Woosh((LivingEntity)armorStand);
        EntityManager.noHit((Entity)armorStand);
        EntityManager.shutTheFuckUp((Entity)armorStand);
        final Location firstLocation = e.getLocation().add(0.0, 12.0, 0.0);
        final EntityLiving nms = ((CraftLivingEntity)e).getHandle();
        for (final Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)e).getHandle(), 0));
        }
        nms.r(((CraftEntity)e).getHandle());
        final Location secondLocation = player.getLocation();
        final Vector mobsVector = firstLocation.toVector();
        final Vector vectorBetween = secondLocation.toVector().subtract(mobsVector);
        vectorBetween.divide(new Vector(10, 10, 10));
        vectorBetween.add(new Vector(0, 0, 0));
        final int id = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)SkyBlock.getPlugin(), () -> armorStand.setVelocity(vectorBetween), 10L, 10L);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SkyBlock.getPlugin(), () -> Bukkit.getScheduler().cancelTask(id), 40L);
        new BukkitRunnable() {
            public void run() {
                if (!DiamondGiant.this.swordActiv) {
                    this.cancel();
                    return;
                }
                if (armorStand.isOnGround()) {
                    DiamondGiant.this.swordActiv = false;
                    SUtil.delay(() -> DiamondGiant.this.swordSlamCD = false, 300L);
                    armorStand.remove();
                    final Giant sword = (Giant)e.getWorld().spawnEntity(armorStand.getLocation(), EntityType.GIANT);
                    Sputnik.applyPacketGiant((Entity)sword);
                    sword.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                    EntityManager.noAI((Entity)sword);
                    EntityManager.noHit((Entity)sword);
                    EntityManager.shutTheFuckUp((Entity)sword);
                    sword.setCustomName("Dinnerbone");
                    sword.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    sword.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                    final ArmorStand stand = (ArmorStand)player.getWorld().spawnEntity(armorStand.getLocation(), EntityType.ARMOR_STAND);
                    stand.setVisible(false);
                    stand.setGravity(true);
                    stand.setPassenger((Entity)sword);
                    sword.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
                    e.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 0.0f);
                    e.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 0.35f);
                    for (final Entity entities : sword.getWorld().getNearbyEntities(sword.getLocation().add(sword.getLocation().getDirection().multiply(3)), 7.0, 7.0, 7.0)) {
                        if (entities.hasMetadata("NPC")) {
                            continue;
                        }
                        if (entities instanceof ArmorStand) {
                            continue;
                        }
                        if (entities instanceof Giant) {
                            continue;
                        }
                        if (!(entities instanceof Player)) {
                            continue;
                        }
                        if (entities.getLocation().add(sword.getLocation().getDirection().multiply(3)).distance(sword.getLocation()) > 2.0) {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&3&lThe Diamond Giant &chit you with &eSword of 10,000 Truths &cfor " + SUtil.commaify(SadanFunction.dmgc(50000, p, (Entity)e)) + " &cdamage."));
                        }
                        else {
                            final Player p = (Player)entities;
                            p.sendMessage(Sputnik.trans("&3&lThe Diamond Giant &chit you with &eSword of 10,000 Truths &cfor " + SUtil.commaify(SadanFunction.dmgc(55000, p, (Entity)e)) + " &cdamage."));
                        }
                    }
                    SUtil.delay(() -> sword.remove(), 35L);
                    SUtil.delay(() -> stand.remove(), 35L);
                    SUtil.delay(() -> {
                        final Object val$e = e;
                        e.getEquipment().setItemInHand(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)));
                    }, 35L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_HURT, 1.0f, 0.0f);
    }
}
