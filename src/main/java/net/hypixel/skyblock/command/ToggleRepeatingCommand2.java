package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.Repeater;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "db:ssp", permission = PlayerRank.ADMIN)
public class ToggleRepeatingCommand2 extends SCommand
{
    public Repeater repeater;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player == null) {
            this.send((Object)ChatColor.RED + "Something occurred while taking services from the API!");
        }
    }
}
