package com.jmsgvn.deuellib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.jmsgvn.deuellib.common.command.PingCommand;
import com.jmsgvn.deuellib.event.ChatListener;
import com.jmsgvn.deuellib.scoreboard.ScoreboardManager;
import com.jmsgvn.deuellib.scoreboard.command.ToggleScoreboardCommand;
import com.jmsgvn.deuellib.tab.TabManager;
import com.jmsgvn.deuellib.tab.command.ToggleTabCommand;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class DeuelLib extends JavaPlugin {

    private ProtocolManager protocolManager;
    private LuckPerms luckPerms;
    private static JedisPool pool;
    @Override public void onEnable() {
        super.onEnable();

        long start = System.currentTimeMillis();

        pool = new JedisPool("localhost", 6379);

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
        pool.close();
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }

    private void loadSettings() {
        if (getConfig().getBoolean("settings.tab")) {
            TabManager.init();
        }

        if (getConfig().getBoolean("settings.scoreboard")) {
            ScoreboardManager.init();
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

    }

    private void loadCommands() {
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

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
