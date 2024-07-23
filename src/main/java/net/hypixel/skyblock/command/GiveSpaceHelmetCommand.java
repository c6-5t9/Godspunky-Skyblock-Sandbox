package net.hypixel.skyblock.command;

import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "bruhbu", aliases = "gsh", permission = PlayerRank.ADMIN)
public class GiveSpaceHelmetCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (!player.isOp()) {
            return;
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final User user = sender.getUser();
        if (0 == args.length) {
            this.send((Object)ChatColor.RED + "Invaild Syntax!");
            return;
        }
        final String pgv = args[0];
        final String lore = args[1];
        final SItem sitem = SItem.of(SMaterial.HIDDEN_DONATOR_HELMET);
        sitem.setDataString("p_given", player.getName());
        if (null != Bukkit.getPlayer(pgv)) {
            sitem.setDataString("p_rcv", pgv);
            if (null != args[1]) {
                sitem.setDataString("lore_d", lore);
            }
            else {
                sitem.setDataString("lore_d", "null");
            }
            player.getInventory().addItem(new ItemStack[] { sitem.getStack() });
            this.send((Object)ChatColor.GREEN + "Done!");
        }
    }
}
