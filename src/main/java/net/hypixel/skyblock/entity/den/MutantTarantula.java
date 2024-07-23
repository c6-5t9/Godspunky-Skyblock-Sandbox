package net.hypixel.skyblock.entity.den;

import java.util.Collections;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.ChatColor;

public class MutantTarantula extends BaseSpider
{
    @Override
    public String getEntityName() {
        return (Object)ChatColor.RED + "Mutant Tarantula";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 576000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 5000.0;
    }
    
    @Override
    public double getXPDropped() {
        return 500.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (final Entity e : entity.getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (!(e instanceof Player)) {
                        return;
                    }
                    ((Player)e).damage(MutantTarantula.this.getDamageDealt() * 0.5, (Entity)entity);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.TARANTULA_WEB).getStack(), 5), EntityDropType.GUARANTEED, 1.0));
    }
}
