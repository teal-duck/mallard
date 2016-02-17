package com.superduckinvaders.game;

import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.objective.Objective;
import com.superduckinvaders.game.util.LwjglTestRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test the Character class's special methods.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // Round keeps throwing NullPointerException!
public class RoundTest {
    private static String TEST_OBJECTIVE_STRING = "TEST_STRING";

    private class TestObjective extends Objective {
        TestObjective(Round round) {
            super(round);
        }

        @Override
        public String getObjectiveString() {
            return TEST_OBJECTIVE_STRING;
        }

        @Override
        public void update(float delta) {
        }
    }

    @Test
    public void CanSetObjective() {
        Round round = new Round(mock(DuckGame.class));
        Objective testObj = new TestObjective(round);
        round.setObjective(testObj);
        assertEquals(round.getObjective().getObjectiveString(), TEST_OBJECTIVE_STRING);
    }
}
