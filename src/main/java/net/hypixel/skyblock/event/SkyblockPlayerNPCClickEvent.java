package net.hypixel.skyblock.event;

import net.hypixel.skyblock.npc.impl.SkyBlockNPC;
import org.bukkit.entity.Player;

public class SkyblockPlayerNPCClickEvent extends SkyblockEvent
{
    private Player player;
    private SkyBlockNPC npc;
    
    public Player getPlayer() {
        return this.player;
    }
    
    public SkyBlockNPC getNpc() {
        return this.npc;
    }
    
    public SkyblockPlayerNPCClickEvent(final Player player, final SkyBlockNPC npc) {
        this.player = player;
        this.npc = npc;
    }
}
