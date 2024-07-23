package net.hypixel.skyblock.item.accessory;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.MaterialFunction;

public interface AccessoryFunction extends MaterialFunction
{
    default void onDamageInInventory(final SItem weapon, final Player damager, final Entity damaged, final SItem accessory, final AtomicDouble damage) {
    }
    
    default void update(final SItem instance, final Player player, final int accessorySlot) {
    }
}
