package com.athens.athens2048.core;


public class GameController {
    private Game game;

    GameController(GameObserver gameObserver) {
        game = new Game();
        game.addGameObserver(gameObserver);
    }

    public void startGame() {
        game.reset();
    }

    public void backToFirstStage() {
        game.backToFirstStage();
    }

    public void backToLastMove() {
        game.backToLastMove();
    }

    public void undoStep() {
        game.undoStep();
    }

    public void redoStep() {
        game.redoStep();
    }

    public void move(Direction direction) {
        game.onKeyPressed(direction);
    }
}
