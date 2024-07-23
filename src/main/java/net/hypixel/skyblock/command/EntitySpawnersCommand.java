package net.hypixel.skyblock.command;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.entity.EntitySpawner;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Manage entity spawners.", usage = "/<command> [create <type> | delete <index>]", aliases = "entityspawner,es,spawner,spawners", permission = PlayerRank.ADMIN)
public class EntitySpawnersCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        if (0 == args.length) {
            final StringBuilder builder = new StringBuilder("Spawners:");
            final List<EntitySpawner> spawners = EntitySpawner.getSpawners();
            for (int i = 0; i < spawners.size(); ++i) {
                final EntitySpawner spawner = (EntitySpawner)spawners.get(i);
                builder.append("\n ").append(i + 1).append(": ").append(SUtil.prettify(spawner.getLocation())).append(" (").append(spawner.getType().name()).append(")");
            }
            this.send(builder.toString());
            return;
        }
        if (2 != args.length) {
            throw new CommandArgumentException();
        }
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[0].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case -1352294148: {
                if (lowerCase2.equals((Object)"create")) {
                    n = 0;
                    break;
                }
                break;
            }
            case -1335458389: {
                if (lowerCase2.equals((Object)"delete")) {
                    n = 1;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                final SEntityType type = SEntityType.getEntityType(args[1]);
                if (null == type) {
                    throw new CommandFailException((Object)ChatColor.RED + "That is not a valid entity type!");
                }
                final EntitySpawner spawner2 = new EntitySpawner(type, player.getLocation());
                this.send((Object)ChatColor.GREEN + "New entity spawner has been created at " + (Object)ChatColor.YELLOW + SUtil.prettify(spawner2.getLocation()) + " with the type " + spawner2.getType().getGenericInstance());
                break;
            }
            case 1: {
                final int index = Integer.parseInt(args[1]) - 1;
                final List<EntitySpawner> spawners2 = EntitySpawner.getSpawners();
                if (0 > index || index > spawners2.size() - 1) {
                    throw new CommandFailException((Object)ChatColor.RED + "There is no spawner at that location!");
                }
                ((EntitySpawner)spawners2.remove(index)).delete();
                this.send((Object)ChatColor.GREEN + "Entity spawner deleted.");
                break;
            }
        }
    }
}
