package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.manager.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin.plugin;

public class AuraListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(sender instanceof Player)){
            plugin.getLogger().info("Only players can use this command");
        }

        assert sender instanceof Player;
        Player player = (Player) sender;

        StringBuilder stringBuilder = new StringBuilder();
        for (PlayerData playerData : PlayerData.playerDataList){
            stringBuilder
                    .append(playerData.getPlayer().getName())
                    .append(" : ")
                    .append(playerData.getAuraCount())
                    .append(" auras")
                    .append("\n");
        }

        player.sendMessage(Component.text(stringBuilder.toString()));

        return true;
    }
}
