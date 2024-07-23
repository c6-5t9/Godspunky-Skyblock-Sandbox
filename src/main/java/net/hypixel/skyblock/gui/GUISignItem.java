package net.hypixel.skyblock.gui;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface GUISignItem extends GUIClickableItem
{
    GUI onSignClose(final String p0, final Player p1);
    
    UUID inti();
}
