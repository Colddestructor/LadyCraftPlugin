package fr.colddestructor.ladyCraftPlugin.listener;

import fr.colddestructor.ladyCraftPlugin.helper.YamlHelper;
import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        new PlayerData(player);
        YamlHelper.reloadTargetPlayerData(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player);

        YamlHelper.savePlayerData(playerData);
        PlayerData.playerDataList.remove(playerData);
    }

}
