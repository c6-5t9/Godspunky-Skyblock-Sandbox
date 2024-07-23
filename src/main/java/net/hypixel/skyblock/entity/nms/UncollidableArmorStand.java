package net.hypixel.skyblock.entity.nms;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

public class UncollidableArmorStand extends EntityArmorStand implements EntityStatistics, EntityFunction, SNMSEntity
{
    public UncollidableArmorStand(final World world) {
        super(world);
        this.n(true);
    }
    
    public UncollidableArmorStand() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return null;
    }
    
    public double getEntityMaxHealth() {
        return 1.0;
    }
    
    public double getDamageDealt() {
        return 0.0;
    }
    
    public boolean hasNameTag() {
        return false;
    }
    
    public double getXPDropped() {
        return 0.0;
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
}
