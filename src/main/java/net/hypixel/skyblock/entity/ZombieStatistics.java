package net.hypixel.skyblock.entity;

public interface ZombieStatistics extends EntityStatistics, Ageable
{
    default boolean isVillager() {
        return false;
    }
}
