package net.hypixel.skyblock.entity.dungeons.regularentity;

import net.hypixel.skyblock.entity.SEntityEquipment;
import java.util.Arrays;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class SuperArcher implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Super Archer";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 3000000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 60.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("DungeonMobs", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)55);
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.ARROW, SUtil.random(2, 4)), EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.ENCHANTED_BONE, EntityDropType.RARE, 0.05) });
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.BOW), null, null, null, null);
    }
}
