package net.hypixel.skyblock.item;

import java.util.List;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public interface ItemData
{
    NBTTagCompound getData();
    
    default List<String> getDataLore(final String key, final Object value) {
        return null;
    }
}
