package com.athens.athens2048.core;


import com.athens.athens2048.commands.*;
import com.athens.athens2048.random.DuoTuple;
import java.util.ArrayList;
import java.util.List;


public class Game implements ScoredCounter{

    private final int HEIGHT = 4;
    private final int WIDTH = 4;

    private int totalScore = 0;
    private int bestScore = 0;

    private boolean gameOver = false;
    private List<GameObserver> gameObservers = new ArrayList<>();

    //private Tile tiles[][];
    //private Tile firstTiles[][];
    private ArrayList<Turn> turns;
    CommandManager commandManager = new CommandManager();
    Board board;

    // For the replay functionality
    private int turnIndex = 0;
    private boolean undoing = false;
    private boolean atFirstStage = false;

    Game() {
        board = new Board(this);
        reset();
        commandManager.initCommands(board, this);
    }

    public void addGameObserver(GameObserver gameObserver) {
        this.gameObservers.add(gameObserver);
    }

    public void removeGameObserver(GameObserver gameObserver) {
        this.gameObservers.remove(gameObserver);
    }

    public void setScore(int score) {
        totalScore = score;
        for (GameObserver observer : gameObservers)
            observer.scoreUpdated(totalScore);
    }
    public int getScore(){
        return totalScore;
    }

    public void reset() {
        setScore(0);
        gameOver = false;
        // Init the firstTiles array
        initPlayback();
        // Init the game's tile array
        initTiles();
        // Draw the commandManager
        updateBoard();
    }

    private void initPlayback(){
        if(turns == null)
            turns = new ArrayList<>();
        else
            removeEnd(turns, 0);
        turnIndex = 0;
        undoing = false;

        board.initFirstStage(HEIGHT, WIDTH);
/*
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
*/
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
            board.setTileValue(turn.coordinates, turn.tileValue);
            //tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
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
                board.setTileValue(turn.coordinates, turn.tileValue);
                //tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
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
            board.setTileValue(turn.coordinates, turn.tileValue);
            //tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
        }
        updateBoard();
        return true;
    }

    // Function to call to restart from the beggining of the playback
    public void backToFirstStage() {
        setScore(0);
        initTiles();
        turnIndex = 0;
        updateBoard();
    }

    public void backToLastMove(){
        backToFirstStage();
        atFirstStage = false;
        while(replay() == true){

        }
        updateBoard();
    }

    // Function to call to step through the playback steps by one
    private boolean replay(){
        if(turnIndex >= turns.size()){
            return false;
        }
        Turn turn  = turns.get(turnIndex);
        turn.command.execute();
        board.setTileValue(turn.coordinates, turn.tileValue);
        //tiles[turn.coordinates.x][turn.coordinates.y].setNumber(turn.tileValue);
        turnIndex++;
        return true;
    }


    // Function to reset the commandManager's tiles to the starting state
    // It actually copies values from 'firstTiles' array to 'tiles' array
    private void initTiles() {
        board.resetToFirstStage(HEIGHT, WIDTH);
        /*
        if(tiles == null) {
            tiles = new Tile[HEIGHT][WIDTH];
        }

        for(int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++)
                // Copy firstTiles's values to game's tiles array
                if (tiles[i][j] == null)
                    tiles[i][j] = new Tile(firstTiles[i][j].getNumber());
                else
                    tiles[i][j].setNumber(firstTiles[i][j].getNumber());
        }
        */
        atFirstStage = true;
    }


    private void updateBoard() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                for (GameObserver observer : gameObservers)
                    observer.updateTile(i, j, board.getTileValue(i, j));
                    //observer.updateTile(i, j, tiles[i][j].getNumber());
            }
    }

    // Function to remove the end of an ArrayList
    private static void removeEnd(ArrayList<Turn> turns, int includedStart){
        int size = turns.size();
        for(int i = includedStart; i< size; i++){
            turns.remove(turns.size()-1);
        }
    }

    void  onKeyPressed(Direction direction) {
        if (gameOver)
            return;

        // If this we change the very first move from history

        if(turns.size()>0 && atFirstStage == true && direction != ((GameCommand)turns.get(0).command).getDirection()){
            turnIndex = 0;
            removeEnd(turns, turnIndex);
        }


        if(turnIndex > 0 && turnIndex <= turns.size()){
            removeEnd(turns, turnIndex);
            turnIndex = 0;
        }
        atFirstStage = false;
        if (!commandManager.computeMove(direction))
            return;

        DuoTuple<Integer, Integer> randomPoint = board.pickRandomTileCoordinates();
        //DuoTuple<Integer, Integer> randomPoint = RandomTilePicker.getInstance().update(tiles);

        if (randomPoint != null) {
            int randomNumber = board.pickRandomTileValue();
            board.setTileValue(randomPoint, randomNumber);
            //tiles[randomPoint.x][randomPoint.y].setNumber(randomNumber);
            registerTurn(direction, randomNumber, randomPoint);
        }

        updateBoard();
        checkGameOver();
    }

    private void registerTurn(Direction direction, int randomNumber, DuoTuple<Integer,Integer> coordinates) {
        turns.add(new Turn(randomNumber, coordinates, commandManager.getCommand(direction)));
    }

    private void checkGameOver() {
/*
        Tile [][] newTiles = new Tile[board.getBoardHeight()][board.getBoardWidth()];
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                newTiles[i][j] = new Tile(board.getTileValue(i,j));
                //newTiles[i][j] = new Tile(tiles[i][j].getNumber());
            }
        }
        */

        Board newBoard = new Board(this);
        newBoard.initFromBoard(board);


        boolean movePossible = false;
        for (Direction direction : Direction.values()) {
            if (commandManager.computeMove(direction, newBoard)) {
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


}
