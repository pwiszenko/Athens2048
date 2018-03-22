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

    private ArrayList<Turn> turns;
    CommandManager commandManager = new CommandManager();
    Board board;

    // For the replay functionality
    private int turnIndex = 0;
    private boolean undoing = false;
    private boolean atFirstStage = false;

    Game() {
        board = new Board(this, 2);
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
        resetToFirstStage();
        // Draw the Board
        updateBoard();
    }

    private void initPlayback(){
        if(turns == null)
            turns = new ArrayList<>();
        else
            removeEnd(turns, 0);
        turnIndex = 0;
        undoing = false;

        board.initFirstStage(HEIGHT, WIDTH, true);
    }

    // Function the undo the last done move
    public void undoStep() {
        setScore(0);

        resetToFirstStage();
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

        resetToFirstStage();
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
        }
        updateBoard();
        return true;
    }

    // Function to call to restart from the beggining of the playback
    public void backToFirstStage() {
        setScore(0);
        resetToFirstStage();
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
        turnIndex++;
        return true;
    }


    // Function to reset the Board's tiles to the starting state
    // It actually copies values from 'firstTiles' array to 'tiles' array
    private void resetToFirstStage() {
        board.resetToFirstStage(HEIGHT, WIDTH);
        atFirstStage = true;
    }


    private void updateBoard() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                for (GameObserver observer : gameObservers)
                    observer.updateTile(i, j, board.getTileValue(i, j));
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

        // If we change the very first move from history
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

        if (randomPoint != null) {
            int randomNumber = board.pickRandomTileValue();
            board.setTileValue(randomPoint, randomNumber);
            registerTurn(direction, randomNumber, randomPoint);
        }

        updateBoard();
        checkGameOver();
    }

    private void registerTurn(Direction direction, int randomNumber, DuoTuple<Integer,Integer> coordinates) {
        turns.add(new Turn(randomNumber, coordinates, commandManager.getCommand(direction)));
    }

    private void checkGameOver() {
        Board newBoard = new Board(this, 0);
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
