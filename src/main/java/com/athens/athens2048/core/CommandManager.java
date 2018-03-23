package com.athens.athens2048.core;

import com.athens.athens2048.commands.*;


public class CommandManager {
    private Command[] moveCommands;

    public void initCommands(Board board) {
        moveCommands = new Command[Direction.directionCount];
        Command noCommand = new NoCommand(board);
        for (int i = 0; i < Direction.directionCount; i++) {
            moveCommands[i] = noCommand;
        }
        UpCommand upCommand = new UpCommand(board);
        DownCommand downCommand = new DownCommand(board);
        RightCommand rightCommand = new RightCommand(board);
        LeftCommand leftCommand = new LeftCommand(board);

        setCommand(Direction.TOP.getValue(), upCommand);
        setCommand(Direction.RIGHT.getValue(), rightCommand);
        setCommand(Direction.BOTTOM.getValue(), downCommand);
        setCommand(Direction.LEFT.getValue(), leftCommand);
    }

    private void setCommand(int slot, Command moveCommand) {
        moveCommands[slot] = moveCommand;
    }

    public Command getCommand(Direction direction){
        return moveCommands[direction.getValue()];
    }

    // computeMove function for regular moves
    // Regular meaning that we actually edit the 'tiles' array
    // Non-regular is for the other computeMove() which is used when doing checkGameOver()
    public boolean computeMove(Direction direction) {
        return moveCommands[direction.getValue()].execute();
    }

    // computeMove function for non-regular moves
    // Non-regular is used when doing checkGameOver()
    // Regular moves is for when we actually edit the 'tiles' array
    //public boolean computeMove(Direction direction, Tile[][] theTiles) {
    public boolean computeMove(Direction direction, Board board) {
        return moveCommands[direction.getValue()].execute(board, false);
    }
}
