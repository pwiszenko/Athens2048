package com.athens.athens2048;

import static com.athens.athens2048.Direction.BOTTOM;

public class DownCommand extends GameCommand implements Command {
    public DownCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }


    public boolean execute(){
        boolean merged = false;
        for (int position = 0; position < tiles.length; position++) {
            if (game.update(BOTTOM, position, tiles.length - 1, 0))
                merged =  true;
        }
        return merged;
    }

}
