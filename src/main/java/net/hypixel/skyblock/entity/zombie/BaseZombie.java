package net.hypixel.skyblock.entity.zombie;

import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Location;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.util.SUtil;
import java.util.Objects;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.ZombieStatistics;

public abstract class BaseZombie implements ZombieStatistics, EntityFunction
{
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
        if (Objects.equals((Object)quest.getType().getName(), (Object)"Revenant Horror") || Objects.equals((Object)quest.getType().getName(), (Object)"Atoned Horror")) {
            final Location k = killed.getLocation().clone();
            if (SUtil.random(0, 8) == 0 && quest.getType().getTier() == 3) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.REVENANT_SYCOPHANT, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 16) == 0 && quest.getType().getTier() == 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.REVENANT_CHAMPION, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 45) == 0 && quest.getType().getTier() == 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.DEFORMED_REVENANT, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
            if (SUtil.random(0, 16) == 0 && quest.getType().getTier() == 5) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.ATONED_CHAMPION, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
            if (SUtil.random(0, 40) == 0 && quest.getType().getTier() == 5) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.ATONED_REVENANT, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
        }
    }
}
