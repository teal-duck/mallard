package com.superduckinvaders.game.desktop;

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
		
		
	}

}
