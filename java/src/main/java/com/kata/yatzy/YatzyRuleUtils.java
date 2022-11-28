package com.kata.yatzy;

import com.kata.yatzy.model.YatzyRuleEnum;

import java.util.*;

/**
 * Utils to score a given roll
 */
public class YatzyRuleUtils {

    private YatzyRuleUtils() {
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
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return getSumOfDiceWithSameOccurence(counts, 5) != 0 ? 50 : 0;
    }

    /**
     * It scores the sum of the dice that reads one, two, three, four, five or six, respectively.
     *
     * @param categorySum type of sided faces
     * @param dice        five six-sided dice
     * @return scores
     */
    public static int sum(YatzyRuleEnum categorySum, int... dice) {
        return switch (categorySum) {
            case ONES -> sumOfDice(1, dice);
            case TWOS -> sumOfDice(2, dice);
            case THREES -> sumOfDice(3, dice);
            case FOURS -> sumOfDice(4, dice);
            case FIVES -> sumOfDice(5, dice);
            case SIXES -> sumOfDice(6, dice);
            default -> 0;
        };
    }

    /**
     * It scores the sum of the two highest matching dice.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int onePair(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return getSumOfDiceWithSameOccurence(counts, 2);
    }

    /**
     * If there are two pairs of dice with the same number, it scores the sum of these dice.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int twoPairs(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        int resultOnePair = getSumOfDiceWithSameOccurence(counts, 2);
        if (resultOnePair != 0) {
            counts.remove(resultOnePair / 2);
            int resultTwoPair = getSumOfDiceWithSameOccurence(counts, 2);
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
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return getSumOfDiceWithSameOccurence(counts, 4);
    }

    /**
     * If there are three dice with the same number, it scores the sum of these dice.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int threeOfAKind(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return getSumOfDiceWithSameOccurence(counts, 3);
    }

    /**
     * When placed on “small straight”, if the dice read 1,2,3,4,5, it scores 15
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int smallStraight(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return hasTheSameOccurrenceWithoutIndexStartFace(0, counts) ? 15 : 0;
    }

    /**
     * When placed on “large straight”, if the dice read 2,3,4,5,6, it scores 20
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int largeStraight(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        return hasTheSameOccurrenceWithoutIndexStartFace(1, counts) ? 20 : 0;
    }

    /**
     * If the dice are two of a kind and three of a kind, it scores the sum of all the dice.
     *
     * @param dice five six-sided dice
     * @return scores
     */
    public static int fullHouse(int... dice) {
        Map<Integer, Integer> counts = getCountsOfDices(dice);
        if (getSumOfDiceWithSameOccurence(counts, 5) == 0) {
            int sumOfThreeSameDice = getSumOfDiceWithSameOccurence(counts, 3);
            if (sumOfThreeSameDice != 0) {
                int sumOfTwoSameDice = getSumOfDiceWithSameOccurence(counts, 2);
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
     * Count the number of occurence for rolling dice
     *
     * @param dice any number of dice
     * @return HashMap with occurrence for each face of dice
     */
    private static Map<Integer, Integer> getCountsOfDices(int... dice) {
        Map<Integer, Integer> countsOfDices = new HashMap<>();
        for (int oneDice : dice) {
            countsOfDices.put(oneDice, (countsOfDices.get(oneDice) != null ? countsOfDices.get(oneDice) + 1 : 1));
        }
        return countsOfDices;
    }

    /**
     * Find which face matches the number of occurrence
     *
     * @param counts     HashMap with occurrence for each face of dice
     * @param occurrence the number of occurrence to find
     * @return the highest repetition face
     */
    private static int getSumOfDiceWithSameOccurence(Map<Integer, Integer> counts, int occurrence) {
        return occurrence * (counts.entrySet().stream().filter(e -> e.getValue() >= occurrence).map(Map.Entry::getKey).max(Comparator.naturalOrder()).orElse(0));
    }

    /**
     * Search if there are 5 consecutive faces of dice
     *
     * @param indexStart index to start the search
     * @param counts     HashMap with occurrence for each face of dice
     * @return true if they are 5 consecutive faces of dice
     */
    private static boolean hasTheSameOccurrenceWithoutIndexStartFace(int indexStart, Map<Integer, Integer> counts) {
        Optional<Map.Entry<Integer, Integer>> maxCounts = counts.entrySet().stream().max(Map.Entry.comparingByValue());
        return maxCounts.filter(e -> e.getValue() == 1 && counts.get(indexStart) == null).isPresent();
    }
}



