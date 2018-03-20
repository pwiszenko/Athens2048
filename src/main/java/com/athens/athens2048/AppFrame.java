package com.athens.athens2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class AppFrame extends JFrame {
    AppFrame() {

        // Set JFrame properties for the game board
        this.setTitle("2048 by ATHENS March 2018");
        this.setSize(800, 600);
        this.setLayout(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // Generates the game panels

        buildGameBoard();

        Game game = new Game(this);

        // Add key listeners for Up/North/East/West keys
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
            }

            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.onKeyPressed(Direction.WEST);
                }
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.onKeyPressed(Direction.EAST);
                }
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    game.onKeyPressed(Direction.NORTH);
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.onKeyPressed(Direction.SOUTH);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    /**
     * Variables of type {@link JPanel} that represent the different game titles.
     */
    private JPanel gamePanel = new JPanel();
    private JPanel scorePanel = new JPanel();
    private JPanel bestScorePanel = new JPanel();

    /**
     * Variables of type {@link JLabel} that represent the player scores.
     */
    private JLabel score_text = new JLabel("Score:");
    private JLabel max_text = new JLabel("Best:");
    private JLabel current_score = new JLabel("0");
    private JLabel max_score = new JLabel("0");

    // Bord size (default: 4 - 4x4)
    private int max_tiles = 4;

    // X and Y where the titles start to be drawn
    private int leftBorder = 40;
    private int topBorder = 40;
    private int gameTitleBorder = 10;

    /**
     * Variable of type {@link JButton} that represents an array of game titles.
     */
    private JButton[][] gameTile = new JButton[max_tiles][max_tiles];

    /**
     * Creates the game titles and the other elements in the {@link JFrame}
     */
    private void buildGameBoard() {

        // Setup the game title JPanel background and dimensions
        GridLayout customGridLayout = new GridLayout(max_tiles, max_tiles);
        customGridLayout.setHgap(15);
        customGridLayout.setVgap(15);

        gamePanel.setLayout(customGridLayout);
        gamePanel.setBounds(leftBorder, topBorder,
                2*gameTitleBorder + 100*max_tiles + 15*(max_tiles-1),
                2*gameTitleBorder + 100*max_tiles + 15*(max_tiles-1));
        gamePanel.setBackground(Color.LIGHT_GRAY);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(gameTitleBorder, gameTitleBorder, gameTitleBorder, gameTitleBorder));

        // Setup the game titles of the game titles JPanel.
        for (int x = 0; x < max_tiles; x++) {
            for (int y = 0; y < max_tiles; y++) {
                gameTile[x][y] = new JButton("");
                updateTile(x,y,0);
                gameTile[x][y].setPreferredSize(new Dimension((int) 100, (int) 100));
                gameTile[x][y].setEnabled(false);
                gameTile[x][y].setBorderPainted(false);
                gamePanel.add(gameTile[x][y]);
            }
        }
        // Add game titles JPanel to main JFrame
        this.add(gamePanel);

        // Add the score titles to the JFrame
        this.add(scorePanel);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(Box.createVerticalGlue());
        scorePanel.add(score_text);
        scorePanel.add(Box.createVerticalGlue());
        scorePanel.add(current_score);
        scorePanel.add(Box.createVerticalGlue());

        // Setup the scores titles in the JFrame
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.setBounds(leftBorder + gameTitleBorder + max_tiles*100 + max_tiles*15 + 80, 150, 100, 60);

        score_text.setAlignmentX(CENTER_ALIGNMENT);
        score_text.setFont(new Font("Arial", Font.BOLD, 20));
        score_text.setForeground(Color.DARK_GRAY);

        current_score.setAlignmentX(CENTER_ALIGNMENT);
        current_score.setFont(new Font("Arial", Font.BOLD, 15));
        current_score.setForeground(Color.DARK_GRAY);
    }

    /**
     * Updates the color and text of a title given an x and y (with x and y starting on 1 from the top left)
     */
    public void updateTile(int x, int y, int value){

        // Update text of the tile according to the value
        if (value < 2) {
            gameTile[x][y].setText("");
        } else {
            gameTile[x][y].setText(Integer.toString(value));
        }

        // Update the background color according to the value of the title
        gameTile[x][y].setOpaque(true);
        switch (value){
            case 2:
                gameTile[x][y].setBackground(new Color(238,228,218));
                break;
            case 4:
                gameTile[x][y].setBackground(new Color(237,218,224));
                break;
            case 8:
                gameTile[x][y].setBackground(new Color(242,177,121));
                break;
            case 16:
                gameTile[x][y].setBackground(new Color(245,149,99));
                break;
            case 32:
                gameTile[x][y].setBackground(new Color(246,124,95));
                break;
            case 64:
                gameTile[x][y].setBackground(new Color(246,94,59));
                break;
            case 128:
                gameTile[x][y].setBackground(new Color(237,207,114));
                break;
            case 254:
                gameTile[x][y].setBackground(new Color(237,204,97));
                break;
            case 512:
                gameTile[x][y].setBackground(new Color(237,200,80));
                break;
            case 1024:
                gameTile[x][y].setBackground(new Color(237,197,63));
                break;
            case 2048:
                gameTile[x][y].setBackground(new Color(237,194,46));
                break;
            default:
                // Empty title
                gameTile[x][y].setBackground(Color.GRAY);
        }

        // Change font size according to the number of digits needed to be displayed
        if (value > 512) {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 20));
        } else if (value > 64) {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 25));
        } else {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 30));
        }
    }

}
