package com.superduckinvaders.game.objective;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.util.LwjglTestRunner;


/**
 * Test if the objectives actually work. If the parent works, the children have a pretty good chance of working as well.
 */
@RunWith(LwjglTestRunner.class)
public class ObjectiveTest {
	private static String TEST_OBJECTIVE_STRING = "TEST_OBJECTIVE_STRING";


	private class MyObjective extends Objective {
		boolean updated = false;


		MyObjective(Round round) {
			super(round);
		}


		@Override
		public String getObjectiveString() {
			return ObjectiveTest.TEST_OBJECTIVE_STRING;
		}


		@Override
		public void update(float delta) {
			updated = true;
		}
	}


	private Round round;


	@Before
	public void setUp() {
		round = Mockito.mock(Round.class);
	}


	@Test
	public void HasCorrectObjectiveString() {
		MyObjective obj = new MyObjective(round);
		Assert.assertEquals(obj.getObjectiveString(), ObjectiveTest.TEST_OBJECTIVE_STRING);
	}


	@Test
	public void IsUpdated() {
		MyObjective obj = new MyObjective(round);
		obj.update(0f);
		Assert.assertTrue(obj.updated);
	}


	@Test
	public void IsOngoingByDefault() {
		MyObjective obj = new MyObjective(round);
		Assert.assertEquals(obj.getStatus(), Objective.ObjectiveStatus.ONGOING);
	}
}
