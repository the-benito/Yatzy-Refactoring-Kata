package com.kata.yatzy.model;

import java.util.Arrays;

public record DiceRoll(int... dice) {
    public DiceRoll {
        if (dice.length != 5) {
            throw new IllegalArgumentException("Can't role more or less than 5 dice");
        }
        if (!Arrays.stream(dice).allMatch(d -> d > 0 && d <= 6))
            throw new IllegalArgumentException("It's not a valid dice");
    }
}
