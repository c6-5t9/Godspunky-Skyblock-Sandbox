package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", aliases = "sbmenu", permission = PlayerRank.DEFAULT)
public class SkySimMenuCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        GUIType.SKYBLOCK_MENU.getGUI().open(player);
    }
}
