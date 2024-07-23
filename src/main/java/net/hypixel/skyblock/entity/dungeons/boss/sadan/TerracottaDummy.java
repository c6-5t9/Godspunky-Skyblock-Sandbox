package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.entity.SEntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class TerracottaDummy extends BaseZombie
{
    @Override
    public String getEntityName() {
        return Sputnik.trans("");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.4E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 40000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        if (entity.getWorld().getPlayers().size() == 0) {
            return;
        }
        ((CraftZombie)entity).setBaby(false);
        Sputnik.applyPacketNPC((Entity)entity, "Ethelian", null, false);
        EntityManager.noAI((Entity)entity);
        EntityManager.noHit((Entity)entity);
        entity.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("t_sadan_p1_1", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.shutTheFuckUp((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        SUtil.delay(() -> Sputnik.applyPacketNPC((Entity)entity, "Ethelian", null, false), 50L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                Sputnik.applyPacketNPC((Entity)entity, "Ethelian", null, false);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 200L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, null, null, null, null);
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
}
