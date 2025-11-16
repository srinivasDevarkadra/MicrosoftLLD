package org.example.snakeAndLadder.Service;

import org.example.snakeAndLadder.model.Player;
import org.example.snakeAndLadder.model.Board;
import org.example.snakeAndLadder.model.Obstacle;

public class BoardService {
    
    /**
     * Processes the move on the board, handling snakes and ladders
     * @param board The game board
     * @param newPosition The position the player wants to move to
     * @param player The player making the move
     * @return The final position after considering snakes and ladders
     */
    public int moveOnBoard(Board board, int newPosition, Player player) {
        if (!isMoveValid(board.getSize(), newPosition)) {
            return player.getCurrentPosition(); // Can't move beyond board
        }
        
        int finalPosition = newPosition;
        
        // Check for snake at new position
        if (hasSnake(board, newPosition)) {
            Obstacle snake = board.getSnakesMap().get(newPosition);
            finalPosition = snake.getEnd(); // Move to the end of the snake
            System.out.println("🐍 Oops! Snake bite! Sliding down to " + finalPosition);
        } 
        // Check for ladder at new position (only if not already on a snake)
        else if (hasLadder(board, newPosition)) {
            Obstacle ladder = board.getLadderMap().get(newPosition);
            finalPosition = ladder.getEnd(); // Climb to the end of the ladder
            System.out.println("🪜 Yay! Found a ladder! Climbing up to " + finalPosition);
        }
        
        return finalPosition;
    }

    /**
     * Checks if a move to the given position is valid
     */
    public boolean isMoveValid(int boardSize, int newPosition) {
        return newPosition < boardSize;
    }

    /**
     * Checks if there's a snake at the given position
     */
    public boolean hasSnake(Board board, int position) {
        return board.getSnakesMap().containsKey(position);
    }

    /**
     * Checks if there's a ladder at the given position
     */
    public boolean hasLadder(Board board, int position) {
        return board.getLadderMap().containsKey(position);
    }
}
