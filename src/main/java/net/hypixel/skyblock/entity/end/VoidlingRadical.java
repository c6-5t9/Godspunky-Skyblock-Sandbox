package net.hypixel.skyblock.entity.end;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class VoidlingRadical extends BaseEnderman
{
    @Override
    public String getEntityName() {
        return "Voidling Radical";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.2E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 6000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 400.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SMaterial.NULL_SPHERE, EntityDropType.GUARANTEED, 1.0));
    }
}
