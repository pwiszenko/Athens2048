package com.athens.athens2048;

import static com.athens.athens2048.Direction.TOP;

public class UpCommand extends GameCommand implements Command{

    public UpCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(){
        boolean merged = false;
        for (int position = 0; position < tiles.length; position++) {
            if (game.update(TOP, position, 0, tiles.length - 1))
                merged = true;
        }

        return merged;
    }

}
