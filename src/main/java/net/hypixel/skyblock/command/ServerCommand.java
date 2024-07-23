package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.SkyBlock;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your coin amount.", usage = "", aliases = "ss", permission = PlayerRank.ADMIN)
public class ServerCommand extends SCommand
{
    public Map<UUID, List<String>> servers;
    
    public ServerCommand() {
        this.servers = (Map<UUID, List<String>>)new HashMap();
    }
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final UUID runUUID = UUID.randomUUID();
        if (SkyBlock.getPlugin().getBc() == null) {
            this.send("&cThis is not a BungeeCord based server!");
            return;
        }
        if (sender.getPlayer() == null) {
            this.send("&cConsole Sender cannot execute Proxy commands!");
            return;
        }
        final Player p = sender.getPlayer();
        if (!p.hasPermission("sse.proxy.sserver")) {
            this.send("&cThis command is restricted!");
            return;
        }
        if (args.length != 1) {
            this.send("&cCorrect Command Usage: /ss <server name>");
            return;
        }
        SkyBlock.getPlugin().getBc().getServers().whenComplete((result, error) -> {
            final List list = (List)this.servers.put((Object)runUUID, (Object)result);
        });
        boolean isExist = false;
        String targetServer = null;
        for (final String sv : (List)this.servers.get((Object)runUUID)) {
            if (sv.equalsIgnoreCase(args[0])) {
                targetServer = sv;
                isExist = true;
            }
        }
        if (!isExist && targetServer == null) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ((List)this.servers.get((Object)runUUID)).size(); ++i) {
                final String server = (String)((List)this.servers.get((Object)runUUID)).get(i);
                if (i == this.servers.size() - 1) {
                    sb.append(server);
                }
                else {
                    sb.append(server + ", ");
                }
            }
            this.send("&cThat server doesn't exist! &aYou may send players to these following servers: &f" + (Object)sb);
            this.servers.remove((Object)runUUID);
            return;
        }
        final String finalTarget = targetServer;
        if (SkyBlock.getPlugin().getServerName().equalsIgnoreCase(args[0])) {
            this.servers.remove((Object)runUUID);
            this.send("&cYou're already playing on this server");
            return;
        }
        this.send("&7Hooking up request...");
        final User u = User.getUser(p.getUniqueId());
        u.asyncSavingData();
        SUtil.delay(() -> {
            this.send("&7Sending you to " + finalTarget + "...");
            SkyBlock.getPlugin().getBc().connect(p, finalTarget);
        }, 8L);
        this.servers.remove((Object)runUUID);
    }
}
