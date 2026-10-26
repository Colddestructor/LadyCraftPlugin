package fr.colddestructor.ladyCraftPlugin.helper;

import fr.colddestructor.ladyCraftPlugin.commands.AuraListCommand;
import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.game.JumpGame;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import fr.colddestructor.ladyCraftPlugin.manager.ScoreboardManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public static void reloadJumpGameConfig(){
        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (firstLoadConfig){
            ConfigurationSection section2 = configuration.createSection("jump-game");
            section2.set("enabled", true);
            ConfigurationSection sectionJumpPos1 = section2.createSection("region-pos1");
            sectionJumpPos1.set("x", 0);
            sectionJumpPos1.set("y", 0);
            sectionJumpPos1.set("z", 0);

            ConfigurationSection sectionJumpPos2 = section2.createSection("region-pos2");
            sectionJumpPos2.set("x", 0);
            sectionJumpPos2.set("y", 0);
            sectionJumpPos2.set("z", 0);

            ConfigurationSection sectionJumpStart = section2.createSection("start");
            sectionJumpStart.set("x", 0);
            sectionJumpStart.set("y", 0);
            sectionJumpStart.set("z", 0);

            ConfigurationSection sectionJumpEnd = section2.createSection("end");
            sectionJumpEnd.set("x", 0);
            sectionJumpEnd.set("y", 0);
            sectionJumpEnd.set("z", 0);
        } else {

            ConfigurationSection section2 = configuration.getConfigurationSection("jump-game");
            assert section2 != null;
            JumpGame.isEnabled = section2.getBoolean("enabled");

            JumpGame.RegionPos1 = new ArrayList<>();
            ConfigurationSection sectionJumpPos1 = section2.getConfigurationSection("region-pos1");
            assert sectionJumpPos1 != null;
            JumpGame.RegionPos1.add(sectionJumpPos1.getInt("x"));
            JumpGame.RegionPos1.add(sectionJumpPos1.getInt("y"));
            JumpGame.RegionPos1.add(sectionJumpPos1.getInt("z"));

            JumpGame.RegionPos2 = new ArrayList<>();
            ConfigurationSection sectionJumpPos2 = section2.getConfigurationSection("region-pos2");
            assert sectionJumpPos2 != null;
            JumpGame.RegionPos2.add(sectionJumpPos2.getInt("x"));
            JumpGame.RegionPos2.add(sectionJumpPos2.getInt("y"));
            JumpGame.RegionPos2.add(sectionJumpPos2.getInt("z"));

            JumpGame.Start = new ArrayList<>();
            ConfigurationSection sectionJumpStart = section2.getConfigurationSection("start");
            assert sectionJumpStart != null;
            JumpGame.Start.add(sectionJumpStart.getInt("x"));
            JumpGame.Start.add(sectionJumpStart.getInt("y"));
            JumpGame.Start.add(sectionJumpStart.getInt("z"));

            JumpGame.End = new ArrayList<>();
            ConfigurationSection sectionJumpEnd = section2.getConfigurationSection("end");
            assert sectionJumpEnd != null;
            JumpGame.End.add(sectionJumpEnd.getInt("x"));
            JumpGame.End.add(sectionJumpEnd.getInt("y"));
            JumpGame.End.add(sectionJumpEnd.getInt("z"));
        }
        saveYaml(configuration, "config.yml");

    }

    public static void reloadMainConfig(){

        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ChatGame.isEnabled = true;
        if (file.length() == 0){
            firstLoadConfig = true;
            ConfigurationSection section = configuration.createSection("chat-game");
            section.set("enabled", true);

            configuration.set("scoreboard-reload-time", ScoreboardManager.reloadScoreBoardTime);
        } else {
            ConfigurationSection section = configuration.getConfigurationSection("chat-game");
            assert section != null;
            ChatGame.isEnabled = section.getBoolean("enabled");

            ScoreboardManager.reloadScoreBoardTime = configuration.getLong("scoreboard-reload-time");
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

    public static void saveJumpData() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section2 = configuration.getConfigurationSection("jump-game");
        assert section2 != null;
        section2.set("enabled", true);

        ConfigurationSection sectionJumpPos1 = section2.getConfigurationSection("region-pos1");
        assert sectionJumpPos1 != null;
        sectionJumpPos1.set("x", JumpGame.RegionPos1.getFirst());
        sectionJumpPos1.set("y", JumpGame.RegionPos1.get(1));
        sectionJumpPos1.set("z", JumpGame.RegionPos1.getLast());

        ConfigurationSection sectionJumpPos2 = section2.getConfigurationSection("region-pos2");
        assert sectionJumpPos2 != null;
        sectionJumpPos2.set("x", JumpGame.RegionPos2.getFirst());
        sectionJumpPos2.set("y", JumpGame.RegionPos2.get(1));
        sectionJumpPos2.set("z", JumpGame.RegionPos2.getLast());

        ConfigurationSection sectionJumpStart = section2.getConfigurationSection("start");
        assert sectionJumpStart != null;
        sectionJumpStart.set("x", JumpGame.Start.getFirst());
        sectionJumpStart.set("y", JumpGame.Start.get(1));
        sectionJumpStart.set("z", JumpGame.Start.getLast());

        ConfigurationSection sectionJumpEnd = section2.getConfigurationSection("end");
        assert sectionJumpEnd != null;
        sectionJumpEnd.set("x", JumpGame.End.getFirst());
        sectionJumpEnd.set("y", JumpGame.End.get(1));
        sectionJumpEnd.set("z", JumpGame.End.getLast());

        saveYaml(configuration, "config.yml");
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
