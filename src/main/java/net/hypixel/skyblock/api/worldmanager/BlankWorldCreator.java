package net.hypixel.skyblock.api.worldmanager;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.WorldCreator;

public class BlankWorldCreator extends WorldCreator
{
    public BlankWorldCreator(final String name) {
        super(name);
    }
    
    public ChunkGenerator generator() {
        return new ChunkGenerator() {
            public ChunkGenerator.ChunkData generateChunkData(final World world, final Random random, final int x, final int z, final ChunkGenerator.BiomeGrid biome) {
                return this.createChunkData(world);
            }
            
            public byte[] generate(final World world, final Random random, final int x, final int z) {
                return new byte[32768];
            }
            
            public byte[][] generateBlockSections(final World world, final Random random, final int x, final int z, final ChunkGenerator.BiomeGrid biomes) {
                return new byte[16][16];
            }
            
            public short[][] generateExtBlockSections(final World world, final Random random, final int x, final int z, final ChunkGenerator.BiomeGrid biomes) {
                return new short[world.getMaxHeight() / 16][];
            }
        };
    }
}
