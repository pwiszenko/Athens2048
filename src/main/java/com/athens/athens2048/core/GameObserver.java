package com.athens.athens2048.core;


public interface GameObserver {
    void scoreUpdated(int score);
    void updateTile(int x, int y, int value);
    void gameOver(int bestScore);
}
