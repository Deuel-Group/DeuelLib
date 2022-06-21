package com.jmsgvn.deuellib.scoreboard.listener;

import com.jmsgvn.deuellib.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ScoreboardManager.create(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ScoreboardManager.remove(event.getPlayer());
    }
}
