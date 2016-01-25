package com.superduckinvaders.game.desktop;

import static org.junit.Assert.*;

import org.junit.Test;

import com.superduckinvaders.game.objective.CollectObjective;
import com.superduckinvaders.game.objective.Objective;

public class ObjectiveTest {

	@Test
	public void test() {
		CollectObjective testObj = new CollectObjective(null, null);
		assertEquals("Collect the object" ,testObj.getObjectiveString());
		assertEquals(Objective.OBJECTIVE_ONGOING, testObj.getStatus());
	}

}
