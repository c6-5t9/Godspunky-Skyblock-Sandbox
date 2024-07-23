package net.hypixel.skyblock.entity.dungeons.regularentity;

import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.entity.SEntityEquipment;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.GameMode;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.zombie.NPCMobs;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class Skeletor extends BaseZombie implements NPCMobs
{
    private final boolean skullShoot;
    
    public Skeletor() {
        this.skullShoot = false;
    }
    
    @Override
    public String getEntityName() {
        return "Skeletor";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 1500000.0;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final MobDisguise pl = Sputnik.applyPacketSkeleton((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)70);
        entity.setMetadata("SKEL", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("DungeonMobs", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                entity.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                if (((CraftZombie)entity).getTarget() != null && ((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation()) > 2.0) {
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        Skeletor.this.throwThickAssBone((Entity)entity);
                        for (final Player players : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                        }
                        entity.getEquipment().setItemInHand((ItemStack)null);
                        SUtil.delay(() -> entity.getEquipment().setItemInHand(new ItemStack(Material.BONE)), 30L);
                    }, 20L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 2L, 100L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final LivingEntity target1 = (LivingEntity)((CraftZombie)entity).getTarget();
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target2 = (Player)entities;
                    if (target2.getGameMode() == GameMode.CREATIVE) {
                        continue;
                    }
                    if (target2.getGameMode() == GameMode.SPECTATOR) {
                        continue;
                    }
                    if (target2.hasMetadata("NPC")) {
                        continue;
                    }
                    if (target2.getNoDamageTicks() == 7) {
                        continue;
                    }
                    if (SUtil.random(0, 10) > 8) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target2.getLocation().subtract(entities.getLocation()).toVector()));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target2).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.BOW), SUtil.getSkullURLStack("", "89d074ad9b9971879eb325bddff3675f7224856bd6d569fc8d483c133d73005d", 1, ""), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS));
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
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
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
    }
    
    @Override
    public double getXPDropped() {
        return 56.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.2;
    }
    
    public void throwThickAssBone(final Entity e) {
        final Vector throwVec = e.getLocation().add(e.getLocation().getDirection().multiply(10)).toVector().subtract(e.getLocation().toVector()).normalize().multiply(1.2);
        final Location throwLoc = e.getLocation().add(0.0, 0.5, 0.0);
        final ArmorStand armorStand1 = (ArmorStand)e.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand1.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
        armorStand1.setGravity(false);
        armorStand1.setVisible(false);
        armorStand1.setMarker(true);
        final Vector teleportTo = e.getLocation().getDirection().normalize().multiply(1);
        final Vector[] previousVector = { throwVec };
        new BukkitRunnable() {
            private int run = -1;
            
            public void run() {
                final int ran;
                final int i = ran = 0;
                final int num = 90;
                final Location loc = null;
                ++this.run;
                if (this.run > 100) {
                    this.cancel();
                    return;
                }
                final Location locof = armorStand1.getLocation();
                locof.setY(locof.getY() + 1.0);
                if (locof.getBlock().getType() != Material.AIR) {
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                final double xPos = armorStand1.getRightArmPose().getX();
                armorStand1.setRightArmPose(new EulerAngle(xPos + 0.7, 0.0, 0.0));
                final Vector newVector = new Vector(throwVec.getX(), previousVector[0].getY() - 0.03, throwVec.getZ());
                previousVector[0] = newVector;
                armorStand1.setVelocity(newVector);
                if (i < 13) {
                    final int angle = i * 20 + 90;
                }
                else {
                    final int angle = i * 20 - 90;
                }
                if (locof.getBlock().getType() != Material.AIR && locof.getBlock().getType() != Material.WATER) {
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                if (i % 2 == 0 && i < 13) {
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                }
                else if (i % 2 == 0) {
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                }
                for (int j = 0; j < 20; ++j) {
                    armorStand1.getWorld().spigot().playEffect(armorStand1.getLocation().clone().add(0.0, 1.75, 0.0), Effect.CRIT, 0, 1, (float)SUtil.random(-0.5, 0.5), (float)SUtil.random(0.0, 0.5), (float)SUtil.random(-0.5, 0.5), 0.0f, 1, 20);
                }
                for (final Entity en : armorStand1.getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (en instanceof Player) {
                        final Player p = (Player)en;
                        p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
                        User.getUser(p.getUniqueId()).damage(p.getMaxHealth() * 15.0 / 100.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, e);
                        p.damage(1.0E-5);
                        armorStand1.remove();
                        this.cancel();
                        break;
                    }
                }
            }
            
            public synchronized void cancel() throws IllegalStateException {
                super.cancel();
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    @Override
    public int mobLevel() {
        return 210;
    }
}
