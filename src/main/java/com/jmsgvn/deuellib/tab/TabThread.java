package com.jmsgvn.deuellib.tab;

import com.jmsgvn.deuellib.DeuelLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TabThread extends Thread{
    private final Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

    public TabThread() {
        setName("Deuel - Tab Thread");
        setDaemon(true);
    }

    public void run() {
        while (DeuelLib.getInstance().isEnabled() && this.protocolLib != null && this.protocolLib.isEnabled()) {
            for (PlayerTab tab : TabManager.getPlayerTabs().values()) {
                try {
                    if (TabManager.isInitiated()) {
                        tab.update();
                    } else {
                        tab.destroy();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(250L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
