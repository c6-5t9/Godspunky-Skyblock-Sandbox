package net.hypixel.skyblock.command;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.potion.PotionEffect;
import net.hypixel.skyblock.features.potion.PotionEffectType;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Adds an potion from Spec to the specified item.", aliases = "spot", permission = PlayerRank.ADMIN)
public class SpecPotionCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 3) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final ItemStack stack = player.getInventory().getItemInHand();
        if (stack == null) {
            throw new CommandFailException((Object)ChatColor.RED + "You don't have anything in your hand!");
        }
        final SItem sItem = SItem.find(stack);
        if (sItem == null) {
            throw new CommandFailException((Object)ChatColor.RED + "That item is not executable!");
        }
        final PotionEffectType type = PotionEffectType.getByNamespace(args[0]);
        if (type == null) {
            throw new CommandFailException((Object)ChatColor.RED + "Invalid potion type, try again, now actually put a vaild potion type, nerd!");
        }
        final long duration = Long.parseLong(args[1]);
        final int level = Integer.parseInt(args[2]);
        if (level <= 0) {
            this.send((Object)ChatColor.RED + "What's the point?");
            return;
        }
        if (level > 2000) {
            this.send((Object)ChatColor.RED + "Cap reached! Limit is LVL 2000!");
            return;
        }
        if (type == PotionEffectType.FEROCITY && level > 15) {
            this.send((Object)ChatColor.RED + "Cap reached! Limit is LVL 15!");
            return;
        }
        sItem.addPotionEffect(new PotionEffect(type, level, duration));
        this.send((Object)ChatColor.GREEN + "Your " + sItem.getType().getDisplayName(sItem.getVariant()) + " now has " + type.getName() + " " + level + (Object)ChatColor.GREEN + " on it.");
    }
}
