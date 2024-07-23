package net.hypixel.skyblock.nms.nmsutil.packetlistener.channel;

import net.hypixel.skyblock.nms.nmsutil.packetlistener.Cancellable;
import java.util.Iterator;
import java.util.Collections;
import net.hypixel.skyblock.nms.nmsutil.reflection.util.AccessUtil;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.concurrent.Executors;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.IPacketListener;
import java.util.concurrent.Executor;
import java.lang.reflect.Method;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.MethodResolver;
import java.lang.reflect.Field;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.FieldResolver;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.minecraft.NMSClassResolver;

public abstract class ChannelAbstract
{
    protected static final NMSClassResolver nmsClassResolver;
    static final Class<?> EntityPlayer;
    static final Class<?> PlayerConnection;
    static final Class<?> NetworkManager;
    static final Class<?> Packet;
    static final Class<?> ServerConnection;
    static final Class<?> MinecraftServer;
    protected static final FieldResolver entityPlayerFieldResolver;
    protected static final FieldResolver playerConnectionFieldResolver;
    protected static final FieldResolver networkManagerFieldResolver;
    protected static final FieldResolver minecraftServerFieldResolver;
    protected static final FieldResolver serverConnectionFieldResolver;
    static final Field networkManager;
    static final Field playerConnection;
    static final Field serverConnection;
    static final Field connectionList;
    protected static final MethodResolver craftServerFieldResolver;
    static final Method getServer;
    final Executor addChannelExecutor;
    final Executor removeChannelExecutor;
    static final String KEY_HANDLER = "packet_handler";
    static final String KEY_PLAYER = "packet_listener_player";
    static final String KEY_SERVER = "packet_listener_server";
    private final IPacketListener iPacketListener;
    
    public ChannelAbstract(final IPacketListener iPacketListener) {
        this.addChannelExecutor = (Executor)Executors.newSingleThreadExecutor();
        this.removeChannelExecutor = (Executor)Executors.newSingleThreadExecutor();
        this.iPacketListener = iPacketListener;
    }
    
    public abstract void addChannel(final Player p0);
    
    public abstract void removeChannel(final Player p0);
    
    public void addServerChannel() {
        try {
            final Object dedicatedServer = ChannelAbstract.getServer.invoke((Object)Bukkit.getServer(), new Object[0]);
            if (dedicatedServer == null) {
                return;
            }
            final Object serverConnection = ChannelAbstract.serverConnection.get(dedicatedServer);
            if (serverConnection == null) {
                return;
            }
            final List currentList = (List)ChannelAbstract.connectionList.get(serverConnection);
            final Field superListField = AccessUtil.setAccessible(currentList.getClass().getSuperclass().getDeclaredField("list"));
            final Object list = superListField.get((Object)currentList);
            if (IListenerList.class.isAssignableFrom(list.getClass())) {
                return;
            }
            final List newList = Collections.synchronizedList((List)this.newListenerList());
            for (final Object o : currentList) {
                newList.add(o);
            }
            ChannelAbstract.connectionList.set(serverConnection, (Object)newList);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public abstract IListenerList newListenerList();
    
    protected final Object onPacketSend(final Object receiver, final Object packet, final Cancellable cancellable) {
        return this.iPacketListener.onPacketSend(receiver, packet, (org.bukkit.event.Cancellable)cancellable);
    }
    
    protected final Object onPacketReceive(final Object sender, final Object packet, final Cancellable cancellable) {
        return this.iPacketListener.onPacketReceive(sender, packet, (org.bukkit.event.Cancellable)cancellable);
    }
    
    static {
        nmsClassResolver = new NMSClassResolver();
        EntityPlayer = ChannelAbstract.nmsClassResolver.resolveSilent("EntityPlayer");
        PlayerConnection = ChannelAbstract.nmsClassResolver.resolveSilent("PlayerConnection");
        NetworkManager = ChannelAbstract.nmsClassResolver.resolveSilent("NetworkManager");
        Packet = ChannelAbstract.nmsClassResolver.resolveSilent("Packet");
        ServerConnection = ChannelAbstract.nmsClassResolver.resolveSilent("ServerConnection");
        MinecraftServer = ChannelAbstract.nmsClassResolver.resolveSilent("MinecraftServer");
        entityPlayerFieldResolver = new FieldResolver(ChannelAbstract.EntityPlayer);
        playerConnectionFieldResolver = new FieldResolver(ChannelAbstract.PlayerConnection);
        networkManagerFieldResolver = new FieldResolver(ChannelAbstract.NetworkManager);
        minecraftServerFieldResolver = new FieldResolver(ChannelAbstract.MinecraftServer);
        serverConnectionFieldResolver = new FieldResolver(ChannelAbstract.ServerConnection);
        networkManager = ChannelAbstract.playerConnectionFieldResolver.resolveSilent("networkManager");
        playerConnection = ChannelAbstract.entityPlayerFieldResolver.resolveSilent("playerConnection");
        serverConnection = ChannelAbstract.minecraftServerFieldResolver.resolveByFirstTypeSilent(ChannelAbstract.ServerConnection);
        connectionList = ChannelAbstract.serverConnectionFieldResolver.resolveByLastTypeSilent(List.class);
        craftServerFieldResolver = new MethodResolver(Bukkit.getServer().getClass());
        getServer = ChannelAbstract.craftServerFieldResolver.resolveSilent("getServer");
    }
    
    interface IChannelHandler
    {
    }
    
    interface IChannelWrapper
    {
    }
    
    interface IListenerList<E> extends List<E>
    {
    }
}
