package com.athens.athens2048;

import static com.athens.athens2048.Direction.BOTTOM;

public class DownCommand extends GameCommand implements Command {
    public DownCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }
    /*
    case BOTTOM:
    start = HEIGHT - 1;
    end = 0;
    maxPosition = HEIGHT;
    */

    public boolean execute(){

        int start = tiles.length - 1;
        int end = 0;
        int maxPosition = tiles.length;

        boolean merged = false;
        for (int position = 0; position < maxPosition; position++) {
            if (game.update(BOTTOM, position, start, end))
                merged =  true;
        }
        return merged;
    }

    public void undo(){

    }
}
