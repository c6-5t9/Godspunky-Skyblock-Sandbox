package net.hypixel.skyblock.command;

import java.util.Iterator;
import net.hypixel.skyblock.user.User;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.GenericItemType;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Adds an enchantment from Spec to the specified item.", aliases = "rench", permission = PlayerRank.DEFAULT)
public class RemoveEnchantCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        final World world = player.getWorld();
        final SItem sitem = SItem.find(player.getItemInHand());
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final User user = sender.getUser();
        if (args.length == 0) {
            this.send((Object)ChatColor.RED + "Invaild Syntax! The command is /rench <specific/all> <type> (Only needed for Specific mode)");
            return;
        }
        if (sitem == null) {
            this.send((Object)ChatColor.RED + "You can't execute this command while not holding anything or an invalid item.");
            return;
        }
        if (sitem.getType().getStatistics().getType() != GenericItemType.ARMOR && sitem.getType().getStatistics().getType() != GenericItemType.WEAPON && sitem.getType().getStatistics().getType() != GenericItemType.RANGED_WEAPON && sitem.getType().getStatistics().getType() != GenericItemType.TOOL && sitem.getType() != SMaterial.ENCHANTED_BOOK) {
            this.send((Object)ChatColor.RED + "You can't execute this command while holding this Item!");
            return;
        }
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[0].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case -2132874958: {
                if (lowerCase2.equals((Object)"specific")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 96673: {
                if (lowerCase2.equals((Object)"all")) {
                    n = 1;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                if (args.length < 2) {
                    this.send((Object)ChatColor.RED + "Missing enchantment type for specific removal mode!");
                    return;
                }
                final EnchantmentType type = EnchantmentType.getByNamespace(args[1]);
                if (type == null) {
                    throw new CommandFailException((Object)ChatColor.RED + "Invalid enchantment type!");
                }
                if (sitem.getEnchantment(type) == null) {
                    throw new CommandFailException((Object)ChatColor.RED + "You can't remove an Enchantment which does not exist on your item!");
                }
                final SItem book = SItem.of(SMaterial.ENCHANTED_BOOK);
                book.addEnchantment(type, sitem.getEnchantment(type).getLevel());
                sitem.removeEnchantment(type);
                player.setItemInHand(sitem.getStack());
                Sputnik.smartGiveItem(book.getStack(), player);
                this.send(Sputnik.trans("&e" + type.getName() + " &ahas been removed from your " + sitem.getFullName()) + " &eand you got your " + book.getFullName() + " &eback!");
                return;
            }
            case 1: {
                if (sitem.getEnchantments().size() <= 0) {
                    this.send(Sputnik.trans("&cThis item have no enchantments on it!"));
                    return;
                }
                if (sitem.getType() == SMaterial.ENCHANTED_BOOK) {
                    this.send(Sputnik.trans("&cThis action cannot be done with this item!"));
                    return;
                }
                final SItem book2 = SItem.of(SMaterial.ENCHANTED_BOOK);
                for (final Enchantment e : sitem.getEnchantments()) {
                    book2.addEnchantment(e.getType(), sitem.getEnchantment(e.getType()).getLevel());
                    sitem.removeEnchantment(e.getType());
                }
                player.setItemInHand(sitem.getStack());
                Sputnik.smartGiveItem(book2.getStack(), player);
                this.send(Sputnik.trans("&eAll enchantments has been removed from your " + sitem.getFullName()) + " &eand you got your " + book2.getFullName() + " &eback!");
                break;
            }
        }
    }
}
