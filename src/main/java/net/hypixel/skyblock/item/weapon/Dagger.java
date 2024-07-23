package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class Dagger implements ToolStatistics, MaterialFunction
{
    @Override
    public int getBaseDamage() {
        return 50;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.4;
    }
    
    @Override
    public String getDisplayName() {
        return "Hunter Knife";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
