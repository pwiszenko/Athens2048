package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

import static com.athens.athens2048.core.Direction.TOP;

public class UpCommand extends GameCommand implements Command {

    public UpCommand(Board board, Game game){
        initialize(board, game);
    }

    public boolean execute(Board board, boolean updateScore){
        boolean merged = false;
        for (int position = 0; position < board.getBoardHeight(); position++) {
            if (board.update(TOP, position, 0, board.getBoardHeight() - 1, updateScore))
                merged = true;
        }
        return merged;
    }

    public Direction getDirection(){
        return Direction.TOP;
    }
}
