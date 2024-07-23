package net.hypixel.skyblock.command;

import net.hypixel.skyblock.user.UserStash;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Adds an enchantment from Spec to the specified item.", aliases = "pickupstash", permission = PlayerRank.DEFAULT)
public class PickupStashCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender.getPlayer() == null) {
            this.send("&cPoor you, but console can't stash items away, do they even... exist?");
            return;
        }
        final User u = User.getUser(sender.getPlayer().getUniqueId());
        if (u != null) {
            final UserStash us = UserStash.getStash(u.getUuid());
            us.pickUpStash();
        }
    }
}
