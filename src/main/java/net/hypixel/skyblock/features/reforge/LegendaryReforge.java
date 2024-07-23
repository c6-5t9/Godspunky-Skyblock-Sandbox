package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class LegendaryReforge implements Reforge
{
    @Override
    public String getName() {
        return "Legendary";
    }
    
    @Override
    public RarityValue<Double> getStrength() {
        return new RarityValue<Double>(3.0, 7.0, 12.0, 18.0, 25.0, 32.0);
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.05, 0.07, 0.09, 0.15, 0.12, 0.18);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.05, 0.1, 0.15, 0.22, 0.28, 0.36);
    }
    
    @Override
    public RarityValue<Double> getIntelligence() {
        return new RarityValue<Double>(5.0, 8.0, 12.0, 18.0, 25.0, 35.0);
    }
    
    @Override
    public RarityValue<Double> getAttackSpeed() {
        return new RarityValue<Double>(2.0, 3.0, 5.0, 7.0, 10.0, 15.0);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.WEAPON);
    }
}
