package net.hypixel.skyblock.entity.dungeons;

import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.end.BaseEnderman;

public class Fels extends BaseEnderman
{
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
        return 1400000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.setMetadata("upsidedown", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)60);
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
