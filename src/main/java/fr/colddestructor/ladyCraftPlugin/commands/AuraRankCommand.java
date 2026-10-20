package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public class AuraRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (sender instanceof Player player){
            LinkedHashMap<String, Integer> sortedList = AuraListCommand.getAuraSortedList(AuraListCommand.auraList);

            if (args.length == 0){
                int pos = AuraListCommand.getPlayerPosition(sortedList, player.getName());
                Component message = TextBuilder.create()
                        .text("Vous êtes actuellement ", TextBuilder.GREEN)
                        .bold(pos + "e", TextBuilder.ORANGE)
                        .text(" avec ", TextBuilder.GREEN)
                        .bold(PlayerData.getPlayerData(player).getAuraCount() + "", TextBuilder.ORANGE)
                        .text(" aura(s)", TextBuilder.GREEN)
                        .build();

                player.sendMessage(message);
            } else if (args.length == 1){
                for (String playerName : sortedList.keySet()) {
                    if (playerName.equalsIgnoreCase(args[0])) {
                        int pos = AuraListCommand.getPlayerPosition(sortedList, playerName);
                        Component message = TextBuilder.create()
                                .append(TextBuilder.pluginName())
                                .bold(" " + playerName, TextBuilder.ORANGE)
                                .text(" est ", TextBuilder.GREEN)
                                .bold(pos + "e", TextBuilder.ORANGE)
                                .text(" avec ", TextBuilder.GREEN)
                                .bold(sortedList.get(playerName) + "", TextBuilder.ORANGE)
                                .text(" aura(s)", TextBuilder.GREEN)
                                .build();

                        player.sendMessage(message);
                        return true;
                    }
                }
                player.sendMessage(TextBuilder.create().text("Joueur inconnu", TextBuilder.RED).build());
            } else {
                player.sendMessage(TextBuilder.create().text("/aurarank (Pseudo)", TextBuilder.RED).build());
            }
        } else {
            plugin.getLogger().info("Console cannot execute that command");
        }

        return true;
    }
}
