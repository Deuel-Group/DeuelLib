package com.jmsgvn.deuellib.tab.listener;

import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TabListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(DeuelLib.getInstance(), ()->{
            TabManager.add(event.getPlayer());
        }, 10L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TabManager.remove(event.getPlayer());
    }
}
