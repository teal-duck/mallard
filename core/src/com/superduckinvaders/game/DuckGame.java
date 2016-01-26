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
     * stores whether the game is in a main game state
     */
    public boolean onGameScreen = false;

    /**
     * Stores the Screen displayed at the start of the game.
     * We can cache this screen because it's the only one that's always constant.
     * @apiNote This should be final, but libGDX won't let us mark it as such.
     */
    public StartScreen startScreen;

    /**
     * Stores the current round that is being rendered using the gameScreen
     */
    private Round round;

    /**
     * Initialises the startScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void create() {
        Assets.load();

        startScreen = new StartScreen(this);
        this.setScreen(startScreen);
    }
    
    /**
     * Switches to the next screen, disposing the current one.
     *
     * @implNote The screen isn't garbage collected until java wants to, so
     *   make sure to not use the deleted screen again.
     */
    public void replaceScreen(Screen newScreen) {
        Screen currentScreen = this.getScreen();
        if (currentScreen != null) currentScreen.dispose();
        setScreen(newScreen);
    }

    /**
     * Restarts the game.
     */

    /**
     * Called by libGDX to set up the graphics.
     */
    @Override
    public void render() {
        super.render();
    }
}
