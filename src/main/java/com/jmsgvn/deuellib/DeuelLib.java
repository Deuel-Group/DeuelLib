package com.jmsgvn.deuellib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.jmsgvn.deuellib.common.command.PingCommand;
import com.jmsgvn.deuellib.scoreboard.ScoreboardManager;
import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeuelLib extends JavaPlugin {

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        super.onEnable();
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        TabManager.init();
        ScoreboardManager.init();

        loadListeners();
        loadCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadListeners() {

    }

    private void loadCommands() {
        getCommand("ping").setExecutor(new PingCommand());
    }

    public static DeuelLib getInstance() {
      return JavaPlugin.getPlugin(DeuelLib.class);
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
