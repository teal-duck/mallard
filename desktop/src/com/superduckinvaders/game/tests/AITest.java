package com.superduckinvaders.game.tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.round.Round;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class AITest {
	
	protected static DuckGame duckGame;
 
    @Before
    public void setUp() {
    	duckGame = new DuckGame();
 
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		config.title = "SUPER DUCK INVADERS! - Team Mallard";
		new LwjglApplication(duckGame, config);
        
		while (duckGame.created == false) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//duckGame.showGameScreen(new Round(duckGame, Assets.levelOneMap));
        
    }
	
	@Test
	public void pathFindingTest1() {
		while(true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        //duckGame.getRound();
		
	}

}
