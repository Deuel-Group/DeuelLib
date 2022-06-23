package com.jmsgvn.deuellib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.jmsgvn.deuellib.common.command.PingCommand;
import com.jmsgvn.deuellib.scoreboard.ScoreboardManager;
import com.jmsgvn.deuellib.scoreboard.command.ToggleScoreboardCommand;
import com.jmsgvn.deuellib.tab.TabManager;
import com.jmsgvn.deuellib.tab.command.ToggleTabCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class DeuelLib extends JavaPlugin {

    private ProtocolManager protocolManager;
    private static JedisPool pool;
    @Override public void onEnable() {
        super.onEnable();

        long start = System.currentTimeMillis();

        pool = new JedisPool("localhost", 6379);

        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading DeuelLib...");

        saveDefaultConfig();

        this.protocolManager = ProtocolLibrary.getProtocolManager();

        loadListeners();
        loadCommands();
        loadSettings();

        getServer().getConsoleSender().sendMessage(
            ChatColor.GREEN + "DeuelLib enabled (" + (System.currentTimeMillis() - start) + "ms)");
    }

    @Override public void onDisable() {
        super.onDisable();
    }

    private void loadListeners() {
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading listeners");
    }

    private void loadSettings() {
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading settings");
        if (getConfig().getBoolean("settings.tab")) {
            TabManager.init();
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "    Tab enabled");
        }

        if (getConfig().getBoolean("settings.scoreboard")) {
            ScoreboardManager.init();
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "    Scoreboard enabled");
        }
    }

    private void loadCommands() {
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading commands");
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("toggletab").setExecutor(new ToggleTabCommand());
        getCommand("toggleboard").setExecutor(new ToggleScoreboardCommand());
    }

    public static DeuelLib getInstance() {
        return JavaPlugin.getPlugin(DeuelLib.class);
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public static JedisPool getPool() {
        return pool;
    }
}
