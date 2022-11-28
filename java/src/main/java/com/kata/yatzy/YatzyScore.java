package com.kata.yatzy;

import com.kata.yatzy.model.YatzyRuleEnum;

import static com.kata.yatzy.YatzyRuleUtils.*;

public class YatzyScore {
    private YatzyScore() {

    }

    /**
     * @param givenCategory the rule to score
     * @param dice          five six-sided dice
     * @return score
     */
    public static int scoreRoll(YatzyRuleEnum givenCategory, int... dice) {
        return switch (givenCategory) {
            case ONES, TWOS, THREES, FOURS, FIVES, SIXES -> sum(givenCategory, dice);
            case CHANCE -> chance(dice);
            case PAIR -> onePair(dice);
            case TWO_PAIR -> twoPairs(dice);
            case YATZY -> yatzy(dice);
            case THREE_OF_KIND -> threeOfAKind(dice);
            case FOUR_OF_KIND -> fourOfAKind(dice);
            case SMALL_STRAIGHT -> smallStraight(dice);
            case LARGE_STRAIGHT -> largeStraight(dice);
            case FULL_HOUSE -> fullHouse(dice);
        };
    }
}
