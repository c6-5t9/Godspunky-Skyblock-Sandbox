package net.hypixel.skyblock.entity.end;

import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Location;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityFunction;

public abstract class BaseEnderman implements EndermanStatistics, EntityFunction
{
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        if (!(damager instanceof Player)) {
            return;
        }
        final Player player = (Player)damager;
        final User user = User.getUser(player.getUniqueId());
        final SlayerQuest quest = user.getSlayerQuest();
        if (null == quest) {
            return;
        }
        if (0L != quest.getSpawned()) {
            return;
        }
        if ("Voidgloom Seraph" == quest.getType().getName()) {
            final Location k = killed.getLocation().clone();
            if (0 == SUtil.random(0, 8) && 3 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDLING_DEVOTEE, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (0 == SUtil.random(0, 16) && 4 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDLING_RADICAL, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (0 == SUtil.random(0, 45) && 4 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDCRAZED_MANIAC, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
        }
    }
}
