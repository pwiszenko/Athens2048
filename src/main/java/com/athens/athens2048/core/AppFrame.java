package com.athens.athens2048.core;

import com.athens.athens2048.gui.AppFrameTheme;
import com.athens.athens2048.gui.DayTheme;
import com.athens.athens2048.gui.NightTheme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class AppFrame extends JFrame implements GameOverListener {

    /**
     * Variables of type {@link JPanel} that represent the different game titles.
     */
    private JPanel gamePanel = new JPanel();
    private JPanel scorePanel = new JPanel();
    private JPanel bestScorePanel = new JPanel();
    private JPanel commandsPanel = new JPanel();

    /**
     * Variables for the player scores.
     */
    private JLabel score_text = new JLabel("Score:");
    private JLabel max_text = new JLabel("Best:");
    private JLabel current_score = new JLabel("0");
    private JLabel max_score = new JLabel("0");
    private int total_score = 0;
    private int best_score = 0;

    /**
     * Variables for the commands instructions
     */
    private JLabel keyTText = new JLabel("Press T to change the game theme");
    private JLabel keyUText = new JLabel("Press U to undo the move");
    private JLabel keyYText = new JLabel("Press Y to redo the move");
    private JLabel keyNText = new JLabel("Press N to restart the game");
    private JLabel keyEText = new JLabel("Press E to see next move (playback mode)");
    private JLabel keyRText = new JLabel("Press R to enter the playback mode");
    private JLabel keyCommands = new JLabel("Instructions:");

    /**
     * Theme variables of the game board - default: Day theme
     */
    private final int NIGHT = 1;
    private final int DAY = 0;
    private int currentTheme = DAY;
    private AppFrameTheme theme = new DayTheme();
    private JLabel themeText = new JLabel("Day theme", SwingConstants.CENTER);
    private Game game;

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
        game = new Game(this);
        startNewGame();

        // Add key listeners for Up/North/East/West keys
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
            }

            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.onKeyPressed(Direction.LEFT);
                }
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.onKeyPressed(Direction.RIGHT);
                }
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    game.onKeyPressed(Direction.TOP);
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.onKeyPressed(Direction.BOTTOM);
                }
                if (event.getKeyCode() == KeyEvent.VK_T) {
                    changeTheme();
                }
                if (event.getKeyCode() == KeyEvent.VK_N) {
                    startNewGame();
                }
                if (event.getKeyCode() == KeyEvent.VK_R) {
                    replayGame();
                }
                if (event.getKeyCode() == KeyEvent.VK_E) {
                    game.replay();
                }
                if (event.getKeyCode() == KeyEvent.VK_U) {
                    total_score = 0;
                    game.undo();
                    current_score.setText(Integer.toString(total_score));
                }
                if (event.getKeyCode() == KeyEvent.VK_Y) {
                    int old_score = total_score;
                    total_score = 0;
                    if (game.redo() == false){
                        total_score = old_score;
                    }
                    current_score.setText(Integer.toString(total_score));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void replayGame(){
        total_score = 0;
        game.resetTurnIndex();
        current_score.setText(Integer.toString(total_score));
    }

    private void startNewGame() {
        total_score = 0;
        game.reset();
        game.addGameOverListener(this);
        current_score.setText(Integer.toString(total_score));
    }

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
                gameTile[x][y].setPreferredSize(new Dimension(100, 100));
                gameTile[x][y].setEnabled(false);
                gameTile[x][y].setBorderPainted(false);
                gameTile[x][y].setOpaque(true);
                gamePanel.add(gameTile[x][y]);
            }
        }
        // Add game titles JPanel to main JFrame
        this.add(gamePanel);

        // Add the score tile to the JFrame
        this.add(scorePanel);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(Box.createVerticalGlue());
        scorePanel.add(score_text);
        scorePanel.add(Box.createVerticalGlue());
        scorePanel.add(current_score);
        scorePanel.add(Box.createVerticalGlue());

        // Setup the score tile in the JFrame
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.setBounds(leftBorder + gameTitleBorder + max_tiles*100 + max_tiles*15 + 80, 50, 100, 60);

        score_text.setAlignmentX(CENTER_ALIGNMENT);
        score_text.setFont(new Font("Arial", Font.BOLD, 20));
        score_text.setForeground(Color.DARK_GRAY);

        current_score.setAlignmentX(CENTER_ALIGNMENT);
        current_score.setFont(new Font("Arial", Font.BOLD, 15));
        current_score.setForeground(Color.DARK_GRAY);

        // Add the best score tile to the JFrame
        this.add(bestScorePanel);
        bestScorePanel.setLayout(new BoxLayout(bestScorePanel, BoxLayout.Y_AXIS));
        bestScorePanel.add(Box.createVerticalGlue());
        bestScorePanel.add(max_text);
        bestScorePanel.add(Box.createVerticalGlue());
        bestScorePanel.add(max_score);
        bestScorePanel.add(Box.createVerticalGlue());

        // Setup the best score tile in the JFrame
        bestScorePanel.setBackground(Color.LIGHT_GRAY);
        bestScorePanel.setBounds(leftBorder + gameTitleBorder + max_tiles*100 + max_tiles*15 + 80, 130, 100, 60);

        max_text.setAlignmentX(CENTER_ALIGNMENT);
        max_text.setFont(new Font("Arial", Font.BOLD, 20));
        max_text.setForeground(Color.DARK_GRAY);

        max_score.setAlignmentX(CENTER_ALIGNMENT);
        max_score.setFont(new Font("Arial", Font.BOLD, 15));
        max_score.setForeground(Color.DARK_GRAY);

        // Add and setup the change theme text
        this.add(themeText);

        themeText.setFont(new Font("Arial", Font.PLAIN, 15));
        themeText.setBorder(LineBorder.createGrayLineBorder());
        themeText.setBounds(leftBorder + gameTitleBorder + max_tiles*100 + max_tiles*15 + 40, 210, 180, 50);

        // Add and setup the commands panel
        this.add(commandsPanel);
        commandsPanel.setOpaque(false);
        commandsPanel.setLayout(new GridLayout(7,1));
        commandsPanel.add(keyCommands);
        commandsPanel.add(keyTText);
        commandsPanel.add(keyUText);
        commandsPanel.add(keyYText);
        commandsPanel.add(keyNText);
        commandsPanel.add(keyRText);
        commandsPanel.add(keyEText);
        commandsPanel.setBounds(leftBorder + gameTitleBorder + max_tiles*100 + max_tiles*15 + 20, 280, 240, 170);

        keyCommands.setFont(new Font("Arial", Font.BOLD, 15));
        keyTText.setFont(new Font("Arial", Font.PLAIN, 12));
        keyUText.setFont(new Font("Arial", Font.PLAIN, 12));
        keyYText.setFont(new Font("Arial", Font.PLAIN, 12));
        keyNText.setFont(new Font("Arial", Font.PLAIN, 12));
        keyRText.setFont(new Font("Arial", Font.PLAIN, 12));
        keyEText.setFont(new Font("Arial", Font.PLAIN, 12));

        // Setup the theme
        setTheme(DAY);
        themeText.setText("Change theme with T key");
    }

    /**
     * Updates the color and text of a title given an x and y (with x and y starting on 1 from the top left)
     */
    private void changeTheme(){
        switch (currentTheme) {
            case NIGHT:
                // If it's night, change to day theme
                setTheme(DAY);
                break;
            case DAY:
                // If it's day, change to night theme
                setTheme(NIGHT);
                break;
        }
    }

    /**
     * Set the theme
     */
    private void setTheme(int themeId){
        switch (themeId) {
            case NIGHT:
                currentTheme = NIGHT;
                theme = new NightTheme();
                themeText.setText("Night theme");
                break;
            case DAY:
                currentTheme = DAY;
                theme = new DayTheme();
                themeText.setText("Day theme");
                break;
        }
        theme.setPanelsBackground(this);
        theme.setThemeLabel(themeText);
        theme.setThemeLabel(keyCommands);
        theme.setThemeLabel(keyEText);
        theme.setThemeLabel(keyUText);
        theme.setThemeLabel(keyRText);
        theme.setThemeLabel(keyYText);
        theme.setThemeLabel(keyNText);
        theme.setThemeLabel(keyTText);

        updateThemeBoard();
    }

    /**
     * Updates the text of a tile given an x and y (with x and y starting on 1 from the top left)
     */
    public void updateTile(int x, int y, int value) {
        // Update text of the tile according to the value
        if (value < 2) {
            gameTile[x][y].setText("");
        } else {
            gameTile[x][y].setText(Integer.toString(value));
        }

        // Update color of the tile according to its value
        updateTileColor(x, y, value);
    }

    /**
     * Updates the color of a tie given an x and y (with x and y starting on 1 from the top left)
     */
    private void updateTileColor(int x, int y, int value){

        // Update the background color according to the value of the title
        theme.setTileColor(gameTile[x][y], value);

        // Change font size according to the number of digits needed to be displayed
        if (value > 512) {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 20));
        } else if (value > 64) {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 25));
        } else {
            gameTile[x][y].setFont(new Font("Arial", Font.BOLD, 30));
        }
    }

    /**
     * Repaints the whole board
     */
    private void updateThemeBoard(){
        for(int x = 0; x < max_tiles; x++) {
            for(int y = 0; y < max_tiles; y++) {
                if(!gameTile[x][y].getText().equals("")){
                    updateTileColor(x, y, Integer.parseInt(gameTile[x][y].getText()));
                }
            }
        }
    }

    /**
     * Changes the JLabel of the current score
     */
    public void increaseScore(int increment) {
        total_score += increment;
        current_score.setText(Integer.toString(total_score));
    }

    /**
     * Displays game over text
     */
    public void gameOver() {
        best_score = total_score > best_score ? total_score : best_score;
        max_score.setText(Integer.toString(best_score));
    }
}
