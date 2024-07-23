package net.hypixel.skyblock.entity.wolf;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class OldWolf extends BaseWolf
{
    @Override
    public String getEntityName() {
        return "Old Wolf";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 15000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 720.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SMaterial.BONE, EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getXPDropped() {
        return 40.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
    
    @Override
    public int mobLevel() {
        return 50;
    }
}
