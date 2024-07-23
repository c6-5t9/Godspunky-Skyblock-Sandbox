package net.hypixel.skyblock.api.nbt;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

public class NBTManager
{
    private static NBTTagCompound getTag(final ItemStack item) {
        final net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (itemNms.hasTag()) {
            tag = itemNms.getTag();
        }
        else {
            tag = new NBTTagCompound();
        }
        return tag;
    }
    
    private static ItemStack setTag(final ItemStack item, final NBTTagCompound tag) {
        final net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        itemNms.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemNms);
    }
    
    public static ItemStack addString(final ItemStack item, final String name, final String value) {
        final NBTTagCompound tag = getTag(item);
        tag.setString(name, value);
        return setTag(item, tag);
    }
    
    public static boolean hasString(final ItemStack item, final String name, final String string) {
        final NBTTagCompound tag = getTag(item);
        return tag.hasKey(name);
    }
    
    public static String getString(final ItemStack item, final String name) {
        final NBTTagCompound tag = getTag(item);
        return tag.getString(name);
    }
}
