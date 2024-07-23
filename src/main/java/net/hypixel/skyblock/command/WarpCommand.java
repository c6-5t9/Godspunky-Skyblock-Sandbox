package net.hypixel.skyblock.command;

import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "warp command", aliases = "warp", permission = PlayerRank.DEFAULT)
public class WarpCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        GUIType.WARP.getGUI().open(sender.getPlayer());
    }
}
