package net.hypixel.skyblock.command;

import net.hypixel.skyblock.SkyBlock;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "kick player", aliases = "kick", permission = PlayerRank.HELPER)
public class Kick extends SCommand
{
    public static Config config;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length >= 2) {
            String reason = "";
            for (int i = 1; i < args.length; ++i) {
                reason = String.valueOf((Object)reason) + args[i] + " ";
            }
            final Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.send("§cPlayer does not exist or offline.");
            }
            sender.send("§aKicked player " + Bukkit.getPlayer(args[0]).getName() + " for " + reason);
            target.kickPlayer("§cYou have been kicked!\n\n§7Reason: §f" + reason + "\n§7Find out more: §b§n" + Kick.config.getString("discord"));
        }
        sender.send("§cInvalid syntax. Correct: /kick <name> <reason>");
    }
    
    static {
        Kick.config = SkyBlock.getInstance().config;
    }
}
