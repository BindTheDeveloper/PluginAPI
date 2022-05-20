package me.dan.pluginapi.menu;

import me.dan.pluginapi.user.User;

public abstract class MenuPerform {

    public abstract boolean perform(MenuItem menuItem, User user);

    public abstract void onClose(User user);

}
