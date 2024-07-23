package net.hypixel.skyblock.gui;

import org.bukkit.plugin.Plugin;

public class SkySimBrainCell
{
    public void accessAIFrom(final BrainCellFor bcf) {
    }
    
    public static void applyAIToNMSPlayer(final Object p, final int limit, final SkySimBrainCell sbc) {
    }
    
    public void getModules() {
    }
    
    public void startTraining(final int level, final Plugin pl, final int limit, final BrainCellFor bcf) {
    }
    
    public static SkySimBrainCell loadFromDB(final String str) {
        return new SkySimBrainCell();
    }
    
    public enum BrainCellFor
    {
        MELEE, 
        MOVEMENT, 
        BOW_ATTACK, 
        ENTITY_TRACKER, 
        ABILITY_USAGE, 
        ATTACK_PLAYER;
    }
}
