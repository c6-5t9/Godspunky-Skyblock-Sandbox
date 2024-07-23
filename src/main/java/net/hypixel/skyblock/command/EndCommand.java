package net.hypixel.skyblock.command;

import org.bukkit.World;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanBossManager;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "fed", permission = PlayerRank.ADMIN)
public class EndCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player.isOp()) {
            final World world = player.getWorld();
            Bukkit.broadcastMessage(Sputnik.trans("&c[SYSTEM] &e" + player.getName() + " forcing the bossroom &c" + world.getName() + " &eto end."));
            SadanBossManager.endFloor(world);
        }
    }
}
