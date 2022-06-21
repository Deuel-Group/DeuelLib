package com.jmsgvn.deuellib.scoreboard;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public interface ScoreboardProvider {

    void provide(LinkedList<String> lines, Player player);
}
