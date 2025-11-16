package org.example.snakeAndLadder.Service;

import java.util.Random;

public class DiceService {
    private final Random random;
    private static final int DICE_SIDES = 6;

    public DiceService() {
        this.random = new Random();
    }

    /**
     * Rolls a dice and returns a value between 1 and 6 (inclusive).
     * @return a random dice roll value
     */
    public int roll() {
        return random.nextInt(DICE_SIDES) + 1;
    }
}
