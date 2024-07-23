package net.hypixel.skyblock.util;

import org.bukkit.Effect;
import org.bukkit.World;
import java.util.Iterator;
import java.util.ArrayList;
import org.bukkit.util.Vector;

public class RayTracing
{
    Vector origin;
    Vector direction;
    
    RayTracing(final Vector origin, final Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }
    
    public Vector getPostion(final double blocksAway) {
        return this.origin.clone().add(this.direction.clone().multiply(blocksAway));
    }
    
    public boolean isOnLine(final Vector position) {
        final double t = (position.getX() - this.origin.getX()) / this.direction.getX();
        return position.getBlockY() == this.origin.getY() + t * this.direction.getY() && position.getBlockZ() == this.origin.getZ() + t * this.direction.getZ();
    }
    
    public ArrayList<Vector> traverse(final double blocksAway, final double accuracy) {
        final ArrayList<Vector> positions = (ArrayList<Vector>)new ArrayList();
        for (double d = 0.0; d <= blocksAway; d += accuracy) {
            positions.add((Object)this.getPostion(d));
        }
        return positions;
    }
    
    public Vector positionOfIntersection(final Vector min, final Vector max, final double blocksAway, final double accuracy) {
        final ArrayList<Vector> positions = this.traverse(blocksAway, accuracy);
        for (final Vector position : positions) {
            if (intersects(position, min, max)) {
                return position;
            }
        }
        return null;
    }
    
    public boolean intersects(final Vector min, final Vector max, final double blocksAway, final double accuracy) {
        final ArrayList<Vector> positions = this.traverse(blocksAway, accuracy);
        for (final Vector position : positions) {
            if (intersects(position, min, max)) {
                return true;
            }
        }
        return false;
    }
    
    public Vector positionOfIntersection(final BoundingBox boundingBox, final double blocksAway, final double accuracy) {
        final ArrayList<Vector> positions = this.traverse(blocksAway, accuracy);
        for (final Vector position : positions) {
            if (intersects(position, boundingBox.min, boundingBox.max)) {
                return position;
            }
        }
        return null;
    }
    
    public boolean intersects(final BoundingBox boundingBox, final double blocksAway, final double accuracy) {
        final ArrayList<Vector> positions = this.traverse(blocksAway, accuracy);
        for (final Vector position : positions) {
            if (intersects(position, boundingBox.min, boundingBox.max)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean intersects(final Vector position, final Vector min, final Vector max) {
        return position.getX() >= min.getX() && position.getX() <= max.getX() && position.getY() >= min.getY() && position.getY() <= max.getY() && position.getZ() >= min.getZ() && position.getZ() <= max.getZ();
    }
    
    public void highlight(final World world, final double blocksAway, final double accuracy) {
        for (final Vector position : this.traverse(blocksAway, accuracy)) {
            world.playEffect(position.toLocation(world), Effect.COLOURED_DUST, 0);
        }
    }
}
