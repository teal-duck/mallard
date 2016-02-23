package com.superduckinvaders.game.entity;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.util.LwjglTestRunner;


/**
 * Test the Character class's special methods.
 */
@RunWith(LwjglTestRunner.class)
public class CharacterTest {
	private class MyCharacter extends Character {
		MyCharacter(Round round, int x, int y, int maxHealth) {
			super(round, x, y, maxHealth);
		}


		@Override
		public void render(SpriteBatch spriteBatch) {
		}
	}


	private Round round;


	@Before
	public void setUp() {
		round = Mockito.mock(Round.class);
	}


	@Test
	public void CanAccessMaxHealth() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 50);
		Assert.assertEquals(ch.getMaximumHealth(), 50);
	}


	@Test
	public void CanAccessCurrentHealth() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 25);
		Assert.assertEquals(ch.getCurrentHealth(), 25);
	}


	@Test
	public void CanBeDamaged() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 50);
		ch.damage(15);
		Assert.assertEquals(ch.getCurrentHealth(), 35);
	}


	@Test
	public void CanBeKilled() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 50);
		ch.damage(55);
		Assert.assertTrue(ch.isDead());
	}


	@Test
	public void IsHealable() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 50);
		ch.damage(30);
		ch.heal(22);
		Assert.assertEquals(ch.getCurrentHealth(), 42);
	}


	@Test
	public void CannotBeHealedPastMax() {
		MyCharacter ch = new MyCharacter(round, 10, 10, 50);
		ch.damage(10);
		ch.heal(25);
		Assert.assertEquals(ch.getCurrentHealth(), ch.getMaximumHealth());
	}
}
