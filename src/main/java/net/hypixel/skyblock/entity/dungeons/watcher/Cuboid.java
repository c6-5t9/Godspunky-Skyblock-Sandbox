package net.hypixel.skyblock.entity.dungeons.watcher;

import org.bukkit.Chunk;
import org.bukkit.Bukkit;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.block.Block;

public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable
{
    protected final String worldName;
    protected final int x1;
    protected final int y1;
    protected final int z1;
    protected final int x2;
    protected final int y2;
    protected final int z2;
    
    public Cuboid(final Location l1, final Location l2) {
        if (!l1.getWorld().equals(l2.getWorld())) {
            throw new IllegalArgumentException("Locations must be on the same world");
        }
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
    }
    
    public Cuboid(final Location l1) {
        this(l1, l1);
    }
    
    public Cuboid(final Cuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }
    
    public Cuboid(final World world, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }
    
    private Cuboid(final String worldName, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }
    
    public Cuboid(final Map<String, Object> map) {
        this.worldName = (String)map.get((Object)"worldName");
        this.x1 = (int)map.get((Object)"x1");
        this.x2 = (int)map.get((Object)"x2");
        this.y1 = (int)map.get((Object)"y1");
        this.y2 = (int)map.get((Object)"y2");
        this.z1 = (int)map.get((Object)"z1");
        this.z2 = (int)map.get((Object)"z2");
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"worldName", (Object)this.worldName);
        map.put((Object)"x1", (Object)this.x1);
        map.put((Object)"y1", (Object)this.y1);
        map.put((Object)"z1", (Object)this.z1);
        map.put((Object)"x2", (Object)this.x2);
        map.put((Object)"y2", (Object)this.y2);
        map.put((Object)"z2", (Object)this.z2);
        return map;
    }
    
    public Location getLowerNE() {
        return new Location(this.getWorld(), (double)this.x1, (double)this.y1, (double)this.z1);
    }
    
    public Location getUpperSW() {
        return new Location(this.getWorld(), (double)this.x2, (double)this.y2, (double)this.z2);
    }
    
    public List<Block> getBlocks() {
        final Iterator<Block> blockI = this.iterator();
        final List<Block> copy = (List<Block>)new ArrayList();
        while (blockI.hasNext()) {
            copy.add(blockI.next());
        }
        return copy;
    }
    
    public Location getCenter() {
        final int x1 = this.getUpperX() + 1;
        final int y1 = this.getUpperY() + 1;
        final int z1 = this.getUpperZ() + 1;
        return new Location(this.getWorld(), this.getLowerX() + (x1 - this.getLowerX()) / 2.0, this.getLowerY() + (y1 - this.getLowerY()) / 2.0, this.getLowerZ() + (z1 - this.getLowerZ()) / 2.0);
    }
    
    public World getWorld() {
        final World world = Bukkit.getWorld(this.worldName);
        if (world == null) {
            throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
        }
        return world;
    }
    
    public int getSizeX() {
        return this.x2 - this.x1 + 1;
    }
    
    public int getSizeY() {
        return this.y2 - this.y1 + 1;
    }
    
    public int getSizeZ() {
        return this.z2 - this.z1 + 1;
    }
    
    public int getLowerX() {
        return this.x1;
    }
    
    public int getLowerY() {
        return this.y1;
    }
    
    public int getLowerZ() {
        return this.z1;
    }
    
    public int getUpperX() {
        return this.x2;
    }
    
    public int getUpperY() {
        return this.y2;
    }
    
    public int getUpperZ() {
        return this.z2;
    }
    
    public Block[] corners() {
        final Block[] res = new Block[8];
        final World w = this.getWorld();
        res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
        res[1] = w.getBlockAt(this.x1, this.y1, this.z2);
        res[2] = w.getBlockAt(this.x1, this.y2, this.z1);
        res[3] = w.getBlockAt(this.x1, this.y2, this.z2);
        res[4] = w.getBlockAt(this.x2, this.y1, this.z1);
        res[5] = w.getBlockAt(this.x2, this.y1, this.z2);
        res[6] = w.getBlockAt(this.x2, this.y2, this.z1);
        res[7] = w.getBlockAt(this.x2, this.y2, this.z2);
        return res;
    }
    
    public Cuboid expand(final CuboidDirection dir, final int amount) {
        switch (dir) {
            case North: {
                return new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case South: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
            }
            case East: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
            }
            case West: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
            }
            case Down: {
                return new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
            }
            case Up: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + (Object)dir);
            }
        }
    }
    
    public Cuboid shift(final CuboidDirection dir, final int amount) {
        return this.expand(dir, amount).expand(dir.opposite(), -amount);
    }
    
    public Cuboid outset(final CuboidDirection dir, final int amount) {
        Cuboid c = null;
        switch (dir) {
            case Horizontal: {
                c = this.expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount).expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount);
                break;
            }
            case Vertical: {
                c = this.expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
                break;
            }
            case Both: {
                c = this.outset(CuboidDirection.Horizontal, amount).outset(CuboidDirection.Vertical, amount);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + (Object)dir);
            }
        }
        return c;
    }
    
    public Cuboid inset(final CuboidDirection dir, final int amount) {
        return this.outset(dir, -amount);
    }
    
    public boolean contains(final int x, final int y, final int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }
    
    public boolean contains(final Block b) {
        return this.contains(b.getLocation());
    }
    
    public boolean contains(final Location l) {
        return this.worldName.equals((Object)l.getWorld().getName()) && this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }
    
    public int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }
    
    public byte getAverageLightLevel() {
        long total = 0L;
        int n = 0;
        for (final Block b : this) {
            if (b.isEmpty()) {
                total += b.getLightLevel();
                ++n;
            }
        }
        return (byte)((n > 0) ? ((byte)(total / n)) : 0);
    }
    
    public Cuboid contract() {
        return this.contract(CuboidDirection.Down).contract(CuboidDirection.South).contract(CuboidDirection.East).contract(CuboidDirection.Up).contract(CuboidDirection.North).contract(CuboidDirection.West);
    }
    
    public Cuboid contract(final CuboidDirection dir) {
        Cuboid face = this.getFace(dir.opposite());
        switch (dir) {
            case Down: {
                while (face.containsOnly(0) && face.getLowerY() > this.getLowerY()) {
                    face = face.shift(CuboidDirection.Down, 1);
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face.getUpperY(), this.z2);
            }
            case Up: {
                while (face.containsOnly(0) && face.getUpperY() < this.getUpperY()) {
                    face = face.shift(CuboidDirection.Up, 1);
                }
                return new Cuboid(this.worldName, this.x1, face.getLowerY(), this.z1, this.x2, this.y2, this.z2);
            }
            case North: {
                while (face.containsOnly(0) && face.getLowerX() > this.getLowerX()) {
                    face = face.shift(CuboidDirection.North, 1);
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, face.getUpperX(), this.y2, this.z2);
            }
            case South: {
                while (face.containsOnly(0) && face.getUpperX() < this.getUpperX()) {
                    face = face.shift(CuboidDirection.South, 1);
                }
                return new Cuboid(this.worldName, face.getLowerX(), this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case East: {
                while (face.containsOnly(0) && face.getLowerZ() > this.getLowerZ()) {
                    face = face.shift(CuboidDirection.East, 1);
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.getUpperZ());
            }
            case West: {
                while (face.containsOnly(0) && face.getUpperZ() < this.getUpperZ()) {
                    face = face.shift(CuboidDirection.West, 1);
                }
                return new Cuboid(this.worldName, this.x1, this.y1, face.getLowerZ(), this.x2, this.y2, this.z2);
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + (Object)dir);
            }
        }
    }
    
    public Cuboid getFace(final CuboidDirection dir) {
        switch (dir) {
            case Down: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
            }
            case Up: {
                return new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
            }
            case North: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
            }
            case South: {
                return new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case East: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
            }
            case West: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + (Object)dir);
            }
        }
    }
    
    public boolean containsOnly(final int blockId) {
        for (final Block b : this) {
            if (b.getTypeId() != blockId) {
                return false;
            }
        }
        return true;
    }
    
    public Cuboid getBoundingCuboid(final Cuboid other) {
        if (other == null) {
            return this;
        }
        final int xMin = Math.min(this.getLowerX(), other.getLowerX());
        final int yMin = Math.min(this.getLowerY(), other.getLowerY());
        final int zMin = Math.min(this.getLowerZ(), other.getLowerZ());
        final int xMax = Math.max(this.getUpperX(), other.getUpperX());
        final int yMax = Math.max(this.getUpperY(), other.getUpperY());
        final int zMax = Math.max(this.getUpperZ(), other.getUpperZ());
        return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }
    
    public Block getRelativeBlock(final int x, final int y, final int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }
    
    public Block getRelativeBlock(final World w, final int x, final int y, final int z) {
        return w.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }
    
    public List<Chunk> getChunks() {
        final List<Chunk> res = (List<Chunk>)new ArrayList();
        final World w = this.getWorld();
        final int x1 = this.getLowerX() & 0xFFFFFFF0;
        final int x2 = this.getUpperX() & 0xFFFFFFF0;
        final int z1 = this.getLowerZ() & 0xFFFFFFF0;
        final int z2 = this.getUpperZ() & 0xFFFFFFF0;
        for (int x3 = x1; x3 <= x2; x3 += 16) {
            for (int z3 = z1; z3 <= z2; z3 += 16) {
                res.add((Object)w.getChunkAt(x3 >> 4, z3 >> 4));
            }
        }
        return res;
    }
    
    public Iterator<Block> iterator() {
        return (Iterator<Block>)new CuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }
    
    public Cuboid clone() {
        return new Cuboid(this);
    }
    
    @Override
    public String toString() {
        return "Cuboid: " + this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2;
    }
    
    public class CuboidIterator implements Iterator<Block>
    {
        private final World w;
        private final int baseX;
        private final int baseY;
        private final int baseZ;
        private int x;
        private int y;
        private int z;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        
        public CuboidIterator(final World w, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            final int x3 = 0;
            this.z = 0;
            this.y = 0;
            this.x = 0;
        }
        
        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }
        
        public Block next() {
            final Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            if (++this.x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return b;
        }
        
        public void remove() {
        }
    }
    
    public enum CuboidDirection
    {
        North, 
        East, 
        South, 
        West, 
        Up, 
        Down, 
        Horizontal, 
        Vertical, 
        Both, 
        Unknown;
        
        public CuboidDirection opposite() {
            switch (this) {
                case North: {
                    return CuboidDirection.South;
                }
                case East: {
                    return CuboidDirection.West;
                }
                case South: {
                    return CuboidDirection.North;
                }
                case West: {
                    return CuboidDirection.East;
                }
                case Horizontal: {
                    return CuboidDirection.Vertical;
                }
                case Vertical: {
                    return CuboidDirection.Horizontal;
                }
                case Up: {
                    return CuboidDirection.Down;
                }
                case Down: {
                    return CuboidDirection.Up;
                }
                case Both: {
                    return CuboidDirection.Both;
                }
                default: {
                    return CuboidDirection.Unknown;
                }
            }
        }
    }
}
