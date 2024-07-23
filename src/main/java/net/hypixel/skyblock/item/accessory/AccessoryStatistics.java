package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Reforgable;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.PlayerBoostStatistics;

public interface AccessoryStatistics extends PlayerBoostStatistics, SkullStatistics, Reforgable
{
    default GenericItemType getType() {
        return GenericItemType.ACCESSORY;
    }
    
    default SpecificItemType getSpecificType() {
        return SpecificItemType.ACCESSORY;
    }
}
