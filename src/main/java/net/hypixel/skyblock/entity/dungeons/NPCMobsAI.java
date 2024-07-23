package net.hypixel.skyblock.entity.dungeons;

import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class NPCMobsAI extends BaseZombie
{
    private PlayerWatcher watcher;
    private String skinURL;
    private String skinURL_P2;
    private boolean useURL;
    
    @Override
    public String getEntityName() {
        return "Empty NPC Entity";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 0.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 0.0;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "adventuure", null, false);
        this.watcher = pl.getWatcher();
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)80);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        this.activeEvent(entity, sEntity);
    }
    
    public void activeEvent(final LivingEntity entity, final SEntity sEntity) {
    }
    
    public String getSkinURL() {
        return "";
    }
}
