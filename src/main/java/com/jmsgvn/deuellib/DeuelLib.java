package com.jmsgvn.deuellib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeuelLib extends JavaPlugin {

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        super.onEnable();
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        TabManager.init();

        loadListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadListeners() {

    }

    public static DeuelLib getInstance() {
      return JavaPlugin.getPlugin(DeuelLib.class);
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
