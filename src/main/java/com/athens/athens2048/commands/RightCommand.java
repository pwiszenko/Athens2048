package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.RIGHT;

public class RightCommand  extends GameCommand implements Command {

    public RightCommand(Board board, Game game){
        initialize(board, game);
    }

    public boolean execute(Board board, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < board.getBoardWidth(); position++) {
            if (board.update(RIGHT, position, board.getBoardWidth() - 1, 0, updateScore))
                merged = true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.RIGHT;
    }

}
