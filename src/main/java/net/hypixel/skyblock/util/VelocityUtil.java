package net.hypixel.skyblock.util;

import org.bukkit.util.Vector;

public class VelocityUtil
{
    public static Vector calculateVelocity(final Vector from, final Vector to, final int heightGain) {
        final double gravity = 0.115;
        final int endGain = to.getBlockY() - from.getBlockY();
        final double horizDist = Math.sqrt(distanceSquared(from, to));
        final int gain = heightGain;
        final double maxGain = (gain > endGain + gain) ? gain : ((double)(endGain + gain));
        final double a = -horizDist * horizDist / (4.0 * maxGain);
        final double b = horizDist;
        final double c = -endGain;
        final double slope = -b / (2.0 * a) - Math.sqrt(b * b - 4.0 * a * c) / (2.0 * a);
        final double vy = Math.sqrt(maxGain * 0.115);
        final double vh = vy / slope;
        final int dx = to.getBlockX() - from.getBlockX();
        final int dz = to.getBlockZ() - from.getBlockZ();
        final double mag = Math.sqrt((double)(dx * dx + dz * dz));
        final double dirx = dx / mag;
        final double dirz = dz / mag;
        final double vx = vh * dirx;
        final double vz = vh * dirz;
        return new Vector(vx, vy, vz);
    }
    
    private static double distanceSquared(final Vector from, final Vector to) {
        final double dx = to.getBlockX() - from.getBlockX();
        final double dz = to.getBlockZ() - from.getBlockZ();
        return dx * dx + dz * dz;
    }
}
