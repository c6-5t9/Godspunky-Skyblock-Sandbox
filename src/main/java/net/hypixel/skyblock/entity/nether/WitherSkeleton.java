package net.hypixel.skyblock.entity.nether;

import java.util.Arrays;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SkeletonStatistics;

public class WitherSkeleton implements SkeletonStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Wither Skeleton";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 250.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 152.0;
    }
    
    @Override
    public boolean isWither() {
        return true;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.BONE, 3), EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.COAL, EntityDropType.COMMON, 0.5), new EntityDrop(SMaterial.ENCHANTED_COAL, EntityDropType.OCCASIONAL, 0.025), new EntityDrop(SMaterial.ENCHANTED_CHARCOAL, EntityDropType.RARE, 0.01) });
    }
    
    @Override
    public double getXPDropped() {
        return 13.0;
    }
}
