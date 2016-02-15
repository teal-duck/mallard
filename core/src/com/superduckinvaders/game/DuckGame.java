package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

    Music theme;

    public static final Session session = new Session();

     public static class Session{
        public int levelCounter = 1;
        public int healthCounter = 6;

        public void incrementLevelCounter(){
            levelCounter += 1;
        }
        public void setLevel(int level){levelCounter = level;}
        public void setHealthCounter(int health){
            healthCounter = health;
        }
    }

    /**
     * Initialises the startScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void create() {
        Assets.load();
        this.setScreen(new StartScreen(this));
        theme = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.mp3"));

        theme.play();
        theme.setVolume(0.5f);
    }

    /**
     * Called by libGDX to set up the graphics.
     */
    @Override
    public void render() {
        super.render();

}

}
