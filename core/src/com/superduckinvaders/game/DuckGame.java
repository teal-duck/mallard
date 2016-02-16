package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.screen.GameScreen;
import com.superduckinvaders.game.screen.LoseScreen;
import com.superduckinvaders.game.screen.StartScreen;
import com.superduckinvaders.game.screen.WinScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DuckGame extends Game {
	
    /**
     * The width of the game window.
     */
    public static final int GAME_WIDTH = 1280;
    /**
     * The height of the game window.
     */
    public static final int GAME_HEIGHT = 720;

    Music menuTheme;

    public static final Session session = new Session();

     public static class Session{

        public int currentLevel = 1;
        public int maxUnlocked = 1;
        public int healthCounter = 6;
        public int totalScore = 0;
        public void incrementLevelCounter(){
            currentLevel += 1;
        }

        public void setLevel(int level){
             currentLevel = level;
        }
        public void unlock(int level){
             maxUnlocked = Math.max(maxUnlocked, level);
        }
        public boolean isUnlocked(int level){
             return maxUnlocked >= level;
        }
        public void unlockNext(){
            unlock(currentLevel+1);
        }
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

        menuTheme = Gdx.audio.newMusic(Gdx.files.internal("MenuTheme.ogg"));
        //MenuTheme.ogg is credited to SIMG, originally name Passionate.

        menuTheme.play();
        menuTheme.setVolume(0.2f);
        menuTheme.setLooping(true);
    }

    /**
     * Called by libGDX to set up the graphics.
     */
    @Override
    public void render() {
        super.render();
        // Take a screenshot if V is pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

            Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
            PixmapIO.writePNG(Gdx.files.external("DuckInvaders/" + new SimpleDateFormat("SS-ss-mm-HH").format(new Date()) + ".png"), pixmap);
            pixmap.dispose();
        }
    }
}
