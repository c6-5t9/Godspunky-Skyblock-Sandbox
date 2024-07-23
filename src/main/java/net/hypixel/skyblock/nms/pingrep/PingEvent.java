package net.hypixel.skyblock.nms.pingrep;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;

public class PingEvent
{
    private final PingReply reply;
    private boolean cancelEvent;
    private boolean cancelPong;
    
    public PingEvent(final PingReply reply) {
        this.reply = reply;
    }
    
    public PingReply getReply() {
        return this.reply;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelEvent = cancel;
    }
    
    public void cancelPong(final boolean cancel) {
        this.cancelPong = cancel;
    }
    
    public boolean isCancelled() {
        return this.cancelEvent;
    }
    
    public boolean isPongCancelled() {
        return this.cancelPong;
    }
    
    public ServerInfoPacket createNewPacket(final PingReply reply) {
        try {
            final String name = Bukkit.getServer().getClass().getPackage().getName();
            final String version = name.substring(name.lastIndexOf(46) + 1);
            final Class<?> packet = Class.forName("net.hypixel.skyblock.nms.pingrep." + version + ".ServerInfoPacketHandler");
            final Constructor<?> constructor = packet.getDeclaredConstructor(reply.getClass());
            return (ServerInfoPacket)constructor.newInstance(new Object[] { reply });
        }
        catch (final ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void sendPong() {
        try {
            final String name = Bukkit.getServer().getClass().getPackage().getName();
            final String version = name.substring(name.lastIndexOf(46) + 1);
            final Class<?> packet = Class.forName("net.hypixel.skyblock.nms.pingrep." + version + ".PongPacketHandler");
            final Constructor<?> constructor = packet.getDeclaredConstructor(this.getClass());
            final PongPacket pong = (PongPacket)constructor.newInstance(new Object[] { this });
            pong.send();
        }
        catch (final ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
