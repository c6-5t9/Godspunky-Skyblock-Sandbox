package net.hypixel.skyblock.entity.den;

import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.entity.JockeyStatistics;

public class SpiderJockey extends BaseSpider implements JockeyStatistics
{
    @Override
    public SEntityType getPassenger() {
        return SEntityType.JOCKEY_SKELETON;
    }
    
    @Override
    public String getEntityName() {
        return "Spider";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 180.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 30.0;
    }
    
    @Override
    public double getXPDropped() {
        return 8.0;
    }
    
    public static class JockeySkeleton implements EntityStatistics, EntityFunction
    {
        @Override
        public String getEntityName() {
            return "Jockey Skeleton";
        }
        
        @Override
        public double getEntityMaxHealth() {
            return 250.0;
        }
        
        @Override
        public double getDamageDealt() {
            return 38.0;
        }
        
        @Override
        public double getXPDropped() {
            return 6.0;
        }
    }
}
