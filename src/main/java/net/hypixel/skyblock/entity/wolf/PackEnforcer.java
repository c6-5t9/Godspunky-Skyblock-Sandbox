package net.hypixel.skyblock.entity.wolf;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class PackEnforcer extends BaseWolf
{
    @Override
    public String getEntityName() {
        return "Pack Enforcer";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 45000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 900.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SMaterial.WOLF_TOOTH, EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getXPDropped() {
        return 150.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
}
