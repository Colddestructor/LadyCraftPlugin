package fr.colddestructor.ladyCraftPlugin.game;

import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import fr.colddestructor.ladyCraftPlugin.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JumpGame {

    public static boolean isEnabled = true;
    public static List<Integer> RegionPos1 = new ArrayList<>();
    public static List<Integer> RegionPos2 = new ArrayList<>();
    public static List<Integer> Start = new ArrayList<>();
    public static List<Integer> End = new ArrayList<>();

    public static ArrayList<JumpGame> jumpGameList = new ArrayList<>();

    private final long timeStart;
    private final Player player;

    public JumpGame(Player player) {

        this.timeStart = Instant.now().toEpochMilli();
        this.player = player;

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        player.sendMessage(TextBuilder.create()
                        .append(TextBuilder.pluginName())
                        .text(" Début du jump !", TextBuilder.GREEN)
                        .build());

        JumpGame.jumpGameList.add(this);
    }

    public void endJumpGame(){
        jumpGameList.remove(this);
        long totalTime = Instant.now().toEpochMilli()  - timeStart;

        this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        this.player.sendMessage(TextBuilder.create()
                        .append(TextBuilder.pluginName())
                        .text(" Temps : ", TextBuilder.GREEN)
                        .bold(String.valueOf(totalTime/1000.0), TextBuilder.ORANGE)
                        .text("s", TextBuilder.GREEN)
                        .build());

        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(TextBuilder.create()
                            .append(TextBuilder.header())
                            .newLine()
                            .newLine()
                            .text("Le joueur ", TextBuilder.GREEN)
                            .append(player.displayName())
                            .text(" a finit le jump en ", TextBuilder.GREEN)
                            .bold(String.valueOf(totalTime/1000.0), TextBuilder.ORANGE)
                            .text("s", TextBuilder.GREEN)
                            .newLine()
                            .append(TextBuilder.footer())
                            .build());
        }
        this.player.sendMessage(TextBuilder.create()
                .append(TextBuilder.pluginName())
                .text(" + 20 auras", TextBuilder.GREEN)
                .build());
        PlayerData.getPlayerData(this.player).addAura(20);
        ScoreboardManager.updateAura(this.player);
    }

    public void outOfReachJumpGame(){
        jumpGameList.remove(this);
        player.sendMessage(TextBuilder.create()
                        .append(TextBuilder.pluginName())
                        .text(" Vous avez sorti de la zone de jump", TextBuilder.GREEN)
                        .build());
    }

    public Player getPlayer() {
        return player;
    }
}
