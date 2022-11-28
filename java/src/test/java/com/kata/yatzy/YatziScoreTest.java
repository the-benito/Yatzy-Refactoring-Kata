package com.kata.yatzy;

import com.kata.yatzy.model.DiceRoll;
import com.kata.yatzy.model.YatzyRuleEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YatziScoreTest {
    @Test
    public void when_given_category_should_score() {
        assertEquals(20, YatzyScore.scoreRoll(YatzyRuleEnum.LARGE_STRAIGHT, new DiceRoll(6, 2, 3, 4, 5).dice()));
        assertEquals(18, YatzyScore.scoreRoll(YatzyRuleEnum.FULL_HOUSE, new DiceRoll(6, 2, 2, 2, 6).dice()));
        assertEquals(0, YatzyScore.scoreRoll(YatzyRuleEnum.PAIR, new DiceRoll(5, 4, 3, 2, 1).dice()));
    }
}
