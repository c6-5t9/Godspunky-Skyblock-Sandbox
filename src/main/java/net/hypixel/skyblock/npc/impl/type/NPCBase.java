package net.hypixel.skyblock.npc.impl.type;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

public interface NPCBase
{
    void hide(final Player p0);
    
    default void hideNameTag(final Player player) {
    }
    
    void show(final Player p0);
    
    void sendRotation(final Player p0);
    
    default void sendPacket(final Player player, final Packet<?> packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    void setLocation(final Location p0);
    
    int entityId();
}
