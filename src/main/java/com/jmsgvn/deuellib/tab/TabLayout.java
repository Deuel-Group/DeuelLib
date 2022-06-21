package com.jmsgvn.deuellib.tab;

import com.jmsgvn.deuellib.tab.common.SkinTexture;
import com.jmsgvn.deuellib.tab.common.TabListCommons;

/**
 * The TabLayout class that is designed for each player in the TabProvider
 */
public class TabLayout {

    /**
     * Tabs are set up as 4 columns with a maximum of 20 rows in each. By forcing the layout to be
     * a 2d array we increase performance (over a regular hashmap) and make sure all entries into
     * the layout are allowed and will show up for the player
     */
    private final String[][] layout = {
        {"","","","","","","","","","","","","","","","","","","",""},
        {"","","","","","","","","","","","","","","","","","","",""},
        {"","","","","","","","","","","","","","","","","","","",""},
        {"","","","","","","","","","","","","","","","","","","",""}};

    /**
     * Represents the pings that correlate to the layout
     */
    private final int[][] pings = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

    /**
     * Represents the skin of each player tab
     */
    private final SkinTexture[][] skinTextures = {
        {TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture},
        {TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture},
        {TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture},
        {TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture,
            TabListCommons.defaultTexture, TabListCommons.defaultTexture}
    };

    /**
     * The header the player will see in the tab
     */
    private String header = "";

    /**
     * The footer the player will see in the tab
     */
    private String footer = "";

    /**
     * Set the layout of the tab
     *
     * @param column the integer position between 0 and 3 of the column
     * @param row the integer position between 0 and 19 of the row
     * @param text the text to be placed in the layout
     * @param ping the ping displayed next to the layout
     */
    public void set(int column, int row, String text, int ping) {
        if (!validate(column, row)) {
            return;
        }

        layout[column][row] = text;
        pings[column][row] = ping;
    }

    /**
     * Set the layout of the tab
     *
     * @param column the integer position between 0 and 3 of the column
     * @param row the integer position between 0 and 19 of the row
     * @param text the text to be placed in the layout
     */
    public void set(int column, int row, String text) {
        set(column, row, text, 0);
    }

    /**
     * Set the skin of a player in tab
     *
     * @param column column of the player
     * @param row row of the player
     * @param skinTexture type of skin texture
     */
    public void setSkinTextures(int column, int row, SkinTexture skinTexture) {
        if (!validate(column, row)) {
            return;
        }

        skinTextures[column][row] = skinTexture;
    }

    /**
     * Get the skin texture of a player in the tab
     *
     * @param column column of the skin
     * @param row row of the skin
     * @return a skin texture if one exists
     */
    public SkinTexture getSkinTexture(int column, int row) {
        if (!validate(column, row)) {
            return null;
        }

        return skinTextures[column][row];
    }



    /**
     * Validate if a column and row are correct
     *
     * @param column the column
     * @param row the row
     * @return true if validation is successful
     */
    private boolean validate(int column, int row) {
        if (column < 0 || column > 3) {
            throw new IllegalArgumentException("The Tab column must be between 0 and 3");
        }

        if (row < 0 || row > 19) {
            throw new IllegalArgumentException("The Tab row must be between 0 and 19");
        }
        return true;
    }

    /**
     * Get the string of the layout at a certain position
     *
     * @param column the column of the string
     * @param row the row of the string
     * @return the string at the specified layout
     */
    public String getString(int column, int row) {
        if (!validate(column, row)) {
            return "null";
        }

        return layout[column][row];
    }

    /**
     * Get the ping of the layout at a certain position
     *
     * @param column the column of the ping
     * @param row the row of the ping
     * @return the ping integer of the specified layout
     */
    public int getPing(int column, int row) {
        if (!validate(column, row)) {
            return 0;
        }

        return pings[column][row];
    }

    /**
     * The header
     * @return the header
     */
    public String getHeader() {
        return this.header;
    }

    /**
     * Set the header
     * @param header the new header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Get the footer
     * @return the footer
     */
    public String getFooter() {
        return this.footer;
    }

    /**
     * Set the footer
     * @param footer the new footer
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

}
