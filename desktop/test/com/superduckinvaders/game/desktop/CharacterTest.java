package com.superduckinvaders.game.desktop;

import static org.junit.Assert.*;

import org.junit.Test;
import com.superduckinvaders.game.entity.Mob;
public class CharacterTest {

	/**
	 * testing constructor, healing and damaging functions
	 * tests whether character dies
	 */
	@Test
	public void test1() {
		Mob testSubject = new Mob(null, 120, 130, 5, null, 10);
		int[] expected = {120, 130, 5};
		int[] actual = {(int)testSubject.getX(), (int)testSubject.getY(), testSubject.getMaximumHealth()};
		assertArrayEquals(expected, actual);
		assertEquals(testSubject.getMaximumHealth(), testSubject.getCurrentHealth());
		testSubject.damage(3);
		assertEquals(2, testSubject.getCurrentHealth());
		testSubject.heal(1);
		assertEquals(3, testSubject.getCurrentHealth());
		testSubject.heal(10);
		assertEquals(5, testSubject.getCurrentHealth());
		testSubject.damage(99999);
		assertEquals(true, testSubject.isDead());
	}

}
