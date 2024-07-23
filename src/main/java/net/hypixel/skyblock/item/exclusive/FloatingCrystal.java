package net.hypixel.skyblock.item.exclusive;

import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public abstract class FloatingCrystal implements SkullStatistics, MaterialFunction
{
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public void onInteraction(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }
        final Player player = e.getPlayer();
        final SEntity sEntity = new SEntity(player.getLocation().clone().add(player.getLocation().getDirection().multiply(1.5)), this.getCrystalType(), new Object[0]);
    }
    
    protected abstract SEntityType getCrystalType();
}
