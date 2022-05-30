package me.dan.pluginapi;

import lombok.Getter;
import me.dan.pluginapi.configurable.Config;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.event.UserManagerLoadEvent;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.item.Item;
import me.dan.pluginapi.listener.InventoryClickListener;
import me.dan.pluginapi.listener.InventoryCloseListener;
import me.dan.pluginapi.location.LocationWrapper;
import me.dan.pluginapi.menu.Menu;
import me.dan.pluginapi.menu.MenuItem;
import me.dan.pluginapi.menu.MenuManager;
import me.dan.pluginapi.message.TitleMessage;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.pluginapi.user.UserManager;
import org.bukkit.Bukkit;

@Getter
public final class PluginAPI extends CustomPlugin {
    @Getter
    private static PluginAPI instance;

    private UserManager userManager;

    private MenuManager menuManager;

    @Override
    public void enable() {
        instance = this;
        this.menuManager = new MenuManager();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            this.userManager = new UserManager();
            Bukkit.getServer().getPluginManager().callEvent(new UserManagerLoadEvent());
        }, 20);
        registerSerializers(Item.class, Menu.class, MenuItem.class, LocationWrapper.class, TitleMessage.class);
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", getDataFolder().getAbsolutePath(), null, this), Messages.values());
        registerEvents(new InventoryClickListener(), new InventoryCloseListener());
    }

    @Override
    public void disable() {
        userManager.runSaveTask();
        instance = null;
    }
}
