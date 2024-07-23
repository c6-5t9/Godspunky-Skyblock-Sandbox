package net.hypixel.skyblock.user;

import org.bukkit.entity.Player;

public class CrystalInventory
{
    public static void load(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(150);
        statistics.getMaxHealth().set(150, Double.valueOf(10.0 * user.getCrystalLVL(0)));
        statistics.getDefense().set(150, Double.valueOf(5.0 * user.getCrystalLVL(1)));
        statistics.getCritDamage().set(150, Double.valueOf(0.01 * user.getCrystalLVL(2)));
        statistics.getCritChance().set(150, Double.valueOf(0.005 * user.getCrystalLVL(3)));
        statistics.getIntelligence().set(150, Double.valueOf(5.0 * user.getCrystalLVL(4)));
        statistics.getFerocity().set(150, Double.valueOf(3.0 * user.getCrystalLVL(5)));
        statistics.getMagicFind().set(150, Double.valueOf(0.02 * user.getCrystalLVL(6)));
        statistics.getStrength().set(150, Double.valueOf(5.0 * user.getCrystalLVL(7)));
        for (int i = 0; i <= 7; ++i) {
            player.sendMessage(String.valueOf(user.getCrystalLVL(i)));
        }
    }
}
