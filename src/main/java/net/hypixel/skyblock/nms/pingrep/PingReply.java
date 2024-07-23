package net.hypixel.skyblock.nms.pingrep;

import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;
import java.util.List;

public class PingReply
{
    private final Object ctx;
    private String motd;
    private int onlinePlayers;
    private int maxPlayers;
    private int protocolVersion;
    private String protocolName;
    private List<String> playerSample;
    private boolean hidePlayerSample;
    private CachedServerIcon icon;
    
    public PingReply(final Object ctx, final String motd, final int onlinePlayers, final int maxPlayers, final int protocolVersion, final String protocolName, final List<String> playerSample) {
        this.hidePlayerSample = false;
        this.icon = Bukkit.getServerIcon();
        this.ctx = ctx;
        this.motd = motd;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.protocolVersion = protocolVersion;
        this.protocolName = protocolName;
        this.playerSample = playerSample;
    }
    
    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String getMOTD() {
        return this.motd;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public String getProtocolName() {
        return this.protocolName;
    }
    
    public List<String> getPlayerSample() {
        return this.playerSample;
    }
    
    public boolean isPlayerSampleHidden() {
        return this.hidePlayerSample;
    }
    
    public CachedServerIcon getIcon() {
        return this.icon;
    }
    
    public void setOnlinePlayers(final int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }
    
    public void setMaxPlayers(final int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    public void setMOTD(final String motd) {
        this.motd = motd;
    }
    
    public void setProtocolVersion(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    public void setProtocolName(final String protocolName) {
        this.protocolName = protocolName;
    }
    
    public void setPlayerSample(final List<String> playerSample) {
        this.playerSample = playerSample;
    }
    
    public void hidePlayerSample(final boolean hidePlayerSample) {
        this.hidePlayerSample = hidePlayerSample;
    }
    
    public void setIcon(final CachedServerIcon icon) {
        this.icon = icon;
    }
}
