package me.dan.pluginapi.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class TitleMessage extends Serializable {

    private final String header;
    private final String footer;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("header", header);
        map.put("footer", footer);
        return map;
    }

    public static TitleMessage deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        String header = c.getString(path + ".header");
        String footer = c.getString(path + ".footer");
        return new TitleMessage(header, footer);
    }

}
