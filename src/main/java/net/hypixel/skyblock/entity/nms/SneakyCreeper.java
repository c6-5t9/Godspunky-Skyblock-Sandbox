package net.hypixel.skyblock.entity.nms;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.SEntity;
import java.lang.reflect.Field;
import org.bukkit.event.Event;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.event.CreeperIgniteEvent;
import org.bukkit.entity.Creeper;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import net.minecraft.server.v1_8_R3.World;
import net.hypixel.skyblock.entity.caverns.CreeperFunction;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.minecraft.server.v1_8_R3.EntityCreeper;

public class SneakyCreeper extends EntityCreeper implements EntityStatistics, SNMSEntity, CreeperFunction
{
    public SneakyCreeper(final World world) {
        super(world);
    }
    
    public SneakyCreeper() {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
    }
    
    public String getEntityName() {
        return "Sneaky Creeper";
    }
    
    public double getEntityMaxHealth() {
        return 120.0;
    }
    
    public double getDamageDealt() {
        return 80.0;
    }
    
    public boolean isVisible() {
        return false;
    }
    
    public void t_() {
        try {
            final Field f = EntityCreeper.class.getDeclaredField("fuseTicks");
            f.setAccessible(true);
            final int fuseTicks = (int)f.get((Object)this);
            if (this.cm() > 0 && fuseTicks == 0) {
                final CreeperIgniteEvent ignite = new CreeperIgniteEvent((Creeper)this.getBukkitEntity());
                SkyBlock.getPlugin().getServer().getPluginManager().callEvent((Event)ignite);
                if (ignite.isCancelled()) {
                    return;
                }
            }
        }
        catch (final IllegalAccessException | NoSuchFieldException ex) {}
        super.t_();
    }
    
    public void onCreeperIgnite(final CreeperIgniteEvent e, final SEntity sEntity) {
        sEntity.setVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (e.getEntity().isDead()) {
                    return;
                }
                sEntity.setVisible(false);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 35L);
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
    
    public double getXPDropped() {
        return 8.0;
    }
}
