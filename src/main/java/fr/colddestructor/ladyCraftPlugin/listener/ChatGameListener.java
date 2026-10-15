package fr.colddestructor.ladyCraftPlugin.listener;

import fr.colddestructor.ladyCraftPlugin.game.ChatGame;
import fr.colddestructor.ladyCraftPlugin.helper.TextBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatGameListener implements Listener {

    @EventHandler()
    public void onChat(AsyncChatEvent event) {
        if (!ChatGame.chatGameActive){return;}
        Component component  = event.message();
        String text = PlainTextComponentSerializer.plainText().serialize(component);
        if(!text.equalsIgnoreCase(ChatGame.selectedWord)){return;}
        Player player = event.getPlayer();
        long time = System.currentTimeMillis() - ChatGame.askQuestionTime;
        ChatGame.chatGameActive = false;
        Component message = TextBuilder.create()
                .append(TextBuilder.pluginName())
                .text(" Le joueur ", TextBuilder.GREEN)
                .append(player.displayName())
                .text(" a répondu le premier en ", TextBuilder.GREEN)
                .bold((time/1000f) + "s",  TextBuilder.ORANGE)
                .build();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
    }
}
