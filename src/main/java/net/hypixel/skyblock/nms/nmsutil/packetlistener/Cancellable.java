package net.hypixel.skyblock.nms.nmsutil.packetlistener;

public class Cancellable implements org.bukkit.event.Cancellable
{
    private boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }
}
