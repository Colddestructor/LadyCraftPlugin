package fr.colddestructor.ladyCraftPlugin.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerData {

    private final Player player;
    private int auraCount;

    public static ArrayList<PlayerData> playerDataList = new ArrayList<>();

    public PlayerData(Player player) {
        this.player = player;
        this.auraCount = 0;
        playerDataList.add(this);
    }

    public void setAuraCount(int auraCount) {
        this.auraCount = auraCount;
    }

    public void addAura(int auraCount){
        this.auraCount += auraCount;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAuraCount() {
        return auraCount;
    }

    public static PlayerData getPlayerData(Player player) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.getPlayer() == player) {
                return playerData;
            }
        }
        return new PlayerData(player);
    }

}