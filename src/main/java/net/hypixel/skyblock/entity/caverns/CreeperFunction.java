package net.hypixel.skyblock.entity.caverns;

import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.event.CreeperIgniteEvent;
import net.hypixel.skyblock.entity.EntityFunction;

public interface CreeperFunction extends EntityFunction
{
    default void onCreeperIgnite(final CreeperIgniteEvent e, final SEntity sEntity) {
    }
}
