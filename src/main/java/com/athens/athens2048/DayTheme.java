package com.athens.athens2048;

import javax.swing.*;
import java.awt.*;

/*
 * Implementation of the strategy {@link AppFrameTheme}
 */
public class DayTheme implements AppFrameTheme{
    @Override
    public void setTileColor(JButton tile, int value) {
        switch (value){
            case 2:
                tile.setBackground(new Color(238,228,218));
                break;
            case 4:
                tile.setBackground(new Color(237,218,224));
                break;
            case 8:
                tile.setBackground(new Color(242,177,121));
                break;
            case 16:
                tile.setBackground(new Color(245,149,99));
                break;
            case 32:
                tile.setBackground(new Color(246,124,95));
                break;
            case 64:
                tile.setBackground(new Color(246,94,59));
                break;
            case 128:
                tile.setBackground(new Color(237,207,114));
                break;
            case 256:
                tile.setBackground(new Color(237,204,97));
                break;
            case 512:
                tile.setBackground(new Color(237,200,80));
                break;
            case 1024:
                tile.setBackground(new Color(237,197,63));
                break;
            case 2048:
                tile.setBackground(new Color(237,194,46));
                break;
            default:
                // Empty title
                tile.setBackground(Color.GRAY);
        }
    }

    @Override
    public void setThemeLabel(JLabel button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
    }

    @Override
    public void setPanelsBackground(JFrame frame) {
        frame.getContentPane().setBackground(Color.WHITE);
    }
}
