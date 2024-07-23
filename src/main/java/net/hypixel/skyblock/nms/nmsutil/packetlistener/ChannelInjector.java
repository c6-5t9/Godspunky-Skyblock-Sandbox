package net.hypixel.skyblock.nms.nmsutil.packetlistener;

import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.ConstructorResolver;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.channel.ChannelAbstract;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.ClassResolver;

public class ChannelInjector
{
    private static final ClassResolver CLASS_RESOLVER;
    private ChannelAbstract channel;
    
    public boolean inject(final IPacketListener iPacketListener) {
        final List<Exception> exceptions = (List<Exception>)new ArrayList();
        try {
            Class.forName("io.netty.channel.Channel");
            this.channel = this.newChannelInstance(iPacketListener, "net.hypixel.skyblock.nms.nmsutil.packetlistener.channel.INCChannel");
            System.out.println("[SkyBlock Protocol Injector] Using INChannel");
            return true;
        }
        catch (final Exception e1) {
            exceptions.add((Object)e1);
            for (final Exception e2 : exceptions) {
                e2.printStackTrace();
            }
            return false;
        }
    }
    
    protected ChannelAbstract newChannelInstance(final IPacketListener iPacketListener, final String clazzName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (ChannelAbstract)new ConstructorResolver(ChannelInjector.CLASS_RESOLVER.resolve(clazzName)).resolve((Class<?>[][])new Class[][] { { IPacketListener.class } }).newInstance(new Object[] { iPacketListener });
    }
    
    public void addChannel(final Player p) {
        this.channel.addChannel(p);
    }
    
    public void removeChannel(final Player p) {
        this.channel.removeChannel(p);
    }
    
    public void addServerChannel() {
        this.channel.addServerChannel();
    }
    
    static {
        CLASS_RESOLVER = new ClassResolver();
    }
}
