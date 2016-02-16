package com.superduckinvaders.game.desktop.util;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the test runner. Woot.
 */
@RunWith(LwjglTestRunner.class)
public class LwjglTestRunnerTest {
    private class CustomException extends Exception {}

    @Test
    public void RunsWithoutExceptions() {}

    @Test(expected=CustomException.class)
    public void CausesAnExceptionWhenExpected() throws CustomException {
        throw(new CustomException());
    }
}
