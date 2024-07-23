package net.hypixel.skyblock.item.armor.storm;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class StormHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseIntelligence() {
        return 400.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 180.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 80.0;
    }
    
    @Override
    public String getURL() {
        return "7c33b1e96b8ba078a91f0002d4919c615543cd093c2d709d9aee9f6268134c2c";
    }
    
    @Override
    public String getDisplayName() {
        return "Storm's Helmet";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }
    
    @Override
    public String getLore() {
        return null;
    }
}
