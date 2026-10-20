package fr.colddestructor.ladyCraftPlugin;

import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.helper.YamlHelper;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import fr.colddestructor.ladyCraftPlugin.manager.RegisterManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LadyCraftPlugin extends JavaPlugin {

    public static LadyCraftPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        new RegisterManager(plugin).register();

        YamlHelper.reloadMainConfig();
        YamlHelper.reloadPlayerData();
        ChatGame.setupChatGame(plugin);
        YamlHelper.firstLoadConfig = false;

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            YamlHelper.savePlayerData(PlayerData.getPlayerData(player));
        }
    }
}
