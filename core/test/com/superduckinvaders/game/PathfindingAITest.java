package com.superduckinvaders.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.mob.Mob;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test mallard's "unique" implementation of A* search.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // Until we fix the errors!
public class PathfindingAITest {
    private Round round;
    private TextureSet textureSet;

    @Before
    public void setUp() {
        TiledMap map = new TiledMap(); // FIXME: Replace with a dummy map or something.
        round = spy(new Round(mock(DuckGame.class), map));
        textureSet = mock(TextureSet.class);
    }

    @Test
    public void ChasesPlayerHorizontally() {
        Player player = new Player(round, 0f, 0f);
        when(round.getPlayer()).thenReturn(player);
        Mob mob = new Mob(round, 10f, 0f, 100, textureSet, 5, new PathfindingAI(round, 0));
        round.update(1f);
        assertEquals(mob.getPosition(), new Vector2(5f, 0f));
    }

    @Test
    public void ChasesPlayerVertically() {
        Player player = new Player(round, 0f, 0f);
        when(round.getPlayer()).thenReturn(player);
        Mob mob = new Mob(round, 0f, 10f, 100, textureSet, 5, new PathfindingAI(round, 0));
        round.update(1f);
        assertEquals(mob.getPosition(), new Vector2(0f, 5f));
    }

    @Test
    public void StopsIfRangeProvided() {
        Player player = new Player(round, 0f, 0f);
        when(round.getPlayer()).thenReturn(player);
        Mob mob = new Mob(round, 0f, 10f, 100, textureSet, 5, new PathfindingAI(round, 2));
        round.update(10f);
        assertEquals(mob.getPosition(), new Vector2(0f, 2f)); // Stops at 2y from player.
    }

    @Test
    public void TakesExpectedRouteAroundObstacle() {
    }
}
