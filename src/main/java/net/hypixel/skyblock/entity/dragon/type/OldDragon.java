package net.hypixel.skyblock.entity.dragon.type;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.apache.commons.lang3.Range;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.dragon.Dragon;

public class OldDragon extends Dragon
{
    public OldDragon(final World world) {
        super(world, 1.2, (Range<Double>)OldDragon.DEFAULT_DAMAGE_DEGREE_RANGE, 300L);
    }
    
    public OldDragon() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Old Dragon";
    }
    
    public double getEntityMaxHealth() {
        return 1.5E7;
    }
    
    public double getDamageDealt() {
        return 1400.0;
    }
}
