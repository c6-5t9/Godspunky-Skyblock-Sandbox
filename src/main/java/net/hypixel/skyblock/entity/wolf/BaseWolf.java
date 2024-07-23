package net.hypixel.skyblock.entity.wolf;

import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Location;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.user.User;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import net.hypixel.skyblock.util.SUtil;
import java.util.stream.Collectors;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.EntityFunction;

public abstract class BaseWolf implements WolfStatistics, EntityFunction
{
    private LivingEntity target;
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    return;
                }
                if (BaseWolf.this.target == null || BaseWolf.this.target.isDead()) {
                    BaseWolf.this.target = null;
                }
                if (BaseWolf.this.target != null) {
                    return;
                }
                final Player found = SUtil.getRandom((java.util.List<Player>)entity.getNearbyEntities(16.0, 4.0, 16.0).stream().filter(e -> e instanceof Player && (((Player)e).getGameMode() == GameMode.SURVIVAL || ((Player)e).getGameMode() == GameMode.ADVENTURE)).collect(Collectors.toList()));
                BaseWolf.this.target = (LivingEntity)found;
                ((Wolf)entity).setTarget(BaseWolf.this.target);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        if (!(damager instanceof Player)) {
            return;
        }
        final Player player = (Player)damager;
        final User user = User.getUser(player.getUniqueId());
        final SlayerQuest quest = user.getSlayerQuest();
        if (quest == null) {
            return;
        }
        if (quest.getSpawned() != 0L) {
            return;
        }
        if (quest.getType().getName() == "Sven Packmaster") {
            final Location k = killed.getLocation().clone();
            if (SUtil.random(0, 8) == 0 && quest.getType().getTier() >= 3 && quest.getType().getTier() < 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.SVEN_FOLLOWER, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 12) == 0 && quest.getType().getTier() >= 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.PACK_ENFORCER, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 25) == 0 && quest.getType().getTier() >= 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.SVEN_ALPHA, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
        }
    }
}
