package net.hypixel.skyblock.api.reflection;

public class ReflectionTPS implements Runnable
{
    public static int TICK_COUNT;
    public static long[] TICKS;
    public static long LAST_TICK;
    
    public static double getTPS() {
        return getTPS(100);
    }
    
    public static double getTPS(final int ticks) {
        if (ReflectionTPS.TICK_COUNT < ticks) {
            return 20.0;
        }
        final int target = (ReflectionTPS.TICK_COUNT - 1 - ticks) % ReflectionTPS.TICKS.length;
        final long elapsed = System.currentTimeMillis() - ReflectionTPS.TICKS[target];
        return ticks / (elapsed / 1000.0);
    }
    
    public static long getElapsed(final int tickID) {
        if (ReflectionTPS.TICK_COUNT - tickID >= ReflectionTPS.TICKS.length) {}
        final long time = ReflectionTPS.TICKS[tickID % ReflectionTPS.TICKS.length];
        return System.currentTimeMillis() - time;
    }
    
    public void run() {
        ReflectionTPS.TICKS[ReflectionTPS.TICK_COUNT % ReflectionTPS.TICKS.length] = System.currentTimeMillis();
        ++ReflectionTPS.TICK_COUNT;
    }
    
    static {
        ReflectionTPS.TICK_COUNT = 0;
        ReflectionTPS.TICKS = new long[600];
        ReflectionTPS.LAST_TICK = 0L;
    }
}
