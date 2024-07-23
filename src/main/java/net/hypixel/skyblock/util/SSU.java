package net.hypixel.skyblock.util;

import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.entity.Firework;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.EntityFireworks;

public class SSU extends EntityFireworks
{
    Player[] players;
    boolean gone;
    
    public SSU(final World world, final Player... p) {
        super(world);
        this.players = null;
        this.gone = false;
        this.players = p;
        this.a(new float[] { 0.25f, 0.25f });
    }
    
    public void t_() {
        if (this.gone) {
            return;
        }
        if (!this.world.isClientSide) {
            this.gone = true;
            if (this.players != null) {
                if (this.players.length > 0) {
                    for (final Player player : this.players) {
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutEntityStatus((Entity)this, (byte)17));
                    }
                }
                else {
                    this.world.broadcastEntityEffect((Entity)this, (byte)17);
                }
            }
            this.die();
        }
    }
    
    public static void spawn(final Location location, final FireworkEffect effect, final Player... players) {
        try {
            final SSU firework = new SSU((World)((CraftWorld)location.getWorld()).getHandle(), players);
            final FireworkMeta meta = ((Firework)firework.getBukkitEntity()).getFireworkMeta();
            meta.addEffect(effect);
            ((Firework)firework.getBukkitEntity()).setFireworkMeta(meta);
            firework.setPosition(location.getX(), location.getY(), location.getZ());
            if (((CraftWorld)location.getWorld()).getHandle().addEntity((Entity)firework)) {
                firework.setInvisible(true);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
