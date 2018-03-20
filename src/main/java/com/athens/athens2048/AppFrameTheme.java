package com.athens.athens2048;

import javax.swing.*;

/*
 * The interface defining the strategy.
 */

public interface AppFrameTheme {
    // Sets the colors of the theme (background and font)
    public void setTileColor(JButton tile, int value);

    // Sets the colors of the theme (background and font)
    public void setThemeLabel(JLabel button);

    // Sets the background of the game panels
    public void setPanelsBackground(JFrame frame);
}
