package net.hypixel.skyblock.command;

import java.util.Iterator;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "fsd", permission = PlayerRank.ADMIN)
public class SaveDataCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player != null) {
            if (player.isOp()) {
                this.send((Object)ChatColor.GRAY + "Performing save action, please wait...");
                SLog.info("[SYSTEM] Saving players data, this action was performed by " + player.getName() + "...");
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    final User user = User.getUser(p.getUniqueId());
                    if (user != null) {
                        if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                            User.getUser(player).configsave();
                        }
                        else {
                            User.getUser(player).save();
                        }
                    }
                }
                Bukkit.broadcastMessage(Sputnik.trans("&b[SkyBlock D.C] &aAll players data have been saved! Action performed by " + player.getDisplayName() + "&a!"));
            }
        }
        else {
            SLog.info("[SYSTEM] Saving players data, this action was performed by CONSOLE...");
            for (final Player p : Bukkit.getOnlinePlayers()) {
                final User user = User.getUser(p.getUniqueId());
                if (user != null) {
                    if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                        user.configsave();
                    }
                    else {
                        user.save();
                    }
                }
            }
            Bukkit.broadcastMessage(Sputnik.trans("&b[SkyBlock D.C] &aAll players data have been saved! Action performed by &cCONSOLE&a!"));
        }
    }
}
