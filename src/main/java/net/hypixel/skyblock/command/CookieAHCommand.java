package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", aliases = "ah", permission = PlayerRank.DEFAULT)
public class CookieAHCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (PlayerUtils.getCookieDurationTicks(player) <= 0L) {
            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 0.0f, 2.0f);
            this.send(Sputnik.trans("&eYou need the &dCookie Buff &eto use this command!"));
            this.send(Sputnik.trans("&eObtain a &6Booster Cookie &efrom the community shop in the hub!"));
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
            return;
        }
        GUIType.AUCTION_HOUSE.getGUI().open(player);
    }
}
