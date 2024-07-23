package net.hypixel.skyblock.command;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "", aliases = "smd", permission = PlayerRank.ADMIN)
public class StackMyDimoon extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final int stg = 0;
        final Player player = sender.getPlayer();
        final ItemStack[] iss = player.getInventory().getContents();
        for (int i = 0; i < player.getInventory().getContents().length; ++i) {
            final ItemStack itemStack = iss[i];
        }
    }
}
