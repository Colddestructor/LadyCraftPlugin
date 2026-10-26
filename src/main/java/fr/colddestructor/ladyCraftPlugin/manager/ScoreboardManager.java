package fr.colddestructor.ladyCraftPlugin.manager;

import fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin;
import fr.colddestructor.ladyCraftPlugin.commands.AuraListCommand;
import fr.colddestructor.ladyCraftPlugin.helper.BetterScoreboard;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ScoreboardManager {

    public static HashMap<Player, BetterScoreboard> playersScoreboards = new HashMap<>();
    public static long reloadScoreBoardTime = 60;

    public static void setupScoreBoard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            createScoreboardPlayer(player);
        }
    }

    public static void createScoreboardPlayer(Player player) {

        //  ┏━━━━━━LadyCraft━━━━━━┓
        //
        //    Total jeu :
        //    Total morts :
        //
        //  ━━━━━━━━━━━━━━━━━━━━━━━
        //
        //    Top Aura :
        //    Aura :
        //    Temps de fly :
        //
        //  ┗━━━━━━LadyCraft━━━━━━┛

        DisplaySlot displaySlot = DisplaySlot.SIDEBAR;
        Component title = TextBuilder.create()
                .bold("LadyStats", TextBuilder.ORANGE)
                .build();

        BetterScoreboard betterScoreboard = new BetterScoreboard(player, displaySlot, title);
        betterScoreboard.addLines(new ArrayList<>(List.of(
                "§5",
                 "§a┏━━━━━§r[§d§lLadyCraft§r]§a━━━━━┓",
                "§1",
                "§a    Total jeu : ", // §e§l2h
                "§a    Total morts : ", //§e§l3💀
                "§2",
                "§7┣━━━━━━━━━━━━━━━━━━━━━━━┫",
                "§3",
                "§a    Top Aura : ", // §e§l2e
                "§a    Aura : ", // §e§l230
                "§a    Fly : ", // §e§l0m
                "§4",
                "§a┗━━━━━§r[§d§lLadyCraft§r]§a━━━━━┛"
        )));
        betterScoreboard.update();
        ScoreboardManager.playersScoreboards.put(player, betterScoreboard);
    }

    public static void updateDeathCount(Player player){
        BetterScoreboard scoreboard = ScoreboardManager.playersScoreboards.get(player);
        int deaths = player.getStatistic(Statistic.DEATHS);
        scoreboard.setLine(4, "§a    Total morts : §e§l" + deaths + "\uD83D\uDC80");
    }

    public static void updateTimePlayed(Player player){
        BetterScoreboard scoreboard = ScoreboardManager.playersScoreboards.get(player);
        int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        scoreboard.setLine(3, "§a    Total jeu : §e§l" + ticks/20/3600 + "h");
    }

    public static void updateAura(Player player){
        BetterScoreboard scoreboard = ScoreboardManager.playersScoreboards.get(player);
        int auraCount = PlayerData.getPlayerData(player).getAuraCount();
        int leaderBoardPosition = AuraListCommand.getPlayerPosition(
                AuraListCommand.getAuraSortedList(AuraListCommand.auraList),
                player.getName());
        scoreboard.setLine(8, "§a    Top Aura : §e§l" + leaderBoardPosition + "e");

        scoreboard.setLine(9, "§a    Auras : §e§l" + auraCount);
    }

    public static void scheduleScoreBoardUpdates(){
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateDeathCount(player);
                    updateTimePlayed(player);
                    updateAura(player);
                }
            }
        }.runTaskTimerAsynchronously(LadyCraftPlugin.plugin, 1L, ScoreboardManager.reloadScoreBoardTime * 20);
    }
}
