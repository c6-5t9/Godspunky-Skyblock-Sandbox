package net.hypixel.skyblock.entity.dragon.type;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.apache.commons.lang3.Range;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.dragon.Dragon;

public class WiseDragon extends Dragon
{
    public WiseDragon(final World world) {
        super(world, 1.4, (Range<Double>)Dragon.DEFAULT_DAMAGE_DEGREE_RANGE, 200L);
    }
    
    public WiseDragon() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Wise Dragon";
    }
    
    public double getEntityMaxHealth() {
        return 9000000.0;
    }
    
    public double getDamageDealt() {
        return 1200.0;
    }
}
