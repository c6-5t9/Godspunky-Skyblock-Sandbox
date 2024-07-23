package net.hypixel.skyblock.command;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.Repeater;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your mana amount.", permission = PlayerRank.ADMIN)
public class ManaCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 1) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final UUID uuid = player.getUniqueId();
        if (!Repeater.MANA_MAP.containsKey((Object)uuid)) {
            throw new CommandFailException("Something went wrong!");
        }
        final int set = Integer.parseInt(args[0]);
        Repeater.MANA_MAP.remove((Object)uuid);
        Repeater.MANA_MAP.put((Object)uuid, (Object)set);
        this.send((Object)ChatColor.GREEN + "Your mana is now " + (Object)ChatColor.AQUA + set + ".");
    }
}
