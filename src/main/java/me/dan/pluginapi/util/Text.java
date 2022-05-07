package me.dan.pluginapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Text {

    public String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
