package net.hypixel.skyblock.command;

import java.util.Iterator;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.SkyBlock;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your coin amount.", usage = "", aliases = "ssend", permission = PlayerRank.ADMIN)
public class SSend extends SCommand
{
    public Map<UUID, List<String>> servers;
    public Map<UUID, List<String>> players;
    
    public SSend() {
        this.servers = (Map<UUID, List<String>>)new HashMap();
        this.players = (Map<UUID, List<String>>)new HashMap();
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
        if (!p.hasPermission("sse.proxy.bungeesend")) {
            this.send("&cThis command is restricted!");
            return;
        }
        if (args.length != 2) {
            this.send("&cCorrect Command Usage: /ssend <all/current/specific player> <server name>");
            return;
        }
        SkyBlock.getPlugin().getBc().getServers().whenComplete((result, error) -> {
            final List list = (List)this.servers.put((Object)runUUID, (Object)result);
        });
        for (int i = 0; i < ((List)this.servers.get((Object)runUUID)).size(); ++i) {
            SLog.info(((List)this.servers.get((Object)runUUID)).get(i));
        }
        boolean isExist = false;
        String targetServer = null;
        for (final String sv : (List)this.servers.get((Object)runUUID)) {
            SLog.info(sv);
            if (!sv.equalsIgnoreCase(args[1])) {
                continue;
            }
            targetServer = sv;
            isExist = true;
        }
        if (!isExist) {
            final StringBuilder sb = new StringBuilder();
            for (int j = 0; j < ((List)this.servers.get((Object)runUUID)).size(); ++j) {
                final String server = (String)((List)this.servers.get((Object)runUUID)).get(j);
                if (j == ((List)this.servers.get((Object)runUUID)).size() - 1) {
                    sb.append(server);
                }
                else {
                    sb.append(server + ", ");
                }
            }
            this.send("&cThat server doesn't exist! &aYou may send players to these following servers: &f" + (Object)sb);
            this.servers.remove((Object)runUUID);
            this.players.remove((Object)runUUID);
            return;
        }
        final String finalTarget = targetServer;
        if (args[0].equalsIgnoreCase("all")) {
            SkyBlock.getPlugin().getBc().getPlayerList("ALL").whenComplete((result, error) -> {
                final List list = (List)this.players.put((Object)runUUID, (Object)result);
            });
            this.send("&7Hooking up request for all players you requested (All Servers)...");
            for (final String player : (List)this.players.get((Object)runUUID)) {
                SkyBlock.getPlugin().getBc().forward("ALL", "savePlayerData", "ALL_PLAYERS".getBytes());
                SkyBlock.getPlugin().getBc().sendMessage(player, "&7Hooking up request...");
                SUtil.delay(() -> {
                    SkyBlock.getPlugin().getBc().sendMessage(player, "&7Sending you to " + finalTarget + "...");
                    SkyBlock.getPlugin().getBc().connectOther(player, finalTarget);
                }, 8L);
            }
            this.servers.remove((Object)runUUID);
            this.players.remove((Object)runUUID);
        }
        else if (args[0].equalsIgnoreCase("current") || args[0].equalsIgnoreCase("cur")) {
            this.send("&7Hooking up request for all players you requested (This Server)...");
            for (final Player player2 : Bukkit.getOnlinePlayers()) {
                final User u = User.getUser(player2.getUniqueId());
                u.send("&7Hooking up request...");
                u.asyncSavingData();
                SUtil.delay(() -> {
                    u.send("&7Sending you to " + finalTarget + "...");
                    SkyBlock.getPlugin().getBc().connect(player2, finalTarget);
                }, 8L);
            }
            this.servers.remove((Object)runUUID);
            this.players.remove((Object)runUUID);
        }
        else {
            for (final String player : (List)this.players.get((Object)runUUID)) {
                if (!args[0].equalsIgnoreCase(player)) {
                    continue;
                }
                this.send("&7Hooking up request for " + player + "...");
                SkyBlock.getPlugin().getBc().sendMessage(player, "&7Hooking up request...");
                SUtil.delay(() -> {
                    SkyBlock.getPlugin().getBc().sendMessage(player, "&7Sending you to " + finalTarget + "...");
                    SkyBlock.getPlugin().getBc().connectOther(player, finalTarget);
                }, 8L);
                this.servers.remove((Object)runUUID);
                this.players.remove((Object)runUUID);
                return;
            }
            this.send("&cUnable to find that player, maybe they've gone offline?");
            this.servers.remove((Object)runUUID);
            this.players.remove((Object)runUUID);
        }
    }
}
