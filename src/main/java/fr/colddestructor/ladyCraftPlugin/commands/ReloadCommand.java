package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.helper.YamlHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        YamlHelper.reloadMainConfig();

        ChatGame.setupChatGame(plugin);
        YamlHelper.reloadChatGameConfig();

        YamlHelper.firstLoadConfig = false;
        if (commandSender instanceof Player player){
            player.sendMessage(TextBuilder.create()
                    .append(TextBuilder.pluginName())
                    .text("LadyCraftPlugin reloaded successfully !", TextBuilder.GREEN).build());
        }
        return true;
    }
}
