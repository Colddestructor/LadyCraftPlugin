package fr.colddestructor.ladyCraftPlugin.commands;

import fr.colddestructor.ladyCraftPlugin.game.JumpGame;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JumpRegionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (sender instanceof Player player){
            if (args.length != 1){
                return false;
            }

            List<Integer> pos = List.of(
                    player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(),
                    player.getLocation().getBlockZ());

            String posName;
            if (args[0].equalsIgnoreCase("pos1")) {
                JumpGame.RegionPos1 = new ArrayList<>();
                JumpGame.RegionPos1.addAll(pos);
                posName = "pos1";
            } else if (args[0].equalsIgnoreCase("pos2")) {
                JumpGame.RegionPos2 = new ArrayList<>();
                JumpGame.RegionPos2.addAll(pos);
                posName = "pos2";
            } else if (args[0].equalsIgnoreCase("start")) {
                JumpGame.Start = new ArrayList<>();
                JumpGame.Start.addAll(pos);
                posName = "start";
            } else if (args[0].equalsIgnoreCase("end")) {
                JumpGame.End = new ArrayList<>();
                JumpGame.End.addAll(pos);
                posName = "end";
            } else {
                return false;
            }
            player.sendMessage(TextBuilder.create()
                    .append(TextBuilder.pluginName())
                    .text(" Change ", TextBuilder.GREEN)
                    .bold(posName, TextBuilder.GREEN)
                    .text(" to a new location : ",  TextBuilder.GREEN)
                    .bold(pos.toString(), TextBuilder.ORANGE)
                    .build());
        }

        return true;
    }
}
