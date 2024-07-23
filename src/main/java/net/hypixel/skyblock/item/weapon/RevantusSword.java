package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class RevantusSword implements ToolStatistics, MaterialFunction
{
    @Override
    public int getBaseDamage() {
        return 500;
    }
    
    @Override
    public double getBaseStrength() {
        return 150.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.2;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 100.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Dark Claymore";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.LONGSWORD;
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("&oThis &othing &owas &otoo &obig &oand &olong &oto &obe &ocalled &oa &osword, &oit &owas &omore &olike &oa &olarge &oiron &opipe, &ocan &oswing &oup &oto &c&o6 &oblocks!");
    }
}
