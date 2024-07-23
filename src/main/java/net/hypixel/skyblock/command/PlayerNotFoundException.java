package net.hypixel.skyblock.command;

import org.bukkit.ChatColor;

public class PlayerNotFoundException extends RuntimeException
{
    public PlayerNotFoundException() {
        super((Object)ChatColor.GRAY + "Player not found!");
    }
}
