package net.hypixel.skyblock.entity;

public interface SkeletonStatistics extends EntityStatistics
{
    default boolean isWither() {
        return false;
    }
}
