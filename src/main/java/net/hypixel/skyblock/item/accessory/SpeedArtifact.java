package net.hypixel.skyblock.item.accessory;

import java.util.HashMap;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.MaterialFunction;

public class SpeedArtifact implements AccessoryStatistics, MaterialFunction
{
    private static final Map<UUID, Integer> HITS;
    
    @Override
    public String getURL() {
        return "f06706eecb2d558ace27abda0b0b7b801d36d17dd7a890a9520dbe522374f8a6";
    }
    
    @Override
    public String getDisplayName() {
        return "Speed Artifact";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.05;
    }
    
    static {
        HITS = (Map)new HashMap();
    }
}
