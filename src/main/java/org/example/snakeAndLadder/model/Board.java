package org.example.snakeAndLadder.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    int size;
    Map<Integer, Obstacle> snakesMap;
    Map<Integer, Obstacle> ladderMap;

    public  Board(int size) {
        this.size = size;
        this.snakesMap = new HashMap<>();
        this.ladderMap = new HashMap<>();
    }

    public void addSnake(Obstacle snake) {
        int start = snake.getStart();
        snakesMap.put(start, snake);
    }

    public void addLadder(Obstacle ladder) {
        int end = ladder.getEnd();
        ladderMap.put(end, ladder);
    }

    public int getSize() {
        return size;
    }



    public Map<Integer, Obstacle> getLadderMap() {
        return ladderMap;
    }

    public Map<Integer, Obstacle> getSnakesMap() {
        return snakesMap;
    }
}
