package net.hypixel.skyblock.item.accessory;

import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;

public class TreasureArtifact implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Treasure Artifact";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans2("&7Grants &a+3 extra loot to end", "&7of dungeon chests.") });
    }
    
    @Override
    public String getURL() {
        return "e10f20a55b6e188ebe7578459b64a6fbd825067bc497b925ca43c2643d059025";
    }
    
    @Override
    public double getBaseStrength() {
        return 350.0;
    }
}
