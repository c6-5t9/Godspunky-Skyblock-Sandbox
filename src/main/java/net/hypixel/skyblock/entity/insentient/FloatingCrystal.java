package net.hypixel.skyblock.entity.insentient;

import org.bukkit.block.BlockState;
import java.util.Iterator;
import java.util.List;
import org.bukkit.block.Block;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.nms.VelocityArmorStand;

public abstract class FloatingCrystal extends VelocityArmorStand
{
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final ArmorStand stand = (ArmorStand)entity;
        stand.setVisible(false);
        stand.setHelmet(SUtil.getSkull(this.getURL(), null));
        stand.setVelocity(new Vector(0.0, 0.1, 0.0));
        stand.setMetadata("specUnbreakableArmorStand", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                final Vector velClone = stand.getVelocity().clone();
                stand.setVelocity(new Vector(0.0, (velClone.getY() < 0.0) ? 0.1 : -0.1, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 15L, 15L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                final Location location = stand.getLocation();
                location.setYaw(stand.getLocation().getYaw() + 15.0f);
                stand.teleport(location);
                stand.getWorld().spigot().playEffect(stand.getEyeLocation().clone().add(SUtil.random(-0.5, 0.5), 0.0, SUtil.random(-0.5, 0.5)), Effect.FIREWORKS_SPARK, 24, 1, 0.0f, 0.0f, 0.0f, 1.0f, 0, 64);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                final List<Block> farmland = SUtil.getNearbyBlocks(stand.getEyeLocation(), 11, Material.SOIL);
                if (farmland.size() == 0) {
                    return;
                }
                final List<Block> possible = (List<Block>)new ArrayList();
                for (final Block block : farmland) {
                    final Block a = block.getLocation().clone().add(0.0, 1.0, 0.0).getBlock();
                    if (a.getType() == Material.AIR) {
                        possible.add((Object)a);
                    }
                }
                if (possible.size() == 0) {
                    return;
                }
                final Block above = (Block)possible.get(SUtil.random(0, possible.size() - 1));
                if (above == null) {
                    return;
                }
                above.setType(Material.CROPS);
                final BlockState state = above.getState();
                state.setRawData((byte)7);
                state.update();
                final Location blockLocation = above.getLocation();
                final Location crystalLocation = stand.getEyeLocation();
                final Vector vector = blockLocation.clone().add(0.5, 0.0, 0.5).toVector().subtract(crystalLocation.clone().toVector());
                final double count = 25.0;
                for (int i = 1; i <= 25; ++i) {
                    stand.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 25.0)), Effect.FIREWORKS_SPARK, 24, 1, 0.0f, 0.0f, 0.0f, 1.0f, 0, 64);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    protected abstract String getURL();
}
