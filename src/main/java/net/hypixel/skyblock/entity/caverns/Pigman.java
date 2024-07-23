package net.hypixel.skyblock.entity.caverns;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class Pigman implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Pigman";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 250.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 75.0;
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SItem.of(SMaterial.GOLD_SWORD).getStack(), null, null, null, null);
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.GOLD_NUGGET).getStack(), SUtil.random(1, 2)), EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getXPDropped() {
        return 20.0;
    }
}
