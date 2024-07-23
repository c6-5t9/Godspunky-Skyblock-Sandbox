package net.hypixel.skyblock.entity.caverns;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.texture.ItemTexture;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class UndeadGiaKhanhvn extends BaseZombie
{
    private boolean isEating;
    private final boolean isBowing;
    private boolean EatingCooldown;
    
    public UndeadGiaKhanhvn() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
    }
    
    @Override
    public String getEntityName() {
        return Sputnik.trans("&c&lUndead GiaKhanhVN");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 5.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 60000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.getEquipment().setItemInHand(SItem.of(SMaterial.BONE).getStack());
        Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTYzMTk3MDM3OTg1MiwKICAicHJvZmlsZUlkIiA6ICIyMWUzNjdkNzI1Y2Y0ZTNiYjI2OTJjNGEzMDBhNGRlYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJHZXlzZXJNQyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mZWI0ZTFlNTk3NDNiMDBmNWYwZmM3YTM1NjY3MGIzOTYxMGI4M2IwN2VjNTg2OWJjYmYzOGYwYTU3NGE2YTk5IgogICAgfQogIH0KfQ==", "KuyyIyFjOe4dOCqyEJZGZ/6zOD6tW69/q/CGJ+Y5rIKLfDIcRfPECoDt9mXc/R8aun50jpJdSYbgFoupv7sQaUWPAuPdcVx9YNrPWxbTn8F+1aWntg/AKswhYLb1C6jbwhzKg/PDckE405SuGn/QAcS0OFEfBb/sl/4cJ3xzTfrgnOmQuNIeoCMmBurJFTdPcFbg4CWQuKIenJDj5BM4rBM5aH1v0KJlSz6srU9tKHAr6nwTnKzComylP2nAxZsZD5PXvaYhBiWC+qUme2TVX77hOxReDQWFpG4Vfj2y/+Lh54BRRZWN7kb2B9msJxPgGw+V82A9+agoXpqClkfy1GHG1ZcAyyEblEygkeqR2ElFtyaLp1H29ebtCO4G6Tgh8shCzPFAhoGag03jBWqGQ+gVzU2R2z/w9aJXsRVAkI4fV5tg753v6a4XGUM/NbvIbwt3OJF9DwRknsbp/2dIryorKmVyMyuWG0/Qt/VWyrGj6j7Dj2/kL/xxwRe2E6B3VM260Obkx+NPJ2MmtmJ7k1G92xubnlVvA+fcLKrH+E6OeyrGCG2Eme0otZtookHshgtxI1q17m2NtOwlirw4IZWK+WsNkhzJlOpnJSCF0/koXwunKyniRSbHNWGBizoFG1LF1zSJcOkU443chhmgP0niSNwPPweQsa4qWMS1ca4=", true);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)70);
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final double height = 1.85;
        final ArmorStand hologram = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, 1.85, 0.0), (Class)ArmorStand.class);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setSmall(false);
        hologram.setMarker(true);
        hologram.setBasePlate(false);
        hologram.setCustomNameVisible(true);
        for (final Entity e : entity.getNearbyEntities(10.0, 10.0, 10.0)) {
            Sputnik.moveTo(entity, e.getLocation(), 0.2f);
        }
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (UndeadGiaKhanhvn.this.isEating) {
                    entity.getWorld().playSound(entity.getLocation(), Sound.EAT, 1.0f, 1.0f);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 4L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (UndeadGiaKhanhvn.this.isEating) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    final Location loc = entity.getLocation();
                    loc.add(0.0, 1.4, 0.0);
                    loc.add(entity.getLocation().getDirection().multiply(0.5));
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                    new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc).setParticleData((ParticleData)new ItemTexture(new ItemStack(Material.CAULDRON_ITEM))).setOffset(new Vector((double)Sputnik.randomVector(), 0.3, (double)Sputnik.randomVector())).display();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 3L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.getHealth() < entity.getMaxHealth() / 2.0 && !UndeadGiaKhanhvn.this.EatingCooldown && !UndeadGiaKhanhvn.this.isEating) {
                    UndeadGiaKhanhvn.this.EatingCooldown = true;
                    SUtil.delay(() -> UndeadGiaKhanhvn.this.isEating = true, 5L);
                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.ENCHANTED_GOLDEN_APPLE).getStack());
                    new BukkitRunnable() {
                        public void run() {
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            entity.getWorld().playSound(entity.getLocation(), Sound.BURP, 1.0f, 1.0f);
                            final double healamount = SUtil.random(100000000, 150000000);
                            if (!entity.isDead()) {
                                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healamount));
                            }
                            SUtil.delay(() -> {
                                final Object val$entity = entity;
                                entity.getEquipment().setItemInHand(SItem.of(SMaterial.BONE).getStack());
                            }, 5L);
                            UndeadGiaKhanhvn.this.isEating = false;
                            SUtil.delay(() -> UndeadGiaKhanhvn.this.EatingCooldown = false, SUtil.random(400, 500));
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 60L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 10L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    hologram.setCustomName(Sputnik.trans(Sputnik.entityNameTag(entity, Sputnik.buildcustomString(UndeadGiaKhanhvn.this.getEntityName(), 0, true))));
                    new BukkitRunnable() {
                        public void run() {
                            hologram.remove();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
                }
                if (hologram.isDead()) {
                    this.cancel();
                    return;
                }
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.teleport(entity.getLocation().clone().add(0.0, 1.85, 0.0));
                hologram.setCustomName(Sputnik.trans(Sputnik.entityNameTag(entity, Sputnik.buildcustomString(UndeadGiaKhanhvn.this.getEntityName(), 0, true))));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 0L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (UndeadGiaKhanhvn.this.isEating) {
                        continue;
                    }
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
                    if (target.getNoDamageTicks() == 7) {
                        continue;
                    }
                    if (SUtil.random(0, 10) > 8) {
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
        return 15570.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.3;
    }
    
    @Override
    public int mobLevel() {
        return 540;
    }
}
