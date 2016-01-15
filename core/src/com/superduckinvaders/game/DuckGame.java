package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.round.Round;

public class DuckGame extends Game {
    GameScreen gameScreen;
    private Round round;
    
    @Override
    public void create() {
        Assets.load();

        round = new Round(this, Assets.levelOneMap);//<--- extension point (choose from different maps)

        setScreen(gameScreen = new GameScreen(round));
    }
    
    /**
     * testing purposes, do not touch
     */
    public Round getRound(){
        return round;
    }

    @Override
    public void render() {
        super.render();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
}
