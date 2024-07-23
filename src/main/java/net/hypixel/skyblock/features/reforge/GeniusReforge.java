package net.hypixel.skyblock.features.reforge;

import net.hypixel.skyblock.item.RarityValue;

public class GeniusReforge implements Reforge
{
    @Override
    public String getName() {
        return "Genius";
    }
    
    @Override
    public RarityValue<Double> getIntelligence() {
        return RarityValue.singleDouble(10000.0);
    }
}
