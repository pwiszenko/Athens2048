package com.athens.athens2048;


public class NoCommand extends GameCommand implements Command{
    public NoCommand(Tile [][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(){
        return false;
    }

    public void undo(){

    }
}
