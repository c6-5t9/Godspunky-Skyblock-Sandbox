package net.hypixel.skyblock.entity.dragon.type;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.apache.commons.lang3.Range;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.dragon.Dragon;

public class SuperiorDragon extends Dragon
{
    public SuperiorDragon(final World world) {
        super(world, 1.6, (Range<Double>)SuperiorDragon.DEFAULT_DAMAGE_DEGREE_RANGE, 300L);
    }
    
    public SuperiorDragon() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Superior Dragon";
    }
    
    public double getEntityMaxHealth() {
        return 1.2E7;
    }
    
    public double getDamageDealt() {
        return 1600.0;
    }
}
