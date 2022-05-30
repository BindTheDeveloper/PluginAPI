package me.dan.pluginapi.menu;

import me.dan.pluginapi.user.User;
import org.bukkit.inventory.Inventory;

public abstract class MenuPerform {

    public abstract boolean perform(MenuItem menuItem, User user);

    public abstract void onClose(User user, Inventory inventory);

}
