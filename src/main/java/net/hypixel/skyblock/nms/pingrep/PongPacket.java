package net.hypixel.skyblock.nms.pingrep;

public abstract class PongPacket
{
    private PingEvent event;
    
    public PongPacket(final PingEvent event) {
        this.event = event;
    }
    
    public abstract void send();
    
    public PingEvent getEvent() {
        return this.event;
    }
    
    public void setEvent(final PingEvent event) {
        this.event = event;
    }
}
