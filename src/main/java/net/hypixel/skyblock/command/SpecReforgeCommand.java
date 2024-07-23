package net.hypixel.skyblock.command;

import net.hypixel.skyblock.features.reforge.Reforge;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import net.hypixel.skyblock.item.SItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Reforge an item from Spec.", aliases = "sref", permission = PlayerRank.ADMIN)
public class SpecReforgeCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (1 != args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final ItemStack stack = player.getInventory().getItemInHand();
        if (null == stack) {
            throw new CommandFailException((Object)ChatColor.RED + "You don't have anything in your hand!");
        }
        final SItem sItem = SItem.find(stack);
        if (null == sItem) {
            throw new CommandFailException((Object)ChatColor.RED + "That item is un-reforgeable!");
        }
        final Reforge reforge = ReforgeType.getReforgeType(args[0]).getReforge();
        sItem.setReforge(reforge);
        this.send((Object)ChatColor.GREEN + "Your " + sItem.getType().getDisplayName(sItem.getVariant()) + " now has " + reforge.getName() + " on it.");
    }
}
