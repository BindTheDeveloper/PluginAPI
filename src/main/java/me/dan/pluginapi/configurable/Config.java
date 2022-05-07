package me.dan.pluginapi.configurable;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;

@Getter
public enum Config implements Configuration {

    SAVE_INTERVAL("save-interval", 300);

    private final String path;
    @Setter
    private Object value;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }

}
