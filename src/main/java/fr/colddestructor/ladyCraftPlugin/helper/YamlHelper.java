package fr.colddestructor.ladyCraftPlugin.helper;

import fr.colddestructor.ladyCraftPlugin.commands.AuraListCommand;
import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
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

    public static void reloadPlayerData(){

        for (PlayerData playerData : PlayerData.playerDataList){
            savePlayerData(playerData);
        }
        PlayerData.playerDataList.clear();
        AuraListCommand.auraList.clear();

        File file = new File(plugin.getDataFolder(), "player-data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if  (file.length() == 0){
            for (Player player : Bukkit.getOnlinePlayers()){
               new PlayerData(player);
               ConfigurationSection section = configuration.createSection(player.getUniqueId().toString());
               section.set("name", player.getName());
               section.set("aura-count", 0);
            }
        } else {
            for (String key :  configuration.getKeys(false)) {
                ConfigurationSection playerData = configuration.getConfigurationSection(key);
                assert playerData != null;
                AuraListCommand.auraList.put(playerData.getString("name"), playerData.getInt("aura-count"));
            }
            for (Player player : Bukkit.getOnlinePlayers()){
                PlayerData playerData = new PlayerData(player);
                ConfigurationSection section = configuration.getConfigurationSection(player.getUniqueId().toString());
                assert section != null;
                playerData.setAuraCount(section.getInt("aura-count"));
            }
        }
        saveYaml(configuration, "player-data.yml");
    }

    public static void reloadTargetPlayerData(Player target){
        File file = new File(plugin.getDataFolder(), "player-data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String UUID = target.getUniqueId().toString();

        if  (file.length() > 0){
            for (String uuid : configuration.getKeys(false)){
                if (uuid.equals(UUID)){
                    ConfigurationSection section = configuration.getConfigurationSection(uuid);
                    assert section != null;

                    PlayerData playerData = PlayerData.getPlayerData(target);
                    playerData.setAuraCount(section.getInt("aura-count"));
                    Component message = TextBuilder.create()
                            .text("Vous avez actuellement ", TextBuilder.GREEN)
                            .bold(String.valueOf(playerData.getAuraCount()), TextBuilder.ORANGE)
                            .text(" aura(s)", TextBuilder.GREEN)
                            .build();
                    playerData.getPlayer().sendMessage(message);
                }
            }
        }
        saveYaml(configuration, "player-data.yml");
    }

    public static void savePlayerData(PlayerData playerData){
        File file = new File(plugin.getDataFolder(), "player-data.yml");
        final YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(file);

        String playerUUID = playerData.getPlayer().getUniqueId().toString();

        ConfigurationSection section;
        if (file.length() == 0 || !playerDataConfig.getKeys(false).contains(playerUUID)) {
            section = playerDataConfig.createSection(playerUUID);
        } else {
            section = playerDataConfig.getConfigurationSection(playerUUID);
        }

        assert section != null;
        section.set("aura-count", playerData.getAuraCount());
        section.set("name", playerData.getPlayer().getName());
        saveYaml(playerDataConfig, "player-data.yml");
    }

}
