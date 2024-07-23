package net.hypixel.skyblock.nms.pingrep;

public abstract class ServerInfoPacket
{
    private PingReply reply;
    
    public ServerInfoPacket(final PingReply reply) {
        this.reply = reply;
    }
    
    public abstract void send();
    
    public PingReply getReply() {
        return this.reply;
    }
    
    public void setReply(final PingReply reply) {
        this.reply = reply;
    }
}
