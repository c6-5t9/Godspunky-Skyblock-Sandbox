package net.hypixel.skyblock.nms.nmsutil.packetlistener.channel;

import java.net.SocketAddress;

public class ChannelWrapper<T>
{
    private final T channel;
    
    public ChannelWrapper(final T channel) {
        this.channel = channel;
    }
    
    public T channel() {
        return this.channel;
    }
    
    public SocketAddress getRemoteAddress() {
        return null;
    }
    
    public SocketAddress getLocalAddress() {
        return null;
    }
}
