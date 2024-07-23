package net.hypixel.skyblock.entity.zombie;

import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;

public class ZombieVillager extends BaseZombie
{
    @Override
    public String getEntityName() {
        return "Zombie Villager";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 120.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 24.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS));
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SMaterial.ROTTEN_FLESH, EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.POISONOUS_POTATO, EntityDropType.OCCASIONAL, 0.05) });
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.35;
    }
    
    @Override
    public double getXPDropped() {
        return 7.0;
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return true;
    }
    
    @Override
    public int mobLevel() {
        return 1;
    }
}
