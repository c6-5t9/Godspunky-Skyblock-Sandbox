package net.hypixel.skyblock.entity.dungeons.regularentity;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class SuperTankZombie implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Super Tank Zombie";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 1600500.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.STONE_SWORD), SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(15132390)), SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(8553090)), SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(8553090)), SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(15132390)));
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)98);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 1));
    }
    
    @Override
    public double getXPDropped() {
        return 100.0;
    }
}
