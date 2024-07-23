package net.hypixel.skyblock.command;

import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "joinfloor6", permission = PlayerRank.DEFAULT)
public class JoinFloorCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        GUIType.CATACOMBS_BOSS.getGUI().open(sender.getPlayer());
    }
}
