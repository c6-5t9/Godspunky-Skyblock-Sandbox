package net.hypixel.skyblock.item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public interface ToolStatistics extends PlayerBoostStatistics, Enchantable, Reforgable
{
    String getDisplayName();
    
    Rarity getRarity();
    
    GenericItemType getType();
    
    default boolean isStackable() {
        return false;
    }
    
    default NBTTagCompound getData() {
        return new NBTTagCompound();
    }
}
