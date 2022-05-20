package me.dan.pluginapi.message;

import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.util.Text;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public interface Message {

    String getString();

    List<String> getStringList();

    String getPrefix();

    default void send(CommandSender player, Placeholder... placeholders) {

        if (player == null) {
            return;
        }

        try{
            sendList(player, placeholders);
            return;
        }catch (ClassCastException ignored){

        }

        String text = this.getString();

        text = Placeholder.apply(text, placeholders);

        player.sendMessage(Text.c(text.replace("{prefix}", getPrefix())));
    }

    default void sendList(CommandSender player, Placeholder... placeholders) {

        if(player == null){
            return;
        }

        StringBuilder text = new StringBuilder();
        for (String message : this.getStringList()) {
            text.append(Placeholder.apply(message, placeholders)).append("\n");
        }

        player.sendMessage(Text.c(text.toString().replace("{prefix}", (String) getPrefix())));
    }

}
