package net.hypixel.skyblock.features.reforge;

import java.util.Collections;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public class FastReforge implements Reforge
{
    @Override
    public String getName() {
        return "Fast";
    }
    
    @Override
    public RarityValue<Double> getAttackSpeed() {
        return new RarityValue<Double>(10.0, 20.0, 30.0, 40.0, 50.0, 60.0);
    }
    
    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Collections.singletonList((Object)GenericItemType.WEAPON);
    }
}
