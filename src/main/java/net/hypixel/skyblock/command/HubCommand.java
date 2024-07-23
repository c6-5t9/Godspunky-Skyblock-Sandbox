package net.hypixel.skyblock.command;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.region.RegionGenerator;
import org.bukkit.command.CommandSender;
import java.util.Map;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Manage world regions.", usage = "", aliases = "hub", permission = PlayerRank.DEFAULT)
public class HubCommand extends SCommand
{
    public static Map<CommandSender, RegionGenerator> REGION_GENERATION_MAP;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        this.send(Sputnik.trans("&7Sending you to the Hub..."));
        final Location l = new Location(Bukkit.getWorld("world"), -2.5, 70.0, -68.5, 180.0f, 0.0f);
        if (null != sender.getPlayer()) {
            sender.getPlayer().teleport(l);
        }
    }
    
    static {
        HubCommand.REGION_GENERATION_MAP = (Map<CommandSender, RegionGenerator>)new HashMap();
    }
}
