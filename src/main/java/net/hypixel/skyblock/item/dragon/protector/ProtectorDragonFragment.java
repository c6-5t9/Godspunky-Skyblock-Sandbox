package net.hypixel.skyblock.item.dragon.protector;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class ProtectorDragonFragment implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "48de339af63a229c9238d027e47f53eeb56141a419f51b35c31ea1494b435dd3";
    }
    
    @Override
    public String getDisplayName() {
        return "Protector Dragon Fragment";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
}
