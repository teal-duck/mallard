package com.superduckinvaders.game.tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.round.Round;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class AITest {
	
    protected static DuckGame duckGame;
    private static LwjglApplication app;
 
    @Before
    public void setUp() {
       
        // use same LwjglApplication window across all AssetTestCase tests
        if (app != null)
            return;
       
        duckGame = new DuckGame();
 
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		config.title = "SUPER DUCK INVADERS! - Team Mallard";
        app = new LwjglApplication(duckGame, config);
    }
	
	@Test
	public void pathFindingTest1() {
		
	}

}
