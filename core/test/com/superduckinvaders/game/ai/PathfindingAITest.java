package com.superduckinvaders.game.ai;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.mob.Mob;
import com.superduckinvaders.game.util.LwjglTestRunner;


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
		@SuppressWarnings("unused")
		TiledMap map = new TiledMap(); // FIXME: Replace with a dummy map or something.
		round = Mockito.spy(new Round(Mockito.mock(DuckGame.class)));
		textureSet = Mockito.mock(TextureSet.class);
	}


	@Test
	public void ChasesPlayerHorizontally() {
		Player player = new Player(round, 0f, 0f);
		Mockito.when(round.getPlayer()).thenReturn(player);
		Mob mob = new Mob(round, 10f, 0f, 100, textureSet, 5, new PathfindingAI(round, 0));
		round.update(1f);
		Assert.assertEquals(mob.getPosition(), new Vector2(5f, 0f));
	}


	@Test
	public void ChasesPlayerVertically() {
		Player player = new Player(round, 0f, 0f);
		Mockito.when(round.getPlayer()).thenReturn(player);
		Mob mob = new Mob(round, 0f, 10f, 100, textureSet, 5, new PathfindingAI(round, 0));
		round.update(1f);
		Assert.assertEquals(mob.getPosition(), new Vector2(0f, 5f));
	}


	@Test
	public void StopsIfRangeProvided() {
		Player player = new Player(round, 0f, 0f);
		Mockito.when(round.getPlayer()).thenReturn(player);
		Mob mob = new Mob(round, 0f, 10f, 100, textureSet, 5, new PathfindingAI(round, 2));
		round.update(10f);
		Assert.assertEquals(mob.getPosition(), new Vector2(0f, 2f)); // Stops at 2y from player.
	}


	@Test
	public void TakesExpectedRouteAroundObstacle() {
	}
}
