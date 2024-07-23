package net.hypixel.skyblock.features.quest;

import net.hypixel.skyblock.user.User;
import java.util.ArrayList;
import java.util.Iterator;
import net.hypixel.skyblock.features.quest.hub.IntroduceYourselfQuest;
import net.hypixel.skyblock.features.quest.starting.GettingStartedQuest;
import java.util.List;
import net.hypixel.skyblock.features.region.RegionType;
import java.util.HashMap;

public class QuestLineHandler
{
    private final HashMap<RegionType, List<QuestLine>> quests;
    
    public QuestLineHandler() {
        this.quests = (HashMap<RegionType, List<QuestLine>>)new HashMap();
        this.register(RegionType.PRIVATE_ISLAND, new GettingStartedQuest());
        this.register(RegionType.VILLAGE, new IntroduceYourselfQuest());
        for (final List<QuestLine> quest : this.quests.values()) {
            quest.forEach(QuestLine::onEnable);
        }
    }
    
    public void disable() {
        for (final List<QuestLine> quest : this.quests.values()) {
            quest.forEach(QuestLine::onDisable);
        }
    }
    
    public void register(final RegionType[] locations, final QuestLine line) {
        for (final RegionType loc : locations) {
            this.register(loc, line);
        }
    }
    
    public void register(final RegionType location, final QuestLine line) {
        if (this.quests.containsKey((Object)location)) {
            ((List)this.quests.get((Object)location)).add((Object)line);
        }
        else {
            final ArrayList<QuestLine> list = (ArrayList<QuestLine>)new ArrayList();
            list.add((Object)line);
            this.quests.put((Object)location, (Object)list);
        }
    }
    
    public QuestLine getFromPlayer(final User player) {
        if (player.getRegion() == null) {
            return null;
        }
        final RegionType loc = player.getRegion().getType();
        final List<QuestLine> lines = (List<QuestLine>)this.quests.get((Object)loc);
        if (lines == null || lines.isEmpty()) {
            return null;
        }
        final List<String> completed = player.getCompletedQuests();
        if (lines == null) {
            return null;
        }
        for (final QuestLine quest : lines) {
            if (completed.contains((Object)quest.getName())) {
                continue;
            }
            return quest;
        }
        return null;
    }
    
    public QuestLine getQuest(final Objective objective) {
        for (final List<QuestLine> lines : this.quests.values()) {
            for (final QuestLine line : lines) {
                for (final Objective obj : line.getLine()) {
                    if (obj.getId().equals((Object)objective.getId())) {
                        return line;
                    }
                }
            }
        }
        return null;
    }
    
    public List<QuestLine> getQuests() {
        final List<QuestLine> quests = (List<QuestLine>)new ArrayList();
        for (final List<QuestLine> questLines : this.quests.values()) {
            for (final QuestLine quest : questLines) {
                if (quests.contains((Object)quest)) {
                    continue;
                }
                quests.add((Object)quest);
            }
        }
        return quests;
    }
}
