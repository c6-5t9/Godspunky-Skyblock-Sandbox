package net.hypixel.skyblock.command;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import java.util.UUID;
import java.util.List;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Hidden command.", aliases = "attc", permission = PlayerRank.DEFAULT)
public class AccessTimedCommand extends SCommand
{
    public static final List<UUID> KEYS;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player p = sender.getPlayer();
        if (2 != args.length) {
            this.send((Object)ChatColor.RED + "System Command! You don't have access to it.");
            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            return;
        }
        if (!AccessTimedCommand.KEYS.contains((Object)UUID.fromString(args[0]))) {
            this.send((Object)ChatColor.RED + "The requested action is no longer available!");
            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            return;
        }
        p.chat("/trade " + args[1]);
        AccessTimedCommand.KEYS.remove((Object)UUID.fromString(args[0]));
    }
    
    static {
        KEYS = (List)new ArrayList();
    }
}
