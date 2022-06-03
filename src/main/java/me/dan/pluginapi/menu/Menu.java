package me.dan.pluginapi.menu;

import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu extends Serializable {

    private Map<Integer, MenuItem> itemMap;

    private String name;
    private int size;

    public Menu(String name, int size) {
        this.name = name;
        this.size = size;
        this.itemMap = new HashMap<>();
    }

    public Menu() {
        this.itemMap = new HashMap<>();
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public Menu setSize(int size) {
        this.size = size;
        return this;
    }

    public Inventory createInventory(Placeholder... placeholders) {
        Inventory inventory = Bukkit.createInventory(null, size, Text.c(Placeholder.apply(name, placeholders)));
        setItems(inventory, placeholders);
        return inventory;
    }

    public void setItems(Inventory inventory, Placeholder... placeholders) {
        for (int i : itemMap.keySet()) {
            inventory.setItem(i - 1, itemMap.get(i).getItem().toItemStack(placeholders));
        }
        System.out.println("Items Set");
    }

    public Menu addItems(MenuItem... menuItems) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.isFill()) {
                for (int slot = 1; slot <= size; slot++) {
                    itemMap.put(slot, menuItem);
                }
                continue;
            }

            for (int slot : menuItem.getSlots()) {
                itemMap.put(slot, menuItem);
            }
        }
        return this;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("size", size);

        List<MenuItem> menuItems = new ArrayList<>();
        for (MenuItem menuItem : itemMap.values()) {
            if (!menuItems.contains(menuItem)) {
                menuItems.add(menuItem);
                Map<String, Object> menuItemSerialized = menuItem.serialize();
                for (String key : menuItemSerialized.keySet()) {
                    Object obj = menuItemSerialized.get(key);
                    String path = "items." + menuItem.getKey() + "." + key;
                    map.put(path, obj);
                }
            }
        }

        return map;
    }

    public static Menu deserialize(YamlFile yamlFile, String path) {
        Menu menu = new Menu();
        YamlConfiguration c = yamlFile.getConfig();
        menu.setName(c.getString(path + ".name"));
        menu.setSize(c.getInt(path + ".size"));
        List<MenuItem> menuItems = new ArrayList<>();
        if (yamlFile.getConfig().contains(path + ".items")) {
            for (String item : yamlFile.getConfig().getConfigurationSection(path + ".items").getKeys(false)) {
                MenuItem menuItem = MenuItem.deserialize(yamlFile, path + ".items." + item);
                menuItems.add(menuItem);
            }

            for (int i = 0; i < menuItems.size(); i++) {
                if (menuItems.get(i).isFill()) {
                    menu.addItems(menuItems.get(i));
                    menuItems.remove(menuItems.get(i));
                    break;
                }
            }
        }
        menu.addItems(menuItems.toArray(new MenuItem[0]));
        return menu;
    }

    public MenuItem getMenuItem(int slot) {
        return itemMap.get(slot + 1);
    }

}
