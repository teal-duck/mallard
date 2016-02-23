package com.superduckinvaders.game.entity;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.util.LwjglTestRunner;


/**
 * Test the Character class's special methods.
 */
@RunWith(LwjglTestRunner.class)
public class ParticleTest {
	private Round round;
	private Animation anim;


	@Before
	public void setUp() {
		round = Mockito.mock(Round.class);
		anim = Mockito.mock(Animation.class);
	}


	@Test
	public void NotRemovedBeforeDuration() {
		Particle pt = new Particle(round, 0f, 0f, 0.6f, anim);
		pt.update(0.5f);
		Assert.assertFalse(pt.isRemoved());
	}


	@Test
	public void RemovedAfterDuration() {
		Particle pt = new Particle(round, 0f, 0f, 0.6f, anim);
		pt.update(0.7f);
		Assert.assertTrue(pt.isRemoved());
	}
}
