package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;


import static com.athens.athens2048.core.Direction.LEFT;

public class LeftCommand extends GameCommand implements Command {

    public LeftCommand(Board board){
        initialize(board);
    }

    public boolean execute(Board board, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < board.getWidth(); position++) {
            if (board.update(LEFT, position, 0, board.getWidth()-1, updateScore))
                merged = true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.LEFT;
    }
}
