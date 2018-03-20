package com.athens.athens2048;


import java.util.ArrayList;

public class Game {
    /**
     * Game controller - it should contain the 'game logic', i.e. turns, processing input events, etc.
     */

    private final int HEIGHT = 4;
    private final int WIDTH = 4;
    private Tile tiles[][];
    private AppFrame frame;

    Game(AppFrame frame) {
        this.frame = frame;
        tiles = new Tile[HEIGHT][WIDTH];
        for(int i=0; i<HEIGHT; i++)
        {
            for(int j=0; j<WIDTH; j++)
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

    void onKeyPressed(Direction direction) {
        switch (direction) {
            case NORTH:
                // do something with the board
                break;

            case EAST:
                break;

            case SOUTH:
                break;

            case WEST:
                leftMerge();
                updateBoard();
                break;
        }
    }

    private void updateBoard() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                frame.updateTile(i, j, tiles[i][j].getNumber());
    }

    private void _leftSlideRow(int row)
    {
        ArrayList<Tile> aux_tiles = new ArrayList<>();

        // sets all zeros to the right in aux_row
        for(int i=0; i<WIDTH; i++)
        {
            if(tiles[row][i].getNumber() >= 2)
            {
                aux_tiles.add(tiles[row][i]);
            }
        }

        int aux_index = 0;
        //copy aux_row to tiles
        for(int i=0; i<WIDTH; i++)
        {
            if (aux_index < aux_tiles.size()) {
                tiles[row][i].setNumber(aux_tiles.get(aux_index).getNumber());
                aux_index++;
            }
            else
                tiles[row][i].setNumber(0);
        }
    }

    private void _leftUpdateRow(int row)
    {
        _leftSlideRow(row);

        // checks if there can be a merge
        if(tiles[row][0].getNumber() == 0 || tiles[row][1].getNumber() == 0)
            return;

        for(int i=0; i<WIDTH-1; i++)
        {
            if(tiles[row][i].getNumber() == tiles[row][i+1].getNumber())
            {	// merge
                tiles[row][i].setNumber(2 * tiles[row][i].getNumber());

                // brings every tile to the left
                int j=i+1;
                while(j<WIDTH-1)
                {
                    tiles[row][j].setNumber(tiles[row][j+1].getNumber());
                    j++;
                }

                // makes the last tile = 0
                tiles[row][j].setNumber(0);
            }
        }
    }

    public void leftMerge()
    {
        for(int row=0; row<HEIGHT; row++)
            _leftUpdateRow(row);
    }
}
