package fr.colddestructor.ladyCraftPlugin.manager;

import fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin;
import fr.colddestructor.ladyCraftPlugin.commands.ChatGameCommand;
import fr.colddestructor.ladyCraftPlugin.commands.ReloadCommand;
import fr.colddestructor.ladyCraftPlugin.listener.ChatGameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public final class RegisterManager {

    private final LadyCraftPlugin plugin;

    public RegisterManager(LadyCraftPlugin plugin) {
        this.plugin = plugin;
    }


    public void register() {
        registerEvents();
        registerCommands();
    }

    public void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ChatGameListener(), this.plugin);
    }

    public void  registerCommands(){
        Objects.requireNonNull(this.plugin.getCommand("chatgame")).setExecutor(new ChatGameCommand());
        Objects.requireNonNull(this.plugin.getCommand("lcreload")).setExecutor(new ReloadCommand());
    }

}
