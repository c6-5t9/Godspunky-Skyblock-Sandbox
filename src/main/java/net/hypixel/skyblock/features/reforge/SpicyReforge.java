package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class SpicyReforge implements Reforge
{
    @Override
    public String getName() {
        return "Spicy";
    }
    
    @Override
    public RarityValue<Double> getStrength() {
        return new RarityValue<Double>(2.0, 3.0, 4.0, 7.0, 10.0, 12.0);
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return RarityValue.singleDouble(0.01);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.25, 0.35, 0.45, 0.6, 0.8, 1.0);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.WEAPON);
    }
}
