package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.gui.GUIType;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", aliases = "av", permission = PlayerRank.DEFAULT)
public class CookieAnvilCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (PlayerUtils.getCookieDurationTicks(player) <= 0L) {
            this.send(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
            return;
        }
        GUIType.ANVIL.getGUI().open(player);
    }
}
