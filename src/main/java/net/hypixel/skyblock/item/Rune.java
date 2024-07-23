package net.hypixel.skyblock.item;

import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public interface Rune extends SkullStatistics, MaterialFunction, ItemData
{
    default NBTTagCompound getData() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setByte("level", (byte)1);
        return compound;
    }
    
    default void onInstanceUpdate(final SItem instance) {
        final byte level = instance.getData().getByte("level");
        instance.setDisplayName(this.getDisplayName() + " " + SUtil.toRomanNumeral(level));
    }
}
