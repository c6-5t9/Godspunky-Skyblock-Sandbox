package net.hypixel.skyblock.command;

import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "open mob spawn gui.", usage = "/mobgui", aliases = "mobgui", permission = PlayerRank.ADMIN)
public class MobGuiCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        GUIType.MOB_GUI.getGUI().open(sender.getPlayer());
    }
}
