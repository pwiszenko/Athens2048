package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

public abstract class GameCommand implements Command{
    protected Game game;
    //protected Tile[][] tiles;
    protected Board board;
    //public void initialize(Tile [][] tiles, Game game){
    public void initialize(Board board, Game game){
        this.board = board;
        this.game = game;

    }
    public boolean execute(){
        return execute(board, true);
    }
    public abstract Direction getDirection();
}
