package me.dan.pluginapi.plugin;

import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class CustomPlugin extends JavaPlugin {

    public CustomPlugin() {
    }

    public void registerCommands(AbstractCommand... abstractCommands) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            for (AbstractCommand abstractCommand : abstractCommands) {
                commandMap.register(abstractCommand.getLabel(), abstractCommand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, this));
    }

    public abstract void enable();

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public abstract void disable();

}
