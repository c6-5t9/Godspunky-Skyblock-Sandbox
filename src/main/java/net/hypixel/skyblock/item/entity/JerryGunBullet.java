package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class JerryGunBullet implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "17db1923d03c4ef4e9f6e872c5a6ad2578b1aff2b281fbc3ffa7466c825fb9";
    }
    
    @Override
    public String getDisplayName() {
        return "item.watcher.skull";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
}
