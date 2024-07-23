package net.hypixel.skyblock.item.entity;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class BS2 implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "eef162def845aa3dc7d46cd08a7bf95bbdfd32d381215aa41bffad5224298728";
    }
    
    @Override
    public String getDisplayName() {
        return "BZB2";
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
