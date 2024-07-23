package net.hypixel.skyblock.command;

import net.hypixel.skyblock.gui.menu.Items.HexGUI;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(permission = PlayerRank.DEFAULT, aliases = "hex")
public class HexCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        new HexGUI(sender.getPlayer()).open(sender.getPlayer());
    }
}
