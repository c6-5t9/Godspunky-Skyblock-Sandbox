package net.hypixel.skyblock.nms.nmsutil.apihelper;

import com.google.common.io.ByteArrayDataOutput;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class SkySimBungee
{
    private final String channel;
    private final List<String> servers;
    
    public SkySimBungee(final String channel) {
        this.servers = (List<String>)new ArrayList();
        this.channel = channel;
    }
    
    public static SkySimBungee getNewBungee() {
        return new SkySimBungee("BungeeCord");
    }
    
    public void sendData(final Player p, final String subchannel, final String args) {
        Player sender = null;
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        if (args != null) {
            out.writeUTF(args);
        }
        if (p == null) {
            sender = (Player)Iterables.getFirst((Iterable)Bukkit.getOnlinePlayers(), (Object)null);
        }
        else {
            sender = p;
        }
        if (sender != null) {
            sender.sendPluginMessage((Plugin)SkyBlock.getPlugin(), this.channel, out.toByteArray());
        }
        else {
            SLog.warn("Player object mustn't be null!");
        }
    }
}
