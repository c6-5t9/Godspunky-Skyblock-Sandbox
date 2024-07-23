package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.entity.StaticDragonManager;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "bruhbu", aliases = "edf", permission = PlayerRank.ADMIN)
public class EndDragonFightCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        final World world = player.getWorld();
        if (world.getName().equalsIgnoreCase("world")) {
            if (StaticDragonManager.ACTIVE) {
                this.send((Object)ChatColor.GREEN + "Processing...");
                SUtil.delay(() -> StaticDragonManager.endFight(), 10L);
                this.endDragonFight(world);
                SUtil.delay(() -> SUtil.broadcast((Object)ChatColor.RED + "[SYSTEM] " + (Object)ChatColor.YELLOW + player.getName() + " have ended all Dragon fight in this world!", player), 10L);
            }
            else {
                this.send((Object)ChatColor.RED + "There are no active dragon fight!");
            }
        }
        else {
            this.send((Object)ChatColor.RED + "This command is not available on this world!");
        }
    }
    
    public void endDragonFight(final World world) {
        for (final Entity e : world.getEntities()) {
            if (e.getType() == EntityType.ENDER_DRAGON || e.getType() == EntityType.ENDER_CRYSTAL) {
                e.remove();
            }
        }
    }
}
