package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.LEFT;

public class LeftCommand extends GameCommand implements Command {

    public LeftCommand(Board board, Game game){
        initialize(board, game);
    }

    public boolean execute(Board board, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < board.getBoardWidth(); position++) {
            if (board.update(LEFT, position, 0, board.getBoardWidth()-1, updateScore))
                merged = true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.LEFT;
    }
}
