package com.superduckinvaders.game.desktop;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.desktop.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.Player;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

/**
 * Test the Player's methods.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // FIXME: Test after merge!
public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player(mock(Round.class), 0, 0);
    }
}
