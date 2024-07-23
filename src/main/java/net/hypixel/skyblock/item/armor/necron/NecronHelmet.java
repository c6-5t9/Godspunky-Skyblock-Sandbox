package net.hypixel.skyblock.item.armor.necron;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class NecronHelmet implements MaterialFunction, SkullStatistics, ToolStatistics
{
    @Override
    public double getBaseStrength() {
        return 40.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.3;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 30.0;
    }
    
    @Override
    public double getBaseHealth() {
        return 180.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 100.0;
    }
    
    @Override
    public String getURL() {
        return "7ede9549be73d9a9b2311b45ff887161f11bc56361b980cf7c73737b22804004";
    }
    
    @Override
    public String getDisplayName() {
        return "Necron's Helmet";
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
