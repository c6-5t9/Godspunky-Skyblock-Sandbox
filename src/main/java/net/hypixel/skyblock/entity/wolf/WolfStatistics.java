package net.hypixel.skyblock.entity.wolf;

import net.hypixel.skyblock.entity.Ageable;
import net.hypixel.skyblock.entity.EntityStatistics;

public interface WolfStatistics extends EntityStatistics, Ageable
{
    default boolean isAngry() {
        return false;
    }
}
