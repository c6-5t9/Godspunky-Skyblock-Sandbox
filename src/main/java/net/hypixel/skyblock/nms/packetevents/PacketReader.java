package net.hypixel.skyblock.nms.packetevents;

import java.lang.reflect.Field;
import java.util.Iterator;
import net.hypixel.skyblock.user.User;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.event.SkyblockPlayerNPCClickEvent;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;
import net.hypixel.skyblock.npc.impl.SkyblockNPCManager;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelDuplexHandler;
import org.bukkit.entity.Player;

public class PacketReader
{
    private Player player;
    
    public void injectPlayer(final Player player) {
        this.player = player;
        final ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object packet) {
                if (packet instanceof PacketPlayInUseEntity) {
                    try {
                        final PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity)packet;
                        PacketReader.this.readPacket((Packet<?>)packetPlayInUseEntity, player);
                    }
                    catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    super.channelRead(channelHandlerContext, packet);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        };
        final ChannelPipeline pipeline = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), (ChannelHandler)channelDuplexHandler);
    }
    
    public void readPacket(final Packet<?> packet, final Player p) {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            final int id = (int)this.getValue(packet, "a");
            if (this.getValue(packet, "action").toString().equalsIgnoreCase("interact")) {
                for (final SkyBlockNPC npc : SkyblockNPCManager.getNPCS()) {
                    if (npc.getEntityID() == id) {
                        final SkyblockPlayerNPCClickEvent npcClickEvent = new SkyblockPlayerNPCClickEvent(this.player, npc);
                        Bukkit.getPluginManager().callEvent((Event)npcClickEvent);
                        if (npc.canSpeak(User.getUser(this.player))) {
                            npc.speak(this.player);
                        }
                        else {
                            npc.getParameters().onInteract(this.player, npc);
                        }
                    }
                }
            }
        }
    }
    
    private Object getValue(final Object instance, final String name) {
        Object result = null;
        try {
            final Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
