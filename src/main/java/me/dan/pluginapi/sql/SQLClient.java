package me.dan.pluginapi.sql;

import lombok.Getter;
import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.sql.builder.CreateBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;

@Getter
public class SQLClient {

    private final Plugin plugin;
    private final String ip;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    public SQLClient(Plugin plugin, String ip, int port, String database, String username, String password) {
        this.plugin = plugin;
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        openConnection();
        keepAlive();
    }

    private void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"
                            + ip + ":" + port + "/" + database,
                    username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void keepAlive() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, this::openConnection), 40, 40);
    }

    public void sendUpdateSync(String update) {
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet sendQuerySync(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable(String tableName, SQLColumn... sqlColumns) {
        CreateBuilder createBuilder = new CreateBuilder(tableName);
        for (SQLColumn sqlColumn : sqlColumns) {
            createBuilder.addColumn(sqlColumn);
        }
        sendUpdateSync(createBuilder.build());
    }



}
