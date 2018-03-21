package com.athens.athens2048.commands;

import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.LEFT;

public class LeftCommand extends GameCommand implements Command {
    public LeftCommand(Tile[][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(){

        boolean merged = false;
        for (int position = 0; position < tiles[0].length; position++) {
            if (game.update(LEFT, position, 0, tiles[0].length-1))
                merged = true;
        }
        return merged;
    }
}
