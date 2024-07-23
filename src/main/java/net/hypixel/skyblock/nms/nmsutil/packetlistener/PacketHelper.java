package net.hypixel.skyblock.nms.nmsutil.packetlistener;

import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.SentPacket;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.channel.ChannelWrapper;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.ReceivedPacket;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.PacketHandler;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.nms.nmsutil.apihelper.APIManager;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.nms.nmsutil.apihelper.API;
import org.bukkit.event.Listener;

public class PacketHelper implements IPacketListener, Listener, API
{
    private ChannelInjector channelInjector;
    public boolean injected;
    
    public PacketHelper() {
        this.injected = false;
    }
    
    public void load() {
        this.channelInjector = new ChannelInjector();
        final boolean inject = this.channelInjector.inject(this);
        this.injected = inject;
        if (inject) {
            this.channelInjector.addServerChannel();
            SLog.info("Injected custom channel handlers.");
        }
        else {
            SLog.severe("Failed to inject channel handlers");
        }
    }
    
    public void init(final Plugin plugin) {
        APIManager.registerEvents(this, (Listener)this);
        SLog.info("Adding channels for online players...");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.channelInjector.addChannel(player);
        }
    }
    
    public void disable(final Plugin plugin) {
        if (!this.injected) {
            return;
        }
        SLog.info("Removing channels for online players...");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.channelInjector.removeChannel(player);
        }
        SLog.info("Removing packet handlers (" + PacketHandler.getHandlers().size() + ")...");
        while (!PacketHandler.getHandlers().isEmpty()) {
            PacketHandler.removeHandler((PacketHandler)PacketHandler.getHandlers().get(0));
        }
    }
    
    public static boolean addPacketHandler(final PacketHandler handler) {
        return PacketHandler.addHandler(handler);
    }
    
    public static boolean removePacketHandler(final PacketHandler handler) {
        return PacketHandler.removeHandler(handler);
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        this.channelInjector.addChannel(e.getPlayer());
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        this.channelInjector.removeChannel(e.getPlayer());
    }
    
    @Override
    public Object onPacketReceive(final Object sender, final Object packet, final Cancellable cancellable) {
        ReceivedPacket receivedPacket;
        if (sender instanceof Player) {
            receivedPacket = new ReceivedPacket(packet, cancellable, (Player)sender);
        }
        else {
            receivedPacket = new ReceivedPacket(packet, cancellable, (ChannelWrapper)sender);
        }
        PacketHandler.notifyHandlers(receivedPacket);
        if (receivedPacket.getPacket() != null) {
            return receivedPacket.getPacket();
        }
        return packet;
    }
    
    @Override
    public Object onPacketSend(final Object receiver, final Object packet, final Cancellable cancellable) {
        SentPacket sentPacket;
        if (receiver instanceof Player) {
            sentPacket = new SentPacket(packet, cancellable, (Player)receiver);
        }
        else {
            sentPacket = new SentPacket(packet, cancellable, (ChannelWrapper)receiver);
        }
        PacketHandler.notifyHandlers(sentPacket);
        if (sentPacket.getPacket() != null) {
            return sentPacket.getPacket();
        }
        return packet;
    }
}
