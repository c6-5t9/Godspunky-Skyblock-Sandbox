package net.hypixel.skyblock.entity.wolf;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.nms.SvenPackmaster;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class SvenPup extends BaseWolf
{
    private final double health;
    private final double damage;
    private final CraftPlayer target;
    private final SvenPackmaster parent;
    
    public SvenPup(final Double health, final Double damage, final CraftPlayer target, final SvenPackmaster parent) {
        this.health = health;
        this.damage = damage;
        this.target = target;
        this.parent = parent;
    }
    
    @Override
    public String getEntityName() {
        return "Sven Pup";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return this.health;
    }
    
    @Override
    public double getDamageDealt() {
        return this.damage;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public boolean isAngry() {
        return true;
    }
    
    @Override
    public boolean isBaby() {
        return true;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((Wolf)entity).setTarget((LivingEntity)this.target);
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        this.parent.getPups().remove((Object)sEntity);
    }
    
    public SvenPackmaster getParent() {
        return this.parent;
    }
}
