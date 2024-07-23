package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class OddReforge implements Reforge
{
    @Override
    public String getName() {
        return "Odd";
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.12, 0.15, 0.15, 0.2, 0.25, 0.25);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.1, 0.15, 0.15, 0.22, 0.3, 0.3);
    }
    
    @Override
    public RarityValue<Double> getIntelligence() {
        return new RarityValue<Double>(-5.0, -10.0, -18.0, -32.0, -50.0, -50.0);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.WEAPON);
    }
}
