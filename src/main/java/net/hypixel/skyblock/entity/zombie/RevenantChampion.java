package net.hypixel.skyblock.entity.zombie;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;

public class RevenantChampion extends BaseZombie
{
    @Override
    public String getEntityName() {
        return "Revenant Champion";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 90000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 2200.0;
    }
    
    @Override
    public double getXPDropped() {
        return 600.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)), null, SUtil.enchant(new ItemStack(Material.DIAMOND_CHESTPLATE)), SUtil.enchant(new ItemStack(Material.CHAINMAIL_LEGGINGS)), new ItemStack(Material.IRON_BOOTS));
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.REVENANT_FLESH).getStack(), 2), EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
}
