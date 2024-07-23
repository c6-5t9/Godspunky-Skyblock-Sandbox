package net.hypixel.skyblock.entity.dungeons.regularentity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderman;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.end.BaseEnderman;

public class Fels extends BaseEnderman
{
    private boolean spawned;
    
    public Fels() {
        this.spawned = false;
    }
    
    @Override
    public String getEntityName() {
        return "Fels";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 1800000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final ArmorStand drop = (ArmorStand)entity.getWorld().spawn(entity.getLocation().clone().add(0.0, -1.4, 0.0), (Class)ArmorStand.class);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCustomNameVisible(false);
        drop.getEquipment().setHelmet(SUtil.getSkullURLStack("asadas", "96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951", 0, ""));
        entity.setMetadata("upsidedown", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("DungeonMobs", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)65);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 100));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 100));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (!Fels.this.spawned) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 100));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 100));
                    ((CraftEnderman)entity).setTarget((LivingEntity)null);
                    final Collection<Entity> ce = (Collection<Entity>)entity.getWorld().getNearbyEntities(entity.getLocation(), 0.5, 0.5, 0.5);
                    ce.removeIf(entity -> !(entity instanceof Player));
                    if (ce.size() > 0) {
                        drop.remove();
                        entity.removePotionEffect(PotionEffectType.INVISIBILITY);
                        entity.removePotionEffect(PotionEffectType.SLOW);
                        final ArrayList<LivingEntity> ep = (ArrayList<LivingEntity>)new ArrayList();
                        ce.addAll((Collection)ep);
                        Fels.this.spawned = true;
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 1L);
    }
    
    @Override
    public double getXPDropped() {
        return 320.0;
    }
    
    @Override
    public int mobLevel() {
        return 0;
    }
}
