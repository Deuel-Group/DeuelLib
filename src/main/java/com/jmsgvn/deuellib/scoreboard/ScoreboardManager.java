package com.jmsgvn.deuellib.scoreboard;

import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.scoreboard.listener.ScoreboardListener;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {

    private static Map<UUID, PlayerBoard> boards = new ConcurrentHashMap<>();
    private static boolean initiated = false;

    private static String scoreboardTitle = "";
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

    public static String getScoreboardTitle() {
        return scoreboardTitle;
    }

    public static void setScoreboardTitle(String scoreboardTitle) {
        ScoreboardManager.scoreboardTitle = scoreboardTitle;
    }
}
