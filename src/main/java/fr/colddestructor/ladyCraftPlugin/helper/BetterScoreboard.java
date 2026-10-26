package fr.colddestructor.ladyCraftPlugin.helper;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class BetterScoreboard {

    private final Objective mainObjective;
    private final ArrayList<String> lines;
    private final Player player;
    private final Scoreboard scoreboard;
    private DisplaySlot displaySlot;

    public final static ArrayList<BetterScoreboard> betterScoreboardList = new ArrayList<>();

    public BetterScoreboard(Player player, DisplaySlot displaySlot, Component title) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.player = player;
        mainObjective = scoreboard.registerNewObjective(title.toString() + player.getUniqueId(), Criteria.DUMMY, title);
        lines = new ArrayList<>();
        this.displaySlot = displaySlot;

        betterScoreboardList.add(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void addLines(ArrayList<String> newLines) {
        lines.addAll(newLines);
    }

    public void setLine(int index, String line) {
        mainObjective.getScore(lines.get(index)).resetScore();
        lines.set(index, line);
        mainObjective.getScore(lines.get(index)).setScore(lines.size() - index);
        mainObjective.setDisplaySlot(displaySlot);
    }

    public void suffixLine(int index, String line) {
        lines.set(index, lines.get(index) + line);
    }

    public void prefixLine(int index, String line) {
        lines.set(index, line + lines.get(index));
    }

    public void changeDisplaySlot(DisplaySlot displaySlot) {
        this.displaySlot = displaySlot;
    }

    public void update() {
        // May be broken ...
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            mainObjective.getScore(line).setScore(lines.size() - i);
        }
        mainObjective.setDisplaySlot(displaySlot);
        player.setScoreboard(scoreboard);
    }

    // TODO : [DONE] ADD LINES TO SCOREBOARD
    // TODO : [DONE] MODIFY LINES EASILY
    // TODO : [DONE] UPDATE THE SCOREBOARD (LINE BY LINE)
    // TODO : [DONE] CHANGE DISPLAY SLOT
    // TODO : [DONE] SETUP SCOREBOARD

}
