package net.hypixel.skyblock.features.reforge;

import net.hypixel.skyblock.item.SpecificItemType;
import java.util.Arrays;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.List;
import net.hypixel.skyblock.item.RarityValue;

public interface Reforge
{
    String getName();
    
    default RarityValue<Double> getStrength() {
        return RarityValue.zeroDouble();
    }
    
    default RarityValue<Double> getCritChance() {
        return RarityValue.zeroDouble();
    }
    
    default RarityValue<Double> getCritDamage() {
        return RarityValue.zeroDouble();
    }
    
    default RarityValue<Double> getIntelligence() {
        return RarityValue.zeroDouble();
    }
    
    default RarityValue<Double> getFerocity() {
        return RarityValue.zeroDouble();
    }
    
    default RarityValue<Double> getAttackSpeed() {
        return RarityValue.zeroDouble();
    }
    
    default List<GenericItemType> getCompatibleTypes() {
        return (List<GenericItemType>)Arrays.asList((Object[])GenericItemType.values());
    }
    
    default Reforge blank() {
        return () -> "Blank";
    }
    
    default List<SpecificItemType> getSpecificTypes() {
        return (List<SpecificItemType>)Arrays.asList((Object[])SpecificItemType.values());
    }
}
