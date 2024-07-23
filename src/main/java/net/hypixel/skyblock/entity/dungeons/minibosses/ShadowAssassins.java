package net.hypixel.skyblock.entity.dungeons.minibosses;

import org.bukkit.util.Vector;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect;
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
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class ShadowAssassins extends BaseZombie
{
    private final boolean isEating;
    private final boolean isBowing;
    private final boolean EatingCooldown;
    
    public ShadowAssassins() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lShadow Assassin");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 4.9E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 900000.0;
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
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        entity.getEquipment().setBoots(b(1704483, Material.LEATHER_BOOTS));
        Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTU4OTEzNzY1ODgxOSwKICAicHJvZmlsZUlkIiA6ICJlM2I0NDVjODQ3ZjU0OGZiOGM4ZmEzZjFmN2VmYmE4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5pRGlnZ2VyVGVzdCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zMzk5ZTAwZjQwNDQxMWU0NjVkNzQzODhkZjEzMmQ1MWZlODY4ZWNmODZmMWMwNzNmYWZmYTFkOTE3MmVjMGYzIgogICAgfQogIH0KfQ==", "Zc+egPfvdkkutM0qR13oIUCXYuLIRkXGLuKutWxSUbW4H7jujEIQD+aKW+Yy9JekTbaqehvp+OArXMkjRs9h8o0ZGAJY/xlXF3OVzfBA7hIvrtx7cSaIRIr5pfjcBCUe0m1l8shByayaCtu/q11QZzZCX1+ZHKgghG9W95EnkmyAESHNjIXFBCMxPCElGfjEIsKwdt48NIlDiCmx3pUSCr3AnL8FvHrG4CMNZK+hhMStOV8nLq7l6MppsUUmRWkL0DVDTEh9BHzAWw3pBOvwP3r9Ax/5amBDrB1sN8vSa/bfuMxlxH11UGt3kb04SOuxYuMCCSCzKq0xSzlP5H5HfW3wSSk9T2zcpyEZgsIud28FZzBjcdgB+Umq0Cp7IybAi6xFbjC8zNgh+y24sNv6F4XJzv8v5eB1AwUZXStDrqrIpTb1XHIJurRNBbyXh3q8XuR2ECmpZAwupKtxWDo5og6IbigQEjKFjMrmvgnUd1dukcdro+w/p2IgmGHVXoR6jtN1YNnpldILDJiql8R097Nco3wU0crU5M1qfqkHHEvOOrf7iOZRF+psNaiJSZuBNmmTdS+13Q+nNwoTfGERFb8Em3YxKFs5j9l4a7HxbW2YvH93sGHCxuPgd9bXJ9KPh6Yp9Uch1cDB/uF4FfOwN7WMQ8ON7IhAHAegjLththc=", true);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)65);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                if (null == ((CraftZombie)entity).getTarget()) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 100));
                    entity.getEquipment().setBoots((ItemStack)null);
                }
                else {
                    entity.removePotionEffect(PotionEffectType.INVISIBILITY);
                    entity.getEquipment().setBoots(ShadowAssassins.b(1704483, Material.LEATHER_BOOTS));
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (null != ((CraftZombie)entity).getTarget() && 5.0 <= ((CraftZombie)entity).getTarget().getLocation().distance(entity.getLocation())) {
                    Location locofTPing = ((CraftZombie)entity).getTarget().getLocation().add(((CraftZombie)entity).getTarget().getLocation().getDirection().multiply(-1));
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playSound(entity.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 1.0f);
                    entity.getLocation().clone().getWorld().spigot().playEffect(entity.getLocation().clone().add(0.0, 1.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                    entity.getLocation().clone().getWorld().spigot().playEffect(entity.getLocation().clone().add(0.0, 1.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                    if (Material.AIR != locofTPing.getBlock().getType()) {
                        locofTPing = ((CraftZombie)entity).getTarget().getLocation();
                    }
                    entity.teleport(locofTPing);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playEffect(entity.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 10);
                    entity.getWorld().playSound(entity.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 1.0f);
                    entity.getLocation().clone().getWorld().spigot().playEffect(entity.getLocation().clone().add(0.0, 1.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                    entity.getLocation().clone().getWorld().spigot().playEffect(entity.getLocation().clone().add(0.0, 1.0, 0.0), Effect.LARGE_SMOKE, 21, 0, 0.1f, 0.0f, 0.1f, 0.01f, 1, 30);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 70L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (ShadowAssassins.this.isEating) {
                        continue;
                    }
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target = (Player)entities;
                    if (GameMode.CREATIVE == target.getGameMode()) {
                        continue;
                    }
                    if (GameMode.SPECTATOR == target.getGameMode()) {
                        continue;
                    }
                    if (target.hasMetadata("NPC")) {
                        continue;
                    }
                    if (7 == target.getNoDamageTicks()) {
                        continue;
                    }
                    if (8 < SUtil.random(0, 10)) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entities.getLocation()).toVector()));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SItem.of(SMaterial.IRON_SWORD).getStack(), null, null, null, null);
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
        final Entity en = (Entity)sEntity.getEntity();
        final Vector v = new Vector(0, 0, 0);
        SUtil.delay(() -> en.setVelocity(v), 1L);
    }
    
    @Override
    public double getXPDropped() {
        return 5570.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.35;
    }
}
