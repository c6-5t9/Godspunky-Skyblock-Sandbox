package net.hypixel.skyblock.listener;

import net.minecraft.server.v1_8_R3.Packet;
import net.hypixel.skyblock.nms.packetevents.PacketSentServerSideEvent;
import net.hypixel.skyblock.api.disguise.PlayerDisguise;
import java.util.List;
import net.hypixel.skyblock.nms.pingrep.PingReply;
import net.hypixel.skyblock.command.RebootServerCommand;
import java.util.ArrayList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.nms.packetevents.SkySimServerPingEvent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import java.util.UUID;
import net.hypixel.skyblock.user.User;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.ReceivedPacket;
import org.bukkit.GameMode;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.hypixel.skyblock.SkyBlock;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.hypixel.skyblock.nms.packetevents.PacketReceiveServerSideEvent;
import net.hypixel.skyblock.config.Config;

public class PacketListener extends PListener
{
    public static Config config;
    
    @EventHandler
    public void onBookCrashPacket(final PacketReceiveServerSideEvent event) {
        final ReceivedPacket packet = event.getWrappedPacket();
        if (packet.getPacket() instanceof PacketPlayInCustomPayload) {
            final String packetType = ((PacketPlayInCustomPayload)packet.getPacket()).a();
            if (packetType.toLowerCase().contains((CharSequence)"bedit") || packetType.toLowerCase().contains((CharSequence)"bsign")) {
                packet.setCancelled(true);
                final Player p = SkyBlock.findPlayerByIPAddress(packet.getChannel().getRemoteAddress().toString());
                if (null != p) {
                    this.punish(p);
                }
            }
        }
        if (packet.getPacket() instanceof PacketPlayInSetCreativeSlot) {
            final PacketPlayInSetCreativeSlot pks = (PacketPlayInSetCreativeSlot)packet.getPacket();
            final Player p = packet.getPlayer();
            if (null != p && GameMode.CREATIVE != p.getGameMode()) {
                packet.setCancelled(true);
                this.punish(p);
            }
        }
    }
    
    void punish(final Player p) {
        p.sendMessage(Sputnik.trans("&cHey kiddo, you want to crash the server huh? Nice try idiot, your IP address has been logged, enjoy!"));
    }
    
    @EventHandler
    public void signInputListener(final PacketReceiveServerSideEvent event) {
        final ReceivedPacket packet = event.getWrappedPacket();
        if (packet.getPacket() instanceof PacketPlayInUpdateSign) {
            if (null == packet.getPlayer()) {
                return;
            }
            final UUID p = packet.getPlayer().getUniqueId();
            final IChatBaseComponent[] ic = ((PacketPlayInUpdateSign)packet.getPacket()).b();
            final User u = User.getUser(p);
            if (null != u && u.isWaitingForSign() && !u.isCompletedSign()) {
                u.setSignContent(ic[0].getText());
                u.setCompletedSign(true);
            }
        }
    }
    
    @EventHandler
    void onPing(final SkySimServerPingEvent e) {
        final PingReply pr = e.getPingReply();
        if (Bukkit.getServer().hasWhitelist()) {
            pr.setProtocolName((Object)ChatColor.RED + "Maintenance");
            final List<String> sample = (List<String>)new ArrayList();
            pr.setMOTD(Sputnik.trans("             &aHypixel Network &c[1.8-1.17]&r\n       &c&lSERVER UNDER MAINTENANCE"));
            sample.add((Object)Sputnik.trans("&bJoin our &9Discord &bserver for more info"));
            sample.add((Object)((Object)ChatColor.GOLD + PacketListener.config.getString("discord")));
            pr.setPlayerSample(sample);
            pr.setProtocolVersion(-1);
            return;
        }
        if (!RebootServerCommand.secondMap.containsKey((Object)Bukkit.getServer())) {
            final List<String> sample = (List<String>)new ArrayList();
            sample.add((Object)Sputnik.trans("&cPowered by &6Hypixel Engine&c"));
            pr.setPlayerSample(sample);
            pr.setProtocolName((Object)ChatColor.DARK_RED + "SkyBlockEngine 1.8.x - 1.20");
        }
        else {
            pr.setProtocolName((Object)ChatColor.RED + "\u26a0 Restarting Soon!");
            final List<String> sample = (List<String>)new ArrayList();
            sample.add((Object)Sputnik.trans("&4\u26a0 &cThis server instance is performing a"));
            sample.add((Object)Sputnik.trans("&cscheduled or a server update reboot"));
            sample.add((Object)Sputnik.trans("&cWe do not suggest joining the server right"));
            sample.add((Object)Sputnik.trans("&cnow, please wait until the reboot is complete!"));
            pr.setPlayerSample(sample);
            pr.setProtocolVersion(-1);
        }
        pr.setMOTD(Sputnik.trans("             &aHypixel Network &c[1.8-1.20]&r\n  &c&lDungeon & Enderman Slayer! &8\u279c &a&lNOW LIVE!"));
        pr.setMaxPlayers(50);
    }
    
    @EventHandler
    public void onPacketIn(final PacketReceiveServerSideEvent event) {
        PlayerDisguise.packetInManager(event.getPacket());
    }
    
    @EventHandler
    public void onPacketOut(final PacketSentServerSideEvent event) {
        PlayerDisguise.packetOutManager((Packet<?>)event.getPacket());
    }
    
    static {
        PacketListener.config = SkyBlock.getInstance().config;
    }
}
