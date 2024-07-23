package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Entity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", aliases = "kamh", permission = PlayerRank.ADMIN)
public class KillAllHostileMobs extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (null == player) {
            this.send((Object)ChatColor.RED + "You can't use this command here!");
        }
        if (player.isOp()) {
            final World world = player.getWorld();
            for (final Entity entity : world.getEntities()) {
                if (entity instanceof Monster && !entity.hasMetadata("pets") && !entity.hasMetadata("Ire")) {
                    entity.remove();
                }
            }
            this.send((Object)ChatColor.WHITE + "You removed all" + (Object)ChatColor.RED + " HOSTILE" + (Object)ChatColor.RESET + " mobs in this world.");
        }
        else {
            this.send(Sputnik.trans("&cYou cant use this, lol."));
        }
    }
}
