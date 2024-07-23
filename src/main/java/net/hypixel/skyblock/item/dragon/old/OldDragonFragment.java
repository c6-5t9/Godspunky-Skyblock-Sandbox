package net.hypixel.skyblock.item.dragon.old;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class OldDragonFragment implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "7aa09ad177fbccc53fa316cc04bdd2c9366baed889df76c5a29defea8170def5";
    }
    
    @Override
    public String getDisplayName() {
        return "Old Dragon Fragment";
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
