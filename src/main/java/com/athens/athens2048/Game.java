package com.athens.athens2048;


import java.util.ArrayList;
import java.util.List;

public class Game {

    private final int HEIGHT = 4;
    private final int WIDTH = 4;

    private boolean gameOver = false;
    private List<GameOverListener> gameOverListeners;

    private Tile tiles[][];
    private AppFrame frame;

    Game(AppFrame frame) {
        this.frame = frame;
        this.gameOverListeners = new ArrayList<>();

        tiles = new Tile[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
                tiles[i][j] = new Tile(0);
        }

        tiles[0][0] = new Tile(2);
        tiles[0][1] = new Tile(2);
        tiles[0][2] = new Tile(4);
        tiles[0][3] = new Tile(4);
        tiles[1][0] = new Tile(0);
        tiles[1][1] = new Tile(0);
        tiles[1][2] = new Tile(2);
        tiles[1][3] = new Tile(2);
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
        int start = 0, end = 0, maxPosition = 0;
        switch (direction) {
            case LEFT:
                start = 0;
                end = WIDTH - 1;
                maxPosition = WIDTH;
                break;
            case RIGHT:
                start = WIDTH - 1;
                end = 0;
                maxPosition = WIDTH;
                break;
            case TOP:
                start = 0;
                end = HEIGHT - 1;
                maxPosition = HEIGHT;
                break;
            case BOTTOM:
                start = HEIGHT - 1;
                end = 0;
                maxPosition = HEIGHT;
                break;
        }

        boolean merged = false;
        for (int position = 0; position < maxPosition; position++) {
            if (update(direction, position, start, end))
                merged = true;
        }

        return merged;
    }

    private boolean update(Direction direction, int position, int startIndex, int endIndex)
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
