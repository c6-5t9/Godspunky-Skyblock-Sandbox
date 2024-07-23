package net.hypixel.skyblock.command;

import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

public class CommandPermissionException extends RuntimeException
{
    public CommandPermissionException(final PlayerRank permission) {
        super((Object)ChatColor.RED + "You need " + (Object)permission + "  rank to use this command");
    }
}
