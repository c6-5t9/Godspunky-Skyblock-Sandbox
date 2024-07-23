package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class SharpReforge implements Reforge
{
    @Override
    public String getName() {
        return "Sharp";
    }
    
    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.1, 0.12, 0.14, 0.17, 0.2, 0.25);
    }
    
    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.2, 0.3, 0.4, 0.55, 0.75, 0.9);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.WEAPON);
    }
}
