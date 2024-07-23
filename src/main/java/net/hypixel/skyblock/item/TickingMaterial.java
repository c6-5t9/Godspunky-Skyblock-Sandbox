package net.hypixel.skyblock.item;

import org.bukkit.entity.Player;

public interface TickingMaterial extends MaterialFunction
{
    default void tick(final SItem item, final Player owner) {
    }
    
    default long getInterval() {
        return 2L;
    }
}
