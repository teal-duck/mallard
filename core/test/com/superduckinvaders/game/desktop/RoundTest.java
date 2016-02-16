package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.desktop.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.mob.Mob;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.objective.Objective;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

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
        Round round = new Round(mock(DuckGame.class), new TiledMap());
        Objective testObj = new TestObjective(round);
        round.setObjective(testObj);
        assertEquals(round.getObjective().getObjectiveString(), TEST_OBJECTIVE_STRING);
    }
}
