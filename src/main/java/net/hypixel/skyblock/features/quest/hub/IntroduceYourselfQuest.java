package net.hypixel.skyblock.features.quest.hub;

import org.bukkit.event.EventHandler;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;
import net.hypixel.skyblock.event.SkyblockPlayerNPCClickEvent;
import java.util.Arrays;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.quest.Objective;
import java.util.List;
import net.hypixel.skyblock.features.quest.QuestLine;

public class IntroduceYourselfQuest extends QuestLine
{
    private static List<String> VILLAGERS;
    
    public IntroduceYourselfQuest() {
        super("introduce_yourself", "Introduce Yourself", new Objective[] { new TalkToVillagersObjective() });
    }
    
    @Override
    protected List<String> getRewards() {
        return (List<String>)Collections.singletonList((Object)((Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GOLD + 50 + " &7Coins"));
    }
    
    @Override
    protected boolean hasCompletionMessage() {
        return true;
    }
    
    @Override
    protected void reward(final User player) {
        player.addCoins(50L);
    }
    
    static {
        IntroduceYourselfQuest.VILLAGERS = (List<String>)Arrays.asList((Object[])new String[] { "TOM", "STELLA", "DUKE", "LIAM", "JACK", "JAMIE", "RYU", "LYNN", "LEO", "VEX", "FELIX", "ANDREW" });
    }
    
    private static class TalkToVillagersObjective extends Objective
    {
        public TalkToVillagersObjective() {
            super("talk_to_villagers", "Explore Hub");
        }
        
        @Override
        public String getSuffix(final User player) {
            return (Object)ChatColor.GRAY + "(" + (Object)ChatColor.YELLOW + player.getTalkedVillagers().size() + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + "12" + (Object)ChatColor.GRAY + ")";
        }
        
        @EventHandler
        public void onClick(final SkyblockPlayerNPCClickEvent e) {
            if (!this.isThisObjective(e.getPlayer())) {
                return;
            }
            final SkyBlockNPC npc = e.getNpc();
            if (IntroduceYourselfQuest.VILLAGERS.contains((Object)npc.getId())) {
                User.getUser(e.getPlayer()).addTalkedVillager(npc.getId());
            }
            if (User.getUser(e.getPlayer()).getTalkedVillagers().size() >= 12) {
                this.complete(e.getPlayer());
            }
        }
    }
}
