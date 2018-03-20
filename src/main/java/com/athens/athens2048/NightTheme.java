package com.athens.athens2048;

import javax.swing.*;
import java.awt.*;

public class NightTheme implements AppFrameTheme {
    @Override
    public void setTileColor(JButton tile, int value) {
        switch (value){
            case 2:
                tile.setBackground(new Color(66,85,99));
                break;
            case 4:
                tile.setBackground(new Color(69,184,172));
                break;
            case 8:
                tile.setBackground(new Color(148,177,121));
                break;
            case 16:
                tile.setBackground(new Color(200,0,161));
                break;
            case 32:
                tile.setBackground(new Color(0,175,215));
                break;
            case 64:
                tile.setBackground(new Color(246,94,59));
                break;
            case 128:
                tile.setBackground(new Color(255,105,0));
                break;
            case 256:
                tile.setBackground(new Color(196,214,0));
                break;
            case 512:
                tile.setBackground(new Color(0,156,222));
                break;
            case 1024:
                tile.setBackground(new Color(239,66,111));
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
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
    }

    @Override
    public void setPanelsBackground(JFrame frame) {
        frame.getContentPane().setBackground(Color.BLACK);
    }
}
