package me.dan.pluginapi.listener;

import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.menu.Menu;
import me.dan.pluginapi.menu.MenuPerform;
import me.dan.pluginapi.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        User user = User.get(e.getPlayer().getUniqueId());

        Menu menu = PluginAPI.getInstance().getMenuManager().getMenu(user);

        if (menu == null) {
            return;
        }

        MenuPerform menuPerform = PluginAPI.getInstance().getMenuManager().getMenuPerform(menu);

        if (menuPerform == null) {
            return;
        }

        menuPerform.onClose(user, e.getInventory());

        PluginAPI.getInstance().getMenuManager().clearMenu(user);
    }

}
