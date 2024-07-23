package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.user.User;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "go to or create your island", aliases = "isd,deleteisland,islanddelete", permission = PlayerRank.DEFAULT, usage = "/deleteisland")
public class IslandDeleteCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        User.getUser(player).setIslandLocation(0.0, 0.0);
        if (SkyBlock.getPlugin().config.getBoolean("Config")) {
            User.getUser(player).configsave();
        }
        else {
            User.getUser(player).save();
        }
    }
}
