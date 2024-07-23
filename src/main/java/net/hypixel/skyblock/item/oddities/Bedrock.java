package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class Bedrock implements MaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Bedrock";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.BLOCK;
    }
    
    @Override
    public String getLore() {
        return "How do you have this??";
    }
}
