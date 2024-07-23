package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class Unreal implements Reforge
{
    @Override
    public String getName() {
        return "Unreal";
    }
    
    @Override
    public RarityValue<Double> getStrength() {
        return new RarityValue<Double>(3.0, 7.0, 12.0, 18.0, 25.0, 34.0);
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.08, 0.09, 0.1, 0.11, 0.13, 0.15);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.05, 0.1, 0.18, 0.32, 0.5, 0.7);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.RANGED_WEAPON);
    }
}
