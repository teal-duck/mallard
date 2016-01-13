package com.superduckinvaders.tests;

import static org.junit.Assert.*;

import com.superduckinvaders.game.round.Round;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.DuckGame;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntityTest {
	private static Round testRound;
	
	@BeforeClass
	public static void OneTimeSetup(){
		DuckGame game = new DuckGame();
		game.create();
		testRound = game.getRound();
	}
	

	@Test
	public void updateMovementTest() {
		Mob testSubject = new Mob(testRound, 20, 20, 5, null, 2);
		
		//x positive direction
		testSubject.setVelocity(1, 0);
		testSubject.update(1);
		int[] expectedCoord = {22, 20};
		int[] actualCoord = {(int)testSubject.getX(), (int)testSubject.getY()};
		assertArrayEquals(expectedCoord, actualCoord);
		
		//x negative direction, lag frames introduced (i.e. delta is higher than usual)
		testSubject.setVelocity(-1, 0);
		testSubject.update(2);
		expectedCoord[0]=18;
		expectedCoord[1]=20;
		actualCoord[0]=(int)testSubject.getX();
		actualCoord[1]=(int)testSubject.getY();
		assertArrayEquals(expectedCoord, actualCoord);
		
		//combine x and y directions, assume vector normalization before movement
		testSubject.setVelocity(3, 4);
		testSubject.update(5);
		expectedCoord[0] = 21;
		expectedCoord[1] = 24;
		actualCoord[0]=(int)testSubject.getX();
		actualCoord[1]=(int)testSubject.getY();
		assertArrayEquals(expectedCoord, actualCoord);
	}
	
	@Test 
	public void CollisionDetectionTest(){
		//TODO finish me once collision detection is complete
		return;
	}
}
		
