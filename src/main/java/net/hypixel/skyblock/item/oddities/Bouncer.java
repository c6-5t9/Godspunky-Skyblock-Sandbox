package net.hypixel.skyblock.item.oddities;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ItemData;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class Bouncer implements MaterialStatistics, MaterialFunction, ItemData
{
    @Override
    public String getDisplayName() {
        return "Bouncer";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.BLOCK;
    }
    
    @Override
    public NBTTagCompound getData() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setFloat("bounce", 1.0f);
        compound.setLong("delay", 20L);
        compound.setFloat("velX", 1.0f);
        compound.setFloat("velY", 1.0f);
        compound.setFloat("velZ", 1.0f);
        return compound;
    }
}
