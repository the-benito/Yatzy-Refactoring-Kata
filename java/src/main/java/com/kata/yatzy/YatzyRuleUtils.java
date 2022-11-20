package com.kata.yatzy;

import com.kata.yatzy.model.YatzyRuleEnum;

import java.util.Arrays;

public class YatzyRuleUtils {

    private YatzyRuleUtils() {
    }

    /**
     * Scores the sum of all dice, no matter what they read.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int chance(int... dice) {
        return Arrays.stream(dice).sum();
    }

    /**
     * If all dice have the same number, it scores 50 points.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int yatzy(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 5) != 0 ? 50 : 0;
    }

    /**
     * It scores the sum of the dice that reads one, two, three, four, five or six, respectively.
     * @param ruleSum type of sided faces
     * @param dice five six-sided dice
     * @return scores
     */
    public static int sum(YatzyRuleEnum ruleSum, int... dice) {
        int sum = 0;
        switch (ruleSum) {
            case ONES -> sum = sumOfDice(1, dice);
            case TWOS -> sum = sumOfDice(2, dice);
            case THREES -> sum = sumOfDice(3, dice);
            case FOURS -> sum = sumOfDice(4, dice);
            case FIVES -> sum = sumOfDice(5, dice);
            case SIXES -> sum = sumOfDice(6, dice);
            default -> {
                return 0;
            }
        }
        return sum;
    }

    /**
     * It scores the sum of the two highest matching dice.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int onePair(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 2);
    }

    /**
     * If there are two pairs of dice with the same number, it scores the sum of these dice.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int twoPairs(int... dice) {
        int[] counts = getCounts(dice);
        int resultOnePair = getSumOfSameDice(counts, 2);
        if (resultOnePair != 0) {
            counts[(resultOnePair / 2) - 1] = 0;
            int resultTwoPair = getSumOfSameDice(counts, 2);
            return resultTwoPair != 0 ? resultOnePair + resultTwoPair : 0;
        }
        return 0;
    }

    /**
     * If there are four dice with the same number, it scores the sum of these dice.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int fourOfAKind(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 4);
    }

    /**
     * If there are three dice with the same number, it scores the sum of these dice.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int threeOfAKind(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 3);
    }

    /**
     * When placed on “small straight”, if the dice read 1,2,3,4,5, it scores 15
     * @param dice five six-sided dice
     * @return scores
     */
    public static int smallStraight(int... dice) {
        return chance(dice) == 15 ? 15 : 0;
    }

    /**
     * When placed on “large straight”, if the dice read 2,3,4,5,6, it scores 20
     * @param dice five six-sided dice
     * @return scores
     */
    public static int largeStraight(int... dice) {
        return chance(dice) == 20 ? 20 : 0;
    }

    /**
     * If the dice are two of a kind and three of a kind, it scores the sum of all the dice.
     * @param dice five six-sided dice
     * @return scores
     */
    public static int fullHouse(int... dice) {
        int[] counts = getCounts(dice);
        if (getSumOfSameDice(counts, 5) == 0) {
            int sumOfThreeSameDice = getSumOfSameDice(counts, 3);
            if (sumOfThreeSameDice != 0) {
                int sumOfTwoSameDice = getSumOfSameDice(counts, 2);
                return sumOfTwoSameDice != 0 ? sumOfTwoSameDice + sumOfThreeSameDice : 0;
            }
        }
        return 0;
    }

    /**
     * Calculate the sum of all selected face of dice
     * @param face the face selected
     * @param dice any number of dice
     * @return sum of selected face of dice
     */
    private static int sumOfDice(int face, int... dice) {
        return Arrays.stream(dice).filter(e -> e == face).sum();
    }

    /**
     * Count the number of occurrence for each face of dice
     * @param dice five six-sided dice
     * @return an array with occurrence for each face of dice
     */
    private static int[] getCounts(int... dice) {
        if (dice.length != 5) {
            throw new IllegalArgumentException("Can't role more or less than 5 dice");
        }
        int[] counts = new int[6];
        for (int die : dice) {
            counts[die - 1]++;
        }
        return counts;
    }

    /**
     *  Find which face matches the number of occurrence
     * @param counts an array with occurrence for each face of dice
     * @param occurrence the number of occurrence to find
     * @return the highest repetition face
     */
    private static int getSumOfSameDice(int[] counts, int occurrence) {
        for (int i = counts.length - 1; i >= 0; i--) {
            if (counts[i] >= occurrence)
                return (i + 1) * occurrence;
        }
        return 0;
    }
}


