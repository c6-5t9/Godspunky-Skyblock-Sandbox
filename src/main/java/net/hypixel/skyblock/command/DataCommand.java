package net.hypixel.skyblock.command;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.apache.commons.lang3.StringUtils;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Sets data for a Spec item.", permission = PlayerRank.ADMIN)
public class DataCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (3 > args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final PlayerInventory inv = player.getInventory();
        if (null == inv.getItemInHand()) {
            throw new CommandFailException((Object)ChatColor.RED + "Error! Hold an item in your hand!");
        }
        final SItem sItem = SItem.find(inv.getItemInHand());
        final String key = args[0];
        if (!sItem.hasDataFor(key)) {
            throw new CommandFailException((Object)ChatColor.RED + "Error! This item does not have data for '" + key + "'");
        }
        final String joined = StringUtils.join((Object[])args, " ", 1, args.length - 1);
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[args.length - 1].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case -891985903: {
                if (lowerCase2.equals((Object)"string")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 1958052158: {
                if (lowerCase2.equals((Object)"integer")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 104431: {
                if (lowerCase2.equals((Object)"int")) {
                    n = 2;
                    break;
                }
                break;
            }
            case 3327612: {
                if (lowerCase2.equals((Object)"long")) {
                    n = 3;
                    break;
                }
                break;
            }
            case 64711720: {
                if (lowerCase2.equals((Object)"boolean")) {
                    n = 4;
                    break;
                }
                break;
            }
            case 3029738: {
                if (lowerCase2.equals((Object)"bool")) {
                    n = 5;
                    break;
                }
                break;
            }
            case -1325958191: {
                if (lowerCase2.equals((Object)"double")) {
                    n = 6;
                    break;
                }
                break;
            }
            case 100: {
                if (lowerCase2.equals((Object)"d")) {
                    n = 7;
                    break;
                }
                break;
            }
            case 97526364: {
                if (lowerCase2.equals((Object)"float")) {
                    n = 8;
                    break;
                }
                break;
            }
            case 102: {
                if (lowerCase2.equals((Object)"f")) {
                    n = 9;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                sItem.setDataString(key, joined);
                break;
            }
            case 1:
            case 2: {
                sItem.setDataInt(key, Integer.parseInt(joined));
                break;
            }
            case 3: {
                sItem.setDataLong(key, Long.parseLong(joined));
                break;
            }
            case 4:
            case 5: {
                sItem.setDataBoolean(key, Boolean.parseBoolean(joined));
                break;
            }
            case 6:
            case 7: {
                sItem.setDataDouble(key, Double.parseDouble(joined));
                break;
            }
            case 8:
            case 9: {
                sItem.setDataFloat(key, Float.parseFloat(joined));
                break;
            }
        }
        sItem.update();
        this.send((Object)ChatColor.GREEN + "'" + key + "' for this item has been set to '" + joined + "' as type '" + args[args.length - 1].toLowerCase() + "'");
    }
}
