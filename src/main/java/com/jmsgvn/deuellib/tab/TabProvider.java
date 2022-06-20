package com.jmsgvn.deuellib.tab;

import org.bukkit.entity.Player;

/**
 * API to allow plugins that depend on DeuelLib to enable a Tab
 */
public interface TabProvider {

    /**
     * Provide a player with a custom filled tab
     * @param player the players tab to edit
     * @return a TabLayout to be loaded by the player
     */
    TabLayout provide(Player player);
}
