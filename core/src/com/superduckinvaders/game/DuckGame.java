package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.screen.GameScreen;
import com.superduckinvaders.game.screen.LoseScreen;
import com.superduckinvaders.game.screen.StartScreen;
import com.superduckinvaders.game.screen.WinScreen;

public class DuckGame extends Game {
	
    /**
     * The width of the game window.
     */
    public static final int GAME_WIDTH = 1280;
    /**
     * The height of the game window.
     */
    public static final int GAME_HEIGHT = 720;

    /**
     * Initialises the startScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void create() {
        Assets.load();
        this.setScreen(new StartScreen(this));
    }

    /**
     * Called by libGDX to set up the graphics.
     */
    @Override
    public void render() {
        super.render();
    }
}
