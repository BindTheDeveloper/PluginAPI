package me.dan.pluginapi.menu;

import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.item.Item;

import java.util.List;
import java.util.Map;

public class MenuItem extends Serializable {

    private Item item;
    private String key;
    private boolean fill;
    private Integer[] slots;

    public MenuItem(Item item, String key, boolean fill, Integer... slots) {
        this.key = key;
        this.fill = fill;
        this.slots = slots;
        this.item = item;
    }

    public MenuItem() {

    }

    public MenuItem setItem(Item item) {
        this.item = item;
        return this;
    }

    public MenuItem setKey(String key) {
        this.key = key;
        return this;
    }

    public MenuItem setFill(boolean fill) {
        this.fill = fill;
        return this;
    }

    public MenuItem setSlots(Integer... slots) {
        this.slots = slots;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> stringObjectMap = item.serialize();
        stringObjectMap.put("key", key);
        stringObjectMap.put("fill", fill);
        stringObjectMap.put("slots", slots);

        return stringObjectMap;
    }

    public static MenuItem deserialize(YamlFile yamlFile, String path) {
        MenuItem menuItem = new MenuItem();
        menuItem.item = Item.deserialize(yamlFile, path);
        menuItem.fill = yamlFile.getConfig().getBoolean(path + ".fill");
        menuItem.key = yamlFile.getConfig().getString(path + ".key");
        Integer[] intArray = new Integer[0];
        List<Integer> integerList = yamlFile.getConfig().getIntegerList(path + ".slots");
        intArray = integerList.toArray(intArray);
        menuItem.slots = intArray;
        return menuItem;
    }

    public Item getItem() {
        return item;
    }

    public boolean isFill() {
        return fill;
    }

    public Integer[] getSlots() {
        return slots;
    }

    public String getKey() {
        return key;
    }
}
