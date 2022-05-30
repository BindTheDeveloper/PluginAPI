package me.dan.pluginapi.listener;

import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.menu.Menu;
import me.dan.pluginapi.menu.MenuItem;
import me.dan.pluginapi.menu.MenuPerform;
import me.dan.pluginapi.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }

        Player player = (Player) e.getWhoClicked();
        User user = User.get(player.getUniqueId());

        Menu menu = PluginAPI.getInstance().getMenuManager().getMenu(user);

        if(menu == null){
            return;
        }

        MenuItem menuItem = menu.getMenuItem(e.getSlot());

        if(menuItem == null){
            return;
        }

        MenuPerform menuPerform = PluginAPI.getInstance().getMenuManager().getMenuPerform(menu);

        if(menuPerform == null){
            return;
        }

        e.setCancelled(menuPerform.perform(menuItem, user));

    }

}
