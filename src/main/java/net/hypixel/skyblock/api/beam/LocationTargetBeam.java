package net.hypixel.skyblock.api.beam;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.api.protocol.PacketFactory1_8_R3;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import net.hypixel.skyblock.api.protocol.WrappedBeamPacket;

public class LocationTargetBeam
{
    private final WrappedBeamPacket packetSquidSpawn;
    private final WrappedBeamPacket packetSquidMove;
    private final WrappedBeamPacket packetGuardianSpawn;
    private final WrappedBeamPacket packetGuardianMove;
    private final WrappedBeamPacket packetRemoveEntities;
    
    public LocationTargetBeam(final Location startingPosition, final Location endingPosition) {
        Preconditions.checkNotNull((Object)startingPosition, (Object)"startingPosition cannot be null");
        Preconditions.checkNotNull((Object)endingPosition, (Object)"endingPosition cannot be null");
        Preconditions.checkState(startingPosition.getWorld().equals(endingPosition.getWorld()), (Object)"startingPosition and endingPosition must be in the same world");
        this.packetSquidSpawn = PacketFactory1_8_R3.createPacketSquidSpawn(endingPosition);
        this.packetSquidMove = PacketFactory1_8_R3.createPacketEntityMove(this.packetSquidSpawn);
        this.packetGuardianSpawn = PacketFactory1_8_R3.createPacketGuardianSpawn(startingPosition, this.packetSquidSpawn);
        this.packetGuardianMove = PacketFactory1_8_R3.createPacketEntityMove(this.packetGuardianSpawn);
        this.packetRemoveEntities = PacketFactory1_8_R3.createPacketRemoveEntities(this.packetSquidSpawn, this.packetGuardianSpawn);
    }
    
    public void start(final Player player) {
        this.packetSquidSpawn.send(player);
        this.packetGuardianSpawn.send(player);
    }
    
    public void setStartingPosition(final Player player, final Location location) {
        PacketFactory1_8_R3.modifyPacketEntitySpawn(this.packetSquidSpawn, location);
        PacketFactory1_8_R3.modifyPacketEntityMove(this.packetSquidMove, location).send(player);
    }
    
    public void setEndingPosition(final Player player, final Location location) {
        PacketFactory1_8_R3.modifyPacketEntitySpawn(this.packetGuardianSpawn, location);
        PacketFactory1_8_R3.modifyPacketEntityMove(this.packetGuardianMove, location).send(player);
    }
    
    public void cleanup(final Player player) {
        this.packetRemoveEntities.send(player);
    }
}
