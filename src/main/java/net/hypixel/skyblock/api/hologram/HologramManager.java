package net.hypixel.skyblock.api.hologram;

import java.util.HashSet;
import java.util.Set;

public class HologramManager
{
    private static final Set<Hologram> HOLOGRAMS;
    
    public static void register(final Hologram hologram) {
        HologramManager.HOLOGRAMS.add((Object)hologram);
    }
    
    public static void remove(final Hologram hologram) {
        HologramManager.HOLOGRAMS.remove((Object)hologram);
    }
    
    public static Set<Hologram> getHolograms() {
        return HologramManager.HOLOGRAMS;
    }
    
    static {
        HOLOGRAMS = (Set)new HashSet();
    }
}
