package me.dan.pluginapi.user;

import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.configurable.Config;
import me.dan.pluginapi.file.gson.GsonUtil;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class UserManager {

    public static final File FOLDER = new File(PluginAPI.getInstance().getDataFolder(), "user");

    private final List<User> saveQueue;

    private final Map<UUID, User> userMap;

    public UserManager() {
        this.userMap = new HashMap<>();
        this.saveQueue = new ArrayList<>();
        loadUsers();
        long delay = Config.SAVE_INTERVAL.getInt() * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginAPI.getInstance(), this::runSaveTask, delay, delay);
    }

    private void loadUsers() {

        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
            return;
        }

        if (FOLDER.listFiles() == null) {
            return;
        }

        for (File file : FOLDER.listFiles()) {
            String uuidString = file.getName().split("\\.")[0];
            UUID uuid;
            try {
                uuid = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                continue;
            }

            userMap.put(uuid, GsonUtil.read(FOLDER, file.getName(), User.class));

        }

    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    public User getUser(UUID uuid) {
        User user = userMap.get(uuid);
        if (user == null) {
            user = new User(uuid);
            userMap.put(uuid, user);
        }
        if (!saveQueue.contains(user)) {
            saveQueue.add(user);
        }
        return user;
    }

    public void runSaveTask() {

        List<User> savedUsers = new ArrayList<>();

        for (User user : saveQueue) {
            savedUsers.add(user);
            user.save();
        }

        saveQueue.removeAll(savedUsers);

    }

}
