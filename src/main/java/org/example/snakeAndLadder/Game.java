package org.example.snakeAndLadder;

import org.example.snakeAndLadder.Service.BoardService;
import org.example.snakeAndLadder.Service.DiceService;
import org.example.snakeAndLadder.model.Board;
import org.example.snakeAndLadder.model.Player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Queue<Player> playersQueue;
    private final DiceService diceService;
    private final BoardService boardService;
    private final Scanner scanner;

    public Game(int boardSize, int numPlayers) {
        // Initialize the game board with snakes and ladders
        this.board = GameInitializer.initializeBoard(boardSize);
        this.playersQueue = new LinkedList<>();
        this.diceService = new DiceService();
        this.boardService = new BoardService();
        this.scanner = new Scanner(System.in);
        initializePlayers(numPlayers);
        
        System.out.println("\nGame initialized with " + board.getSnakesMap().size() + " snakes and " + 
                          board.getLadderMap().size() + " ladders!");
    }

    private void initializePlayers(int numPlayers) {
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine();
            playersQueue.add(new Player(playerName, 0));
        }
    }

    public void start() {
        System.out.println("\nStarting the game!");
        
        while (playersQueue.size() > 1) {
            Player currentPlayer = playersQueue.poll();
            System.out.println("\n" + currentPlayer.getName() + "'s turn. Press Enter to roll the dice...");
            scanner.nextLine();
            
            int diceValue = diceService.roll();
            System.out.println(currentPlayer.getName() + " rolled a " + diceValue);
            
            int currentPosition = currentPlayer.getCurrentPosition();
            int newPosition = currentPosition + diceValue;
            
            if (newPosition == board.getSize() - 1) {
                System.out.println("\n🎉 " + currentPlayer.getName() + " is the winner! 🎉");
                return;
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
            
            playersQueue.offer(currentPlayer);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Snake and Ladder Game!");
        System.out.print("Enter the size of the board: ");
        int boardSize = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Game game = new Game(boardSize, numPlayers);
        game.start();
        
        scanner.close();
    }
}
