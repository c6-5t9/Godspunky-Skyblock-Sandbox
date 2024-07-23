package net.hypixel.skyblock.util;

import net.minecraft.server.v1_8_R3.NBTBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class SerialNBTTagCompound extends NBTTagCompound implements ConfigurationSerializable
{
    public SerialNBTTagCompound() {
    }
    
    public SerialNBTTagCompound(final NBTTagCompound compound) {
        for (final String k : compound.c()) {
            this.set(k, compound.get(k));
        }
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        for (final String k : this.c()) {
            final NBTBase b = this.get(k);
            if (b instanceof NBTTagCompound) {
                final SerialNBTTagCompound serial = new SerialNBTTagCompound((NBTTagCompound)b);
                for (final Map.Entry<String, Object> entry : serial.serialize().entrySet()) {
                    map.put((Object)(k + "." + (String)entry.getKey()), entry.getValue());
                }
            }
            else {
                map.put((Object)k, SUtil.getObjectFromCompound(this, k));
            }
        }
        return map;
    }
    
    public static SerialNBTTagCompound deserialize(final Map<String, Object> map) {
        final SerialNBTTagCompound compound = new SerialNBTTagCompound();
        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            String key = (String)entry.getKey();
            final String[] dir = ((String)entry.getKey()).split("\\.");
            if (dir.length >= 2) {
                key = dir[dir.length - 1];
                NBTTagCompound track = compound;
                for (int i = 0; i < dir.length - 1; ++i) {
                    if (!track.hasKey(dir[i])) {
                        track.set(dir[i], (NBTBase)new NBTTagCompound());
                    }
                    track = track.getCompound(dir[i]);
                }
                track.set(key, SUtil.getBaseFromObject(entry.getValue()));
            }
            else {
                compound.set(key, SUtil.getBaseFromObject(entry.getValue()));
            }
        }
        return compound;
    }
}
