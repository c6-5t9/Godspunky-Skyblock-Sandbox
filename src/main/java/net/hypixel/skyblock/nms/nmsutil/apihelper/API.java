package net.hypixel.skyblock.nms.nmsutil.apihelper;

import org.bukkit.plugin.Plugin;

public interface API
{
    void load();
    
    void init(final Plugin p0);
    
    void disable(final Plugin p0);
}
