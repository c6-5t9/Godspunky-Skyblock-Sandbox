package net.hypixel.skyblock.features.quest;

import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Objective implements Listener
{
    private final String id;
    private final String display;
    
    public Objective(final String id, final String display) {
        this.id = id;
        this.display = display;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)SkyBlock.getPlugin());
    }
    
    private QuestLine getQuest() {
        return SkyBlock.getPlugin().getQuestLineHandler().getQuest(this);
    }
    
    public Objective getNext() {
        return this.getQuest().getNext(this);
    }
    
    public void complete(final Player p) {
        final User player = User.getUser(p.getUniqueId());
        final Objective next = this.getNext();
        player.addCompletedObjectives(this.getId());
        if (this.getNext() == null) {
            this.getQuest().complete(player.toBukkitPlayer());
            return;
        }
        final String message = " \n " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + " NEW OBJECTIVE\n" + (Object)ChatColor.WHITE + "  " + next.getDisplay() + "\n";
        player.toBukkitPlayer().playSound(player.toBukkitPlayer().getLocation(), Sound.NOTE_PLING, 10.0f, 0.0f);
        player.toBukkitPlayer().sendMessage(message);
        player.toBukkitPlayer().sendMessage(" ");
    }
    
    public String getSuffix(final User player) {
        return "";
    }
    
    protected boolean isThisObjective(final Player player) {
        final User skyblockPlayer = User.getUser(player.getUniqueId());
        return skyblockPlayer.getQuestLine() != null && skyblockPlayer.getQuestLine().getObjective(skyblockPlayer) != null && skyblockPlayer.getQuestLine().getObjective(skyblockPlayer).getId().equals((Object)this.getId());
    }
    
    public boolean hasSuffix(final User skyblockPlayer) {
        return !this.getSuffix(skyblockPlayer).equals((Object)"");
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getDisplay() {
        return this.display;
    }
}
