package net.hypixel.skyblock.entity.dragon.type;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.apache.commons.lang3.Range;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.dragon.Dragon;

public class YoungDragon extends Dragon
{
    public YoungDragon(final World world) {
        super(world, 1.8, (Range<Double>)YoungDragon.DEFAULT_DAMAGE_DEGREE_RANGE, 300L);
    }
    
    public YoungDragon() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Young Dragon";
    }
    
    public double getEntityMaxHealth() {
        return 7500000.0;
    }
    
    public double getDamageDealt() {
        return 1600.0;
    }
}
