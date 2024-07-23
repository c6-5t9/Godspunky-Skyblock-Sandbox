package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.Rarity;

public class HegemonyArtifact implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Hegemony Artifact";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public String getURL() {
        return "313384a293cfbba3489b483ebc1de7584ca2726d7f5c3a620513474925e87b97";
    }
}
