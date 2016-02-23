package com.superduckinvaders.game.entity;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.util.LwjglTestRunner;

import junit.framework.TestCase;


/**
 * Test mallard's "unique" implementation of A* search.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // Issues with asset loading :(
public class ProjectileTest {
	private Round round;
	private PhysicsEntity owner;


	@Before
	public void setUp() {
		Assets.load();
		round = Mockito.mock(Round.class);
		owner = Mockito.mock(PhysicsEntity.class);
	}


	@Test
	public void StartsAtCorrectPosition() {
		Projectile proj = new Projectile(round, new Vector2(100, 200), new Vector2(10, 20), 0, owner);
		TestCase.assertEquals(proj.getPosition(), new Vector2(100, 200));
	}
}
