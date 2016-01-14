package com.superduckinvaders.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.round.Round;


public class AITest {
	
	private static Round testRound;
	
	@BeforeClass
	public static void OneTimeSetup(){
		DuckGame game = new DuckGame();
		game.create();
		testRound = game.getRound();
		
	}
	
	@Test
	public void pathFindingTest1() {
		
	}

}
