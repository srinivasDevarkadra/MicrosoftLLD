package org.example.snakeAndLadder.model;

public class Obstacle {
    int start;
    int end;

    public Obstacle(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart() {
        this.start = start;
    }

    public void setEnd() {
        this.end = end;
    }
}
