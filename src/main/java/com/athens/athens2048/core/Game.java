package com.athens.athens2048.core;


import com.athens.athens2048.commands.*;
import com.athens.athens2048.random.DuoTuple;
import com.athens.athens2048.random.RandomTilePicker;
import java.util.ArrayList;
import java.util.List;


public class Game {

    private final int HEIGHT = 4;
    private final int WIDTH = 4;

    private int totalScore = 0;
    private int bestScore = 0;

    private boolean gameOver = false;
    private List<GameObserver> gameObservers = new ArrayList<>();

    private Tile tiles[][];
    private Tile firstTiles[][];
    private Command[] moveCommands;
    private ArrayList<Turn> turns;

    // For the replay functionality
    private int turnIndex = 0;
    private boolean undoing = false;

    Game() {
        reset();
        initCommands();
    }

    public void addGameObserver(GameObserver gameObserver) {
        this.gameObservers.add(gameObserver);
    }

    public void removeGameObserver(GameObserver gameObserver) {
        this.gameObservers.remove(gameObserver);
    }

    private void setScore(int score) {
        totalScore = score;
        for (GameObserver observer : gameObservers)
            observer.scoreUpdated(totalScore);
    }

    public void reset() {
        setScore(0);

        // Init the firstTiles array
        initPlayback();
        // Init the game's tile array
        initTiles();
        // Draw the board
        updateBoard();
    }

    private void initPlayback(){
        if(turns == null)
            turns = new ArrayList<>();
        else
            removeEnd(turns, 0);
        turnIndex = 0;
        undoing = false;

        if(firstTiles == null) {

            firstTiles = new Tile[HEIGHT][WIDTH];

            // Actually instanciate firstTiles's tiles
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++)
                    firstTiles[i][j] = new Tile(0);
            }
        }

        // Fill the tiles
        firstTiles[0][0].setNumber(2);
        firstTiles[0][1].setNumber(2);
        firstTiles[0][2].setNumber(4);
        firstTiles[0][3].setNumber(4);
        firstTiles[1][0].setNumber(0);
        firstTiles[1][1].setNumber(0);
        firstTiles[1][2].setNumber(2);
        firstTiles[1][3].setNumber(2);
        firstTiles[2][0].setNumber(0);
        firstTiles[2][1].setNumber(0);
        firstTiles[2][2].setNumber(0);
        firstTiles[2][3].setNumber(0);
        firstTiles[3][0].setNumber(0);
        firstTiles[3][1].setNumber(0);
        firstTiles[3][2].setNumber(0);
        firstTiles[3][3].setNumber(0);
    }

    // Function to call to restart from the beggining of the playback
    public void resetTurnIndex() {
        setScore(0);

        initTiles();
        turnIndex = 0;

        updateBoard();
    }

    // Function the undo the last done move
    public void undoStep() {
        setScore(0);

        initTiles();
        if(turnIndex <= 0) {
            if(undoing) {
                updateBoard();
                return;
            }
            undoing = true;
            turnIndex = turns.size() - 1;
        }
        else {
            if(!undoing) {
                undoing = true;
                turnIndex--;
            }
        }

        if (turns.size() == 0)
            return;

        for (int i = 0; i < turnIndex; i ++) {
            Turn turn  = turns.get(i);
            turn.command.execute();
            tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
        }
        turnIndex--;
        updateBoard();
    }

    public void redoStep() {
        int oldScore = totalScore;
        setScore(0);
        if (!redo())
            setScore(oldScore);
    }

    // Function the redo the last undone move
    private boolean redo() {
        setScore(0);

        initTiles();
        if(turnIndex  >= turns.size())
            return false;

        if(undoing) {
            undoing = false;
            if(turnIndex !=0)
                turnIndex += 2;
            else {
                //do the first move
                Turn turn  = turns.get(0);
                turn.command.execute();
                tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
                turnIndex++;
                updateBoard();
                return true;
            }
        } else {
            turnIndex++;
        }
        if(turns.size() == 0)
            return false;
        for(int i = 0; i < turnIndex; i ++){


            Turn turn  = turns.get(i);
            turn.command.execute();
            tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
        }
        updateBoard();
        return true;
    }

    // Function to call to step through the playback steps by one
    public void replay(){
        if(turnIndex >= turns.size()){
            return;
        }
        Turn turn  = turns.get(turnIndex);
        turn.command.execute();
        tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
        turnIndex++;
        updateBoard();
    }

    private void initCommands() {
        moveCommands = new Command[Direction.directionCount];
        Command noCommand = new NoCommand(tiles, this);
        for (int i = 0; i < Direction.directionCount; i++) {
            moveCommands[i] = noCommand;
        }
        UpCommand upCommand = new UpCommand(tiles, this);
        DownCommand downCommand = new DownCommand(tiles, this);
        RightCommand rightCommand = new RightCommand(tiles, this);
        LeftCommand leftCommand = new LeftCommand(tiles, this);

        setCommand(Direction.TOP.getValue(), upCommand);
        setCommand(Direction.RIGHT.getValue(), rightCommand);
        setCommand(Direction.BOTTOM.getValue(), downCommand);
        setCommand(Direction.LEFT.getValue(), leftCommand);
    }

    // Function to reset the board's tiles to the starting state
    // It actually copies values from 'firstTiles' array to 'tiles' array
    private void initTiles() {
        if(tiles == null) {
            tiles = new Tile[HEIGHT][WIDTH];
        }

        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
                // Copy firstTiles's values to game's tiles array
                if(tiles[i][j] == null)
                    tiles[i][j] = new Tile(firstTiles[i][j].getNumber());
                else
                    tiles[i][j].setNumber(firstTiles[i][j].getNumber());
        }
    }

    private void setCommand(int slot, Command moveCommand) {
        moveCommands[slot] = moveCommand;
    }

    // OnMove function for regular moves
    // Regular meaning that we actully edit the 'tiles' array
    // Non-regular is for the other onMove() which is used when doing checkGameOver()
    private boolean onMove(Direction direction) {
        return moveCommands[direction.getValue()].execute();
    }

    // OnMove function for non-regular moves
    // Non-regular is used when doing checkGameOver()
    // Regular moves is for when we actually edit the 'tiles' array
    private boolean onMove(Direction direction, Tile[][] theTiles) {
        return moveCommands[direction.getValue()].execute(theTiles, false);
    }

    private void updateBoard() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                for (GameObserver observer : gameObservers)
                    observer.updateTile(i, j, tiles[i][j].getNumber());
            }
    }

    // Function to remove the end of an ArrayList
    private static void removeEnd(ArrayList<Turn> turns, int includedStart){
        int size = turns.size();
        for(int i = includedStart; i< size; i++){
            turns.remove(turns.size()-1);
        }
    }

    void onKeyPressed(Direction direction) {
        if (gameOver)
            return;

        if(turnIndex > 0 && turnIndex < turns.size()){
            removeEnd(turns, turnIndex);
            turnIndex = 0;
        }

        if (!merge(direction))
            return;


        DuoTuple<Integer, Integer> randomPoint = RandomTilePicker.getInstance().update(tiles);

        if (randomPoint != null) {
            int randomNumber = RandomTilePicker.getInstance().pickRandomTileValue();
            tiles[randomPoint.x][randomPoint.y].setNumber(randomNumber);
            registerTurn(direction, randomNumber, randomPoint);
        }

        updateBoard();
        checkGameOver();
    }

    private void registerTurn(Direction direction, int randomNumber, DuoTuple<Integer,Integer> coordinates) {
        turns.add(new Turn(randomNumber, coordinates, moveCommands[direction.getValue()]));
    }

    private void checkGameOver() {
        Tile [][] newTiles = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            //newTiles[i] = new Tile[tiles.length];
            for (int j = 0; j < tiles[0].length; j++) {
                newTiles[i][j] = new Tile(tiles[i][j].getNumber());
            }
        }

        boolean movePossible = false;
        for (Direction direction : Direction.values()) {
            if (merge(direction, newTiles)) {
                movePossible = true;
                break;
            }
        }

        if (!movePossible) {
            gameOver = true;

            bestScore = bestScore < totalScore ? totalScore : bestScore;

            for (GameObserver observer : gameObservers)
                observer.gameOver(bestScore);
        }
    }

    private boolean merge(Direction direction)
    {
        boolean merged;

        // USE COMMAND PATTERN
        merged = onMove(direction);

        return merged;
    }

    private boolean merge(Direction direction, Tile [][] theTiles)
    {
        boolean merged;

        // USE COMMAND PATTERN
        merged = onMove(direction, theTiles);

        return merged;
    }

    public boolean update(Direction direction, int position, int startIndex, int endIndex, Tile [][] theTiles, boolean updateScore)
    {
        boolean merged = false;
        if (slide(direction, position, startIndex, endIndex, theTiles))
            merged = true;

        int diff = startIndex < endIndex ? 1 : -1;

        for (int i = startIndex; i * diff <= (endIndex - diff) * diff; i += diff)
        {
            Tile currTile = Direction.isHorizontal(direction) ? theTiles[position][i] : theTiles[i][position];
            Tile nextTile = Direction.isHorizontal(direction) ? theTiles[position][i + diff] : theTiles[i + diff][position];

            if (currTile.getNumber() == nextTile.getNumber() && currTile.getNumber() != 0)
            {
                // merge
                merged = true;
                if(updateScore) {
                    setScore(totalScore + 2 * currTile.getNumber());
                }
                currTile.setNumber(2 * currTile.getNumber());

                // brings every tile to the left
                int j = i + diff;
                while (j * diff <= (endIndex - diff) * diff)
                {
                    if (Direction.isHorizontal(direction)) {
                        theTiles[position][j].setNumber(theTiles[position][j + diff].getNumber());
                    } else {
                        theTiles[j][position].setNumber(theTiles[j + diff][position].getNumber());
                    }

                    j += diff;
                }

                // makes the last tile = 0
                if (Direction.isHorizontal(direction)) {
                    theTiles[position][j].setNumber(0);
                } else {
                    theTiles[j][position].setNumber(0);
                }
            }
        }
        return merged;
    }

    private boolean slide(Direction direction, int position, int startIndex, int endIndex, Tile [][]theTiles)
    {
        boolean shifted = false;
        int diff = startIndex < endIndex ? 1 : -1;

        for (int i = startIndex + diff; i * diff <= endIndex * diff; i += diff) {
            Tile currTile = Direction.isHorizontal(direction) ? theTiles[position][i] : theTiles[i][position];
            Tile prevTile = Direction.isHorizontal(direction) ? theTiles[position][i - diff] : theTiles[i - diff][position];

            int currIndex = i;
            if (currTile.getNumber() == 0)
                continue;
            while (prevTile.getNumber() == 0) {
                shifted = true;
                prevTile.setNumber(currTile.getNumber());
                currTile.setNumber(0);
                currIndex -= diff;
                if (currIndex != startIndex) {
                    currTile = prevTile;
                    prevTile = Direction.isHorizontal(direction)
                            ? theTiles[position][currIndex - diff] : theTiles[currIndex - diff][position];
                }
                else
                    break;
            }
        }

        return shifted;
    }
}
