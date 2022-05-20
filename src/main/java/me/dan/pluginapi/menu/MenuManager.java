package me.dan.pluginapi.menu;

import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.user.User;
import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    private final Map<Menu, MenuPerform> menuPerformMap;

    private final Map<User, Menu> currentMenu;

    public MenuManager() {
        this.menuPerformMap = new HashMap<>();
        this.currentMenu = new HashMap<>();
    }

    public void setMenu(User user, Menu menu, Placeholder... placeholders) {
        currentMenu.put(user, menu);
        user.getPlayer().openInventory(menu.createInventory(placeholders));
    }

    public MenuPerform getMenuPerform(Menu menu) {
        return menuPerformMap.get(menu);
    }

    public void registerPerformMethod(Menu menu, MenuPerform menuPerform) {
        menuPerformMap.put(menu, menuPerform);
    }

    public void clearMenu(User user) {
        currentMenu.remove(user);
    }

    public Menu getMenu(User user) {
        return currentMenu.get(user);
    }

}
