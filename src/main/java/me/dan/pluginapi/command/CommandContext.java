package me.dan.pluginapi.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
@Accessors(makeFinal = true)
public class CommandContext {

    private CommandSender commandSender;
    private String[] args;
    private String label;
}
