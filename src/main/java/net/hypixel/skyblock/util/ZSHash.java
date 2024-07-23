package net.hypixel.skyblock.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class ZSHash
{
    public static final Map<UUID, Integer> Charges;
    public static final Map<UUID, Integer> Cooldown;
    
    static {
        Charges = (Map)new HashMap();
        Cooldown = (Map)new HashMap();
    }
}
