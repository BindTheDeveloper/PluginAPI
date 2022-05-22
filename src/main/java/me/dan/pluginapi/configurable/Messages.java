package me.dan.pluginapi.configurable;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;
import me.dan.pluginapi.message.TitleMessage;

import java.util.List;

@Getter
public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&8[&bAPI&8] &3"),
    NO_PERMISSION("no-permission", "{prefix}You need the permission {node} to perform this command!"),
    PLAYERS_ONLY("players-only", "{prefix}Only players can perform this command!"),
    USAGE("usage", "{prefix}Usage: {usage}");

    private final String path;
    @Setter
    private Object value;

    Messages(String key, Object value) {
        this.path = key;
        this.value = value;
    }

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}
