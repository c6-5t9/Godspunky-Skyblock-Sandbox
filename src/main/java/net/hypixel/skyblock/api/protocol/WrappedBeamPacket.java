package net.hypixel.skyblock.api.protocol;

import java.lang.reflect.InvocationTargetException;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.entity.Player;
import com.comphenix.protocol.events.PacketContainer;

public class WrappedBeamPacket
{
    private final PacketContainer handle;
    
    public WrappedBeamPacket(final PacketContainer container) {
        this.handle = container;
    }
    
    public void send(final Player receiver) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
        }
        catch (final InvocationTargetException ex) {
            throw new RuntimeException("son of a bitch, did you actually fuck sth up????.", (Throwable)ex);
        }
    }
    
    public PacketContainer getHandle() {
        return this.handle;
    }
}
