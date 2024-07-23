package net.hypixel.skyblock.features.quest;

import java.util.Collections;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Iterator;
import net.hypixel.skyblock.user.User;
import java.util.Arrays;
import java.util.List;

public abstract class QuestLine
{
    protected final List<Objective> line;
    private final String name;
    private final String display;
    
    public QuestLine(final String name, final String display, final Objective... objectives) {
        this.line = (List<Objective>)Arrays.asList((Object[])objectives);
        this.display = display;
        this.name = name;
    }
    
    public Objective getObjective(final User skyblockPlayer) {
        final List<String> completed = skyblockPlayer.getCompletedObjectives();
        for (final Objective obj : this.line) {
            if (completed.contains((Object)obj.getId())) {
                continue;
            }
            return obj;
        }
        return new Objective("", "");
    }
    
    public Objective getNext(final Objective obj) {
        try {
            return (Objective)this.line.get(this.line.indexOf((Object)obj) + 1);
        }
        catch (final ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    public void complete(final Player player) {
        final User skyblockPlayer = User.getUser(player.getUniqueId());
        skyblockPlayer.addCompletedQuest(this.getName());
        if (!this.hasCompletionMessage()) {
            return;
        }
        final String message = " \n " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + " QUEST COMPLETE\n" + (Object)ChatColor.WHITE + "  " + this.display + "\n";
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 10.0f, 0.0f);
        player.sendMessage(message);
        player.sendMessage(" ");
        for (final Objective objective : this.line) {
            player.sendMessage((Object)ChatColor.GREEN + "   \u2713 " + (Object)ChatColor.WHITE + objective.getDisplay());
        }
        if (this.getRewards().size() > 0) {
            player.sendMessage(" ");
            player.sendMessage((Object)ChatColor.GREEN + "  " + (Object)ChatColor.BOLD + "REWARD");
            for (final String reward : this.getRewards()) {
                player.sendMessage("   " + ChatColor.translateAlternateColorCodes('&', reward));
            }
        }
        player.sendMessage("  ");
        this.reward(User.getUser(player.getUniqueId()));
    }
    
    protected List<String> getRewards() {
        return (List<String>)Collections.emptyList();
    }
    
    protected void reward(final User player) {
    }
    
    protected boolean hasCompletionMessage() {
        return false;
    }
    
    public void onDisable() {
    }
    
    public void onEnable() {
    }
    
    public List<Objective> getLine() {
        return this.line;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplay() {
        return this.display;
    }
}
