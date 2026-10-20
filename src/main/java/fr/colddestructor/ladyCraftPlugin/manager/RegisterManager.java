package fr.colddestructor.ladyCraftPlugin.manager;

import fr.colddestructor.ladyCraftPlugin.LadyCraftPlugin;
import fr.colddestructor.ladyCraftPlugin.commands.*;
import fr.colddestructor.ladyCraftPlugin.listener.ChatGameListener;
import fr.colddestructor.ladyCraftPlugin.listener.PlayerDataListener;
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
        pluginManager.registerEvents(new PlayerDataListener(), this.plugin);
    }

    public void  registerCommands(){
        Objects.requireNonNull(this.plugin.getCommand("chatgame")).setExecutor(new ChatGameCommand());
        Objects.requireNonNull(this.plugin.getCommand("lcreload")).setExecutor(new ReloadCommand());
        Objects.requireNonNull(this.plugin.getCommand("auralist")).setExecutor(new AuraListCommand());
        Objects.requireNonNull(this.plugin.getCommand("aurarank")).setExecutor(new AuraRankCommand());
        Objects.requireNonNull(this.plugin.getCommand("addword")).setExecutor(new AddWordCommand());
        Objects.requireNonNull(this.plugin.getCommand("delword")).setExecutor(new DelWordCommand());
    }

}
