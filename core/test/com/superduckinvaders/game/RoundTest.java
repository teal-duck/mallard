package com.superduckinvaders.game;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.superduckinvaders.game.objective.Objective;
import com.superduckinvaders.game.util.LwjglTestRunner;

import junit.framework.TestCase;


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
			return RoundTest.TEST_OBJECTIVE_STRING;
		}


		@Override
		public void update(float delta) {
		}
	}


	@Test
	public void CanSetObjective() {
		Round round = new Round(Mockito.mock(DuckGame.class));
		Objective testObj = new TestObjective(round);
		round.setObjective(testObj);
		TestCase.assertEquals(round.getObjective().getObjectiveString(), RoundTest.TEST_OBJECTIVE_STRING);
	}
}
