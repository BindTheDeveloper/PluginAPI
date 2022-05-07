package me.dan.pluginapi;

import lombok.Getter;
import me.dan.pluginapi.configurable.Config;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.item.Item;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.pluginapi.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PluginAPI extends JavaPlugin {
    @Getter
    private static PluginAPI instance;

    private UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;
        this.userManager = new UserManager();
        Serialization.register(Item.class);
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", getDataFolder().getAbsolutePath(), null, this), Messages.values());
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
