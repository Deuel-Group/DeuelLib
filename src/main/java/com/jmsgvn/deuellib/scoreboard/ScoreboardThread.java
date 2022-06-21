package com.jmsgvn.deuellib.scoreboard;

import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.tab.PlayerTab;
import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ScoreboardThread extends Thread{

    private Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

    public ScoreboardThread() {
        setName("Deuel - Scoreboard Thread");
        setDaemon(true);
    }

    public void run() {
        while (DeuelLib.getInstance().isEnabled() && this.protocolLib != null && this.protocolLib.isEnabled()) {
            for (Player player : DeuelLib.getInstance().getServer().getOnlinePlayers()) {
                try {
                    ScoreboardManager.update(player);
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
