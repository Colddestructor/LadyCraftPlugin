package fr.colddestructor.ladyCraftPlugin.listener;

import fr.colddestructor.ladyCraftPlugin.manager.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        ScoreboardManager.updateDeathCount(player);
    }

}
