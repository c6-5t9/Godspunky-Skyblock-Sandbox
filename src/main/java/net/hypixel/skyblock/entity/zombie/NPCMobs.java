package net.hypixel.skyblock.entity.zombie;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.entity.LivingEntity;

public interface NPCMobs
{
    default PlayerDisguise onEntitySpawn(final LivingEntity entity, final String url1, final String url2, final boolean apl1) {
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, url1, url2, apl1);
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        return pl;
    }
}
