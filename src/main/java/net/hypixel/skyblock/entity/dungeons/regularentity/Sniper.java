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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class Sniper implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Sniper";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 8000000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 60.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 100));
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
        return new SEntityEquipment(new ItemStack(Material.BOW), SUtil.getSkullURLStack("asas", "98949a424802498a1f1d6b30dfd4556379831b4f6e9d59c9a880f192e61da765", 1, ""), null, null, null);
    }
}
