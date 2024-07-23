package net.hypixel.skyblock.util;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class BoundingBox
{
    Vector max;
    Vector min;
    
    BoundingBox(final Vector min, final Vector max) {
        this.max = max;
        this.min = min;
    }
    
    BoundingBox(final Block block) {
        final IBlockData blockData = ((CraftWorld)block.getWorld()).getHandle().getType(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        final net.minecraft.server.v1_8_R3.Block blockNative = blockData.getBlock();
        blockNative.updateShape((IBlockAccess)((CraftWorld)block.getWorld()).getHandle(), new BlockPosition(block.getX(), block.getY(), block.getZ()));
        this.min = new Vector(block.getX() + blockNative.B(), block.getY() + blockNative.D(), block.getZ() + blockNative.F());
        this.max = new Vector(block.getX() + blockNative.C(), block.getY() + blockNative.E(), block.getZ() + blockNative.G());
    }
    
    BoundingBox(final AxisAlignedBB bb) {
        this.min = new Vector(bb.a, bb.b, bb.c);
        this.max = new Vector(bb.d, bb.e, bb.f);
    }
    
    public Vector midPoint() {
        return this.max.clone().add(this.min).multiply(0.5);
    }
}
