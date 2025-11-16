package org.example.snakeAndLadder.model;

public class Player {
    private final String name;
    private int currentPosition;

    public Player(String name, int currentPosition) {
        this.currentPosition = currentPosition;
        this.name = name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int newPosition) {
        this.currentPosition = newPosition;
    }

    public String getName() {
        return this.name;
    }
}
