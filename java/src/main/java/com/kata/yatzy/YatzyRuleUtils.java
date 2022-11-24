package com.kata.yatzy;

import com.kata.yatzy.model.YatzyRuleEnum;

import java.util.Arrays;

/**
 * Utils to score a given roll
 */
public class YatzyRuleUtils {

    private YatzyRuleUtils() {
    }

    /**
     * @param givenCategory the rule to score
     * @param dice          five six-sided dice
     * @return score
     */
    public static int scoreRoll(YatzyRuleEnum givenCategory, int... dice) {
        int score = 0;
        switch (givenCategory) {
            case ONES:
            case TWOS:
            case THREES:
            case FOURS:
            case FIVES:
            case SIXES:
                score = sum(givenCategory, dice);
                break;
            case CHANCE:
                score = chance(dice);
                break;
            case PAIR:
                score = onePair(dice);
                break;
            case TWO_PAIR:
                score = twoPairs(dice);
                break;
            case YATZY:
                score = yatzy(dice);
                break;
            case THREE_OF_KIND:
                score = threeOfAKind(dice);
                break;
            case FOUR_OF_KIND:
                score = fourOfAKind(dice);
                break;
            case SMALL_STRAIGHT:
                score = smallStraight(dice);
                break;
            case LARGE_STRAIGHT:
                score = largeStraight(dice);
                break;
            case FULL_HOUSE:
                score = fullHouse(dice);
                break;
        }
        return score;
    }

    /**
     * Scores the sum of all dice, no matter what they read.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int chance(int... dice) {
        return Arrays.stream(dice).sum();
    }

    /**
     * If all dice have the same number, it scores 50 points.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int yatzy(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 5) != 0 ? 50 : 0;
    }

    /**
     * It scores the sum of the dice that reads one, two, three, four, five or six, respectively.
     *
     * @param categorySum type of sided faces
     * @param dice        five six-sided dice
     * @return scores
     */
    public static int sum(YatzyRuleEnum categorySum, int... dice) {
        int sum = 0;
        switch (categorySum) {
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
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int onePair(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 2);
    }

    /**
     * If there are two pairs of dice with the same number, it scores the sum of these dice.
     *
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
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int fourOfAKind(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 4);
    }

    /**
     * If there are three dice with the same number, it scores the sum of these dice.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int threeOfAKind(int... dice) {
        int[] counts = getCounts(dice);
        return getSumOfSameDice(counts, 3);
    }

    /**
     * When placed on “small straight”, if the dice read 1,2,3,4,5, it scores 15
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int smallStraight(int... dice) {
        int[] counts = getCounts(dice);
        return hasTheSameOccurrence(0, counts) ? 15 : 0;
    }

    /**
     * When placed on “large straight”, if the dice read 2,3,4,5,6, it scores 20
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int largeStraight(int... dice) {
        int[] counts = getCounts(dice);
        return hasTheSameOccurrence(1, counts) ? 20 : 0;
    }

    /**
     * If the dice are two of a kind and three of a kind, it scores the sum of all the dice.
     *
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
     *
     * @param face the face selected
     * @param dice any number of dice
     * @return sum of selected face of dice
     */
    private static int sumOfDice(int face, int... dice) {
        return Arrays.stream(dice).filter(e -> e == face).sum();
    }

    /**
     * Count the number of occurrence for each face of dice
     *
     * @param dice many six-sided dice
     * @return an array with occurrence for each face of dice
     */
    private static int[] getCounts(int... dice) {
        int[] counts = new int[6];
        for (int oneDice : dice) {
            if (oneDice > 6) {
                throw new IllegalArgumentException("It's not a valid dice");
            }
            counts[oneDice - 1]++;
        }
        return counts;
    }

    /**
     * Find which face matches the number of occurrence
     *
     * @param counts     an array with occurrence for each face of dice
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

    /**
     * Search if there are 5 consecutive faces of dice
     *
     * @param indexStart index to start the search
     * @param counts     an array with occurrence for each face of dice
     * @return true if they are 5 consecutive faces of dice
     */
    private static boolean hasTheSameOccurrence(int indexStart, int[] counts) {
        int indexEnd = indexStart + 4 > counts.length ? counts.length : indexStart + 4;
        for (int i = indexStart; i < indexEnd; i++) {
            if (counts[i] != 1) {
                return false;
            }
        }
        return true;
    }
}



