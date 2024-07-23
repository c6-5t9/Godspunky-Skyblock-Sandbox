package net.hypixel.skyblock.command;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modifies the rarity of an item.", aliases = "rar", permission = PlayerRank.ADMIN)
public class SpecRarityCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length > 1) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        if (!player.isOp()) {
            return;
        }
        final ItemStack stack = player.getInventory().getItemInHand();
        if (stack == null) {
            throw new CommandFailException((Object)ChatColor.RED + "You don't have anything in your hand!");
        }
        final SItem sItem = SItem.find(stack);
        if (sItem == null) {
            throw new CommandFailException((Object)ChatColor.RED + "That item is not executable!");
        }
        if (args.length == 0) {
            this.send("Your " + sItem.getType().getDisplayName(sItem.getVariant()) + (Object)ChatColor.GRAY + " is " + sItem.getRarity().getDisplay() + (Object)ChatColor.GRAY + ".");
            return;
        }
        final Rarity prev = sItem.getRarity();
        final String s2;
        final String s = s2 = args[0];
        int n = -1;
        switch (s2.hashCode()) {
            case 3739: {
                if (s2.equals((Object)"up")) {
                    n = 0;
                    break;
                }
                break;
            }
            case -231171556: {
                if (s2.equals((Object)"upgrade")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 3089570: {
                if (s2.equals((Object)"down")) {
                    n = 2;
                    break;
                }
                break;
            }
            case 1308176501: {
                if (s2.equals((Object)"downgrade")) {
                    n = 3;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0:
            case 1: {
                sItem.upgradeRarity();
                this.send("Your " + sItem.getType().getDisplayName(sItem.getVariant()) + (Object)ChatColor.GRAY + " has been upgraded. (" + prev.getDisplay() + (Object)ChatColor.GRAY + " \u279c " + sItem.getRarity().getDisplay() + (Object)ChatColor.GRAY + ")");
                return;
            }
            case 2:
            case 3: {
                sItem.downgradeRarity();
                this.send("Your " + sItem.getType().getDisplayName(sItem.getVariant()) + (Object)ChatColor.GRAY + " has been downgraded. (" + prev.getDisplay() + (Object)ChatColor.GRAY + " \u279c " + sItem.getRarity().getDisplay() + (Object)ChatColor.GRAY + ")");
                return;
            }
            default: {
                final Rarity chosen = Rarity.getRarity(args[0]);
                if (chosen == null) {
                    throw new CommandFailException((Object)ChatColor.RED + "That rarity does not exist, sucks to be you!");
                }
                sItem.setRarity(chosen);
                this.send("Your " + sItem.getType().getDisplayName(sItem.getVariant()) + (Object)ChatColor.GRAY + "'s rarity has been modified. (" + prev.getDisplay() + (Object)ChatColor.GRAY + " \u279c " + sItem.getRarity().getDisplay() + (Object)ChatColor.GRAY + ")");
            }
        }
    }
}
