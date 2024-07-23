package net.hypixel.skyblock.entity.wolf;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class Wolf extends BaseWolf
{
    @Override
    public String getEntityName() {
        return "Wolf";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 250.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 80.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SMaterial.BONE, EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getXPDropped() {
        return 10.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
    
    @Override
    public int mobLevel() {
        return 15;
    }
}
