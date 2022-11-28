package com.kata.yatzy;

import com.kata.yatzy.model.DiceRoll;
import com.kata.yatzy.model.YatzyRuleEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YatzyRuleUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void more_dice_roll_should_exception() {
        new DiceRoll(1, 2, 3, 4, 5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void more_face_of_one_dice_should_exception() {
        new DiceRoll(1, 2, 7, 4, 5);
    }

    @Test
    public void chance_should_scores_sum_of_all_dice() {
        assertEquals(15, YatzyRuleUtils.chance(new DiceRoll(2, 3, 4, 5, 1).dice()));
        assertEquals(16, YatzyRuleUtils.chance(new DiceRoll(3, 3, 4, 5, 1).dice()));
    }

    @Test
    public void yatzy_should_scores_50() {
        assertEquals(50, YatzyRuleUtils.yatzy(new DiceRoll(4, 4, 4, 4, 4).dice()));
        assertEquals(50, YatzyRuleUtils.yatzy(new DiceRoll(6, 6, 6, 6, 6).dice()));
        assertEquals(0, YatzyRuleUtils.yatzy(new DiceRoll(6, 6, 6, 6, 3).dice()));
    }

    @Test
    public void ones_should_scores_sum_dice_with_one() {
        assertEquals(1, YatzyRuleUtils.sum(YatzyRuleEnum.ONES, new DiceRoll(1, 2, 3, 4, 5).dice()));
        assertEquals(2, YatzyRuleUtils.sum(YatzyRuleEnum.ONES, new DiceRoll(1, 2, 1, 4, 5).dice()));
        assertEquals(0, YatzyRuleUtils.sum(YatzyRuleEnum.ONES, new DiceRoll(6, 2, 2, 4, 5).dice()));
        assertEquals(4, YatzyRuleUtils.sum(YatzyRuleEnum.ONES, new DiceRoll(1, 2, 1, 1, 1).dice()));
    }

    @Test
    public void twos_should_scores_sum_dice_with_two() {
        assertEquals(4, YatzyRuleUtils.sum(YatzyRuleEnum.TWOS, new DiceRoll(1, 2, 3, 2, 6).dice()));
        assertEquals(10, YatzyRuleUtils.sum(YatzyRuleEnum.TWOS, new DiceRoll(2, 2, 2, 2, 2).dice()));
    }

    @Test
    public void threes_should_scores_sum_dice_with_three() {
        assertEquals(6, YatzyRuleUtils.sum(YatzyRuleEnum.THREES, new DiceRoll(1, 2, 3, 2, 3).dice()));
        assertEquals(12, YatzyRuleUtils.sum(YatzyRuleEnum.THREES, new DiceRoll(2, 3, 3, 3, 3).dice()));
    }

    @Test
    public void fours_should_scores_sum_dice_with_four() {
        assertEquals(12, YatzyRuleUtils.sum(YatzyRuleEnum.FOURS, new DiceRoll(4, 4, 4, 5, 5).dice()));
        assertEquals(8, YatzyRuleUtils.sum(YatzyRuleEnum.FOURS, new DiceRoll(4, 4, 5, 5, 5).dice()));
        assertEquals(4, YatzyRuleUtils.sum(YatzyRuleEnum.FOURS, new DiceRoll(4, 5, 5, 5, 5).dice()));
    }

    @Test
    public void fives_should_scores_sum_dice_with_five() {
        assertEquals(10, YatzyRuleUtils.sum(YatzyRuleEnum.FIVES, new DiceRoll(4, 4, 4, 5, 5).dice()));
        assertEquals(15, YatzyRuleUtils.sum(YatzyRuleEnum.FIVES, new DiceRoll(4, 4, 5, 5, 5).dice()));
        assertEquals(20, YatzyRuleUtils.sum(YatzyRuleEnum.FIVES, new DiceRoll(4, 5, 5, 5, 5).dice()));
    }

    @Test
    public void sixes_should_scores_sum_dice_with_six() {
        assertEquals(0, YatzyRuleUtils.sum(YatzyRuleEnum.SIXES, new DiceRoll(4, 4, 4, 5, 5).dice()));
        assertEquals(6, YatzyRuleUtils.sum(YatzyRuleEnum.SIXES, new DiceRoll(4, 4, 6, 5, 5).dice()));
        assertEquals(18, YatzyRuleUtils.sum(YatzyRuleEnum.SIXES, new DiceRoll(6, 5, 6, 6, 5).dice()));
    }

    @Test
    public void one_pair_should_scores_sum() {
        assertEquals(0, YatzyRuleUtils.onePair(new DiceRoll(5, 4, 3, 2, 1).dice()));
        assertEquals(6, YatzyRuleUtils.onePair(new DiceRoll(3, 4, 3, 5, 6).dice()));
        assertEquals(10, YatzyRuleUtils.onePair(new DiceRoll(5, 3, 3, 3, 5).dice()));
        assertEquals(12, YatzyRuleUtils.onePair(new DiceRoll(5, 3, 6, 6, 5).dice()));
        assertEquals(8, YatzyRuleUtils.onePair(new DiceRoll(3, 3, 3, 4, 4).dice()));
        assertEquals(6, YatzyRuleUtils.onePair(new DiceRoll(3, 3, 3, 3, 1).dice()));
    }

    @Test
    public void two_pairs_should_scores_sum() {
        assertEquals(16, YatzyRuleUtils.twoPairs(new DiceRoll(3, 3, 5, 4, 5).dice()));
        assertEquals(16, YatzyRuleUtils.twoPairs(new DiceRoll(3, 3, 5, 5, 5).dice()));
    }

    @Test
    public void three_of_a_kind_should_scores_sum() {
        assertEquals(9, YatzyRuleUtils.threeOfAKind(new DiceRoll(3, 3, 3, 4, 5).dice()));
        assertEquals(15, YatzyRuleUtils.threeOfAKind(new DiceRoll(5, 3, 5, 4, 5).dice()));
        assertEquals(9, YatzyRuleUtils.threeOfAKind(new DiceRoll(3, 3, 3, 3, 5).dice()));
        assertEquals(9, YatzyRuleUtils.threeOfAKind(new DiceRoll(3, 3, 3, 3, 3).dice()));
    }

    @Test
    public void four_of_a_kind_should_scores_sum() {
        assertEquals(12, YatzyRuleUtils.fourOfAKind(new DiceRoll(3, 3, 3, 3, 5).dice()));
        assertEquals(20, YatzyRuleUtils.fourOfAKind(new DiceRoll(5, 5, 5, 4, 5).dice()));
    }

    @Test
    public void small_straight_should_scores_15() {
        assertEquals(15, YatzyRuleUtils.smallStraight(new DiceRoll(1, 2, 3, 4, 5).dice()));
        assertEquals(15, YatzyRuleUtils.smallStraight(new DiceRoll(2, 3, 4, 5, 1).dice()));
        assertEquals(0, YatzyRuleUtils.smallStraight(new DiceRoll(1, 2, 2, 4, 5).dice()));
        assertEquals(0, YatzyRuleUtils.smallStraight(new DiceRoll(1, 2, 2, 5, 5).dice()));
    }

    @Test
    public void large_straight_should_scores_20() {
        assertEquals(20, YatzyRuleUtils.largeStraight(new DiceRoll(6, 2, 3, 4, 5).dice()));
        assertEquals(20, YatzyRuleUtils.largeStraight(new DiceRoll(2, 3, 4, 5, 6).dice()));
        assertEquals(0, YatzyRuleUtils.largeStraight(new DiceRoll(1, 2, 2, 4, 5).dice()));
        assertEquals(0, YatzyRuleUtils.largeStraight(new DiceRoll(2, 2, 5, 5, 6).dice()));
    }

    @Test
    public void full_house_should_scores_sum() {
        assertEquals(18, YatzyRuleUtils.fullHouse(new DiceRoll(6, 2, 2, 2, 6).dice()));
        assertEquals(0, YatzyRuleUtils.fullHouse(new DiceRoll(2, 3, 4, 5, 6).dice()));
    }
}
