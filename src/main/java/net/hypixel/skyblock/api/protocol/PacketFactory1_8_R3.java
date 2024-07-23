package net.hypixel.skyblock.api.protocol;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.utility.MinecraftReflection;
import org.bukkit.entity.Entity;
import org.bukkit.Location;

public class PacketFactory1_8_R3
{
    public static WrappedBeamPacket createPacketSquidSpawn(final Location location) {
        final Entity fakeSquid = (Entity)Accessors.getConstructorAccessor(MinecraftReflection.getCraftBukkitClass("entity.CraftSquid"), new Class[] { MinecraftReflection.getCraftBukkitClass("CraftServer"), MinecraftReflection.getMinecraftClass("EntitySquid") }).invoke(new Object[] { null, Accessors.getConstructorAccessor(MinecraftReflection.getMinecraftClass("EntitySquid"), new Class[] { MinecraftReflection.getNmsWorldClass() }).invoke(new Object[] { null }) });
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        container.getIntegers().write(0, (Object)EIDGen.generateEID());
        container.getIntegers().write(1, (Object)94);
        container.getIntegers().write(2, (Object)(int)Math.floor(location.getX() * 32.0));
        container.getIntegers().write(3, (Object)(int)Math.floor(location.getY() * 32.0));
        container.getIntegers().write(4, (Object)(int)Math.floor(location.getZ() * 32.0));
        container.getBytes().write(0, (Object)(byte)(location.getYaw() * 256.0f / 360.0f));
        container.getBytes().write(1, (Object)(byte)(location.getPitch() * 256.0f / 360.0f));
        final WrappedDataWatcher wrapper = WrappedDataWatcher.getEntityWatcher(fakeSquid);
        wrapper.setObject(0, (Object)32);
        container.getDataWatcherModifier().write(0, (Object)wrapper);
        return new WrappedBeamPacket(container);
    }
    
    public static WrappedVillagerPacket createPacketVillagerSpawn(final Location location) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        container.getIntegers().write(0, (Object)EIDGen.generateEID());
        container.getIntegers().write(1, (Object)120);
        container.getIntegers().write(2, (Object)(int)Math.floor(location.getX() * 32.0));
        container.getIntegers().write(3, (Object)(int)Math.floor(location.getY() * 32.0));
        container.getIntegers().write(4, (Object)(int)Math.floor(location.getZ() * 32.0));
        container.getBytes().write(0, (Object)(byte)(location.getYaw() * 256.0f / 360.0f));
        container.getBytes().write(1, (Object)(byte)(location.getPitch() * 256.0f / 360.0f));
        return new WrappedVillagerPacket(container);
    }
    
    public static WrappedBeamPacket createPacketGuardianSpawn(final Location location, final WrappedBeamPacket squidPacket) {
        final Entity fakeGuardian = (Entity)Accessors.getConstructorAccessor(MinecraftReflection.getCraftBukkitClass("entity.CraftGuardian"), new Class[] { MinecraftReflection.getCraftBukkitClass("CraftServer"), MinecraftReflection.getMinecraftClass("EntityGuardian") }).invoke(new Object[] { null, Accessors.getConstructorAccessor(MinecraftReflection.getMinecraftClass("EntityGuardian"), new Class[] { MinecraftReflection.getNmsWorldClass() }).invoke(new Object[] { null }) });
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        container.getIntegers().write(0, (Object)EIDGen.generateEID());
        container.getIntegers().write(1, (Object)68);
        container.getIntegers().write(2, (Object)(int)Math.floor(location.getX() * 32.0));
        container.getIntegers().write(3, (Object)(int)Math.floor(location.getY() * 32.0));
        container.getIntegers().write(4, (Object)(int)Math.floor(location.getZ() * 32.0));
        container.getBytes().write(0, (Object)(byte)(location.getYaw() * 256.0f / 360.0f));
        container.getBytes().write(1, (Object)(byte)(location.getPitch() * 256.0f / 360.0f));
        final WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(fakeGuardian);
        watcher.setObject(0, (Object)32);
        watcher.setObject(16, (Object)0);
        watcher.setObject(17, squidPacket.getHandle().getIntegers().read(0));
        container.getDataWatcherModifier().write(0, (Object)watcher);
        return new WrappedBeamPacket(container);
    }
    
    public static WrappedBeamPacket modifyPacketEntitySpawn(final WrappedBeamPacket entitySpawnPacket, final Location location) {
        final PacketContainer container = entitySpawnPacket.getHandle();
        container.getIntegers().write(2, (Object)(int)Math.floor(location.getX() * 32.0));
        container.getIntegers().write(3, (Object)(int)Math.floor(location.getY() * 32.0));
        container.getIntegers().write(4, (Object)(int)Math.floor(location.getZ() * 32.0));
        container.getBytes().write(0, (Object)(byte)(location.getYaw() * 256.0f / 360.0f));
        container.getBytes().write(1, (Object)(byte)(location.getPitch() * 256.0f / 360.0f));
        return entitySpawnPacket;
    }
    
    public static WrappedBeamPacket createPacketEntityMove(final WrappedBeamPacket entityPacket) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        container.getIntegers().write(0, entityPacket.getHandle().getIntegers().read(0));
        return new WrappedBeamPacket(container);
    }
    
    public static WrappedBeamPacket modifyPacketEntityMove(final WrappedBeamPacket entityMovePacket, final Location location) {
        final PacketContainer container = entityMovePacket.getHandle();
        container.getIntegers().write(1, (Object)(int)Math.floor(location.getX() * 32.0));
        container.getIntegers().write(2, (Object)(int)Math.floor(location.getY() * 32.0));
        container.getIntegers().write(3, (Object)(int)Math.floor(location.getZ() * 32.0));
        container.getBytes().write(0, (Object)(byte)(location.getYaw() * 256.0f / 360.0f));
        container.getBytes().write(1, (Object)(byte)(location.getPitch() * 256.0f / 360.0f));
        return entityMovePacket;
    }
    
    public static WrappedBeamPacket createPacketRemoveEntities(final WrappedBeamPacket squidPacket, final WrappedBeamPacket guardianPacket) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        container.getIntegerArrays().write(0, (Object)new int[] { (int)squidPacket.getHandle().getIntegers().read(0), (int)guardianPacket.getHandle().getIntegers().read(0) });
        return new WrappedBeamPacket(container);
    }
}
