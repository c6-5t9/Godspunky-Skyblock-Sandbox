package net.hypixel.skyblock.item.dragon.wise;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class WiseDragonFragment implements SkullStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "1d7620b2e4934963bb12508310d05494c067dc33e008cecf2cd7b4549654fab3";
    }
    
    @Override
    public String getDisplayName() {
        return "Wise Dragon Fragment";
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
