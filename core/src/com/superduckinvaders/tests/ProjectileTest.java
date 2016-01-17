package com.superduckinvaders.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import com.superduckinvaders.game.entity.Projectile;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;
import com.superduckinvaders.game.DuckGame;

public class ProjectileTest {

	private static Round testRound;
	
	@BeforeClass
	public static void OneTimeSetup(){
		DuckGame game = new DuckGame();
		game.create();
		testRound = game.getRound();
	}
	
	@Test
	public void projectileTest() {
		//simple movement tests
		//x direction test
		Mob testMob = new Mob(testRound, 500, 500, 5, null, 100);
		Projectile testProjectile = new Projectile(testRound, testMob.getX(), testMob.getY(), testMob.getX()+100, testMob.getY(), 10, 10, testMob);
		testProjectile.update(1);
		int[] expectedCoord = new int[] {510, 500};
		int[] actualCoord = new int[] {(int)testProjectile.getX(), (int)testProjectile.getY()};
		assertArrayEquals(expectedCoord, actualCoord);
		
		//y direction test
		testProjectile = new Projectile(testRound, testMob.getX(), testMob.getY(), testMob.getX(), testMob.getY()+100, 10, 10, testMob);
		testProjectile.update(1);
		expectedCoord = new int[]{500, 510};
		actualCoord = new int[]{(int)testProjectile.getX(), (int)testProjectile.getY()};
		assertArrayEquals(expectedCoord, actualCoord);
		
		//collision detection tests
		//move the mob to some boundary of the map
		testMob.setVelocity(-1, 0);
		for (int i =0; i<100; i++){
			testMob.update((float)0.1);
		}
		//the mob should be colliding with something to the left here and be removed
		testProjectile= new Projectile(testRound, testMob.getX(), testMob.getY(), testMob.getX(), testMob.getY()-100, 10, 10, testMob);
		testProjectile.update(1);
		assertEquals(false,  testProjectile.isRemoved());
		
	}

}
