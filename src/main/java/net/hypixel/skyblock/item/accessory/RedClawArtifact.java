package net.hypixel.skyblock.item.accessory;

import java.util.HashMap;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.MaterialFunction;

public class RedClawArtifact implements AccessoryStatistics, MaterialFunction
{
    private static final Map<UUID, Integer> HITS;
    
    @Override
    public String getURL() {
        return "caf59b8aa0f83546ef0d178ccf87e7ed88cf7858caae79b3633cbd75b650525f";
    }
    
    @Override
    public String getDisplayName() {
        return "Red Claw Artifact";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public double getBaseCritChance() {
        return 0.05;
    }
    
    @Override
    public double getBaseStrength() {
        return 450.0;
    }
    
    static {
        HITS = (Map)new HashMap();
    }
}
