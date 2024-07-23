package net.hypixel.skyblock.command;

import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "bruhbu", aliases = "resetcb", permission = PlayerRank.ADMIN)
public class ResetCookieCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final User user = sender.getUser();
        if (!player.isOp()) {
            this.send((Object)ChatColor.RED + "No permission to execute this command!");
            return;
        }
        if (0 == args.length) {
            this.send((Object)ChatColor.RED + "Invaild Syntax! You need to provide a player");
            return;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (null != target) {
            PlayerUtils.setCookieDurationTicks(target, 0L);
            this.send(Sputnik.trans("&aReseted " + target.getName() + "'s &dCookie Buff&a."));
            target.sendMessage(Sputnik.trans("&e[WARNING] ") + (Object)ChatColor.RED + player.getName() + " have reseted your Cookie Buff. If you believe this is an error, contact Admins.");
            return;
        }
        this.send((Object)ChatColor.RED + "Invaild Syntax! You need to provide a vaild player");
    }
}
