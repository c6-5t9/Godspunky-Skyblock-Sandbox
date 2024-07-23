package net.hypixel.skyblock.nms.pingrep;

import net.hypixel.skyblock.nms.pingrep.reflect.ReflectUtils;
import java.util.ArrayList;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftIconCache;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ServerPing;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

public class ServerInfoPacketHandler extends ServerInfoPacket
{
    private static final Field SERVER_PING_FIELD;
    
    public ServerInfoPacketHandler(final PingReply reply) {
        super(reply);
    }
    
    @Override
    public void send() {
        try {
            final Field field = this.getReply().getClass().getDeclaredField("ctx");
            field.setAccessible(true);
            final Object ctx = field.get((Object)this.getReply());
            final Method writeAndFlush = ctx.getClass().getMethod("writeAndFlush", Object.class);
            writeAndFlush.setAccessible(true);
            writeAndFlush.invoke(ctx, new Object[] { constructPacket(this.getReply()) });
        }
        catch (final NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }
    
    public static PacketStatusOutServerInfo constructPacket(final PingReply reply) {
        final GameProfile[] sample = new GameProfile[reply.getPlayerSample().size()];
        final List<String> list = reply.getPlayerSample();
        for (int i = 0; i < list.size(); ++i) {
            sample[i] = new GameProfile(UUID.randomUUID(), (String)list.get(i));
        }
        final ServerPing.ServerPingPlayerSample playerSample = new ServerPing.ServerPingPlayerSample(reply.getMaxPlayers(), reply.getOnlinePlayers());
        playerSample.a(sample);
        final ServerPing ping = new ServerPing();
        ping.setMOTD((IChatBaseComponent)new ChatComponentText(reply.getMOTD()));
        ping.setPlayerSample(playerSample);
        ping.setServerInfo(new ServerPing.ServerData(reply.getProtocolName(), reply.getProtocolVersion()));
        ping.setFavicon(((CraftIconCache)reply.getIcon()).value);
        return new PacketStatusOutServerInfo(ping);
    }
    
    public static PingReply constructReply(final PacketStatusOutServerInfo packet, final ChannelHandlerContext ctx) {
        try {
            final ServerPing ping = (ServerPing)ServerInfoPacketHandler.SERVER_PING_FIELD.get((Object)packet);
            final String motd = IChatBaseComponent.ChatSerializer.a(ping.a());
            final int max = ping.b().a();
            final int online = ping.b().b();
            final int protocolVersion = ping.c().b();
            final String protocolName = ping.c().a();
            final GameProfile[] profiles = ping.b().c();
            final List<String> list = (List<String>)new ArrayList();
            for (int i = 0; i < profiles.length; ++i) {
                list.add((Object)profiles[i].getName());
            }
            final PingReply reply = new PingReply(ctx, motd, online, max, protocolVersion, protocolName, list);
            return reply;
        }
        catch (final IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        SERVER_PING_FIELD = ReflectUtils.getFirstFieldByType(PacketStatusOutServerInfo.class, ServerPing.class);
    }
}
