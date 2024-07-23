package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class FierceReforge implements Reforge
{
    @Override
    public String getName() {
        return "Fierce";
    }
    
    @Override
    public RarityValue<Double> getStrength() {
        return new RarityValue<Double>(2.0, 4.0, 6.0, 8.0, 10.0, 12.0);
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.02, 0.03, 0.04, 0.05, 0.06, 0.08);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.04, 0.07, 0.1, 0.14, 0.18, 0.24);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.ARMOR);
    }
}
