package net.hypixel.skyblock.entity.caverns;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SlimeStatistics;

public class LargeSlime implements SlimeStatistics, EntityFunction
{
    @Override
    public String getEntityName() {
        return "Slime";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 250.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 150.0;
    }
    
    @Override
    public int getSize() {
        return 10;
    }
    
    @Override
    public void onAttack(final EntityDamageByEntityEvent e) {
        new BukkitRunnable() {
            public void run() {
                e.getEntity().setVelocity(e.getEntity().getVelocity().clone().setY(1.5));
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
    }
    
    @Override
    public double getXPDropped() {
        return 20.0;
    }
}
