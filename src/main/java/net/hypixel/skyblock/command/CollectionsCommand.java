package net.hypixel.skyblock.command;

import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.collection.ItemCollection;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your collections.", permission = PlayerRank.ADMIN)
public class CollectionsCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 3) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final ItemCollection collection = ItemCollection.getByIdentifier(args[0]);
        if (collection == null) {
            throw new CommandFailException("Could not find the specified collection!");
        }
        final User user = sender.getUser();
        final int amount = Integer.parseInt(args[2]);
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[1].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case 96417: {
                if (lowerCase2.equals((Object)"add")) {
                    n = 0;
                    break;
                }
                break;
            }
            case -2060248300: {
                if (lowerCase2.equals((Object)"subtract")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 114240: {
                if (lowerCase2.equals((Object)"sub")) {
                    n = 2;
                    break;
                }
                break;
            }
            case 113762: {
                if (lowerCase2.equals((Object)"set")) {
                    n = 3;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                user.addToCollection(collection, amount);
                this.send("You have added " + SUtil.commaify(amount) + " to your " + collection.getName() + " collection.");
                return;
            }
            case 1:
            case 2: {
                user.setCollection(collection, user.getCollection(collection) - amount);
                this.send("You have subtracted " + SUtil.commaify(amount) + " from your " + collection.getName() + " collection.");
                return;
            }
            case 3: {
                user.setCollection(collection, amount);
                this.send("You have set your " + collection.getName() + " collection to " + SUtil.commaify(amount) + ".");
                return;
            }
            default: {
                throw new CommandArgumentException();
            }
        }
    }
}
