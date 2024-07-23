package net.hypixel.skyblock.item.bow;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.MaterialFunction;

public interface BowFunction extends MaterialFunction
{
    default void onBowShoot(final SItem bow, final EntityShootBowEvent e) {
    }
    
    default void onBowHit(final Entity hit, final Player shooter, final Arrow arrow, final SItem weapon, final AtomicDouble damage) {
    }
}
