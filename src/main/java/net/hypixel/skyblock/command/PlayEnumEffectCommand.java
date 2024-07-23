package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Play a Bukkit enum sound.", usage = "/playenumsound <sound>", permission = PlayerRank.ADMIN)
public class PlayEnumEffectCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length < 1 || args.length > 2) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final Effect effect = Effect.valueOf(args[0].toUpperCase());
        int count = 1;
        if (args.length == 2) {
            count = Integer.parseInt(args[1]);
        }
        for (int i = 0; i < count; ++i) {
            player.getWorld().playEffect(player.getLocation(), effect, (Object)effect.getData());
        }
        player.sendMessage((Object)ChatColor.GRAY + "Played " + effect.name() + ".");
    }
}
