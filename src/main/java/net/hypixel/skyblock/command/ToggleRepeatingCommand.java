package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.Repeater;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "db:tsr", permission = PlayerRank.ADMIN)
public class ToggleRepeatingCommand extends SCommand
{
    public Repeater repeater;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player != null) {
            if (this.repeater == null) {
                this.repeater = new Repeater();
                player.sendMessage("SERVER LOOP TURNED ON");
            }
            else {
                this.repeater.stop();
                player.sendMessage("SERVER LOOP SHUTTED DOWN!");
            }
        }
        else {
            this.send((Object)ChatColor.RED + "Something occurred while taking services from the API!");
        }
    }
}
