package net.hypixel.skyblock.entity.end;

import org.bukkit.material.MaterialData;
import net.hypixel.skyblock.entity.EntityStatistics;

public interface EndermanStatistics extends EntityStatistics
{
    default MaterialData getCarriedMaterial() {
        return null;
    }
}
