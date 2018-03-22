package com.athens.athens2048.commands;

import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.TOP;

public class UpCommand extends GameCommand implements Command {

    public UpCommand(Tile[][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(Tile[][] etiles, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < etiles.length; position++) {
            if (game.update(TOP, position, 0, etiles.length - 1, etiles, updateScore))
                merged = true;
        }

        return merged;
    }

}
