package com.athens.athens2048;

import static com.athens.athens2048.Direction.TOP;

public class UpCommand extends GameCommand implements Command{
/*
    case TOP:
    start = 0;
    end = HEIGHT - 1;
    maxPosition = HEIGHT;
*/


    public UpCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(){

        int start = 0;
        int end = tiles.length - 1;
        int maxPosition = tiles.length;

        boolean merged = false;
        for (int position = 0; position < maxPosition; position++) {
            if (game.update(TOP, position, start, end))
                merged = true;
        }

        return merged;
    }

    public void undo(){

    }

}
