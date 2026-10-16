package fr.colddestructor.ladyCraftPlugin.game;

import fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.helper.YamlHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.colddestructor.ladyCraftPlugin.helper.TextBuilder.ORANGE;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public class ChatGame {

    LadyCraftPlugin plugin = LadyCraftPlugin.plugin;
    int delay;
    boolean isRandomTiming;
    List<String> words;

    public static boolean state;
    public static ChatGame chatGame;

    public static long askQuestionTime = 0L;
    public static boolean chatGameActive = false;
    public static String selectedWord = "";

    public ChatGame(int delay, boolean isRandomTiming, List<String> words) {
        this.delay = delay;
        this.isRandomTiming = isRandomTiming;
        this.words = words;
    }

    public void play(){
        askQuestionTime = System.currentTimeMillis();
        Random random = new Random();
        random.nextInt(words.size());
        chatGameActive = true;
        selectedWord = words.get(random.nextInt(words.size()));

        String plainText = PlainTextComponentSerializer.plainText().serialize(TextBuilder.header());
        int MaxLength = plainText.length();
        int spaceAmount = MaxLength/2 - selectedWord.length()/2;

        Component content = TextBuilder.create()
                .newLine()
                .text(" ".repeat(15) + "Le/La premier(e) à marquer le mot :", GREEN)
                .newLine()
                .newLine()
                .bold(" ".repeat(spaceAmount) + selectedWord, ORANGE)
                .newLine()
                .newLine()
                .text(" ".repeat(32) + "a gagné !", GREEN)
                .build();

        Component message = TextBuilder.create()
                .append(TextBuilder.header())
                .newLine()
                .append(content)
                .newLine()
                .append(TextBuilder.footer())
                .build();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }
    }

    public void playTimer(){
        int time;
        if (isRandomTiming) {
            Random rand = new Random();
            time = rand.nextInt(delay/2, delay);
        } else {
            time = delay;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                play();
            }
        }.runTaskTimer(plugin, this.delay * 20L, time * 20L);
    }

    public static void setupChatGame(LadyCraftPlugin plugin){
        if (state){
            ChatGame.chatGame = new ChatGame(0, true, new ArrayList<>()); // Only setup, doesn't matter
            YamlHelper.reloadChatGameConfig();
            ChatGame.chatGame.playTimer();
            plugin.getLogger().info("ChatGame successfully enabled");
        } else {
            plugin.getLogger().info("ChatGame successfully disabled");
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setRandomTiming(boolean randomTiming) {
        isRandomTiming = randomTiming;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isRandomTiming() {
        return isRandomTiming;
    }

    public List<String> getWords() {
        return words;
    }
}
