package net.hypixel.skyblock.command;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "", aliases = "bbc", permission = PlayerRank.ADMIN)
public class BuyCookieCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player.isOp()) {
            final int amount = Integer.parseInt(args[0]);
            final PlayerInventory inv = player.getInventory();
            for (int i = 0; i < amount; ++i) {
                final ItemStack stack = SItem.of(SMaterial.HIDDEN_BOOSTER_COOKIE).getStack();
                Sputnik.smartGiveItem(stack, player);
            }
        }
        else {
            this.send((Object)ChatColor.RED + "Unknown Command.");
        }
    }
}
