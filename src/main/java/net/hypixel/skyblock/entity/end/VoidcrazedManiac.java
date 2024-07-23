package net.hypixel.skyblock.entity.end;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public class VoidcrazedManiac extends BaseEnderman
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.DARK_RED + "Voidcrazed Maniac";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 7.5E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 15000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 5000.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SMaterial.NULL_SPHERE, EntityDropType.GUARANTEED, 1.0));
    }
}
