package net.hypixel.skyblock.util;

import java.util.ArrayDeque;
import com.google.common.collect.Iterables;
import java.util.Collection;
import com.google.common.io.ByteArrayDataInput;
import java.util.Arrays;
import java.net.InetSocketAddress;
import java.util.List;
import com.google.common.io.ByteArrayDataOutput;
import org.bukkit.entity.Player;
import com.google.common.io.ByteStreams;
import java.util.function.BiFunction;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.Queue;
import java.util.Map;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.Plugin;
import java.util.WeakHashMap;

public class BungeeChannel
{
    private static WeakHashMap<Plugin, BungeeChannel> registeredInstances;
    private final PluginMessageListener messageListener;
    private final Plugin plugin;
    private final Map<String, Queue<CompletableFuture<?>>> callbackMap;
    private Map<String, ForwardConsumer> forwardListeners;
    private ForwardConsumer globalForwardListener;
    
    public static synchronized BungeeChannel of(final Plugin plugin) {
        return (BungeeChannel)BungeeChannel.registeredInstances.compute((Object)plugin, (k, v) -> {
            if (v == null) {
                v = new BungeeChannel(plugin);
            }
            return v;
        });
    }
    
    public BungeeChannel(final Plugin plugin) {
        this.plugin = (Plugin)Objects.requireNonNull((Object)plugin, "plugin cannot be null");
        this.callbackMap = (Map<String, Queue<CompletableFuture<?>>>)new HashMap();
        synchronized (BungeeChannel.registeredInstances) {
            BungeeChannel.registeredInstances.compute((Object)plugin, (k, oldInstance) -> {
                if (oldInstance != null) {
                    oldInstance.unregister();
                }
                return this;
            });
        }
        this.messageListener = this::onPluginMessageReceived;
        final Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
        messenger.registerIncomingPluginChannel(plugin, "BungeeCord", this.messageListener);
    }
    
    public void registerForwardListener(final ForwardConsumer globalListener) {
        this.globalForwardListener = globalListener;
    }
    
    public void registerForwardListener(final String channelName, final ForwardConsumer listener) {
        if (this.forwardListeners == null) {
            this.forwardListeners = (Map<String, ForwardConsumer>)new HashMap();
        }
        synchronized (this.forwardListeners) {
            this.forwardListeners.put((Object)channelName, (Object)listener);
        }
    }
    
    public CompletableFuture<Integer> getPlayerCount(final String serverName) {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<Integer> future = (CompletableFuture<Integer>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)("PlayerCount-" + serverName), (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public CompletableFuture<List<String>> getPlayerList(final String serverName) {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<List<String>> future = (CompletableFuture<List<String>>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)("PlayerList-" + serverName), (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerList");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public CompletableFuture<List<String>> getServers() {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<List<String>> future = (CompletableFuture<List<String>>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)"GetServers", (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServers");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public void connect(final Player player, final String serverName) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    public void connectOther(final String playerName, final String server) {
        final Player player = this.getFirstPlayer();
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ConnectOther");
        output.writeUTF(playerName);
        output.writeUTF(server);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    public CompletableFuture<InetSocketAddress> getIp(final Player player) {
        final CompletableFuture<InetSocketAddress> future = (CompletableFuture<InetSocketAddress>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)"IP", (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("IP");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public void sendMessage(final String playerName, final String message) {
        final Player player = this.getFirstPlayer();
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Message");
        output.writeUTF(playerName);
        output.writeUTF(message);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    public CompletableFuture<String> getServer() {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<String> future = (CompletableFuture<String>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)"GetServer", (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServer");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public CompletableFuture<String> getUUID(final Player player) {
        final CompletableFuture<String> future = (CompletableFuture<String>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)"UUID", (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUID");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public CompletableFuture<String> getUUID(final String playerName) {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<String> future = (CompletableFuture<String>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)("UUIDOther-" + playerName), (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUIDOther");
        output.writeUTF(playerName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public CompletableFuture<InetSocketAddress> getServerIp(final String serverName) {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<InetSocketAddress> future = (CompletableFuture<InetSocketAddress>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)("ServerIP-" + serverName), (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerIP");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }
    
    public void kickPlayer(final String playerName, final String kickMessage) {
        final Player player = this.getFirstPlayer();
        final CompletableFuture<InetSocketAddress> future = (CompletableFuture<InetSocketAddress>)new CompletableFuture();
        synchronized (this.callbackMap) {
            this.callbackMap.compute((Object)"KickPlayer", (BiFunction)this.computeQueueValue(future));
        }
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("KickPlayer");
        output.writeUTF(playerName);
        output.writeUTF(kickMessage);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    public void forward(final String server, final String channelName, final byte[] data) {
        final Player player = this.getFirstPlayer();
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Forward");
        output.writeUTF(server);
        output.writeUTF(channelName);
        output.writeShort(data.length);
        output.write(data);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    public void forwardToPlayer(final String playerName, final String channelName, final byte[] data) {
        final Player player = this.getFirstPlayer();
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ForwardToPlayer");
        output.writeUTF(playerName);
        output.writeUTF(channelName);
        output.writeShort(data.length);
        output.write(data);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
    
    private void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }
        final ByteArrayDataInput input = ByteStreams.newDataInput(message);
        final String subchannel = input.readUTF();
        synchronized (this.callbackMap) {
            if (subchannel.equals((Object)"PlayerCount") || subchannel.equals((Object)"PlayerList") || subchannel.equals((Object)"UUIDOther") || subchannel.equals((Object)"ServerIP")) {
                final String identifier = input.readUTF();
                final Queue<CompletableFuture<?>> callbacks = (Queue<CompletableFuture<?>>)this.callbackMap.get((Object)(subchannel + "-" + identifier));
                if (callbacks == null || callbacks.isEmpty()) {
                    return;
                }
                final CompletableFuture<Object> callback = (CompletableFuture<Object>)callbacks.poll();
                try {
                    final String s3;
                    final String s = s3 = subchannel;
                    int n = -1;
                    switch (s3.hashCode()) {
                        case -2095967602: {
                            if (s3.equals((Object)"PlayerCount")) {
                                n = 0;
                                break;
                            }
                            break;
                        }
                        case -205896897: {
                            if (s3.equals((Object)"PlayerList")) {
                                n = 1;
                                break;
                            }
                            break;
                        }
                        case 1186775061: {
                            if (s3.equals((Object)"UUIDOther")) {
                                n = 2;
                                break;
                            }
                            break;
                        }
                        case 1443747786: {
                            if (s3.equals((Object)"ServerIP")) {
                                n = 3;
                                break;
                            }
                            break;
                        }
                    }
                    switch (n) {
                        case 0: {
                            callback.complete((Object)input.readInt());
                            break;
                        }
                        case 1: {
                            callback.complete((Object)Arrays.asList((Object[])input.readUTF().split(", ")));
                            break;
                        }
                        case 2: {
                            callback.complete((Object)input.readUTF());
                            break;
                        }
                        case 3: {
                            final String ip = input.readUTF();
                            final int port = input.readUnsignedShort();
                            callback.complete((Object)new InetSocketAddress(ip, port));
                            break;
                        }
                    }
                }
                catch (final Exception ex) {
                    callback.completeExceptionally((Throwable)ex);
                }
            }
            else {
                final Queue<CompletableFuture<?>> callbacks2 = (Queue<CompletableFuture<?>>)this.callbackMap.get((Object)subchannel);
                if (callbacks2 == null) {
                    final short dataLength = input.readShort();
                    final byte[] data = new byte[dataLength];
                    input.readFully(data);
                    if (this.globalForwardListener != null) {
                        this.globalForwardListener.accept(subchannel, player, data);
                    }
                    if (this.forwardListeners != null) {
                        synchronized (this.forwardListeners) {
                            final ForwardConsumer listener = (ForwardConsumer)this.forwardListeners.get((Object)subchannel);
                            if (listener != null) {
                                listener.accept(subchannel, player, data);
                            }
                        }
                    }
                    return;
                }
                if (callbacks2.isEmpty()) {
                    return;
                }
                final CompletableFuture<Object> callback2 = (CompletableFuture<Object>)callbacks2.poll();
                try {
                    final String s4;
                    final String s2 = s4 = subchannel;
                    int n2 = -1;
                    switch (s4.hashCode()) {
                        case 719507834: {
                            if (s4.equals((Object)"GetServers")) {
                                n2 = 0;
                                break;
                            }
                            break;
                        }
                        case -1500810727: {
                            if (s4.equals((Object)"GetServer")) {
                                n2 = 1;
                                break;
                            }
                            break;
                        }
                        case 2616251: {
                            if (s4.equals((Object)"UUID")) {
                                n2 = 2;
                                break;
                            }
                            break;
                        }
                        case 2343: {
                            if (s4.equals((Object)"IP")) {
                                n2 = 3;
                                break;
                            }
                            break;
                        }
                    }
                    switch (n2) {
                        case 0: {
                            callback2.complete((Object)Arrays.asList((Object[])input.readUTF().split(", ")));
                            break;
                        }
                        case 1:
                        case 2: {
                            callback2.complete((Object)input.readUTF());
                            break;
                        }
                        case 3: {
                            final String ip2 = input.readUTF();
                            final int port2 = input.readInt();
                            callback2.complete((Object)new InetSocketAddress(ip2, port2));
                            break;
                        }
                    }
                }
                catch (final Exception ex2) {
                    callback2.completeExceptionally((Throwable)ex2);
                }
            }
        }
    }
    
    public void unregister() {
        final Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.unregisterIncomingPluginChannel(this.plugin, "BungeeCord", this.messageListener);
        messenger.unregisterOutgoingPluginChannel(this.plugin);
        this.callbackMap.clear();
    }
    
    private BiFunction<String, Queue<CompletableFuture<?>>, Queue<CompletableFuture<?>>> computeQueueValue(final CompletableFuture<?> queueValue) {
        return (BiFunction<String, Queue<CompletableFuture<?>>, Queue<CompletableFuture<?>>>)((key, value) -> {
            if (value == null) {
                value = (Queue)new ArrayDeque();
            }
            value.add((Object)queueValue);
            return value;
        });
    }
    
    private Player getFirstPlayer() {
        final Player firstPlayer = this.getFirstPlayer0((Collection<? extends Player>)Bukkit.getOnlinePlayers());
        if (firstPlayer == null) {
            throw new IllegalArgumentException("Bungee Messaging Api requires at least one player to be online.");
        }
        return firstPlayer;
    }
    
    private Player getFirstPlayer0(final Player[] playerArray) {
        return (playerArray.length > 0) ? playerArray[0] : null;
    }
    
    private Player getFirstPlayer0(final Collection<? extends Player> playerCollection) {
        return (Player)Iterables.getFirst((Iterable)playerCollection, (Object)null);
    }
    
    static {
        BungeeChannel.registeredInstances = (WeakHashMap<Plugin, BungeeChannel>)new WeakHashMap();
    }
    
    @FunctionalInterface
    public interface ForwardConsumer
    {
        void accept(final String p0, final Player p1, final byte[] p2);
    }
}
