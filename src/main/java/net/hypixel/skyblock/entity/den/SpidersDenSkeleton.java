package net.hypixel.skyblock.entity.den;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;

public class SpidersDenSkeleton implements EntityStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Skeleton";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 100.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 33.0;
    }
    
    @Override
    public double getXPDropped() {
        return 6.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(new ItemStack(Material.BONE, SUtil.random(5, 7)), EntityDropType.GUARANTEED, 1.0));
    }
}
