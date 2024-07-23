package net.hypixel.skyblock.entity;

public final class StaticGiantManager
{
    public static boolean ACTIVE;
    public static SEntity GIANT;
    
    public static void endFight() {
        if (StaticGiantManager.GIANT == null) {
            return;
        }
        StaticGiantManager.ACTIVE = false;
        StaticGiantManager.GIANT = null;
    }
    
    static {
        StaticGiantManager.ACTIVE = false;
        StaticGiantManager.GIANT = null;
    }
}
