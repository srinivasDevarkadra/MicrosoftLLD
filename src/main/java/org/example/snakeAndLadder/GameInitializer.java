package org.example.snakeAndLadder;

import org.example.snakeAndLadder.model.Board;
import org.example.snakeAndLadder.model.Snake;
import org.example.snakeAndLadder.model.Ladder;

import java.util.Random;

public class GameInitializer {
    
    public static Board initializeBoard(int size) {
        Board board = new Board(size);
        
        // Add some snakes (start > end)
        addSnakes(board, size);
        
        // Add some ladders (start < end)
        addLadders(board, size);
        
        return board;
    }
    
    private static void addSnakes(Board board, int boardSize) {
        // Add some predefined snakes (you can customize these)
        addSnake(board, 16, 6);
        addSnake(board, 47, 26);
        addSnake(board, 49, 11);
        addSnake(board, 56, 53);
        addSnake(board, 62, 19);
        addSnake(board, 64, 60);
        addSnake(board, 87, 24);
        addSnake(board, 93, 73);
        addSnake(board, 95, 75);
        addSnake(board, 98, 78);
    }
    
    private static void addLadders(Board board, int boardSize) {
        // Add some predefined ladders (you can customize these)
        addLadder(board, 1, 38);
        addLadder(board, 4, 14);
        addLadder(board, 9, 31);
        addLadder(board, 21, 42);
        addLadder(board, 28, 84);
        addLadder(board, 36, 44);
        addLadder(board, 51, 67);
        addLadder(board, 71, 91);
        addLadder(board, 80, 100);
    }
    
    private static void addSnake(Board board, int start, int end) {
        if (start <= end) {
            throw new IllegalArgumentException("Snake start must be greater than end");
        }
        board.addSnake(new Snake(start, end));
    }
    
    private static void addLadder(Board board, int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("Ladder start must be less than end");
        }
        board.addLadder(new Ladder(start, end));
    }
}
