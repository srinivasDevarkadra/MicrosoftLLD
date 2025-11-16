package org.example.snakeAndLadder;

import org.example.snakeAndLadder.Service.BoardService;
import org.example.snakeAndLadder.Service.DiceService;
import org.example.snakeAndLadder.model.Board;
import org.example.snakeAndLadder.model.Player;



public class ThreadedGame {
    private Board board;
    private DiceService diceService;
    private BoardService boardService;
    private Player player1;
    private Player player2;
    private Player currentPlayerTurn;
    private boolean isGameWon;



    public ThreadedGame(int boardSize, String player1Name, String player2Name) {
        this.board = GameInitializer.initializeBoard(boardSize);
        this.boardService = new BoardService();
        this.diceService = new DiceService();
        this.player1 = new Player(player1Name, 0);
        this.player2 = new Player(player2Name, 0);
        this.currentPlayerTurn = player1;
        this.isGameWon = false;
    }

    public void startThreadedGame() {
        Thread player1Thread = new Thread(() -> startTask(player1), "player1");
        Thread player2Thread = new Thread(() -> startTask(player2), "player2");
        
        player1Thread.start();
        player2Thread.start();
        
        try {
            player1Thread.join();
            player2Thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Game interrupted!");
        }
    }


    public void startTask(Player currentPlayer) {
        while (!isGameWon) {
            synchronized (this) { // synchronized is for at any given time only one thread executes
                //below condition give fair chances like alternative "if 1 completed it should not execute again 2 should go"
                while (currentPlayerTurn != currentPlayer && !isGameWon) {
                    try {
                        System.out.println("Player " + currentPlayer.getName() + " waiting for their turn...");
                        this.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Player " + currentPlayer.getName() + " thread was interrupted!");
                        return;
                    }
                }

                if (isGameWon) {
                    break;
                }

                System.out.println("\n" + currentPlayer.getName() + "'s turn!");

                int diceValue = diceService.roll();
                System.out.println(currentPlayer.getName() + " rolled a " + diceValue);

                int currentPosition = currentPlayer.getCurrentPosition();
                int newPosition = currentPosition + diceValue;

                if (newPosition == board.getSize() - 1) {
                    System.out.println("\n🎉 " + currentPlayer.getName() + " is the winner! 🎉");
                    isGameWon = true;
                    this.notifyAll();
                    break;
                } else if (newPosition < board.getSize()) {
                    int finalPosition = boardService.moveOnBoard(board, newPosition, currentPlayer);
                    currentPlayer.setCurrentPosition(finalPosition);

                    if (finalPosition != newPosition) {
                        // Already printed the movement in moveOnBoard for snakes/ladders
                    } else {
                        System.out.println(currentPlayer.getName() + " moved to position " + finalPosition);
                    }
                } else {
                    System.out.println(currentPlayer.getName() + " needs exactly " +
                            (board.getSize() - 1 - currentPosition) + " to win!");
                }

                currentPlayerTurn = (currentPlayer == player1) ? player2 : player1;
                this.notifyAll();
            }
        }
    }








    public static void main(String[] args) {
        // Example: Create a threaded game with 2 players
        ThreadedGame game = new ThreadedGame(100, "Alice", "Bob");
        game.startThreadedGame();
    }
}
