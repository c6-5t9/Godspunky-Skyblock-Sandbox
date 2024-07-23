package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;

public class SuperspeedTalisman implements AccessoryStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Superspeed Talisman";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public double getBaseSpeed() {
        return 3.0;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ACCESSORY;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.ACCESSORY;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getURL() {
        return "bb884d61f235235047483ac4ba4ce528691e6424bac13814159272d9673ac";
    }
}
