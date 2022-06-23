package com.jmsgvn.deuellib.scoreboard;

import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.scoreboard.listener.ScoreboardListener;
import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {

    private static Map<UUID, PlayerBoard> boards = new ConcurrentHashMap<>();
    private static boolean initiated = false;
    private static ScoreboardProvider provider;
    public static void init() {
        initiated = true;
        (new ScoreboardThread()).start();
        DeuelLib.getInstance().getServer().getPluginManager().registerEvents(new ScoreboardListener(), DeuelLib.getInstance());
    }

    public static ScoreboardProvider getProvider() {
        return provider;
    }

    public static void setProvider(ScoreboardProvider provider) {
        ScoreboardManager.provider = provider;
    }

    public static void create(Player player) {
        boards.put(player.getUniqueId(), new PlayerBoard(player));
    }

    public static void remove(Player player) {
        boards.remove(player.getUniqueId());
    }

    public static PlayerBoard getBoard(Player player) {
        return boards.get(player.getUniqueId());
    }

    public static void update(Player player) {
        PlayerBoard board = getBoard(player);
        if (board != null) board.update();
    }

    public static String getScoreboardTitle(Player player) {
        return provider.title(player);
    }

    public static Map<UUID, PlayerBoard> getBoards() {
        return boards;
    }

    public static boolean isInitiated() {
        return initiated;
    }

    public static void setInitiated(boolean initiated) {
        ScoreboardManager.initiated = initiated;
//        Bukkit.getScheduler().runTaskLater(DeuelLib.getInstance(), ()-> {
//            if (!initiated) {
//                boards.clear();
//            } else {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    ScoreboardManager.create(player);
//                }
//            }
//        }, 20L);
    }
}
