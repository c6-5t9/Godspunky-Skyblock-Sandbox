package net.hypixel.skyblock.entity.den;

import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;

public class BroodMother extends BaseSpider
{
    @Override
    public String getEntityName() {
        return "Brood Mother";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 6000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 100.0;
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        for (int am = SUtil.random(4, 5), i = 0; i < am; ++i) {
            new SEntity((Entity)sEntity.getEntity(), SEntityType.CAVE_SPIDER, new Object[0]);
        }
    }
    
    @Override
    public double getXPDropped() {
        return 17.0;
    }
}
