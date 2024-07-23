package net.hypixel.skyblock.entity;

public interface Ageable
{
    default boolean isBaby() {
        return false;
    }
}
