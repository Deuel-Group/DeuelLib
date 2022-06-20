package com.jmsgvn.deuellib.tab;

import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerTab {

    private final Map<String, String> previousNames = new HashMap<>();

    private final Map<String, Integer> previousPings = new HashMap<>();

    private String lastHeader = "";

    private String lastFooter = "";

    private boolean initiated = false;

    private UUID uuid;
    private TabLayout layout;

    public boolean isInitiated() {
        return this.initiated;
    }

    private void init() {
        if (!this.initiated) {
            layout = new TabLayout();
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 20; y++) {
                    addPlayer(ChatColor.RED + "", getIdentifier(x, y), 0);
                    previousNames.put(getIdentifier(x, y), "");
                    previousPings.put(getIdentifier(x, y), 0);
                }
            }
            initiated = true;
        }
    }

    public PlayerTab(UUID uuid) {
        this.uuid = uuid;
        init();
    }

    private void addPlayer(String name, String identifier, int ping) {
        addPlayer(name, ping, TabUtils.getOrCreateProfile(identifier));
    }

    private void addPlayer(String name, int ping, GameProfile profile) {
        PlayerInfoPacketMod playerInfoPacketMod = new PlayerInfoPacketMod();
        playerInfoPacketMod.addPlayer(this, name, ping, profile);
    }

    private void updatePlayer(String name, String identifier, int ping) {
        updatePlayer(name, ping, TabUtils.getOrCreateProfile(identifier));
    }

    private void updatePlayer(String name, int ping, GameProfile profile) {
        PlayerInfoPacketMod playerInfoPacketMod = new PlayerInfoPacketMod();
        playerInfoPacketMod.updateName(this, name, ping, profile);
    }

    private void updateHeaderAndFooter(String header, String footer) {
        PlayerInfoPacketMod playerInfoPacketMod = new PlayerInfoPacketMod();
        playerInfoPacketMod.updateHeaderAndFooter(toPlayer(), header, footer);
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getIdentifier(int column, int row) {
        if (row < 10) {
            return "$" + column + "0" + row;
        } else {
            return "$" + column + row;
        }
    }
    public void update() {

        if (!this.initiated) {
            return;
        }

        layout = TabManager.getProvider().provide(toPlayer());

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 20; y++) {
                String name = layout.getString(x, y);
                int ping = layout.getPing(x, y);

                if (previousNames.get(getIdentifier(x, y)).equalsIgnoreCase(name)) {
                    continue;
                }

                updatePlayer(name, getIdentifier(x, y), ping);

                if (!lastHeader.equalsIgnoreCase(layout.getHeader()) || !lastFooter.equalsIgnoreCase(
                    layout.getFooter())) {
                    updateHeaderAndFooter(layout.getHeader(), layout.getFooter());
                    lastHeader = layout.getHeader();
                    lastFooter = layout.getFooter();
                }

                previousNames.put(getIdentifier(x, y), name);
            }
        }
    }

}
