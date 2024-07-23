package net.hypixel.skyblock.npc.impl;

import java.util.HashSet;
import java.util.Set;

public class SkyblockNPCManager
{
    private static final Set<SkyBlockNPC> SKYBLOCK_NPCS;
    
    public static void registerNPC(final SkyBlockNPC skyblockNPC) {
        SkyblockNPCManager.SKYBLOCK_NPCS.add((Object)skyblockNPC);
    }
    
    public static Set<SkyBlockNPC> getNPCS() {
        return SkyblockNPCManager.SKYBLOCK_NPCS;
    }
    
    static {
        SKYBLOCK_NPCS = (Set)new HashSet();
    }
}
