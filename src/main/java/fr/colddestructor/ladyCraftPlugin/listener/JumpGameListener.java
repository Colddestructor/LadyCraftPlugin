package fr.colddestructor.ladyCraftPlugin.listener;

import fr.colddestructor.ladyCraftPlugin.game.JumpGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JumpGameListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!JumpGame.isEnabled) {
            return;
        }

        Player player = event.getPlayer();
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        if (isInNoFallArea(player) && player.isGliding()) {
            player.setGliding(false);
        }
        if (!isInNoFallArea(player)) {
            JumpGame jpSave = null;
            for (JumpGame jp : JumpGame.jumpGameList){
                if (jp.getPlayer().equals(player)) {
                    jpSave = jp;
                }
            }
            if (jpSave != null) {
                jpSave.outOfReachJumpGame();
            }
        }

        if (JumpGame.Start.getFirst() == x && JumpGame.Start.get(1) == y && JumpGame.Start.getLast() == z) {
            createJumpGame(player);
        }
        if (JumpGame.End.getFirst() == x && JumpGame.End.get(1) == y && JumpGame.End.getLast() == z) {
            JumpGame jpSave = null;
            for (JumpGame jp : JumpGame.jumpGameList){
                if (jp.getPlayer() == player){
                    jpSave = jp;
                }
            }
            if (jpSave != null){
                jpSave.endJumpGame();
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!JumpGame.isEnabled) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && isInNoFallArea(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!JumpGame.isEnabled) {
            return;
        }
        JumpGame.jumpGameList.removeIf(jp -> jp.getPlayer().equals(event.getPlayer()));
    }

    private static void createJumpGame(Player player){
        for (JumpGame jp : JumpGame.jumpGameList){
            if (jp.getPlayer() == player) {
                return;
            }
        }
        new JumpGame(player);
    }

    private static boolean isInNoFallArea(Player player) {
        boolean noFallAreaXMin = player.getLocation().getBlockX() >= JumpGame.RegionPos1.getFirst();
        boolean noFallAreaYMin = player.getLocation().getBlockY() >= JumpGame.RegionPos1.get(1);
        boolean noFallAreaZMin = player.getLocation().getBlockZ() >= JumpGame.RegionPos1.getLast();

        boolean noFallAreaXMax = player.getLocation().getBlockX() <= JumpGame.RegionPos2.getFirst();
        boolean noFallAreaYMax = player.getLocation().getBlockY() <= JumpGame.RegionPos2.get(1);
        boolean noFallAreaZMax = player.getLocation().getBlockZ() <= JumpGame.RegionPos2.getLast();

        return noFallAreaXMax &&  noFallAreaYMax && noFallAreaZMax && noFallAreaXMin && noFallAreaYMin && noFallAreaZMin;
    }


}
