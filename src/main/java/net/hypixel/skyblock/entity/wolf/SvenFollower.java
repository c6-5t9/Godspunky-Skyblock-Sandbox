package net.hypixel.skyblock.entity.wolf;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class SvenFollower extends BaseWolf
{
    @Override
    public String getEntityName() {
        return "Pack Enforcer";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 120000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 1100.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.WOLF_TOOTH).getStack(), 2), EntityDropType.GUARANTEED, 1.0));
    }
    
    @Override
    public double getXPDropped() {
        return 250.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
}
