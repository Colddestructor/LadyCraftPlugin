package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public class AuraListCommand implements CommandExecutor {

    public static HashMap<String, Integer> auraList = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(sender instanceof Player)){
            plugin.getLogger().info("Only players can use this command");
        }

        assert sender instanceof Player;
        Player player = (Player) sender;

        TextBuilder message = TextBuilder.create().append(TextBuilder.header());

        // Leaderboard
        LinkedHashMap<String, Integer> sortedList = getAuraSortedList(auraList);

        int max = 0;
        for (String playerName : sortedList.keySet()) {
            if (max >= 5){break;}
            message.append(
                    TextBuilder.create()
                            .newLine()
                            .text(playerName + " : ", TextBuilder.GREEN)
                            .bold(auraList.get(playerName).toString(), TextBuilder.ORANGE)
                            .text(" aura(s)", TextBuilder.GREEN)
                            .build());
            max++;
        }

        // Player Rank
        int playerAura = PlayerData.getPlayerData(player).getAuraCount();

        int pos = getPlayerPosition(sortedList, player.getName());

        message
            .newLine()
            .newLine()
            .text("Vous êtes actuellement ", TextBuilder.GREEN)
            .bold(pos + "e",  TextBuilder.ORANGE)
            .text(" avec ", TextBuilder.GREEN)
            .bold(Integer.toString(playerAura), TextBuilder.ORANGE)
            .text(" aura(s)", TextBuilder.GREEN);

        message.append(TextBuilder.footer());

        player.sendMessage(message.build());

        return true;
    }

    public static LinkedHashMap<String, Integer> getAuraSortedList(HashMap<String, Integer> auraList){

        return auraList.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, ignored) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static int getPlayerPosition(LinkedHashMap<String, Integer> sortedList, String playerName){
        int pos = 1;
        for (String playerNameList : sortedList.keySet()) {
            if (playerNameList.equals(playerName)){
                break;
            }
            pos++;
        }
        return pos;
    }

}
