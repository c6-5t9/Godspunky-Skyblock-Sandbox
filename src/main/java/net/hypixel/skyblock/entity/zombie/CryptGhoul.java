package net.hypixel.skyblock.entity.zombie;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;

public class CryptGhoul extends BaseZombie
{
    @Override
    public String getEntityName() {
        return "Crypt Ghoul";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 350.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.IRON_SWORD), null, new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS));
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(new ItemStack(Material.ROTTEN_FLESH, SUtil.random(1, 3)), EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.35;
    }
    
    @Override
    public double getXPDropped() {
        return 30.0;
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return false;
    }
    
    @Override
    public int mobLevel() {
        return 30;
    }
}
