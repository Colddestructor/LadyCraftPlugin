package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin;
import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import fr.colddestructor.ladyCraftPlugin.helper.YamlHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public class AddWordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            return false;
        }

        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = configuration.getConfigurationSection("chat-game");
        assert section != null;
        List<String> wordsList = (List<String>) section.getList("words-list");
        assert wordsList != null;

        for (String arg : args) {
            if (!wordsList.contains(arg)) {
                ChatGame.chatGame.getWords().add(arg);
                wordsList.add(arg);
                if (sender instanceof Player player) {
                    player.sendMessage(TextBuilder.create().text("Mot ajouté : " + arg, TextBuilder.GREEN).build());
                } else {
                    LadyCraftPlugin.plugin.getLogger().info("Word added : " + arg);
                }
            } else {
                if (sender instanceof Player player) {
                    player.sendMessage(TextBuilder.create().text("Mot déjà dans la liste : " + arg, TextBuilder.RED).build());
                } else {
                    LadyCraftPlugin.plugin.getLogger().info("Word already in list : " + arg);
                }
            }
        }

        section.set("words-list", wordsList);
        YamlHelper.saveYaml(configuration, "config.yml");

        return true;
    }
}
