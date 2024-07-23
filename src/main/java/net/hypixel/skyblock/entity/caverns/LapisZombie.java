package net.hypixel.skyblock.entity.caverns;

import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class LapisZombie implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Lapis Zombie";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 200.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 50.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(null, new ItemStack(Material.STAINED_GLASS, 1, (short)11), SItem.of(SMaterial.LAPIS_ARMOR_CHESTPLATE).getStack(), SItem.of(SMaterial.LAPIS_ARMOR_LEGGINGS).getStack(), SItem.of(SMaterial.LAPIS_ARMOR_BOOTS).getStack());
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SMaterial.ROTTEN_FLESH, EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.LAPIS_ARMOR_HELMET, EntityDropType.RARE, 0.05), new EntityDrop(SMaterial.LAPIS_ARMOR_CHESTPLATE, EntityDropType.RARE, 0.05), new EntityDrop(SMaterial.LAPIS_ARMOR_LEGGINGS, EntityDropType.RARE, 0.05), new EntityDrop(SMaterial.LAPIS_ARMOR_BOOTS, EntityDropType.RARE, 0.05) });
    }
    
    @Override
    public double getXPDropped() {
        return 12.0;
    }
}
