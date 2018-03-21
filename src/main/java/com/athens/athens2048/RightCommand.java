package com.athens.athens2048;

import static com.athens.athens2048.Direction.RIGHT;

public class RightCommand  extends GameCommand implements Command{
    public RightCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }

    /*
    case RIGHT:
    start = WIDTH - 1;
    end = 0;
    maxPosition = WIDTH;
    */

    public boolean execute(){

        int start = tiles[0].length - 1;
        int end = 0;
        int maxPosition = tiles[0].length;

        boolean merged = false;
        for (int position = 0; position < maxPosition; position++) {
            if (game.update(RIGHT, position, start, end))
                merged = true;
        }
        return merged;
    }

    public void undo(){

    }
}
