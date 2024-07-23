package net.hypixel.skyblock.item;

import org.bukkit.event.player.PlayerFishEvent;

public interface FishingRodFunction extends MaterialFunction
{
    void onFish(final SItem p0, final PlayerFishEvent p1);
}
