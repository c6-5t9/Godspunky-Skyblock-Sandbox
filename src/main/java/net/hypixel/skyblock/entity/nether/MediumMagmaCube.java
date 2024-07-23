package net.hypixel.skyblock.entity.nether;

import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SlimeStatistics;

public class MediumMagmaCube implements SlimeStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Magma Cube";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 250.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 120.0;
    }
    
    @Override
    public double getXPDropped() {
        return 4.0;
    }
    
    @Override
    public int getSize() {
        return 5;
    }
    
    @Override
    public boolean split() {
        return false;
    }
}
