package net.hypixel.skyblock.features.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import java.util.ArrayList;
import org.bukkit.scoreboard.Score;
import java.util.List;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Sidebar
{
    private static ScoreboardManager manager;
    private final String name;
    private final String identifier;
    private final Scoreboard board;
    private final Objective obj;
    private final List<Score> scores;
    
    public Sidebar(final String name, final String identifier) {
        this.name = name;
        this.identifier = identifier;
        this.board = Sidebar.manager.getNewScoreboard();
        this.obj = this.board.registerNewObjective(identifier, "");
        this.scores = (List<Score>)new ArrayList();
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.obj.setDisplayName(name);
    }
    
    public void add(final String s) {
        final Score score = this.obj.getScore(s);
        this.scores.add(0, (Object)score);
    }
    
    public void apply(final Player player) {
        for (int i = 0; i < this.scores.size(); ++i) {
            ((Score)this.scores.get(i)).setScore(i);
        }
        player.setScoreboard(this.board);
    }
    
    static {
        Sidebar.manager = Bukkit.getScoreboardManager();
    }
}
