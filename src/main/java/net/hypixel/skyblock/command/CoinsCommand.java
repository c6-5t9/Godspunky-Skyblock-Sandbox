package net.hypixel.skyblock.command;

import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your coin amount.", permission = PlayerRank.ADMIN)
public class CoinsCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 0 && args.length != 2) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final User user = sender.getUser();
        if (args.length == 0) {
            user.setPermanentCoins(!user.isPermanentCoins());
            this.send((Object)ChatColor.GREEN + "Your coins are no" + (user.isPermanentCoins() ? "w" : " longer") + " permanent.");
            return;
        }
        final long coins = Long.parseLong(args[1]);
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[0].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case 96417: {
                if (lowerCase2.equals((Object)"add")) {
                    n = 0;
                    break;
                }
                break;
            }
            case -2060248300: {
                if (lowerCase2.equals((Object)"subtract")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 114240: {
                if (lowerCase2.equals((Object)"sub")) {
                    n = 2;
                    break;
                }
                break;
            }
            case 3552391: {
                if (lowerCase2.equals((Object)"take")) {
                    n = 3;
                    break;
                }
                break;
            }
            case 113762: {
                if (lowerCase2.equals((Object)"set")) {
                    n = 4;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                user.addCoins(coins);
                this.send((Object)ChatColor.GREEN + "You (or someone) have added " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + (Object)ChatColor.GREEN + " to your purse.");
                return;
            }
            case 1:
            case 2:
            case 3: {
                user.subCoins(coins);
                this.send((Object)ChatColor.GREEN + "You (or someone) have subtracted " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + (Object)ChatColor.GREEN + " from your Purse.");
                return;
            }
            case 4: {
                user.setCoins(coins);
                this.send((Object)ChatColor.GREEN + "You (or someone) have set your Purse coins to " + (Object)ChatColor.GOLD + SUtil.commaify(coins) + ".");
                return;
            }
            default: {
                throw new CommandArgumentException();
            }
        }
    }
}
