package me.dan.pluginapi.user;

import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.file.gson.GsonUtil;
import me.dan.pluginapi.user.data.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private final UUID uuid;

    private final HashMap<Class<? extends UserData>, UserData> userDataHashMap;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.userDataHashMap = new HashMap<>();
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public Player getPlayer() {
        return getOfflinePlayer().getPlayer();
    }

    public <T extends UserData> T getUserData(Class<T> type) {
        UserData userData;
        if (userDataHashMap.containsKey(type)) {
            userData = userDataHashMap.get(type);
        } else {
            Constructor<T> constructor;
            try {
                constructor = type.getDeclaredConstructor(UUID.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }

            try {
                userData = constructor.newInstance(uuid);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }

            userDataHashMap.put(type, userData);
        }
        return type.cast(userData);
    }

    public void save() {
        GsonUtil.save(UserManager.FOLDER, uuid.toString() + ".yml", this);
    }

    public static User get(UUID uuid) {
        return PluginAPI.getInstance().getUserManager().getUser(uuid);
    }

}
