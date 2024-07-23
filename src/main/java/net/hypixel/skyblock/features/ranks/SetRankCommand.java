package net.hypixel.skyblock.features.ranks;

import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Bukkit;
import java.util.Objects;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class SetRankCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        final Player player = (Player)sender;
        final User user = User.getUser(player);
        if (sender.isOp() || ((User)Objects.requireNonNull((Object)user)).rank == PlayerRank.ADMIN || ((User)Objects.requireNonNull((Object)user)).rank == PlayerRank.ADMIN) {
            if (args.length >= 2) {
                try {
                    final Player target = Bukkit.getPlayer(args[0]);
                    final PlayerRank newRank = PlayerRank.valueOf(args[1].toUpperCase().replace((CharSequence)"+", (CharSequence)"P"));
                    User.getUser(target).setRank(newRank);
                    final String prefix = (newRank == PlayerRank.DEFAULT) ? "&7Default" : newRank.getPrefix().replace((CharSequence)"[", (CharSequence)"").replace((CharSequence)"]", (CharSequence)"");
                    sender.sendMessage(Sputnik.trans("&aSet " + args[0] + "'s Rank To " + prefix + "&a!"));
                    return true;
                }
                catch (final Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            sender.sendMessage(Sputnik.trans("&cUsage: /setrank <player> <rank>"));
            return false;
        }
        sender.sendMessage(Sputnik.trans("&cYou need ADMIN rank to use this command."));
        return false;
    }
}
