package com.superduckinvaders.game.util;


import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test the test runner. Woot.
 */
@RunWith(LwjglTestRunner.class)
public class LwjglTestRunnerTest {
	private class CustomException extends Exception {
		private static final long serialVersionUID = 1L;
	}


	@Test
	public void RunsWithoutExceptions() {
	}


	@Test(expected = CustomException.class)
	public void CausesAnExceptionWhenExpected() throws CustomException {
		throw (new CustomException());
	}
}
