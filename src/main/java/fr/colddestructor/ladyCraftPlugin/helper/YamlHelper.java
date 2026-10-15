package fr.colddestructor.ladyCraftPlugin.helper;

import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public final class YamlHelper {

    public static boolean firstLoadConfig = false;

    private YamlHelper() {
        throw new UnsupportedOperationException();
    }

    public static void saveYaml(YamlConfiguration configuration, String filename) {
        try {
            configuration.save(new File(plugin.getDataFolder(), filename));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void reloadChatGameConfig(){

        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ChatGame.chatGame.setRandomTiming(true);
        ChatGame.chatGame.setDelay(600);
        ChatGame.chatGame.setWords(List.of("Chausson", "Intergouvernementalisation", "Inversement", "carambar", "cytoplasme",
                "je mange des pommes", "alambic", "Diffraction", "therkkio"));

        ConfigurationSection section = configuration.getConfigurationSection("chat-game");
        assert section != null;
        if (firstLoadConfig) {
            section.set("delay", ChatGame.chatGame.getDelay());
            section.set("words-list", ChatGame.chatGame.getWords());
            section.set("isRandomTiming", ChatGame.chatGame.isRandomTiming());
        } else {
            ChatGame.chatGame.setRandomTiming(section.getBoolean("isRandomTiming"));
            ChatGame.chatGame.setDelay(section.getInt("delay"));
            ChatGame.chatGame.setWords((List<String>) section.getList("words-list"));
        }
        saveYaml(configuration, "config.yml");
    }

    public static void reloadMainConfig(){

        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ChatGame.state = true;
        if (file.length() == 0){
            firstLoadConfig = true;
            ConfigurationSection section = configuration.createSection("chat-game");
            section.set("enabled", true);
        } else {
            ConfigurationSection section = configuration.getConfigurationSection("chat-game");
            assert section != null;
            ChatGame.state = section.getBoolean("enabled");
        }
        saveYaml(configuration, "config.yml");

    }

}
