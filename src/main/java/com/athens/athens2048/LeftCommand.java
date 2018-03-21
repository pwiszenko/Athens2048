package com.athens.athens2048;

import static com.athens.athens2048.Direction.LEFT;

public class LeftCommand extends GameCommand implements Command {
    public LeftCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }
    /*
    case LEFT:
    start = 0;
    end = WIDTH - 1;
    maxPosition = WIDTH;
    */

    public boolean execute(){


        int start = 0;
        int end =tiles[0].length-1;
        int maxPosition = tiles[0].length;

        boolean merged = false;
        for (int position = 0; position < maxPosition; position++) {
            if (game.update(LEFT, position, start, end))
                merged = true;
        }
        return merged;
    }

    public void undo(){

    }
}
