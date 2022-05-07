package me.dan.pluginapi.item;

import lombok.Builder;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.util.Pair;
import me.dan.pluginapi.util.Text;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Item extends Serializable {

    private String material;
    private Byte data;
    private String name;
    private List<String> lore;
    private List<Pair<Enchantment, Integer>> enchantments;
    private List<ItemFlag> itemFlags;

    public ItemStack toItemStack(Placeholder... placeholders) {
        if (material == null) {
            return null;
        }

        this.material = material.toUpperCase();
        if (Material.getMaterial(material) == null) {
            return null;
        }

        ItemStack itemStack = new ItemStack(Material.getMaterial(material), 1, data != null ? data : 0);

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        if (name != null) {
            itemMeta.setDisplayName(Text.c(Placeholder.apply(name, placeholders)));
        }

        if (lore != null) {
            List<String> loreLines = new ArrayList<>();
            for (String loreLine : lore) {
                loreLines.add(Text.c(Placeholder.apply(loreLine, placeholders)));
            }
            itemMeta.setLore(loreLines);
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        }

        if (enchantments != null) {
            for (Pair<Enchantment, Integer> enchantment : enchantments) {
                itemStack.addUnsafeEnchantment(enchantment.getKey(), enchantment.getValue());
            }
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", material);
        if (data != null) {
            map.put("data", data.intValue());
        }
        if (name != null) {
            map.put("name", name);
        }
        if (lore != null) {
            map.put("lore", lore);
        }
        if (itemFlags != null) {
            List<String> itemFlags = new ArrayList<>();
            for (ItemFlag itemFlag : getItemFlags()) {
                itemFlags.add(itemFlag.name());
            }
            map.put("flags", itemFlags);
        }
        if (enchantments != null) {
            for (Pair<Enchantment, Integer> pair : enchantments) {
                map.put("enchantments." + pair.getKey().getName(), pair.getValue());
            }
        }

        return map;
    }

    public static Item deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        ItemBuilder build = Item.builder();
        build.material(c.getString(path + ".material"));
        if (c.contains(path + ".data")) {
            build.data((byte) c.getInt(path + ".data"));
        }
        if (c.contains(path + ".name")) {
            build.name(c.getString(path + ".name"));
        }
        if (c.contains(path + ".lore")) {
            build.lore(c.getStringList(path + ".lore"));
        }

        if (c.contains(path + ".flags")) {
            List<ItemFlag> itemFlags = new ArrayList<>();
            for (String flagString : c.getStringList(path + ".flags")) {
                try {
                    ItemFlag itemFlag = ItemFlag.valueOf(flagString.toUpperCase());
                    itemFlags.add(itemFlag);
                } catch (IllegalArgumentException e) {

                }
            }
            build.itemFlags(itemFlags);
        }

        if (c.contains(path + ".enchants")) {
            List<Pair<Enchantment, Integer>> enchantList = new ArrayList<>();
            for (String enchantKey : c.getConfigurationSection(path + ".enchants").getKeys(false)) {
                Enchantment enchantment = Enchantment.getByName(enchantKey.toUpperCase());
                if (enchantment == null) {
                    continue;
                }

                enchantList.add(new Pair<>(enchantment, c.getInt(path + ".enchants." + enchantKey)));
            }
            build.enchantments(enchantList);
        }

        return build.build();
    }

}
