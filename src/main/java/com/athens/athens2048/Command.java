package com.athens.athens2048;

public interface Command {
    public boolean execute();
    public void undo();
}
