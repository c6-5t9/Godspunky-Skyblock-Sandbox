package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Location;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class SleepingGolem_S extends BaseZombie
{
    @Override
    public String getEntityName() {
        return Sputnik.trans("&5&lSleeping Golem");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 0.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftZombie)entity).setBaby(false);
        final Location l = entity.getLocation().clone();
        l.setPitch(45.0f);
        entity.teleport(l);
        EntityManager.noAI((Entity)entity);
        Sputnik.applyPacketGolem((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)0);
        entity.setMetadata("NNP", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        new SEntity(killed.getLocation(), SEntityType.WOKE_GOLEM, new Object[0]);
        killed.remove();
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
        return 0.0;
    }
}
