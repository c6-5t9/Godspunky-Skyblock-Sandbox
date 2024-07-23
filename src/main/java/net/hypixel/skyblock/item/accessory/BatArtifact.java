package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;

public class BatArtifact implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Bat Artifact";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 3.0;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.3;
    }
    
    @Override
    public double getBaseHealth() {
        return 5.0;
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
    public String getURL() {
        return "6681a72da7263ca9aef066542ecca7a180c40e328c0463fcb114cb3b83057552";
    }
}
