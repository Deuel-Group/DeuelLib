package com.jmsgvn.deuellib.scoreboard.team;

import org.bukkit.entity.Player;

public class Team {
    private String teamName;
    private String displayName;
    private String teamPrefix;
    private String teamSuffix;
    private boolean nameTagVisible;
    private Player[] players;

    public Team(String teamName, String displayName, String teamPrefix, String teamSuffix,
        boolean nameTagVisible, Player[] players) {
        this.teamName = teamName;
        this.displayName = displayName;
        this.teamPrefix = teamPrefix;
        this.teamSuffix = teamSuffix;
        this.nameTagVisible = nameTagVisible;
        this.players = players;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTeamPrefix() {
        return teamPrefix;
    }

    public void setTeamPrefix(String teamPrefix) {
        this.teamPrefix = teamPrefix;
    }

    public String getTeamSuffix() {
        return teamSuffix;
    }

    public void setTeamSuffix(String teamSuffix) {
        this.teamSuffix = teamSuffix;
    }

    public boolean isNameTagVisible() {
        return nameTagVisible;
    }

    public void setNameTagVisible(boolean nameTagVisible) {
        this.nameTagVisible = nameTagVisible;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
