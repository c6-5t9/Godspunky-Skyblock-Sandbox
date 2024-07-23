package net.hypixel.skyblock.util;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import net.minecraft.server.v1_8_R3.TileEntityFlowerPot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.block.Block;

public class MagicFlowerPot
{
    public static final boolean changePot(final Block flowerPot, final boolean refreshChunk, final ArmorStand s) {
        if (s.isDead()) {
            return false;
        }
        if (!s.isDead() && flowerPot.getType() == Material.FLOWER_POT) {
            try {
                final ItemStack is = new ItemStack(Material.RED_ROSE);
                final TileEntityFlowerPot tefp = (TileEntityFlowerPot)((CraftWorld)flowerPot.getWorld()).getHandle().getTileEntity(new BlockPosition(flowerPot.getX(), flowerPot.getY(), flowerPot.getZ()));
                tefp.a(CraftItemStack.asNMSCopy(is).getItem(), CraftItemStack.asNMSCopy(is).getData());
                tefp.update();
                ((CraftWorld)flowerPot.getWorld()).getHandle().notify(new BlockPosition(flowerPot.getX(), flowerPot.getY(), flowerPot.getZ()));
                if (refreshChunk) {
                    flowerPot.getWorld().refreshChunk(flowerPot.getChunk().getX(), flowerPot.getChunk().getZ());
                }
            }
            catch (final Exception ex) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public static final boolean changePotAir(final Block flowerPot, final boolean refreshChunk) {
        if (flowerPot.getType() == Material.FLOWER_POT) {
            try {
                final ItemStack is = new ItemStack(Material.AIR);
                final TileEntityFlowerPot tefp = (TileEntityFlowerPot)((CraftWorld)flowerPot.getWorld()).getHandle().getTileEntity(new BlockPosition(flowerPot.getX(), flowerPot.getY(), flowerPot.getZ()));
                tefp.a(CraftItemStack.asNMSCopy(is).getItem(), CraftItemStack.asNMSCopy(is).getData());
                tefp.update();
                ((CraftWorld)flowerPot.getWorld()).getHandle().notify(new BlockPosition(flowerPot.getX(), flowerPot.getY(), flowerPot.getZ()));
                if (refreshChunk) {
                    flowerPot.getWorld().refreshChunk(flowerPot.getChunk().getX(), flowerPot.getChunk().getZ());
                }
            }
            catch (final Exception ex) {
                return false;
            }
            return true;
        }
        return false;
    }
}
