package net.hypixel.skyblock.entity.dragon.type;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.apache.commons.lang3.Range;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.dragon.Dragon;

public class ProtectorDragon extends Dragon
{
    public ProtectorDragon(final World world) {
        super(world, 1.4, (Range<Double>)ProtectorDragon.DEFAULT_DAMAGE_DEGREE_RANGE, 300L);
    }
    
    public ProtectorDragon() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Protector Dragon";
    }
    
    public double getEntityMaxHealth() {
        return 9000000.0;
    }
    
    public double getDamageDealt() {
        return 1300.0;
    }
}
