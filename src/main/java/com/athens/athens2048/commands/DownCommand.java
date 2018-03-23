package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;


import static com.athens.athens2048.core.Direction.BOTTOM;

public class DownCommand extends GameCommand implements Command {

    public DownCommand(Board board){
        initialize(board);
    }

    public boolean execute(Board board, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < board.getHeight(); position++) {
            if (board.update(BOTTOM, position, board.getHeight() - 1, 0, updateScore))
                merged =  true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.BOTTOM;
    }

}
