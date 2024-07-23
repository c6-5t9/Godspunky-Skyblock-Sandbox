package net.hypixel.skyblock.item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public interface Ownable extends ItemData
{
    default NBTTagCompound getData() {
        return new NBTTagCompound();
    }
}
