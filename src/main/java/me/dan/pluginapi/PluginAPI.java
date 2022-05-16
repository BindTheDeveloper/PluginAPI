package me.dan.pluginapi;

import lombok.Getter;
import me.dan.pluginapi.configurable.Config;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.event.UserManagerLoadEvent;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.item.Item;
import me.dan.pluginapi.location.LocationWrapper;
import me.dan.pluginapi.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PluginAPI extends JavaPlugin {
    @Getter
    private static PluginAPI instance;

    private UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getScheduler().runTaskLater(this, () -> {
            this.userManager = new UserManager();
            Bukkit.getServer().getPluginManager().callEvent(new UserManagerLoadEvent());
        }, 20);
        Serialization.register(Item.class);
        Serialization.register(LocationWrapper.class);
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", getDataFolder().getAbsolutePath(), null, this), Messages.values());
    }

    @Override
    public void onDisable() {
        userManager.runSaveTask();
        instance = null;
    }
}
