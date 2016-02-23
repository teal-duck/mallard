package com.superduckinvaders.game.entity;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.util.LwjglTestRunner;


/**
 * Test the Player's methods.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // FIXME: java.lang.UnsatisfiedLinkError :(
public class PlayerTest {
	private Player player;


	@Before
	public void setUp() {
		@SuppressWarnings("unused")
		Player player = new Player(Mockito.mock(Round.class), 10, 15);
	}


	@Test
	public void StartsAtCorrectPosition() {
		Player positionedPlayer = new Player(Mockito.mock(Round.class), 10, 15);
		Assert.assertEquals(positionedPlayer.getPosition(), new Vector2(10, 15));
	}


	@Test
	public void ScoreStartsAtZero() {
		Assert.assertEquals(player.getScore(), 0);
	}


	@Test
	public void CanAddScore() {
		player.addScore(10);
		player.addScore(15);
		Assert.assertEquals(player.getScore(), 25);
	}


	@Test
	public void CanAddPickup() {
		player.givePickup(Player.Pickup.GUN, 5f);
		Assert.assertTrue(player.hasPickup(Player.Pickup.GUN));
	}


	public void ScoreMultiplierWorks() {
		player.givePickup(Player.Pickup.SCORE_MULTIPLIER, 5f);
		player.addScore(10);
		Assert.assertEquals(player.getScore(), 50);
	}


	@Test
	public void CannotBeDamagedWhenInvulnerable() {
		int startHealth = player.getCurrentHealth();
		player.givePickup(Player.Pickup.INVULNERABLE, 5f);
		player.damage(10);
		Assert.assertEquals(player.getCurrentHealth(), startHealth);
	}
}
