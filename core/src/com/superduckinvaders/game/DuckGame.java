package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.round.Round;

public class DuckGame extends Game {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    private StartScreen startScreen = null;
    private GameScreen gameScreen = null;
    private WinScreen winScreen = null;
    private LoseScreen loseScreen = null;

    private Round round;
    
    @Override
    public void create() {
        Assets.load();

        round = new Round(this, Assets.levelOneMap);

        showStartScreen();
    }

    public void showStartScreen() {
        if (startScreen != null) {
            startScreen.dispose();
        }

        setScreen(startScreen = new StartScreen(this));
    }

    public void showGameScreen(Round round) {
        if (gameScreen != null) {
            gameScreen.dispose();
        }

        setScreen(gameScreen = new GameScreen(round));
    }

    public void showWinScreen(int score) {
        if (winScreen != null) {
            winScreen.dispose();
        }

        setScreen(winScreen = new WinScreen(this, score));
    }

    public void showLoseScreen() {
        if (loseScreen != null) {
            loseScreen.dispose();
        }

        setScreen(loseScreen = new LoseScreen(this));
    }
    
    /**
     * testing purposes, do not touch
     */
    public Round getRound(){
        return gameScreen.getRound();
    }

    @Override
    public void render() {
        super.render();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
}
