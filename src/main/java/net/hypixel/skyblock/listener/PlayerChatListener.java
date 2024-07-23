package net.hypixel.skyblock.listener;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;
import net.hypixel.skyblock.user.User;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener extends PListener
{
    @EventHandler(priority = EventPriority.LOW)
    public void Event(final AsyncPlayerChatEvent e) {
        final User data = User.getUser(e.getPlayer());
        e.setFormat("%1$s" + (Object)((data.rank == PlayerRank.DEFAULT) ? ChatColor.GRAY : ChatColor.WHITE) + ": %2$s");
        e.getPlayer().setDisplayName(e.getPlayer().getName());
        final PlayerRank rank = data.rank;
        String userTag;
        if (rank == PlayerRank.DEFAULT) {
            userTag = rank.getPrefix() + e.getPlayer().getName();
        }
        else {
            userTag = rank.getPrefix() + " " + e.getPlayer().getName();
        }
        if (!e.getPlayer().getDisplayName().equals((Object)ChatColor.translateAlternateColorCodes('&', userTag))) {
            e.getPlayer().setDisplayName(ChatColor.translateAlternateColorCodes('&', userTag));
        }
        if (rank.isAboveOrEqual(PlayerRank.MVPPLUSPLUS)) {
            e.setMessage(e.getMessage().replace((CharSequence)"<3", (CharSequence)"§c\u2764").replace((CharSequence)"\u2b50", (CharSequence)"§6\u272d").replace((CharSequence)":owo:", (CharSequence)"§dO§5w§dO").replace((CharSequence)"o/", (CharSequence)"§d(/\u25d5\u30ee\u25d5)/").replace((CharSequence)":OOF:", (CharSequence)"§c§lOOF").replace((CharSequence)":123:", (CharSequence)"§a1§e2§c3").replace((CharSequence)":shrug:", (CharSequence)"§e¯\\(\u30c4)/¯").replace((CharSequence)":yes:", (CharSequence)"§a\u2714").replace((CharSequence)":no:", (CharSequence)"§c\u2716").replace((CharSequence)":java:", (CharSequence)"§b\u2668").replace((CharSequence)":arrow:", (CharSequence)"§e\u27a1").replace((CharSequence)":typing:", (CharSequence)"§e\u270e§6..."));
        }
    }
}
