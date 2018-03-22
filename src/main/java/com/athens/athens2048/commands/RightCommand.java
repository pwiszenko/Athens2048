package com.athens.athens2048.commands;

import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.RIGHT;

public class RightCommand  extends GameCommand implements Command {
    public RightCommand(Tile[][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(Tile[][] etiles, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < etiles[0].length; position++) {
            if (game.update(RIGHT, position, etiles[0].length - 1, 0, etiles, updateScore))
                merged = true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.RIGHT;
    }

}
