package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", aliases = "kam", permission = PlayerRank.ADMIN)
public class KillAllMobs extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player == null) {
            this.send((Object)ChatColor.RED + "You can't use this command here!");
            return;
        }
        if (player.isOp()) {
            final World world = player.getWorld();
            for (final Entity entity : world.getEntities()) {
                if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ITEM_FRAME && entity.getType() != EntityType.MINECART && !entity.hasMetadata("pets") && !entity.hasMetadata("NPC") && !entity.hasMetadata("ss_drop") && !entity.hasMetadata("Ire") && !entity.hasMetadata("inv")) {
                    entity.remove();
                }
            }
            this.send((Object)ChatColor.WHITE + "You removed" + (Object)ChatColor.YELLOW + " ALL" + (Object)ChatColor.RESET + " the mobs in this world.");
        }
        else {
            this.send((Object)ChatColor.RED + "You can't use this, lol.");
        }
    }
}
