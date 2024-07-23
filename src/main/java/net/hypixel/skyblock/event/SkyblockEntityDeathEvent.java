package net.hypixel.skyblock.event;

import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.entity.SEntity;

public class SkyblockEntityDeathEvent extends SkyblockEvent
{
    private SEntity entity;
    private User killer;
    
    public SEntity getEntity() {
        return this.entity;
    }
    
    public User getKiller() {
        return this.killer;
    }
    
    public SkyblockEntityDeathEvent(final SEntity entity, final User killer) {
        this.entity = entity;
        this.killer = killer;
    }
}
