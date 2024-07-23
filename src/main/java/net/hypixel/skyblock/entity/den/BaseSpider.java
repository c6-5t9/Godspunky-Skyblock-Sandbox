package net.hypixel.skyblock.entity.den;

import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.SEntityType;
import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.Location;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.EntityStatistics;

public abstract class BaseSpider implements EntityStatistics, EntityFunction
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
        if (quest.getType().getName() == "Tarantula Broodfather") {
            final Location k = killed.getLocation().clone();
            if (SUtil.random(0, 10) == 0 && quest.getType().getTier() >= 3 && quest.getType().getTier() < 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.TARANTULA_VERMIN, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 18) == 0 && quest.getType().getTier() >= 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.TARANTULA_BEAST, new Object[0]).setTarget((LivingEntity)player), 12L);
                return;
            }
            if (SUtil.random(0, 50) == 0 && quest.getType().getTier() >= 4) {
                SlayerQuest.playMinibossSpawn(k, (Entity)player);
                SUtil.delay(() -> new SEntity(k, SEntityType.MUTANT_TARANTULA, new Object[0]).setTarget((LivingEntity)player), 12L);
            }
        }
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SMaterial.STRING, EntityDropType.GUARANTEED, 1.0), new EntityDrop(SMaterial.SPIDER_EYE, EntityDropType.OCCASIONAL, 0.5) });
    }
}
