package me.dan.pluginapi.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class LocationWrapper extends Serializable {

    private final String world;
    private final int x, y, z;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", world);
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return map;
    }

    public static LocationWrapper deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        int x = c.getInt(path + ".x");
        int y = c.getInt(path + ".y");
        int z = c.getInt(path + ".z");
        String world = c.getString(path + ".world");
        return new LocationWrapper(world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocationWrapper)) {
            return false;
        }

        LocationWrapper obj = (LocationWrapper) o;

        return obj.x == this.x && obj.y == this.y && obj.z == this.z && obj.world.equalsIgnoreCase(this.world);

    }

}
