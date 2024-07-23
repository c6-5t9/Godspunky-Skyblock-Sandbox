package net.hypixel.skyblock.entity.dungeons.watcher;

import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;

public class WatcherParasiteFish implements EntityStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Parasite";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 6.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 500000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 90.0;
    }
    
    @Override
    public int mobLevel() {
        return 125;
    }
}
