package com.athens.athens2048.core;


import com.athens.athens2048.commands.*;
import com.athens.athens2048.random.DuoTuple;
import com.athens.athens2048.random.RandomTilePicker;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final int HEIGHT = 4;
    private final int WIDTH = 4;


    private boolean gameOver = false;
    private List<GameOverListener> gameOverListeners;

    private Tile tiles[][];
    private AppFrame frame;
    Command[] moveCommands;

    Game(AppFrame frame) {


        this.frame = frame;
        this.gameOverListeners = new ArrayList<>();

        tiles = new Tile[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
                tiles[i][j] = new Tile(0);
        }

        tiles[0][0].setNumber(2);
        tiles[0][1].setNumber(2);
        tiles[0][2].setNumber(4);
        tiles[0][3].setNumber(4);
        tiles[1][0].setNumber(0);
        tiles[1][1].setNumber(0);
        tiles[1][2].setNumber(2);
        tiles[1][3].setNumber(2);


        // Initialize commands
        moveCommands = new Command[Direction.directionCount];
        Command noCommand = new NoCommand(tiles, this);
        for (int i = 0; i < Direction.directionCount; i++) {
            moveCommands[i] = noCommand;
        }
        UpCommand upCommand = new UpCommand(tiles, this);
        DownCommand downCommand = new DownCommand(tiles, this);
        RightCommand rightCommand = new RightCommand(tiles, this);
        LeftCommand leftCommand = new LeftCommand(tiles, this);

        setCommand(Direction.TOP.getValue(), upCommand);
        setCommand(Direction.RIGHT.getValue(), rightCommand);
        setCommand(Direction.BOTTOM.getValue(), downCommand);
        setCommand(Direction.LEFT.getValue(), leftCommand);


        updateBoard();
    }


    public void addGameOverListener(GameOverListener gameOverListener) {
        this.gameOverListeners.add(gameOverListener);
    }

    public void removeGameOverListener(GameOverListener gameOverListener) {
        this.gameOverListeners.remove(gameOverListener);
    }

    private void notifyGameOverListeners() {
        for (GameOverListener listener : gameOverListeners)
            listener.gameOver();
    }

    public void setCommand(int slot, Command moveCommand) {
        moveCommands[slot] = moveCommand;
    }

    public boolean onMove(Direction direction) {
        return moveCommands[direction.getValue()].execute();

    }

    private void updateBoard() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                frame.updateTile(i, j, tiles[i][j].getNumber());
        frame.repaint();
    }

    void onKeyPressed(Direction direction) {
        if (gameOver)
            return;

        if (!merge(direction))
            return;

        DuoTuple<Integer, Integer> randomPoint = RandomTilePicker.getInstance().update(tiles);
        if (randomPoint != null) {
            int randomNumber = RandomTilePicker.getInstance().pickRandomTileValue();
            tiles[randomPoint.x][randomPoint.y].setNumber(randomNumber);
        }

        updateBoard();
        checkGameOver();
    }

    private void checkGameOver() {
        Tile [][] newTiles = new Tile[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            newTiles[i] = new Tile[tiles.length];
            for (int j = 0; j < tiles.length; j++) {
                newTiles[i][j] = new Tile(tiles[i][j].getNumber());
            }
        }

        boolean movePossible = false;
        for (Direction direction : Direction.values()) {
            if (merge(direction)) {
                movePossible = true;
                break;
            }
        }

        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles.length; j++)
                tiles[i][j].setNumber(newTiles[i][j].getNumber());

        if (!movePossible) {
            gameOver = true;
            notifyGameOverListeners();
        }
    }

    private boolean merge(Direction direction)
    {


        boolean merged = false;

        // USE COMMAND PATTERN
        merged = onMove(direction);

        return merged;
    }

    public boolean update(Direction direction, int position, int startIndex, int endIndex)
    {
        boolean merged = false;
        if (slide(direction, position, startIndex, endIndex))
            merged = true;

        int diff = startIndex < endIndex ? 1 : -1;

        for (int i = startIndex; i * diff <= (endIndex - diff) * diff; i += diff)
        {
            Tile currTile = Direction.isHorizontal(direction) ? tiles[position][i] : tiles[i][position];
            Tile nextTile = Direction.isHorizontal(direction) ? tiles[position][i + diff] : tiles[i + diff][position];

            if (currTile.getNumber() == nextTile.getNumber() && currTile.getNumber() != 0)
            {
                // merge
                merged = true;
                frame.increaseScore(2 * currTile.getNumber());
                currTile.setNumber(2 * currTile.getNumber());

                // brings every tile to the left
                int j = i + diff;
                while (j * diff <= (endIndex - diff) * diff)
                {
                    if (Direction.isHorizontal(direction)) {
                        tiles[position][j].setNumber(tiles[position][j + diff].getNumber());
                    } else {
                        tiles[j][position].setNumber(tiles[j + diff][position].getNumber());
                    }

                    j += diff;
                }

                // makes the last tile = 0
                if (Direction.isHorizontal(direction)) {
                    tiles[position][j].setNumber(0);
                } else {
                    tiles[j][position].setNumber(0);
                }
            }
        }
        return merged;
    }

    private boolean slide(Direction direction, int position, int startIndex, int endIndex)
    {
        boolean shifted = false;
        int diff = startIndex < endIndex ? 1 : -1;

        for (int i = startIndex + diff; i * diff <= endIndex * diff; i += diff) {
            Tile currTile = Direction.isHorizontal(direction) ? tiles[position][i] : tiles[i][position];
            Tile prevTile = Direction.isHorizontal(direction) ? tiles[position][i - diff] : tiles[i - diff][position];

            int currIndex = i;
            if (currTile.getNumber() == 0)
                continue;
            while (prevTile.getNumber() == 0) {
                shifted = true;
                prevTile.setNumber(currTile.getNumber());
                currTile.setNumber(0);
                currIndex -= diff;
                if (currIndex != startIndex) {
                    currTile = prevTile;
                    prevTile = Direction.isHorizontal(direction)
                            ? tiles[position][currIndex - diff] : tiles[currIndex - diff][position];
                }
                else
                    break;
            }
        }

        return shifted;
    }
}